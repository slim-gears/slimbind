package com.slimgears.slimbind.signals.internal;

import com.slimgears.slimbind.signals.Signal;
import java8.util.function.Predicate;

/**
 * Created by denis on 4/1/2017.
 */
public class FilteredSignal<T> extends DefaultSignal<T> {
    private final Predicate<T> predicate;

    private FilteredSignal(Signal<T> signal, Predicate<T> predicate) {
        this.predicate = predicate;
        signal.subscribe(this::publish);
    }

    @Override
    public void publish(T value) {
        if (predicate.test(value)) {
            super.publish(value);
        }
    }

    public static <T> Signal<T> from(Signal<T> signal, Predicate<T> predicate) {
        return new FilteredSignal<>(signal, predicate);
    }
}
