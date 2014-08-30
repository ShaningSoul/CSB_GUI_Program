/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this file,
 * You can obtain one at http://mozilla.org/MPL/2.0/.
 *
 * Copyright (C) 2014, SungBo Hwang <tyamazaki@naver.com>.
 */
package org.bmdrc.chemistry.tool;

import java.io.File;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import org.bmdrc.gui.MainFrame;
import org.bmdrc.util.Module;
import org.bmdrc.util.SDFReader;
import org.bmdrc.util.SDFWriter;
import org.openscience.cdk.MoleculeSet;
import org.openscience.cdk.interfaces.IMoleculeSet;

/**
 *
 * @author SungBo Hwang <tyamazaki@naver.com>
 */
public class MoleculeModifier implements Serializable {

    private static final long serialVersionUID = 8607769951655757519L;
    private MainFrame itsMainFrame;

    public MoleculeModifier(MainFrame itsMainFrame) {
        this.itsMainFrame = itsMainFrame;
    }

    public MainFrame getMainFrame() {
        return itsMainFrame;
    }

    public void setMainFrame(MainFrame itsMainFrame) {
        this.itsMainFrame = itsMainFrame;
    }

    public MainFrame setMainFrame() {
        return itsMainFrame;
    }

    public void mergeMoleculeFile(String theInputDir, String theResultFilePath) {
        List<File> theFileList = Module.getFileList(theInputDir);
        IMoleculeSet theResultMoleculeSet = new MoleculeSet();
        int theNumberOfFile = theFileList.size();

        this.setMainFrame().setLogTextArea().append("Merge Molecule File Start!!\n");
        for (File theFile : theFileList) {
            try {
                IMoleculeSet theMoleculeSet = SDFReader.openMoleculeFile(theFile);

                theResultMoleculeSet.addMolecule(theMoleculeSet.getMolecule(0));
            } catch (NullPointerException e) {
                this.setMainFrame().setLogTextArea().append(theFile.toString() + " ---> Error!!\n");
                theNumberOfFile--;
            }
        }
        this.setMainFrame().setLogTextArea().append("Read End!!\n");
        SDFWriter.writeSDFile(theResultMoleculeSet, new File(theResultFilePath));
        this.setMainFrame().setLogTextArea().append("Write End!!\n");
        this.setMainFrame().setLogTextArea().append("Total Number of File : " + theFileList.size() + "\n");
        this.setMainFrame().setLogTextArea().append("Success : " + theNumberOfFile + "\n");
        this.setMainFrame().setLogTextArea().append("Fail : " + (theFileList.size() - theNumberOfFile) + "\n");
    }

    public static void generateMergeMoleculeFileFilePathBox(MainFrame theFrame) {
        int theInputBoxVerticalMargin = 5;

        theFrame.setInputArea().removeAll();

        theFrame.setFilePathTextFieldList(new ArrayList<JTextField>());
        theFrame.setInputArea().add(theFrame.getTemplateFilePathBox());
        theFrame.setInputArea().add(theFrame.generateInputBox(new JLabel("Input Dir : "), new JTextField(20), new JButton("select")));
        theFrame.setInputArea().add(Box.createVerticalStrut(theInputBoxVerticalMargin));
        theFrame.setInputArea().add(theFrame.generateInputBox(new JLabel("Result File : "), new JTextField(20), new JButton("select")));
    }

    public void splitedMoleculeFile(String theMoleculeFilePath, String theResultDir) {
        IMoleculeSet theMoleculeSet = SDFReader.openMoleculeFile(new File(theMoleculeFilePath));
        
        this.setMainFrame().setLogTextArea().append("Split Molecule File Start!!\n");
        if (!theResultDir.substring(theResultDir.length() - 1, theResultDir.length() - 1).equals("\\")) {
            theResultDir = theResultDir + "\\";
        }

        for (int mi = 0, mEnd = theMoleculeSet.getMoleculeCount(); mi < mEnd; mi++) {
            IMoleculeSet theResultMoleculeSet = new MoleculeSet();

            theResultMoleculeSet.addMolecule(theMoleculeSet.getMolecule(mi));
            SDFWriter.writeSDFile(theResultMoleculeSet, new File(theResultDir + String.format("%04d", mi) + ".sdf"));
        }
        this.setMainFrame().setLogTextArea().append("Split Molecule End!!\n");
        this.setMainFrame().setLogTextArea().append("Total Number of File : " + theMoleculeSet.getMoleculeCount() + "\n");
    }

    public static void generateSplitedMoleculeFileFilePathBox(MainFrame theFrame) {
        int theInputBoxVerticalMargin = 5;

        theFrame.setInputArea().removeAll();

        theFrame.setFilePathTextFieldList(new ArrayList<JTextField>());
        theFrame.setInputArea().add(theFrame.getTemplateFilePathBox());
        theFrame.setInputArea().add(theFrame.generateInputBox(new JLabel("Input File : "), new JTextField(20), new JButton("select")));
        theFrame.setInputArea().add(Box.createVerticalStrut(theInputBoxVerticalMargin));
        theFrame.setInputArea().add(theFrame.generateInputBox(new JLabel("Result Dir : "), new JTextField(20), new JButton("select")));
    }
}
