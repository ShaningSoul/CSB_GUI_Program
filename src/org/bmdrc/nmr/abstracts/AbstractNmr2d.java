/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.bmdrc.nmr.abstracts;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import org.bmdrc.nmr.Nmr1dUnit;
import org.bmdrc.nmr.Nmr2dCHUnit;
import org.bmdrc.nmr.Nmr2dCHUnitList;
import org.bmdrc.nmr.Nmr2dUnit;

/**
 *
 * @author labwindows
 */
public abstract class AbstractNmr2d extends AbstractNmr implements Serializable{
    private static final long serialVersionUID = 7542606196164622426L;

    private Nmr2dCHUnitList itsPeak;
    private Nmr2dCHUnitList itsMedianPeak;
    private List<Nmr1dUnit> itsNmr1H;
    private List<Nmr1dUnit> itsNmr13C;
    private final Double TOLERENCE_OF_CARBON = 1000.0 / this.getNMRResolution();
    private final Double TOLERENCE_OF_HYDROGEN = 40.0 / this.getNMRResolution();
    private final double MAXIMUM_CARBON_NMR_CHEMICAL_SHIFT = 200.0;
    private final double MAXIMUM_HYDROGEN_NMR_CHEMICAL_SHIFT = 12.0;
    private final int SPARKY_CARBON_SHIFT_INDEX = 0;
    private final int SPARKY_HYDROGEN_SHIFT_INDEX = 1;
    private final int SPARKY_INTENSITY_INDEX = 2;
    private final int ACDLAB_CARBONC_SHIFT_INDEX = 2;
    private final int ACDLAB_HYDROGEN_SHIFT_INDEX = 1;
    private final int ACDLAB_INTENSITY_INDEX = 5;
    private final int ACDLAB_LEAST_OF_NUMBER_OF_TABLES = 8;
    private final double MAXIMUM_DISTANCE_IN_NMR_2D = Math.sqrt(2.0);

    public Nmr2dCHUnitList getPeak() {
        return itsPeak;
    }

    public void setPeak(Nmr2dCHUnitList thePeak) {
        this.itsPeak = thePeak;
    }

    public Nmr2dCHUnitList setPeak() {
        return itsPeak;
    }

    public Nmr2dCHUnitList getMedianPeak() {
        return itsMedianPeak;
    }

    public void setMedianPeak(Nmr2dCHUnitList theMedianPeak) {
        this.itsMedianPeak = theMedianPeak;
    }

    public Nmr2dCHUnitList setMedianPeak() {
        return itsMedianPeak;
    }
    
    public void fromSpark(String theSparkFile) {
        List<String> theLines = this.__readLines(theSparkFile);
        this.setPeak(new Nmr2dCHUnitList());

        this.__removeIndexLine(theLines);
        this.__addPeakDataFromSparky(theLines);
    }

    public List<Nmr1dUnit> getNmr1H() {
        return itsNmr1H;
    }

    public void setNmr1H(List<Nmr1dUnit> theNmr1H) {
        this.itsNmr1H = theNmr1H;
    }

    public List<Nmr1dUnit> setNmr1H() {
        return itsNmr1H;
    }

    public List<Nmr1dUnit> getNmr13C() {
        return itsNmr13C;
    }

    public void setNmr13C(List<Nmr1dUnit> theNmr13C) {
        this.itsNmr13C = theNmr13C;
    }

    public List<Nmr1dUnit> setNmr13C() {
        return itsNmr13C;
    }

    private List<String> __readLines(final String theInputFilePath) {
        List<String> theFileInformation = new java.util.ArrayList<String>();
        String theCheckString = null;

        if (theInputFilePath.length() == 0) {
            System.err.println("Check Input File!!");
            System.exit(1);
        }

        try {
            BufferedReader theFileReader = new BufferedReader(new FileReader(theInputFilePath));

            while ((theCheckString = theFileReader.readLine()) != null) {
                theFileInformation.add(theCheckString);
            }

            theFileReader.close();
        } catch (IOException e) {
            System.err.println(e);
            System.exit(1);
        }

        return theFileInformation;
    }

    private void __removeIndexLine(final List<String> theLines) {
        theLines.remove(0);
        theLines.remove(0);
    }

    private void __addPeakDataFromSparky(final List<String> theLines) {
        for (int i = 0; i < theLines.size(); i++) {
            if (theLines.get(i).trim().isEmpty()) {
                continue;
            }

            String[] theSplitedString = theLines.get(i).split(" ");
            List<Double> thePeakDataArray = new java.util.ArrayList<Double>();
            Nmr2dCHUnit thePeak = new Nmr2dCHUnit();

            for (int j = 0; j < theSplitedString.length; j++) {
                if (!theSplitedString[j].isEmpty() && !theSplitedString[j].contains("?-?")) {
                    thePeakDataArray.add(Double.parseDouble(theSplitedString[j].toString()));
                }
            }

            thePeak.setCarbonShift(thePeakDataArray.get(this.SPARKY_CARBON_SHIFT_INDEX));
            thePeak.setHydrogenShift(thePeakDataArray.get(this.SPARKY_HYDROGEN_SHIFT_INDEX));
            thePeak.setIntensity(thePeakDataArray.get(this.SPARKY_INTENSITY_INDEX));

            this.setPeak().addPeak(thePeak);
        }
    }

    public void fromACDLabFile(final String theInputFilePath) {
        List<String> theLines = this.__readLines(theInputFilePath);
        this.setPeak(new Nmr2dCHUnitList());

        this.__removeIndexLine(theLines);
        this.__addPeakDataFromACDLab(theLines);
    }

    private void __addPeakDataFromACDLab(final List<String> theLines) throws NumberFormatException {
        for (int i = 0; i < theLines.size(); i++) {
            String[] theSplitedString = theLines.get(i).split("\t");
            Nmr2dCHUnit thePeak = new Nmr2dCHUnit();

            if (theLines.get(i).trim().isEmpty() || theSplitedString.length < ACDLAB_LEAST_OF_NUMBER_OF_TABLES) {
                continue;
            }

            thePeak.setCarbonShift(Double.parseDouble(theSplitedString[ACDLAB_CARBONC_SHIFT_INDEX]));
            thePeak.setHydrogenShift(Double.parseDouble(theSplitedString[ACDLAB_HYDROGEN_SHIFT_INDEX]));
            thePeak.setIntensity(Double.parseDouble(theSplitedString[ACDLAB_INTENSITY_INDEX]));

            this.setPeak().addPeak(thePeak);
        }
    }

    public void generateMedianPeakList() {
        this.setMedianPeak(new Nmr2dCHUnitList());
        Nmr2dCHUnitList theCopiedPeakList = new Nmr2dCHUnitList(this.getPeak());

        for (Nmr1dUnit theNmr1HPeak : this.getNmr1H()) {
            for (Nmr1dUnit theNmr13CPeak : this.getNmr13C()) {
                this.__generatedMedianPeak(theCopiedPeakList, theNmr1HPeak, theNmr13CPeak);
            }
        }

        this.__removeNoisePeak();
    }

    private void __generatedMedianPeak(Nmr2dCHUnitList theCopiedPeakList, Nmr1dUnit theCriterion1HPeak, Nmr1dUnit theCriterion13CPeak) {
        Nmr2dCHUnit theCriterionPeakBasedOnNmr1D = new Nmr2dCHUnit();
        Nmr2dCHUnit theFirstPeakInGroup = new Nmr2dCHUnit();
        Nmr2dCHUnitList theClosePeakSet = new Nmr2dCHUnitList();

        theCriterionPeakBasedOnNmr1D.setPeak(theCriterion13CPeak.getChemicalShift(), theCriterion1HPeak.getChemicalShift());

        theFirstPeakInGroup = this.__getClosestPeak(theCriterionPeakBasedOnNmr1D);

        if (this.__isPeakInGroup(theFirstPeakInGroup, theCriterionPeakBasedOnNmr1D)) {
            this.__removePeakInList(theCopiedPeakList, theFirstPeakInGroup);
            theClosePeakSet = this.__searchPossiblePeakInGroup(theCopiedPeakList, theFirstPeakInGroup);
            this.setMedianPeak().addPeak(this.__calculateMedianPosition(theClosePeakSet));
        }
    }

    private void __removeNoisePeak() {
        for (int pi = this.getMedianPeak().size() - 1; pi >= 0; pi--) {
            if (this.__isNoisePeak(this.getMedianPeak().get(pi))) {
                this.getMedianPeak().remove(pi);
            }
        }
    }

    private boolean __isNoisePeak(Nmr2dCHUnit thePeak) {
        if (!this.__isMatchedNmr1HPeakList(thePeak) || !this.__isMatchedNmr13CPeakList(thePeak)) {
            return true;
        }

        return false;
    }

    private boolean __isMatchedNmr1HPeakList(Nmr2dCHUnit thePeak) {
        for (int hi = 0, hEnd = this.getNmr1H().size(); hi < hEnd; hi++) {
            if (this.__isPeakInGroupByNmr1H(this.getNmr1H().get(hi), thePeak)) {
                return true;
            }
        }

        return false;
    }

    private boolean __isMatchedNmr13CPeakList(Nmr2dCHUnit thePeak) {
        for (int ci = 0, cEnd = this.getNmr13C().size(); ci < cEnd; ci++) {
            if (this.__isPeakInGroupByNmr1H(this.getNmr13C().get(ci), thePeak)) {
                return true;
            }
        }

        return false;
    }

    private void __removePeakInList(Nmr2dCHUnitList thePeakList, Nmr2dCHUnit theRemovedPeak) {
        for (int pi = 0, pEnd = thePeakList.size(); pi < pEnd; pi++) {
            if (thePeakList.getPeak(pi).getCarbonShift() == theRemovedPeak.getCarbonShift() && thePeakList.getPeak(pi).getHydrogenShift() == theRemovedPeak.getHydrogenShift()) {
                thePeakList.remove(pi);
                break;
            }
        }
    }

    private Nmr2dCHUnit __getClosestPeak(Nmr2dCHUnit theCheckPeak) {
        Nmr2dCHUnit theClosestPeak = new Nmr2dCHUnit();
        double theMinimumDistance = MAXIMUM_DISTANCE_IN_NMR_2D;

        for (Nmr2dCHUnit thePeak : this.getPeak().getPeakList()) {
            if (!__isSamePeak(thePeak, theCheckPeak)) {
                double theDistance = Math.sqrt(Math.pow(thePeak.getCarbonShift() / this.MAXIMUM_CARBON_NMR_CHEMICAL_SHIFT - theCheckPeak.getCarbonShift() / this.MAXIMUM_CARBON_NMR_CHEMICAL_SHIFT, 2.0)
                        + Math.pow(thePeak.getHydrogenShift() / MAXIMUM_HYDROGEN_NMR_CHEMICAL_SHIFT - theCheckPeak.getHydrogenShift() / MAXIMUM_HYDROGEN_NMR_CHEMICAL_SHIFT, 2.0));

                if (theMinimumDistance > theDistance) {
                    theMinimumDistance = theDistance;
                    theClosestPeak = thePeak;
                }
            }
        }

        return theClosestPeak;
    }

    private boolean __isSamePeak(Nmr2dCHUnit theCriterionPeak, Nmr2dCHUnit theCheckPeak) {
        if (theCriterionPeak.getCarbonShift() == theCheckPeak.getCarbonShift() && theCriterionPeak.getHydrogenShift() == theCheckPeak.getHydrogenShift()) {
            return true;
        } else {
            return false;
        }
    }

    private boolean __isPeakInGroup(Nmr2dCHUnit theFirstPeakInGroup, Nmr2dCHUnit theCriterionPeak) {
        if (this.__isPeakInGroupByNmr13C(theFirstPeakInGroup, theCriterionPeak) && this.__isPeakInGroupByNmr1H(theFirstPeakInGroup, theCriterionPeak)) {
            return true;
        }

        return false;
    }

    private boolean __isPeakInGroupByNmr13C(Nmr2dCHUnit theFirstPeakInGroup, Nmr2dCHUnit theCriterionPeak) {
        if (Math.abs(theFirstPeakInGroup.getCarbonShift() - theCriterionPeak.getCarbonShift()) <= this.TOLERENCE_OF_CARBON) {
            return true;
        }

        return false;
    }

    private boolean __isPeakInGroupByNmr13C(Nmr1dUnit theFirstPeakInGroup, Nmr2dCHUnit theCriterionPeak) {
        if (Math.abs(theFirstPeakInGroup.getChemicalShift() - theCriterionPeak.getCarbonShift()) <= this.TOLERENCE_OF_CARBON) {
            return true;
        }

        return false;
    }

    private boolean __isPeakInGroupByNmr1HMultiplicity(Nmr2dCHUnit theFirstPeakInGroup, Nmr2dCHUnit theCriterionPeak) {
        if (theFirstPeakInGroup.getHydrogenShift() >= theCriterionPeak.getHydrogenMinOfRange() && theFirstPeakInGroup.getHydrogenShift() <= theCriterionPeak.getHydrogenMaxOfRange()) {
            return true;
        }

        return false;
    }

    private boolean __isPeakInGroupByNmr1HMultiplicity(Nmr1dUnit theNmr1HPeak, Nmr2dCHUnit theTestNmr2DPeak) {
        if (theNmr1HPeak.getMaxOfRange() >= theTestNmr2DPeak.getHydrogenMinOfRange() && theNmr1HPeak.getMinOfRange() <= theTestNmr2DPeak.getHydrogenMaxOfRange()) {
            return true;
        }

        return false;
    }

    private boolean __isPeakInGroupByNmr1H(Nmr2dCHUnit theFirstPeakInGroup, Nmr2dCHUnit theCriterionPeak) {
        if (this.__isPeakInGroupByNmr1HMultiplicity(theFirstPeakInGroup, theCriterionPeak)
                || Math.abs(theFirstPeakInGroup.getHydrogenShift() - theCriterionPeak.getHydrogenShift()) <= this.TOLERENCE_OF_HYDROGEN) {
            return true;
        }

        return false;
    }

    private boolean __isPeakInGroupByNmr1H(Nmr1dUnit theNmr1HPeak, Nmr2dCHUnit theCriterionPeak) {
        if (this.__isPeakInGroupByNmr1HMultiplicity(theNmr1HPeak, theCriterionPeak)
                || Math.abs(theNmr1HPeak.getChemicalShift() - theCriterionPeak.getHydrogenShift()) <= this.TOLERENCE_OF_HYDROGEN) {
            return true;
        }

        return false;
    }

    private Nmr2dCHUnitList __searchPossiblePeakInGroup(Nmr2dCHUnitList theCopiedPeak, Nmr2dCHUnit theFirstPeakInGroup) {
        Nmr2dCHUnitList thePeakSetInSameGroup = new Nmr2dCHUnitList();
        int theIndexOfStartPeak = this.__indexOfSamePeak(theCopiedPeak, theFirstPeakInGroup);
        boolean theJugmentContainingClusterMemberPeak = false;

        theCopiedPeak.remove(theIndexOfStartPeak);
        thePeakSetInSameGroup.addPeak(theFirstPeakInGroup);

        while (!theJugmentContainingClusterMemberPeak) {
            theJugmentContainingClusterMemberPeak = true;

            for (int pi = theCopiedPeak.size() - 1; pi >= 0; pi--) {
                if (this.__isClusterMember(thePeakSetInSameGroup, theCopiedPeak.get(pi))) {
                    thePeakSetInSameGroup.addPeak(theCopiedPeak.get(pi));
                    theCopiedPeak.remove(pi);
                    theJugmentContainingClusterMemberPeak = false;
                }
            }
        }

        return thePeakSetInSameGroup;
    }

    private int __indexOfSamePeak(Nmr2dCHUnitList theCriterionPeakSet, Nmr2dCHUnit theTestPeak) {
        for (int pi = 0, pEnd = theCriterionPeakSet.size(); pi < pEnd; pi++) {
            if (theCriterionPeakSet.get(pi).getCarbonShift() == theTestPeak.getCarbonShift() && theCriterionPeakSet.get(pi).getHydrogenShift() == theTestPeak.getHydrogenShift()) {
                return pi;
            }
        }

        return -1;
    }

    private boolean __isClusterMember(Nmr2dCHUnitList thePeakSetInSameGroup, Nmr2dCHUnit theTestPeak) {
        int theIndexOfMatchedCheckData = -1, theIndexOfMatchedPeakData = -1;

        if (!this.__isMatchcedSamePeakInNmr1HBetweenAllPeakOfGroupAndTestPeak(thePeakSetInSameGroup, theTestPeak)
                && !this.__isMatchcedSamePeakInNmr13CBetweenAllPeakOfGroupAndTestPeak(thePeakSetInSameGroup, theTestPeak)) {
            return false;
        }

        return true;
    }

    private boolean __isMatchcedSamePeakInNmr1HBetweenAllPeakOfGroupAndTestPeak(Nmr2dCHUnitList thePeakSetInSameGroup, Nmr2dCHUnit theTestPeak) {
        double theMinimumDistanceInCheckData = this.MAXIMUM_DISTANCE_IN_NMR_2D;
        double theMinimumDistanceInPeakData = this.MAXIMUM_DISTANCE_IN_NMR_2D;
        int theIndexOfMatchedCheckData = -1;
        int theIndexOfMatchedPeakData = -1;

        for (int pi = 0, pEnd = thePeakSetInSameGroup.size(); pi < pEnd; pi++) {
            for (int hi = 0, hEnd = this.getNmr1H().size(); hi < hEnd; hi++) {
                if (theMinimumDistanceInCheckData > this.__calculateDistance(this.getNmr1H().get(hi).getChemicalShift(), theTestPeak.getCarbonShift())
                        && this.__isPeakInGroupByNmr1HMultiplicity(this.getNmr1H().get(hi), theTestPeak)) {
                    theMinimumDistanceInCheckData = this.__calculateDistance(this.getNmr1H().get(hi).getChemicalShift(), theTestPeak.getCarbonShift());
                    theIndexOfMatchedCheckData = hi;
                }

                if (theMinimumDistanceInCheckData > this.__calculateDistance(this.getNmr1H().get(hi).getChemicalShift(), thePeakSetInSameGroup.get(pi).getCarbonShift())
                        && this.__isPeakInGroupByNmr1HMultiplicity(this.getNmr1H().get(hi), thePeakSetInSameGroup.get(pi))) {
                    theMinimumDistanceInPeakData = this.__calculateDistance(this.getNmr1H().get(hi).getChemicalShift(), thePeakSetInSameGroup.get(pi).getCarbonShift());
                    theIndexOfMatchedPeakData = hi;
                }
            }

            if ((theIndexOfMatchedCheckData != theIndexOfMatchedPeakData) || theIndexOfMatchedCheckData == -1 || theIndexOfMatchedPeakData == -1) {
                return false;
            }
        }

        return true;
    }

    private boolean __isMatchcedSamePeakInNmr13CBetweenAllPeakOfGroupAndTestPeak(Nmr2dCHUnitList thePeakSetInSameGroup, Nmr2dCHUnit theTestPeak) {
        double theMinimumDistanceInCheckData = this.MAXIMUM_DISTANCE_IN_NMR_2D;
        double theMinimumDistanceInPeakData = this.MAXIMUM_DISTANCE_IN_NMR_2D;
        int theIndexOfMatchedCheckData = -1;
        int theIndexOfMatchedPeakData = -1;

        for (int pi = 0, pEnd = thePeakSetInSameGroup.size(); pi < pEnd; pi++) {
            for (int ci = 0, cEnd = this.getNmr13C().size(); ci < cEnd; ci++) {
                if (theMinimumDistanceInCheckData > this.__calculateDistance(this.getNmr13C().get(ci).getChemicalShift(), theTestPeak.getCarbonShift())
                        && this.__isPeakInGroupByNmr13C(this.getNmr13C().get(ci), theTestPeak)) {
                    theMinimumDistanceInCheckData = this.__calculateDistance(this.getNmr13C().get(ci).getChemicalShift(), theTestPeak.getCarbonShift());
                    theIndexOfMatchedCheckData = ci;
                }

                if (theMinimumDistanceInPeakData > this.__calculateDistance(this.getNmr13C().get(ci).getChemicalShift(), thePeakSetInSameGroup.get(ci).getCarbonShift())
                        && this.__isPeakInGroupByNmr13C(this.getNmr13C().get(ci), thePeakSetInSameGroup.getPeak(ci))) {
                    theMinimumDistanceInPeakData = this.__calculateDistance(this.getNmr13C().get(ci).getChemicalShift(), thePeakSetInSameGroup.get(ci).getCarbonShift());
                    theIndexOfMatchedPeakData = ci;
                }
            }

            if ((theIndexOfMatchedCheckData != theIndexOfMatchedPeakData) || theIndexOfMatchedCheckData == -1) {
                return false;
            }
        }

        return true;
    }

    private Double __calculateDistance(double theFirstShift, double theSecondShift) {
        return Math.abs(theFirstShift - theSecondShift);
    }

    private Nmr2dCHUnit __calculateMedianPosition(Nmr2dCHUnitList thePeakList) {
        Nmr2dCHUnit theMedianPosition = new Nmr2dCHUnit();
        double theSumOfCarbonShift = 0;
        double theSumOfHydrogenShift = 0;
        int theSizeOfPeakList = thePeakList.size();

        for (Nmr2dCHUnit thePeak : thePeakList.toList()) {
            theSumOfCarbonShift += thePeak.getCarbonShift();
            theSumOfHydrogenShift += thePeak.getHydrogenShift();
        }

        theMedianPosition.setCarbonShift(theSumOfCarbonShift / (double) theSizeOfPeakList);
        theMedianPosition.setHydrogenShift(theSumOfHydrogenShift / (double) theSizeOfPeakList);

        return theMedianPosition;
    }
}
