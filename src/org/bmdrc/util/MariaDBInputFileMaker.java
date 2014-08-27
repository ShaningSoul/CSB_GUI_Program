package org.bmdrc.util;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.openscience.cdk.MoleculeSet;
import org.openscience.cdk.interfaces.IMoleculeSet;

/**
 *
 * @author SungBo Hwang, CSB
 */
public class MariaDBInputFileMaker implements Serializable{
    private static final long serialVersionUID = 3110973777696891645L;

    private Map<String, List<String>> itsPropertyMap;
    private IMoleculeSet itsMoleculeSet;
    private List<String> itsUnusualStringListAtFileName;
    //constant char variable
    private final char BACK_SLASH_CHAR = '\\';
    //constant string variable
    private final String CONVERTED_COMMA_TO_STRING = " ";
    private final String CONVERTED_UNUSUAL_CHAR_IN_FILE_NAME = "-";
    private final String CRITERION_DIVIDED_CELL = "\t";
    private final String RESULT_FILE_SUFFIX = ".txt";
    private final String NEW_LINE = "\n";
    private final String BACK_SLASH_STRING = "\\";
    private final String UNDER_BAR = "_";
    private final String COMMA_STRING = ",";
    private final String SLASH_STRING = "/";
    private final String DOUBLE_DOT_STRING = ":";
    private final String STAR_MARK_STRING = "*";
    private final String QUESTION_MARK_STRING = "?";
    private final String QUOTATION_MARK_STRING = "*";
    private final String LESS_MARK_STRING = "<";
    private final String BIGGER_MARK_STRING = ">";
    private final String BAR_STRING = "|";
    //constant int variable
    private final int KEY_INDEX = 0;
    private final int VALUE_INDEX = 1;

    public MariaDBInputFileMaker() {
        this.setMoleculeSet(new MoleculeSet());
        this.setPropertyMap(new HashMap<String, List<String>>());
        this.__generateUnusualStringListAtFileName();
    }

    public MariaDBInputFileMaker(IMoleculeSet theMoleculeSet) {
        this.itsMoleculeSet = theMoleculeSet;
        this.__generatePropertyMap();
        this.__generateUnusualStringListAtFileName();
    }

    public Map<String, List<String>> getPropertyMap() {
        return itsPropertyMap;
    }

    public void setPropertyMap(Map<String, List<String>> thePropertyMap) {
        this.itsPropertyMap = thePropertyMap;
    }

    public Map<String, List<String>> setPropertyMap() {
        return itsPropertyMap;
    }

    public IMoleculeSet getMoleculeSet() {
        return itsMoleculeSet;
    }

    public void setMoleculeSet(IMoleculeSet theMoleculeSet) {
        this.itsMoleculeSet = theMoleculeSet;
        this.__generatePropertyMap();
        this.__generateUnusualStringListAtFileName();
    }

    public IMoleculeSet setMoleculeSet() {
        return itsMoleculeSet;
    }

    public List<String> getUnusualStringListAtFileName() {
        return itsUnusualStringListAtFileName;
    }

    private void __setUnusualStringListAtFileName(List<String> theUnusualStringListAtFileName) {
        this.itsUnusualStringListAtFileName = theUnusualStringListAtFileName;
    }
    
    private List<String> __setUnusualStringListAtFileName() {
        return itsUnusualStringListAtFileName;
    }
    
    private void __generateUnusualStringListAtFileName() {
        this.__setUnusualStringListAtFileName(new ArrayList<String>());
        
        this.__setUnusualStringListAtFileName().add(this.BACK_SLASH_STRING);
        this.__setUnusualStringListAtFileName().add(this.SLASH_STRING);
        this.__setUnusualStringListAtFileName().add(this.DOUBLE_DOT_STRING);
        this.__setUnusualStringListAtFileName().add(this.STAR_MARK_STRING);
        this.__setUnusualStringListAtFileName().add(this.QUESTION_MARK_STRING);
        this.__setUnusualStringListAtFileName().add(this.QUOTATION_MARK_STRING);
        this.__setUnusualStringListAtFileName().add(this.LESS_MARK_STRING);
        this.__setUnusualStringListAtFileName().add(this.BIGGER_MARK_STRING);
        this.__setUnusualStringListAtFileName().add(this.BAR_STRING);
    }
    
    public void writeDBFile(String theDir, String theCriterionKey) throws IOException {
        this.__writeValueListInOneKey(theDir);
        this.__writeValueListInTwoKey(theDir, theCriterionKey);

    }

    private void __writeValueListInTwoKey(String theDir, String theCriterionKey) throws IOException {
        for (String theKey : this.getPropertyMap().keySet()) {
            if (!theKey.equals(theCriterionKey)) {
                BufferedWriter theFileWriter;
                String theResultFileInformation = this.__getFileInformationAtTwoKey(theKey, theCriterionKey);
                StringBuilder theResultFilePath = new StringBuilder();

                theResultFilePath.append(this.__getBackSlashAtLastPosition(theDir)).append(this.__convertUnusualFileName(theCriterionKey)).append(this.UNDER_BAR).append(this.__convertUnusualFileName(theKey)).append(this.RESULT_FILE_SUFFIX);
                theFileWriter = new BufferedWriter(new FileWriter(theResultFilePath.toString()));
                
                theFileWriter.flush();
                theFileWriter.write(theResultFileInformation);
                theFileWriter.close();
            }
        }
    }

    private String __getFileInformationAtTwoKey(String theKey, String theCriterionKey) {
        StringBuilder theStringBuilder = new StringBuilder();

        theStringBuilder.append(theCriterionKey).append(this.CRITERION_DIVIDED_CELL).append(theKey).append(this.NEW_LINE);

        for (int vi = 0, vEnd = this.getPropertyMap().get(theKey).size(); vi < vEnd; vi++) {
            String theValue = this.getPropertyMap().get(theKey).get(vi);

            if (!theValue.contains(this.COMMA_STRING)) {
                theStringBuilder.append(this.getPropertyMap().get(theCriterionKey).get(vi)).append(this.CRITERION_DIVIDED_CELL).append(theValue).append(this.NEW_LINE);
            } else {
                theStringBuilder.append(this.__getValueStringContainingComma(this.getPropertyMap().get(theCriterionKey).get(vi), theValue));
            }
        }

        return theStringBuilder.toString();
    }

    private String __getValueStringContainingComma(String theCriterionValue, String theValue) {
        StringBuilder theStringBuilder = new StringBuilder();
        String[] theSplitedValue = theValue.split(this.COMMA_STRING);
        
        for(String theString : theSplitedValue) {
            theStringBuilder.append(theCriterionValue).append(this.CRITERION_DIVIDED_CELL).append(theString).append(this.NEW_LINE);
        }
        
        return theStringBuilder.toString();
    }
    
    private String __convertUnusualFileName(String theFileName) {
        for(String theUnusualString : this.getUnusualStringListAtFileName()) {
            if(theFileName.contains(theUnusualString)) {
                theFileName = this.__convertUnusualFileName(theFileName, theUnusualString);
            }
        }
        
        return theFileName;
    }
    
    private String __convertUnusualFileName(String theFileName, String theUnusualString) {
        StringBuilder theNewFileName = new StringBuilder();
        String[] theSplitedString = theFileName.split(theUnusualString);
        
        for(int si = 0 , sEnd = theSplitedString.length ; si < sEnd ; si++) {
            theNewFileName.append(theSplitedString[si]);
            
            if(si < sEnd - 1) {
                theNewFileName.append(this.CONVERTED_UNUSUAL_CHAR_IN_FILE_NAME);
            }
        }
        
        return theNewFileName.toString();
    }
    
    private void __writeValueListInOneKey(String theDir) throws IOException {
        for (String theKey : this.getPropertyMap().keySet()) {
            BufferedWriter theFileWriter = new BufferedWriter(new FileWriter(this.__getBackSlashAtLastPosition(theDir) + this.__convertUnusualFileName(theKey) + this.RESULT_FILE_SUFFIX));
            String theResultFileInformation = this.__getFileInformationAtOneKey(theKey);

            theFileWriter.flush();
            theFileWriter.write(theResultFileInformation);
            theFileWriter.close();
        }
    }

    private String __getFileInformationAtOneKey(String theKey) {
        StringBuilder theStringBuilder = new StringBuilder();

        theStringBuilder.append(theKey).append(this.NEW_LINE);

        for (String theValue : this.getPropertyMap().get(theKey)) {
            theStringBuilder.append(theValue).append(this.NEW_LINE);
        }

        return theStringBuilder.toString();
    }

    private String __getBackSlashAtLastPosition(String theDir) {
        if (theDir.charAt(theDir.length() - 1) != this.BACK_SLASH_CHAR) {
            theDir = theDir + this.BACK_SLASH_STRING;
        }

        return theDir;
    }

    public void writePropertyMapFromCsvFormatInOneFile(File theResultFile) throws IOException {
        BufferedWriter theFileWriter = new BufferedWriter(new FileWriter(theResultFile));
        String theFileInformation = this.__getPropertyMapByStringType();

        theFileWriter.flush();
        theFileWriter.write(theFileInformation);
        theFileWriter.close();
    }

    private String __getPropertyMapByStringType() {
        StringBuilder theStringBuilder = new StringBuilder();

        theStringBuilder.append(this.__getKeyListInformationInPropertyMap());

        for (int mi = 0, mEnd = this.getMoleculeSet().getMoleculeCount(); mi < mEnd; mi++) {
            theStringBuilder.append(this.__getValueListInformationInOneMolecule(mi));
        }

        return theStringBuilder.toString();
    }

    private String __getKeyListInformationInPropertyMap() {
        StringBuilder theStringBuilder = new StringBuilder();
        Set<String> theKeySet = this.getPropertyMap().keySet();
        Iterator theKeySetIterator = theKeySet.iterator();

        while (theKeySetIterator.hasNext()) {
            theStringBuilder.append(theKeySetIterator.next()).append(this.CRITERION_DIVIDED_CELL);
        }

        theStringBuilder.replace(theStringBuilder.length() - 1, theStringBuilder.length(), this.NEW_LINE);

        return theStringBuilder.toString();
    }

    private String __getValueListInformationInOneMolecule(int theMoleculeIndex) {
        List<String> theValueList = this.__getValueListInOneMolecule(theMoleculeIndex);
        StringBuilder theStringBuilder = new StringBuilder();

        for (int li = 0, lEnd = theValueList.size(); li < lEnd; li++) {
            theStringBuilder.append(this.__convertStringToComma(theValueList.get(li)));

            if (li < lEnd - 1) {
                theStringBuilder.append(this.CRITERION_DIVIDED_CELL);
            } else {
                theStringBuilder.append(this.NEW_LINE);
            }
        }

        return theStringBuilder.toString();
    }

    private String __convertStringToComma(String theValue) {
        StringBuilder theStringBuilder = new StringBuilder();
        String[] theSplitedString = theValue.split(",");

        for (int ai = 0, aEnd = theSplitedString.length; ai < aEnd; ai++) {
            theStringBuilder.append(theSplitedString[ai]);

            if (ai < aEnd - 1) {
                theStringBuilder.append(this.CONVERTED_COMMA_TO_STRING);
            }
        }

        return theStringBuilder.toString();
    }

    private List<String> __getValueListInOneMolecule(int theMoleculeIndex) {
        Set<String> theKeySet = this.getPropertyMap().keySet();
        List<String> theValueList = new ArrayList<>();

        for (String theKey : theKeySet) {
            theValueList.add(this.getPropertyMap().get(theKey).get(theMoleculeIndex));
        }

        return theValueList;
    }

    private void __generatePropertyMap() {
        this.setPropertyMap(new HashMap<String, List<String>>());

        for (int mi = 0, mEnd = this.getMoleculeSet().getMoleculeCount(); mi < mEnd; mi++) {
            this.__generatePropertyMapAtOneMolecule(mi);
        }
    }

    private void __generatePropertyMapAtOneMolecule(int theMoleculeIndex) {
        Set<Object> theKeySetInMolecule = this.getMoleculeSet().getMolecule(theMoleculeIndex).getProperties().keySet();
        Set<String> theKeySetInVariable = this.getPropertyMap().keySet();

        for (Object theKey : theKeySetInMolecule) {
            if (theKeySetInVariable.contains(theKey.toString())) {
                this.__addInformationToPropertyMap(theKey.toString(), this.getMoleculeSet().getMolecule(theMoleculeIndex).getProperty(theKey).toString());
            } else {
                this.__addFirstInformationToPropertyMap(theKey.toString(), this.getMoleculeSet().getMolecule(theMoleculeIndex).getProperty(theKey).toString(), theMoleculeIndex);
            }
        }

        if (theKeySetInMolecule.size() != theKeySetInVariable.size()) {
            for (Object theKey : theKeySetInVariable) {
                if (!theKeySetInMolecule.contains(theKey)) {
                    this.__addEmptyInformationToPropertyMap(theKey.toString());
                }
            }
        }
    }

    private void __addInformationToPropertyMap(String theKey, String theValue) {
        List<String> theValueList = this.getPropertyMap().get(theKey);

        theValueList.add(theValue);
        this.setPropertyMap().put(theKey, theValueList);
    }

    private void __addFirstInformationToPropertyMap(String theKey, String theValue, int theMoleculeIndex) {
        List<String> theValueList = this.__initializeValueList(theMoleculeIndex);

        theValueList.add(theValue);
        this.setPropertyMap().put(theKey, theValueList);
    }

    private void __addEmptyInformationToPropertyMap(String theKey) {
        List<String> theValueList = this.getPropertyMap().get(theKey);

        theValueList.add(new String());
        this.setPropertyMap().put(theKey, theValueList);
    }

    private List<String> __initializeValueList(int theMoleculeIndex) {
        List<String> theValueList = new ArrayList<>();

        for (int li = 0, lEnd = theMoleculeIndex; li < lEnd; li++) {
            theValueList.add(new String());
        }

        return theValueList;
    }

    private List<File> __getFileList(String theWorkDirPath) {
        List<File> theFileList = new ArrayList<>();
        File theWorkDir = new File(theWorkDirPath);

        if (theWorkDir.isDirectory()) {
            File theCheckFileList[] = theWorkDir.listFiles();

            for (int i = 0; i < theCheckFileList.length; i++) {
                if (theCheckFileList[i].isFile()) {
                    theFileList.add(theCheckFileList[i]);
                }
            }
        }

        return theFileList;
    }
}
