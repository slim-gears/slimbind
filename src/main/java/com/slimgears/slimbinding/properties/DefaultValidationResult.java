package com.slimgears.slimbinding.properties;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

/**
 * Created by denis on 3/31/2017.
 */
public class DefaultValidationResult implements ValidationResult {
    private final List<Exception> errors;

    public DefaultValidationResult(Collection<Exception> errors) {
        this.errors = new ArrayList<>(errors);
    }

    @Override
    public Collection<Exception> getErrors() {
        return errors;
    }

    @Override
    public boolean isValid() {
        return errors.isEmpty();
    }

    public static ValidationResult valid() {
        return new ValidationResult() {
            @Override
            public Collection<Exception> getErrors() {
                return Collections.emptyList();
            }

            @Override
            public boolean isValid() {
                return true;
            }
        };
    }
}
