package com.slimgears.slimbinding;

import com.slimgears.slimbinding.annotations.Description;
import com.slimgears.slimbinding.annotations.Title;
import com.slimgears.slimbinding.properties.Property;

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
