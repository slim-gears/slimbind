/**
 * Copyright (c) 2011-2016 EMC Corporation
 * All Rights Reserved
 * EMC Confidential: Restricted Internal Distribution
 * 4ebcffbc4faf87cb4da8841bbf214d32f045c8a8.ScaleIO
 */
package com.slimgears.slimbind.properties.internal;

import com.slimgears.slimbind.properties.Property;
import com.slimgears.slimbind.properties.PropertyInfo;
import com.slimgears.slimbind.signals.Signal;
import com.slimgears.slimbind.signals.Signals;

/**
 * Created by itskod on 02/04/2017.
 */
public class AbstractProperty implements Property {
    private final PropertyInfo info;
    private final Signal<Boolean> enabled;

    public AbstractProperty(PropertyInfo info) {
        this.info = info;
        this.enabled = Signals.<Boolean>builder()
                .defaultValue(true)
                .build();
    }

    @Override
    public PropertyInfo info() {
        return info;
    }

    @Override
    public Signal<Boolean> enabled() {
        return enabled;
    }

    static class DefaultInfo implements PropertyInfo {
        private String title;
        private String description;

        @Override
        public String getTitle() {
            return title;
        }

        @Override
        public String getDescription() {
            return description;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public void setDescription(String description) {
            this.description = description;
        }
    }
}
