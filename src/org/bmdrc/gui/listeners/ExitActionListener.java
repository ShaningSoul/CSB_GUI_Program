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
import java.io.Serializable;
import org.bmdrc.gui.MainFrame;

/**
 *
 * @author SungBo Hwang <tyamazaki@naver.com>
 */
public class ExitActionListener implements ActionListener, Serializable {
    private static final long serialVersionUID = -8254828145739606953L;

    private MainFrame itsFrame;

    public ExitActionListener(MainFrame theFrame) {
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
    
    @Override
    public void actionPerformed(ActionEvent ae) {
        this.setFrame().dispose();
    }

}
