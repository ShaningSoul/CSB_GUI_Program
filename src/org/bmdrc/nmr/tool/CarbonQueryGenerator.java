package org.bmdrc.nmr.tool;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import org.bmdrc.nmr.HMBC;
import org.bmdrc.nmr.HMQC;
import org.bmdrc.nmr.Nmr13C;
import org.bmdrc.nmr.Nmr1H;
import org.bmdrc.nmr.Nmr1dUnit;
import org.bmdrc.nmr.Nmr2dCHUnit;
import org.bmdrc.nmr.Nmr2dCHUnitList;
import org.bmdrc.util.Module;

/**
 *
 * @author SungBo Hwang, CSB
 */
public class CarbonQueryGenerator implements Serializable{
    private static final long serialVersionUID = -4323226004568064272L;

    //Input variable
    private HMBC itsHMBC;
    private HMQC itsHMQC;
    private Nmr1H itsNmr1H;
    private Nmr13C itsNmr13C;
    private int itsNumberOfCarbon;
    private int itsNumberOfHydrogen;
    private List<CarbonQuery> itsCarbonQueryInHydrogenBoundedCarbon;
    private List<CarbonQuery> itsCarbonQueryInHydrogenBoundedHeteroAtom;
    //variable used in class
    private Nmr2dCHUnitList itsMergedHmbcAndHmqc;
    private List<Nmr2dCHUnitList> itsGroupByCarbonShift;
    private List<Nmr2dCHUnitList> itsGroupByHydrogenShift;
    private List<Integer> itsIndexArrayOfCriterionPeakInCarbonGroup;
    private List<List<Integer>> itsIndexOfArrayOfMatchedCarbonPeakInNotHetero;
    //constant used in class
    private final int QUERY_SIZE_OF_HYDROGEN = 4;
    private final int QUERY_SIZE_OF_CARBON = 4;
    private final double MAXIMUM_DISTANCE_IN_NMR_2D = 1200;
    private final double MAXIMUM_CARBON_NMR_CHEMICAL_SHIFT = 200.0;
    private final double MAXIMUM_HYDROGEN_NMR_CHEMICAL_SHIFT = 12.0;

    public HMBC getHMBC() {
        return itsHMBC;
    }

    public void setHMBC(HMBC theHMBC) {
        this.itsHMBC = theHMBC;
    }

    public HMQC getHMQC() {
        return itsHMQC;
    }

    public void setHMQC(HMQC theHMQC) {
        this.itsHMQC = theHMQC;
    }

    public Nmr1H getNmr1H() {
        return itsNmr1H;
    }

    public void setItsNmr1H(Nmr1H theNmr1H) {
        this.itsNmr1H = theNmr1H;
    }

    public Nmr13C getNmr13C() {
        return itsNmr13C;
    }

    public void setNmr13C(Nmr13C theNmr13C) {
        this.itsNmr13C = theNmr13C;
    }

    public Nmr2dCHUnitList getMergedHmbcAndHmqc() {
        return itsMergedHmbcAndHmqc;
    }

    public void setMergedHmbcAndHmqc(Nmr2dCHUnitList theMergedHmbcAndHmqc) {
        this.itsMergedHmbcAndHmqc = theMergedHmbcAndHmqc;
    }

    public Nmr2dCHUnitList setMergedHmbcAndHmqc() {
        return itsMergedHmbcAndHmqc;
    }

    public int getNumberOfCarbon() {
        return itsNumberOfCarbon;
    }

    public void setNumberOfCarbon(int theNumberOfCarbon) {
        this.itsNumberOfCarbon = theNumberOfCarbon;
    }

    public int getNumberOfHydrogen() {
        return itsNumberOfHydrogen;
    }

    public void setNumberOfHydrogen(int theNumberOfHydrogen) {
        this.itsNumberOfHydrogen = theNumberOfHydrogen;
    }

    public List<CarbonQuery> getCarbonQueryInHydrogenBoundedCarbon() {
        return itsCarbonQueryInHydrogenBoundedCarbon;
    }

    public void setCarbonQueryInHydrogenBoundedCarbon(List<CarbonQuery> theCarbonQueryInHydrogenBoundedCarbon) {
        this.itsCarbonQueryInHydrogenBoundedCarbon = theCarbonQueryInHydrogenBoundedCarbon;
    }

    public List<CarbonQuery> setCarbonQueryInHydrogenBoundedCarbon() {
        return itsCarbonQueryInHydrogenBoundedCarbon;
    }

    public List<Integer> getIndexArrayOfCriterionPeakInCarbonGroup() {
        return itsIndexArrayOfCriterionPeakInCarbonGroup;
    }

    public void setIndexArrayOfCriterionPeakInCarbonGroup(List<Integer> theIndexArrayOfCriterionPeakInCarbonGroup) {
        this.itsIndexArrayOfCriterionPeakInCarbonGroup = theIndexArrayOfCriterionPeakInCarbonGroup;
    }

    public List<Nmr2dCHUnitList> getGroupByCarbonShift() {
        return itsGroupByCarbonShift;
    }

    public void setGroupByCarbonShift(List<Nmr2dCHUnitList> theGroupByCarbonShift) {
        this.itsGroupByCarbonShift = theGroupByCarbonShift;
    }

    public List<Nmr2dCHUnitList> setGroupByCarbonShift() {
        return itsGroupByCarbonShift;
    }

    public List<Nmr2dCHUnitList> getGroupByHydrogenShift() {
        return itsGroupByHydrogenShift;
    }

    public void setGroupByHydrogenShift(List<Nmr2dCHUnitList> theGroupByHydrogenShift) {
        this.itsGroupByHydrogenShift = theGroupByHydrogenShift;
    }

    public List<Nmr2dCHUnitList> setGroupByHydrogenShift() {
        return itsGroupByHydrogenShift;
    }

    public List<List<Integer>> getIndexOfArrayOfMatchedCarbonPeakInNotHetero() {
        return itsIndexOfArrayOfMatchedCarbonPeakInNotHetero;
    }

    public void setIndexOfArrayOfMatchedCarbonPeakInNotHetero(List<List<Integer>> theIndexOfArrayOfMatchedCarbonPeakInNotHetero) {
        this.itsIndexOfArrayOfMatchedCarbonPeakInNotHetero = theIndexOfArrayOfMatchedCarbonPeakInNotHetero;
    }

    public List<List<Integer>> setIndexOfArrayOfMatchedCarbonPeakInNotHetero() {
        return itsIndexOfArrayOfMatchedCarbonPeakInNotHetero;
    }

    public List<CarbonQuery> getCarbonQueryInHydrogenBoundedHeteroAtom() {
        return itsCarbonQueryInHydrogenBoundedHeteroAtom;
    }

    public void setCarbonQueryInHydrogenBoundedHeteroAtom(List<CarbonQuery> theCarbonQueryInHydrogenBoundedHeteroAtom) {
        this.itsCarbonQueryInHydrogenBoundedHeteroAtom = theCarbonQueryInHydrogenBoundedHeteroAtom;
    }

    public List<CarbonQuery> setCarbonQueryInHydrogenBoundedHeteroAtom() {
        return itsCarbonQueryInHydrogenBoundedHeteroAtom;
    }

    public void generateMergedHmbcAndHmqc() {
        List<Nmr2dCHUnit> theMedianPeak = new ArrayList<>();

        this.getHMBC().generateMedianPeakList();
        this.getHMQC().generateMedianPeakList();

        this.setMergedHmbcAndHmqc().addPeakList(this.getHMBC().getMedianPeak());
        this.setMergedHmbcAndHmqc().addPeakList(this.getHMQC().getMedianPeak());
    }

    public void generateCarbonQuery() {
        Nmr2dCHUnitList theCriterionPeakList = new Nmr2dCHUnitList();
        List<Integer> theNumberOfBoundHydrogenList = new ArrayList<>();

        this.__generateGroupByCarbonShift();
        this.__generateGroupByHydrogenShift();

        theCriterionPeakList = this.__generateCriterionPeakList();
        theNumberOfBoundHydrogenList = this.__getNumberOfBoundHydrogenList(theCriterionPeakList);

        this.setCarbonQueryInHydrogenBoundedCarbon(this.__generateCarbonQueryListInHydrogenBoundedCarbon(theCriterionPeakList, theNumberOfBoundHydrogenList));
        this.setCarbonQueryInHydrogenBoundedHeteroAtom(this.__generateCarbonQueryListInHydrogenBoundedHeteroAtom(theCriterionPeakList, theNumberOfBoundHydrogenList));
    }

    private void __generateGroupByCarbonShift() {
        this.setGroupByCarbonShift(new ArrayList<Nmr2dCHUnitList>());

        this.__initializeGroupByCarbonShift(this.getNmr13C().getPeakData().size());
        this.__determineGroupByCarbonShift();
        this.__removeEmptyGroupInCarbonShift();

    }

    private void __initializeGroupByCarbonShift(int theSizeOfArray) {
        for (int pi = 0; pi < theSizeOfArray; pi++) {
            this.setGroupByCarbonShift().add(new Nmr2dCHUnitList());
        }
    }

    private void __determineGroupByCarbonShift() {
        for (int pi = 0, pEnd = this.getMergedHmbcAndHmqc().getPeakList().size(); pi < pEnd; pi++) {
            double theMinimumDistance = -1;
            int theIndexOfTypeOfPeak = -1;

            for (int ci = 0, cEnd = this.getNmr13C().getPeakData().size(); ci < cEnd; ci++) {
                if (ci == 0 || theMinimumDistance > this.__calculateDistanceByCarbonShift(this.getNmr13C().getPeakData().get(ci), this.getMergedHmbcAndHmqc().getPeak(pi))) {
                    theMinimumDistance = this.__calculateDistanceByCarbonShift(this.getNmr13C().getPeakData().get(ci), this.getMergedHmbcAndHmqc().getPeak(pi));
                    theIndexOfTypeOfPeak = ci;
                }
            }

            this.getGroupByCarbonShift().get(theIndexOfTypeOfPeak).setPeakList().add(this.getMergedHmbcAndHmqc().getPeak(pi));
        }
    }

    private void __removeEmptyGroupInCarbonShift() {
        for (int ai = this.getGroupByCarbonShift().size() - 1; ai >= 0; ai--) {
            if (this.getGroupByCarbonShift().get(ai).isEmpty()) {
                this.setGroupByCarbonShift().remove(ai);
            }
        }
    }

    private double __calculateDistanceByCarbonShift(Nmr1dUnit the1DPeak, Nmr2dCHUnit the2DPeak) {
        return Math.abs(the1DPeak.getChemicalShift() - the2DPeak.getCarbonShift());
    }

    private void __generateGroupByHydrogenShift() {
        this.setGroupByHydrogenShift(new ArrayList<Nmr2dCHUnitList>());

        this.__initializeGroupByHydrogenShift(this.getNmr1H().getPeakData().size());
        this.__determineGroupByHydrogenShift();
        this.__removeEmptyGroupInHydrogenShift();
    }

    private void __initializeGroupByHydrogenShift(int theSizeOfArray) {
        for (int pi = 0; pi < theSizeOfArray; pi++) {
            this.setGroupByHydrogenShift().add(new Nmr2dCHUnitList());
        }
    }

    private void __determineGroupByHydrogenShift() {
        for (int pi = 0, pEnd = this.getMergedHmbcAndHmqc().getPeakList().size(); pi < pEnd; pi++) {
            double theMinimumDistance = -1;
            int theIndexOfTypeOfPeak = -1;

            for (int hi = 0, hEnd = this.getNmr13C().getPeakData().size(); hi < hEnd; hi++) {
                if (hi == 0 || theMinimumDistance > this.__calculateDistanceByCarbonShift(this.getNmr1H().getPeakData().get(hi), this.getMergedHmbcAndHmqc().getPeak(pi))) {
                    theMinimumDistance = this.__calculateDistanceByCarbonShift(this.getNmr13C().getPeakData().get(hi), this.getMergedHmbcAndHmqc().getPeak(pi));
                    theIndexOfTypeOfPeak = hi;
                }
            }

            this.setGroupByHydrogenShift().get(theIndexOfTypeOfPeak).setPeakList().add(this.getMergedHmbcAndHmqc().getPeak(pi));
        }
    }

    private void __removeEmptyGroupInHydrogenShift() {
        for (int ai = this.getGroupByCarbonShift().size() - 1; ai >= 0; ai--) {
            if (this.getGroupByHydrogenShift().get(ai).isEmpty()) {
                this.setGroupByHydrogenShift().remove(ai);
            }
        }
    }

    private Nmr2dCHUnitList __generateCriterionPeakList() {
        Nmr2dCHUnitList theCriterionPeakList = new Nmr2dCHUnitList();

        for (int qi = 0, qEnd = this.getHMQC().getMedianPeak().size(); qi < qEnd; qi++) {
            Nmr2dCHUnit theClosestPeak = this.__getClosestPeak(this.getMergedHmbcAndHmqc(), this.getHMQC().getMedianPeak().get(qi));

            theCriterionPeakList.addPeak(theClosestPeak);
        }

        return theCriterionPeakList;
    }

    private Nmr2dCHUnit __getClosestPeak(Nmr2dCHUnitList thePeakList, Nmr2dCHUnit theCheckPeak) {
        Nmr2dCHUnit theClosestPeak = new Nmr2dCHUnit();
        double theMinimumDistance = MAXIMUM_DISTANCE_IN_NMR_2D;

        for (Nmr2dCHUnit thePeak : thePeakList.getPeakList()) {
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

    private List<Integer> __getNumberOfBoundHydrogenList(Nmr2dCHUnitList theCriterionPeakList) {
        List<Integer> theNumberOfBoundHydrogenList = this.__initializeNumberOfBoundHydrogenList(theCriterionPeakList);

        for (int hi = 0, hEnd = this.getNmr1H().getPeakData().size(); hi < hEnd; hi++) {
            List<Integer> theIndexArrayMatchedBetween1HAndCriterion = this.__getIndexArrayMatchedBetween1HAndCriterion(theCriterionPeakList, this.getNmr1H().getPeak(hi));
            int theNumberOfHydrogen = this.getNmr1H().getNumberOfHydrogen(hi);

            if (!theIndexArrayMatchedBetween1HAndCriterion.isEmpty()) {
                this.__setNumberArrayOfBoundHydrogen(theNumberOfBoundHydrogenList, theIndexArrayMatchedBetween1HAndCriterion, theNumberOfHydrogen);
            }
        }

        return theNumberOfBoundHydrogenList;
    }

    private int __getIndexOfPeakInGroupByCarbonShift(Nmr2dCHUnit theTestPeak) {
        for (int li = 0, lEnd = this.getGroupByCarbonShift().size(); li < lEnd; li++) {
            if (this.getGroupByCarbonShift().get(li).getPeakList().contains(theTestPeak)) {
                return li;
            }
        }

        return -1;
    }

    private int __getIndexOfPeakGroupByHydrogenShift(Nmr2dCHUnit theTestPeak) {
        for (int li = 0, lEnd = this.getGroupByHydrogenShift().size(); li < lEnd; li++) {
            if (this.getGroupByHydrogenShift().get(li).getPeakList().contains(theTestPeak)) {
                return li;
            }
        }

        return -1;
    }

    private List<Integer> __initializeNumberOfBoundHydrogenList(Nmr2dCHUnitList theCriterionPeakList) {
        List<Integer> theIndexArrayOfGroupByCarbonShift = new ArrayList<>();
        List<Integer> theNumberOfBoundHydrogenList = new ArrayList<>();

        for (int pi = 0, pEnd = theCriterionPeakList.size(); pi < pEnd; pi++) {
            theIndexArrayOfGroupByCarbonShift.add(this.__getIndexOfPeakInGroupByCarbonShift(theCriterionPeakList.getPeak(pi)));
        }

        for (int i = 0; i < theIndexArrayOfGroupByCarbonShift.size(); i++) {
            theNumberOfBoundHydrogenList.add(0);
        }

        return theNumberOfBoundHydrogenList;
    }

    private boolean __isMatchedPeakInMultiplicity(Nmr1dUnit the1HPeak, Nmr2dCHUnit the2DPeak) {
        if (the1HPeak.getMinOfRange() <= the2DPeak.getHydrogenShift() && the1HPeak.getMaxOfRange() >= the2DPeak.getHydrogenShift()) {
            return true;
        }

        return false;
    }

    private double __calculateDistance(double theFirstChemicalShift, double theSecondChemicalShift) {
        return Math.abs(theFirstChemicalShift - theSecondChemicalShift);
    }

    private List<Integer> __getIndexArrayMatchedBetween1HAndCriterion(Nmr2dCHUnitList theCriterionPeakList, Nmr1dUnit the1DPeak) {
        List<Integer> theIndexArrayMatchedBetween1HAndHMBC = new ArrayList<>();

        double theMinimumDistance = this.MAXIMUM_DISTANCE_IN_NMR_2D;

        for (int pi = 0, pEnd = theCriterionPeakList.size(); pi < pEnd; pi++) {
            if (this.__isMatchedPeakInMultiplicity(the1DPeak, theCriterionPeakList.getPeak(pi))) {
                if (theMinimumDistance > this.__calculateDistance(the1DPeak.getChemicalShift(), theCriterionPeakList.getPeak(pi).getHydrogenShift())) {
                    theIndexArrayMatchedBetween1HAndHMBC.add(pi);
                    theMinimumDistance = this.__calculateDistance(the1DPeak.getChemicalShift(), theCriterionPeakList.getPeak(pi).getHydrogenShift());
                }
            }
        }

        return theIndexArrayMatchedBetween1HAndHMBC;
    }

    private void __setNumberArrayOfBoundHydrogen(List<Integer> theNumberArrayOfBoundHydrogen, List<Integer> theIndexArrayMatchedBetween1HAndHMBC, int theNumberOfHydrogen) {
        if (this.__isCorrectNumberOfBoundHydrogen(theIndexArrayMatchedBetween1HAndHMBC, theNumberOfHydrogen)) {
            for (int j = 0; j < theIndexArrayMatchedBetween1HAndHMBC.size(); j++) {
                theNumberArrayOfBoundHydrogen.set(theIndexArrayMatchedBetween1HAndHMBC.get(j),
                        theNumberArrayOfBoundHydrogen.get(theIndexArrayMatchedBetween1HAndHMBC.get(j)) + (theNumberOfHydrogen / theIndexArrayMatchedBetween1HAndHMBC.size()));
            }
        } else {
            this.__printIncorrectNumberOfBoundHydrogen(theIndexArrayMatchedBetween1HAndHMBC, theNumberOfHydrogen);
        }
    }

    private boolean __isCorrectNumberOfBoundHydrogen(List<Integer> theIndexArrayMatchedBetween1HAndCriterion, int theNumberOfHydrogen) {
        if (theNumberOfHydrogen % theIndexArrayMatchedBetween1HAndCriterion.size() == 0 && (theNumberOfHydrogen / theIndexArrayMatchedBetween1HAndCriterion.size()) < 4) {
            return true;
        }

        return false;
    }

    private void __printIncorrectNumberOfBoundHydrogen(List<Integer> theIndexArrayMatchedBetween1HAndCriterion, int theNumberOfHydrogen) {
        System.err.print(theIndexArrayMatchedBetween1HAndCriterion);
        System.err.print(" ");
        System.err.print(theNumberOfHydrogen % theIndexArrayMatchedBetween1HAndCriterion.size());
        System.err.println(" Error!!");
    }

    private List<CarbonQuery> __generateCarbonQueryListInHydrogenBoundedCarbon(Nmr2dCHUnitList theCriterionPeakList, List<Integer> theNumberOfBoundHydrogenList) {
        List<CarbonQuery> theCarbonQueryList = new ArrayList<>();

        for (int pi = theCriterionPeakList.size() - 1; pi >= 0; pi--) {
            if (this.__isSameHydrogenPeak(theCriterionPeakList, pi)) {
                theCriterionPeakList.remove(pi);
            } else {
                theCarbonQueryList.add(this.__generateCarbonQueryByCriterionPeak(theCriterionPeakList.get(pi), theNumberOfBoundHydrogenList, this.getGroupByHydrogenShift()));
            }
        }

        return theCarbonQueryList;
    }

    private boolean __isSameHydrogenPeak(Nmr2dCHUnitList theCriterionPeakList, int theIndexOfPeak) {
        ArrayList<Integer> theIndexArrayOfCriterionPeakInGroupByHydrogenShift = new ArrayList<>();


        for (int ci = 0, pEnd = theCriterionPeakList.size(); ci < pEnd; ci++) {
            theIndexArrayOfCriterionPeakInGroupByHydrogenShift.add(this.__getIndexOfCriterionPeakInGroupByHydrogenShift(theCriterionPeakList.get(ci)));
        }

        if (Module.count(theIndexArrayOfCriterionPeakInGroupByHydrogenShift, theIndexArrayOfCriterionPeakInGroupByHydrogenShift.get(theIndexOfPeak)) > 1) {
            return true;
        }

        return false;
    }

    private int __getIndexOfCriterionPeakInGroupByHydrogenShift(Nmr2dCHUnit theCriterionPeak) {
        int theIndexOfCriterionPeakInGroupByHydrogenShift = -1;

        for (int gi = 0, gEnd = this.getGroupByHydrogenShift().size(); gi < gEnd; gi++) {
            if (this.getGroupByHydrogenShift().get(gi).contains(theCriterionPeak)) {

                theIndexOfCriterionPeakInGroupByHydrogenShift = gi;
                break;
            }
        }

        return theIndexOfCriterionPeakInGroupByHydrogenShift;
    }

    private CarbonQuery __generateCarbonQueryByCriterionPeak(Nmr2dCHUnit theCriterionPeak, List<Integer> theNumberOfBoundHydrogenList, List<Nmr2dCHUnitList> theGroupByHydrogenShift) {
        List<Integer> theArrayMatchedSameHydrogenShift = new ArrayList<>();
        CarbonQuery theCarbonQuery = new CarbonQuery();
        int theIndexOfSortedByHydrogenPeak = this.__getIndexOfPeakGroupByHydrogenShift(theCriterionPeak);

        for (int gi = 0, gEnd = theGroupByHydrogenShift.get(theIndexOfSortedByHydrogenPeak).size(); gi < gEnd; gi++) {
            int theIndexOfPeakInGroupByCarbonPeak = this.__getIndexOfPeakInGroupByCarbonShift(this.getGroupByHydrogenShift().get(theIndexOfSortedByHydrogenPeak).get(gi));

            theArrayMatchedSameHydrogenShift.add(theIndexOfPeakInGroupByCarbonPeak);
            if (!this.getIndexArrayOfCriterionPeakInCarbonGroup().contains(theIndexOfPeakInGroupByCarbonPeak)) {
                theCarbonQuery.setNumberOfCGroup(theCarbonQuery.getNumberOfCGroup() + 1);
            } else {
                int theIndexOfArray = this.getIndexArrayOfCriterionPeakInCarbonGroup().indexOf(theIndexOfPeakInGroupByCarbonPeak);

                theCarbonQuery.set(theNumberOfBoundHydrogenList.get(theIndexOfArray), theCarbonQuery.get(theNumberOfBoundHydrogenList.get(theIndexOfArray)) + 1);
            }
        }

        this.setIndexOfArrayOfMatchedCarbonPeakInNotHetero().add(theArrayMatchedSameHydrogenShift);

        return theCarbonQuery;
    }

    private List<CarbonQuery> __generateCarbonQueryListInHydrogenBoundedHeteroAtom(Nmr2dCHUnitList theCriterionPeakList, List<Integer> theNumberOfBoundHydrogenList) {
        List<CarbonQuery> theCarbonQueryList = new ArrayList<>();
        List<Nmr2dCHUnitList> theModifiedGroupByHydrogenShift = new ArrayList<>(this.getGroupByHydrogenShift());
        Nmr2dCHUnitList theModifiedMergedHmbcAndHmqc = new Nmr2dCHUnitList(this.getMergedHmbcAndHmqc());

        this.__removePeaksAndGroupNotBoundedHeteroAtom(theModifiedGroupByHydrogenShift, theModifiedMergedHmbcAndHmqc, theCriterionPeakList);

        for (int pi = theModifiedGroupByHydrogenShift.size() - 1; pi >= 0; pi--) {
            theCarbonQueryList.add(this.__generateCarbonQueryByCriterionPeak(theCriterionPeakList.get(pi), theNumberOfBoundHydrogenList, theModifiedGroupByHydrogenShift));
        }

        return theCarbonQueryList;
    }

    private void __removePeaksAndGroupNotBoundedHeteroAtom(List<Nmr2dCHUnitList> thePeakGroupList, Nmr2dCHUnitList theModifiedMergedHmbcAndHmqc, Nmr2dCHUnitList theCriterionPeakList) {
        for (Nmr2dCHUnit theCriterionPeak : theCriterionPeakList.getPeakList()) {
            for (int gi = thePeakGroupList.size() - 1; gi >= 0; gi--) {
                if (this.__containSamePeakInGroup(thePeakGroupList.get(gi), theCriterionPeak)) {
                    for (int li = 0, lEnd = thePeakGroupList.get(gi).size(); li < lEnd; li++) {
                        theModifiedMergedHmbcAndHmqc.remove(this.__getIndexOfSamePeakInGroup(theModifiedMergedHmbcAndHmqc, thePeakGroupList.get(gi).get(li)));
                    }
                    thePeakGroupList.remove(gi);
                    break;
                }
            }
        }
    }

    private boolean __containSamePeakInGroup(Nmr2dCHUnitList thePeakList, Nmr2dCHUnit theTestPeak) {
        for (Nmr2dCHUnit thePeak : thePeakList.getPeakList()) {
            if (thePeak.getCarbonShift().equals(theTestPeak.getCarbonShift()) && thePeak.getHydrogenShift().equals(theTestPeak.getHydrogenShift())) {
                return true;
            }
        }

        return false;
    }

    private int __getIndexOfSamePeakInGroup(Nmr2dCHUnitList thePeakList, Nmr2dCHUnit theTestPeak) {
        for (int li = 0, lEnd = thePeakList.getPeakList().size(); li < lEnd; li++) {
            if (thePeakList.get(li).getCarbonShift().equals(theTestPeak.getCarbonShift()) && thePeakList.get(li).getHydrogenShift().equals(theTestPeak.getHydrogenShift())) {
                return li;
            }
        }

        return -1;
    }
}
