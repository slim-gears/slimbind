/**
 * Copyright (c) 2011-2016 EMC Corporation
 * All Rights Reserved
 * EMC Confidential: Restricted Internal Distribution
 * 4ebcffbc4faf87cb4da8841bbf214d32f045c8a8.ScaleIO
 */
package com.slimgears.slimbind.signals.internal;

import com.slimgears.slimbind.signals.Signal;
import com.slimgears.slimbind.signals.SignaledCollection;
import com.slimgears.slimbind.signals.Signals;
import java8.util.stream.Stream;
import java8.util.stream.StreamSupport;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Created by itskod on 02/04/2017.
 */
public class DefaultSignaledCollection<T> implements SignaledCollection<T> {
    private final Map<T, Signal<T>> items = new LinkedHashMap<>();
    private final Signal<T> added = Signals.<T>builder().build();
    private final Signal<T> removed = Signals.<T>builder().build();

    public DefaultSignaledCollection() {
        added.subscribe(this::addItem);
        removed.subscribe(this::removeItem);
    }

    @Override
    public Stream<Signal<T>> items() {
        return StreamSupport.stream(items.values());
    }

    @Override
    public boolean contains(T value) {
        return items.containsKey(value);
    }

    @Override
    public Signal<T> added() {
        return added;
    }

    @Override
    public Signal<T> removed() {
        return removed;
    }

    private void addItem(T item) {
        Signal<T> itemSignal = Signals.<T>builder().defaultValue(item).build();
        items.put(item, itemSignal);
        itemSignal.subscribe(newVal -> {
            items.remove(item);
            items.put(newVal, itemSignal);
        });
    }

    private void removeItem(T item) {
        items.remove(item);
    }
}
