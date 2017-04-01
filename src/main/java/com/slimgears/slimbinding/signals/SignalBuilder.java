package com.slimgears.slimbinding.signals;

import java8.util.function.Predicates;

import java.util.concurrent.Executor;
import java8.util.function.Predicate;

/**
 * Created by denis on 4/1/2017.
 */
public class SignalBuilder<T> {
    private Executor executor = Runnable::run;
    private Predicate<T> predicate;

    public SignalBuilder<T> executor(Executor executor) {
        this.executor = executor;
        return this;
    }

    public SignalBuilder<T> filter(Predicate<T> predicate) {
        this.predicate = this.predicate == null ? predicate : Predicates.and(this.predicate, predicate);
        return this;
    }

    public Signal<T> build() {
        Signal<T> signal = new DefaultSignal<>();

        if (executor != null) {
            signal = CustomExecutorSignal.from(signal, executor);
        }

        if (predicate != null) {
            signal = FilteredSignal.from(signal, predicate);
        }

        return signal;
    }
}
