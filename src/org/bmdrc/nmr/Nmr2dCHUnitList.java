/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.bmdrc.nmr;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
/**
 *
 * @author labwindows
 */
public class Nmr2dCHUnitList implements Iterable, Serializable{
    private static final long serialVersionUID = 1022875418690874678L;
    private List<Nmr2dCHUnit> itsPeakList;

    public Nmr2dCHUnitList() {
        this.itsPeakList = new ArrayList<>();
    }

    
    public Nmr2dCHUnitList(List<Nmr2dCHUnit> thePeakList) {
        this.itsPeakList = new ArrayList<>(thePeakList);
    }
    
    public Nmr2dCHUnitList(Nmr2dCHUnitList thePeakList) {
        this.itsPeakList = new ArrayList<>(thePeakList.getPeakList());
    }

    
    public List<Nmr2dCHUnit> getPeakList() {
        return itsPeakList;
    }

    public void setPeakList(List<Nmr2dCHUnit> thePeakList) {
        this.itsPeakList = thePeakList;
    }
    
    public List<Nmr2dCHUnit> setPeakList() {
        return itsPeakList;
    }
    
    public Nmr2dCHUnit getPeak(int theIndex) {
        return this.getPeakList().get(theIndex);
    }
    
    public void setPeak(Nmr2dCHUnit thePeak, int theIndex) {
        this.setPeakList().set(theIndex, thePeak);
    }
    
    public void addPeak(Nmr2dCHUnit the2DPeak) {
        this.setPeakList().add(the2DPeak);
    }
    
    public void addPeakList(Nmr2dCHUnitList the2DPeakList) {
        for(int pi = 0 , pEnd = the2DPeakList.getPeakList().size(); pi < pEnd ; pi++) {
            this.setPeakList().add(the2DPeakList.getPeakList().get(pi));
        }
    }
    
    public int size() {
        return this.getPeakList().size();
    }
    
    public void remove(int theIndex) {
        this.getPeakList().remove(theIndex);
    }
    
    public List<Nmr2dCHUnit> toList() {
        return this.getPeakList();
    }
    
    public Nmr2dCHUnit get(int theIndex) {
        return this.getPeak(theIndex);
    }
    
    public boolean isEmpty() {
        if(this.getPeakList().isEmpty()) {
            return true;
        }
        
        return false;
    }
    
    public boolean contains(Nmr2dCHUnit thePeak) {
        if(this.getPeakList().contains(thePeak)) {
            return true;
        }
        
        return false;
    }

    @Override
    public Iterator iterator() {
        return this.getPeakList().iterator();
    }
}
