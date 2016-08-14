package com.kpi.magazines.utils.validation.errors;

/**
 * Created by Konstantin Minkov on 21.07.2016.
 *
 * Holder for errors.
 */
public interface Errors {

    String getError(String field);
    void setError(String key, String value);
    boolean hasErrors();
}
