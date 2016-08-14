package com.kpi.magazines.utils.validation.errors;

import lombok.ToString;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Konstantin Minkov on 21.07.2016.
 *
 * Holder for errors, occurred during validation process.
 */

@ToString
public class ValidationErrors implements Errors {

    private Map<String, String> errors;

    @Override
    public String getError(String field) {
        return errors != null ? errors.get(field) : null;
    }

    @Override
    public void setError(String key, String value) {
        if (errors == null) {
            errors = new HashMap<>();
        }
        if (value != null) {
            errors.put(key, value);
        }
    }

    @Override
    public boolean hasErrors() {
        return errors != null && !errors.isEmpty();
    }
}
