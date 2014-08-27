/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.bmdrc.gui;

import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.io.Serializable;


/**
 *
 * @author tyanazaki
 */
public class ComboBoxActionListener implements ItemListener, Serializable {
    private static final long serialVersionUID = 5561897554004515423L;

    private MainFrame itsFrame;
    
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
        this.setFrame().setSelectedCalculationMethod(ie.getItem().toString());
    }
    
}
