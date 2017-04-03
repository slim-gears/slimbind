/**
 * Copyright (c) 2011-2016 EMC Corporation
 * All Rights Reserved
 * EMC Confidential: Restricted Internal Distribution
 * 4ebcffbc4faf87cb4da8841bbf214d32f045c8a8.ScaleIO
 */
package com.slimgears.slimbind.properties.internal;

import com.slimgears.slimbind.properties.MultiSelectionProperty;
import com.slimgears.slimbind.properties.PropertyInfo;
import com.slimgears.slimbind.properties.ValidationResult;
import com.slimgears.slimbind.properties.Validator;
import com.slimgears.slimbind.properties.Validators;
import com.slimgears.slimbind.signals.Signal;
import com.slimgears.slimbind.signals.Signals;
import java8.util.function.Consumer;
import java8.util.function.Predicate;
import java8.util.stream.StreamSupport;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by itskod on 02/04/2017.
 */
public class DefaultMultiSelectionProperty<T> extends DefaultCollectionProperty<T> implements MultiSelectionProperty<T> {
    private final Signal<Set<T>> selectionSignal;
    private final Validator<Set<T>> validator;
    private final Signal<Boolean> validSignal;
    private Set<T> selection;

    public DefaultMultiSelectionProperty(PropertyInfo info) {
        super(info);
        validator = new DefaultValidator<>();
        validator.addValidator(Validators
                .forPredicate(set -> StreamSupport.stream(set).allMatch(items()::contains)));

        validSignal = Signals.map(validator, ValidationResult::isValid);
        selectionSignal = Signals.<Set<T>>builder()
                .defaultValue(Collections.emptySet())
                .filter(val -> validator.validate(val).isValid())
                .build();

        selectionSignal.subscribe(set -> this.selection = set);
    }

    @Override
    protected void onRemoved(T item) {
        if (selection.contains(item)) {
            unselect(item);
        }
        super.onRemoved(item);
    }

    @Override
    public void subscribe(Consumer<Set<T>> subscriber) {
        selectionSignal.subscribe(subscriber);
    }

    @Override
    public void unsubscribe(Consumer<Set<T>> subscriber) {
        selectionSignal.unsubscribe(subscriber);
    }

    @Override
    public void publish(Set<T> value) {
        selectionSignal.publish(value);
    }

    @Override
    public Validator<Set<T>> validator() {
        return validator;
    }

    @Override
    public Signal<Boolean> valid() {
        return validSignal;
    }

    @Override
    public void clearSelection() {
        selectionSignal.publish(Collections.emptySet());
    }

    @Override
    public void select(T item) {
        select(item, true);
    }

    @Override
    public void unselect(T item) {
        select(item, false);
    }

    @Override
    public void select(T item, boolean selected) {
        modifySelection(set -> set.contains(item) == !selected,
                selected
                        ? set -> set.add(item)
                        : set -> set.remove(item));
    }

    private void modifySelection(Predicate<Set<T>> condition, Consumer<Set<T>> modifier) {
        if (condition.test(selection)) {
            Set<T> newSet = new HashSet<>(selection);
            modifier.accept(newSet);
            publish(newSet);
        }
    }

    @Override
    public void accept(Set<T> set) {
        selectionSignal.accept(set);
    }

    @Override
    public Set<T> get() {
        return selectionSignal.get();
    }
}
