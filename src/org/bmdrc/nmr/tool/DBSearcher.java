package org.bmdrc.nmr.tool;

import java.util.ArrayList;
import java.util.List;
import org.bmdrc.util.TopologicalDistanceMatrix;
import org.openscience.cdk.interfaces.IAtom;
import org.openscience.cdk.interfaces.IMolecule;
import org.openscience.cdk.interfaces.IMoleculeSet;

/**
 *
 * @author SungBo Hwang, CSB
 */
public class DBSearcher {

    private List<CarbonQuery> itsCarbonQueryList;
    private List<HydrogenQuery> itsHydrogenQueryList;
    private IMoleculeSet itsResultMoleculeSet;
    private IMoleculeSet itsDBMoleculeSet;
    private int itsMaximumCouplingDistanceDistanceInQuternary;
    private int itsMaximumCouplingDistanceDistanceInOther;
    //constant variable
    private final int HYDROGEN_ATOMIC_NUMBER = 1;
    private final int NUMBER_OF_BOUNDED_HYDROGEN_IN_QUTERNARY_CARBON = 0;
    private final int SIZE_OF_CARBON_QUERY_SIZE = 4;

    public List<CarbonQuery> getCarbonQueryList() {
        return itsCarbonQueryList;
    }

    public void setCarbonQueryList(List<CarbonQuery> theCarbonQueryList) {
        this.itsCarbonQueryList = theCarbonQueryList;
    }

    public List<CarbonQuery> setCarbonQueryList() {
        return itsCarbonQueryList;
    }

    public List<HydrogenQuery> getHydrogenQueryList() {
        return itsHydrogenQueryList;
    }

    public void setHydrogenQueryList(List<HydrogenQuery> theHydrogenQueryList) {
        this.itsHydrogenQueryList = theHydrogenQueryList;
    }

    public List<HydrogenQuery> setHydrogenQueryList() {
        return itsHydrogenQueryList;
    }

    public IMoleculeSet getResultMoleculeSet() {
        return itsResultMoleculeSet;
    }

    public void setResultMoleculeSet(IMoleculeSet theResultMoleculeSet) {
        this.itsResultMoleculeSet = theResultMoleculeSet;
    }

    public IMoleculeSet setResultMoleculeSet() {
        return itsResultMoleculeSet;
    }

    public IMoleculeSet getDBMoleculeSet() {
        return itsDBMoleculeSet;
    }

    public void setDBMoleculeSet(IMoleculeSet theDBMoleculeSet) {
        this.itsDBMoleculeSet = theDBMoleculeSet;
    }

    public IMoleculeSet setDBMoleculeSet() {
        return itsDBMoleculeSet;
    }

    public int getMaximumCouplingDistanceDistanceInQuternary() {
        return itsMaximumCouplingDistanceDistanceInQuternary;
    }

    public void setMaximumCouplingDistanceDistanceInQuternary(int theMaximumCouplingDistanceDistanceInQuternary) {
        this.itsMaximumCouplingDistanceDistanceInQuternary = theMaximumCouplingDistanceDistanceInQuternary;
    }

    public int getMaximumCouplingDistanceDistanceInOther() {
        return itsMaximumCouplingDistanceDistanceInOther;
    }

    public void setMaximumCouplingDistanceDistanceInOther(int theMaximumCouplingDistanceDistanceInOther) {
        this.itsMaximumCouplingDistanceDistanceInOther = theMaximumCouplingDistanceDistanceInOther;
    }

    public void searchMatchedMoleculeIn2DNMR() {
        for (int mi = 0, mEnd = this.getDBMoleculeSet().getMoleculeCount(); mi < mEnd; mi++) {
            if (this.__isMatchedMoleculeIn2DNMR(this.getDBMoleculeSet().getMolecule(mi))) {
                this.setResultMoleculeSet().addMolecule(this.getDBMoleculeSet().getMolecule(mi));
            }
        }
    }

    private boolean __isMatchedMoleculeIn2DNMR(IMolecule theMolecule) {
        TopologicalDistanceMatrix theDistanceMatrixAmongCarbon = new TopologicalDistanceMatrix(theMolecule);
        List<CarbonQuery> theCarbonQueryListInMolecule = this.__generateCarbonQueryListInMolecule(theMolecule, theDistanceMatrixAmongCarbon);
        List<List<Integer>> thePreviousPathMatrix = new ArrayList<>();

        for (int qi = 0, qEnd = this.getCarbonQueryList().size(); qi < qEnd; qi++) {
            List<List<Integer>> theNewPathMatrix = new ArrayList<>();
            List<Integer> theMatchedAtomNumberList = this.__generateMatchedAtomNumberList(theCarbonQueryListInMolecule, this.getCarbonQueryList().get(qi));

            if (qi > 0) {
                for (int k = thePreviousPathMatrix.size() - 1; k >= 0; k--) {
                    boolean theJugment = false;

                    for (int l = 0; l < theMatchedAtomNumberList.size(); l++) {
                        if (!thePreviousPathMatrix.get(k).contains(theMatchedAtomNumberList.get(l))) {
                            List<Integer> theCheckArray = new ArrayList<>(thePreviousPathMatrix.get(k));

                            theCheckArray.add(theMatchedAtomNumberList.get(l));
                            theNewPathMatrix.add(theCheckArray);
                            theJugment = true;
                        }
                    }

                    if (!theJugment) {
                        thePreviousPathMatrix.remove(k);
                    }
                }
            } else {
                for (int k = 0; k < theMatchedAtomNumberList.size(); k++) {
                    java.util.ArrayList<Integer> theCheckArray = new java.util.ArrayList<Integer>();

                    theCheckArray.add(theMatchedAtomNumberList.get(k));
                    theNewPathMatrix.add((java.util.ArrayList<Integer>) theCheckArray.clone());
                }
            }

            thePreviousPathMatrix = theNewPathMatrix;
        }

        if (!thePreviousPathMatrix.isEmpty()) {
            return true;
        }
        return false;
    }

    private List<Integer> __generateMatchedAtomNumberList(List<CarbonQuery> theCarbonQueryListInMolecule, CarbonQuery theSearchingQuery) {
        List<Integer> theMatchedAtomNumberList = new ArrayList<>();

        for (int qli = 0, qlEnd = theCarbonQueryListInMolecule.size(); qli < qlEnd; qli++) {
            if (!theCarbonQueryListInMolecule.get(qli).isEmpty()) {
                boolean theJugment = true;

                for (int ci = 0; ci < this.SIZE_OF_CARBON_QUERY_SIZE; ci++) {
                    if (theCarbonQueryListInMolecule.get(qli).get(ci) < theSearchingQuery.get(ci)) {
                        theJugment = false;
                        break;
                    }
                }

                if (theJugment) {
                    theMatchedAtomNumberList.add(qli);
                }
            }
        }

        return theMatchedAtomNumberList;
    }

    private List<CarbonQuery> __generateCarbonQueryListInMolecule(IMolecule theMolecule, TopologicalDistanceMatrix theDistanceMatrix) {
        List<CarbonQuery> theCarbonQueryListInMolecule = new ArrayList<>();

        for (int ai = 0, aEnd = theMolecule.getAtomCount(); ai < aEnd; ai++) {
            CarbonQuery theCarbonQuery = new CarbonQuery();

            if (this.__getNumberOfBoundedHydrogen(theMolecule, ai) != 0) {
                theCarbonQuery = this.__getCarbonQueryAtAtomInMolecule(theMolecule, ai, theDistanceMatrix);
            }

            theCarbonQueryListInMolecule.add(theCarbonQuery);
        }

        return theCarbonQueryListInMolecule;
    }

    private CarbonQuery __getCarbonQueryAtAtomInMolecule(IMolecule theMolecule, int theIndexOfAtom, TopologicalDistanceMatrix theDistanceMatrix) {
        CarbonQuery theCarbonQuery = new CarbonQuery();
        List<Integer> theConnectedAtomIndexInDistance = this.__getConnectedAtomIndexInDistance(theMolecule, theIndexOfAtom, theDistanceMatrix);
        List<Integer> theNumberOfHydrogenList = new ArrayList<>();

        for (int ai = 0, aEnd = theConnectedAtomIndexInDistance.size(); ai < aEnd; ai++) {
            int theNumberOfBoundedHydrogen = this.__getNumberOfBoundedHydrogen(theMolecule, theConnectedAtomIndexInDistance.get(ai));

            if (this.__isAtomInCriterionDistance(theNumberOfBoundedHydrogen, theDistanceMatrix.getDistance(theIndexOfAtom, theConnectedAtomIndexInDistance.get(ai)))) {
                theCarbonQuery.set(theNumberOfBoundedHydrogen, theCarbonQuery.get(theNumberOfBoundedHydrogen) + 1);
            }
        }

        return theCarbonQuery;
    }

    private boolean __isAtomInCriterionDistance(int theNumberOfBoundedHydrogen, int theDistance) {
        if (theNumberOfBoundedHydrogen == 0 && this.getMaximumCouplingDistanceDistanceInQuternary() > theDistance) {
            return true;
        } else if (theNumberOfBoundedHydrogen != 0 && this.getMaximumCouplingDistanceDistanceInOther() > theDistance) {
            return true;
        }

        return false;
    }

    private List<Integer> __getConnectedAtomIndexInDistance(IMolecule theMolecule, int theIndexOfAtom, TopologicalDistanceMatrix theDistanceMatrix) {
        List<Integer> theConnectedAtomIndexInDistance = new ArrayList<>();
        int theCriterionDistance = -1;

        if (this.__getNumberOfBoundedHydrogen(theMolecule, theIndexOfAtom) == this.NUMBER_OF_BOUNDED_HYDROGEN_IN_QUTERNARY_CARBON) {
            theCriterionDistance = this.getMaximumCouplingDistanceDistanceInQuternary();
        } else {
            theCriterionDistance = this.getMaximumCouplingDistanceDistanceInOther();
        }

        for (int di = 0, dEnd = theDistanceMatrix.getDistanceArray(theIndexOfAtom).size(); di < dEnd; di++) {
            if (theDistanceMatrix.getDistance(theIndexOfAtom, di) <= theCriterionDistance) {
                theConnectedAtomIndexInDistance.add(di);
            }
        }

        return theConnectedAtomIndexInDistance;
    }

    private int __getNumberOfBoundedHydrogen(IMolecule theMolecule, int theAtomNumber) {
        int theNumberOfBoundedHydrogen = 0;
        List<IAtom> theConnectedAtomList = theMolecule.getConnectedAtomsList(theMolecule.getAtom(theAtomNumber));

        for (IAtom theAtom : theConnectedAtomList) {
            if (theAtom.getAtomicNumber() == this.HYDROGEN_ATOMIC_NUMBER) {
                theNumberOfBoundedHydrogen++;
            }
        }

        return theNumberOfBoundedHydrogen;
    }
}
