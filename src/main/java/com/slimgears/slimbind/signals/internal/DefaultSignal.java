package com.slimgears.slimbind.signals.internal;

import com.slimgears.slimbind.signals.Signal;
import com.slimgears.slimbind.utils.WeakNotifier;
import java8.util.function.Consumer;

/**
 * Created by denis on 4/1/2017.
 */
public class DefaultSignal<T> implements Signal<T> {
    private final WeakNotifier<Consumer<T>> notifier = new WeakNotifier<>();
    private T lastValue;

    @Override
    public void subscribe(Consumer<T> subscriber) {
        notifier.subscribe(subscriber);
        if (lastValue != null) {
            subscriber.accept(lastValue);
        }
    }

    @Override
    public void unsubscribe(Consumer<T> subscriber) {
        notifier.unsubscribe(subscriber);
    }

    @Override
    public void publish(T value) {
        if (!notifier.isPublishing()) {
            lastValue = value;
            notifier.publish(s -> s.accept(value));
        }
    }

    @Override
    public void accept(T value) {
        publish(value);
    }

    @Override
    public T get() {
        return lastValue;
    }
}
