/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this file,
 * You can obtain one at http://mozilla.org/MPL/2.0/.
 *
 * Copyright (C) 2014, SungBo Hwang <tyamazaki@naver.com>.
 */
package org.bmdrc.gui.listeners;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import org.bmdrc.gui.MainFrame;
import org.bmdrc.gui.interfaces.IEtcCalculationMethod;
import org.bmdrc.gui.interfaces.IMassCalculationMethod;
import org.bmdrc.gui.interfaces.INmrCalculationMethod;
import org.bmdrc.gui.interfaces.ITypeOfCalculation;
import org.bmdrc.interfaces.IStringConstant;
import org.bmdrc.mass.tool.Compare20And500Scan;
import org.bmdrc.nmr.gui.NmrInCalculateButtonActionListener;
import org.bmdrc.chemistry.tool.InputThreadInformation;
import org.bmdrc.chemistry.tool.MoleculeModifier;
import org.bmdrc.chemistry.tool.MpeoeAndCdeapCalculator;
import org.bmdrc.chemistry.tool.SFECalculator;

/**
 *
 * @author SungBo Hwang <tyamazaki@naver.com>
 */
public class CalculateButtonActionListener implements ActionListener,IMassCalculationMethod, INmrCalculationMethod, IEtcCalculationMethod, IStringConstant, ITypeOfCalculation {

    private MainFrame itsFrame;
    //constant int variable
    private final Integer INPUT_FILE_PATH_INDEX = 0;
    private final Integer RESULT_FILE_PATH_INDEX = 1;
    private final Integer TWENTY_SCAN_FILE_NAME_INDEX_IN_COMPARE_20_AND_500_SCAN = 0;
    private final Integer FIVE_HUNDRED_SCAN_FILE_NAME_INDEX_IN_COMPARE_20_AND_500_SCAN = 1;
    private final Integer INPUT_DIR_PATH_INDEX_IN_COMPARE_20_AND_500_SCAN = 2;
    private final Integer TOLERENCE_INDEX_IN_COMPARE_20_AND_500_SCAN = 3;
    private final Integer PEAK_TOLERENCE_INDEX_IN_COMPARE_20_AND_500_SCAN = 4;
    private final Integer TWENTY_SCAN_PEAK_SHIFT_INDEX_IN_COMPARE_20_AND_500_SCAN = 5;
    private final Integer FIVE_HUNDRED_SCAN_PEAK_SHIFT_INDEX_IN_COMPARE_20_AND_500_SCAN = 6;

    public CalculateButtonActionListener(MainFrame itsFrame) {
        this.itsFrame = itsFrame;
    }

    public MainFrame getFrame() {
        return itsFrame;
    }

    public void setFrame(MainFrame itsFrame) {
        this.itsFrame = itsFrame;
    }

    public MainFrame setFrame() {
        return itsFrame;
    }

    @Override
    public void actionPerformed(ActionEvent ae) {
        this.setFrame().revalidate();
        
        if(this.getFrame().getTypeOfCalculationMethod().equals(this.MASS_TYPE)) {
            this.__calculateMassType();
        } else if(this.getFrame().getTypeOfCalculationMethod().equals(this.NMR_TYPE)) {
            NmrInCalculateButtonActionListener.calculate(this.getFrame());
        } else if(this.getFrame().getTypeOfCalculationMethod().equals(this.ETC_TYPE)) {
            this.__calculateEtcType();
        } else {
            this.getFrame().setLogTextArea().append("Need to Check!!\n");
        }
        
        
    }

    private void __calculateMassType() {
        if (this.getFrame().getSelectedCalculationMethod().equals(this.COMPARE_20_AND_500_SCAN)) {
            this.__compare20And500Scan();
        } 
    }
    
    private void __calculateEtcType() {
        if (this.getFrame().getSelectedCalculationMethod().equals(this.INPUT_THREAD_INFORMATION)) {
            this.__calculateInputThreadInformation();
        } else if (this.getFrame().getSelectedCalculationMethod().equals(this.MERGE_MOLECULE_FILE)) {
            this.__mergeMoleculeFile();
        } else if (this.getFrame().getSelectedCalculationMethod().equals(this.SPLIT_MOLECULE_FILE)) {
            this.__splitMoleculeFile();
        } else if (this.getFrame().getSelectedCalculationMethod().equals(this.CALCULATE_MPEOE_AND_CDEAP)) {
            this.__calculateMpeoeAndCdeap();
        } else if (this.getFrame().getSelectedCalculationMethod().equals(this.CALCULATE_SOLVATION_FREE_ENERGY)) {
            SFECalculator.calculateSFE(this.getFrame());
        }
    }
    private void __calculateInputThreadInformation() {
        InputThreadInformation theCalculator = new InputThreadInformation(this.getFrame());

        try {
            if (this.__isCorrectFilePathInInputThreadInformation(this.getFrame().getFilePathTextFieldList())) {
                theCalculator.putThreadInformation(this.getFrame().getFilePathTextFieldList().get(this.INPUT_FILE_PATH_INDEX).getText(),
                        this.getFrame().getFilePathTextFieldList().get(this.RESULT_FILE_PATH_INDEX).getText());
            } else {
                JOptionPane.showMessageDialog(null, "파일 경로가 잘못 되었습니다.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (FileNotFoundException ex) {
            this.setFrame().setLogTextArea().append("File Not Existed!!\n");
            JOptionPane.showMessageDialog(null, "File Not Existed!!", "Error", JOptionPane.ERROR_MESSAGE);
        } catch (IOException ex) {
            this.setFrame().setLogTextArea().append("IO exception!!\n");
            JOptionPane.showMessageDialog(null, "IO exception!!", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void __mergeMoleculeFile() {
        MoleculeModifier theMoleculeModifier = new MoleculeModifier(this.getFrame());

        if (this.__isCorrectFilePathInMergeMoleculeFile(this.getFrame().getFilePathTextFieldList())) {
            theMoleculeModifier.mergeMoleculeFile(this.getFrame().getFilePathTextFieldList().get(this.INPUT_FILE_PATH_INDEX).getText(),
                    this.getFrame().getFilePathTextFieldList().get(this.RESULT_FILE_PATH_INDEX).getText());
        } else {
            JOptionPane.showMessageDialog(null, "파일 경로가 잘못 되었습니다.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void __splitMoleculeFile() {
        MoleculeModifier theMoleculeModifier = new MoleculeModifier(this.getFrame());

        if (this.__isCorrectFilePathInSplitedMoleculeFile(this.getFrame().getFilePathTextFieldList())) {
            theMoleculeModifier.splitedMoleculeFile(this.getFrame().getFilePathTextFieldList().get(this.INPUT_FILE_PATH_INDEX).getText(),
                    this.getFrame().getFilePathTextFieldList().get(this.RESULT_FILE_PATH_INDEX).getText());
        } else {
            JOptionPane.showMessageDialog(null, "파일 경로가 잘못 되었습니다.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void __compare20And500Scan() {
        Compare20And500Scan theCalculator = new Compare20And500Scan(this.getFrame());
        try {
            if (this.__isCorrectConditionInCompare20And500Scan(this.getFrame().getFilePathTextFieldList())) {
                theCalculator.compare20And500Scan();
            }
        } catch (FileNotFoundException ex) {
            this.setFrame().setLogTextArea().append("File Not Existed!!\n");
            JOptionPane.showMessageDialog(null, "File Not Existed!!", "Error", JOptionPane.ERROR_MESSAGE);
        } catch (IOException ex) {
            this.setFrame().setLogTextArea().append("IO exception!!\n");
            JOptionPane.showMessageDialog(null, "IO exception!!", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private boolean __isCorrectFilePathInInputThreadInformation(List<JTextField> theFilePathTextFieldList) {
        for (JTextField theTextField : theFilePathTextFieldList) {
            if (theTextField.getText().isEmpty()) {
                return false;
            } else if (new File(theTextField.getText()).isFile()) {
                return false;
            }
        }

        return true;
    }

    private boolean __isCorrectFilePathInMergeMoleculeFile(List<JTextField> theFilePathTextFieldList) {
        for (JTextField theTextField : theFilePathTextFieldList) {
            if (theTextField.getText().isEmpty()) {
                return false;
            }
        }

        if (!new File(theFilePathTextFieldList.get(this.INPUT_FILE_PATH_INDEX).getText()).isDirectory()) {
            return false;
        }


        return true;
    }

    private boolean __isCorrectFilePathInSplitedMoleculeFile(List<JTextField> theFilePathTextFieldList) {
        for (JTextField theTextField : theFilePathTextFieldList) {
            if (theTextField.getText().isEmpty()) {
                return false;
            }
        }

        String theInputFilePath = theFilePathTextFieldList.get(this.INPUT_FILE_PATH_INDEX).getText();
        String theSuffix = theInputFilePath.split(this.DOT_REGEX)[theInputFilePath.split(this.DOT_REGEX).length - 1];
        File theInputfile = new File(theInputFilePath);

        if (!theInputfile.isFile() && this.__isMoleculeFile(theSuffix)) {
            return false;
        }


        return true;
    }

    private boolean __isMoleculeFile(String theSuffix) {
        if (theSuffix.equals(this.SDF_SUFFIX) || theSuffix.equals(this.SD_SUFFIX)) {
            return true;
        }

        return false;
    }

    private boolean __isCorrectConditionInCompare20And500Scan(List<JTextField> theFilePathTextFieldList) {
        if (!theFilePathTextFieldList.get(this.PEAK_TOLERENCE_INDEX_IN_COMPARE_20_AND_500_SCAN).getText().matches("[0-9]+")) {
            return false;
        } else {
            if(!this.__isStringDouble(theFilePathTextFieldList.get(this.TOLERENCE_INDEX_IN_COMPARE_20_AND_500_SCAN).getText())) {
                return false;
            } else if(!this.__isStringDouble(theFilePathTextFieldList.get(this.TWENTY_SCAN_PEAK_SHIFT_INDEX_IN_COMPARE_20_AND_500_SCAN).getText())) {
                return false;
            } else if(!this.__isStringDouble(theFilePathTextFieldList.get(this.FIVE_HUNDRED_SCAN_PEAK_SHIFT_INDEX_IN_COMPARE_20_AND_500_SCAN).getText())) {
                return false;
            }
        }

        return true;
    }

    private boolean __isStringDouble(String theString) {
        try {
            Double.parseDouble(theString);
        } catch (NumberFormatException e) {
            return false;
        }
        
        return true;
    }
    
    private void __calculateMpeoeAndCdeap() {
        try {
            MpeoeAndCdeapCalculator theCalculator = new MpeoeAndCdeapCalculator();
            
            theCalculator.generateMpeoeAndCdeapListByMoleculeFile(new File(this.getFrame().getFilePathTextFieldList().get(this.INPUT_FILE_PATH_INDEX).getText()), false);
            theCalculator.writeMpeoeAndCdeapInMoleculeFile(new File(this.getFrame().getFilePathTextFieldList().get(this.RESULT_FILE_PATH_INDEX).getText()));
        } catch (FileNotFoundException ex) {
            JOptionPane.showMessageDialog(null, "Input file이 존재하지 않습니다.", "Error", JOptionPane.ERROR_MESSAGE);
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(null, "IOException Error!!", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    
}
