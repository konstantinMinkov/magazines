package com.kpi.magazines.utils.validation.validators;

import com.kpi.magazines.utils.validation.errors.Errors;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by Konstantin Minkov on 21.07.2016.
 *
 * Basic Validator interface.
 */
public interface Validator {

    /**
     * Gets params from request and validates them.
     * @param request - HttpServletRequest.
     * @return - true, if all params are valid, otherwise holds all errors inside Errors object.
     */
    boolean isValid(HttpServletRequest request);

    /**
     * @return Errors, occurred during validation process.
     */
    Errors getErrors();
}
