/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.bmdrc.nmr.tool;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.openscience.cdk.interfaces.IMolecule;

/**
 *
 * @author labwindows
 */
public class FunctionalGroupList {
    private List<String> itsFunctionalGroupList;
    //constant string variable
    private final String KEY_VALUE_FUNCTIONAL_GROUP = "Functional_Group";
    private final String CRITERION_DIVIDED_FUNCTIONAL_GROUP_PROPERTY = "\t";
    
    public FunctionalGroupList() {
        this.setFunctionalGroupList(new ArrayList<String>());
    }

    public FunctionalGroupList(IMolecule theMolecule) {
        String theValue = theMolecule.getProperty(this.KEY_VALUE_FUNCTIONAL_GROUP).toString();
        String[] theSplitedValue = theValue.split(this.CRITERION_DIVIDED_FUNCTIONAL_GROUP_PROPERTY);
        
        this.setFunctionalGroupList(new ArrayList<String>());
        
        for(String thePropertyValue : theSplitedValue) {
            this.setFunctionalGroupList().add(thePropertyValue);
        }
    }

    public List<String> getFunctionalGroupList() {
        return itsFunctionalGroupList;
    }

    public void setFunctionalGroupList(List<String> theFunctionalGroupList) {
        this.itsFunctionalGroupList = theFunctionalGroupList;
    }

    public List<String> setFunctionalGroupList() {
        return itsFunctionalGroupList;
    }

    public void addFunctionalGroup(String theFunctionalGroup) {
        this.setFunctionalGroupList().add(theFunctionalGroup);
    }

    public void addAllFunctionalGroupArray(String[] theFunctionalGroupArray) {
        List<String> theFunctionalGroupList = new ArrayList<>();

        Collections.addAll(theFunctionalGroupList, theFunctionalGroupArray);
        this.setFunctionalGroupList().addAll(theFunctionalGroupList);
    }

    public boolean contains(String theFunctionalGroup) {
        if (this.getFunctionalGroupList().contains(theFunctionalGroup)) {
            return true;
        } else if (this.__containSimilarFunctionalGroup(theFunctionalGroup)) {
            return true;
        }

        return false;
    }

    private boolean __containSimilarFunctionalGroup(String theFunctionalGroup) {
        for (int fi = 0, fEnd = this.getFunctionalGroupList().size(); fi < fEnd; fi++) {
            if (this.getFunctionalGroupList().get(fi).contains(theFunctionalGroup)) {
                return true;
            }
        }

        return false;
    }
    
    public String get(int theIndex) {
        return this.getFunctionalGroupList().get(theIndex);
    }
    
    public String getFunctionalGroup(int theIndex) {
        return this.getFunctionalGroupList().get(theIndex);
    }
    
    @Override
    public String toString() {
        return this.getFunctionalGroupList().toString();
    }
    
    public boolean containFunctionalGroup(String theFunctionalGroup) {
        return this.getFunctionalGroupList().contains(theFunctionalGroup);
    }
    
    public List<Integer> getIndexListInFunctionalGroup(String theFunctionalGroup) {
        List<Integer> theIndexListInFunctionalGroup = new ArrayList<>();
        
        for(int li = 0 , lEnd = this.getFunctionalGroupList().size() ; li < lEnd ; li++) {
            if(this.getFunctionalGroupList().get(li).contains(theFunctionalGroup)) {
                theIndexListInFunctionalGroup.add(li);
            }
        }
        
        return theIndexListInFunctionalGroup;
    }
    
    public int size() {
        return this.getFunctionalGroupList().size();
    }
}
