/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this file,
 * You can obtain one at http://mozilla.org/MPL/2.0/.
 *
 * Copyright (C) 2014, SungBo Hwang <tyamazaki@naver.com>.
 */

package org.bmdrc.nmr.util;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import org.bmdrc.nmr.interfaces.IFunctionalGroupConstantVariable;

/**
 *
 * @author SungBo Hwang <tyamazaki@naver.com>
 */
public class FunctionalGroupConstantVariable implements IFunctionalGroupConstantVariable, Serializable {
    private static final long serialVersionUID = 2411335635922521941L;

    public List<String> getFunctionalGroupList() {
        List<String> theFunctionalGroupList = new ArrayList<>();

        theFunctionalGroupList.add(this.FUNCTIONAL_GROUP_ALCHOL);
        theFunctionalGroupList.add(this.FUNCTIONAL_GROUP_ALDEHYDE);
        theFunctionalGroupList.add(this.FUNCTIONAL_GROUP_ALKENE);
        theFunctionalGroupList.add(this.FUNCTIONAL_GROUP_ALKYNYL);
        theFunctionalGroupList.add(this.FUNCTIONAL_GROUP_ALLYLIC);
        theFunctionalGroupList.add(this.FUNCTIONAL_GROUP_AMINECCH2N);
        theFunctionalGroupList.add(this.FUNCTIONAL_GROUP_AROMATICALKYL);
        theFunctionalGroupList.add(this.FUNCTIONAL_GROUP_BENZYL);
        theFunctionalGroupList.add(this.FUNCTIONAL_GROUP_CARBON_BOUND_CARBOXYLIC);
        theFunctionalGroupList.add(this.FUNCTIONAL_GROUP_CARBON_BOUND_OH);
        theFunctionalGroupList.add(this.FUNCTIONAL_GROUP_CARBOXYLIC_ACID);
        theFunctionalGroupList.add(this.FUNCTIONAL_GROUP_CHARGED_ATOM);
        theFunctionalGroupList.add(this.FUNCTIONAL_GROUP_ESTER);
        theFunctionalGroupList.add(this.FUNCTIONAL_GROUP_ESTERR2CHCOOR);
        theFunctionalGroupList.add(this.FUNCTIONAL_GROUP_ESTERROOCH2R);
        theFunctionalGroupList.add(this.FUNCTIONAL_GROUP_ETHER);
        theFunctionalGroupList.add(this.FUNCTIONAL_GROUP_H3C_N);
        theFunctionalGroupList.add(this.FUNCTIONAL_GROUP_H3C_P);
        theFunctionalGroupList.add(this.FUNCTIONAL_GROUP_H3C_S);
        theFunctionalGroupList.add(this.FUNCTIONAL_GROUP_HAIIDE_NITROGEN);
        theFunctionalGroupList.add(this.FUNCTIONAL_GROUP_HALIDE);
        theFunctionalGroupList.add(this.FUNCTIONAL_GROUP_HALIDE_START);
        theFunctionalGroupList.add(this.FUNCTIONAL_GROUP_HO_N);
        theFunctionalGroupList.add(this.FUNCTIONAL_GROUP_HO_P);
        theFunctionalGroupList.add(this.FUNCTIONAL_GROUP_HO_S);
        theFunctionalGroupList.add(this.FUNCTIONAL_GROUP_HYDROGEN);
        theFunctionalGroupList.add(this.FUNCTIONAL_GROUP_KETONE);
        theFunctionalGroupList.add(this.FUNCTIONAL_GROUP_METHOXY);
        theFunctionalGroupList.add(this.FUNCTIONAL_GROUP_N1_SPECIAL_CASE);
        theFunctionalGroupList.add(this.FUNCTIONAL_GROUP_N2_SPECIAL_CASE);
        theFunctionalGroupList.add(this.FUNCTIONAL_GROUP_N3_SPECIAL_CASE);
        theFunctionalGroupList.add(this.FUNCTIONAL_GROUP_NH2_S);
        theFunctionalGroupList.add(this.FUNCTIONAL_GROUP_N_NH);
        theFunctionalGroupList.add(this.FUNCTIONAL_GROUP_N_NH2);
        theFunctionalGroupList.add(this.FUNCTIONAL_GROUP_O_NH2);
        theFunctionalGroupList.add(this.FUNCTIONAL_GROUP_O_NH_O);
        theFunctionalGroupList.add(this.FUNCTIONAL_GROUP_PHENYL);
        theFunctionalGroupList.add(this.FUNCTIONAL_GROUP_PRIMARYALKYL);
        theFunctionalGroupList.add(this.FUNCTIONAL_GROUP_PRIMARYAMINE);
        theFunctionalGroupList.add(this.FUNCTIONAL_GROUP_PRIMARYAMINE_NH2N);
        theFunctionalGroupList.add(this.FUNCTIONAL_GROUP_PRIMARY_AMIDE_N);
        theFunctionalGroupList.add(this.FUNCTIONAL_GROUP_PRIMARY_AMINE_BOUND_AROMATIC_ATOM);
        theFunctionalGroupList.add(this.FUNCTIONAL_GROUP_P_NH2);
        theFunctionalGroupList.add(this.FUNCTIONAL_GROUP_QUINONE);
        theFunctionalGroupList.add(this.FUNCTIONAL_GROUP_SECONDARYAMINE);
        theFunctionalGroupList.add(this.FUNCTIONAL_GROUP_SECONDARY_ALKYL);
        theFunctionalGroupList.add(this.FUNCTIONAL_GROUP_SECONDARY_AMIDE_N);
        theFunctionalGroupList.add(this.FUNCTIONAL_GROUP_SECONDARY_SULFOXAMIDE);
        theFunctionalGroupList.add(this.FUNCTIONAL_GROUP_TERTIARY_ALKYL);
        theFunctionalGroupList.add(this.FUNCTIONAL_GROUP_THIOL);
        theFunctionalGroupList.add(this.FUNCTIONAL_GROUP_VINYLIC);
        
        return theFunctionalGroupList;
    }
}
