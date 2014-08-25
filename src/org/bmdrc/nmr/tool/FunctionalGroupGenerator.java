package org.bmdrc.nmr.tool;

import java.util.*;
import java.util.regex.PatternSyntaxException;
import org.bmdrc.nmr.interfaces.IAtomicNumber;
import org.bmdrc.nmr.interfaces.IFunctionalGroupConstantVariable;
import org.bmdrc.nmr.interfaces.IHoseCodeSymbol;
import org.bmdrc.util.Module;
import org.bmdrc.interfaces.IStringConstant;
import org.openscience.cdk.Molecule;
import org.openscience.cdk.MoleculeSet;
import org.openscience.cdk.interfaces.IMolecule;
import org.openscience.cdk.interfaces.IMoleculeSet;

/**
 *
 * @author SungBo Hwang, CSB
 * @version 1.0
 */
public class FunctionalGroupGenerator implements IFunctionalGroupConstantVariable, IHoseCodeSymbol, IStringConstant, IAtomicNumber{

    private IMoleculeSet itsMoleculeSet;
    private List<Integer> itsUsualAtomicNumberList;
    //constant string variable
    private final String KEY_VALUE_OF_FUNCTIONAL_GROUP = "Functional_Group";
    private final String KEY_VALUE_OF_HOSE_CODE = "HOSE_CODE_4";
    private final String CRITERION_HOSE_CODE_IN_HYDROGEN = "H-";
    private final String CRITERION_HOSE_CODE_IN_CARBOXYLIC_ACID = "=OC";
    private final String CRITERION_HOSE_CODE_DIVIDED_BETWEEN_ZERO_AND_FIRST = ";";
    private final String CRITERION_HOSE_CODE_DIVIDED_BETWEEN_FIRST_AND_SECOND = "\\(";
    private final String CRITERION_HOSE_CODE_DIVIDED_BETWEEN_SECOND_AND_THIRD = "/";
    private final String CHECK_NUMBER_OF_HOSE_CODE_REGEX = "[A-Z]\\-[0-9]\\;";
    
    //constant int variable
    private final int ZERO_CELL_INDEX = 0;
    private final int FIRST_CELL_INDEX = 1;
    private final int SECOND_CELL_INDEX = 2;
    private final int THIRD_CELL_INDEX = 3;
    private final int NUMBER_OF_BOUNDED_ATOM_IN_ZERO_CELL = 3;
    private final int FIRST_POSITION = 0;
    private final int SECOND_POSITION = 1;
    
    public FunctionalGroupGenerator() {
        this.setMoleculeSet(new MoleculeSet());
        this.__generateUsualAtomicNumberList();
    }

    public FunctionalGroupGenerator(IMoleculeSet theMoleculeSet) {
        this.itsMoleculeSet = theMoleculeSet;
        this.__generateUsualAtomicNumberList();
    }

    public IMoleculeSet getMoleculeSet() {
        return itsMoleculeSet;
    }

    public void setMoleculeSet(IMoleculeSet theMoleculeSet) {
        this.itsMoleculeSet = theMoleculeSet;
    }

    public IMoleculeSet setMoleculeSet() {
        return itsMoleculeSet;
    }

    public List<Integer> getUsualAtomicNumberList() {
        return itsUsualAtomicNumberList;
    }

    public void setUsualAtomicNumberList(List<Integer> theUsualAtomicNumberList) {
        this.itsUsualAtomicNumberList = theUsualAtomicNumberList;
    }

    public List<Integer> setUsualAtomicNumberList() {
        return itsUsualAtomicNumberList;
    }

    private void __generateUsualAtomicNumberList() {
        this.setUsualAtomicNumberList(new ArrayList<Integer>());
        
        this.setUsualAtomicNumberList().add(this.C_ATOMIC_NUMBER);
        this.setUsualAtomicNumberList().add(this.H_ATOMIC_NUMBER);
        this.setUsualAtomicNumberList().add(this.O_ATOMIC_NUMBER);
        this.setUsualAtomicNumberList().add(this.N_ATOMIC_NUMBER);
        this.setUsualAtomicNumberList().add(this.F_ATOMIC_NUMBER);
        this.setUsualAtomicNumberList().add(this.Br_ATOMIC_NUMBER);
        this.setUsualAtomicNumberList().add(this.Cl_ATOMIC_NUMBER);
        this.setUsualAtomicNumberList().add(this.I_ATOMIC_NUMBER);
        this.setUsualAtomicNumberList().add(this.S_ATOMIC_NUMBER);
        this.setUsualAtomicNumberList().add(this.P_ATOMIC_NUMBER);
    }

    private boolean __containUnusualAtom(IMolecule theMolecule) {
        for (int ai = 0, aEnd = theMolecule.getAtomCount(); ai < aEnd; ai++) {
            if (!this.getUsualAtomicNumberList().contains(theMolecule.getAtom(ai).getAtomicNumber())) {
                return true;
            }
        }

        return false;
    }

    public void inputFunctionalGroupList() {
        for (int mi = 0, mEnd = this.getMoleculeSet().getMoleculeCount(); mi < mEnd; mi++) {
            if (!this.__containUnusualAtom(this.getMoleculeSet().getMolecule(mi))) {
                this.__inputFunctionalGroupList(this.getMoleculeSet().getMolecule(mi));
            }
        }
    }

    private void __inputFunctionalGroupList(IMolecule theMolecule) {
        List<String> theHOSECODEList = this.__divideHOSECODE(theMolecule);
        StringBuilder theResultFunctionalGroup = new StringBuilder();

        for (int hi = 0, hEnd = theHOSECODEList.size(); hi < hEnd; hi++) {
            if (!theHOSECODEList.get(hi).isEmpty()) {
                theResultFunctionalGroup.append(this.__getFunctionalGroup(theHOSECODEList.get(hi)));

                if (hi != theHOSECODEList.size() - 1) {
                    theResultFunctionalGroup.append(this.TAB_STRING);
                }
            }
        }

        theMolecule.setProperty(this.KEY_VALUE_OF_FUNCTIONAL_GROUP, theResultFunctionalGroup.toString());
    }

    private String __getFunctionalGroup(String theHoseCode) {
        String theFunctionalGroup = new String();

        if (!theHoseCode.contains(this.CRITERION_HOSE_CODE_IN_HYDROGEN)) {
            List<String> theCell = this.__divideCell(theHoseCode);

            if (theCell.get(this.FIRST_CELL_INDEX).contains(this.POSITIVE_CHARGE)) {
                theFunctionalGroup = this.FUNCTIONAL_GROUP_CHARGED_ATOM;
            } else {
                theFunctionalGroup = this.__setFunctionalGroup(theCell);
            }
        } else {
            theFunctionalGroup = this.FUNCTIONAL_GROUP_HYDROGEN;
        }

        return theFunctionalGroup;
    }

    private List<String> __divideHOSECODE(IMolecule theMolecule) {
        String[] theHoseCodeArray = theMolecule.getProperty(this.KEY_VALUE_OF_HOSE_CODE).toString().split("\t");
        List<String> theHoseCodeList = new ArrayList<>();
        
        for(String theHoseCode : theHoseCodeArray) {
            if(theHoseCode.split(this.CHECK_NUMBER_OF_HOSE_CODE_REGEX).length > 2) {
                String[] theSplitedString = theHoseCode.split(this.CLOSE_PARENTHESES_REGEX);
                
                for(String theString : theSplitedString) {
                    String theNewHoseCode = theString.trim() + ")";
                    
                    theHoseCodeList.add(theNewHoseCode);
                }
            } else {
                theHoseCodeList.add(theHoseCode);
            }
        }
        return theHoseCodeList;
    }

    private List<String> __divideCell(String theHOSECODE) {
        ArrayList<String> theCell = new ArrayList<>();
        ArrayList<String> theSplitedStringList = new ArrayList<>();
        String[] theSplitedString = theHOSECODE.split(this.CRITERION_HOSE_CODE_DIVIDED_BETWEEN_ZERO_AND_FIRST);

        try {
            theCell.add(theSplitedString[0]);
            theSplitedString = theSplitedString[1].split(this.CRITERION_HOSE_CODE_DIVIDED_BETWEEN_FIRST_AND_SECOND);
            theCell.add(theSplitedString[0]);
            Collections.addAll(theSplitedStringList, theSplitedString[1].split(this.CRITERION_HOSE_CODE_DIVIDED_BETWEEN_SECOND_AND_THIRD));
            theCell.addAll(theSplitedStringList);
        } catch (IndexOutOfBoundsException e) {
            e.printStackTrace();
            System.err.println(theSplitedString[0]);
            System.err.println(theHOSECODE);
        } catch (PatternSyntaxException e) {
            e.printStackTrace();
            System.err.println(theSplitedString[1]);
            System.err.println(theHOSECODE);
        }
        return theCell;
    }

    private void __modifySecondCellList(HoseCodeManipulator theHoseCodeManipulator) throws NumberFormatException {
        List<String> theCheckSecondCell = new ArrayList<>();

        for (int si = 0, sEnd = theHoseCodeManipulator.getSecondCellList().size() - 1; si < sEnd; si++) {
            if (theHoseCodeManipulator.getSecondCellList().get(si).equals(this.AROMATIC_CARBON) && theHoseCodeManipulator.getSecondCellList().get(si + 1).equals(this.AROMATIC_CARBON)) {
                theCheckSecondCell.add(this.AROMATIC_CARBON + this.AROMATIC_CARBON);
                si++;
            } else {
                theCheckSecondCell.add(theHoseCodeManipulator.getSecondCellList().get(si));
            }
        }

        theCheckSecondCell.add(theHoseCodeManipulator.getSecondCellList().get(theHoseCodeManipulator.getSecondCellList().size() - 1));
        theHoseCodeManipulator.setSecondCellList(theCheckSecondCell);
    }

    private boolean __isHalideSymbol(String theHoseCodeInCell) {
        if (theHoseCodeInCell.contains(this.BROMIDE_SYMBOL) || theHoseCodeInCell.contains(this.CHLORIDE_SYMBOL) || theHoseCodeInCell.contains(this.IODIDE_SYMBOL)
                || theHoseCodeInCell.contains(this.FLUORINE_SYMBOL)) {
            return true;
        }

        return false;
    }

    private boolean __isBoundOnlyOneCarbonNotAromatic(HoseCodeManipulator theHoseCodeManipulator) {
        if (!theHoseCodeManipulator.getHoseCodeListDiviedByLevel().get(this.FIRST_CELL_INDEX).contains(this.CARBON_SYMBOL)) {
            return false;
        } else if (theHoseCodeManipulator.getHoseCodeListDiviedByLevel().get(this.FIRST_CELL_INDEX).contains(this.AROMATIC_SYMBOL)) {
            return false;
        } else if (Module.count(theHoseCodeManipulator.getFirstCellList(), this.CARBON_SYMBOL) == 1) {
            return false;
        } else if (theHoseCodeManipulator.getHoseCodeListDiviedByLevel().get(this.FIRST_CELL_INDEX).contains(this.DOUBLE_BOND_SYMBOL)) {
            return false;
        }

        return true;
    }

    private String __getFunctionalGroupInOxygenCase(HoseCodeManipulator theHoseCodeManipulator) {
        String theFunctionalGroup = new String();

        if (theHoseCodeManipulator.getHoseCodeListDiviedByLevel().get(this.SECOND_CELL_INDEX).contains(this.AROMATIC_CARBON + this.AROMATIC_CARBON)) {
            return this.FUNCTIONAL_GROUP_PHENYL;
        } else if (this.__isBoundOnlyOneCarbonNotAromatic(theHoseCodeManipulator)) {
            if (theHoseCodeManipulator.getSecondCellList().contains(this.CRITERION_HOSE_CODE_IN_CARBOXYLIC_ACID)) {
                return this.FUNCTIONAL_GROUP_CARBOXYLIC_ACID;
            } else {
                return this.FUNCTIONAL_GROUP_ALCHOL + this.FIRST_ERROR_CHECK_POINT;
            }
        } else if (theHoseCodeManipulator.getHoseCodeListDiviedByLevel().get(this.FIRST_CELL_INDEX).equals(this.NITROGEN_SYMBOL)) {
            return this.FUNCTIONAL_GROUP_HO_N;
        } else if (theHoseCodeManipulator.getHoseCodeListDiviedByLevel().get(this.FIRST_CELL_INDEX).equals(this.PHOSPHATE_SYMBOL)) {
            return this.FUNCTIONAL_GROUP_HO_P;
        } else if (theHoseCodeManipulator.getHoseCodeListDiviedByLevel().get(this.FIRST_CELL_INDEX).equals(this.SULFUR_SYMBOL)) {
            return this.FUNCTIONAL_GROUP_HO_S;
        } else if(theHoseCodeManipulator.getHoseCodeListDiviedByLevel().get(this.FIRST_CELL_INDEX).length() >= 2) {
            return this.FUNCTIONAL_GROUP_NOHYDROGENOXYGEN;
        }

        return this.FUNCTIONAL_GROUP_ALCHOL + this.SIXTH_ERROR_CHECK_POINT;

    }

    private boolean __isPrimaryAmine(HoseCodeManipulator theHoseCodeManipulator) {
        if (theHoseCodeManipulator.getFirstCellList().contains(this.OXYGEN_SYMBOL) || theHoseCodeManipulator.getFirstCellList().contains(this.PHOSPHATE_SYMBOL)) {
            return true;
        }

        return false;
    }

    private String __getFunctionalGroupInNitrogenBoundedOneCarbonCase(HoseCodeManipulator theHoseCodeManipulator) {
        if (theHoseCodeManipulator.getSecondCellList().isEmpty()) {
            return this.FUNCTIONAL_GROUP_PRIMARYAMINE;
        } else if (theHoseCodeManipulator.getSecondCellList().get(this.FIRST_POSITION).contains(this.DOUBLE_BOND_SYMBOL + this.OXYGEN_SYMBOL)) {
            return this.FUNCTIONAL_GROUP_PRIMARY_AMIDE_N;
        } else if (this.__count(theHoseCodeManipulator.getSecondCellList().get(this.FIRST_POSITION), this.AROMATIC_SYMBOL) == 2) {
            return this.FUNCTIONAL_GROUP_PRIMARY_AMINE_BOUND_AROMATIC_ATOM;
        } else {
            return this.FUNCTIONAL_GROUP_PRIMARYAMINE;
        }
    }

    private String __getFunctionalGroupInNitrogenBoundedOneAtomCase(HoseCodeManipulator theHoseCodeManipulator) {
        if (theHoseCodeManipulator.getHoseCodeListDiviedByLevel().get(this.FIRST_CELL_INDEX).equals(this.NITROGEN_SYMBOL)) {
            return this.FUNCTIONAL_GROUP_PRIMARYAMINE_NH2N;
        } else if (theHoseCodeManipulator.getHoseCodeListDiviedByLevel().get(this.FIRST_CELL_INDEX).equals(this.CARBON_SYMBOL)) {
            return this.__getFunctionalGroupInNitrogenBoundedOneCarbonCase(theHoseCodeManipulator);
        } else if (theHoseCodeManipulator.getHoseCodeListDiviedByLevel().get(this.FIRST_CELL_INDEX).equals(this.SULFUR_SYMBOL)) {
            return this.FUNCTIONAL_GROUP_NH2_S;
        } else if (theHoseCodeManipulator.getHoseCodeListDiviedByLevel().get(this.FIRST_CELL_INDEX).equals(this.OXYGEN_SYMBOL)) {
            return this.FUNCTIONAL_GROUP_O_NH2;
        } else if (theHoseCodeManipulator.getHoseCodeListDiviedByLevel().get(this.FIRST_CELL_INDEX).equals(this.PHOSPHATE_SYMBOL)) {
            return this.FUNCTIONAL_GROUP_P_NH2;
        } else if (theHoseCodeManipulator.getHoseCodeListDiviedByLevel().get(this.FIRST_CELL_INDEX).equals(this.NITROGEN_SYMBOL)) {
            return this.FUNCTIONAL_GROUP_N_NH2;
        }

        return this.SECOND_ERROR_CHECK_POINT;
    }

    private boolean __isSecondaryAmideN(HoseCodeManipulator theHoseCodeManipulator) {
        if(theHoseCodeManipulator.containsOnlyComma(this.SECOND_CELL_INDEX)) {
            return false;
        } else if (theHoseCodeManipulator.getSecondCellList().get(this.FIRST_POSITION).contains(this.DOUBLE_BOND_SYMBOL + this.OXYGEN_SYMBOL)) {
            return true;
        } else if(theHoseCodeManipulator.getSecondCellList().get(this.SECOND_POSITION).contains(this.DOUBLE_BOND_SYMBOL + this.OXYGEN_SYMBOL)) {
            return true;
        }
        
        return false;
    }

    private boolean __isSecondarySulfoxiamideN(HoseCodeManipulator theHoseCodeManipulator) {
        if(theHoseCodeManipulator.containsOnlyComma(this.SECOND_CELL_INDEX)) {
            return false;
        } else if (theHoseCodeManipulator.getSecondCellList().get(this.FIRST_POSITION).contains(this.DOUBLE_BOND_SYMBOL + this.SULFUR_SYMBOL)) {
            return true;
        } else if(theHoseCodeManipulator.getSecondCellList().get(this.SECOND_POSITION).contains(this.DOUBLE_BOND_SYMBOL + this.SULFUR_SYMBOL)) {
            return true;
        }
        
        return false;
    }
    
    private String __getFunctionalGroupInNitrogenBoundedCarbonAtomCase(HoseCodeManipulator theHoseCodeManipulator) {
        if (this.__isSecondaryAmideN(theHoseCodeManipulator)) {
            return this.FUNCTIONAL_GROUP_SECONDARY_AMIDE_N;
        } else if(this.__isSecondarySulfoxiamideN(theHoseCodeManipulator)) {
            return this.FUNCTIONAL_GROUP_SECONDARY_SULFOXAMIDE;
        } else {
            return this.FUNCTIONAL_GROUP_SECONDARYAMINE;
        }
    }

    private String __getFunctionalGroupInNitrogenBoundedTwoAtomCase(HoseCodeManipulator theHoseCodeManipulator) {
        if (theHoseCodeManipulator.getHoseCodeListDiviedByLevel().get(this.FIRST_CELL_INDEX).contains(this.TRIPLE_BOND_SYMBOL)) {
            return this.FUNCTIONAL_GROUP_NOHYDROGENNITROGEN;
        } else if (theHoseCodeManipulator.getHoseCodeListDiviedByLevel().get(this.FIRST_CELL_INDEX).contains(this.DOUBLE_BOND_SYMBOL + this.CARBON_SYMBOL)) {
            return this.FUNCTIONAL_GROUP_IMINE;
        } else if (theHoseCodeManipulator.getHoseCodeListDiviedByLevel().get(this.FIRST_CELL_INDEX).contains(this.NITROGEN_SYMBOL)) {
            return this.FUNCTIONAL_GROUP_N_NH;
        } else if (theHoseCodeManipulator.getHoseCodeListDiviedByLevel().get(this.FIRST_CELL_INDEX).contains(this.OXYGEN_SYMBOL + this.OXYGEN_SYMBOL)) {
            return this.FUNCTIONAL_GROUP_O_NH_O;
        } else if (theHoseCodeManipulator.getFirstCellList().contains(this.SULFUR_SYMBOL) || theHoseCodeManipulator.getFirstCellList().contains(this.PHOSPHATE_SYMBOL)) {
            if (!theHoseCodeManipulator.getHoseCodeListDiviedByLevel().get(this.FIRST_CELL_INDEX).contains(this.DOUBLE_BOND_SYMBOL)) {
                return this.FUNCTIONAL_GROUP_N3_SPECIAL_CASE;
            } else {
                return this.FUNCTIONAL_GROUP_N1_SPECIAL_CASE;
            }
        } else if (theHoseCodeManipulator.getFirstCellList().contains(this.CARBON_SYMBOL)) {
            return this.__getFunctionalGroupInNitrogenBoundedCarbonAtomCase(theHoseCodeManipulator);
        } 

        return this.THIRD_ERROR_CHECK_POINT;
    }

    private String __getFunctionalGroupInNitrogenCase(HoseCodeManipulator theHoseCodeManipulator) {
        String theFunctionalGroup = new String();

        if (this.__isHalideSymbol(theHoseCodeManipulator.getHoseCodeListDiviedByLevel().get(this.FIRST_CELL_INDEX))) {
            theFunctionalGroup = this.FUNCTIONAL_GROUP_HAIIDE_NITROGEN;
        } else if (theHoseCodeManipulator.getHoseCodeListDiviedByLevel().get(this.FIRST_CELL_INDEX).length() == 1) {
            theFunctionalGroup = this.__getFunctionalGroupInNitrogenBoundedOneAtomCase(theHoseCodeManipulator);
        } else if (theHoseCodeManipulator.getHoseCodeListDiviedByLevel().get(this.FIRST_CELL_INDEX).length() == 2) {
            theFunctionalGroup = this.__getFunctionalGroupInNitrogenBoundedTwoAtomCase(theHoseCodeManipulator);
        } else {
            theFunctionalGroup = this.FUNCTIONAL_GROUP_NOHYDROGENNITROGEN;
        }

        return theFunctionalGroup;
    }

    private boolean __isQuternaryCarbonHavingTripleBond(HoseCodeManipulator theHoseCodeManipulator) {
        if (theHoseCodeManipulator.getHoseCodeListDiviedByLevel().get(this.FIRST_CELL_INDEX).length() == 3
                && theHoseCodeManipulator.getHoseCodeListDiviedByLevel().get(this.FIRST_CELL_INDEX).contains(this.TRIPLE_BOND_SYMBOL)) {
            return true;
        }

        return false;
    }

    private boolean __isQuternaryCarbonNotHaveTripleBond(HoseCodeManipulator theHoseCodeManipulator) {
        if (theHoseCodeManipulator.getHoseCodeListDiviedByLevel().get(this.FIRST_CELL_INDEX).length() == 4 && !theHoseCodeManipulator.getFirstCellList().contains(this.AROMATIC_SYMBOL)) {
            return true;
        } else if (theHoseCodeManipulator.getHoseCodeListDiviedByLevel().get(this.FIRST_CELL_INDEX).length() > 4
                && this.__count(theHoseCodeManipulator.getHoseCodeListDiviedByLevel().get(this.FIRST_CELL_INDEX), this.AROMATIC_SYMBOL) >= 2) {
            return true;
        }

        return false;
    }

    private boolean __isQuternaryCarbon(HoseCodeManipulator theHoseCodeManipulator) {
        if (this.__isQuternaryCarbonHavingTripleBond(theHoseCodeManipulator) || this.__isQuternaryCarbonNotHaveTripleBond(theHoseCodeManipulator)) {
            return true;
        }

        return false;
    }

    private String __getFunctionalGroupInCarbonBoundOneOxygenCase(HoseCodeManipulator theHoseCodeManipulator) {
        if (theHoseCodeManipulator.getSecondCellList().contains(this.CARBON_SYMBOL)) {
            if (theHoseCodeManipulator.getThirdCellList().isEmpty()) {
                return this.FUNCTIONAL_GROUP_METHOXY;
            } else if (theHoseCodeManipulator.getThirdCellList().get(0).contains(this.DOUBLE_BOND_SYMBOL + this.OXYGEN_SYMBOL)) {
                return this.FUNCTIONAL_GROUP_ESTER;
            } else {
                return this.FUNCTIONAL_GROUP_METHOXY;
            }
        } else {
            return this.FUNCTIONAL_GROUP_METHOXY;
        }
    }

    private boolean __isCarbonBoundKetone(HoseCodeManipulator theHoseCodeManipulator) {
        if (!theHoseCodeManipulator.getHoseCodeListDiviedByLevel().get(this.FIRST_CELL_INDEX).equals(this.CARBON_SYMBOL)) {
            return false;
        } else if (!theHoseCodeManipulator.getHoseCodeListDiviedByLevel().get(this.SECOND_CELL_INDEX).equals(this.DOUBLE_BOND_SYMBOL + this.OXYGEN_SYMBOL)) {
            return false;
        }

        return true;
    }

    private String __getFunctionalGroupInCarbonBoundOneAtomCase(HoseCodeManipulator theHoseCodeManipulator) {
        if (theHoseCodeManipulator.getFirstCellList().contains(this.OXYGEN_SYMBOL)) {
            return this.__getFunctionalGroupInCarbonBoundOneOxygenCase(theHoseCodeManipulator);
        } else if (theHoseCodeManipulator.getFirstCellList().contains(this.NITROGEN_SYMBOL)) {
            return this.FUNCTIONAL_GROUP_H3C_N;
        } else if (theHoseCodeManipulator.getFirstCellList().contains(this.PHOSPHATE_SYMBOL)) {
            return this.FUNCTIONAL_GROUP_H3C_P;
        } else if (theHoseCodeManipulator.getFirstCellList().contains(this.SULFUR_SYMBOL)) {
            return this.FUNCTIONAL_GROUP_H3C_S;
        } else if (this.__isCarbonBoundKetone(theHoseCodeManipulator)) {
            return this.FUNCTIONAL_GROUP_KETONE;
        }

        return this.FUNCTIONAL_GROUP_PRIMARYALKYL;
    }

    private String __getFunctionalGroupInCOCase(HoseCodeManipulator theHoseCodeManipulator) {
        int theOxygenIndexInFirstCell = 1;
        int theCarbonIndexInFirstCell = 0;

        if (theHoseCodeManipulator.containsOnlyComma(this.SECOND_CELL_INDEX)) {
            return this.FUNCTIONAL_GROUP_CARBON_BOUND_OH;
        } else if (theHoseCodeManipulator.getSecondCellList().get(theOxygenIndexInFirstCell).contains(this.CARBON_SYMBOL)) {
            if (theHoseCodeManipulator.getThirdCellList().get(theHoseCodeManipulator.getThirdCellList().size() - 1).contains(this.DOUBLE_BOND_SYMBOL + this.OXYGEN_SYMBOL)) {
                return this.FUNCTIONAL_GROUP_ESTERROOCH2R;
            } else {
                return this.FUNCTIONAL_GROUP_CARBON_BOUND_OH;
            }
        } else {
            return this.FUNCTIONAL_GROUP_SECONDARY_ALKYL;
        }
    }

    private String __getFunctionalGroupInCCCaseAtFirstCell(HoseCodeManipulator theHoseCodeManipulator) {
        int theSingleBondOxygenInformationPosition = -1;

        if (theHoseCodeManipulator.getSecondCellList().indexOf(this.DOUBLE_BOND_SYMBOL + this.OXYGEN_SYMBOL + this.OXYGEN_SYMBOL) == this.SECOND_POSITION) {
            theSingleBondOxygenInformationPosition = theHoseCodeManipulator.getThirdCellList().size() - 1;
        } else if (theHoseCodeManipulator.getSecondCellList().indexOf(this.DOUBLE_BOND_SYMBOL + this.OXYGEN_SYMBOL + this.OXYGEN_SYMBOL) == this.FIRST_POSITION) {
            theSingleBondOxygenInformationPosition = this.SECOND_POSITION;
        }

        if (theHoseCodeManipulator.getSecondCellList().contains(this.DOUBLE_BOND_SYMBOL + this.OXYGEN_SYMBOL + this.OXYGEN_SYMBOL)
                && theHoseCodeManipulator.getThirdCellList().get(theSingleBondOxygenInformationPosition).isEmpty()) {
            return this.FUNCTIONAL_GROUP_ESTER;
        } else {
            return this.FUNCTIONAL_GROUP_SECONDARY_ALKYL;
        }
    }

    private String __getFunctionalGroupInCarbonBoundTwoOxygenCase(HoseCodeManipulator theHoseCodeManipulator) {
        if (theHoseCodeManipulator.getSecondCellList().contains(this.PHOSPHATE_SYMBOL) || theHoseCodeManipulator.getSecondCellList().contains(this.SULFUR_SYMBOL)) {
            return this.FUNCTIONAL_GROUP_SECONDARY_ALKYL;
        } else {
            return this.FUNCTIONAL_GROUP_ETHER;
        }
    }

    private boolean __isEmptyAtNitrogenPosition(HoseCodeManipulator theHoseCodeManipulator) {
        if (theHoseCodeManipulator.containsOnlyComma(this.SECOND_CELL_INDEX)) {
            return true;
        } else if (theHoseCodeManipulator.getSecondCellList().get(theHoseCodeManipulator.getHoseCodeListDiviedByLevel().get(this.FIRST_CELL_INDEX).indexOf(this.NITROGEN_SYMBOL)).isEmpty()) {
            return true;
        }

        return false;
    }

    private String __getFunctionalGroupInCarbonBoundTwoAtomCase(HoseCodeManipulator theHoseCodeManipulator) {
        if (theHoseCodeManipulator.getHoseCodeListDiviedByLevel().get(this.FIRST_CELL_INDEX).contains(this.OXYGEN_SYMBOL + this.OXYGEN_SYMBOL)) {
            return this.__getFunctionalGroupInCarbonBoundTwoOxygenCase(theHoseCodeManipulator);
        } else if (theHoseCodeManipulator.getHoseCodeListDiviedByLevel().get(this.FIRST_CELL_INDEX).contains(this.DOUBLE_BOND_SYMBOL + this.CARBON_SYMBOL)
                && theHoseCodeManipulator.getHoseCodeListDiviedByLevel().get(this.FIRST_CELL_INDEX).length() < 4) {
            return this.FUNCTIONAL_GROUP_ALKENE;
        } else if (theHoseCodeManipulator.getHoseCodeListDiviedByLevel().get(this.FIRST_CELL_INDEX).equals(this.CARBON_SYMBOL + this.OXYGEN_SYMBOL)) {
            return this.__getFunctionalGroupInCOCase(theHoseCodeManipulator);
        } else if (theHoseCodeManipulator.getHoseCodeListDiviedByLevel().get(this.FIRST_CELL_INDEX).contains(this.CARBON_SYMBOL)
                && this.__count(theHoseCodeManipulator.getFirstCellList(), this.CARBON_SYMBOL) == 2) {
            return this.__getFunctionalGroupInCCCaseAtFirstCell(theHoseCodeManipulator);
        } else if (theHoseCodeManipulator.getHoseCodeListDiviedByLevel().get(this.FIRST_CELL_INDEX).contains(this.CARBON_SYMBOL + this.NITROGEN_SYMBOL)
                && this.__isEmptyAtNitrogenPosition(theHoseCodeManipulator)) {
            return this.FUNCTIONAL_GROUP_SECONDARY_ALKYL;
        } else if (theHoseCodeManipulator.getHoseCodeListDiviedByLevel().get(this.FIRST_CELL_INDEX).contains(this.DOUBLE_BOND_SYMBOL + this.CARBON_SYMBOL)) {
            return this.FUNCTIONAL_GROUP_ALKENE;
        }

        return this.FUNCTIONAL_GROUP_SECONDARY_ALKYL;
    }

    private boolean __isCarbonBoundTwoHydrogen(HoseCodeManipulator theHoseCodeManipulator) {
        if (theHoseCodeManipulator.getHoseCodeListDiviedByLevel().get(this.FIRST_CELL_INDEX).length() != 2) {
            return false;
        } else if (theHoseCodeManipulator.getHoseCodeListDiviedByLevel().get(this.FIRST_CELL_INDEX).contains(this.TRIPLE_BOND_SYMBOL)) {
            return false;
        } else if (theHoseCodeManipulator.getHoseCodeListDiviedByLevel().get(this.FIRST_CELL_INDEX).contains(this.AROMATIC_SYMBOL)) {
            return false;
        }

        return true;
    }

    private boolean __isCarbonBoundOneHydrogen(HoseCodeManipulator theHoseCodeManipulator) {
        if (theHoseCodeManipulator.getHoseCodeListDiviedByLevel().get(this.FIRST_CELL_INDEX).length() != 3) {
            return false;
        } else if (theHoseCodeManipulator.getHoseCodeListDiviedByLevel().get(this.FIRST_CELL_INDEX).contains(this.TRIPLE_BOND_SYMBOL)) {
            return false;
        } else if (theHoseCodeManipulator.getHoseCodeListDiviedByLevel().get(this.FIRST_CELL_INDEX).contains(this.AROMATIC_SYMBOL)) {
            return false;
        }

        return true;
    }

    private boolean __isCarbonBoundCarboxlicAcid(HoseCodeManipulator theHoseCodeManipulator) {
        if (!theHoseCodeManipulator.getHoseCodeListDiviedByLevel().get(this.FIRST_CELL_INDEX).contains(this.DOUBLE_BOND_SYMBOL + this.OXYGEN_SYMBOL)) {
            return false;
        } else if (this.__count(theHoseCodeManipulator.getFirstCellList(), this.OXYGEN_SYMBOL) != 2) {
            return false;
        } else if (theHoseCodeManipulator.containsOnlyComma(this.SECOND_CELL_INDEX)) {
            return true;
        } else if (!theHoseCodeManipulator.getSecondCellList().get(this.__getHydrogenIndexInCarboxylicAcid(theHoseCodeManipulator)).isEmpty()) {
            return false;
        }

        return true;
    }

    private boolean __isEmptyInDoubleBondOxygen(HoseCodeManipulator theHoseCodeManipulator, List<String> theCharacterListFromStartToDoubleOxygen, int theSize) {
        if (theHoseCodeManipulator.getThirdCellList().get(theSize - this.__count(theCharacterListFromStartToDoubleOxygen, this.DOUBLE_BOND_SYMBOL)
                - this.__count(theCharacterListFromStartToDoubleOxygen, this.RING_CLOSER_SYMBOL) - this.__count(theCharacterListFromStartToDoubleOxygen, this.POSITIVE_CHARGE)
                - this.__count(theCharacterListFromStartToDoubleOxygen, this.NEGATIVE_CHARGE) - this.__count(theCharacterListFromStartToDoubleOxygen, this.TRIPLE_BOND_SYMBOL)).isEmpty()) {
            return true;
        }

        return false;
    }

    private boolean __containCarbonAtPosition(HoseCodeManipulator theHoseCodeManipulator, List<String> theCharacterListFromStartToDoubleOxygen, int theSize) {
        if (theHoseCodeManipulator.containsOnlyComma(this.THIRD_CELL_INDEX)) {
            return false;
        } else if (theHoseCodeManipulator.getThirdCellList().get(theSize - this.__count(theCharacterListFromStartToDoubleOxygen, this.DOUBLE_BOND_SYMBOL)
                - this.__count(theCharacterListFromStartToDoubleOxygen, this.RING_CLOSER_SYMBOL) - this.__count(theCharacterListFromStartToDoubleOxygen, this.POSITIVE_CHARGE)
                - this.__count(theCharacterListFromStartToDoubleOxygen, this.NEGATIVE_CHARGE) - this.__count(theCharacterListFromStartToDoubleOxygen, this.TRIPLE_BOND_SYMBOL) + 1)
                .equals(this.CARBON_SYMBOL)) {
            return true;
        }

        return false;
    }

    private String __getFunctionalGroupInCarbonBoundThreeCarbonCase(HoseCodeManipulator theHoseCodeManipulator) {
        if (theHoseCodeManipulator.getSecondCellList().contains(this.DOUBLE_BOND_SYMBOL + this.OXYGEN_SYMBOL + this.OXYGEN_SYMBOL)) {
            List<String> theSplitedString = new ArrayList<>();
            int theSize = 0;

            for (int ai = 0, aEnd = theHoseCodeManipulator.getSecondCellList().indexOf(this.DOUBLE_BOND_SYMBOL + this.OXYGEN_SYMBOL + this.OXYGEN_SYMBOL); ai < aEnd; ai++) {
                Collections.addAll(theSplitedString, theHoseCodeManipulator.getSecondCellList().get(ai).split(EMPTY_STRING));

                theSize += theHoseCodeManipulator.getSecondCellList().get(ai).length();
            }

            if (theHoseCodeManipulator.containsOnlyComma(this.THIRD_CELL_INDEX)) {
                return this.FUNCTIONAL_GROUP_CARBON_BOUND_CARBOXYLIC;
            } else if (this.__isEmptyInDoubleBondOxygen(theHoseCodeManipulator, theSplitedString, theSize) && this.__containCarbonAtPosition(theHoseCodeManipulator, theSplitedString, theSize)) {
                return this.FUNCTIONAL_GROUP_ESTERR2CHCOOR;
            } else {
                return this.FUNCTIONAL_GROUP_TERTIARY_ALKYL;
            }
        }

        return this.FUNCTIONAL_GROUP_TERTIARY_ALKYL;
    }

    private String __getFunctionalGroupInCarbonBoundThreeAtomCase(HoseCodeManipulator theHoseCodeManipulator) {
        if (theHoseCodeManipulator.getHoseCodeListDiviedByLevel().get(this.FIRST_CELL_INDEX).contains(this.DOUBLE_BOND_SYMBOL + this.OXYGEN_SYMBOL)
                && theHoseCodeManipulator.getHoseCodeListDiviedByLevel().get(this.FIRST_CELL_INDEX).contains(this.CARBON_SYMBOL)) {
            return this.FUNCTIONAL_GROUP_ALDEHYDE;
        } else if (theHoseCodeManipulator.getHoseCodeListDiviedByLevel().get(this.FIRST_CELL_INDEX).contains(this.DOUBLE_BOND_SYMBOL + this.CARBON_SYMBOL)) {
            return this.FUNCTIONAL_GROUP_VINYLIC;
        } else if (this.__isCarbonBoundCarboxlicAcid(theHoseCodeManipulator)) {
            return this.FUNCTIONAL_GROUP_CARBON_BOUND_CARBOXYLIC;
        } else if (theHoseCodeManipulator.getHoseCodeListDiviedByLevel().get(this.FIRST_CELL_INDEX).contains(this.CARBON_SYMBOL)
                && this.__count(theHoseCodeManipulator.getFirstCellList(), this.CARBON_SYMBOL) == 3) {
            return this.__getFunctionalGroupInCarbonBoundThreeCarbonCase(theHoseCodeManipulator);
        } else if (this.__count(theHoseCodeManipulator.getFirstCellList(), this.CARBON_SYMBOL) == 2
                && (theHoseCodeManipulator.getHoseCodeListDiviedByLevel().get(this.FIRST_CELL_INDEX).contains(this.OXYGEN_SYMBOL)
                && theHoseCodeManipulator.getHoseCodeListDiviedByLevel().get(this.FIRST_CELL_INDEX).contains(this.NITROGEN_SYMBOL))) {
            return this.FUNCTIONAL_GROUP_TERTIARY_ALKYL;
        } else if (this.__count(theHoseCodeManipulator.getHoseCodeListDiviedByLevel().get(this.FIRST_CELL_INDEX), this.OXYGEN_SYMBOL) == 2
                && theHoseCodeManipulator.getHoseCodeListDiviedByLevel().get(this.FIRST_CELL_INDEX).contains(this.DOUBLE_BOND_SYMBOL)) {
            return this.FUNCTIONAL_GROUP_ALDEHYDE;
        } else if (theHoseCodeManipulator.getHoseCodeListDiviedByLevel().get(this.FIRST_CELL_INDEX).contains(this.DOUBLE_BOND_SYMBOL + this.CARBON_SYMBOL + this.CARBON_SYMBOL)) {
            return this.FUNCTIONAL_GROUP_ALKENE;
        }

        return this.FUNCTIONAL_GROUP_TERTIARY_ALKYL;
    }

    private String __getFunctionalGroupInCarbonCase(HoseCodeManipulator theHoseCodeManipulator) {
        String theFunctionalGroup = new String();
        int theJugment = 0;

        theSearch:
        if (this.__isQuternaryCarbon(theHoseCodeManipulator)) {
            theFunctionalGroup = this.FUNCTIONAL_GROUP_NOHYDROGENCARBON;
        } else if (this.__hasTwoStarSymbol(theHoseCodeManipulator.getSecondCellList())
                && !theHoseCodeManipulator.getHoseCodeListDiviedByLevel().get(this.FIRST_CELL_INDEX).equals(this.CARBON_SYMBOL)) {
            theFunctionalGroup = this.FUNCTIONAL_GROUP_AROMATICALKYL;
        } else if (theHoseCodeManipulator.getHoseCodeListDiviedByLevel().get(this.FIRST_CELL_INDEX).equals(this.TRIPLE_BOND_SYMBOL + this.CARBON_SYMBOL)) {
            theFunctionalGroup = this.FUNCTIONAL_GROUP_ALKYNYL;
        } else if (this.__count(theHoseCodeManipulator.getFirstCellList(), this.AROMATIC_SYMBOL) == 2) {
            if (theHoseCodeManipulator.getHoseCodeListDiviedByLevel().get(this.FIRST_CELL_INDEX).contains(this.DOUBLE_BOND_SYMBOL + this.OXYGEN_SYMBOL)) {
                theFunctionalGroup = this.FUNCTIONAL_GROUP_QUINONE;
            } else {
                theFunctionalGroup = this.FUNCTIONAL_GROUP_BENZYL;
            }
        } else if (theHoseCodeManipulator.getHoseCodeListDiviedByLevel().get(this.FIRST_CELL_INDEX).contains(this.DOUBLE_BOND_SYMBOL + this.CARBON_SYMBOL + this.CARBON_SYMBOL)) {
            theFunctionalGroup = this.FUNCTIONAL_GROUP_ALLYLIC;
        } else if (this.__isHalideSymbol(theHoseCodeManipulator.getHoseCodeListDiviedByLevel().get(this.FIRST_CELL_INDEX))) {
            theFunctionalGroup = this.FUNCTIONAL_GROUP_HALIDE;
        } else if (theHoseCodeManipulator.getHoseCodeListDiviedByLevel().get(this.FIRST_CELL_INDEX).length() == 1) {
            theFunctionalGroup = this.__getFunctionalGroupInCarbonBoundOneAtomCase(theHoseCodeManipulator);
        } else if (this.__isCarbonBoundTwoHydrogen(theHoseCodeManipulator)) {
            theFunctionalGroup = this.__getFunctionalGroupInCarbonBoundTwoAtomCase(theHoseCodeManipulator);
        } else if (this.__isCarbonBoundOneHydrogen(theHoseCodeManipulator)) {
            theFunctionalGroup = this.__getFunctionalGroupInCarbonBoundThreeAtomCase(theHoseCodeManipulator);
        } else {
            theFunctionalGroup = this.FOURTH_ERROR_CHECK_POINT;
        }

        return theFunctionalGroup;
    }

    private String __setFunctionalGroup(List<String> theCell) {
        String theFunctionalGroup = new String();
        HoseCodeManipulator theHoseCodeManipulator = new HoseCodeManipulator(theCell);
        int theJugment = 0;

        try {
            if (!theHoseCodeManipulator.getSecondCellList().isEmpty()) {
                this.__modifySecondCellList(theHoseCodeManipulator);
            }
        } catch (NumberFormatException e) {
            e.printStackTrace();
            System.err.println(theHoseCodeManipulator.getZeroCellList());
        }

        if (this.__isHalideSymbol(theCell.get(this.ZERO_CELL_INDEX))) {
            theFunctionalGroup = this.FUNCTIONAL_GROUP_HALIDE_START;
        } else if (theCell.get(0).contains(this.OXYGEN_SYMBOL + this.NEGATIVE_CHARGE)) {
            theFunctionalGroup = this.__getFunctionalGroupInOxygenCase(theHoseCodeManipulator);
        } else if (theCell.get(0).contains(this.NITROGEN_SYMBOL + this.NEGATIVE_CHARGE)) {
            theFunctionalGroup = this.__getFunctionalGroupInNitrogenCase(theHoseCodeManipulator);
        } else if (theCell.get(0).contains(this.CARBON_SYMBOL + this.NEGATIVE_CHARGE) && !theHoseCodeManipulator.getFirstCellList().contains(this.POSITIVE_CHARGE)) {
            theFunctionalGroup = this.__getFunctionalGroupInCarbonCase(theHoseCodeManipulator);
        } else if (theCell.get(0).contains(this.SULFUR_SYMBOL + this.NEGATIVE_CHARGE)) {
            if (theHoseCodeManipulator.getFirstCellList().size() == 1) {
                theFunctionalGroup = this.FUNCTIONAL_GROUP_THIOL;
            } else {
                theFunctionalGroup = this.FUNCTIONAL_GROUP_NOHYDROGENSULFONE;
            }
        } else if (theCell.get(0).contains(this.PHOSPHATE_SYMBOL + this.NEGATIVE_CHARGE)) {
            if (theHoseCodeManipulator.getFirstCellList().size() == 1) {
                theFunctionalGroup = this.FUNCTIONAL_GROUP_PH;
            } else {
                theFunctionalGroup = this.FUNCTIONAL_GROUP_NOHYDROGENPHOSPORE;
            }
        } else if (theCell.get(0).contains(this.HYDROGEN_SYMBOL + this.NEGATIVE_CHARGE)) {
            theFunctionalGroup = this.FUNCTIONAL_GROUP_HYDROGEN;
        } else {
            theFunctionalGroup = this.FIVETH_ERROR_CHECK_POINT;
        }

        return theFunctionalGroup;
    }

    private List<List<String>> __getSplitedDataMatrix(List<String> theSecondCellList) {
        List<List<String>> theSplitedDataMatrix = new ArrayList<>();

        for (int li = 0, lEnd = theSecondCellList.size(); li < lEnd; li++) {
            List<String> theSplitedDataList = new ArrayList<>();
            String[] theSplitedString = theSecondCellList.get(li).split(this.EMPTY_STRING);

            Collections.addAll(theSplitedDataList, theSplitedString);
            theSplitedDataMatrix.add(theSplitedDataList);
        }

        return theSplitedDataMatrix;
    }

    private boolean __hasTwoStarSymbol(List<String> theSecondCellList) {
        List<List<String>> theSplitedDataMatrix = this.__getSplitedDataMatrix(theSecondCellList);

        for (int ri = 0, rEnd = theSplitedDataMatrix.size(); ri < rEnd; ri++) {
            if (theSplitedDataMatrix.get(ri).isEmpty()) {
                continue;
            }

            if (this.__count(theSplitedDataMatrix.get(ri), this.AROMATIC_SYMBOL) == 2) {
                return true;
            }
        }

        return false;
    }

    private List<String> __divideCellByCharacter(List<String> theCell, int theCellNumber) {
        String[] theSplitedStringList = theCell.get(theCellNumber).split(this.EMPTY_STRING);
        List<String> theSplitedString = new ArrayList<>();

        Collections.addAll(theSplitedString, theSplitedStringList);
        theSplitedString.remove(0);

        return theSplitedString;
    }

    private List<String> __divideCellByComma(List<String> theCell, Integer theCellNumber) {
        String[] theSplitedStringArray = theCell.get(theCellNumber).split(this.COMMA_STRING);
        List<String> theSplitedStringList = new ArrayList<>();

        Collections.addAll(theSplitedStringList, theSplitedStringArray);

        return theSplitedStringList;
    }

    private Integer __getHydrogenIndexInCarboxylicAcid(HoseCodeManipulator theHoseCodeManipulator) {
        int theCheckIndex = 0;
        StringBuilder theMergeString = new StringBuilder();

        for (int i = 0; i < theHoseCodeManipulator.getFirstCellList().size(); i++) {
            theMergeString.append(theHoseCodeManipulator.getFirstCellList().get(i));
        }

        while ((theCheckIndex = theMergeString.indexOf(this.OXYGEN_SYMBOL, theCheckIndex)) != -1) {
            if (theHoseCodeManipulator.getFirstCellList().get(theCheckIndex - 1) != this.DOUBLE_BOND_SYMBOL) {
                return theCheckIndex;
            }
        }

        return -1;
    }

    private <T> int __count(List<T> theList, T theValue) {
        int theNumberOfValue = 0;

        for (T theValueInList : theList) {
            if (theValueInList.equals(theValue)) {
                theNumberOfValue++;
            }
        }

        return theNumberOfValue;
    }

    private int __count(String theString, String theCriterionString) {
        int theNumberOfValue = 0;
        int theSizeOfCriterionString = theCriterionString.length();

        for (int si = 0, sEnd = theString.length() - theSizeOfCriterionString; si < sEnd; si++) {
            String theSubString = theString.substring(si, si + theSizeOfCriterionString);

            if (theSubString.equals(theCriterionString)) {
                theNumberOfValue++;
            }
        }

        return theNumberOfValue;
    }
}


