package org.bmdrc.mass.tool;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.bmdrc.interfaces.IStringConstant;
import org.bmdrc.mass.PeakList;
import org.bmdrc.mass.PeakUnit;
import org.openscience.cdk.DefaultChemObjectBuilder;
import org.openscience.cdk.MoleculeSet;
import org.openscience.cdk.interfaces.IAtomContainer;
import org.openscience.cdk.interfaces.IMolecule;
import org.openscience.cdk.interfaces.IMoleculeSet;
import org.openscience.cdk.io.iterator.IteratingMDLReader;

/**
 *
 * @author SungBo Hwang, CSB
 * @author Gi Beom Shin, CSB
 */
public class PeakMatcher implements IStringConstant, Serializable {
    private static final long serialVersionUID = 2443180864192011990L;

    private IMoleculeSet itsMoleculeSet;
    private PeakList itsPeakList;
    private Map<String, Double> itsAdductMap;
    private double itsTolerance;
    private final String PROPERTY_NAME_OF_PEAK = "peak";
    private final String PROPERTY_NAME_OF_ADDUCTINFO = "adductInfo";
    private final String PROPERTY_NAME_OF_INTENSITY = "peakIntensity";
    private final String PROPERTY_NAME_OF_EXACT_MASS = "exact mass";
    private final int PROPERTY_INDEX_OF_PEAK = 0;
    private final int PROPERTY_INDEX_OF_ADDUCTINFO = 1;
    private final int PROPERTY_INDEX_OF_INTENSITY = 2;
    private final int SIZE_OF_PROPERTY = 3;
    private final int INDEX_OF_ADDUCT_MOLECULAR_FORMULA = 0;
    private final int INDEX_OF_ADDUCT_MOLECULAR_WEIGHT = 1;

    /*
    *peakMatcher constructor
    *
    *
    */
    public PeakMatcher(File theMoleculeFile, File thePeakListFile, File theAdductFile, double theTolerance) throws FileNotFoundException, IOException {
        this.__getMoleculeFromSDF(theMoleculeFile);
        this.setPeakList().open(thePeakListFile, this.COMMA_REGEX);
        this.__openAdductFile(theAdductFile);
        this.itsTolerance = theTolerance;
    }
    
    public PeakMatcher(MoleculeSet theMoleculeSet, File thePeakListFile, File theAdductFile, double theTolerance) throws FileNotFoundException, IOException {
        this.__setMoleculeSet(theMoleculeSet);
        this.setPeakList().open(thePeakListFile, this.COMMA_REGEX);
        this.__openAdductFile(theAdductFile);
        this.itsTolerance = theTolerance;
    }

    public IMoleculeSet getMoleculeSet() {
        return itsMoleculeSet;
    }

    public void setMoleculeSet(IMoleculeSet theMoleculeSet) {
        this.itsMoleculeSet = theMoleculeSet;
    }
    public IMoleculeSet setMoleculeSet(){
        this.itsMoleculeSet = new MoleculeSet();
        return itsMoleculeSet;
    }

    public PeakList getPeakList() {
        return itsPeakList;
    }

    public void setPeakList(PeakList thePeakList) {
        this.itsPeakList = thePeakList;
    }

    public PeakList setPeakList() {
        this.itsPeakList = new PeakList();
        return itsPeakList;
    }

    public Map<String, Double> getAdductMap() {
        return itsAdductMap;
    }

    public void setAdductMap(Map<String, Double> theAdductMap) {
        this.itsAdductMap = theAdductMap;
    }

    public Map<String, Double> setAdductMap() {
        return itsAdductMap;
    }

    public double getTolerance() {
        return itsTolerance;
    }

    public void setTolerence(double theTolerance) {
        this.itsTolerance = theTolerance;
    }

    public IMoleculeSet matchBetweenMoleculeSetAndPeakList() {
        IMoleculeSet theMoleculeSet = new MoleculeSet();

        for (IAtomContainer theSingleMolecule : this.getMoleculeSet().molecules()) {
            if (this.__ismatchedBetweenMoleculeAndPeakList(theSingleMolecule)) {
                theMoleculeSet.addMolecule((IMolecule) theSingleMolecule);
            }
        }

        return theMoleculeSet;
    }

    private boolean __ismatchedBetweenMoleculeAndPeakList(IAtomContainer theSingleMolecule) {
        List<StringBuilder> thePropertyValueList = this.__initializePropertyValueList();

        for (PeakUnit theSinglePeak : this.getPeakList().getPeakList()) {
            this.__matchBetweenMoleculeAndPeak(theSingleMolecule, theSinglePeak, thePropertyValueList);
        }

        if (this.__isMatched(thePropertyValueList)) {
            theSingleMolecule.setProperty(this.PROPERTY_NAME_OF_PEAK, thePropertyValueList.get(this.PROPERTY_INDEX_OF_PEAK).toString());
            theSingleMolecule.setProperty(this.PROPERTY_NAME_OF_ADDUCTINFO, thePropertyValueList.get(this.PROPERTY_INDEX_OF_ADDUCTINFO).toString());
            theSingleMolecule.setProperty(this.PROPERTY_NAME_OF_INTENSITY, thePropertyValueList.get(this.PROPERTY_INDEX_OF_INTENSITY).toString());

            return true;
        }

        return false;
    }

    private void __matchBetweenMoleculeAndPeak(IAtomContainer theSingleMolecule, PeakUnit theSinglePeak, List<StringBuilder> thePropertyValueList) {
        for (String theKey : this.getAdductMap().keySet()) {
            if (this.__isExactMassMatchedWithPeak(theSingleMolecule, theSinglePeak.getWeight(), this.getAdductMap().get(theKey))) {
                thePropertyValueList.get(this.PROPERTY_INDEX_OF_PEAK).append(theSinglePeak.getWeight().toString() + "\n");
                thePropertyValueList.get(this.PROPERTY_INDEX_OF_ADDUCTINFO).append(this.getAdductMap().get(theKey) + "\n");
                thePropertyValueList.get(this.PROPERTY_INDEX_OF_INTENSITY).append(theSinglePeak.getIntensity().toString() + "\n");
            }
        }
    }

    private boolean __isMatched(List<StringBuilder> thePropertyValueList) {
        return thePropertyValueList.get(this.PROPERTY_INDEX_OF_PEAK).length() != 0;
    }

    private List<StringBuilder> __initializePropertyValueList() {
        List<StringBuilder> thePropertyValueList = new ArrayList<>();

        for (int li = 0, lEnd = this.SIZE_OF_PROPERTY; li < lEnd; li++) {
            thePropertyValueList.add(new StringBuilder());
        }

        return thePropertyValueList;
    }

    private boolean __isExactMassMatchedWithPeak(IAtomContainer theSingleMolecule, double theWeightInPeak, double theAdductMass) {
        return (Math.abs(theWeightInPeak - (Double) theSingleMolecule.getProperty(this.PROPERTY_NAME_OF_EXACT_MASS) - theAdductMass) < this.getTolerance());
    }

    private void __getMoleculeFromSDF(File theSdFile) {
        try {
            IteratingMDLReader reader = new IteratingMDLReader(
                    new FileInputStream(theSdFile),
                    DefaultChemObjectBuilder.getInstance());
            while (reader.hasNext()) {
                this.setMoleculeSet().addMolecule((IMolecule) reader.next());
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
    
    private void __setMoleculeSet(MoleculeSet theMolSet){
        this.setMoleculeSet().add(theMolSet);
    }
    
    private void __openAdductFile(File theAdductFile) throws FileNotFoundException, IOException {
        BufferedReader theFileReader = new BufferedReader(new FileReader(theAdductFile));
        String theFileString = new String();
        
        this.setAdductMap(new HashMap<String, Double>());
        
        while((theFileString = theFileReader.readLine()) != null) {
            String[] theSplitedString = theFileString.split("\t");
            
            this.setAdductMap().put(theSplitedString[this.INDEX_OF_ADDUCT_MOLECULAR_FORMULA], Double.parseDouble(theSplitedString[this.INDEX_OF_ADDUCT_MOLECULAR_WEIGHT]));
        }
    }
}
