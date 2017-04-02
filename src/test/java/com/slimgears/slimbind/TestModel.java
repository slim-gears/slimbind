package com.slimgears.slimbind;

import com.slimgears.slimbind.annotations.Description;
import com.slimgears.slimbind.annotations.Title;
import com.slimgears.slimbind.properties.Property;

/**
 * Created by denis on 4/1/2017.
 */
public interface TestModel {
    @Title("First Name") @Description("Name property")
    Property<String> firstName();

    @Title("Second Name") @Description("Name property")
    Property<String> secondName();

    @Title("Age") @Description("Age property")
    Property<Integer> age();
}
