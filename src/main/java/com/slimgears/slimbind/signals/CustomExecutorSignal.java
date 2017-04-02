package com.slimgears.slimbind.signals;

import java.util.concurrent.Executor;

/**
 * Created by denis on 4/1/2017.
 */
public class CustomExecutorSignal<T> extends DefaultSignal<T> {
    private final Executor executor;

    private CustomExecutorSignal(Signal<T> signal, Executor executor) {
        this.executor = executor;
        signal.subscribe(this::publish);
    }

    @Override
    public void publish(T value) {
        executor.execute(() -> super.publish(value));
    }

    public static <T> Signal<T> from(Signal<T> signal, Executor executor) {
        return new CustomExecutorSignal<>(signal, executor);
    }
}
