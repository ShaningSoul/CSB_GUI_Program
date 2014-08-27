/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.bmdrc.util;

import java.io.Serializable;
import java.util.List;

/**
 *
 * @author labwindows
 */
public class ArrayListManipulator implements Serializable{
    private static final long serialVersionUID = -7005098550835545917L;
    
    public static <T> Integer count(List<T> theArrayList, T theObject) {
        int theNumber = 0;
        
        for (int i = 0; i < theArrayList.size(); i++) {
            if (theArrayList.get(i) == null) {
                continue;
            } else if (theArrayList.get(i).equals(theObject)) {
                theNumber++;
            }
        }

        return theNumber;
    }

    public static <T> Integer count(List<T> theArrayList, T theObject, Integer theEndIndex) {
        int theNumber = 0;

        for (int i = 0; i < theEndIndex; i++) {
            if (theArrayList.get(i) == null) {
                continue;
            } else if (theArrayList.get(i).equals(theObject)) {
                theNumber++;
            }
        }

        return theNumber;
    }
}
