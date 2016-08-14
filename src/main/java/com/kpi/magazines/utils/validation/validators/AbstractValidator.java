package com.kpi.magazines.utils.validation.validators;

import com.kpi.magazines.utils.validation.ErrorsHolder;
import com.kpi.magazines.utils.validation.errors.Errors;
import com.kpi.magazines.utils.validation.errors.ValidationErrors;
import com.sun.istack.internal.NotNull;

import javax.servlet.http.HttpServletRequest;
import java.util.Locale;

/**
 * Created by Konstantin Minkov on 21.07.2016.
 */
public abstract class AbstractValidator implements Validator {

    protected final ErrorsHolder errorsHolder;
    protected Errors errors = new ValidationErrors();

    protected AbstractValidator(String languageTag) {
        this(languageTag != null ? Locale.forLanguageTag(languageTag) : Locale.ENGLISH);
    }

    protected AbstractValidator(@NotNull Locale locale) {
        this.errorsHolder = ErrorsHolder.getErrorsHolder(locale);
    }

    @Override
    public boolean isValid(HttpServletRequest request) {
        validate(request);
        return !errors.hasErrors();
    }

    @Override
    public Errors getErrors() {
        return errors;
    }

    protected abstract void validate(HttpServletRequest request);
}
