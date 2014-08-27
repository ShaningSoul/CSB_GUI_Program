package org.bmdrc.nmr.tool;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author SungBo Hwang. CSB
 */
public class HydrogenQuery implements Serializable{
    private static final long serialVersionUID = -1744063235525005341L;
    private List<Integer> itsHydrogenQuery;
    
    private final int SIZE_OF_QUERY = 4;
    private final int INDEX_OF_RH = 0;
    private final int INDEX_OF_RH2 = 1;
    private final int INDEX_OF_RH3 = 2;
    private final int INDEX_OF_OH = 3;

    public HydrogenQuery() {
        this.itsHydrogenQuery = new ArrayList<>();
        
        for(int ai = 0, aEnd = this.SIZE_OF_QUERY ; ai < aEnd ; ai++) {
            this.itsHydrogenQuery.add(0);
        }
    }

    
    public HydrogenQuery(List<Integer> itsHydrogenQuery) {
        this.itsHydrogenQuery = new ArrayList<>(itsHydrogenQuery);
    }

    
    public List<Integer> getHydrogenQuery() {
        return itsHydrogenQuery;
    }

    public void setHydrogenQuery(List<Integer> theHydrogenQuery) {
        this.itsHydrogenQuery = theHydrogenQuery;
    }
    
    public List<Integer> setHydrogenQuery() {
        return itsHydrogenQuery;
    }
    
    public void setNumberOfRHGroup(int theNumberOfRH) {
        this.setHydrogenQuery().set(INDEX_OF_RH, theNumberOfRH);
    }
    
    public void setNumberOfRH2Group(int theNumberOfRH2) {
        this.setHydrogenQuery().set(INDEX_OF_RH2, theNumberOfRH2);
    }
    
    public void setNumberOfRH3Group(int theNumberOfRH3) {
        this.setHydrogenQuery().set(INDEX_OF_RH3, theNumberOfRH3);
    }
    
    public void setNumberOfOHGroup(int theNumberOfOH) {
        this.setHydrogenQuery().set(INDEX_OF_OH, theNumberOfOH);
    }
    
    public int getNumberOfRHGroup() {
        return this.getHydrogenQuery().get(INDEX_OF_RH);
    }
    
    public int getNumberOfRH2Group() {
        return this.getHydrogenQuery().get(INDEX_OF_RH2);
    }
    
    public int getNumberOfRH3Group() {
        return this.getHydrogenQuery().get(INDEX_OF_RH3);
    }
    
    public int getNumberOfOHGroup() {
        return this.getHydrogenQuery().get(INDEX_OF_OH);
    }
    
    @Override
    public String toString() {
        return "[RH, RH2, RH3, OH] = " + this.getHydrogenQuery() + "\n";
    }
}
