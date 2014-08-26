/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this file,
 * You can obtain one at http://mozilla.org/MPL/2.0/.
 *
 * Copyright (C) 2014, SungBo Hwang <tyamazaki@naver.com>.
 */
package org.bmdrc.gui;

import java.awt.Dimension;
import java.util.ArrayList;
import java.util.List;
import javax.swing.Box;
import javax.swing.JComboBox;
import org.bmdrc.gui.listeners.ComboBoxActionListener;
import org.bmdrc.gui.interfaces.IMassCalculationMethod;

/**
 *
 * @author SungBo Hwang <tyamazaki@naver.com>
 */
public class MassComboBox implements IMassCalculationMethod {

    private MainFrame itsFrame;
    //constant int variable
    private Integer FIRST_INDEX = 0;
    private Integer MAXIMUM_COMBO_BOX_HEIGHT = 30;
    private Integer MAXIMUM_ROW_COUNT = 4;
    

    public MassComboBox(MainFrame itsFrame) {
        this.itsFrame = itsFrame;
    }

    public MainFrame getFrame() {
        return itsFrame;
    }

    public void setFrame(MainFrame theFrame) {
        this.itsFrame = theFrame;
    }

    public MainFrame setFrame() {
        return itsFrame;
    }

    public String[] getMassCalculationMethodArray() {
        List<String> theMassCalculationMethodArray = new ArrayList<>();

        theMassCalculationMethodArray.add(this.COMPARE_20_AND_500_SCAN);
        theMassCalculationMethodArray.add(this.MASS_MATCHING);

        return theMassCalculationMethodArray.toArray(new String[theMassCalculationMethodArray.size()]);
    }

    public void generateMassComboBox() {
        JComboBox theMassComboBox = new JComboBox(this.getMassCalculationMethodArray());
        
        theMassComboBox.setMaximumRowCount(this.MAXIMUM_ROW_COUNT);
        theMassComboBox.setMaximumSize(new Dimension(Integer.MAX_VALUE, this.MAXIMUM_COMBO_BOX_HEIGHT));
        this.setFrame().setComboBox().removeAll();
        this.setFrame().setComboBox().add(theMassComboBox);
        theMassComboBox.setSelectedIndex(this.FIRST_INDEX);
        theMassComboBox.addItemListener(new ComboBoxActionListener(this.getFrame()));
        theMassComboBox.setName("Mass");
        theMassComboBox.setSize(Integer.MAX_VALUE, this.MAXIMUM_COMBO_BOX_HEIGHT);

    }
}
