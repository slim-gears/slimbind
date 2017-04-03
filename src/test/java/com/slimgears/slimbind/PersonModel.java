package com.slimgears.slimbind;

import com.slimgears.slimbind.properties.Action;
import com.slimgears.slimbind.properties.ValueProperty;

/**
 * Created by denis on 4/1/2017.
 */
public interface PersonModel {
    ValueProperty<String> firstName();
    ValueProperty<String> lastName();
    ValueProperty<Integer> age();

    Action submit();
}
