/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this file,
 * You can obtain one at http://mozilla.org/MPL/2.0/.
 *
 * Copyright (C) 2014, SungBo Hwang <tyamazaki@naver.com>.
 */
package org.bmdrc.nmr.gui;

import org.bmdrc.chemistry.tool.HoseCodeGenerator;
import org.bmdrc.gui.MainFrame;
import org.bmdrc.gui.interfaces.INmrCalculationMethod;
import org.bmdrc.nmr.tool.DBSearcher;

/**
 *
 * @author SungBo Hwang <tyamazaki@naver.com>
 */
public class NmrInComboBoxActionListener implements INmrCalculationMethod{

    public static void generateFilePathBox(MainFrame theFrame) {
        if(theFrame.getSelectedCalculationMethod().equals(NmrInComboBoxActionListener.DB_SEARCH_USING_2D_NMR)) {
            DBSearcher.generateDBSearcherFilePathBox(theFrame);
        } else if(theFrame.getSelectedCalculationMethod().equals(NmrInComboBoxActionListener.HOSE_CODE_GENERATION)) {
            HoseCodeGenerator.generateFilePathBox(theFrame);
        } else if(theFrame.getSelectedCalculationMethod().equals(NmrInComboBoxActionListener.HOSE_CODE_GENERATION_IN_MULTIPLE_FILE)) {
            HoseCodeGenerator.generateBatchFilePathBox(theFrame);
        }
    }
}
