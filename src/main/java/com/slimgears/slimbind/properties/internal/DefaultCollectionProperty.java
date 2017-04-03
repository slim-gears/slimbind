/**
 * Copyright (c) 2011-2016 EMC Corporation
 * All Rights Reserved
 * EMC Confidential: Restricted Internal Distribution
 * 4ebcffbc4faf87cb4da8841bbf214d32f045c8a8.ScaleIO
 */
package com.slimgears.slimbind.properties.internal;

import com.slimgears.slimbind.properties.CollectionProperty;
import com.slimgears.slimbind.properties.PropertyInfo;
import com.slimgears.slimbind.properties.ValueProperty;
import com.slimgears.slimbind.signals.SignaledCollection;
import com.slimgears.slimbind.signals.Signals;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by itskod on 02/04/2017.
 */
public class DefaultCollectionProperty<T> extends AbstractProperty implements CollectionProperty<T> {
    private final SignaledCollection<T> signaledCollection;
    private final Map<T, ValueProperty<T>> propertyMap = new HashMap<>();

    public DefaultCollectionProperty(PropertyInfo info) {
        super(info);
        this.signaledCollection = Signals.signaledCollection();
        signaledCollection.added().subscribe(this::onAdded);
        signaledCollection.removed().subscribe(this::onRemoved);
    }

    @Override
    public SignaledCollection<T> items() {
        return signaledCollection;
    }

    @Override
    public ValueProperty<T> property(T value) {
        return propertyMap.get(value);
    }

    protected void onAdded(T item) {
        propertyMap.put(item, new DefaultValueProperty<>(new DefaultInfo()));
    }

    protected void onRemoved(T item) {
        propertyMap.remove(item);
    }
}
