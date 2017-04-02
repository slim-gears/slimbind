package com.slimgears.slimbind.signals;

import com.slimgears.slimbind.WeakList;
import java8.util.function.Consumer;

import java.util.Objects;

/**
 * Created by denis on 4/1/2017.
 */
public class DefaultSignal<T> implements Signal<T> {
    private final WeakList<Consumer<T>> subscribers = new WeakList<>();
    private T lastValue;

    @Override
    public void subscribe(Consumer<T> subscriber) {
        subscribers.add(subscriber);
        if (lastValue != null) {
            subscriber.accept(lastValue);
        }
    }

    @Override
    public void unsubscribe(Consumer<T> subscriber) {
        subscribers.remove(subscriber);
    }

    @Override
    public void publish(T value) {
        if (!Objects.equals(lastValue, value)) {
            lastValue = value;
            subscribers.stream().forEach(s -> s.accept(value));
        }
    }
}
