/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this file,
 * You can obtain one at http://mozilla.org/MPL/2.0/.
 *
 * Copyright (C) 2014, SungBo Hwang <tyamazaki@naver.com>.
 */
package org.bmdrc.gui.interfaces;

/**
 *
 * @author SungBo Hwang <tyamazaki@naver.com>
 */
public interface IEtcCalculationMethod {

    String INPUT_THREAD_INFORMATION = "Input Thread Information(Gaussian)";
    String MERGE_MOLECULE_FILE = "Merge Molecule File";
    String SPLIT_MOLECULE_FILE = "Split Molecule File";
    String CALCULATE_MPEOE_AND_CDEAP = "Calculate MPEOE and CDEAP";
    String CALCULATE_SOLVATION_FREE_ENERGY = "Calculate Solvation Free Energy using GSFED";
}
