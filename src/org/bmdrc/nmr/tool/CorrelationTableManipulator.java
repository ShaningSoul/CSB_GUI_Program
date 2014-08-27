/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.bmdrc.nmr.tool;

import java.io.File;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.bmdrc.nmr.Nmr1dUnit;
import org.bmdrc.nmr.Nmr1dUnitList;
import org.bmdrc.util.SDFReader;
import org.bmdrc.util.SDFWriter;
import org.openscience.cdk.MoleculeSet;
import org.openscience.cdk.interfaces.IMolecule;
import org.openscience.cdk.interfaces.IMoleculeSet;

/**
 *
 * @author labwindows
 */
public class CorrelationTableManipulator implements Serializable{
    private static final long serialVersionUID = 6449952388099687109L;

    private IMoleculeSet itsMoleculeSet;
    private List<Nmr1dUnitList> itsHydrogenChemicalShiftDataInMoleculeSet;
    private Map<String, Nmr1dUnit> itsChemicalShiftDataByFunctionalGroup;
    private Map<String, Nmr1dUnit> itsReferenceCorrelationTable;
    private List<FunctionalGroupList> itsFunctionalGroupListInMoleculeSet;
    //constant int variable
    private final int MAXIMUM_SPECTRUM_NUMBER = 21;
    private final int INDEX_OF_CHEMICAL_SHIFT_IN_NMRSHIFTDB = 0;
    private final int INDEX_OF_ATOM_NUMBER_IN_NMRSHIFTDB = 2;
    private final int STARTING_OUTLIER_PROPERTY_VALUE_INDEX = 3;
    private final int MINIMUM_VALUE_INDEX = 0;
    private final int MAXIMUM_VALUE_INDEX = 1;
    private final int CHEMICAL_SHIFT_INDEX_IN_OUTLIER_PROPERTY = 0;
    //constant string variable in key value
    private final String KEY_VALUE_OF_13C_SPECTRUM = "Spectrum 13C ";
    private final String KEY_VALUE_OF_1H_SPECTRUM = "Spectrum 1H ";
    private final String KEY_VALUE_OF_SOLVENT = "Solvent";
    private final String KEY_VALUE_OF_FUNCTIONAL_GROUP = "Functional_Group";
    private final String KEY_VALUE_OF_OUTLIER_MOLECULE = "Outlier Molecule";
    private final String KEY_VALUE_OF_MATCHED_MOLECULE = "Matched Molecule";
    private final String KEY_VALUE_OF_NMRSHIFTDB2_ID = "nmrshiftdb2 ID";
    //constant string variable
    private final String STARTING_STRING_VALUE_OF_OUTLIER_PROPERTY = "[Shift,Atom Number] = ";
    private final String STARTING_STRING_VALUE_OF_MATCHED_PROPERTY = "[Shift,Atom Number] = ";
    private final String STARTING_STRING_VALUE_OF_OUTLIER_PROPERTY_IN_ONE_GROUP = "[";
    private final String STARTING_STRING_VALUE_OF_MATCHED_PROPERTY_IN_ONE_GROUP = "[";
    private final String END_STRING_OF_OUTLIER_PROPERTY_IN_ONE_GROUP = "] ";
    private final String END_STRING_OF_MATCHED_PROPERTY_IN_ONE_GROUP = "] ";
    private final String CRITERION_STRING_OF_SPLITING_SOLVENT = "[0-9]?:";
    private final String REGEX_DIVIDED_BETWEEN_EXPERIMENT_NUMBER_AND_TYPE_OF_SOLVENT = ":";
    private final String REGEX_OF_RATIO_INFORMATION = "\\s\\([0-9](:)[0-9]\\)";
    private final String REGEX_DIVIDED_PEAK_INFORMATION = "\\|";
    private final String REGEX_DIVIDED_BETWEEN_FUNCTIONAL_GROUPS = "\t";
    private final String REGEX_DIVIDED_OUTLIER_PROPERTY_BETWEEN_GROUPS = "\t";
    private final String EMPTY_STRING = "";
    private final String SD_FILE_SUFFIX = ".sd";
    private final String SPACE_STRING = "\\s";
    private final String COMMA_STRING = ",";

    public CorrelationTableManipulator() {
        this.setChemicalShiftDataByFunctionalGroup(new HashMap<String, Nmr1dUnit>());
        this.setMoleculeSet(new MoleculeSet());
        this.setHydrogenChemicalShiftDataInMoleculeSet(new ArrayList<Nmr1dUnitList>());
        this.setFunctionalGroupListInMoleculeSet(new ArrayList<FunctionalGroupList>());
        this.__generateReferenceCorrlationTable();
    }

    public CorrelationTableManipulator(IMoleculeSet theMoleculeSet) {
        this.setChemicalShiftDataByFunctionalGroup(new HashMap<String, Nmr1dUnit>());
        
        this.itsMoleculeSet = theMoleculeSet;
        this.__generateReferenceCorrlationTable();
        this.__openChemicalShiftDataInMoleculeSet();
        this.__openFunctionalGroupListInMoleculeSet();
    }

    public CorrelationTableManipulator(File theMoleculeFile) {
        this.setMoleculeSet(SDFReader.openMoleculeFile(theMoleculeFile));
        this.__generateReferenceCorrlationTable();
    }

    public IMoleculeSet getMoleculeSet() {
        return itsMoleculeSet;
    }

    public void setMoleculeSet(IMoleculeSet theMoleculeSet) {
        this.itsMoleculeSet = theMoleculeSet;
        this.__openChemicalShiftDataInMoleculeSet();
        this.__openFunctionalGroupListInMoleculeSet();
    }

    public IMoleculeSet setMoleculeSet() {
        return itsMoleculeSet;
    }

    public Map<String, Nmr1dUnit> getChemicalShiftDataByFunctionalGroup() {
        return itsChemicalShiftDataByFunctionalGroup;
    }

    public void setChemicalShiftDataByFunctionalGroup(Map<String, Nmr1dUnit> theChemicalShiftDataByFunctionalGroup) {
        this.itsChemicalShiftDataByFunctionalGroup = theChemicalShiftDataByFunctionalGroup;
    }

    public Map<String, Nmr1dUnit> setChemicalShiftDataByFunctionalGroup() {
        return itsChemicalShiftDataByFunctionalGroup;
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

    public List<Nmr1dUnitList> getHydrogenChemicalShiftDataInMoleculeSet() {
        return itsHydrogenChemicalShiftDataInMoleculeSet;
    }

    public void setHydrogenChemicalShiftDataInMoleculeSet(List<Nmr1dUnitList> theHydrogenChemicalShiftDataInMoleculeSet) {
        this.itsHydrogenChemicalShiftDataInMoleculeSet = theHydrogenChemicalShiftDataInMoleculeSet;
    }

    public List<Nmr1dUnitList> setHydrogenChemicalShiftDataInMoleculeSet() {
        return itsHydrogenChemicalShiftDataInMoleculeSet;
    }

    public Set<String> getAtomTypeListInReferenceCorrelationTable() {
        return this.getReferenceCorrelationTable().keySet();
    }

    public List<FunctionalGroupList> getFunctionalGroupListInMoleculeSet() {
        return itsFunctionalGroupListInMoleculeSet;
    }

    public void setFunctionalGroupListInMoleculeSet(List<FunctionalGroupList> theFunctionalGroupListInMoleculeSet) {
        this.itsFunctionalGroupListInMoleculeSet = theFunctionalGroupListInMoleculeSet;
    }

    public List<FunctionalGroupList> setFunctionalGroupListInMoleculeSet() {
        return itsFunctionalGroupListInMoleculeSet;
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

        //---------------------------------------------------- Start
        the1dPeak.setRange(1.0, 5.0);
        this.setReferenceCorrelationTable().put("PrimaryAmine", new Nmr1dUnit(the1dPeak));
        
        the1dPeak.setRange(1.0, 5.0);
        this.setReferenceCorrelationTable().put("SecondaryAmine", new Nmr1dUnit(the1dPeak));
        //---------------------------------------------------- End
        the1dPeak.setRange(5.0, 9.0);
        this.setReferenceCorrelationTable().put("Amide", new Nmr1dUnit(the1dPeak));
        //---------------------------------------------------- Start
        the1dPeak.setRange(5.0, 9.0);
        this.setReferenceCorrelationTable().put("Primary_Amide_N", new Nmr1dUnit(the1dPeak));
        
        the1dPeak.setRange(5.0, 9.0);
        this.setReferenceCorrelationTable().put("Secondary_Amide_N", new Nmr1dUnit(the1dPeak));
        //---------------------------------------------------- End
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
                    try{
                    theSolventIndexList.add(Integer.parseInt(theSplitedString[theSplitedString.length - 1]));
                    } catch(NumberFormatException e) {
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

    public void checkOutlierMolecule(List<String> theCheckingFunctionalGroupList, String theResultDir) {
        File theDir = new File(theResultDir);

        if (!theDir.exists()) {
            theDir.mkdir();
        }

        for (int ai = 0, aEnd = theCheckingFunctionalGroupList.size(); ai < aEnd; ai++) {
            String theOutlierFilePath = theResultDir + theCheckingFunctionalGroupList.get(ai) +"_F" + this.SD_FILE_SUFFIX;
            String theMatchedFilePath = theResultDir + theCheckingFunctionalGroupList.get(ai) +"_T" + this.SD_FILE_SUFFIX;
            IMoleculeSet theOutlierMoleculeSet = this.__getOutlierMoleculeSetInOneFunctionalGroup(theCheckingFunctionalGroupList.get(ai));
            IMoleculeSet theMatchedMoleculeSet = this.__getMatchedMoleculeSetInOneFunctionalGroup(theCheckingFunctionalGroupList.get(ai));

            if (!theOutlierMoleculeSet.isEmpty()) {
                List<Double> theMaximumAndMinimumShift = this.__getMaximumAndMinimumShift(theOutlierMoleculeSet);
                
                System.out.println(theCheckingFunctionalGroupList.get(ai) + " : " + theOutlierMoleculeSet.getMoleculeCount() + "\t[Min , Max] : [" + 
                        theMaximumAndMinimumShift.get(this.MINIMUM_VALUE_INDEX) + " , " + theMaximumAndMinimumShift.get(this.MAXIMUM_VALUE_INDEX) + "] / Correlation Table : [" +
                        this.getReferenceCorrelationTable().get(theCheckingFunctionalGroupList.get(ai)).getMinOfRange() + " , " + 
                        this.getReferenceCorrelationTable().get(theCheckingFunctionalGroupList.get(ai)).getMaxOfRange() + "]");
                
                SDFWriter.writeSDFile(theOutlierMoleculeSet, new File(theOutlierFilePath));
            }
            
            if(!theMatchedMoleculeSet.isEmpty()) {
                SDFWriter.writeSDFile(theMatchedMoleculeSet, new File(theMatchedFilePath));
            }
            
            this.__removeOutlierAndMatchedProperty();
        }
    }
    
    private void __removeOutlierAndMatchedProperty() {
        for(int mi = 0 , mEnd = this.getMoleculeSet().getMoleculeCount() ; mi < mEnd ; mi++) {
            if(this.getMoleculeSet().getMolecule(mi).getProperties().containsKey(this.KEY_VALUE_OF_OUTLIER_MOLECULE)) {
                this.getMoleculeSet().getMolecule(mi).removeProperty(this.KEY_VALUE_OF_OUTLIER_MOLECULE);
            }
            
            if(this.getMoleculeSet().getMolecule(mi).getProperties().containsKey(this.KEY_VALUE_OF_MATCHED_MOLECULE)) {
                this.getMoleculeSet().getMolecule(mi).removeProperty(this.KEY_VALUE_OF_MATCHED_MOLECULE);
            }
        }
    }
    
    private IMoleculeSet __getMatchedMoleculeSetInOneFunctionalGroup(String theFunctionalGroup) {
        IMoleculeSet theMatchedMoleculeSet = new MoleculeSet();
        
        for (int mi = 0, mEnd = this.getMoleculeSet().getMoleculeCount(); mi < mEnd; mi++) {
            if (this.__isMatchedMolecule(mi, theFunctionalGroup)) {
                this.__setMatchedProperty(mi, theFunctionalGroup);
                theMatchedMoleculeSet.addMolecule(this.getMoleculeSet().getMolecule(mi));
            }
        }
        
        return theMatchedMoleculeSet;
    }
    
    private List<Double> __getMaximumAndMinimumShift(IMoleculeSet theMoleculeSet) {
        List<Double> theMaximumAndMinimumShift = new ArrayList<>();
        Set<Double> theTotalShiftInformation = new HashSet<>();
        
        for(int mi = 0 , mEnd = theMoleculeSet.getMoleculeCount() ; mi < mEnd ; mi++) {
            String thePropertyValue = theMoleculeSet.getMolecule(mi).getProperty(this.KEY_VALUE_OF_OUTLIER_MOLECULE).toString();
            theTotalShiftInformation.addAll(this.__getChemicalShiftListAtOutlierMolecule(thePropertyValue));
        }
        
        theMaximumAndMinimumShift.add(Collections.min(theTotalShiftInformation));
        theMaximumAndMinimumShift.add(Collections.max(theTotalShiftInformation));
        
        return theMaximumAndMinimumShift;
    }

    private List<Double> __getChemicalShiftListAtOutlierMolecule(String theOutlierInformation) {
        List<Double> theChemicalShiftListAtOutlier = new ArrayList<>();
        String[] theSplitedString = theOutlierInformation.split(this.SPACE_STRING);
        
        for(int ai = this.STARTING_OUTLIER_PROPERTY_VALUE_INDEX, aEnd = theSplitedString.length ; ai < aEnd ; ai++) {
            theChemicalShiftListAtOutlier.add(this.__getChemicalShiftInformation(theSplitedString[ai]));
        }
        
        return theChemicalShiftListAtOutlier;
    }
    
    private double __getChemicalShiftInformation(String theOutlierInformation) {
        String[] theSplitedString = theOutlierInformation.split(this.COMMA_STRING);
        
        return Double.parseDouble(theSplitedString[this.CHEMICAL_SHIFT_INDEX_IN_OUTLIER_PROPERTY].substring(this.STARTING_STRING_VALUE_OF_OUTLIER_PROPERTY_IN_ONE_GROUP.length()));
    }
    
    private IMoleculeSet __getOutlierMoleculeSetInOneFunctionalGroup(String theFunctionalGroup) {
        IMoleculeSet theOutlierMoleculeSet = new MoleculeSet();
        
        for (int mi = 0, mEnd = this.getMoleculeSet().getMoleculeCount(); mi < mEnd; mi++) {
            if (this.__isOutlierMolecule(mi, theFunctionalGroup)) {
                this.__setOutlierProperty(mi, theFunctionalGroup);
                theOutlierMoleculeSet.addMolecule(this.getMoleculeSet().getMolecule(mi));
            }
        }

        return theOutlierMoleculeSet;
    }

    private boolean __isOutlierMolecule(int theMoleculeIndex, String theFunctionalGroup) {
        Nmr1dUnitList theCheckPeakList = this.__getPeakListInMoleculeContainFunctionalGroup(theMoleculeIndex, theFunctionalGroup);
        Nmr1dUnit theShiftRangeInReferenceCorrelationTable = this.getReferenceCorrelationTable().get(theFunctionalGroup);

        for (int li = 0, lEnd = theCheckPeakList.getPeakList().size(); li < lEnd; li++) {
            if (!this.__isShiftInRange(theCheckPeakList.getPeak(li), theFunctionalGroup)) {
                return true;
            }
        }

        return false;
    }

    private boolean __isMatchedMolecule(int theMoleculeIndex, String theFunctionalGroup) {
        Nmr1dUnitList theCheckPeakList = this.__getPeakListInMoleculeContainFunctionalGroup(theMoleculeIndex, theFunctionalGroup);
        Nmr1dUnit theShiftRangeInReferenceCorrelationTable = this.getReferenceCorrelationTable().get(theFunctionalGroup);

        for (int li = 0, lEnd = theCheckPeakList.getPeakList().size(); li < lEnd; li++) {
            if (this.__isShiftInRange(theCheckPeakList.getPeak(li), theFunctionalGroup)) {
                return true;
            }
        }

        return false;
    }
    
    private void __setMatchedProperty(int theMoleculeIndex, String theFunctionalGroup) {
        Nmr1dUnitList theCheckPeakList = this.__getPeakListInMoleculeContainFunctionalGroup(theMoleculeIndex, theFunctionalGroup);
        StringBuilder thePropertyValue = new StringBuilder();

        thePropertyValue.append(this.STARTING_STRING_VALUE_OF_MATCHED_PROPERTY);

        for (int li = 0, lEnd = theCheckPeakList.getPeakList().size(); li < lEnd; li++) {
            if (this.__isShiftInRange(theCheckPeakList.getPeak(li), theFunctionalGroup)) {
                thePropertyValue.append(this.STARTING_STRING_VALUE_OF_MATCHED_PROPERTY_IN_ONE_GROUP).append(theCheckPeakList.getPeak(li).getChemicalShift())
                        .append(this.COMMA_STRING).append(theCheckPeakList.getPeak(li).getAnnotatedAtomNumber()).append(this.END_STRING_OF_MATCHED_PROPERTY_IN_ONE_GROUP);
            }
        }

        this.getMoleculeSet().getMolecule(theMoleculeIndex).setProperty(this.KEY_VALUE_OF_MATCHED_MOLECULE, thePropertyValue);
    }
    
    private void __setOutlierProperty(int theMoleculeIndex, String theFunctionalGroup) {
        Nmr1dUnitList theCheckPeakList = this.__getPeakListInMoleculeContainFunctionalGroup(theMoleculeIndex, theFunctionalGroup);
        StringBuilder thePropertyValue = new StringBuilder();

        thePropertyValue.append(this.STARTING_STRING_VALUE_OF_OUTLIER_PROPERTY);

        for (int li = 0, lEnd = theCheckPeakList.getPeakList().size(); li < lEnd; li++) {
            if (!this.__isShiftInRange(theCheckPeakList.getPeak(li), theFunctionalGroup)) {
                thePropertyValue.append(this.STARTING_STRING_VALUE_OF_OUTLIER_PROPERTY_IN_ONE_GROUP).append(theCheckPeakList.getPeak(li).getChemicalShift())
                        .append(this.COMMA_STRING).append(theCheckPeakList.getPeak(li).getAnnotatedAtomNumber()).append(this.END_STRING_OF_OUTLIER_PROPERTY_IN_ONE_GROUP);
            }
        }

        this.getMoleculeSet().getMolecule(theMoleculeIndex).setProperty(this.KEY_VALUE_OF_OUTLIER_MOLECULE, thePropertyValue);
    }

    private Nmr1dUnitList __getPeakListInMoleculeContainFunctionalGroup(int theMoleculeIndex, String theFunctionalGroup) {
        Nmr1dUnitList thePeakList = new Nmr1dUnitList();
        List<Integer> theFunctionalGroupIndexListInMolecule = this.__getFunctionalGroupIndexListInMolecule(theMoleculeIndex, theFunctionalGroup);

        for (Integer theFunctionalGroupIndex : theFunctionalGroupIndexListInMolecule) {
            Nmr1dUnitList thePeakListInMoleculeContainFunctionalGroup = this.getHydrogenChemicalShiftDataInMoleculeSet().get(theMoleculeIndex)
                    .getPeakListUsingAnnotatedAtomNumber(theFunctionalGroupIndex);

            if (!thePeakListInMoleculeContainFunctionalGroup.isEmpty()) {
                thePeakList.addAllPeaks(thePeakListInMoleculeContainFunctionalGroup);
            }
        }

        return thePeakList;
    }

    private List<Integer> __getFunctionalGroupIndexListInMolecule(int theMoleculeIndex, String theFunctionalGroup) {
        List<Integer> theFunctionalGroupIndexListInMolecule = new ArrayList<>();
        String[] theFunctionalGroupDescriptorValueArray = this.getMoleculeSet().getMolecule(theMoleculeIndex).getProperty(this.KEY_VALUE_OF_FUNCTIONAL_GROUP).toString()
                .split(this.REGEX_DIVIDED_BETWEEN_FUNCTIONAL_GROUPS);

        for (int ai = 0, aEnd = theFunctionalGroupDescriptorValueArray.length; ai < aEnd; ai++) {
            if (theFunctionalGroupDescriptorValueArray[ai].equals(theFunctionalGroup)) {
                theFunctionalGroupIndexListInMolecule.add(ai);
            }
        }

        return theFunctionalGroupIndexListInMolecule;
    }

    private boolean __isShiftInRange(Nmr1dUnit thePeak, String theFunctionalGroup) {
        Nmr1dUnit theCriterionPeak = this.getReferenceCorrelationTable().get(theFunctionalGroup);

        try {
        if (thePeak.getChemicalShift() >= theCriterionPeak.getMinOfRange() && thePeak.getChemicalShift() <= theCriterionPeak.getMaxOfRange()) {
            return true;
        }
        } catch(NullPointerException e) {
            e.printStackTrace();
            System.err.println(theFunctionalGroup);
        }
        return false;
    }
}

