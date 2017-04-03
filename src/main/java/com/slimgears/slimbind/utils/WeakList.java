package com.slimgears.slimbind.utils;

import java8.util.stream.Stream;
import java8.util.stream.StreamSupport;

import java.lang.ref.ReferenceQueue;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Created by denis on 3/31/2017.
 */
public class WeakList<T>  {
    private final List<WeakReference<T>> items = new ArrayList<>();
    private final ReferenceQueue<T> referenceQueue = new ReferenceQueue<>();

    public void add(T val) {
        WeakReference<T> ref = new WeakReference<>(val, referenceQueue);
        clean();
        items.add(ref);
    }

    public void remove(T val) {
        items.removeIf(ref -> val.equals(ref.get()));
    }

    public Stream<T> stream() {
        clean();
        return StreamSupport.stream(items).map(WeakReference::get).filter(Objects::nonNull);
    }

    private void clean() {
        while (true) {
            //noinspection unchecked
            WeakReference<T> ref = (WeakReference<T>) referenceQueue.poll();
            if (ref == null) {
                break;
            }
            items.remove(ref);
        }
    }
}
