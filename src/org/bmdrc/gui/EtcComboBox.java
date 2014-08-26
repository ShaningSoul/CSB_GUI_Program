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
import javax.swing.JLayeredPane;
import org.bmdrc.gui.listeners.ComboBoxActionListener;
import org.bmdrc.gui.interfaces.IEtcCalculationMethod;

/**
 *
 * @author SungBo Hwang <tyamazaki@naver.com>
 */
public class EtcComboBox implements IEtcCalculationMethod {

    private MainFrame itsFrame;
    //constant int variable
    private Integer FIRST_INDEX = 0;
    private Integer MAXIMUM_COMBO_BOX_HEIGHT = 30;
    private Integer MAXIMUM_ROW_COUNT = 4;

    public EtcComboBox(MainFrame itsFrame) {
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

    public String[] getTypeOfCalculationMethodArray() {
        List<String> theTypeOfCalculationMethodList = new ArrayList<>();

        theTypeOfCalculationMethodList.add(this.INPUT_THREAD_INFORMATION);
        theTypeOfCalculationMethodList.add(this.MERGE_MOLECULE_FILE);
        theTypeOfCalculationMethodList.add(this.SPLIT_MOLECULE_FILE);
        theTypeOfCalculationMethodList.add(this.CALCULATE_MPEOE_AND_CDEAP);
        theTypeOfCalculationMethodList.add(this.CALCULATE_SOLVATION_FREE_ENERGY);

        return theTypeOfCalculationMethodList.toArray(new String[theTypeOfCalculationMethodList.size()]);
    }

    public void generateEtcCombBox() {
        JComboBox theEtcComboBox = new JComboBox(this.getTypeOfCalculationMethodArray());

        theEtcComboBox.setMaximumRowCount(this.MAXIMUM_ROW_COUNT);
        theEtcComboBox.setMaximumSize(new Dimension(Integer.MAX_VALUE, this.MAXIMUM_COMBO_BOX_HEIGHT));
        theEtcComboBox.setFocusable(true);
        if (this.setFrame().setComboBox() != null) {
            this.setFrame().setComboBox().removeAll();
        } else {
            this.setFrame().setComboBox(Box.createHorizontalBox());
        }
        this.setFrame().setComboBox().add(theEtcComboBox);
        theEtcComboBox.addItemListener(new ComboBoxActionListener(this.getFrame()));
        theEtcComboBox.setSelectedIndex(this.FIRST_INDEX);
        theEtcComboBox.setName("Etc");
    }
}
