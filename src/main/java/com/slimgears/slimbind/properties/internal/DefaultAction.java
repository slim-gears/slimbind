/**
 * Copyright (c) 2011-2016 EMC Corporation
 * All Rights Reserved
 * EMC Confidential: Restricted Internal Distribution
 * 4ebcffbc4faf87cb4da8841bbf214d32f045c8a8.ScaleIO
 */
package com.slimgears.slimbind.properties.internal;

import com.slimgears.slimbind.utils.WeakList;
import com.slimgears.slimbind.properties.Action;
import com.slimgears.slimbind.properties.PropertyInfo;

/**
 * Created by itskod on 02/04/2017.
 */
public class DefaultAction extends AbstractProperty implements Action {
    private final WeakList<Runnable> subscribers = new WeakList<>();

    public DefaultAction(PropertyInfo info) {
        super(info);
    }

    @Override
    public void subscribe(Runnable subscriber) {
        subscribers.add(subscriber);
    }

    @Override
    public void unsubscribe(Runnable subscriber) {
        subscribers.remove(subscriber);
    }

    @Override
    public void trigger() {
        subscribers.stream().forEach(Runnable::run);
    }
}
