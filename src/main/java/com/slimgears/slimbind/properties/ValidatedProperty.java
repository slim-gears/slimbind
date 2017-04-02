package com.slimgears.slimbind.properties;

/**
 * Created by denis on 4/1/2017.
 */
public interface ValidatedProperty<T> extends Property<T> {
    Validator<T> validator();
}
