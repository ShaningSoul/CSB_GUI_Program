/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.bmdrc.util;

import java.util.ArrayList;
import java.util.List;
import org.openscience.cdk.interfaces.IAtom;
import org.openscience.cdk.interfaces.IMolecule;

/**
 *
 * @author labwindows
 */
public class ConnectingMatrix {

    private TwoDimensionList<Integer> itsConnectingMatrix;
    private IMolecule itsMolecule;
    private Integer itsAtomNumber;
    //constant int variable
    private final Integer END_NODE_VALUE = -1;

    public ConnectingMatrix(IMolecule theMolecule, Integer theAtomNumber) {
        this.setConnectingMatrix(new TwoDimensionList<Integer>());
        this.setMolecule(theMolecule);
        this.setAtomNumber(theAtomNumber);
    }

    public TwoDimensionList<Integer> getConnectingMatrix() {
        return itsConnectingMatrix;
    }

    public void setConnectingMatrix(TwoDimensionList<Integer> theConnectingMatrix) {
        this.itsConnectingMatrix = theConnectingMatrix;
    }

    public TwoDimensionList<Integer> setConnectingMatrix() {
        return itsConnectingMatrix;
    }

    public IMolecule getMolecule() {
        return itsMolecule;
    }

    public void setMolecule(IMolecule theMolecule) {
        this.itsMolecule = theMolecule;
    }

    public Integer getAtomNumber() {
        return itsAtomNumber;
    }

    public void setAtomNumber(Integer theAtomNumber) {
        this.itsAtomNumber = theAtomNumber;
    }

    public TwoDimensionList<Integer> generateConnectingMatrix() {
        TwoDimensionList<Integer> theNewConnectingMatrix = new TwoDimensionList<>();
        List<Integer> theConnectingList = new ArrayList<>();

        theConnectingList.add(this.getAtomNumber());
        this.setConnectingMatrix(new TwoDimensionList<Integer>());
        this.setConnectingMatrix().add(theConnectingList);

        while (!this.__isEndNodeInAllLastList()) {
            for (List<Integer> thePreviousConnectingList : this.getConnectingMatrix().get2dList()) {
                TwoDimensionList<Integer> theConnectingMatrix = this.__generateConnectingMatrix(thePreviousConnectingList);
                
                theNewConnectingMatrix.addAll(theConnectingMatrix);
            }
        }

        return this.getConnectingMatrix();
    }

    private boolean __isEndNodeInAllLastList() {
        if (this.getConnectingMatrix().isEmpty()) {
            return false;
        } else {
            for (List<Integer> thePathList : this.getConnectingMatrix().get2dList()) {
                if (thePathList.get(thePathList.size() - 1) != this.END_NODE_VALUE) {
                    return false;
                }
            }
        }

        return true;
    }

    private TwoDimensionList<Integer> __generateConnectingMatrix(List<Integer> thePreviousConnectingList) {
        TwoDimensionList<Integer> theConnectingMatrix = new TwoDimensionList<>();
        List<Integer> theConnectingAtomNumberList = this.__getConnectedAtomNumberList(this.getMolecule().getAtom(thePreviousConnectingList.get(thePreviousConnectingList.size() - 1)));
        List<Integer> theCopyConnectingList = new ArrayList<>(thePreviousConnectingList);

        for (Integer theConnectingAtomNumber : theConnectingAtomNumberList) {
            if (!thePreviousConnectingList.contains(theConnectingAtomNumber)) {
                theCopyConnectingList = new ArrayList<>(thePreviousConnectingList);

                theCopyConnectingList.add(theConnectingAtomNumber);
                theConnectingMatrix.add(theCopyConnectingList);
            }
        }

        if (theConnectingMatrix.isEmpty()) {
            theCopyConnectingList.add(this.END_NODE_VALUE);
            theConnectingMatrix.add(theCopyConnectingList);
        }

        return theConnectingMatrix;
    }

    private List<Integer> __getConnectedAtomNumberList(IAtom theAtom) {
        List<Integer> theConnectedAtomNumberList = new ArrayList<>();
        List<IAtom> theConnectedAtomList = this.getMolecule().getConnectedAtomsList(theAtom);

        for (IAtom theConnectedAtom : theConnectedAtomList) {
            theConnectedAtomNumberList.add(this.getMolecule().getAtomNumber(theConnectedAtom));
        }

        return theConnectedAtomNumberList;
    }
}
