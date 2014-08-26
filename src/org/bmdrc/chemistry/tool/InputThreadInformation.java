/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this file,
 * You can obtain one at http://mozilla.org/MPL/2.0/.
 *
 * Copyright (C) 2014, SungBo Hwang <tyamazaki@naver.com>.
 */
package org.bmdrc.chemistry.tool;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JTextField;
import org.bmdrc.gui.MainFrame;
import org.bmdrc.util.Module;

/**
 *
 * @author SungBo Hwang <tyamazaki@naver.com>
 */
public class InputThreadInformation {

    private MainFrame itsFrame;

    public InputThreadInformation(MainFrame itsFrame) {
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

    public void putThreadInformation(String theGjfDir, String theInputDataDir) throws FileNotFoundException, IOException {
        this.setFrame().setLogTextArea().append("Input Thread Information Start!!\n");
        List<File> theFileList = Module.getFileList(theGjfDir);
        int theNumberOfFile = 0;
        StringBuilder theStringBuilder = new StringBuilder();

        if (theInputDataDir.charAt(theInputDataDir.length() - 1) != '\\') {
            theInputDataDir = theInputDataDir + "\\";
        }

        if (!new File(theInputDataDir).exists()) {
            new File(theInputDataDir).mkdir();
        }

        for (File theFile : theFileList) {
            this.setFrame().setLogTextArea().append(theFile + " Start ---> ");
            StringBuilder theResultFileString = new StringBuilder();
            String theFileString = new String();
            BufferedReader theFileReader = new BufferedReader(new FileReader(theFile));

            theResultFileString.append("%nprocshared=4\n");

            while ((theFileString = theFileReader.readLine()) != null) {
                theResultFileString.append(theFileString).append("\n");
            }

            BufferedWriter theFileWriter = new BufferedWriter(new FileWriter(theInputDataDir + theFile.getName()));

            theFileWriter.flush();
            theFileWriter.write(theResultFileString.toString());
            theFileWriter.close();
            theStringBuilder.append(theFile.getName()).append(", ").append(theFile.getName().substring(0, 4)).append(".g09\n");
            theNumberOfFile++;
            this.setFrame().setLogTextArea().append("End!!\n\n");
        }

        BufferedWriter theFileWriter = new BufferedWriter(new FileWriter(theInputDataDir + "batch.bcf"));

        theFileWriter.flush();
        theFileWriter.write(theStringBuilder.toString());
        theFileWriter.close();
        this.setFrame().setLogTextArea().append("Input Thread Information End!!\n");
    }

    public static void generateInputThreadInformationFilePathBox(MainFrame theFrame) {
        final int theInputBoxVerticalMargin = 5;
        
        if (theFrame.setInputArea() != null) {
            theFrame.setInputArea().removeAll();
        } else {
            theFrame.setInputArea(Box.createVerticalBox());
        }
        
        theFrame.setFilePathTextFieldList(new ArrayList<JTextField>());
        theFrame.setInputArea().add(theFrame.getTemplateFilePathBox());
        theFrame.setInputArea().add(theFrame.generateInputBox(new JLabel("Input Dir : "), new JTextField(20), new JButton("select")));
        theFrame.setInputArea().add(Box.createVerticalStrut(theInputBoxVerticalMargin));
        theFrame.setInputArea().add(theFrame.generateInputBox(new JLabel("Result Dir : "), new JTextField(20), new JButton("select")));
    }
}
