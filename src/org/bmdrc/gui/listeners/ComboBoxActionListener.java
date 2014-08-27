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
import modify_file.MassMatcher;
import org.bmdrc.gui.MainFrame;
import org.bmdrc.gui.interfaces.IEtcCalculationMethod;
import org.bmdrc.gui.interfaces.IMassCalculationMethod;
import org.bmdrc.gui.interfaces.ITypeOfCalculation;
import org.bmdrc.mass.tool.Compare20And500Scan;
import org.bmdrc.nmr.gui.NmrInComboBoxActionListener;
import org.bmdrc.chemistry.tool.InputThreadInformation;
import org.bmdrc.chemistry.tool.MoleculeModifier;
import org.bmdrc.chemistry.tool.MpeoeAndCdeapCalculator;
import org.bmdrc.chemistry.tool.SFECalculator;

/**
 *
 * @author SungBo Hwang <tyamazaki@naver.com>
 */
public class ComboBoxActionListener implements ItemListener, IEtcCalculationMethod, IMassCalculationMethod, ITypeOfCalculation, Serializable {
    private static final long serialVersionUID = -3309733742091373243L;

    private MainFrame itsFrame;
    //constant int variable
    private final Integer FILE_PATH_BOX_INDEX = 0;

    public ComboBoxActionListener(MainFrame theFrame) {
        this.setFrame(theFrame);
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
    public void itemStateChanged(ItemEvent ie) {
        String theSelectedCalculationMethod = ie.getItem().toString();

        this.setFrame().setSelectedCalculationMethod(theSelectedCalculationMethod);

        if (this.getFrame().getTypeOfCalculationMethod().equals(this.MASS_TYPE)) {
            System.out.println(this.getFrame().getTypeOfCalculationMethod());
            this.__calculateMassType(theSelectedCalculationMethod);
        } else if (this.getFrame().getTypeOfCalculationMethod().equals(this.NMR_TYPE)) {
            System.out.println(this.getFrame().getTypeOfCalculationMethod());
            NmrInComboBoxActionListener.generateFilePathBox(this.getFrame());
        } else if (this.getFrame().getTypeOfCalculationMethod().equals(this.ETC_TYPE)) {
            System.out.println(this.getFrame().getTypeOfCalculationMethod());
            this.__calculateEtcType(theSelectedCalculationMethod);
        }

        this.getFrame().getInputArea().revalidate();
    }

    private void __calculateMassType(String theSelectedCalculationMethod) {
        if (theSelectedCalculationMethod.equals(this.COMPARE_20_AND_500_SCAN)) {
            Compare20And500Scan.generateSplitedMoleculeFileFilePathBox(this.getFrame());
        } else if (theSelectedCalculationMethod.equals(this.MASS_MATCHING)) {
            MassMatcher.generatematchBetweenMassAndMoleculeInDBFilePathBox(this.getFrame());
        }
    }
    
    private void __calculateEtcType(String theSelectedCalculationMethod) {
        if (theSelectedCalculationMethod.equals(this.INPUT_THREAD_INFORMATION)) {
            InputThreadInformation.generateInputThreadInformationFilePathBox(this.getFrame());
        } else if (theSelectedCalculationMethod.equals(this.MERGE_MOLECULE_FILE)) {
            MoleculeModifier.generateMergeMoleculeFileFilePathBox(this.getFrame());
        } else if (theSelectedCalculationMethod.equals(this.SPLIT_MOLECULE_FILE)) {
            MoleculeModifier.generateSplitedMoleculeFileFilePathBox(this.getFrame());
        } else if(theSelectedCalculationMethod.equals(this.CALCULATE_MPEOE_AND_CDEAP)) {
            MpeoeAndCdeapCalculator.generateCalculateMpeoeAndCdeapFilePathBox(this.getFrame());
        } else if (theSelectedCalculationMethod.equals(this.CALCULATE_SOLVATION_FREE_ENERGY)) {
            SFECalculator.generatecalculateSFEFilePathBox(this.getFrame());
        }
    }
}
