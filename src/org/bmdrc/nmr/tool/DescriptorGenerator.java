/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.bmdrc.nmr.tool;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.bmdrc.chemistry.interfaces.IAtomRadius;
import org.bmdrc.interfaces.IStringConstant;
import org.bmdrc.nmr.Nmr1dUnit;
import org.bmdrc.nmr.Nmr1dUnitList;
import org.bmdrc.nmr.util.FunctionalGroupConstantVariable;
import org.bmdrc.chemistry.util.DistanceMatrix;
import org.bmdrc.util.TwoDimensionList;
import org.openscience.cdk.interfaces.IAtom;
import org.openscience.cdk.interfaces.IMolecule;

/**
 *
 * @author labwindows
 */
public class DescriptorGenerator implements IStringConstant, IAtomRadius, Serializable {
    private static final long serialVersionUID = -380066083793082117L;

    private NMRShiftDB itsNMRShiftDB;
    private List<String> itsDescriptorNameList;
    private TwoDimensionList<Double> itsDescriptorValue2dList;
    private List<String> itsFunctionalGroupList;
    private List<DistanceMatrix> itsDistanceMatrixList;
    private TwoDimensionList<String> itsFunctionalGroup2dListInMolecule;
    private List<Double> itsAtomRadiusList;
    private List<String> itsIndexList;
    private Set<String> itsTotalAtomSymbolSet;
    private Set<String> itsTotalSolvent;
    //constant string variable
    private final String NMRSHIFTID_KEY = "nmrshiftdb2 ID";
    private final String HOSE_CODE_KEY = "HOSE_CODE_";
    private final String FUNCTIONAL_GROUP_KEY = "Functional_Group";
    private final String INDEX_DESCRIPTOR = "INDEX";
    private final String CHEMICAL_SHIFT_DESCRIPTOR = "Chemical_Shift";
    private final String MPEOE_DESCRIPTOR = "MPEOE";
    private final String CDEAP_DESCRIPTOR = "CDEAP";
    private final String NEW_DESCRIPTOR_NAME = "Overlap_Ratio";
    //constant double variable
    private final double MAXIMUM_DISTANCE = 3.0;
    private final double MINIMUM_DISTANCE = 0.1;
    private final double INTERVAL = 0.1;
    private final double NOT_OVERLAP_VOLUME = 0.0;
    //constant int variable
    private final int NOT_CONTAIN_FUNCTIONAL_GROUP = -1;
    private final int FIRST_INDEX = 0;

    public DescriptorGenerator() {
        this.setNMRShiftDB(new NMRShiftDB());
        this.setDescriptorNameList(new ArrayList<String>());
        this.setDescriptorValue2dList(new TwoDimensionList<Double>());
        this.setIndexList(new ArrayList<String>());
        this.setTotalAtomSymbolSet(new HashSet<String>());
        this.__generateFunctionalGroupList();
        this.__generateDistanceMatrixList();
        this.__generateFunctionalGroup2dListInMolecule();
        this.__generateDescriptorNameList();
        this.__setAtomRadiusList(this.getAtomRadiusList());
    }

    public DescriptorGenerator(NMRShiftDB theNMRShiftDB) throws CloneNotSupportedException {
        this.setNMRShiftDB(new NMRShiftDB(theNMRShiftDB));
        this.setDescriptorNameList(new ArrayList<String>());
        this.setDescriptorValue2dList(new TwoDimensionList<Double>());
        this.setIndexList(new ArrayList<String>());
        this.setTotalAtomSymbolSet(new HashSet<String>());
        this.__generateFunctionalGroupList();
        this.__generateDistanceMatrixList();
        this.__generateFunctionalGroup2dListInMolecule();
        this.__generateDescriptorNameList();
        this.__setAtomRadiusList(this.getAtomRadiusList());
        //this.generateDescriptor();
    }

    public NMRShiftDB getNMRShiftDB() {
        return itsNMRShiftDB;
    }

    public void setNMRShiftDB(NMRShiftDB theNMRShiftDB) {
        this.itsNMRShiftDB = theNMRShiftDB;
        this.__generateFunctionalGroupList();
        this.__generateDistanceMatrixList();
        this.__generateFunctionalGroup2dListInMolecule();
    }

    public NMRShiftDB setNMRShiftDB() {
        return itsNMRShiftDB;
    }

    public List<String> getDescriptorNameList() {
        return itsDescriptorNameList;
    }

    public void setDescriptorNameList(List<String> theDescriptorNameList) {
        this.itsDescriptorNameList = theDescriptorNameList;
    }

    public List<String> setDescriptorNameList() {
        return itsDescriptorNameList;
    }

    public TwoDimensionList<Double> getDescriptorValue2dList() {
        return itsDescriptorValue2dList;
    }

    public void setDescriptorValue2dList(TwoDimensionList<Double> theDescriptorValue2dList) {
        this.itsDescriptorValue2dList = theDescriptorValue2dList;
    }

    public TwoDimensionList<Double> setDescriptorValue2dList() {
        return itsDescriptorValue2dList;
    }

    private List<String> __getFunctionalGroupList() {
        return itsFunctionalGroupList;
    }

    private void __setFunctionalGroupList(List<String> theFunctionalGroupList) {
        this.itsFunctionalGroupList = theFunctionalGroupList;
    }

    private List<String> __setFunctionalGroupList() {
        return itsFunctionalGroupList;
    }

    private List<DistanceMatrix> __getDistanceMatrixList() {
        return itsDistanceMatrixList;
    }

    private void __setDistanceMatrixList(List<DistanceMatrix> theDistanceMatrixList) {
        this.itsDistanceMatrixList = theDistanceMatrixList;
    }

    private List<DistanceMatrix> __setDistanceMatrixList() {
        return itsDistanceMatrixList;
    }

    private TwoDimensionList<String> __getFunctionalGroup2dListInMolecule() {
        return itsFunctionalGroup2dListInMolecule;
    }

    private void __setFunctionalGroup2dListInMolecule(TwoDimensionList<String> theFunctionalGroup2dListInMolecule) {
        this.itsFunctionalGroup2dListInMolecule = theFunctionalGroup2dListInMolecule;
    }

    private TwoDimensionList<String> __setFunctionalGroup2dListInMolecule() {
        return itsFunctionalGroup2dListInMolecule;
    }

    private List<Double> __getAtomRadiusList() {
        return itsAtomRadiusList;
    }

    private void __setAtomRadiusList(List<Double> itsAtomRadiusList) {
        this.itsAtomRadiusList = itsAtomRadiusList;
    }

    private List<Double> __setAtomRadiusList() {
        return itsAtomRadiusList;
    }

    public List<String> getIndexList() {
        return itsIndexList;
    }

    public void setIndexList(List<String> theIndexList) {
        this.itsIndexList = theIndexList;
    }

    public List<String> setIndexList() {
        return itsIndexList;
    }

    public Set<String> getTotalAtomSymbolSet() {
        return itsTotalAtomSymbolSet;
    }

    public void setTotalAtomSymbolSet(Set<String> theTotalAtomSymbolSet) {
        this.itsTotalAtomSymbolSet = theTotalAtomSymbolSet;
    }

    public Set<String> setTotalAtomSymbolSet() {
        return itsTotalAtomSymbolSet;
    }

    private void __generateFunctionalGroupList() {
        FunctionalGroupConstantVariable theFunctionalGroupList = new FunctionalGroupConstantVariable();
        this.__setFunctionalGroupList(theFunctionalGroupList.getFunctionalGroupList());
    }

    private void __generateDistanceMatrixList() {
        this.__setDistanceMatrixList(new ArrayList<DistanceMatrix>());

        for (int mi = 0, mEnd = this.getNMRShiftDB().getMoleculeSet().getMoleculeCount(); mi < mEnd; mi++) {
            this.__setDistanceMatrixList().add(new DistanceMatrix(this.getNMRShiftDB().getMoleculeSet().getMolecule(mi)));
        }
    }

    public void generateDescriptorInFunctionalGroup(String theFunctionalGroup) {
        this.setDescriptorValue2dList(new TwoDimensionList<Double>());
        this.setIndexList(new ArrayList<String>());
        this.__generateTotalAtomSymbolList();

        int theIndexOfMolecule = 0;

        System.out.println(theFunctionalGroup);
        for (List<Nmr1dUnitList> thePeak2dList : this.getNMRShiftDB().getHydrogenPeakListByMolecules().get2dList()) {
            TwoDimensionList<Double> theDescriptorList = this.__generateDescriptorInFunctionalGroup(thePeak2dList, theIndexOfMolecule, theFunctionalGroup);

            if (!theDescriptorList.isEmpty()) {
                this.setDescriptorValue2dList().addAll(theDescriptorList);
            }

            theIndexOfMolecule++;
        }
    }

    private TwoDimensionList<Double> __generateDescriptorInFunctionalGroup(List<Nmr1dUnitList> thePeak2dList, int theIndexOfMolecule, String theFunctionalGroup) {
        TwoDimensionList<Double> theDescriptorValue2dList = new TwoDimensionList<>();

        for (Nmr1dUnitList thePeakList : thePeak2dList) {
            TwoDimensionList<Double> theDescriptorList = this.__generateDescriptorInFunctionalGroup(thePeakList, theIndexOfMolecule, theFunctionalGroup);

            theDescriptorValue2dList.addAll(theDescriptorList);
        }

        return theDescriptorValue2dList;
    }

    private TwoDimensionList<Double> __generateDescriptorInFunctionalGroup(Nmr1dUnitList thePeakList, int theIndexOfMolecule, String theFunctionalGroup) {
        TwoDimensionList<Double> theDescriptorValue2dList = new TwoDimensionList<>();
        List<Integer> theUsedAtomNumberList = new ArrayList<>();

        for (Nmr1dUnit thePeak : thePeakList.getPeakList()) {
            if (this.getNMRShiftDB().getMoleculeSet().getMolecule(theIndexOfMolecule).getAtomCount() > thePeak.getAnnotatedAtomNumber()
                    && this.__isMatchFunctionalGroup(thePeak.getAnnotatedAtomNumber(), theIndexOfMolecule, theFunctionalGroup)
                    && !theUsedAtomNumberList.contains(thePeak.getAnnotatedAtomNumber())) {
                List<Double> theDescriptorList = this.__generateDescriptor(thePeak, theIndexOfMolecule);

                if (!theDescriptorList.isEmpty()) {
                    theDescriptorValue2dList.add(theDescriptorList);
                    this.setIndexList().add(this.getNMRShiftDB().getMoleculeSet().getMolecule(theIndexOfMolecule).getProperty(this.NMRSHIFTID_KEY).toString() + this.UNDER_BAR + thePeak.getAnnotatedAtomNumber());
                    theUsedAtomNumberList.add(thePeak.getAnnotatedAtomNumber());
                }
            }
        }

        return theDescriptorValue2dList;
    }

    private boolean __isMatchFunctionalGroup(int theAnnotatedAtomNumber, int theMoleculeIndex, String theFunctionalGroup) {
        IAtom theAnnotatedAtom = this.getNMRShiftDB().getMoleculeSet().getMolecule(theMoleculeIndex).getAtom(theAnnotatedAtomNumber);
        List<IAtom> theConnectedAtomList = this.getNMRShiftDB().getMoleculeSet().getMolecule(theMoleculeIndex).getConnectedAtomsList(theAnnotatedAtom);

        if (theConnectedAtomList.size() != 1) {
            return false;
        } else {
            int theIndexOfFunctionalGroup = this.getNMRShiftDB().getMoleculeSet().getMolecule(theMoleculeIndex).getAtomNumber(theConnectedAtomList.get(this.FIRST_INDEX));

            if (this.__getFunctionalGroup2dListInMolecule().get(theMoleculeIndex, theIndexOfFunctionalGroup).equals(theFunctionalGroup)) {
                return true;
            } else {
                return false;
            }
        }
    }

    public void generateDescriptor() {
        this.setDescriptorValue2dList(new TwoDimensionList<Double>());
        this.setIndexList(new ArrayList<String>());
        this.setDescriptorNameList(new ArrayList<String>());
        this.__generateTotalAtomSymbolList();

        int theIndexOfMolecule = 0;

        for (List<Nmr1dUnitList> thePeak2dList : this.getNMRShiftDB().getHydrogenPeakListByMolecules().get2dList()) {
            this.setDescriptorValue2dList().addAll(this.__generateDescriptor(thePeak2dList, theIndexOfMolecule));
            theIndexOfMolecule++;
        }
        
        System.out.println(this.setDescriptorValue2dList().size());
    }

    private void __generateFunctionalGroup2dListInMolecule() {
        this.__setFunctionalGroup2dListInMolecule(new TwoDimensionList<String>());
        FunctionalGroupGenerator theFunctionalGroupGenerator = new FunctionalGroupGenerator(this.getNMRShiftDB().getMoleculeSet());

        theFunctionalGroupGenerator.inputFunctionalGroupList();

        for (int mi = 0, mEnd = theFunctionalGroupGenerator.getMoleculeSet().getMoleculeCount(); mi < mEnd; mi++) {
            this.__setFunctionalGroup2dListInMolecule().add(this.__generateFunctionalGroup2dListInMolecule(theFunctionalGroupGenerator.getMoleculeSet().getMolecule(mi)));
        }
    }

    private List<String> __generateFunctionalGroup2dListInMolecule(IMolecule theMolecule) {
        List<String> theFunctionalGroupList = new ArrayList<>();
        String[] theSplitedFunctionalGroupList = theMolecule.getProperty(this.FUNCTIONAL_GROUP_KEY).toString().split(this.TAB_STRING);

        Collections.addAll(theFunctionalGroupList, theSplitedFunctionalGroupList);

        return theFunctionalGroupList;
    }

    private TwoDimensionList<Double> __generateDescriptor(List<Nmr1dUnitList> thePeak2dList, int theIndexOfMolecule) {
        TwoDimensionList<Double> theDescriptorValue2dList = new TwoDimensionList<>();

        for (Nmr1dUnitList thePeakList : thePeak2dList) {
            theDescriptorValue2dList.addAll(this.__generateDescriptor(thePeakList, theIndexOfMolecule));
        }

        return theDescriptorValue2dList;
    }

    private TwoDimensionList<Double> __generateDescriptor(Nmr1dUnitList thePeakList, int theIndexOfMolecule) {
        TwoDimensionList<Double> theDescriptorValue2dList = new TwoDimensionList<>();

        for (Nmr1dUnit thePeak : thePeakList.getPeakList()) {
            if (this.getNMRShiftDB().getMoleculeSet().getMolecule(theIndexOfMolecule).getAtomCount() > thePeak.getAnnotatedAtomNumber()) {
                List<Double> theDescriptorList = this.__generateDescriptor(thePeak, theIndexOfMolecule);

                if (!theDescriptorList.isEmpty()) {
                    theDescriptorValue2dList.add(theDescriptorList);
                }
            }
        }

        return theDescriptorValue2dList;
    }

    private List<Double> __generateDescriptor(Nmr1dUnit thePeak, int theIndexOfMolecule) {
        List<Double> theDescriptorList = new ArrayList<>();
        int theAnnotatedAtomNumber = thePeak.getAnnotatedAtomNumber();
        List<Double> theMolecularDescriptorList = this.__generateDescriptor(theAnnotatedAtomNumber, theIndexOfMolecule);

        if (!this.__containAllZero(theMolecularDescriptorList)) {
            theDescriptorList.add(thePeak.getChemicalShift());
            theDescriptorList.add(this.getNMRShiftDB().getMPEOEListByMolecules().get(theIndexOfMolecule, theAnnotatedAtomNumber));
            theDescriptorList.add(this.getNMRShiftDB().getCDEAPListByMolecules().get(theIndexOfMolecule, theAnnotatedAtomNumber));
            theDescriptorList.addAll(theMolecularDescriptorList);
            this.setIndexList().add(this.getNMRShiftDB().getMoleculeSet().getMolecule(theIndexOfMolecule).getProperty(this.NMRSHIFTID_KEY).toString() + this.UNDER_BAR + thePeak.getAnnotatedAtomNumber());
        }

        return theDescriptorList;
    }

    private boolean __containAllZero(List<Double> theList) {
        for (Double theValue : theList) {
            if (!theValue.equals(0.0)) {
                return false;
            }
        }

        return true;
    }

    private List<Double> __generateDescriptor(int theAnnotatedAtomNumber, int theIndexOfMolecule) {
        List<Double> theDescriptorList = this.__initializeDescriptorList();
        IAtom theAnnotatedAtom = this.getNMRShiftDB().getMoleculeSet().getMolecule(theIndexOfMolecule).getAtom(theAnnotatedAtomNumber);
        List<Double> theDistanceList = this.__getDistanceMatrixList().get(theIndexOfMolecule).getDistanceMatrix().get(theAnnotatedAtomNumber);
        Double theAnnotatedAtomRadius = this.__getAtomRadiusList().get(theAnnotatedAtom.getAtomicNumber());

        for (int ai = 0, aEnd = theDistanceList.size(); ai < aEnd; ai++) {
            IAtom theTestAtom = this.getNMRShiftDB().getMoleculeSet().getMolecule(theIndexOfMolecule).getAtom(ai);
            Double theTestAtomRadius = this.__getAtomRadiusList().get(theTestAtom.getAtomicNumber());
            List<Double> theTestList = new ArrayList<>();
            int theIndex = this.__getAtomSymbolIndex(this.getNMRShiftDB().getMoleculeSet().getMolecule(theIndexOfMolecule).getAtom(ai).getSymbol());

            for (Double li = this.MINIMUM_DISTANCE; li < this.MAXIMUM_DISTANCE; li += this.INTERVAL) {
                if (theDistanceList.get(ai) < theAnnotatedAtomRadius + theTestAtomRadius + li && theDistanceList.get(ai) != 0) {
                    int theDescriptorListIndex = 2 * this.getTotalAtomSymbolSet().size() * (int) (li / this.INTERVAL - 1) + (theIndex * 2);
                    
                    theDescriptorList.set(theDescriptorListIndex, theDescriptorList.get(theDescriptorListIndex)
                            + (this.getNMRShiftDB().getMPEOEListByMolecules().get(theIndexOfMolecule, ai) * this.__getRatioOverlappedVolume(theAnnotatedAtom, theTestAtom, li)));
                    theDescriptorList.set(theDescriptorListIndex + 1, theDescriptorList.get(theDescriptorListIndex + 1)
                            + (this.getNMRShiftDB().getCDEAPListByMolecules().get(theIndexOfMolecule, ai) * this.__getRatioOverlappedVolume(theAnnotatedAtom, theTestAtom, li)));
                } else {
                    break;
                }
            }
        }

        return theDescriptorList;
    }

    private Integer __getAtomSymbolIndex(String theAtomSymbol) {
        List<String> theAtomSymbolList = new ArrayList<>();

        for (String theSymbol : this.getTotalAtomSymbolSet()) {
            theAtomSymbolList.add(theSymbol);
        }

        return theAtomSymbolList.indexOf(theAtomSymbol);
    }

    private Double __getRatioOverlappedVolume(IAtom theAnnotatedAtom, IAtom theTestAtom, Double theStandardDistance) {
        return (this.__calculateOverlapVolume(theAnnotatedAtom, theTestAtom, theStandardDistance) - this.__calculateOverlapVolume(theAnnotatedAtom, theTestAtom, theStandardDistance - this.INTERVAL))
                / this.__calculateSphereVolume(this.__getAtomRadiusList().get(theTestAtom.getAtomicNumber()));
    }

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

    private Double __calculateOverlapVolume(IAtom theHydrogenAtom, IAtom theSecondAtom, Double theStandardDistance) {
        Double theHydrogenAtomRadius = this.__getAtomRadiusList().get(theHydrogenAtom.getAtomicNumber()) + theStandardDistance;
        Double theSecondAtomRadius = this.__getAtomRadiusList().get(theSecondAtom.getAtomicNumber());
        Double theDistanceBetweenTwoAtom = this.__getDistanceBetweenTwoAtoms(theHydrogenAtom, theSecondAtom);

        if (this.__isCorrectConditionInEquation(theHydrogenAtomRadius, theSecondAtomRadius, theDistanceBetweenTwoAtom)) {
            return Math.PI / (12 * theDistanceBetweenTwoAtom) * Math.pow(theHydrogenAtomRadius + theSecondAtomRadius - theDistanceBetweenTwoAtom, 2.0)
                    * (Math.pow(theDistanceBetweenTwoAtom, 2.0) + 2 * theDistanceBetweenTwoAtom * (theHydrogenAtomRadius + theSecondAtomRadius)
                    - 3 * Math.pow(theHydrogenAtomRadius - theSecondAtomRadius, 2.0));
        } else if (Math.abs(theHydrogenAtomRadius - theSecondAtomRadius) > theDistanceBetweenTwoAtom) {
            return this.__calculateSmallSphereVolume(theHydrogenAtomRadius, theSecondAtomRadius);
        } else if (theHydrogenAtomRadius + theSecondAtomRadius < theDistanceBetweenTwoAtom) {
            return this.NOT_OVERLAP_VOLUME;
        } else {
            return Double.NaN;
        }
    }

    private Double __getDistanceBetweenTwoAtoms(IAtom theFirstAtom, IAtom theSecondAtom) {
        if (theFirstAtom.getPoint3d() != null && theSecondAtom.getPoint3d() != null) {
            return theFirstAtom.getPoint3d().distance(theSecondAtom.getPoint3d());
        } else {
            return theFirstAtom.getPoint2d().distance(theSecondAtom.getPoint2d());
        }
    }

    private boolean __isCorrectConditionInEquation(Double theFirstAtomRadius, Double theSecondAtomRadius, double theDistance) {
        if (Math.abs(theFirstAtomRadius - theSecondAtomRadius) > theDistance) {
            return false;
        } else if (theFirstAtomRadius + theSecondAtomRadius < theDistance) {
            return false;
        }

        return true;
    }

    private Double __calculateSmallSphereVolume(Double theFirstAtomRadius, Double theSecondAtomRadius) {
        Double theMinimumRadius = theFirstAtomRadius;

        if (theSecondAtomRadius < theFirstAtomRadius) {
            theMinimumRadius = theSecondAtomRadius;
        }

        return this.__calculateSphereVolume(theMinimumRadius);
    }

    private Double __calculateSphereVolume(Double theRadius) {
        return 4 * Math.PI * Math.pow(theRadius, 3.0) / 3.0;
    }

    private List<Double> __initializeDescriptorList() {
        List<Double> theDescriptorList = new ArrayList<>();

        for (Double di = this.MINIMUM_DISTANCE; di < this.MAXIMUM_DISTANCE; di += this.INTERVAL) {
            for (int si = 0, sEnd = this.getTotalAtomSymbolSet().size(); si < sEnd; si++) {
                theDescriptorList.add(0.0);
                theDescriptorList.add(0.0);
            }
        }
        return theDescriptorList;
    }

    private void __generateDescriptorNameList() {
        this.__generateFunctionalGroupList();
        this.setDescriptorNameList(new ArrayList<String>());

        this.setDescriptorNameList().add(this.INDEX_DESCRIPTOR);
        this.setDescriptorNameList().add(this.CHEMICAL_SHIFT_DESCRIPTOR);
        this.setDescriptorNameList().add(this.MPEOE_DESCRIPTOR);
        this.setDescriptorNameList().add(this.CDEAP_DESCRIPTOR);

        for (Double li = this.MINIMUM_DISTANCE; li < this.MAXIMUM_DISTANCE; li += this.INTERVAL) {
            for (String theSymbol : this.getTotalAtomSymbolSet()) {
                //for(int ai = 0)
                this.setDescriptorNameList().add(this.MPEOE_DESCRIPTOR + this.UNDER_BAR + this.NEW_DESCRIPTOR_NAME + this.UNDER_BAR + theSymbol + this.UNDER_BAR + String.format("%.2f", li));
                this.setDescriptorNameList().add(this.CDEAP_DESCRIPTOR + this.UNDER_BAR + this.NEW_DESCRIPTOR_NAME + this.UNDER_BAR + theSymbol + this.UNDER_BAR + String.format("%.2f", li));
            }
        }
    }

    public void writeTotalDescriptor(File theResultFile, String theColumnSeparator) throws IOException {
        BufferedWriter theFileWriter = new BufferedWriter(new FileWriter(theResultFile));
        String theResultFileString = this.__writeDescriptorFileString(theColumnSeparator);

        theFileWriter.flush();
        theFileWriter.write(theResultFileString);
        theFileWriter.close();
    }

    private String __writeDescriptorFileString(String theColumnSeparator) {
        StringBuilder theStringBuilder = new StringBuilder();

        theStringBuilder.append(this.__writeDescriptorName(theColumnSeparator));

        for (int li = 0, lEnd = this.getDescriptorValue2dList().size(); li < lEnd; li++) {
            theStringBuilder.append(this.__writeDescriptorValueList(this.getDescriptorValue2dList().get(li), this.getIndexList().get(li), theColumnSeparator));
        }

        return theStringBuilder.toString();
    }

    private String __writeDescriptorName(String theColumnSeparator) {
        StringBuilder theStringBuilder = new StringBuilder();
        this.__generateDescriptorNameList();

        for (int ni = 0, nEnd = this.getDescriptorNameList().size(); ni < nEnd; ni++) {
            theStringBuilder.append(this.getDescriptorNameList().get(ni));

            if (ni < nEnd - 1) {
                theStringBuilder.append(theColumnSeparator);
            } else {
                theStringBuilder.append(this.END_LINE);
            }
        }

        return theStringBuilder.toString();
    }

    private String __writeDescriptorValueList(List<Double> theDescriptorValueList, String theIndex, String theColumnSeparator) {
        StringBuilder theStringBuilder = new StringBuilder();

        theStringBuilder.append(theIndex);

        for (int di = 0, dEnd = theDescriptorValueList.size(); di < dEnd; di++) {
            theStringBuilder.append(theColumnSeparator).append(theDescriptorValueList.get(di));
        }

        theStringBuilder.append(this.END_LINE);

        return theStringBuilder.toString();
    }

    private void __generateTotalAtomSymbolList() {
        this.setTotalAtomSymbolSet(new HashSet<String>());

        for (int mi = 0, mEnd = this.getNMRShiftDB().getMoleculeSet().getMoleculeCount(); mi < mEnd; mi++) {
            for (int ai = 0, aEnd = this.getNMRShiftDB().getMoleculeSet().getMolecule(mi).getAtomCount(); ai < aEnd; ai++) {
                this.setTotalAtomSymbolSet().add(this.getNMRShiftDB().getMoleculeSet().getMolecule(mi).getAtom(ai).getSymbol());
            }
        }
    }
}
