package com.kpi.magazines.commands;

import com.kpi.magazines.beans.User;
import com.kpi.magazines.beans.UserRole;
import com.kpi.magazines.config.Page;
import com.kpi.magazines.dao.basic.DaoManager;
import com.kpi.magazines.dao.basic.interfaces.UserDao;
import com.kpi.magazines.utils.validation.validators.Validator;
import com.kpi.magazines.utils.validation.validators.ValidatorProvider;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by Konstantin Minkov on 26.07.2016.
 */
public class LogInCommand extends AbstractCommand {

    public LogInCommand() {
        super();
    }

    public LogInCommand(boolean isProtected) {
        super(isProtected);
    }

    public Page executeGet(HttpServletRequest request, HttpServletResponse response) {
        if (request.getSession().getAttribute("user") != null) {
            return Page.USER_PROFILE.redirect();
        }
        return Page.LOGIN;
    }

    public Page executePost(HttpServletRequest request, HttpServletResponse response) {
        final UserDao userDao = DaoManager.getUserDao();
        final Validator validator = ValidatorProvider.getLogInValidator((String) request.getAttribute("locale"));
        final String login = request.getParameter("login");
        final UserRole userRole;
        final User user;
        if (request.getSession().getAttribute("user") != null) {
            return Page.USER_PROFILE.redirect();
        }
        if ( !validator.isValid(request)) {
            request.setAttribute("errors", validator.getErrors());
            request.setAttribute("login", login);
            return Page.LOGIN;
        }
        user = userDao.findByLogin(login);
        request.getSession().setAttribute("user", user);
        userRole = DaoManager.getUserRoleDao().findById(user.getUserRoleId());
        if (userRole.equals(UserRole.ADMIN)) {
            return Page.ADMIN_PANEL.redirect();
        }
        return Page.USER_PROFILE.redirect();
    }

    public Page executePut(HttpServletRequest request, HttpServletResponse response) {
        throw new UnsupportedOperationException();
    }

    public Page executeDelete(HttpServletRequest request, HttpServletResponse response) {
        throw new UnsupportedOperationException();
    }
}
