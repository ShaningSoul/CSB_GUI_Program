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
import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JTextField;
import modify_file.MassMatcher;
import org.bmdrc.gui.MainFrame;

/**
 *
 * @author SungBo Hwang <tyamazaki@naver.com>
 */
public class MassMatchingAddDirButtonActionListener implements ActionListener {

    private MainFrame itsFrame;
    
    public MassMatchingAddDirButtonActionListener(MainFrame theFrame) {
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
    public void actionPerformed(ActionEvent e) {
        JButton theAddDBDirButton = new JButton("Add Dir");
        
        
        theAddDBDirButton.addActionListener(new MassMatchingAddDirButtonActionListener(this.getFrame()));
        this.setFrame().setInputArea().remove(this.getFrame().getInputArea().getComponentCount()-1);
        this.setFrame().setInputArea().add(MassMatcher.generateInputBox(this.getFrame()));
        this.setFrame().setInputArea().add(theAddDBDirButton);
        this.setFrame().revalidate();
    }

}
