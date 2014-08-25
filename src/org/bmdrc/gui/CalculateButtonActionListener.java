/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.bmdrc.gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JOptionPane;

/**
 *
 * @author tyanazaki
 */
public class CalculateButtonActionListener implements ActionListener {
    private MainFrame itsFrame;

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
        JOptionPane.showMessageDialog(null, "Completed", "Result", JOptionPane.INFORMATION_MESSAGE);
    }
    
}
