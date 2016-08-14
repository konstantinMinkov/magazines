package com.kpi.magazines.commands;

import com.kpi.magazines.beans.User;
import com.kpi.magazines.beans.UserRole;
import com.kpi.magazines.config.Page;
import com.kpi.magazines.utils.mail.EmailLocalizationService;
import com.kpi.magazines.utils.mail.GmailService;
import com.kpi.magazines.utils.velocity.VelocityService;
import com.kpi.magazines.utils.validation.validators.Validator;
import com.kpi.magazines.utils.validation.validators.ValidatorProvider;
import org.apache.commons.lang.RandomStringUtils;
import org.apache.velocity.VelocityContext;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by Konstantin Minkov on 26.07.2016.
 */
public class SignUpCommand extends AbstractCommand {

    private static final String EMAIL_FILE_NAME = "email.vm";

    public SignUpCommand() {
        super();
    }

    public SignUpCommand(boolean isProtected) {
        super(isProtected);
    }

    @Override
    public Page executeGet(HttpServletRequest request, HttpServletResponse response) {
        if (request.getSession().getAttribute("user") != null) {
            return Page.USER_PROFILE.redirect();
        }
        return Page.SIGN_UP;
    }

    @Override
    public Page executePost(HttpServletRequest request, HttpServletResponse response) throws UnsupportedOperationException {
//        final UserDao userDao = DaoManager.getUserDao();
        final String login = request.getParameter("login");
        final String email = request.getParameter("email");
        final UserRole userRole = UserRole.USER;
        final Validator validator = ValidatorProvider.getSignUpValidator((String) request.getAttribute("locale"));
        final User user;
        final String confirmationCode;
        if ( !validator.isValid(request)) {
            request.setAttribute("errors", validator.getErrors());
            request.setAttribute("login", login);
            request.setAttribute("email", email);
            return Page.SIGN_UP;
        }
        user = new User();
        user.setLogin(login);
        user.setEmail(email);
        user.setPassword(request.getParameter("password"));
        user.setUserRoleId(userRole.getId());
        request.getSession().setAttribute("registeredUser", user);
        request.getSession().setAttribute("attempts", 3);
        confirmationCode = RandomStringUtils.random(8, true, true);
        request.getSession().setAttribute("confirmationCode", confirmationCode);
        GmailService.sendMessage("Magazines confirmation code", createMessage(request, confirmationCode), email);
        return Page.CONFIRM_REGISTRATION.redirect();
    }

    private String createMessage(HttpServletRequest request, String confirmationCode) {
        final VelocityContext context = EmailLocalizationService.getContext((String) request.getAttribute("locale"));
        context.internalPut("confirmationCode", confirmationCode);
//        final String absolutePath = request.getServletContext().getRealPath(EMAIL_FILE_NAME);
        return VelocityService.render(EMAIL_FILE_NAME, context);
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
