/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.bmdrc.nmr.tool;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import org.bmdrc.nmr.interfaces.IHoseCodeSymbol;
import org.bmdrc.interfaces.IStringConstant;
import org.bmdrc.util.Module;
import org.bmdrc.util.TwoDimensionList;
import org.openscience.cdk.Molecule;
import org.openscience.cdk.interfaces.IMolecule;

/**
 *
 * @author labwindows
 */
public class HoseCodeDescriptor implements IHoseCodeSymbol, IStringConstant, Serializable {
    private static final long serialVersionUID = -59951873654089193L;

    private IMolecule itsMolecule;
    private List<String> itsHoseCodeNameList;
    private TwoDimensionList<Integer> itsHoseCodeValue2dList;
    private Integer itsHoseCodeLevel;
    //constant string variable
    private final String HOSE_CODE_KEY = "HOSE_CODE_";
    //constant int variable
    private final int POSITIVE_CHARGE_INDEX = 0;
    private final int NEGATIVE_CHARGE_INDEX = 1;
    private final int DOUBLE_BOND_SYMBOL_INDEX = 2;
    private final int TRIPLE_BOND_SYMBOL_INDEX = 3;
    private final int AROMATIC_SYMBOL_INDEX = 4;
    private final int RING_CLOSER_SYMBOL_INDEX = 5;
    private final int AROMATIC_CARBON_INDEX = 6;
    private final int HYDROGEN_SYMBOL_INDEX = 7;
    private final int FLUORINE_SYMBOL_INDEX = 8;
    private final int CHLORIDE_SYMBOL_INDEX = 9;
    private final int IODIDE_SYMBOL_INDEX = 10;
    private final int BROMIDE_SYMBOL_INDEX = 11;
    private final int CARBON_SYMBOL_INDEX = 12;
    private final int OXYGEN_SYMBOL_INDEX = 13;
    private final int NITROGEN_SYMBOL_INDEX = 14;
    private final int PHOSPHATE_SYMBOL_INDEX = 15;
    private final int SULFUR_SYMBOL_INDEX = 16;

    public HoseCodeDescriptor() {
        this.setMolecule(new Molecule());
        this.setHoseCodeNameList(new ArrayList<String>());
        this.setHoseCodeValue2dList(new TwoDimensionList<Integer>());
        this.setHoseCodeLevel(-1);
    }

    public HoseCodeDescriptor(IMolecule theMolecule, int theHoseCodeLevel) {
        this.itsMolecule = theMolecule;
        this.setHoseCodeLevel(theHoseCodeLevel);
        this.generateHoseCodeNameList(theHoseCodeLevel);
        this.generateHoseCodeValueList(theHoseCodeLevel);
    }

    public IMolecule getMolecule() {
        return itsMolecule;
    }

    public void setMolecule(IMolecule theMolecule) {
        this.itsMolecule = theMolecule;
    }

    public List<String> getHoseCodeNameList() {
        return itsHoseCodeNameList;
    }

    public void setHoseCodeNameList(List<String> theHoseCodeNameList) {
        this.itsHoseCodeNameList = theHoseCodeNameList;
    }

    public List<String> setHoseCodeNameList() {
        return itsHoseCodeNameList;
    }

    public TwoDimensionList<Integer> getHoseCodeValue2dList() {
        return itsHoseCodeValue2dList;
    }

    public void setHoseCodeValue2dList(TwoDimensionList<Integer> theHoseCodeValue2dList) {
        this.itsHoseCodeValue2dList = theHoseCodeValue2dList;
    }

    public TwoDimensionList<Integer> setHoseCodeValue2dList() {
        return itsHoseCodeValue2dList;
    }

    public Integer getHoseCodeLevel() {
        return itsHoseCodeLevel;
    }

    public void setHoseCodeLevel(Integer theHoseCodeLevel) {
        this.itsHoseCodeLevel = theHoseCodeLevel;
    }

    public void generateHoseCodeValueList(int theHoseCodeLevel) {
        String[] theHoseCodeArray = this.getMolecule().getProperty(this.HOSE_CODE_KEY + theHoseCodeLevel).toString().split(this.TAB_STRING);

        this.setHoseCodeValue2dList(new TwoDimensionList<Integer>());

        for (String theHoseCode : theHoseCodeArray) {
            this.setHoseCodeValue2dList().add(this.__generateHoseCodeValueList(theHoseCode));
        }
    }

    private List<Integer> __generateHoseCodeValueList(String theHoseCode) {
        List<Integer> theHoseCodeValueList = new ArrayList<>();
        HoseCodeManipulator theHoseCodeManipulator = new HoseCodeManipulator(theHoseCode);

        for (int li = 1; li <= this.getHoseCodeLevel(); li++) {
            if (li < theHoseCodeManipulator.getHoseCodeLevel2dList().size()) {
                theHoseCodeValueList.addAll(this.__setHoseCodeValueListInLevel(theHoseCodeManipulator.getHoseCodeLevel2dList().get(li)));
            } else {
                theHoseCodeValueList.addAll(this.__EmptyHoseCodeValueList());
            }
        }

        return theHoseCodeValueList;
    }

    private List<Integer> __setHoseCodeValueListInLevel(List<String> theHoseCodeListInLevel) {
        List<Integer> theHoseCodeValueListInLevel = this.__EmptyHoseCodeValueList();

        for (String theHoseCodeInLevel : theHoseCodeListInLevel) {
            this.__setHoseCodeValueListInLevel(theHoseCodeInLevel, theHoseCodeValueListInLevel);
        }

        return theHoseCodeValueListInLevel;
    }

    private void __setHoseCodeValueListInLevel(String theHoseCodeInLevel, List<Integer> theHoseCodeValueListInLevel) {
        theHoseCodeValueListInLevel.set(this.POSITIVE_CHARGE_INDEX, theHoseCodeValueListInLevel.get(this.POSITIVE_CHARGE_INDEX) + Module.count(theHoseCodeInLevel, this.POSITIVE_CHARGE));
        theHoseCodeValueListInLevel.set(this.NEGATIVE_CHARGE_INDEX, theHoseCodeValueListInLevel.get(this.NEGATIVE_CHARGE_INDEX) + Module.count(theHoseCodeInLevel, this.NEGATIVE_CHARGE));
        theHoseCodeValueListInLevel.set(this.DOUBLE_BOND_SYMBOL_INDEX, theHoseCodeValueListInLevel.get(this.DOUBLE_BOND_SYMBOL_INDEX) + Module.count(theHoseCodeInLevel, this.DOUBLE_BOND_SYMBOL));
        theHoseCodeValueListInLevel.set(this.TRIPLE_BOND_SYMBOL_INDEX, theHoseCodeValueListInLevel.get(this.TRIPLE_BOND_SYMBOL_INDEX) + Module.count(theHoseCodeInLevel, this.TRIPLE_BOND_SYMBOL));
        theHoseCodeValueListInLevel.set(this.AROMATIC_SYMBOL_INDEX, theHoseCodeValueListInLevel.get(this.AROMATIC_SYMBOL_INDEX) + Module.count(theHoseCodeInLevel, this.AROMATIC_SYMBOL));
        theHoseCodeValueListInLevel.set(this.RING_CLOSER_SYMBOL_INDEX, theHoseCodeValueListInLevel.get(this.RING_CLOSER_SYMBOL_INDEX) + Module.count(theHoseCodeInLevel, this.RING_CLOSER_SYMBOL));
        theHoseCodeValueListInLevel.set(this.AROMATIC_CARBON_INDEX, theHoseCodeValueListInLevel.get(this.AROMATIC_CARBON_INDEX) + Module.count(theHoseCodeInLevel, this.AROMATIC_CARBON));
        theHoseCodeValueListInLevel.set(this.HYDROGEN_SYMBOL_INDEX, theHoseCodeValueListInLevel.get(this.HYDROGEN_SYMBOL_INDEX) + Module.count(theHoseCodeInLevel, this.HYDROGEN_SYMBOL));
        theHoseCodeValueListInLevel.set(this.FLUORINE_SYMBOL_INDEX, theHoseCodeValueListInLevel.get(this.FLUORINE_SYMBOL_INDEX) + Module.count(theHoseCodeInLevel, this.FLUORINE_SYMBOL));
        theHoseCodeValueListInLevel.set(this.CHLORIDE_SYMBOL_INDEX, theHoseCodeValueListInLevel.get(this.CHLORIDE_SYMBOL_INDEX) + Module.count(theHoseCodeInLevel, this.CHLORIDE_SYMBOL));
        theHoseCodeValueListInLevel.set(this.IODIDE_SYMBOL_INDEX, theHoseCodeValueListInLevel.get(this.IODIDE_SYMBOL_INDEX) + Module.count(theHoseCodeInLevel, this.IODIDE_SYMBOL));
        theHoseCodeValueListInLevel.set(this.BROMIDE_SYMBOL_INDEX, theHoseCodeValueListInLevel.get(this.BROMIDE_SYMBOL_INDEX) + Module.count(theHoseCodeInLevel, this.BROMIDE_SYMBOL));
        theHoseCodeValueListInLevel.set(this.CARBON_SYMBOL_INDEX, theHoseCodeValueListInLevel.get(this.CARBON_SYMBOL_INDEX) + Module.count(theHoseCodeInLevel, this.CARBON_SYMBOL));
        theHoseCodeValueListInLevel.set(this.OXYGEN_SYMBOL_INDEX, theHoseCodeValueListInLevel.get(this.OXYGEN_SYMBOL_INDEX) + Module.count(theHoseCodeInLevel, this.OXYGEN_SYMBOL));
        theHoseCodeValueListInLevel.set(this.NITROGEN_SYMBOL_INDEX, theHoseCodeValueListInLevel.get(this.NITROGEN_SYMBOL_INDEX) + Module.count(theHoseCodeInLevel, this.NITROGEN_SYMBOL));
        theHoseCodeValueListInLevel.set(this.PHOSPHATE_SYMBOL_INDEX, theHoseCodeValueListInLevel.get(this.PHOSPHATE_SYMBOL_INDEX) + Module.count(theHoseCodeInLevel, this.PHOSPHATE_SYMBOL));
        theHoseCodeValueListInLevel.set(this.SULFUR_SYMBOL_INDEX, theHoseCodeValueListInLevel.get(this.SULFUR_SYMBOL_INDEX) + Module.count(theHoseCodeInLevel, this.SULFUR_SYMBOL));
    }

    private List<Integer> __initializeHoseCodeValueList() {
        List<Integer> theHoseCodeValueList = new ArrayList<>();

        for (int li = 0, lEnd = this.getHoseCodeNameList().size(); li < lEnd; li++) {
            theHoseCodeValueList.add(0);
        }

        return theHoseCodeValueList;
    }

    private List<Integer> __EmptyHoseCodeValueList() {
        List<Integer> theHoseCodeValueList = new ArrayList<>();

        for (int li = 0; li < 17; li++) {
            theHoseCodeValueList.add(0);
        }

        return theHoseCodeValueList;
    }

    public List<String> generateHoseCodeNameList(int theHoseCodeLevel) {
        this.setHoseCodeNameList(new ArrayList<String>());

        for (int li = 1; li <= theHoseCodeLevel; li++) {
            this.setHoseCodeNameList().addAll(this.__generateHoseCodeNameList(li));
        }

        return this.getHoseCodeNameList();
    }

    private List<String> __generateHoseCodeNameList(int theHoseCodeLevel) {
        List<String> theHoseCodeNameList = new ArrayList<>();

        theHoseCodeNameList.add(this.POSITIVE_CHARGE + this.UNDER_BAR + theHoseCodeLevel);
        theHoseCodeNameList.add(this.NEGATIVE_CHARGE + this.UNDER_BAR + theHoseCodeLevel);
        theHoseCodeNameList.add(this.DOUBLE_BOND_SYMBOL + this.UNDER_BAR + theHoseCodeLevel);
        theHoseCodeNameList.add(this.TRIPLE_BOND_SYMBOL + this.UNDER_BAR + theHoseCodeLevel);
        theHoseCodeNameList.add(this.AROMATIC_SYMBOL + this.UNDER_BAR + theHoseCodeLevel);
        theHoseCodeNameList.add(this.RING_CLOSER_SYMBOL + this.UNDER_BAR + theHoseCodeLevel);
        theHoseCodeNameList.add(this.AROMATIC_CARBON + this.UNDER_BAR + theHoseCodeLevel);
        theHoseCodeNameList.add(this.HYDROGEN_SYMBOL + this.UNDER_BAR + theHoseCodeLevel);
        theHoseCodeNameList.add(this.FLUORINE_SYMBOL + this.UNDER_BAR + theHoseCodeLevel);
        theHoseCodeNameList.add(this.CHLORIDE_SYMBOL + this.UNDER_BAR + theHoseCodeLevel);
        theHoseCodeNameList.add(this.IODIDE_SYMBOL + this.UNDER_BAR + theHoseCodeLevel);
        theHoseCodeNameList.add(this.BROMIDE_SYMBOL + this.UNDER_BAR + theHoseCodeLevel);
        theHoseCodeNameList.add(this.CARBON_SYMBOL + this.UNDER_BAR + theHoseCodeLevel);
        theHoseCodeNameList.add(this.OXYGEN_SYMBOL + this.UNDER_BAR + theHoseCodeLevel);
        theHoseCodeNameList.add(this.NITROGEN_SYMBOL + this.UNDER_BAR + theHoseCodeLevel);
        theHoseCodeNameList.add(this.PHOSPHATE_SYMBOL + this.UNDER_BAR + theHoseCodeLevel);
        theHoseCodeNameList.add(this.SULFUR_SYMBOL + this.UNDER_BAR + theHoseCodeLevel);

        return theHoseCodeNameList;
    }

    public String toString(String theColumnSeparator) {
        StringBuilder theStringBuilder = new StringBuilder();

        for (int ni = 0, nEnd = this.getHoseCodeNameList().size(); ni < nEnd; ni++) {
            theStringBuilder.append(this.getHoseCodeNameList().get(ni));

            if (ni < nEnd - 1) {
                theStringBuilder.append(theColumnSeparator);
            } else {
                theStringBuilder.append(this.END_LINE);
            }
        }

        for (List<Integer> theHoseCodeValueList : this.getHoseCodeValue2dList().get2dList()) {
            for (int vi = 0, vEnd = theHoseCodeValueList.size(); vi < vEnd; vi++) {
                theStringBuilder.append(theHoseCodeValueList.get(vi));

                if (vi < vEnd - 1) {
                    theStringBuilder.append(theColumnSeparator);
                } else {
                    theStringBuilder.append(this.END_LINE);
                }
            }
        }

        return theStringBuilder.toString();
    }

    @Override
    public String toString() {
        StringBuilder theStringBuilder = new StringBuilder();

        theStringBuilder.append("Descriptor Name\n");
        theStringBuilder.append(this.getHoseCodeNameList());
        theStringBuilder.append("Descriptor Value\n");

        for (List<Integer> theDescriptorValueList : this.getHoseCodeValue2dList().get2dList()) {
            theStringBuilder.append(theDescriptorValueList).append(this.END_LINE);
        }

        return theStringBuilder.toString();
    }
}
