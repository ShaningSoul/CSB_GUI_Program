/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this file,
 * You can obtain one at http://mozilla.org/MPL/2.0/.
 *
 * Copyright (C) 2014, SungBo Hwang <tyamazaki@naver.com>.
 */
package org.bmdrc.mass.tool;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JTextField;
import org.bmdrc.gui.MainFrame;
import org.bmdrc.mass.PeakList;
import org.bmdrc.mass.PeakUnit;
import org.bmdrc.util.Module;

/**
 *
 * @author SungBo Hwang <tyamazaki@naver.com>
 */
public class Compare20And500Scan implements Serializable {
    private static final long serialVersionUID = -9131789160963893409L;

    private MainFrame itsFrame;
    //constant int variable
    private final Integer TWENTY_SCAN_FILE_NAME_INDEX = 0;
    private final Integer FIVE_HUNDRED_SCAN_FILE_NAME_INDEX = 1;
    private final Integer INPUT_DIR_PATH_INDEX = 2;
    private final Integer TOLERENCE_INDEX = 3;
    private final Integer PEAK_TOLERENCE_INDEX = 4;
    private final Integer TWENTY_SCAN_PEAK_SHIFT_INDEX = 5;
    private final Integer FIVE_HUNDRED_SCAN_PEAK_SHIFT_INDEX = 6;

    public Compare20And500Scan(MainFrame itsFrame) {
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

    public void compare20And500Scan() throws FileNotFoundException, IOException {
        this.setFrame().setLogTextArea().append("compare 20 and 500 scan start!!\n");
        this.setFrame().revalidate();
        List<File> theDirList = Module.getDirList(this.getFrame().getFilePathTextFieldList().get(this.INPUT_DIR_PATH_INDEX).getText());
        List<PeakList> thePeak2dList = new ArrayList<>();
        Double theTolerence = Double.parseDouble(this.getFrame().getFilePathTextFieldList().get(this.TOLERENCE_INDEX).getText());
        Integer theNumberOfPeakTolerence = Integer.parseInt(this.getFrame().getFilePathTextFieldList().get(this.PEAK_TOLERENCE_INDEX).getText());

        this.setFrame().setLogTextArea().append("novel peak scan~~\n");
        this.setFrame().revalidate();
        
        for (File theDir : theDirList) {
            this.setFrame().setLogTextArea().append(theDir + " scan...");
            this.setFrame().setLogTextArea().revalidate();
            thePeak2dList.add(this.__findNovelPeakList(theDir));
        }

        this.setFrame().setLogTextArea().append("novel peak scan end!!\n");
        this.setFrame().revalidate();
        
        this.setFrame().setLogTextArea().append("Common peak scan...\n");
        this.setFrame().revalidate();
        
        CommonPeakFinder theCommonPeakFinder = new CommonPeakFinder(thePeak2dList, theTolerence, theNumberOfPeakTolerence);
        List<PeakList> theCommonPeak2dList = theCommonPeakFinder.generateCommonPeak2dList();

        for (int li = 0, lEnd = theCommonPeak2dList.size(); li < lEnd; li++) {
            this.setFrame().setLogTextArea().append(theCommonPeak2dList.get(li) + "\t" + this.__calculateAverageWeight(theCommonPeak2dList.get(li)) + "\t" + theCommonPeakFinder.getNumberOfFilePathList().get(li)
                    + "\t" + theCommonPeakFinder.getOutlierFilePath2dList().get(li));
        }
        this.setFrame().setLogTextArea().append("compare 20 and 500 scan End!!\n");
        this.setFrame().revalidate();
    }

    private PeakList __findNovelPeakList(File theDir) throws FileNotFoundException, IOException {
        String the20ScanFileName = this.getFrame().getFilePathTextFieldList().get(this.TWENTY_SCAN_FILE_NAME_INDEX).getText();
        String the500ScanFileName = this.getFrame().getFilePathTextFieldList().get(this.FIVE_HUNDRED_SCAN_FILE_NAME_INDEX).getText();
        Double the20ScanShift = Double.parseDouble(this.getFrame().getFilePathTextFieldList().get(this.TWENTY_SCAN_PEAK_SHIFT_INDEX).getText());
        Double the500ScanShift = Double.parseDouble(this.getFrame().getFilePathTextFieldList().get(this.FIVE_HUNDRED_SCAN_PEAK_SHIFT_INDEX).getText());
        PeakList the20ScanPeakList = new PeakList(new File(theDir + "\\" + the20ScanFileName), ",");
        PeakList the500ScanPeakList = new PeakList(new File(theDir + "\\" + the500ScanFileName), ",");

        the20ScanPeakList.shiftWeight(the20ScanShift);
        the500ScanPeakList.shiftWeight(the500ScanShift);

        NovelPeakFinder theNovelPeakFinder = new NovelPeakFinder(the20ScanPeakList, the500ScanPeakList);

        return theNovelPeakFinder.calculateNovelPeakList();
    }

    private Double __calculateAverageWeight(PeakList thePeakList) {
        Double theAverageWeight = 0.0;
        List<Double> theUsedWeight = new ArrayList<>();

        for (PeakUnit thePeak : thePeakList.getPeakList()) {
            theAverageWeight += thePeak.getWeight();
        }

        return theAverageWeight / (double) thePeakList.size();
    }

    public static void generateSplitedMoleculeFileFilePathBox(MainFrame theFrame) {
        int theInputBoxVerticalMargin = 5;

        theFrame.setInputArea().removeAll();

        theFrame.setFilePathTextFieldList(new ArrayList<JTextField>());
        theFrame.setInputArea().add(theFrame.getTemplateFilePathBox());
        theFrame.setInputArea().add(theFrame.generateInputBox(new JLabel("20 scan File Name\t: "), new JTextField(20), null));
        theFrame.setInputArea().add(Box.createVerticalStrut(theInputBoxVerticalMargin));
        theFrame.setInputArea().add(theFrame.generateInputBox(new JLabel("500 scan File Name\t: "), new JTextField(20), null));
        theFrame.setInputArea().add(Box.createVerticalStrut(theInputBoxVerticalMargin));
        theFrame.setInputArea().add(theFrame.generateInputBox(new JLabel("Input Dir path\t: "), new JTextField(20), new JButton("select")));
        theFrame.setInputArea().add(Box.createVerticalStrut(theInputBoxVerticalMargin));
        theFrame.setInputArea().add(theFrame.generateInputBox(new JLabel("Tolerence\t: "), new JTextField(20), null));
        theFrame.setInputArea().add(Box.createVerticalStrut(theInputBoxVerticalMargin));
        theFrame.setInputArea().add(theFrame.generateInputBox(new JLabel("Number of Peak Tolerence\t: "), new JTextField(20), null));
        theFrame.setInputArea().add(Box.createVerticalStrut(theInputBoxVerticalMargin));
        theFrame.setInputArea().add(theFrame.generateInputBox(new JLabel("20 Scan Shift\t: "), new JTextField(20), null));
        theFrame.setInputArea().add(Box.createVerticalStrut(theInputBoxVerticalMargin));
        theFrame.setInputArea().add(theFrame.generateInputBox(new JLabel("500 Scan Shift\t: "), new JTextField(20), null));
    }
}
