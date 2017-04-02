package com.slimgears.slimbind.properties;

import com.slimgears.slimbind.signals.Signal;

/**
 * Created by denis on 3/31/2017.
 */
public interface Property<T> extends Signal<T> {
    interface Info<T> {
        String getTitle();
        String getDescription();
        Class<T> getType();
    }

    Info<T> info();
}
