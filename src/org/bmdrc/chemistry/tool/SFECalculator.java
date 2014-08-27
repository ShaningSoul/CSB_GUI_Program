/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.bmdrc.chemistry.tool;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Serializable;
import java.util.ArrayList;
import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import org.bmdrc.gui.MainFrame;

/**
 *
 * @author labwindows
 */
public class SFECalculator implements Serializable{
    private static final long serialVersionUID = -4676853903497913501L;

    public static void calculateSFE(MainFrame theFrame) {
        try {
        calculateSFE(theFrame.getFilePathTextFieldList().get(0).getText(), theFrame.getFilePathTextFieldList().get(1).getText());
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "IOException Error!!", "Error", JOptionPane.ERROR_MESSAGE);
        } catch (InterruptedException e) {
            JOptionPane.showMessageDialog(null, "InterruptedException Error!!", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public static void calculateSFE(String theInputFilePath, String theResultFilePath) throws IOException, InterruptedException {
        StringBuilder theCommandString = new StringBuilder();

        theCommandString.append("exe_program\\calculateSFE.exe ").append(theInputFilePath).append(" ").append(theResultFilePath);
        Runtime theRunTime = Runtime.getRuntime();
        Process theProcess = theRunTime.exec(theCommandString.toString());

        BufferedReader theOutStream = new BufferedReader(new InputStreamReader(theProcess.getInputStream()));
        BufferedReader theErrorStream = new BufferedReader(new InputStreamReader(theProcess.getErrorStream()));

        theOutStream.close();
        theErrorStream.close();

        theProcess.waitFor();
    }
    
    public static void generatecalculateSFEFilePathBox(MainFrame theFrame) {
        final int theInputBoxVerticalMargin = 5;
        
        if (theFrame.setInputArea() != null) {
            theFrame.setInputArea().removeAll();
        } else {
            theFrame.setInputArea(Box.createVerticalBox());
        }
        
        theFrame.setInputArea().setFocusable(true);
        theFrame.setFilePathTextFieldList(new ArrayList<JTextField>());
        theFrame.setInputArea().add(theFrame.getTemplateFilePathBox());
        theFrame.setInputArea().add(theFrame.generateInputBox(new JLabel("Input File : "), new JTextField(20), new JButton("select")));
        theFrame.setInputArea().add(Box.createVerticalStrut(theInputBoxVerticalMargin));
        theFrame.setInputArea().add(theFrame.generateInputBox(new JLabel("Result File : "), new JTextField(20), new JButton("select")));
    }
}
