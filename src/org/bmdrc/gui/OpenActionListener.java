/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.bmdrc.gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.Serializable;
import javax.swing.JFileChooser;

/**
 *
 * @author tyanazaki
 */
public class OpenActionListener implements ActionListener, Serializable {
    private static final long serialVersionUID = -6832578127691887573L;
    
    private MainFrame itsFrame;
    
    public OpenActionListener(MainFrame theFrame) {
        this.setFrame(theFrame);
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
    
    public void actionPerformed(ActionEvent ae) {
        JFileChooser theFileChooser = new JFileChooser();
        int theResult = theFileChooser.showOpenDialog(this.getFrame());
        
        if(theResult == JFileChooser.APPROVE_OPTION) {
            this.setFrame().setInputFile(theFileChooser.getSelectedFile());
        }
    }
    
}
