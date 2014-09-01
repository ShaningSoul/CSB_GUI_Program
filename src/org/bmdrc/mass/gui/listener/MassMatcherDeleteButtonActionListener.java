/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this file,
 * You can obtain one at http://mozilla.org/MPL/2.0/.
 *
 * Copyright (C) 2014, SungBo Hwang <tyamazaki@naver.com>.
 */
package org.bmdrc.mass.gui.listener;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.Serializable;
import org.bmdrc.gui.MainFrame;

/**
 *
 * @author SungBo Hwang <tyamazaki@naver.com>
 */
public class MassMatcherDeleteButtonActionListener implements ActionListener, Serializable {
    private static final long serialVersionUID = -3932951115202101347L;

    private MainFrame itsFrame;
    private Integer itsIndex;

    public MassMatcherDeleteButtonActionListener(MainFrame theFrame, int theIndex) {
        this.itsFrame = theFrame;
        this.itsIndex = theIndex;
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

    public Integer getIndex() {
        return itsIndex;
    }

    public void setIndex(Integer itsIndex) {
        this.itsIndex = itsIndex;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String theName = this.getIndex().toString();

        for (int ci = 0, cEnd = this.setFrame().setInputArea().getComponentCount(); ci < cEnd; ci++) {
                if (this.setFrame().setInputArea().getComponent(ci).getName().equals(theName)) {
                    this.setFrame().setInputArea().remove(ci);
                    this.setFrame().setFilePathTextFieldList().remove(ci-1);
                    break;
                }
        }

        this.setFrame().revalidate();
    }
}
