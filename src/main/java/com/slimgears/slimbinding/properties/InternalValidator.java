package com.slimgears.slimbinding.properties;

import com.slimgears.slimbinding.signals.Signal;
import com.slimgears.slimbinding.WeakList;
import com.slimgears.slimbinding.signals.Signals;
import java8.util.function.Consumer;
import java8.util.function.Function;
import java8.util.stream.Collectors;

import java.util.Collection;
import java.util.Objects;

/**
 * Created by denis on 3/31/2017.
 */
public class InternalValidator<T> implements Validator<T> {
    private final WeakList<Function<T, ? extends Exception>> validators = new WeakList<>();
    private final Signal<ValidationResult> resultSignal;

    public InternalValidator() {
        this.resultSignal = Signals.<ValidationResult>builder()
                .build();
    }

    @Override
    public ValidationResult validate(T value) {
        Collection<Exception> errors = validators.stream()
                .map(v -> v.apply(value))
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
        ValidationResult result = new DefaultValidationResult(errors);
        resultSignal.publish(result);
        return result;
    }

    @Override
    public void addValidator(Function<T, ? extends Exception> validator) {
        validators.add(validator);
    }

    @Override
    public void removeValidator(Function<T, ? extends Exception> validator) {
        validators.remove(validator);
    }

    @Override
    public void subscribe(Consumer<ValidationResult> subscriber) {
        resultSignal.subscribe(subscriber);
    }

    @Override
    public void unsubscribe(Consumer<ValidationResult> subscriber) {
        resultSignal.unsubscribe(subscriber);
    }

    @Override
    public void publish(ValidationResult value) {
        throw new RuntimeException("Not implemented");
    }
}
