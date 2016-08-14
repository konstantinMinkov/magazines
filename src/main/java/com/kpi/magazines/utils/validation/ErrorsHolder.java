package com.kpi.magazines.utils.validation;

import com.kpi.magazines.utils.Localizations;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;

/**
 * Created by Konstantin Minkov on 07.07.2016.
 *
 * Provides constants of the errors names.
 * Holds Errors for different locales.
 */
public class ErrorsHolder {

    public static final String NULL_LOGIN = "login.null";
    public static final String INVALID_LOGIN = "login.invalid";
    public static final String LOGIN_EXISTS = "login.exists";
    public static final String NULL_PASSWORD = "password.null";
    public static final String PASSWORDS_MISMATCH = "password.mismatch";
    public static final String INVALID_PASSWORD = "password.invalid";
    public static final String NULL_EMAIL = "email.null";
    public static final String INVALID_EMAIL = "email.invalid";
    public static final String EMAIL_EXISTS = "email.exists";
    public static final String NULL_FIELDS = "fields.null";
    public static final String INCORRECT_LOGIN_PASSWORD = "login.password.incorrect";
    public static final String NULL_NAME = "name.null";
    public static final String NULL_CATEGORY = "category.null";
    public static final String INVALID_PRICE = "price.invalid";
    public static final String INVALID_EDITIONS_PER_MONTH = "editionsPerMonth.invalid";
    public static final String NULL_IMAGE = "image.null";

    private static final String ERRORS_BUNDLE_NAME = "errors";
    private static final Map<Locale, ErrorsHolder> LOCAL_ERRORS = new HashMap<>();
    private final ResourceBundle errorsBundle;

    static {
        LOCAL_ERRORS.put(Locale.ENGLISH, new ErrorsHolder(Locale.ENGLISH));
        LOCAL_ERRORS.put(Locale.forLanguageTag(Localizations.RU),
                new ErrorsHolder(Locale.forLanguageTag(Localizations.RU)));
    }

    private ErrorsHolder(Locale locale) {
        this.errorsBundle = ResourceBundle.getBundle(ERRORS_BUNDLE_NAME, locale);
    }

    public static ErrorsHolder getErrorsHolder(Locale locale) {
        final ErrorsHolder errors = LOCAL_ERRORS.get(locale);
        if (errors == null) {
            return LOCAL_ERRORS.get(Locale.ENGLISH);
        }
        return errors;
    }

    public String getError(String name) {
        return errorsBundle.getString(name);
    }
}
