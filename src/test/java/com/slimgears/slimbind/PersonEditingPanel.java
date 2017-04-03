/**
 * Copyright (c) 2011-2016 EMC Corporation
 * All Rights Reserved
 * EMC Confidential: Restricted Internal Distribution
 * 4ebcffbc4faf87cb4da8841bbf214d32f045c8a8.ScaleIO
 */
package com.slimgears.slimbind;

import javax.swing.*;

/**
 * Created by itskod on 02/04/2017.
 */
public class PersonEditingPanel {
    private final PersonModel model;
    private final JTextField firstNameLabel = new JTextField();
    private final JTextField lastNameLabel = new JTextField();
    private final JSpinner ageSpinner = new JSpinner();
    private final JButton submitButton = new JButton();

    public PersonEditingPanel(PersonModel model) {
        this.model = model;

        ComponentBinder.bind(model.firstName(), firstNameLabel);
        ComponentBinder.bind(model.lastName(), lastNameLabel);
        ComponentBinder.bind(model.age(), ageSpinner);
        ComponentBinder.bind(model.submit(), submitButton);
    }
}
