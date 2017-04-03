/**
 * Copyright (c) 2011-2016 EMC Corporation
 * All Rights Reserved
 * EMC Confidential: Restricted Internal Distribution
 * 4ebcffbc4faf87cb4da8841bbf214d32f045c8a8.ScaleIO
 */
package com.slimgears.slimbind.properties;

import java.util.Set;

/**
 * Created by itskod on 02/04/2017.
 */
public interface MultiSelectionProperty<T> extends CollectionProperty<T>, ValueProperty<Set<T>> {
    void clearSelection();
    void select(T item);
    void unselect(T item);
    void select(T item, boolean selected);
}
