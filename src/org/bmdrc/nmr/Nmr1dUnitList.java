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
public class Nmr1dUnitList implements Iterable, Serializable{
    private static final long serialVersionUID = -2973261833967702580L;
    private List<Nmr1dUnit> itsPeakList;

    public Nmr1dUnitList() {
        this.setPeakList(new ArrayList<Nmr1dUnit>());
    }

    public Nmr1dUnitList(List<Nmr1dUnit> itsPeakList) {
        this.itsPeakList = new ArrayList<>(itsPeakList);
    }
    
    
    public List<Nmr1dUnit> getPeakList() {
        return itsPeakList;
    }

    public void setPeakList(List<Nmr1dUnit> thePeakList) {
        this.itsPeakList = thePeakList;
    }
    
    public List<Nmr1dUnit> setPeakList() {
        return itsPeakList;
    }
    
    public Nmr1dUnit getPeak(int theIndex) {
        return this.getPeakList().get(theIndex);
    }
    
    public Nmr1dUnitList getPeakListUsingAnnotatedAtomNumber(int theAtomNumber) {
        Nmr1dUnitList thePeakListInAnnotatedAtomNumber = new Nmr1dUnitList();
        
        for(int pi = 0, pEnd = this.size() ; pi < pEnd ; pi++) {
            if(this.getPeak(pi).getAnnotatedAtomNumber() == theAtomNumber) {
                thePeakListInAnnotatedAtomNumber.addPeak(this.getPeak(pi));
            }
        }
        
        return thePeakListInAnnotatedAtomNumber;
    }
    
    public void addPeak(Nmr1dUnit thePeak) {
        this.setPeakList().add(thePeak);
    }
    
    public void addAllPeaks(Nmr1dUnitList thePeakList) {
        this.setPeakList().addAll(thePeakList.getPeakList());
    }
    
    public boolean isEmpty() {
        return this.getPeakList().isEmpty();
    }
    
    public int size() {
        return this.getPeakList().size();
    }

    @Override
    public Iterator iterator() {
        return this.getPeakList().iterator();
    }
}
