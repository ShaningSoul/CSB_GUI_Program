/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.bmdrc.nmr.tool;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.regex.PatternSyntaxException;
import org.bmdrc.interfaces.IStringConstant;
import org.bmdrc.util.TwoDimensionList;

/**
 *
 * @author labwindows
 */
public class HoseCodeManipulator implements IStringConstant {

    private String itsHoseCode;
    private TwoDimensionList<String> itsHoseCodeLevel2dList;
    private List<String> itsHoseCodeListDiviedByLevel;
    private List<String> itsZeroCellList;
    private List<String> itsFirstCellList;
    private List<String> itsSecondCellList;
    private List<String> itsThirdCellList;
    //constant int variable
    private int INDEX_OF_ZERO_CELL = 0;
    private int INDEX_OF_FIRST_CELL = 1;
    private int INDEX_OF_SECOND_CELL = 2;
    private int INDEX_OF_THIRD_CELL = 3;
    private int MAXIMUM_SIZE_OF_LIST = 4;
    private int ZERO_CELL_ATOM_SYMBOL_INDEX = 0;
    private int FIRST_INDEX = 0;
    private int STRING_START_INDEX = 0;
    private int ATOM_SYMBOL_END_IN_ZERO_CELL = 1;
    //constant string variable
    private final String DIVIDED_CELL_REGEX = this.OPEN_PARENTHESES_REGEX + this.BAR_STRING + this.CLOSE_PARENTHESES_REGEX + this.BAR_STRING + this.SLUSH_REGEX + this.BAR_STRING + this.SEMICOLON_REGEX;
    private final String CRITERION_HOSE_CODE_DIVIDED_BETWEEN_ZERO_AND_FIRST = ";";
    private final String CRITERION_HOSE_CODE_DIVIDED_BETWEEN_FIRST_AND_SECOND = "\\(";
    private final String CRITERION_HOSE_CODE_DIVIDED_BETWEEN_SECOND_AND_THIRD = "/";
    //constant char variable
    private final char CRITERION_CHARACTER_COMMA = ',';

    public HoseCodeManipulator() {
        this.setHoseCodeListDiviedByLevel(new ArrayList<String>());
        this.setZeroCellList(new ArrayList<String>());
        this.setFirstCellList(new ArrayList<String>());
        this.setSecondCellList(new ArrayList<String>());
        this.setThirdCellList(new ArrayList<String>());
        this.setHoseCode(new String());
        this.setHoseCodeLevel2dList(new TwoDimensionList<String>());
    }

    public HoseCodeManipulator(String theHoseCode) {
        this.setHoseCodeListDiviedByLevel(this.__divideCell(theHoseCode));
        this.setHoseCodeLevel2dList(new TwoDimensionList<String>());

        for (int ci = 0, cEnd = this.getHoseCodeListDiviedByLevel().size(); ci < cEnd; ci++) {
            if (ci == 0) {
                this.__modifyZeroCell(this.getHoseCodeListDiviedByLevel().get(this.FIRST_INDEX));
            } else {
                this.setHoseCodeLevel2dList().add(this.__divideCellByComma(this.getHoseCodeListDiviedByLevel(), ci));
            }
        }
    }

    public HoseCodeManipulator(List<String> theHoseCodeListDiviedByLevel) {
        this.itsHoseCodeListDiviedByLevel = theHoseCodeListDiviedByLevel;
        this.setZeroCellList(this.__divideCellByCharacter(theHoseCodeListDiviedByLevel, this.INDEX_OF_ZERO_CELL));
        this.setFirstCellList(this.__divideCellByCharacter(theHoseCodeListDiviedByLevel, this.INDEX_OF_FIRST_CELL));
        this.setSecondCellList(this.__divideCellByComma(theHoseCodeListDiviedByLevel, this.INDEX_OF_SECOND_CELL));
        this.setThirdCellList(this.__divideCellByComma(theHoseCodeListDiviedByLevel, this.INDEX_OF_THIRD_CELL));
    }

    public List<String> getHoseCodeListDiviedByLevel() {
        return itsHoseCodeListDiviedByLevel;
    }

    public void setHoseCodeListDiviedByLevel(List<String> theHoseCodeListDiviedByLevel) {
        this.itsHoseCodeListDiviedByLevel = theHoseCodeListDiviedByLevel;
    }

    public List<String> setHoseCodeListDiviedByLevel() {
        return itsHoseCodeListDiviedByLevel;
    }

    public List<String> getZeroCellList() {
        return itsZeroCellList;
    }

    public void setZeroCellList(List<String> theZeroCellList) {
        this.itsZeroCellList = theZeroCellList;
    }

    public List<String> setZeroCellList() {
        return itsZeroCellList;
    }

    public List<String> getFirstCellList() {
        return itsFirstCellList;
    }

    public void setFirstCellList(List<String> theFirstCellList) {
        this.itsFirstCellList = theFirstCellList;
    }

    public List<String> setFirstCellList() {
        return itsFirstCellList;
    }

    public List<String> getSecondCellList() {
        return itsSecondCellList;
    }

    public void setSecondCellList(List<String> theSecondCellList) {
        this.itsSecondCellList = theSecondCellList;
    }

    public List<String> setSecondCellList() {
        return itsSecondCellList;
    }

    public List<String> getThirdCellList() {
        return itsThirdCellList;
    }

    public void setThirdCellList(List<String> theThirdCellList) {
        this.itsThirdCellList = theThirdCellList;
    }

    public List<String> setThirdCellList() {
        return itsThirdCellList;
    }

    public String getHoseCode() {
        return itsHoseCode;
    }

    public void setHoseCode(String itsHoseCode) {
        this.itsHoseCode = itsHoseCode;
    }

    public TwoDimensionList<String> getHoseCodeLevel2dList() {
        return itsHoseCodeLevel2dList;
    }

    public void setHoseCodeLevel2dList(TwoDimensionList<String> theHoseCodeLevel2dList) {
        this.itsHoseCodeLevel2dList = theHoseCodeLevel2dList;
    }

    public TwoDimensionList<String> setHoseCodeLevel2dList() {
        return itsHoseCodeLevel2dList;
    }

    private void __modifyZeroCell(String theZeroCell) {
        List<String> theNewZeroList = new ArrayList<>();

        theNewZeroList.add(theZeroCell.substring(this.STRING_START_INDEX, this.ATOM_SYMBOL_END_IN_ZERO_CELL));
        this.setHoseCodeLevel2dList().add(theNewZeroList);
    }

    private List<String> __divideCellByCharacter(List<String> theCell, int theCellNumber) {
        String[] theSplitedStringList = theCell.get(theCellNumber).split(this.EMPTY_STRING);
        List<String> theSplitedString = new ArrayList<>();

        Collections.addAll(theSplitedString, theSplitedStringList);
        theSplitedString.remove(0);

        return theSplitedString;
    }

    private List<String> __divideCellByComma(List<String> theCell, Integer theCellNumber) {
        
        String[] theSplitedStringArray = theCell.get(theCellNumber).split(this.COMMA_REGEX);
        List<String> theSplitedStringList = new ArrayList<>();

        if (!theCell.get(theCellNumber).isEmpty()) {
            Collections.addAll(theSplitedStringList, theSplitedStringArray);

            for (int si = theCell.get(theCellNumber).length() - 1; si >= 0 && theCell.get(theCellNumber).charAt(si) == this.COMMA_STRING.charAt(0); si--) {
                theSplitedStringList.add(new String());
            }
        }

        return theSplitedStringList;
    }

    @Override
    public String toString() {
        StringBuilder theString = new StringBuilder();

        theString.append("the First Cell List  : ").append(this.getFirstCellList()).append("\n");
        theString.append("the Second Cell List : ");

        if (this.getSecondCellList().isEmpty()) {
            theString.append("Empty!!\n");
        } else {
            theString.append(this.getSecondCellList()).append("\n");
        }

        theString.append("the Third Cell List : ");

        if (this.getThirdCellList().isEmpty()) {
            theString.append("Empty!!\n");
        } else {
            theString.append(this.getThirdCellList());
        }

        return theString.toString();
    }

    public boolean containsOnlyComma(int theNumberOfSphere) {
        for (int ci = 0, cEnd = this.getHoseCodeListDiviedByLevel().get(theNumberOfSphere).length(); ci < cEnd; ci++) {
            if (this.getHoseCodeListDiviedByLevel().get(theNumberOfSphere).charAt(ci) != this.CRITERION_CHARACTER_COMMA) {
                return false;
            }
        }

        return true;
    }

    private List<String> __divideCell(String theHOSECODE) {
        String[] theSplitedString = theHOSECODE.split(this.DIVIDED_CELL_REGEX);
        ArrayList<String> theCell = new ArrayList<>();

        Collections.addAll(theCell, theSplitedString);

        return theCell;
    }
}
