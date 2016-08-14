package com.kpi.magazines.commands;

import com.kpi.magazines.beans.User;
import com.kpi.magazines.config.Page;
import com.kpi.magazines.dao.basic.DaoManager;
import org.apache.commons.codec.digest.DigestUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by Konstantin Minkov on 31.07.2016.
 */
public class ConfirmRegistrationCommand extends AbstractCommand {

    public ConfirmRegistrationCommand() {
        super();
    }

    public ConfirmRegistrationCommand(boolean isProtected) {
        super(isProtected);
    }

    @Override
    public Page executeGet(HttpServletRequest request, HttpServletResponse response) {
        final User user = (User) request.getSession().getAttribute("registeredUser");
        if (user == null) {
            return Page.SIGN_UP.redirect();
        }
        return Page.CONFIRM_REGISTRATION;
    }

    @Override
    public Page executePost(HttpServletRequest request, HttpServletResponse response) throws UnsupportedOperationException {
        final User user = (User) request.getSession().getAttribute("registeredUser");
        final String expectedConfirmationCode = (String) request.getSession().getAttribute("confirmationCode");
        int attempts = (int) request.getSession().getAttribute("attempts");
        final String password;
        if (user == null) {
            return Page.SIGN_UP.redirect();
        }
        if ( !expectedConfirmationCode.equals(request.getParameter("confirmationCode"))) {
            attempts--;
            if (attempts <= 0) {
                request.getSession().removeAttribute("registeredUser");
                request.getSession().removeAttribute("confirmationCode");
                return Page.SIGN_UP.redirect();
            }
            request.getSession().setAttribute("attempts", attempts);
            return Page.CONFIRM_REGISTRATION;
        }
        password = user.getPassword();
        user.setPassword(DigestUtils.shaHex(password));
        DaoManager.getUserDao().create(user);
        request.setAttribute("login", user.getLogin());
        request.setAttribute("password", password);
        return Page.LOGIN;
    }

    @Override
    public Page executePut(HttpServletRequest request, HttpServletResponse response) throws UnsupportedOperationException {
        throw new UnsupportedOperationException();
    }

    @Override
    public Page executeDelete(HttpServletRequest request, HttpServletResponse response) throws UnsupportedOperationException {
        throw new UnsupportedOperationException();
    }
}
