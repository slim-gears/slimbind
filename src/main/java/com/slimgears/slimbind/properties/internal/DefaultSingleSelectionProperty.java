/**
 * Copyright (c) 2011-2016 EMC Corporation
 * All Rights Reserved
 * EMC Confidential: Restricted Internal Distribution
 * 4ebcffbc4faf87cb4da8841bbf214d32f045c8a8.ScaleIO
 */
package com.slimgears.slimbind.properties.internal;

import com.slimgears.slimbind.properties.PropertyInfo;
import com.slimgears.slimbind.properties.SingleSelectionProperty;
import com.slimgears.slimbind.properties.ValidationResult;
import com.slimgears.slimbind.properties.Validator;
import com.slimgears.slimbind.properties.Validators;
import com.slimgears.slimbind.signals.Signal;
import com.slimgears.slimbind.signals.Signals;
import java8.util.function.Consumer;

import java.util.Objects;

/**
 * Created by itskod on 02/04/2017.
 */
public class DefaultSingleSelectionProperty<T> extends DefaultCollectionProperty<T> implements SingleSelectionProperty<T> {
    private final Signal<T> selectedValueSignal;
    private final Validator<T> validator;
    private final Signal<Boolean> validSignal;
    private T selectedValue;

    public DefaultSingleSelectionProperty(PropertyInfo info) {
        super(info);
        this.validator = new DefaultValidator<>();
        this.validator.addValidator(Validators
                        .forPredicate(items()::contains, item -> new Exception("Not found: " + item)));

        this.validSignal = Signals.map(validator, ValidationResult::isValid);
        this.selectedValueSignal = Signals.<T>builder()
                .filter(val -> validator.validate(val).isValid())
                .build();

        selectedValueSignal.subscribe(val -> this.selectedValue = val);
    }

    @Override
    protected void onRemoved(T item) {
        if (Objects.equals(selectedValue, item)) {
            publish(null);
        }
        super.onRemoved(item);
    }

    @Override
    public void subscribe(Consumer<T> subscriber) {
        selectedValueSignal.subscribe(subscriber);
    }

    @Override
    public void unsubscribe(Consumer<T> subscriber) {
        selectedValueSignal.unsubscribe(subscriber);
    }

    @Override
    public void publish(T value) {
        selectedValueSignal.publish(value);
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
    public void accept(T value) {
        selectedValueSignal.accept(value);
    }

    @Override
    public T get() {
        return selectedValueSignal.get();
    }
}
