package com.slimgears.slimbind.signals;

import java8.util.function.Consumer;
import java8.util.function.Supplier;

/**
 * Created by denis on 3/31/2017.
 */
public interface Signal<T> extends Consumer<T>, Supplier<T> {
    void subscribe(Consumer<T> subscriber);
    void unsubscribe(Consumer<T> subscriber);
    void publish(T value);
}
