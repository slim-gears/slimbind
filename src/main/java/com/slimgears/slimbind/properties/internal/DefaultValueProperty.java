package com.slimgears.slimbind.properties.internal;

import com.slimgears.slimbind.properties.PropertyInfo;
import com.slimgears.slimbind.properties.ValidationResult;
import com.slimgears.slimbind.properties.Validator;
import com.slimgears.slimbind.properties.ValueProperty;
import com.slimgears.slimbind.signals.Signal;
import com.slimgears.slimbind.signals.Signals;
import java8.util.function.Consumer;
import java8.util.function.Function;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by denis on 3/31/2017.
 */
public class DefaultValueProperty<T> extends AbstractProperty implements ValueProperty<T> {
    private final Signal<T> valueSignal;
    private final Validator<T> validator;
    private final Signal<Boolean> validSignal;

    public DefaultValueProperty(PropertyInfo info) {
        super(info);

        this.validator = new DefaultValidator<>();
        this.validSignal = Signals.map(validator, ValidationResult::isValid);
        this.valueSignal = Signals.<T>builder()
                .filter(val -> validator().validate(val).isValid())
                .build();

    }

    @Override
    public Validator<T> validator() {
        return validator;
    }

    @Override
    public Signal<Boolean> valid() {
        return validSignal;
    }

    @Override
    public void subscribe(Consumer<T> subscriber) {
        valueSignal.subscribe(subscriber);
    }

    @Override
    public void unsubscribe(Consumer<T> subscriber) {
        valueSignal.unsubscribe(subscriber);
    }

    @Override
    public void publish(T value) {
        valueSignal.publish(value);
    }

    @Override
    public void accept(T value) {
        valueSignal.accept(value);
    }

    @Override
    public T get() {
        return valueSignal.get();
    }

    public static class Builder<T> {
        private final List<Function<T, ? extends Exception>> validators = new ArrayList<>();
        private final List<Consumer<T>> subscribers = new ArrayList<>();
        private T value;
        private PropertyInfo info;

        public Builder() {
            this.info = new DefaultInfo();
        }

        public ValueProperty<T> build() {
            ValueProperty<T> property = new DefaultValueProperty<>(info);
            validators.forEach(property.validator()::addValidator);
            subscribers.forEach(property::subscribe);

            if (value != null) {
                property.publish(value);
            }

            return property;
        }

        public Builder<T> info(PropertyInfo info) {
            this.info = info;
            return this;
        }

        public Builder<T> subscribe(Consumer<T> subscriber) {
            subscribers.add(subscriber);
            return this;
        }

        public Builder<T> validator(Function<T, ? extends Exception> validator) {
            validators.add(validator);
            return this;
        }

        public Builder<T> defaultValue(T value) {
            this.value = value;
            return this;
        }
    }
}
