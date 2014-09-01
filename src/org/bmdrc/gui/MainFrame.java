/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this file,
 * You can obtain one at http://mozilla.org/MPL/2.0/.
 *
 * Copyright (C) 2014, SungBo Hwang <tyamazaki@naver.com>.
 */
package org.bmdrc.gui;

import org.bmdrc.gui.listeners.SelectButtonActionListener;
import org.bmdrc.gui.listeners.ComboBoxActionListener;
import org.bmdrc.gui.listeners.CalculateButtonActionListener;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.Serializable;
import java.util.ArrayList;
import javax.swing.*;
import javax.swing.event.*;
import javax.swing.border.*;
import org.bmdrc.gui.listeners.TypeOfCalculationButtonItemListener;
import org.bmdrc.chemistry.gui.interfaces.IChemistryCalculationMethod;
import org.bmdrc.gui.interfaces.ITypeOfCalculation;
import org.bmdrc.chemistry.tool.InputThreadInformation;

/**
 *
 * @author SungBo Hwang <tyamazaki@naver.com>
 */
public class MainFrame extends JFrame implements IChemistryCalculationMethod, ITypeOfCalculation, Serializable {
    private static final long serialVersionUID = 7865624320459887975L;

    private File itsInputFile;
    private ArrayList<JTextField> itsFilePathTextFieldList;
    private Box itsComboBox;
    private TextArea itsLogTextArea;
    private String itsSelectedCalculationMethod;
    private ArrayList<String> itsCalculationMethodList;
    private Box itsFilePathBox;
    private Box itsInputArea;
    private Box itsOptionArea;
    private String itsTypeOfCalculationMethod;
    private ButtonGroup itsTypeOfCalculationMethodButtonGroup;
    //constant string variable
    private final String IMAGE_PATH = "image/CSB.png";
    private final String CALCULATION_METHOD = "Calculation Method";
    private final String OPTION = "Option";
    //constant int variable
    private final Integer TEXT_FIELD_BOX_VERTICAL_MARGIN = 50;
    private final Integer INPUT_AREA_HORIZONTAIL_MARGIN = 10;
    private final Integer CALCULATION_BUTTON_VERTICAL_MARGIN = 25;
    private final Integer LOG_TEXT_AREA_HORIZONTAL_MARGIN = 10;
    private final Integer CHECK_BOX_HORIZONTAL_MARGIN = 10;
    private final Integer OPTION_CHECK_BOX_VERTICAL_MARGIN = 100;
    private final Integer CHOICE_PANEL_INDEX = 0;
    private final Integer MAXIMUM_TEXT_FIELD_HEIGHT = 25;
    private final Integer INPUT_BOX_MARGIN_BETWEEN_LABEL_AND_TEXT_FIELD = 5;
    private final Integer INPUT_BOX_MARGIN_BETWEEN_TEXT_FIELD_AND_BUTTON = 20;
    private final Integer MAXIMUM_LABEL_HEIGHT = 20;
    private final Integer FIRST_INDEX = 0;

    public MainFrame() throws HeadlessException {
        this.setLogTextArea(new TextArea(10, 50));
        this.setSelectedCalculationMethod(new String());
        this.setFilePathTextFieldList(new ArrayList<JTextField>());
        this.setTypeOfCalculationMethod(new String());
        this.setTypeOfCalculationMethodButtonGroup(new ButtonGroup());
        this.__initializeFrame();
    }

    public File getInputFile() {
        return itsInputFile;
    }

    public void setInputFile(File itsInputFile) {
        this.itsInputFile = itsInputFile;
    }

    public File setInputFile() {
        return itsInputFile;
    }

    public Box getComboBox() {
        return itsComboBox;
    }

    public void setComboBox(Box itsComboBox) {
        this.itsComboBox = itsComboBox;
    }

    public Box setComboBox() {
        return itsComboBox;
    }

    public TextArea getLogTextArea() {
        return itsLogTextArea;
    }

    public void setLogTextArea(TextArea theLogTextArea) {
        this.itsLogTextArea = theLogTextArea;
    }

    public TextArea setLogTextArea() {
        return itsLogTextArea;
    }

    public String getSelectedCalculationMethod() {
        return itsSelectedCalculationMethod;
    }

    public void setSelectedCalculationMethod(String theSelectedCalculationMethod) {
        this.itsSelectedCalculationMethod = theSelectedCalculationMethod;
    }

    public Box getInputArea() {
        return itsInputArea;
    }

    public void setInputArea(Box theInputArea) {
        this.itsInputArea = theInputArea;
    }

    public Box setInputArea() {
        return itsInputArea;
    }

    public ArrayList<JTextField> getFilePathTextFieldList() {
        return itsFilePathTextFieldList;
    }

    public void setFilePathTextFieldList(ArrayList<JTextField> theFilePathTextFieldList) {
        this.itsFilePathTextFieldList = theFilePathTextFieldList;
    }

    public ArrayList<JTextField> setFilePathTextFieldList() {
        return itsFilePathTextFieldList;
    }

    public String getTypeOfCalculationMethod() {
        return itsTypeOfCalculationMethod;
    }

    public void setTypeOfCalculationMethod(String theTypeOfCalculationMethod) {
        this.itsTypeOfCalculationMethod = theTypeOfCalculationMethod;
    }

    public ButtonGroup getTypeOfCalculationMethodButtonGroup() {
        return itsTypeOfCalculationMethodButtonGroup;
    }

    public void setTypeOfCalculationMethodButtonGroup(ButtonGroup theTypeOfCalculationMethodButtonGroup) {
        this.itsTypeOfCalculationMethodButtonGroup = theTypeOfCalculationMethodButtonGroup;
    }

    public ButtonGroup setTypeOfCalculationMethodButtonGroup() {
        return itsTypeOfCalculationMethodButtonGroup;
    }

    public Box getOptionArea() {
        return itsOptionArea;
    }

    public void setOptionArea(Box itsOptionArea) {
        this.itsOptionArea = itsOptionArea;
    }

    public Box setOptionArea() {
        return itsOptionArea;
    }
   
    private void __initializeFrame() {
        Toolkit theToolKit = Toolkit.getDefaultToolkit();
        Image theImage = theToolKit.getImage(this.getClass().getClassLoader().getResource(this.IMAGE_PATH));

        this.setTitle("CSB Program");//Program Title setting
        this.setIconImage(theImage);
        this.setSize(800, 600);//Program Frame Size
        this.setVisible(true);//Prigram Visuable
        this.setLayout(new BorderLayout());
        this.add(this.__generateComponentBox());
        this.setSelectedCalculationMethod(this.INPUT_THREAD_INFORMATION);
        this.setDefaultCloseOperation(this.EXIT_ON_CLOSE);
        this.setInputArea().setFocusable(true);
        this.revalidate();
    }

    private Box __generateComponentBox() {
        Box theBox = Box.createVerticalBox();

        theBox.add(this.__generateChoiceBox());
        theBox.add(this.__generateCalculateButtonBox());
        
        return theBox;
    }

    public Box getTemplateFilePathBox() {
        Box theBox = Box.createVerticalBox();
        JLabel theLabel = new JLabel("Input Data");

        //theBox.setMaximumSize(new Dimension(Integer.MAX_VALUE, this.MAXIMUM_TEXT_FIELD_WEIGHT * 5 + this.MAXIMUM_LABEL_HEIGHT));
        theBox.add(theLabel);
        theBox.setName("Template");

        return theBox;
    }

    private Box __generateChoiceBox() {
        Box theChoiceBox = Box.createHorizontalBox();

        InputThreadInformation.generateInputThreadInformationFilePathBox(this);
        theChoiceBox.setMaximumSize(new Dimension(Integer.MAX_VALUE, this.MAXIMUM_TEXT_FIELD_HEIGHT * 5 + this.MAXIMUM_LABEL_HEIGHT));
        theChoiceBox.add(this.getInputArea());
        theChoiceBox.add(Box.createHorizontalStrut(10));
        theChoiceBox.add(this.__generateCalculateBox());

        return theChoiceBox;
    }

    public Box generateInputBox(JLabel theLabel, JTextField theTextField, JButton theButton) {
        Box theBox = Box.createHorizontalBox();
        Box theTextFieldBox = Box.createVerticalBox();

        theTextField.setMaximumSize(new Dimension(Integer.MAX_VALUE, this.MAXIMUM_TEXT_FIELD_HEIGHT));
        theTextFieldBox.add(theTextField);

        theBox.add(theLabel);
        theBox.add(Box.createHorizontalStrut(this.INPUT_BOX_MARGIN_BETWEEN_LABEL_AND_TEXT_FIELD));
        theBox.add(theTextFieldBox);
        theBox.add(Box.createHorizontalStrut(this.INPUT_BOX_MARGIN_BETWEEN_TEXT_FIELD_AND_BUTTON));

        if (theButton != null) {
            theBox.add(theButton);
            theBox.add(Box.createHorizontalStrut(this.INPUT_BOX_MARGIN_BETWEEN_LABEL_AND_TEXT_FIELD));
            theButton.addActionListener(new SelectButtonActionListener(theTextField));
        }
        
        this.setFilePathTextFieldList().add(theTextField);
        return theBox;
    }

    private Box __generateCalculateBox() {
        Box theBox = Box.createVerticalBox();
        JLabel theLabel = new JLabel("Calculation Method");

        this.__generateCalculationComboBox();
        theBox.add(this.__generateCalculateLabelBox());
        theBox.add(this.__generateCalculationTypeBox());
        theBox.add(this.getComboBox());
        //theBox.add(this.__generateCalculationOptionBox());

        return theBox;
    }

    private Box __generateCalculationTypeBox() {
        Box theBox = Box.createHorizontalBox();
        JRadioButton theMassButton = new JRadioButton(this.MASS_TYPE, false);
        JRadioButton theNmrButton = new JRadioButton(this.NMR_TYPE, false);
        JRadioButton theEtcButton = new JRadioButton(this.CHEMISTRY_TYPE, true);

        this.setTypeOfCalculationMethod(this.CHEMISTRY_TYPE);
        
        this.setTypeOfCalculationMethodButtonGroup().add(theMassButton);
        theBox.add(theMassButton);
        theMassButton.addItemListener(new TypeOfCalculationButtonItemListener(this));
        
        this.setTypeOfCalculationMethodButtonGroup().add(theNmrButton);
        theBox.add(theNmrButton);
        theNmrButton.addItemListener(new TypeOfCalculationButtonItemListener(this));

        this.setTypeOfCalculationMethodButtonGroup().add(theEtcButton);
        theBox.add(theEtcButton);
        theEtcButton.addItemListener(new TypeOfCalculationButtonItemListener(this));

        return theBox;
    }

    private Box __generateCalculateLabelBox() {
        Box theBox = Box.createHorizontalBox();

        theBox.add(new JLabel(this.CALCULATION_METHOD));

        return theBox;
    }

    private Box __generateCalculationOptionBox() {
        Box theBox = Box.createVerticalBox();

        theBox.add(this.__generateCalculationOptionLabelBox());
        theBox.add(this.__generateCalculationOptionCheckBox());
        theBox.add(Box.createVerticalStrut(this.OPTION_CHECK_BOX_VERTICAL_MARGIN));

        return theBox;
    }

    private Box __generateCalculationOptionLabelBox() {
        Box theBox = Box.createHorizontalBox();

        theBox.add(new JLabel(this.OPTION));

        return theBox;
    }

    private Box __generateCalculationOptionCheckBox() {
        Box theBox = Box.createVerticalBox();

        theBox.add(this.__generateCalculationIotionCheckBoxSet());
        theBox.add(this.__generateCalculationIotionCheckBoxSet());

        return theBox;
    }

    private Box __generateCalculationIotionCheckBoxSet() {
        Box theBox = Box.createHorizontalBox();

        theBox.add(new JCheckBox("aa"));
        theBox.add(Box.createHorizontalStrut(this.CHECK_BOX_HORIZONTAL_MARGIN));
        theBox.add(new JCheckBox("aa"));
        theBox.add(Box.createHorizontalStrut(this.CHECK_BOX_HORIZONTAL_MARGIN));
        theBox.add(new JCheckBox("aa"));
        theBox.add(Box.createHorizontalStrut(this.CHECK_BOX_HORIZONTAL_MARGIN));
        theBox.add(new JCheckBox("aa"));
        theBox.add(Box.createHorizontalStrut(this.CHECK_BOX_HORIZONTAL_MARGIN));
        theBox.add(new JCheckBox("aa"));

        return theBox;
    }

    private void __generateCalculationComboBox() {
        ChemistryComboBox theComboBox = new ChemistryComboBox(this);
        
        theComboBox.generateEtcCombBox();
        this.setComboBox().setSize(Integer.MAX_VALUE, this.MAXIMUM_TEXT_FIELD_HEIGHT);
    }

    private Box __generateCalculateButtonBox() {
        Box theBox = Box.createVerticalBox();

        theBox.add(Box.createVerticalStrut(this.CALCULATION_BUTTON_VERTICAL_MARGIN));
        theBox.add(this.__generateCalculateButton());
        theBox.add(Box.createVerticalStrut(this.CALCULATION_BUTTON_VERTICAL_MARGIN));
        theBox.add(this.__generateLogTextArea());

        return theBox;
    }

    private Box __generateCalculateButton() {
        Box theBox = Box.createHorizontalBox();
        JButton theCalculateButton = new JButton("Calculate");

        theBox.add(theCalculateButton);
        theCalculateButton.addActionListener(new CalculateButtonActionListener(this));

        return theBox;
    }

    private Box __generateLogTextArea() {
        Box theBox = Box.createHorizontalBox();
        JScrollPane theScrollPane = new JScrollPane(this.getLogTextArea());

        theBox.add(Box.createHorizontalStrut(this.LOG_TEXT_AREA_HORIZONTAL_MARGIN));
        theBox.add(theScrollPane);
        theBox.add(Box.createHorizontalStrut(this.LOG_TEXT_AREA_HORIZONTAL_MARGIN));

        return theBox;
    }
}
