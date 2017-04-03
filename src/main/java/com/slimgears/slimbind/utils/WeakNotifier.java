/**
 * Copyright (c) 2011-2016 EMC Corporation
 * All Rights Reserved
 * EMC Confidential: Restricted Internal Distribution
 * 4ebcffbc4faf87cb4da8841bbf214d32f045c8a8.ScaleIO
 */
package com.slimgears.slimbind.utils;

import java8.util.function.Consumer;

/**
 * Created by itskod on 03/04/2017.
 */
public class WeakNotifier<S> {
    private final WeakList<S> subscribers = new WeakList<>();
    private final RecursionGuard guard = new RecursionGuard(1);

    public void subscribe(S subscriber) {
        subscribers.add(subscriber);
    }

    public void unsubscribe(S subscriber) {
        subscribers.remove(subscriber);
    }

    public boolean isPublishing() {
        return guard.isAcquired();
    }

    public void publish(Consumer<S> notification) {
        try (AutoCloseable ignored = guard.lock()) {
            subscribers.stream().forEach(notification);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
