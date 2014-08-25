/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.bmdrc.util;

import java.util.ArrayList;
import java.util.List;
import org.openscience.cdk.Molecule;
import org.openscience.cdk.interfaces.IAtom;
import org.openscience.cdk.interfaces.IMolecule;

/**
 *
 * @author labwindows
 */
public class TopologicalDistanceMatrix {

    private IMolecule itsMolecule;
    private TwoDimensionList<Integer> itsDistanceMatrix;

    public TopologicalDistanceMatrix() {
        this.setMolecule(new Molecule());
        this.setDistanceMatrix(new TwoDimensionList<Integer>());
    }
    
    public TopologicalDistanceMatrix(IMolecule itsMolecule) {
        this.itsMolecule = itsMolecule;
        this.generateDistanceMatrix();
    }
    
    public IMolecule getMolecule() {
        return itsMolecule;
    }

    public void setMolecule(IMolecule theMolecule) {
        this.itsMolecule = theMolecule;
    }

    public TwoDimensionList<Integer> getDistanceMatrix() {
        return itsDistanceMatrix;
    }

    public void setDistanceMatrix(TwoDimensionList<Integer> theDistanceMatrix) {
        this.itsDistanceMatrix = theDistanceMatrix;
    }

    public TwoDimensionList<Integer> setDistanceMatrix() {
        return itsDistanceMatrix;
    }
    
    public int getDistance(int theFirstAtomNumber, int theSecondAtomNumber) {
        return this.getDistanceMatrix().get(theFirstAtomNumber).get(theSecondAtomNumber);
    }
    
    public void generateDistanceMatrix() {
        this.setDistanceMatrix(new TwoDimensionList<Integer>());
        
        for (int ai = 0, aEnd = this.getMolecule().getAtomCount(); ai < aEnd; ai++) {
            this.setDistanceMatrix().add(this.__generateDistanceArray(ai));
        }
    }

    private List<Integer> __generateDistanceArray(int theIndexOfAtom) {
        List<Integer> theDistanceArray = this.__intializeDistanceArray();
        List<Integer> theStartAtomNumberList = new ArrayList<>();
        List<Integer> theUsedAtomNumberList = new ArrayList<>();
        int theDistance = 0;

        theStartAtomNumberList.add(theIndexOfAtom);
        theUsedAtomNumberList.add(theIndexOfAtom);

        while (!theStartAtomNumberList.isEmpty()) {
            theDistance++;
            theStartAtomNumberList = this.__generateConnectedAtomNumberListNotUsed(theStartAtomNumberList, theUsedAtomNumberList);

            for(Integer theStartAtomNumber : theStartAtomNumberList) {
                if(theDistanceArray.get(theStartAtomNumber) == 0) {
                theDistanceArray.set(theStartAtomNumber, theDistance);
                }
            }
        }

        return theDistanceArray;
    }

    private List<Integer> __intializeDistanceArray() {
        List<Integer> theDistanceArray = new ArrayList<>();

        for (int ai = 0, aEnd = this.getMolecule().getAtomCount(); ai < aEnd; ai++) {
            theDistanceArray.add(0);
        }

        return theDistanceArray;
    }

    private List<Integer> __generateConnectedAtomNumberListNotUsed(List<Integer> theStartAtomNumberList, List<Integer> theUsedAtomNumberList) {
        List<Integer> theConnectedAtomNumberListNotUsed = new ArrayList<>();

        for (Integer theStartAtomNumber : theStartAtomNumberList) {
            List<Integer> theConnectedAtomNumberList = this.__getConnectedAtomNumberList(theStartAtomNumber);
            
            for(Integer theAtomNumber : theConnectedAtomNumberList) {
                if(!theUsedAtomNumberList.contains(theAtomNumber)) {
                    theConnectedAtomNumberListNotUsed.add(theAtomNumber);
                    theUsedAtomNumberList.add(theAtomNumber);
                }
            }
        }

        return theConnectedAtomNumberListNotUsed;
    }

    private List<Integer> __getConnectedAtomNumberList(int theIndexOfAtom) {
        List<IAtom> theConnectedAtomList = this.getMolecule().getConnectedAtomsList(this.getMolecule().getAtom(theIndexOfAtom));
        List<Integer> theConnectedAtomNumberList = new ArrayList<>();

        for (IAtom theAtom : theConnectedAtomList) {
            theConnectedAtomNumberList.add(this.getMolecule().getAtomNumber(theAtom));
        }

        return theConnectedAtomNumberList;
    }
    
    public List<Integer> getDistanceArray(int theIndex) {
        return this.getDistanceMatrix().get(theIndex);
    }
    
    public int getNumberOfAtomAtDistance(int theAtomNumber, int theCriterionDistance) {
        int theNumberOfAtomAtDistance = 0;
        
        for(Integer theDistance : this.getDistanceArray(theAtomNumber)) {
            if(theDistance == theCriterionDistance) {
                theNumberOfAtomAtDistance++;
            }
        }
        
        return theNumberOfAtomAtDistance;
    }
    
    public int indexOfDistance(int theAtomNumber, int theDistance, int theStartIndex) {
        for(int di = theStartIndex, dEnd = this.getDistanceArray(theAtomNumber).size() ; di < dEnd ; di++) {
            if(this.getDistance(theAtomNumber, di) == theDistance) {
                return di;
            }
        }
        
        return -1;
    }
}
