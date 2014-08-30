/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this file,
 * You can obtain one at http://mozilla.org/MPL/2.0/.
 *
 * Copyright (C) 2014, SungBo Hwang <tyamazaki@naver.com>.
 */
package org.bmdrc.mass.gui;

import java.io.Serializable;
import modify_file.MassMatcher;
import org.bmdrc.gui.MainFrame;
import org.bmdrc.mass.gui.interfaces.IMassCalculationMethod;
import org.bmdrc.mass.tool.Compare20And500Scan;

/**
 *
 * @author SungBo Hwang <tyamazaki@naver.com>
 */
public class MassInComboBoxActionListener implements IMassCalculationMethod, Serializable {
    public static void calculate(MainFrame theFrame) {
        switch(theFrame.getSelectedCalculationMethod()) {
            case MassInComboBoxActionListener.COMPARE_20_AND_500_SCAN:
                Compare20And500Scan.generateSplitedMoleculeFileFilePathBox(theFrame);
                break;
            case MassInComboBoxActionListener.MASS_MATCHING:
                MassMatcher.generatematchBetweenMassAndMoleculeInDBFilePathBox(theFrame);
                break;
        }
    }
}
