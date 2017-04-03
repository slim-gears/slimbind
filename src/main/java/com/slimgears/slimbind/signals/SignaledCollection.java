/**
 * Copyright (c) 2011-2016 EMC Corporation
 * All Rights Reserved
 * EMC Confidential: Restricted Internal Distribution
 * 4ebcffbc4faf87cb4da8841bbf214d32f045c8a8.ScaleIO
 */
package com.slimgears.slimbind.signals;

import java8.util.stream.Stream;

/**
 * Created by itskod on 02/04/2017.
 */
public interface SignaledCollection<T> {
    Stream<Signal<T>> items();
    boolean contains(T value);
    Signal<T> added();
    Signal<T> removed();
}
