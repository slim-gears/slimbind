package com.slimgears.slimbind.properties;

import com.slimgears.slimbind.signals.Signal;
import java8.util.function.Function;

/**
 * Created by denis on 3/31/2017.
 */
public interface Validator<T> extends Signal<ValidationResult> {
    ValidationResult validate(T value);
    void addValidator(Function<T, ? extends Exception> validator);
    void removeValidator(Function<T, ? extends Exception> validator);
}
