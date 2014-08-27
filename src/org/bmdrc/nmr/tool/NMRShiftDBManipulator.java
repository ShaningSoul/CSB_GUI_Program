package org.bmdrc.nmr.tool;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.bmdrc.interfaces.IStringConstant;
import org.bmdrc.nmr.Nmr1dUnit;
import org.bmdrc.nmr.Nmr1dUnitList;

/**
 *
 * @author SungBo Hwang, CSB
 */
public class NMRShiftDBManipulator implements IStringConstant, Serializable{
    private static final long serialVersionUID = 1596533747508121744L;
    private NMRShiftDB itsNMRShiftDB;
    private Map<String, List<Double>> itsChemicalShiftListMapByHoseCode;
    //constant string variable
    private final String HOSE_CODE_KEY = "HOSE_CODE_";

    public NMRShiftDBManipulator() {
        this.setNMRShiftDB(new NMRShiftDB());
        this.setChemicalShiftListMapByHoseCode(new HashMap<String, List<Double>>());
    }

    public NMRShiftDBManipulator(NMRShiftDB theNMRShiftDB) throws CloneNotSupportedException {
        this.setNMRShiftDB(new NMRShiftDB(theNMRShiftDB));
        this.setChemicalShiftListMapByHoseCode(new HashMap<String, List<Double>>());
    }
    
    public NMRShiftDB getNMRShiftDB() {
        return itsNMRShiftDB;
    }

    public void setNMRShiftDB(NMRShiftDB theNMRShiftDB) {
        this.itsNMRShiftDB = theNMRShiftDB;
    }

    public Map<String, List<Double>> getChemicalShiftListMapByHoseCode() {
        return itsChemicalShiftListMapByHoseCode;
    }

    public void setChemicalShiftListMapByHoseCode(Map<String, List<Double>> theChemicalShiftListMapByHoseCode) {
        this.itsChemicalShiftListMapByHoseCode = theChemicalShiftListMapByHoseCode;
    }
    
    public Map<String, List<Double>> setChemicalShiftListMapByHoseCode() {
        return itsChemicalShiftListMapByHoseCode;
    }
    
    public Map<String, List<Double>> getHydrogenChemicalShiftListMapByHoseCode(int theHoseCodeLevel) {
        this.setChemicalShiftListMapByHoseCode(new HashMap<String, List<Double>>());

        for (int li = 0, lEnd = this.getNMRShiftDB().getHydrogenPeakListByMolecules().size(); li < lEnd; li++) {
            String[] theHoseCodeArray = this.getNMRShiftDB().getMoleculeSet().getMolecule(li).getProperty(this.HOSE_CODE_KEY + theHoseCodeLevel).toString().split(this.TAB_STRING);

            this.__setHydrogenChemicalShiftListMapByHoseCode(theHoseCodeArray, this.getNMRShiftDB().getHydrogenPeakListByMolecules().get(li), li);
        }

        return this.getChemicalShiftListMapByHoseCode();
    }

    private void __setHydrogenChemicalShiftListMapByHoseCode(String[] theHoseCodeArray, List<Nmr1dUnitList> thePeak2dList, int li) {
        for (Nmr1dUnitList thePeakList : thePeak2dList) {
            for (Nmr1dUnit thePeak : thePeakList.getPeakList()) {
                if (thePeak.getAnnotatedAtomNumber() != -1) {
                    this.__setHydrogenChemicalShiftListMapByHoseCode(thePeak, theHoseCodeArray[thePeak.getAnnotatedAtomNumber()], li);
                }
            }
        }
    }

    private void __setHydrogenChemicalShiftListMapByHoseCode(Nmr1dUnit thePeak, String theHoseCode, int li) {
        Set<String> theKeySet = this.getChemicalShiftListMapByHoseCode().keySet();

        if (theKeySet.contains(theHoseCode)) {
            this.__setHydrogenChemicalShiftListMapByExistedHoseCode(thePeak, theHoseCode);
        } else {
            this.__setHydrogenChemicalShiftListMapByNonexsitedHoseCode(thePeak, theHoseCode);
        }
        
        if(theHoseCode.equals("H-1;O")) {
            System.out.println(thePeak);
            System.out.println(this.getNMRShiftDB().getMoleculeSet().getAtomContainer(li).getProperty("nmrshiftdb2 ID"));
            System.out.println();
        }
    }

    private void __setHydrogenChemicalShiftListMapByExistedHoseCode(Nmr1dUnit thePeak, String theHoseCode) {
        List<Double> theChemicalShiftList = this.getChemicalShiftListMapByHoseCode().get(theHoseCode);

        theChemicalShiftList.add(thePeak.getChemicalShift());
        this.setChemicalShiftListMapByHoseCode().put(theHoseCode, theChemicalShiftList);
    }

    private void __setHydrogenChemicalShiftListMapByNonexsitedHoseCode(Nmr1dUnit thePeak, String theHoseCode) {
        List<Double> theChemicalShiftList = new ArrayList<>();

        theChemicalShiftList.add(thePeak.getChemicalShift());
        this.setChemicalShiftListMapByHoseCode().put(theHoseCode, theChemicalShiftList);
    }
}
