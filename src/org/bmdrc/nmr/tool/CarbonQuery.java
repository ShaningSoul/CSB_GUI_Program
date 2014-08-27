package org.bmdrc.nmr.tool;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author SungBou Hwang, CSB
 */
public class CarbonQuery implements Serializable{
    private static final long serialVersionUID = 6057037478060661551L;

    private List<Integer> itsCarbonQuery;
    
    //constant
    private final int SIZE_OF_QUERY = 4;
    private final int INDEX_OF_C = 0;
    private final int INDEX_OF_CH = 1;
    private final int INDEX_OF_CH2 = 2;
    private final int INDEX_OF_CH3 = 3;

    public CarbonQuery() {
        this.itsCarbonQuery = new ArrayList<>();
        
        for(int ai = 0, aEnd = this.SIZE_OF_QUERY ; ai < aEnd ; ai++) {
            this.itsCarbonQuery.add(0);
        }
    }

    
    public CarbonQuery(List<Integer> theCarbonQuery) {
        this.itsCarbonQuery = new ArrayList<>(theCarbonQuery);
    }

    
    public List<Integer> getCarbonQuery() {
        return itsCarbonQuery;
    }

    public void setCarbonQuery(List<Integer> theCarbonQuery) {
        this.itsCarbonQuery = theCarbonQuery;
    }
    
    public List<Integer> setCarbonQuery() {
        return itsCarbonQuery;
    }
    
    public void setNumberOfCGroup(int theNumberOfC) {
        this.setCarbonQuery().set(INDEX_OF_C, theNumberOfC);
    }
    
    public void setNumberOfCHGroup(int theNumberOfCH) {
        this.setCarbonQuery().set(INDEX_OF_CH, theNumberOfCH);
    }
    
    public void setNumberOfCH2Group(int theNumberOfCH2) {
        this.setCarbonQuery().set(INDEX_OF_CH2, theNumberOfCH2);
    }
    
    public void setNumberOfCH3Group(int theNumberOfCH3) {
        this.setCarbonQuery().set(INDEX_OF_CH3, theNumberOfCH3);
    }
    
    public void set(int theIndexOfGroup, int theNumberOfGroup) {
        this.setCarbonQuery().set(theIndexOfGroup, theNumberOfGroup);
    }
    
    public int getNumberOfCGroup() {
        return this.getCarbonQuery().get(INDEX_OF_C);
    }
    
    public int getNumberOfRH2Group() {
        return this.getCarbonQuery().get(INDEX_OF_CH);
    }
    
    public int getNumberOfRH3Group() {
        return this.getCarbonQuery().get(INDEX_OF_CH2);
    }
    
    public int getNumberOfOHGroup() {
        return this.getCarbonQuery().get(INDEX_OF_CH3);
    }
    
    public int get(int theIndexOfGroup) {
        return this.getCarbonQuery().get(theIndexOfGroup);
    }
    
    public boolean isEmpty() {
        for(int li = 0; li < this.SIZE_OF_QUERY ; li++) {
            if(this.getCarbonQuery().get(li) != 0) {
                return false;
            }
        }
        
        return true;
    }
    
    @Override
    public String toString() {
        return "[C, CH, CH2, CH3] = " + this.getCarbonQuery() + "\n";
    }
}
