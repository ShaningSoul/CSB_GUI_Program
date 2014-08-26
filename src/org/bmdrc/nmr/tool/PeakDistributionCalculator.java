package org.bmdrc.nmr.tool;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.bmdrc.nmr.Nmr1dUnit;
import org.bmdrc.nmr.Nmr1dUnitList;
import org.bmdrc.util.SDFWriter;
import org.openscience.cdk.MoleculeSet;
import org.openscience.cdk.interfaces.IMolecule;
import org.openscience.cdk.interfaces.IMoleculeSet;

/**
 *
 * @author SungBo Hwang, CSB
 */
public class PeakDistributionCalculator {
    //class member variable

    private IMoleculeSet itsMoleculeSet;
    private String itsFunctionalGroup;
    private double itsInterval;
    private Map<String, Nmr1dUnit> itsReferenceCorrelationTable;
    private List<FunctionalGroupList> itsFunctionalGroupListInMoleculeSet;
    private List<Nmr1dUnitList> itsHydrogenChemicalShiftDataInMoleculeSet;
    private List<Integer> itsPeakDistributionList;
    //constant number variable
    private final int MAXIMUM_SPECTRUM_NUMBER = 21;
    private final int INDEX_OF_CHEMICAL_SHIFT_IN_NMRSHIFTDB = 0;
    private final int INDEX_OF_ATOM_NUMBER_IN_NMRSHIFTDB = 2;
    private final int INDEX_OF_MINIMUM_SHIFT_IN_RANGE = 0;
    private final int INDEX_OF_MAXIMUM_SHIFT_IN_RANGE = 1;
    private final double MINIMUM_CHEMICAL_SHIFT = 0;
    private final double MAXIMUM_CHEMICAL_SHIFT = 15;
    //constant string variable
    private final String CRITERION_STRING_OF_SPLITING_SOLVENT = "[0-9]?:";
    private final String KEY_VALUE_OF_1H_SPECTRUM = "Spectrum 1H ";
    private final String KEY_VALUE_OF_NMRSHIFTDB2_ID = "nmrshiftdb2 ID";
    private final String KEY_VALUE_OF_SOLVENT = "Solvent";
    private final String KEY_VALUE_OF_FUNCTIONAL_GROUP = "Functional_Group";
    private final String KEY_VALUE_OF_MOLECULE_IN_TOLERENCE = "MOLECULE_IN_TOLERENCE";
    private final String REGEX_OF_RATIO_INFORMATION = "\\s\\([0-9](:)[0-9]\\)";
    private final String REGEX_DIVIDED_PEAK_INFORMATION = "\\|";
    private final String REGEX_DIVIDED_BETWEEN_EXPERIMENT_NUMBER_AND_TYPE_OF_SOLVENT = ":";
    private final String REGEX_DIVIDED_BETWEEN_FUNCTIONAL_GROUPS = "\t";
    private final String SPACE_STRING = " ";
    private final String EMPTY_STRING = "";

    public PeakDistributionCalculator() {
        this.setMoleculeSet(new MoleculeSet());
        this.setFunctionalGroup(new String());
        this.setInterval(0);
        this.setHydrogenChemicalShiftDataInMoleculeSet(new ArrayList<Nmr1dUnitList>());
        this.setFunctionalGroupListInMoleculeSet(new ArrayList<FunctionalGroupList>());
        this.__generateReferenceCorrlationTable();
        this.setPeakDistributionList(new ArrayList<Integer>());
    }

    public PeakDistributionCalculator(IMoleculeSet theMoleculeSet, String theFunctionalGroup, double theInterval) {
        this.setMoleculeSet(theMoleculeSet);
        this.setFunctionalGroup(theFunctionalGroup);
        this.setInterval(theInterval);
        this.__openChemicalShiftDataInMoleculeSet();
        this.__openFunctionalGroupListInMoleculeSet();
        this.__generateReferenceCorrlationTable();
        this.__generatePeakDistribution();
    }

    public IMoleculeSet getMoleculeSet() {
        return itsMoleculeSet;
    }

    public void setMoleculeSet(IMoleculeSet theMoleculeSet) {
        this.itsMoleculeSet = theMoleculeSet;
        this.__openChemicalShiftDataInMoleculeSet();
        this.__openFunctionalGroupListInMoleculeSet();
        this.__generateReferenceCorrlationTable();
        this.__generatePeakDistribution();
    }

    public IMoleculeSet setMoleculeSet() {
        return itsMoleculeSet;
    }

    public String getFunctionalGroup() {
        return itsFunctionalGroup;
    }

    public void setFunctionalGroup(String theFunctionalGroup) {
        this.itsFunctionalGroup = theFunctionalGroup;
    }

    public String setFunctionalGroup() {
        return itsFunctionalGroup;
    }

    public List<Nmr1dUnitList> getHydrogenChemicalShiftDataInMoleculeSet() {
        return itsHydrogenChemicalShiftDataInMoleculeSet;
    }

    public void setHydrogenChemicalShiftDataInMoleculeSet(List<Nmr1dUnitList> itsHydrogenChemicalShiftDataInMoleculeSet) {
        this.itsHydrogenChemicalShiftDataInMoleculeSet = itsHydrogenChemicalShiftDataInMoleculeSet;
    }

    public List<Nmr1dUnitList> setHydrogenChemicalShiftDataInMoleculeSet() {
        return itsHydrogenChemicalShiftDataInMoleculeSet;
    }

    public double getInterval() {
        return itsInterval;
    }

    public void setInterval(double theInterval) {
        this.itsInterval = theInterval;
    }

    public Map<String, Nmr1dUnit> getReferenceCorrelationTable() {
        return itsReferenceCorrelationTable;
    }

    public void setReferenceCorrelationTable(Map<String, Nmr1dUnit> theReferenceCorrelationTable) {
        this.itsReferenceCorrelationTable = theReferenceCorrelationTable;
    }

    public Map<String, Nmr1dUnit> setReferenceCorrelationTable() {
        return itsReferenceCorrelationTable;
    }

    public List<Integer> getPeakDistributionList() {
        return itsPeakDistributionList;
    }

    public void setPeakDistributionList(List<Integer> itsPeakDistributionList) {
        this.itsPeakDistributionList = itsPeakDistributionList;
    }

    public List<Integer> setPeakDistributionList() {
        return itsPeakDistributionList;
    }

    public List<FunctionalGroupList> getFunctionalGroupListInMoleculeSet() {
        return itsFunctionalGroupListInMoleculeSet;
    }

    public void setFunctionalGroupListInMoleculeSet(List<FunctionalGroupList> itsFunctionalGroupListInMoleculeSet) {
        this.itsFunctionalGroupListInMoleculeSet = itsFunctionalGroupListInMoleculeSet;
    }

    public List<FunctionalGroupList> setFunctionalGroupListInMoleculeSet() {
        return itsFunctionalGroupListInMoleculeSet;
    }

    private void __generatePeakDistribution() {
        this.__initializePeakDistributionList();

        for (int li = 0, lEnd = this.getHydrogenChemicalShiftDataInMoleculeSet().size(); li < lEnd; li++) {
            try {
                this.__setPeakDistribution(li);
            } catch (IndexOutOfBoundsException e) {
                e.printStackTrace();
                System.err.println(this.getMoleculeSet().getMolecule(li).getProperty(this.KEY_VALUE_OF_NMRSHIFTDB2_ID));
            }
        }
    }

    private void __initializePeakDistributionList() {
        this.setPeakDistributionList(new ArrayList<Integer>());

        for (int ai = 0, aEnd = (int) ((this.MAXIMUM_CHEMICAL_SHIFT - this.MINIMUM_CHEMICAL_SHIFT) / this.getInterval()) + 1; ai < aEnd; ai++) {
            this.setPeakDistributionList().add(0);
        }
    }

    private void __setPeakDistribution(int theIndexOfMolecule) {
        for (Nmr1dUnit thePeak : this.getHydrogenChemicalShiftDataInMoleculeSet().get(theIndexOfMolecule).getPeakList()) {
            try {
                this.__setPeakDistribution(thePeak, theIndexOfMolecule);
            } catch (Exception e) {
                continue;
            }
        }
    }

    private void __setPeakDistribution(Nmr1dUnit thePeak, int theIndexOfMolecule) throws Exception {
        FunctionalGroupList theFunctionalGrouplist = this.getFunctionalGroupListInMoleculeSet().get(theIndexOfMolecule);
        int theAnnotatedAtomNumber = thePeak.getAnnotatedAtomNumber();

        if (theFunctionalGrouplist.getFunctionalGroupList().size() <= theAnnotatedAtomNumber) {
            throw new Exception();
        } else if (theFunctionalGrouplist.getFunctionalGroup(theAnnotatedAtomNumber).contains(this.getFunctionalGroup())) {
            int theIndexOfPeakDistribution = this.__getIndexOfPeakDistribution(thePeak.getChemicalShift());

            this.setPeakDistributionList().set(theIndexOfPeakDistribution, this.setPeakDistributionList().get(theIndexOfPeakDistribution) + 1);
        }
    }

    private int __getIndexOfPeakDistribution(double theChemicalShift) {
        return (int) (theChemicalShift / this.getInterval());
    }

    private void __generateReferenceCorrlationTable() {
        Nmr1dUnit the1dPeak = new Nmr1dUnit();

        this.setReferenceCorrelationTable(new HashMap<String, Nmr1dUnit>());

        the1dPeak.setRange(0.8, 1.0);
        this.setReferenceCorrelationTable().put("PrimaryAlkyl", new Nmr1dUnit(the1dPeak));

        the1dPeak.setRange(1.2, 1.4);
        this.setReferenceCorrelationTable().put("SecondaryAlkyl", new Nmr1dUnit(the1dPeak));

        the1dPeak.setRange(1.4, 1.7);
        this.setReferenceCorrelationTable().put("TertiaryAlkyl", new Nmr1dUnit(the1dPeak));

        the1dPeak.setRange(2.2, 2.5);
        this.setReferenceCorrelationTable().put("AromaticAlkyl", new Nmr1dUnit(the1dPeak));

        the1dPeak.setRange(3.0, 4.0);
        this.setReferenceCorrelationTable().put("AlkylChloride", new Nmr1dUnit(the1dPeak));

        the1dPeak.setRange(2.5, 4.0);
        this.setReferenceCorrelationTable().put("AlkylBromide", new Nmr1dUnit(the1dPeak));

        the1dPeak.setRange(2.5, 4.0);
        this.setReferenceCorrelationTable().put("AlkylIoidide", new Nmr1dUnit(the1dPeak));

        the1dPeak.setRange(4.0, 4.5);
        this.setReferenceCorrelationTable().put("AlkylFluoride", new Nmr1dUnit(the1dPeak));

        the1dPeak.setRange(3.3, 3.9);
        this.setReferenceCorrelationTable().put("Ether", new Nmr1dUnit(the1dPeak));

        the1dPeak.setRange(3.3, 3.9);
        this.setReferenceCorrelationTable().put("EsterROOCH2R", new Nmr1dUnit(the1dPeak));

        the1dPeak.setRange(0.5, 6.0);
        this.setReferenceCorrelationTable().put("Alchol", new Nmr1dUnit(the1dPeak));

        the1dPeak.setRange(3.3, 4.0);
        this.setReferenceCorrelationTable().put("CarbonBoundAlchol", new Nmr1dUnit(the1dPeak));

        the1dPeak.setRange(2.1, 2.6);
        this.setReferenceCorrelationTable().put("Ketone", new Nmr1dUnit(the1dPeak));

        the1dPeak.setRange(2.1, 2.6);
        this.setReferenceCorrelationTable().put("EsterRCH2COOR", new Nmr1dUnit(the1dPeak));

        the1dPeak.setRange(9.0, 10.0);
        this.setReferenceCorrelationTable().put("Aldehyde", new Nmr1dUnit(the1dPeak));

        the1dPeak.setRange(4.6, 5.0);
        this.setReferenceCorrelationTable().put("Alkene", new Nmr1dUnit(the1dPeak));

        the1dPeak.setRange(6.0, 9.5);
        this.setReferenceCorrelationTable().put("Benzyl", new Nmr1dUnit(the1dPeak));

        the1dPeak.setRange(10.0, 13.0);
        this.setReferenceCorrelationTable().put("Carboxylic", new Nmr1dUnit(the1dPeak));

        the1dPeak.setRange(4.0, 7.7);
        this.setReferenceCorrelationTable().put("Phenyl", new Nmr1dUnit(the1dPeak));

        the1dPeak.setRange(1.0, 5.0);
        this.setReferenceCorrelationTable().put("Amine", new Nmr1dUnit(the1dPeak));

        the1dPeak.setRange(5.0, 9.0);
        this.setReferenceCorrelationTable().put("Amide", new Nmr1dUnit(the1dPeak));

        the1dPeak.setRange(2.4, 3.1);
        this.setReferenceCorrelationTable().put("Alkynyl", new Nmr1dUnit(the1dPeak));

        the1dPeak.setRange(5.2, 5.7);
        this.setReferenceCorrelationTable().put("Vinylic", new Nmr1dUnit(the1dPeak));

        the1dPeak.setRange(1.6, 1.9);
        this.setReferenceCorrelationTable().put("Allylic", new Nmr1dUnit(the1dPeak));

        the1dPeak.setRange(3.3, 3.3);
        this.setReferenceCorrelationTable().put("Methoxy", new Nmr1dUnit(the1dPeak));
    }

    private void __openChemicalShiftDataInMoleculeSet() {
        this.setHydrogenChemicalShiftDataInMoleculeSet(new ArrayList<Nmr1dUnitList>());

        for (int mi = 0, mEnd = this.getMoleculeSet().getMoleculeCount(); mi < mEnd; mi++) {
            this.setHydrogenChemicalShiftDataInMoleculeSet().add(this.__inputHydrogenChemicalShiftDataInMolecule(this.getMoleculeSet().getMolecule(mi)));
        }
    }

    private Nmr1dUnitList __inputHydrogenChemicalShiftDataInMolecule(IMolecule theMolecule) {
        Nmr1dUnitList theSpectrumPeakList = new Nmr1dUnitList();

        for (int pi = 0; pi < this.MAXIMUM_SPECTRUM_NUMBER; pi++) {
            String theKeyValue = this.KEY_VALUE_OF_1H_SPECTRUM + pi;

            if (theMolecule.getProperties().keySet().contains(theKeyValue) && !this.__isPredictedPeak(theMolecule, pi)) {
                theSpectrumPeakList.addAllPeaks(this.__inputHydrogenChemicalShiftDataInOneSpectrum(theMolecule, theKeyValue, pi));
            }
        }

        return theSpectrumPeakList;
    }

    private Nmr1dUnitList __inputHydrogenChemicalShiftDataInOneSpectrum(IMolecule theMolecule, String theKey, int theSpectrumNumber) {
        String theSpectrumData = theMolecule.getProperty(theKey).toString();
        String[] thePeakInformationArrayInFile = theSpectrumData.split(this.REGEX_DIVIDED_PEAK_INFORMATION);
        List<String> theSolventList = this.__getSolventList(theMolecule);
        List<Integer> theSolventIndexList = this.__getSolventIndexList(theMolecule);
        Nmr1dUnitList thePeakList = new Nmr1dUnitList();

        for (String theSpectrumPeak : thePeakInformationArrayInFile) {
            String[] theSplitedInformation = theSpectrumPeak.split(";");
            Nmr1dUnit thePeak = new Nmr1dUnit();

            thePeak.setAnnotatedAtomNumber(Integer.parseInt(theSplitedInformation[this.INDEX_OF_ATOM_NUMBER_IN_NMRSHIFTDB]));
            thePeak.setChemicalShift(Double.parseDouble(theSplitedInformation[this.INDEX_OF_CHEMICAL_SHIFT_IN_NMRSHIFTDB]));
            try {
                thePeak.setSolvent(theSolventList.get(theSolventIndexList.indexOf(theSpectrumNumber)));
            } catch (ArrayIndexOutOfBoundsException e) {
                e.printStackTrace();
                System.err.println(theKey + " " + theSpectrumNumber + " " + theMolecule.getProperty(this.KEY_VALUE_OF_NMRSHIFTDB2_ID).toString());
            }
            thePeakList.addPeak(thePeak);
        }

        return thePeakList;
    }

    private boolean __isPredictedPeak(IMolecule theMolecule, int theSpectrumNumber) {
        List<Integer> theSolventIndexList = this.__getSolventIndexList(theMolecule);

        if (!theSolventIndexList.contains(theSpectrumNumber)) {
            return true;
        }

        return false;
    }

    private List<String> __getSolventList(IMolecule theMolecule) {
        List<String> theSolventList = new ArrayList<>();

        if (theMolecule.getProperties().keySet().contains(this.KEY_VALUE_OF_SOLVENT)) {
            String thePropertyValue = theMolecule.getProperty(this.KEY_VALUE_OF_SOLVENT).toString();
            String[] theSplitedInformation = thePropertyValue.split(this.CRITERION_STRING_OF_SPLITING_SOLVENT);
            Collections.addAll(theSolventList, theSplitedInformation);
            theSolventList.remove(0);
        }

        return theSolventList;
    }

    private String __removeRatioInformation(String thePropertyValue) {
        return thePropertyValue.replaceAll(this.REGEX_OF_RATIO_INFORMATION, this.EMPTY_STRING);
    }

    private List<Integer> __getSolventIndexList(IMolecule theMolecule) {
        List<Integer> theSolventIndexList = new ArrayList<>();

        if (theMolecule.getProperties().keySet().contains(this.KEY_VALUE_OF_SOLVENT)) {
            String thePropertyValue = theMolecule.getProperty(this.KEY_VALUE_OF_SOLVENT).toString();
            String[] theSplitedPropertyValueArray = this.__removeRatioInformation(thePropertyValue).split(this.REGEX_DIVIDED_BETWEEN_EXPERIMENT_NUMBER_AND_TYPE_OF_SOLVENT);

            for (int si = 0, sEnd = theSplitedPropertyValueArray.length - 1; si < sEnd; si++) {
                if (si == 0) {
                    theSolventIndexList.add(Integer.parseInt(theSplitedPropertyValueArray[si]));
                } else {
                    String[] theSplitedString = theSplitedPropertyValueArray[si].split(this.SPACE_STRING);
                    try {
                        theSolventIndexList.add(Integer.parseInt(theSplitedString[theSplitedString.length - 1]));
                    } catch (NumberFormatException e) {
                        e.printStackTrace();
                        System.err.println(theMolecule.getProperty(this.KEY_VALUE_OF_NMRSHIFTDB2_ID));
                    }
                }
            }
        }

        return theSolventIndexList;
    }

    private void __openFunctionalGroupListInMoleculeSet() {
        this.setFunctionalGroupListInMoleculeSet(new ArrayList<FunctionalGroupList>());

        for (int mi = 0, mEnd = this.getMoleculeSet().getMoleculeCount(); mi < mEnd; mi++) {
            FunctionalGroupList theFunctionalGroupList = new FunctionalGroupList();
            String[] theFunctionalGroupListInMolecule = this.getMoleculeSet().getMolecule(mi).getProperty(this.KEY_VALUE_OF_FUNCTIONAL_GROUP).toString()
                    .split(this.REGEX_DIVIDED_BETWEEN_FUNCTIONAL_GROUPS);

            theFunctionalGroupList.addAllFunctionalGroupArray(theFunctionalGroupListInMolecule);
            this.setFunctionalGroupListInMoleculeSet().add(theFunctionalGroupList);
        }
    }

    public void writePeakDistribution(String theOutputFilePath) throws IOException {
        StringBuilder thePeakDistributionString = new StringBuilder();
        StringBuilder theIndexString = new StringBuilder();
        StringBuilder theResultFileString = new StringBuilder();
        BufferedWriter theFileWriter = new BufferedWriter(new FileWriter(theOutputFilePath));

        for (int li = 0, lEnd = this.getPeakDistributionList().size(); li < lEnd; li++) {
            double theMinimumInRange = this.MINIMUM_CHEMICAL_SHIFT + (li * this.getInterval());
            double theMaximumInRange = this.MINIMUM_CHEMICAL_SHIFT + ((li + 1) * this.getInterval());

            thePeakDistributionString.append(String.format("%.2f", theMinimumInRange)).append("~").append(String.format("%.2f", theMaximumInRange)).append("\t");
            theIndexString.append(this.getPeakDistributionList().get(li)).append("\t");
        }

        theResultFileString.append(thePeakDistributionString.toString()).append("\n").append(theIndexString.toString());

        theFileWriter.flush();
        theFileWriter.write(theResultFileString.toString());
        theFileWriter.close();
    }

    public void writeMoleculeFileHavingNumberUnderTolerence(int theTolerence, File theResultFile) {
        Nmr1dUnitList theChemicalShiftRangeList = this.__getChemicalShiftRangeList(theTolerence);
        IMoleculeSet theMoleculeSet = this.__getMoleculeSetHavingNumberUnderTolerenceInPeakDistribution(theChemicalShiftRangeList);

        SDFWriter.writeSDFile(theMoleculeSet, theResultFile);
    }

    private Nmr1dUnitList __getChemicalShiftRangeList(int theTolerence) {
        Nmr1dUnitList theChemicalShiftRangeList = new Nmr1dUnitList();

        for (int di = 0, dEnd = this.getPeakDistributionList().size(); di < dEnd; di++) {
            if (this.getPeakDistributionList().get(di) <= theTolerence && this.getPeakDistributionList().get(di) != 0) {
                Nmr1dUnit theChemicalShiftRange = this.__setChemicalShiftRange(di);

                theChemicalShiftRangeList.addPeak(theChemicalShiftRange);
            }
        }

        return theChemicalShiftRangeList;
    }

    private Nmr1dUnit __setChemicalShiftRange(int theIndex) {
        Nmr1dUnit theChemicalShiftRange = new Nmr1dUnit();

        theChemicalShiftRange.setMinOfRange(this.MINIMUM_CHEMICAL_SHIFT + (this.getInterval() * theIndex));
        theChemicalShiftRange.setMaxOfRange(this.MINIMUM_CHEMICAL_SHIFT + (this.getInterval() * (theIndex + 1)));

        return theChemicalShiftRange;
    }

    private IMoleculeSet __getMoleculeSetHavingNumberUnderTolerenceInPeakDistribution(Nmr1dUnitList theChemicalShiftRangeList) {
        IMoleculeSet theMoleculeSet = new MoleculeSet();

        for (int hi = 0, hEnd = this.getHydrogenChemicalShiftDataInMoleculeSet().size(); hi < hEnd; hi++) {
            if (this.__isMoleculeInRange(theChemicalShiftRangeList, hi)) {
                this.getMoleculeSet().getMolecule(hi).setProperty(this.KEY_VALUE_OF_MOLECULE_IN_TOLERENCE, this.__getPropertyValueInMatchedShift(theChemicalShiftRangeList, hi));
                theMoleculeSet.addMolecule(this.getMoleculeSet().getMolecule(hi));
            }
        }

        return theMoleculeSet;
    }

    private String __getPropertyValueInMatchedShift(Nmr1dUnitList theRangeList, int theIndexOfShiftData) {
        List<Double> theMatchedShiftListInRange = this.__getMatchedShiftListInRange(theRangeList, theIndexOfShiftData);
        StringBuilder thePropertyValueInMatchedShift = new StringBuilder();

        for (Double theMatchedShift : theMatchedShiftListInRange) {
            thePropertyValueInMatchedShift.append(theMatchedShift).append(this.SPACE_STRING);
        }
        
        return thePropertyValueInMatchedShift.toString();
    }

    private List<Double> __getMatchedShiftListInRange(Nmr1dUnitList theRangeList, int theIndexOfShiftData) {
        List<Double> theMatchedShiftListInRange = new ArrayList<>();

        for (Nmr1dUnit thePeak : theRangeList.getPeakList()) {
            if (this.__isMoleculeInRange(thePeak, theIndexOfShiftData)) {
                theMatchedShiftListInRange.addAll(this.__getChemicalShiftListInRange(thePeak, theIndexOfShiftData));
            }
        }

        return theMatchedShiftListInRange;
    }

    private List<Double> __getChemicalShiftListInRange(Nmr1dUnit theRange, int theIndexOfShiftData) {
        List<Double> theChemicalShiftListInRange = new ArrayList<>();
        
        Nmr1dUnitList thePeakListInMolecule = this.getHydrogenChemicalShiftDataInMoleculeSet().get(theIndexOfShiftData);

        for (Nmr1dUnit thePeak : thePeakListInMolecule.getPeakList()) {
            if (this.__isMatchedFunctionalGroup(thePeak, theIndexOfShiftData) && this.__isMatchedInRange(thePeak, theRange)) {
                theChemicalShiftListInRange.add(thePeak.getChemicalShift());
            }
        }

        return theChemicalShiftListInRange;
    }
    
    private boolean __isMoleculeInRange(Nmr1dUnitList theRangeList, int theIndexOfShiftData) {
        for (Nmr1dUnit thePeak : theRangeList.getPeakList()) {
            if (this.__isMoleculeInRange(thePeak, theIndexOfShiftData)) {
                return true;
            }
        }

        return false;
    }

    private boolean __isMoleculeInRange(Nmr1dUnit theRange, int theIndexOfShiftData) {
        Nmr1dUnitList thePeakListInMolecule = this.getHydrogenChemicalShiftDataInMoleculeSet().get(theIndexOfShiftData);

        for (Nmr1dUnit thePeak : thePeakListInMolecule.getPeakList()) {
            if (this.__isMatchedFunctionalGroup(thePeak, theIndexOfShiftData) && this.__isMatchedInRange(thePeak, theRange)) {
                return true;
            }
        }

        return false;
    }

    private boolean __isMatchedFunctionalGroup(Nmr1dUnit thePeak, int theIndexOfShiftData) {
        FunctionalGroupList theFunctionalGroupList = this.getFunctionalGroupListInMoleculeSet().get(theIndexOfShiftData);

        if (theFunctionalGroupList.size() <= thePeak.getAnnotatedAtomNumber()) {
            return false;
        } else if (theFunctionalGroupList.get(thePeak.getAnnotatedAtomNumber()).contains(this.getFunctionalGroup())) {
            return true;
        }

        return false;
    }

    private boolean __isMatchedInRange(Nmr1dUnit thePeak, Nmr1dUnit theRange) {
        if (thePeak.getChemicalShift() >= theRange.getMinOfRange() && thePeak.getChemicalShift() <= theRange.getMaxOfRange()) {
            return true;
        }

        return false;
    }
}
