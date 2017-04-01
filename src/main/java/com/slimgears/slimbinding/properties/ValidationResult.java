package com.slimgears.slimbinding.properties;

import java.util.Collection;

/**
 * Created by denis on 3/31/2017.
 */
public interface ValidationResult {
    Collection<Exception> getErrors();
    boolean isValid();
}
