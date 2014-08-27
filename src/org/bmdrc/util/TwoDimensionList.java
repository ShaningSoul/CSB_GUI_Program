package org.bmdrc.util;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 *
 * @author SungBo Hwang, CSB
 */
public class TwoDimensionList <Type> implements Iterable, Serializable{
    private static final long serialVersionUID = -2687790628875644020L;
    private List<List<Type>> its2dList;

    public TwoDimensionList() {
        this.set2dList(new ArrayList<List<Type>>());
    }

    public TwoDimensionList(TwoDimensionList<Type> the2dList) {
        this.set2dList(new ArrayList<List<Type>>());
        
        for(List<Type> theList : the2dList.get2dList()) {
            this.set2dList().add(new ArrayList<>(theList));
        }
    }
    
    public List<List<Type>> get2dList() {
        return its2dList;
    }

    public void set2dList(List<List<Type>> the2dList) {
        this.its2dList = the2dList;
    }
    
    public List<List<Type>> set2dList() {
        return its2dList;
    }
    
    public void add(List<Type> theList) {
        this.set2dList().add(theList);
    }
    
    public void set(int theIndex, List<Type> theList) {
        this.set2dList().set(theIndex, theList);
    }
    
    public List<Type> get(int theIndex) {
        return this.get2dList().get(theIndex);
    }
    
    public Type get(int theFirstIndex, int theSecondIndex) {
        return this.get2dList().get(theFirstIndex).get(theSecondIndex);
    }
    
    public void remove(int theIndex) {
        this.set2dList().remove(theIndex);
    }
    
    public void remove(int theFirstIndex, int theSecondIndex) {
        this.set2dList().get(theFirstIndex).remove(theSecondIndex);
    }
    
    public boolean contains(List<Type> theList) {
        return this.get2dList().contains(theList);
    }
    
    public boolean contains(int theFirstIndex, Type theValue) {
        return this.get2dList().get(theFirstIndex).contains(theValue);
    }
    
    public int size() {
        return this.get2dList().size();
    }
    
    public void addAll(TwoDimensionList<Type> the2dList) {
        this.get2dList().addAll(the2dList.get2dList());
    }
    
    public boolean isEmpty() {
        return this.get2dList().isEmpty();
    }
    
    public void setValue(int theRowIndex, int theColumnIndex, Type theValue) {
        this.set2dList().get(theRowIndex).set(theColumnIndex, theValue);
    }
    
    @Override
    public String toString() {
        StringBuilder theStringBuilder = new StringBuilder();
        
        for(List<Type> theList : this.get2dList()) {
            theStringBuilder.append(theList).append("\n");
        }
        
        return theStringBuilder.toString();
    }

    @Override
    public Iterator iterator() {
        return this.get2dList().iterator();
    }
}
