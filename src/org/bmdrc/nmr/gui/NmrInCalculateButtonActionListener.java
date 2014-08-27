/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this file,
 * You can obtain one at http://mozilla.org/MPL/2.0/.
 *
 * Copyright (C) 2014, SungBo Hwang <tyamazaki@naver.com>.
 */
package org.bmdrc.nmr.gui;

import java.io.File;
import java.io.Serializable;
import org.bmdrc.chemistry.tool.HoseCodeGenerator;
import org.bmdrc.gui.MainFrame;
import org.bmdrc.gui.interfaces.INmrCalculationMethod;
import org.bmdrc.nmr.gui.interfaces.INmrInCalculateButtonActionListener;
import org.bmdrc.nmr.tool.DBSearcher;

/**
 *
 * @author SungBo Hwang <tyamazaki@naver.com>
 */
public class NmrInCalculateButtonActionListener implements INmrCalculationMethod, INmrInCalculateButtonActionListener, Serializable {
    private static final long serialVersionUID = -8249962982351770507L;

    private MainFrame itsFrame;
    
    public NmrInCalculateButtonActionListener(MainFrame theFrame) {
        this.itsFrame = theFrame;
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

    public static void calculate(MainFrame theFrame) {
        switch (theFrame.getSelectedCalculationMethod()) {
            case NmrInCalculateButtonActionListener.DB_SEARCH_USING_2D_NMR:
                DBSearcher theSearcher = new DBSearcher(new File(theFrame.getFilePathTextFieldList().get(NmrInCalculateButtonActionListener.INPUT_MOLECULE_FILE_PATH_INDEX_IN_DB_SEARCHER).getText()),
                        Integer.parseInt(theFrame.getFilePathTextFieldList().get(NmrInCalculateButtonActionListener.MAXIMUM_QUTENARY_TOPOLOGICAL_DISTANCE_INDEX_IN_DB_SEARCHER).getText()), 
                        Integer.parseInt(theFrame.getFilePathTextFieldList().get(NmrInCalculateButtonActionListener.MAXIMUM_OTHER_TOPOLOGICAL_DISTANCE_INDEX_IN_DB_SEARCHER).getText()));
                theSearcher.searchMatchedMoleculeIn2DNMR();
                theSearcher.writeResultMoleculeSet(new File(theFrame.getFilePathTextFieldList().get(NmrInCalculateButtonActionListener.RESULT_FILE_PATH_INDEX_IN_DB_SEARCHER).getText()));
                break;
            case NmrInCalculateButtonActionListener.HOSE_CODE_GENERATION:
                HoseCodeGenerator.generateHoseCode(theFrame.getFilePathTextFieldList().get(NmrInCalculateButtonActionListener.INPUT_MOLECULE_FILE_PATH_INDEX_IN_HOSE_CODE_GENERATOR).getText(), 
                        theFrame.getFilePathTextFieldList().get(NmrInCalculateButtonActionListener.RESULT_MOLECULE_FILE_PATH_INDEX_IN_HOSE_CODE_GENERATOR).getText(),
                        Integer.parseInt(theFrame.getFilePathTextFieldList().get(NmrInCalculateButtonActionListener.LEVEL_OF_HOSE_CODE_INDEX_IN_HOSE_CODE_GENERATOR).getText()));
                break;
        }
    }
}
