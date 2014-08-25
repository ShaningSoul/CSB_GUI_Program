/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.bmdrc.util;

import java.util.ArrayList;
import java.util.List;
import org.openscience.cdk.Molecule;
import org.openscience.cdk.interfaces.IMolecule;

/**
 *
 * @author labwindows
 */
public class DistanceMatrix {
    private IMolecule itsMolecule;
    private TwoDimensionList<Double> itsDistanceMatrix;

    public DistanceMatrix() {
        this.setMolecule(new Molecule());
        this.setDistanceMatrix(new TwoDimensionList<Double>());
    }

    public DistanceMatrix(IMolecule itsMolecule) {
        this.itsMolecule = itsMolecule;
        this.generateDistanceMatrix();
    }

    public IMolecule getMolecule() {
        return itsMolecule;
    }

    public void setMolecule(IMolecule theMolecule) {
        this.itsMolecule = theMolecule;
    }

    public IMolecule setMolecule() {
        return itsMolecule;
    }
    
    public TwoDimensionList<Double> getDistanceMatrix() {
        return itsDistanceMatrix;
    }

    public void setDistanceMatrix(TwoDimensionList<Double> theDistanceMatrix) {
        this.itsDistanceMatrix = theDistanceMatrix;
    }
    
    public TwoDimensionList<Double> setDistanceMatrix() {
        return itsDistanceMatrix;
    }
    
    public TwoDimensionList<Double> generateDistanceMatrix() {
        this.__initializeDistanceMatrix();
        
        for(int ai = 0, aEnd = this.getMolecule().getAtomCount(); ai < aEnd ; ai++) {
            for(int aj = 0; aj < aEnd ; aj++) {
                if(this.getMolecule().getAtom(ai).getPoint3d() == null && this.getMolecule().getAtom(aj).getPoint3d() == null) {
                    this.setDistanceMatrix().setValue(ai, aj, this.getMolecule().getAtom(ai).getPoint2d().distance(this.getMolecule().getAtom(aj).getPoint2d()));
                } else {
                    this.setDistanceMatrix().setValue(ai, aj, this.getMolecule().getAtom(ai).getPoint3d().distance(this.getMolecule().getAtom(aj).getPoint3d()));
                }
            }
        }
        
        return this.getDistanceMatrix();
    }
    
    private void __initializeDistanceMatrix() {
        this.setDistanceMatrix(new TwoDimensionList<Double>());
        
        int theNumberOfAtom = this.getMolecule().getAtomCount();
        
        for(int ri = 0; ri < theNumberOfAtom ; ri++) {
            List<Double> theList = new ArrayList<>();
            
            for(int ci = 0; ci < theNumberOfAtom ; ci++) {
                theList.add(0.0);
            }
            
            this.setDistanceMatrix().add(theList);
        }
    }
    
    @Override
    public String toString() {
        StringBuilder theStringBuilder = new StringBuilder();
        
        for(List<Double> theList : this.getDistanceMatrix().get2dList()) {
            theStringBuilder.append(theList).append("\n");
        }
        
        return theStringBuilder.toString();
    }
}
