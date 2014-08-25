package org.bmdrc.nmr;

import java.util.List;
import org.bmdrc.nmr.abstracts.AbstractNmr1d;

/**
 *
 * @author SungBo Hwang, CSB
 */
public class Nmr1H extends AbstractNmr1d{
    List<Integer> itsNumberOfHydrogenList;

    public List<Integer> getNumberOfHydrogenList() {
        return itsNumberOfHydrogenList;
    }

    public void setNumberOfHydrogenList(List<Integer> theNumberOfHydrogenList) {
        this.itsNumberOfHydrogenList = theNumberOfHydrogenList;
    }
    
    public List<Integer> setNumberOfHydrogenList() {
        return itsNumberOfHydrogenList;
    }
    
    public int getNumberOfHydrogen(int theIndex) {
        return this.getNumberOfHydrogenList().get(theIndex);
    }
}
