package com.slimgears.slimbind.properties;

import java8.util.function.Function;
import java8.util.function.Predicates;
import java8.util.Optional;
import java8.util.function.Predicate;

/**
 * Created by denis on 4/1/2017.
 */
public class Validators {
    public static <T, E extends Exception> Function<T, E> forPredicate(Predicate<T> predicate, Function<T, E> errorSupplier) {
        return val -> Optional.ofNullable(val)
                .filter(Predicates.negate(predicate))
                .map(errorSupplier)
                .orElse(null);
    }
}
