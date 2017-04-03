package com.slimgears.slimbind.properties;

import com.slimgears.slimbind.signals.Signal;

/**
 * Created by denis on 3/31/2017.
 */
public interface ValueProperty<T> extends Signal<T>, Property {
    Validator<T> validator();
    Signal<Boolean> valid();
}
