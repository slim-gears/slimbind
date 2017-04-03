/**
 * Copyright (c) 2011-2016 EMC Corporation
 * All Rights Reserved
 * EMC Confidential: Restricted Internal Distribution
 * 4ebcffbc4faf87cb4da8841bbf214d32f045c8a8.ScaleIO
 */
package com.slimgears.slimbind;

import com.slimgears.slimbind.properties.Validators;
import com.slimgears.slimbind.signals.Signal;
import com.slimgears.slimbind.signals.Signals;

/**
 * Created by itskod on 02/04/2017.
 */
public class PersonController {
    private final PersonModel model;
    private final Signal<Boolean> validSignal;

    public PersonController(PersonModel model) {
        this.model = model;

        this.model.firstName().subscribe(this::onFirstNameChanged);
        this.model.lastName().subscribe(this::onLastNameChanged);
        this.model.age().subscribe(this::onAgeChanged);

        this.model.submit().subscribe(this::onSubmit);

        this.model.firstName()
                .validator()
                .addValidator(Validators.forPredicate(name -> !"John".equals(name)));

        this.model.lastName()
                .validator()
                .addValidator(Validators.forPredicate(name -> !"Doe".equals(name)));

        validSignal = Signals.and(
                        model.firstName().valid(),
                        model.lastName().valid(),
                        model.age().valid());
        validSignal.subscribe(this.model.submit().enabled());
    }

    private void onFirstNameChanged(String newName) {
    }

    private void onLastNameChanged(String newName) {
    }

    private void onAgeChanged(int newAge) {
    }

    private void onSubmit() {

    }
}
