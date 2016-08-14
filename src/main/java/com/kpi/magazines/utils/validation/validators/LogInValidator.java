package com.kpi.magazines.utils.validation.validators;

import com.kpi.magazines.utils.validation.ErrorsHolder;
import com.kpi.magazines.utils.validation.errors.ValidationErrors;
import com.kpi.magazines.beans.User;
import com.kpi.magazines.dao.basic.DaoManager;
import com.kpi.magazines.dao.basic.interfaces.UserDao;
import com.sun.istack.internal.NotNull;
import org.apache.commons.codec.digest.DigestUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.Locale;

/**
 * Created by Konstantin Minkov on 21.07.2016.
 */
public class LogInValidator extends AbstractValidator {

    protected LogInValidator(String languageTag) {
        super(languageTag);
    }

    protected LogInValidator(@NotNull Locale locale) {
        super(locale);
    }

    @Override
    protected void validate(HttpServletRequest request) {
        final UserDao userDao = DaoManager.getUserDao();
        final String login = request.getParameter("login");
        final String password = request.getParameter("password");
        final User user;
        errors = new ValidationErrors();
        if (login == null || password == null ||
                login.length() == 0 || password.length() == 0) {
            errors.setError("general", errorsHolder.getError(ErrorsHolder.NULL_FIELDS));
            return;
        }
        user = userDao.findByLoginAndPassword(login, DigestUtils.shaHex(password));
        if (user == null) {
            errors.setError("general", errorsHolder.getError(ErrorsHolder.INCORRECT_LOGIN_PASSWORD));
        }
        System.out.println(errors);
    }
}
