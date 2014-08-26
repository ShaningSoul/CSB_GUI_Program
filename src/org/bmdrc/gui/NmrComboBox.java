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
import javax.swing.JComboBox;
import org.bmdrc.gui.listeners.ComboBoxActionListener;
import org.bmdrc.gui.interfaces.INmrCalculationMethod;

/**
 *
 * @author SungBo Hwang <tyamazaki@naver.com>
 */
public class NmrComboBox implements INmrCalculationMethod {

    private MainFrame itsFrame;
    //constant int variable
    private Integer FIRST_INDEX = 0;
    private Integer MAXIMUM_COMBO_BOX_HEIGHT = 30;

    public NmrComboBox(MainFrame itsFrame) {
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

        theTypeOfCalculationMethodList.add(this.DB_SEARCH_USING_2D_NMR);
        theTypeOfCalculationMethodList.add(this.HOSE_CODE_GENERATION);
        theTypeOfCalculationMethodList.add(this.HOSE_CODE_GENERATION_IN_MULTIPLE_FILE);

        return theTypeOfCalculationMethodList.toArray(new String[theTypeOfCalculationMethodList.size()]);
    }

    public void generateNmrCombBox() {
        JComboBox theNmrComboBox = new JComboBox(this.getTypeOfCalculationMethodArray());

        theNmrComboBox.setMaximumSize(new Dimension(Integer.MAX_VALUE, this.MAXIMUM_COMBO_BOX_HEIGHT));
        this.setFrame().setComboBox().removeAll();
        this.setFrame().setComboBox().add(theNmrComboBox);
        this.setFrame().setSelectedCalculationMethod(this.DB_SEARCH_USING_2D_NMR);
        theNmrComboBox.addItemListener(new ComboBoxActionListener(this.getFrame()));
        theNmrComboBox.setSelectedIndex(this.FIRST_INDEX);
        theNmrComboBox.setName("Nmr");
    }
}
