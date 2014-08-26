/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this file,
 * You can obtain one at http://mozilla.org/MPL/2.0/.
 *
 * Copyright (C) 2014, SungBo Hwang <tyamazaki@naver.com>.
 */
package org.bmdrc.chemistry.atom;

import java.util.ArrayList;
import java.util.List;
import org.bmdrc.chemistry.interfaces.IAtomRadius;

/**
 *
 * @author SungBo Hwang <tyamazaki@naver.com>
 */
public class AtomRadius implements IAtomRadius {

    @Override
    public List<Double> getAtomRadiusList() {
        List<Double> theAtomRadiusList = new ArrayList<>();

        theAtomRadiusList.add(Double.NaN);
        theAtomRadiusList.add(this.H_RADIUS);
        theAtomRadiusList.add(this.He_RADIUS);
        theAtomRadiusList.add(this.Li_RADIUS);
        theAtomRadiusList.add(this.Be_RADIUS);
        theAtomRadiusList.add(this.B_RADIUS);
        theAtomRadiusList.add(this.C_RADIUS);
        theAtomRadiusList.add(this.N_RAIDUS);
        theAtomRadiusList.add(this.O_RADIUS);
        theAtomRadiusList.add(this.F_RADIUS);
        theAtomRadiusList.add(this.Ne_RADIUS);
        theAtomRadiusList.add(this.Na_RADIUS);
        theAtomRadiusList.add(this.Mg_RADIUS);
        theAtomRadiusList.add(this.Al_RADIUS);
        theAtomRadiusList.add(this.Si_RADIUS);
        theAtomRadiusList.add(this.P_RADIUS);
        theAtomRadiusList.add(this.S_RADIUS);
        theAtomRadiusList.add(this.Cl_RADIUS);
        theAtomRadiusList.add(this.Ar_RADIUS);
        theAtomRadiusList.add(this.K_RADIUS);
        theAtomRadiusList.add(this.Ca_RADIUS);
        theAtomRadiusList.add(this.Sc_RADIUS);
        theAtomRadiusList.add(this.Ti_RADIUS);
        theAtomRadiusList.add(this.V_RAIDUS);
        theAtomRadiusList.add(this.Cr_RADIUS);
        theAtomRadiusList.add(this.Mn_RADIUS);
        theAtomRadiusList.add(this.Fe_RADIUS);
        theAtomRadiusList.add(this.Co_RADIUS);
        theAtomRadiusList.add(this.Ni_RADIUS);
        theAtomRadiusList.add(this.Cu_RADIUS);
        theAtomRadiusList.add(this.Zn_RADIUS);
        theAtomRadiusList.add(this.Ga_RADIUS);
        theAtomRadiusList.add(this.Ge_RADIUS);
        theAtomRadiusList.add(this.As_RADIUS);
        theAtomRadiusList.add(this.Se_RADIUS);
        theAtomRadiusList.add(this.Br_RADIUS);
        theAtomRadiusList.add(this.Kr_RADIUS);
        theAtomRadiusList.add(this.Rb_RADIUS);
        theAtomRadiusList.add(this.Sr_RADIUS);
        theAtomRadiusList.add(this.Y_RADIUS);
        theAtomRadiusList.add(this.Zr_RADIUS);
        theAtomRadiusList.add(this.Nb_RADIUS);
        theAtomRadiusList.add(this.Mo_RADIUS);
        theAtomRadiusList.add(this.Tc_RADIUS);
        theAtomRadiusList.add(this.Ru_RADIUS);
        theAtomRadiusList.add(this.Rh_RADIUS);
        theAtomRadiusList.add(this.Pd_RADIUS);
        theAtomRadiusList.add(this.Ag_RADIUS);
        theAtomRadiusList.add(this.Cd_RADIUS);
        theAtomRadiusList.add(this.In_RADIUS);
        theAtomRadiusList.add(this.Sn_RADIUS);
        theAtomRadiusList.add(this.Sb_RADIUS);
        theAtomRadiusList.add(this.Te_RADIUS);
        theAtomRadiusList.add(this.I_RADIUS);
        theAtomRadiusList.add(this.Xe_RADIUS);
        theAtomRadiusList.add(this.Cs_RADIUS);
        theAtomRadiusList.add(this.Ba_RADIUS);

        return theAtomRadiusList;
    }
}
