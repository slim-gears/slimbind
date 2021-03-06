/**
 * Copyright (c) 2011-2016 EMC Corporation
 * All Rights Reserved
 * EMC Confidential: Restricted Internal Distribution
 * 4ebcffbc4faf87cb4da8841bbf214d32f045c8a8.ScaleIO
 */
package com.slimgears.slimbind.properties;

import com.slimgears.slimbind.signals.Signal;

/**
 * Created by itskod on 02/04/2017.
 */
public interface Property {
    PropertyInfo info();
    Signal<Boolean> enabled();
}
