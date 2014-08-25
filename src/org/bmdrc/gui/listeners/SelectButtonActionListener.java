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
import javax.swing.JFileChooser;
import javax.swing.JTextField;
import org.bmdrc.gui.MainFrame;

/**
 *
 * @author SungBo Hwang <tyamazaki@naver.com>
 */
public class SelectButtonActionListener implements ActionListener {

    private MainFrame itsFrame;
    private JTextField itsTextField;
    
    public SelectButtonActionListener(JTextField itsTextField) {
        this.itsTextField = itsTextField;
    }

    public JTextField getTextField() {
        return itsTextField;
    }

    public void setTextField(JTextField theTextField) {
        this.itsTextField = theTextField;
    }
    
    public JTextField setTextField() {
        return itsTextField;
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
        JFileChooser theFileChooser = new JFileChooser();
        theFileChooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
        
        if(theFileChooser.showOpenDialog(theFileChooser) == JFileChooser.APPROVE_OPTION) {
            this.setTextField().setText(theFileChooser.getSelectedFile().toString());
        }
    }

}
