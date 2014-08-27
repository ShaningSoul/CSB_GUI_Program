/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.bmdrc.nmr.abstracts;

import java.io.Serializable;
import java.util.List;
import org.bmdrc.nmr.Nmr1dUnit;

/**
 *
 * @author labwindows
 */
public abstract class AbstractNmr1d extends AbstractNmr implements Serializable{
    private static final long serialVersionUID = -9196291127473127266L;
    private List<Nmr1dUnit> itsPeakData;

    public List<Nmr1dUnit> getPeakData() {
        return itsPeakData;
    }

    public void setPeakData(List<Nmr1dUnit> thePeakData) {
        this.itsPeakData = thePeakData;
    }
    
    public List<Nmr1dUnit> setPeakData() {
        return itsPeakData;
    }
    
    public Nmr1dUnit getPeak(int theIndex) {
        return this.getPeakData().get(theIndex);
    }
}
