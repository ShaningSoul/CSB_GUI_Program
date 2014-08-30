/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this file,
 * You can obtain one at http://mozilla.org/MPL/2.0/.
 *
 * Copyright (C) 2014, SungBo Hwang <tyamazaki@naver.com>.
 */

package org.bmdrc.mass.gui;

import java.io.Serializable;
import org.bmdrc.gui.MainFrame;
import org.bmdrc.mass.gui.interfaces.IMassCalculationMethod;
import org.bmdrc.mass.gui.interfaces.IMassInCalculateButtonActionListener;
import org.bmdrc.mass.tool.Compare20And500Scan;

/**
 *
 * @author SungBo Hwang <tyamazaki@naver.com>
 */
public class MassInCalculateButtonActionListener implements IMassCalculationMethod, IMassInCalculateButtonActionListener, Serializable{
    private static final long serialVersionUID = -5890035005412342367L;

    public static void calculate(MainFrame theFrame) {
        switch(theFrame.getSelectedCalculationMethod()) {
            case MassInCalculateButtonActionListener.COMPARE_20_AND_500_SCAN:
                Compare20And500Scan theCalculator = new Compare20And500Scan(theFrame);
                theCalculator.compare20And500Scan();
                break;
        }
    }
}
