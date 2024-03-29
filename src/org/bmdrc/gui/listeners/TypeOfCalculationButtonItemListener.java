/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this file,
 * You can obtain one at http://mozilla.org/MPL/2.0/.
 *
 * Copyright (C) 2014, SungBo Hwang <tyamazaki@naver.com>.
 */

package org.bmdrc.gui.listeners;

import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.io.Serializable;
import javax.swing.JRadioButton;
import org.bmdrc.chemistry.tool.InputThreadInformation;
import org.bmdrc.gui.ChemistryComboBox;
import org.bmdrc.gui.MainFrame;
import org.bmdrc.gui.MassComboBox;
import org.bmdrc.gui.NmrComboBox;
import org.bmdrc.gui.interfaces.ITypeOfCalculation;
import org.bmdrc.mass.tool.Compare20And500Scan;
import org.bmdrc.nmr.tool.DBSearcher;

/**
 *
 * @author SungBo Hwang <tyamazaki@naver.com>
 */
public class TypeOfCalculationButtonItemListener implements ItemListener, ITypeOfCalculation, Serializable {
    private static final long serialVersionUID = 2285616223945079178L;

    private MainFrame itsFrame;
    //constant int variable
    private final Integer FIRST_INDEX = 0;

    public TypeOfCalculationButtonItemListener(MainFrame itsFrame) {
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
    
    @Override
    public void itemStateChanged(ItemEvent e) {
        JRadioButton theButton = (JRadioButton) e.getItem();
        String theTypeOfCalculation = theButton.getText();
        
        this.setFrame().setComboBox().transferFocus();
        if(theTypeOfCalculation.equals(this.MASS_TYPE)) {
            MassComboBox theMassComboBox = new MassComboBox(this.getFrame());
            
            theMassComboBox.generateMassComboBox();
            this.setFrame().setTypeOfCalculationMethod(this.MASS_TYPE);
            this.setFrame().setSelectedCalculationMethod(theMassComboBox.getMassCalculationMethodArray()[this.FIRST_INDEX]);
            Compare20And500Scan.generateSplitedMoleculeFileFilePathBox(this.getFrame());
        } else if(theTypeOfCalculation.equals(this.NMR_TYPE)) {
            NmrComboBox theNmrComboBox = new NmrComboBox(this.getFrame());
            
            theNmrComboBox.generateNmrCombBox();
            this.setFrame().setTypeOfCalculationMethod(this.NMR_TYPE);
            this.setFrame().setSelectedCalculationMethod(theNmrComboBox.getTypeOfCalculationMethodArray()[this.FIRST_INDEX]);
            DBSearcher.generateDBSearcherFilePathBox(this.getFrame());
        } else if(theTypeOfCalculation.equals(this.CHEMISTRY_TYPE)) {
            ChemistryComboBox theEtcComboBox = new ChemistryComboBox(this.getFrame());
            
            theEtcComboBox.generateEtcCombBox();
            this.setFrame().setSelectedCalculationMethod(theEtcComboBox.getTypeOfCalculationMethodArray()[this.FIRST_INDEX]);
            this.setFrame().setTypeOfCalculationMethod(this.CHEMISTRY_TYPE);
            InputThreadInformation.generateInputThreadInformationFilePathBox(this.getFrame());
        }
        this.setFrame().revalidate();
    }

}
