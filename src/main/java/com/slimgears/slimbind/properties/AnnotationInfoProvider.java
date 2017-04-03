/**
 * Copyright (c) 2011-2016 EMC Corporation
 * All Rights Reserved
 * EMC Confidential: Restricted Internal Distribution
 * 4ebcffbc4faf87cb4da8841bbf214d32f045c8a8.ScaleIO
 */
package com.slimgears.slimbind.properties;

import java.lang.reflect.Method;

/**
 * Created by itskod on 02/04/2017.
 */
public class AnnotationInfoProvider implements PropertyInfoProvider {
    @Override
    public PropertyInfo getInfo(Method method) {
        String title = Properties.titleForMethod(method);
        String description = Properties.descriptionForMethod(method);

        return new PropertyInfo() {
            @Override
            public String getTitle() {
                return title;
            }

            @Override
            public String getDescription() {
                return description;
            }
        };
    }
}
