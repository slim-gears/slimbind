package com.slimgears.slimbind.signals;

import com.slimgears.slimbind.signals.internal.DefaultSignal;
import com.slimgears.slimbind.signals.internal.DefaultSignaledCollection;
import java8.util.function.Consumer;
import java8.util.function.Function;
import java8.util.function.IntFunction;
import java8.util.stream.RefStreams;

/**
 * Created by denis on 3/31/2017.
 */
public class Signals {
    public static <T> SignalBuilder<T> builder() {
        return new SignalBuilder<>();
    }

    public static <T> Signal<T> newSignal() {
        return new DefaultSignal<>();
    }

    public static <T> SignaledCollection<T> signaledCollection() {
        return new DefaultSignaledCollection<>();
    }

    @SafeVarargs
    public static <T> Signal<T[]> aggregate(IntFunction<T[]> arrayFactory, Signal<T>... signals) {
        Signal<T[]> signal = new DefaultSignal<>();
        Consumer<T> subscriber = val -> signal.publish(RefStreams.of(signals).map(Signal::get).toArray(arrayFactory));
        RefStreams.of(signals).forEach(sig -> sig.subscribe(subscriber));
        return signal;
    }

    @SafeVarargs
    public static Signal<Boolean> and(Signal<Boolean>... signals) {
        return map(aggregate(Boolean[]::new, signals), vals -> RefStreams.of(vals).allMatch(Boolean::booleanValue));
    }

    @SafeVarargs
    public static Signal<Boolean> or(Signal<Boolean>... signals) {
        return map(aggregate(Boolean[]::new, signals), vals -> RefStreams.of(vals).anyMatch(Boolean::booleanValue));
    }

    public static <T, R> Signal<R> map(Signal<T> signal, Function<T, R> mapper) {
        Signal<R> newSignal = new DefaultSignal<>();
        signal.subscribe(val -> newSignal.publish(mapper.apply(val)));
        return newSignal;
    }

    public static <T, R> Signal<R> map(Signal<T> signal, Function<T, R> mapper, Function<R, T> backMapper) {
        Signal<R> newSignal = map(signal, mapper);
        newSignal.subscribe(val -> signal.publish(backMapper.apply(val)));
        return newSignal;
    }
}
