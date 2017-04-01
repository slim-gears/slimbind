package com.slimgears.slimbinding.signals;

/**
 * Created by denis on 3/31/2017.
 */
public class Signals {
    public static <T> SignalBuilder<T> builder() {
        return new SignalBuilder<>();
    }
}
