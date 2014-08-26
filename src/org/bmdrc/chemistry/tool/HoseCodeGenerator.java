/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this file,
 * You can obtain one at http://mozilla.org/MPL/2.0/.
 *
 * Copyright (C) 2014, SungBo Hwang <tyamazaki@naver.com>.
 */
package org.bmdrc.chemistry.tool;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import org.bmdrc.gui.MainFrame;
import org.bmdrc.util.Module;
import org.bmdrc.util.SDFReader;
import org.bmdrc.util.SDFWriter;
import org.openscience.cdk.MoleculeSet;
import org.openscience.cdk.exception.CDKException;
import org.openscience.cdk.interfaces.IMolecule;
import org.openscience.cdk.interfaces.IMoleculeSet;

/**
 *
 * @author SungBo Hwang <tyamazaki@naver.com>
 */
public class HoseCodeGenerator {

    public static void generateHoseCodeInMultipleFile(String theInputDir, String theResultDir, int theLevelOfHoseCode) {
        List<File> theFileList = Module.getFileList(theInputDir);
        
        for(File theFile : theFileList) {
            HoseCodeGenerator.generateHoseCode(theFile.toString(), theResultDir.toString() + theFile.getName(), theLevelOfHoseCode);
        }
    }
    
    public static void generateHoseCode(String theInputFilePath, String theResultFilePath, int theLevelOfHoseCode) {
        IMoleculeSet theMoleculeSet = SDFReader.openMoleculeFile(new File(theInputFilePath));
        IMoleculeSet theResultMoleculeSet = new MoleculeSet();

        for (int mi = 0, mEnd = theMoleculeSet.getMoleculeCount(); mi < mEnd; mi++) {
            try {
                generateFunctionalGroup(theMoleculeSet.getMolecule(mi), theLevelOfHoseCode);
            } catch (CDKException e) {
                continue;
            }
            theResultMoleculeSet.addMolecule(theMoleculeSet.getMolecule(mi));
        }

        SDFWriter.writeSDFile(theResultMoleculeSet, new File(theResultFilePath));
    }

    public static void generateFunctionalGroup(IMolecule theMolecule, int theLevelOfHoseCode) throws CDKException {
        final int FIRST_INDEX = 0;
        final String HOSE_CODE_DESCRIPTOR = "HOSE_CODE";
        final String TEMPLATE_FILE = "Template.sdf";
        final String MOLECULE_NAME_DESCRIPTOR = "cdk:Title";

        IMoleculeSet theMoleculeSet = new MoleculeSet();
        String theDescriptorValue = new String();

        Ring_Perception.setAromaticityBond(theMolecule);
        theMoleculeSet.addMolecule(theMolecule);

        for (int li = 1; li <= theLevelOfHoseCode; li++) {
            IMoleculeSet theTemplateMolecule = new MoleculeSet();
            StringBuilder theDescriptorName = new StringBuilder();

            try {
                runProgram(theMoleculeSet, li);
            } catch (Exception ex) {
                StringBuilder theErrorMessage = new StringBuilder();

                theErrorMessage.append(theMolecule.getProperty(MOLECULE_NAME_DESCRIPTOR).toString()).append(" ").append("Error!!");
                JOptionPane.showMessageDialog(null, theErrorMessage.toString(), "Error", JOptionPane.ERROR_MESSAGE);
            }

            theTemplateMolecule = SDFReader.openMoleculeFile(new File(TEMPLATE_FILE));
            theDescriptorName.append(HOSE_CODE_DESCRIPTOR).append("_").append(li);
            theDescriptorValue = theTemplateMolecule.getMolecule(FIRST_INDEX).getProperty(HOSE_CODE_DESCRIPTOR).toString();
            theMolecule.setProperty(theDescriptorName.toString(), theDescriptorValue);
        }
        File theFile = new File(TEMPLATE_FILE);
        theFile.delete();
    }

    public static void runProgram(IMoleculeSet theMoleculeSet, int theLevelOfHoseCode) {
        final String TEMPLATE_FILE = "Template.sdf";
        final String PROGRAM_FILE = "1HNMR_Validation.exe";

        StringBuilder theExecuteString = new StringBuilder();
        SDFWriter.writeSDFile(theMoleculeSet, new File(TEMPLATE_FILE));

        theExecuteString.append(PROGRAM_FILE).append(" ").append(TEMPLATE_FILE).append(" ").append(TEMPLATE_FILE).append(" ").append(theLevelOfHoseCode);
        Runtime theRunTime = Runtime.getRuntime();
        try {
            Process theProcess = theRunTime.exec(theExecuteString.toString());

            theProcess.waitFor();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "exe program Error!!", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public static void generateFilePathBox(MainFrame theFrame) {
        final int theInputBoxVerticalMargin = 5;

        theFrame.setInputArea().removeAll();
        theFrame.setInputArea().setFocusable(true);
        theFrame.setFilePathTextFieldList(new ArrayList<JTextField>());
        theFrame.setInputArea().add(theFrame.getTemplateFilePathBox());
        theFrame.setInputArea().add(theFrame.generateInputBox(new JLabel("Input File : "), new JTextField(20), new JButton("select")));
        theFrame.setInputArea().add(Box.createVerticalStrut(theInputBoxVerticalMargin));
        theFrame.setInputArea().add(theFrame.generateInputBox(new JLabel("Maximum Level of Hose Code : "), new JTextField(20), null));
        theFrame.setInputArea().add(Box.createVerticalStrut(theInputBoxVerticalMargin));
        theFrame.setInputArea().add(theFrame.generateInputBox(new JLabel("Result File : "), new JTextField(20), new JButton("select")));
    }
    
    public static void generateBatchFilePathBox(MainFrame theFrame) {
        final int theInputBoxVerticalMargin = 5;

        theFrame.setInputArea().removeAll();
        theFrame.setInputArea().setFocusable(true);
        theFrame.setFilePathTextFieldList(new ArrayList<JTextField>());
        theFrame.setInputArea().add(theFrame.getTemplateFilePathBox());
        theFrame.setInputArea().add(theFrame.generateInputBox(new JLabel("Input Dir : "), new JTextField(20), new JButton("select")));
        theFrame.setInputArea().add(Box.createVerticalStrut(theInputBoxVerticalMargin));
        theFrame.setInputArea().add(theFrame.generateInputBox(new JLabel("Maximum Level of Hose Code : "), new JTextField(20), null));
        theFrame.setInputArea().add(Box.createVerticalStrut(theInputBoxVerticalMargin));
        theFrame.setInputArea().add(theFrame.generateInputBox(new JLabel("Result Dir : "), new JTextField(20), new JButton("select")));
    }
}
