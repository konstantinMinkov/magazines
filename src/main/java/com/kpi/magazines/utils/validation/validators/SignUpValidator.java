package com.kpi.magazines.utils.validation.validators;

import com.kpi.magazines.beans.User;
import com.kpi.magazines.dao.basic.DaoManager;
import com.kpi.magazines.dao.basic.interfaces.UserDao;
import com.kpi.magazines.utils.validation.ErrorsHolder;
import com.sun.istack.internal.NotNull;

import javax.servlet.http.HttpServletRequest;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Konstantin Minkov on 21.07.2016.
 */
public class SignUpValidator extends AbstractValidator {

    private static final Pattern passwordPattern = Pattern.compile("^[0-9a-zA-Z_]{4,14}$");
    private static final Pattern loginPattern = Pattern.compile("^[0-9a-zA-Z_]{3,14}$");
    private static final Pattern emailPattern = Pattern.compile("^.+@.+$");

    protected SignUpValidator(String languageTag) {
        super(languageTag);
    }

    protected SignUpValidator(@NotNull Locale locale) {
        super(locale);
    }

    @Override
    protected void validate(HttpServletRequest request) {
        final String passwordConfirm = request.getParameter("passwordConfirm");
        final UserDao userDao = DaoManager.getUserDao();
        final User user = new User();
        user.setEmail(request.getParameter("email"));
        user.setLogin(request.getParameter("login"));
        user.setPassword(request.getParameter("password"));
        if (hasGeneralErrors(user, passwordConfirm)) {
            errors.setError("general", getGeneralErrors(user, passwordConfirm));
            return;
        }
        errors.setError("login", validateLogin(user, userDao));
        errors.setError("email", validateEmail(user, userDao));
        errors.setError("password", validatePassword(user));
        errors.setError("passwordConfirm", validatePasswordConfirm(user.getPassword(), passwordConfirm));
    }

    private boolean hasGeneralErrors(User user, String passwordConfirm) {
        return getGeneralErrors(user, passwordConfirm) != null;
    }

    private String getGeneralErrors(User user, String passwordConfirm) {
        if (user.getPassword() == null || user.getEmail() == null
                || user.getLogin() == null || passwordConfirm == null
                || user.getPassword().length() == 0 || user.getEmail().length() == 0
                || user.getLogin().length() == 0 || passwordConfirm.length() == 0 ){
            return errorsHolder.getError(ErrorsHolder.NULL_FIELDS);
        }
        return null;
    }

    private String validateLogin(User user, UserDao userDao) {
        if ( !isLoginValid(user.getLogin())) {
            return errorsHolder.getError(ErrorsHolder.INVALID_LOGIN);
        }
        if ( isLoginExists(user.getLogin(), userDao)) {
            return errorsHolder.getError(ErrorsHolder.LOGIN_EXISTS);
        }
        return null;
    }

    private String validateEmail(User user, UserDao userDao) {
        if ( !isEmailValid(user.getEmail())) {
            return errorsHolder.getError(ErrorsHolder.INVALID_EMAIL);
        }
        if (isEmailExists(user.getEmail(), userDao)) {
            return errorsHolder.getError(ErrorsHolder.EMAIL_EXISTS);
        }
        return null;
    }

    private String validatePassword(User user) {
        if ( !isPasswordValid(user.getPassword())) {
            return errorsHolder.getError(ErrorsHolder.INVALID_PASSWORD);
        }
        return null;
    }

    private String validatePasswordConfirm(String password, String passwordConfirm) {
        if ( !password.equals(passwordConfirm)) {
            return errorsHolder.getError(ErrorsHolder.PASSWORDS_MISMATCH);
        }
        return null;
    }

    private boolean isLoginValid(String login) {
        final Matcher matcher = loginPattern.matcher(login);
        return matcher.matches();
    }

    private boolean isEmailValid(String email) {
        final Matcher matcher = emailPattern.matcher(email);
        return matcher.matches();
    }

    private boolean isPasswordValid(String password) {
        final Matcher matcher = passwordPattern.matcher(password);
        return matcher.matches();
    }

    private boolean isEmailExists(String email, UserDao userDao) {
        return (userDao.findByEmail(email) != null);
    }

    private boolean isLoginExists(String login, UserDao userDao) {
        return (userDao.findByLogin(login) != null);
    }
}
