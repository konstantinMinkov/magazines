package com.kpi.magazines.utils.validation.validators;

import com.kpi.magazines.utils.validation.ErrorsHolder;
import com.sun.istack.internal.NotNull;

import javax.servlet.http.HttpServletRequest;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Konstantin Minkov on 14.08.2016.
 */
public class CardValidator extends AbstractValidator {

    private static final Pattern fullNamePattern = Pattern.compile("[a-zA-Z\\s]{3,}");
    private static final Pattern cardNumberPattern = Pattern.compile("([0-9]{4}-){3}[0-9]{4}");
    private static final Pattern datePattern = Pattern.compile("[0-9]{4}-[0-9]{2}-[0-9]{2}");
    private static final Pattern cvPattern = Pattern.compile("[0-9]{3}");

    protected CardValidator(String languageTag) {
        super(languageTag);
    }

    protected CardValidator(@NotNull Locale locale) {
        super(locale);
    }

    @Override
    protected void validate(HttpServletRequest request) {
        final String fullName = request.getParameter("fullName");
        final String cardNumber = request.getParameter("cardNumber");
        final String date = request.getParameter("date");
        final String cv = request.getParameter("cv");
        final String errorName;
        if (fullName == null || cardNumber == null || date == null || cv == null
                || fullName.length() == 0 || cardNumber.length() == 0 || date.length() == 0 || cv.length() == 0) {
            errors.setError("general", errorsHolder.getError(ErrorsHolder.NULL_FIELDS));
            return;
        }
        errorName = getFirstErrorName(fullName, cardNumber, date, cv);
        if (errorName != null) {
            errors.setError("general", errorsHolder.getError(errorName));
        }
    }

    private String getFirstErrorName(String fullName, String cardNumber, String date, String cv) {
        if ( !isMatches(fullNamePattern, fullName)) return ErrorsHolder.INVALID_FULL_NAME;
        if ( !isMatches(datePattern, date)) return ErrorsHolder.INVALID_DATE;
        if ( !isMatches(cardNumberPattern, cardNumber)) return ErrorsHolder.INVALID_CARD_NUMBER;
        if ( !isMatches(cvPattern, cv)) return ErrorsHolder.INVALID_CV;
        return null;
    }

    private boolean isMatches(Pattern pattern, String value) {
        final Matcher matcher = pattern.matcher(value);
        return matcher.matches();
    }
}
