package com.slimgears.slimbinding.signals;

import java8.util.function.Consumer;

/**
 * Created by denis on 3/31/2017.
 */
public interface Signal<T> {
    void subscribe(Consumer<T> subscriber);
    void unsubscribe(Consumer<T> subscriber);
    void publish(T value);
}
