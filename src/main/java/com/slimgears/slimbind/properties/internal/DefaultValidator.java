package com.slimgears.slimbind.properties.internal;

import com.slimgears.slimbind.properties.ValidationResult;
import com.slimgears.slimbind.properties.Validator;
import com.slimgears.slimbind.signals.Signal;
import com.slimgears.slimbind.utils.WeakList;
import com.slimgears.slimbind.signals.Signals;
import java8.util.function.Consumer;
import java8.util.function.Function;
import java8.util.stream.Collectors;

import java.util.Collection;
import java.util.Objects;

/**
 * Created by denis on 3/31/2017.
 */
public class DefaultValidator<T> implements Validator<T> {
    private final WeakList<Function<T, ? extends Exception>> validators = new WeakList<>();
    private final Signal<ValidationResult> resultSignal;
    private ValidationResult lastResult;

    public DefaultValidator() {
        this.resultSignal = Signals.<ValidationResult>builder()
                .build();
    }

    @Override
    public ValidationResult validate(T value) {
        Collection<Exception> errors = validators.stream()
                .map(v -> v.apply(value))
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
        lastResult = new DefaultValidationResult(errors);
        resultSignal.publish(lastResult);
        return lastResult;
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

    @Override
    public void accept(ValidationResult validationResult) {
        publish(validationResult);
    }

    @Override
    public ValidationResult get() {
        return lastResult;
    }
}
