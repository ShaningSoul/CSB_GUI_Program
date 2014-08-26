/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.bmdrc.nmr.interfaces;

import java.util.List;

/**
 *
 * @author labwindows
 */
public interface IFunctionalGroupConstantVariable {
    List<String> getFunctionalGroupList();
    public static final String FUNCTIONAL_GROUP_HYDROGEN = "Hydrogen";
    public static final String FUNCTIONAL_GROUP_CHARGED_ATOM = "ChargedAtom";
    public static final String FUNCTIONAL_GROUP_CARBOXYLIC_ACID = "Carboxylic";
    public static final String FUNCTIONAL_GROUP_ALCHOL = "Alchol";
    public static final String FUNCTIONAL_GROUP_HALIDE_START = "HalidStart";
    public static final String FUNCTIONAL_GROUP_PHENYL = "Phenyl";
    public static final String FUNCTIONAL_GROUP_HO_N = "HO-N";
    public static final String FUNCTIONAL_GROUP_HO_P = "HO-P";
    public static final String FUNCTIONAL_GROUP_HO_S = "HO-S";
    public static final String FUNCTIONAL_GROUP_NOHYDROGENOXYGEN = "NoHydrogenOxygen";
    public static final String FUNCTIONAL_GROUP_HAIIDE_NITROGEN = "HalidNitrogen";
    public static final String FUNCTIONAL_GROUP_PRIMARYAMINE_NH2N = "PrimaryAmineNH2N";
    //public static final String FUNCTIONAL_GROUP_ANILINE = "Aniline";
    public static final String FUNCTIONAL_GROUP_PRIMARY_AMINE_BOUND_AROMATIC_ATOM = "Primary_Amine_Bound_Aromatic_Atom";
    public static final String FUNCTIONAL_GROUP_PRIMARYAMINE = "PrimaryAmine";
    public static final String FUNCTIONAL_GROUP_N_NH2 = "N-NH2";
    public static final String FUNCTIONAL_GROUP_N_NH = "N-NH";
    public static final String FUNCTIONAL_GROUP_O_NH2 = "O-NH2";
    public static final String FUNCTIONAL_GROUP_P_NH2 = "P-NH2";
    public static final String FUNCTIONAL_GROUP_NH2_S = "NH2-S";
    public static final String FUNCTIONAL_GROUP_NOHYDROGENNITROGEN = "NoHydrogenNitrogen";
    public static final String FUNCTIONAL_GROUP_SECONDARY_AMIDE_N = "Secondary_Amide_N";
    public static final String FUNCTIONAL_GROUP_PRIMARY_AMIDE_N = "Primary_Amide_N";
    public static final String FUNCTIONAL_GROUP_SECONDARYAMINE = "SecondaryAmine";
    public static final String FUNCTIONAL_GROUP_IMINE = "Imine";
    public static final String FUNCTIONAL_GROUP_N1_SPECIAL_CASE = "N-1_Special_Case";
    public static final String FUNCTIONAL_GROUP_N2_SPECIAL_CASE = "N-2_Special_Case";
    public static final String FUNCTIONAL_GROUP_N3_SPECIAL_CASE = "N-3_Special_Case";
    public static final String FUNCTIONAL_GROUP_O_NH_O = "O-NH-O";
    public static final String FUNCTIONAL_GROUP_SECONDARY_SULFOXAMIDE = "NH-C(=S)";
    public static final String FUNCTIONAL_GROUP_NOHYDROGENCARBON = "NoHydrogenCarbon";
    public static final String FUNCTIONAL_GROUP_HALIDE = "Halide";
    public static final String FUNCTIONAL_GROUP_AROMATICALKYL = "AromaticAlkyl";
    public static final String FUNCTIONAL_GROUP_ALKYNYL = "Alkynyl";
    public static final String FUNCTIONAL_GROUP_BENZYL = "Benzyl";
    public static final String FUNCTIONAL_GROUP_QUINONE = "Quinone";
    public static final String FUNCTIONAL_GROUP_ALLYLIC = "Allylic";
    public static final String FUNCTIONAL_GROUP_ESTER = "Ester";
    public static final String FUNCTIONAL_GROUP_METHOXY = "Methoxy";
    public static final String FUNCTIONAL_GROUP_H3C_N = "H3C-N";
    public static final String FUNCTIONAL_GROUP_H3C_P = "H3C-P";
    public static final String FUNCTIONAL_GROUP_H3C_S = "H3C-S";
    public static final String FUNCTIONAL_GROUP_PRIMARYALKYL = "PrimaryAlkyl";
    public static final String FUNCTIONAL_GROUP_KETONE = "Ketone";
    public static final String FUNCTIONAL_GROUP_SECONDARY_ALKYL = "SecondaryAlkyl";
    public static final String FUNCTIONAL_GROUP_ALKENE = "Alkene";
    public static final String FUNCTIONAL_GROUP_ETHER = "Ether";
    public static final String FUNCTIONAL_GROUP_ESTERROOCH2R = "EsterROOCH2R";
    public static final String FUNCTIONAL_GROUP_CARBON_BOUND_OH = "CarbonBoundAlcohol";
    public static final String FUNCTIONAL_GROUP_AMINECCH2N = "AmineCCH2N";
    public static final String FUNCTIONAL_GROUP_ALDEHYDE = "Aldehyde";
    public static final String FUNCTIONAL_GROUP_VINYLIC = "Vinylic";
    public static final String FUNCTIONAL_GROUP_CARBON_BOUND_CARBOXYLIC = "Carbon_bound_carboxylic";
    public static final String FUNCTIONAL_GROUP_ESTERR2CHCOOR = "EsterR2CHCOOR";
    public static final String FUNCTIONAL_GROUP_TERTIARY_ALKYL = "TertiaryAlkyl";
    public static final String FUNCTIONAL_GROUP_THIOL = "Thiol";
    public static final String FUNCTIONAL_GROUP_NOHYDROGENSULFONE = "NoHydrogenSulfone";
    public static final String FUNCTIONAL_GROUP_PH = "HP-";
    public static final String FUNCTIONAL_GROUP_NOHYDROGENPHOSPORE = "NoHydrogenPhospore";
    public static final String FIRST_ERROR_CHECK_POINT = "CP0";
    public static final String SECOND_ERROR_CHECK_POINT = "CP1";
    public static final String THIRD_ERROR_CHECK_POINT = "CP2";
    public static final String FOURTH_ERROR_CHECK_POINT = "CP3";
    public static final String FIVETH_ERROR_CHECK_POINT = "CP4";
    public static final String SIXTH_ERROR_CHECK_POINT = "CP5";
}
