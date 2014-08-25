package org.bmdrc.mass.tool;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import org.bmdrc.mass.PeakList;
import org.bmdrc.mass.PeakList;
import org.bmdrc.mass.PeakUnit;
import org.bmdrc.mass.PeakUnit;
import org.bmdrc.util.Module;
import org.bmdrc.util.TwoDimensionList;

/**
 *
 * @author SungBo Hwang, CSB
 */
public class CommonPeakFinder {

    private List<PeakList> itsPeak2dList;
    private List<PeakList> itsCommonPeak2dList;
    private Double itsTolerence;
    private Integer itsNumberOfMoleculeTolerence;
    private List<Integer> itsNumberOfFilePathList;
    private Set<String> itsTotalFilePathSet;
    private TwoDimensionList<String> itsOutlierFilePath2dList;
    //constant int variable
    private final int FIRST_INDEX = 0;
    private final int MW_INDEX = 0;
    private final int INTENSITY_INDEX = 1;
    //constant String variable
    private final String TAB_STRING = "\t";

    public CommonPeakFinder() {
        this.setPeak2dList(new ArrayList<PeakList>());
        this.setCommonPeak2dList(new ArrayList<PeakList>());
        this.setTolerence(Double.NaN);
        this.setNumberOfMoleculeTolerence(0);
        this.setNumberOfFilePathList(new ArrayList<Integer>());
        this.setTotalFilePathSet(new HashSet<String>());
        this.setOutlierFilePath2dList(new TwoDimensionList<String>());
    }

    public CommonPeakFinder(String theInputDir, Double theTolerence, Integer theNumberOfMoleculeTolerence, String theColumnSeparator) throws FileNotFoundException, IOException {
        this.setPeak2dList(new ArrayList<PeakList>());
        this.setCommonPeak2dList(new ArrayList<PeakList>());
        this.setTolerence(theTolerence);
        this.setNumberOfMoleculeTolerence(theNumberOfMoleculeTolerence);
        this.setNumberOfFilePathList(new ArrayList<Integer>());
        this.__generateTotalFilePathSet();
        this.setOutlierFilePath2dList(new TwoDimensionList<String>());

        this.openDir(theInputDir, theColumnSeparator);
        this.generateCommonPeak2dList(theInputDir, theColumnSeparator);
    }

    public CommonPeakFinder(List<PeakList> thePeak2dList, Double theTolerence, Integer theNumberOfMoleculeTolerence) {
        this.setPeak2dList(thePeak2dList);
        this.setTolerence(theTolerence);
        this.setCommonPeak2dList(new ArrayList<PeakList>());
        this.setNumberOfMoleculeTolerence(theNumberOfMoleculeTolerence);
        this.setNumberOfFilePathList(new ArrayList<Integer>());
        this.__generateTotalFilePathSet();
        this.setOutlierFilePath2dList(new TwoDimensionList<String>());
    }

    public List<PeakList> getPeak2dList() {
        return itsPeak2dList;
    }

    public void setPeak2dList(List<PeakList> thePeak2dList) {
        this.itsPeak2dList = thePeak2dList;
    }

    public List<PeakList> setPeak2dList() {
        return itsPeak2dList;
    }

    public List<PeakList> getCommonPeak2dList() {
        return itsCommonPeak2dList;
    }

    public void setCommonPeak2dList(List<PeakList> theCommonPeak2dList) {
        this.itsCommonPeak2dList = theCommonPeak2dList;
    }

    public List<PeakList> setCommonPeak2dList() {
        return itsCommonPeak2dList;
    }

    public Double getTolerence() {
        return itsTolerence;
    }

    public void setTolerence(Double theTolerence) {
        this.itsTolerence = theTolerence;
    }

    public Integer getNumberOfMoleculeTolerence() {
        return itsNumberOfMoleculeTolerence;
    }

    public void setNumberOfMoleculeTolerence(Integer theNumberOfMoleculeTolerence) {
        this.itsNumberOfMoleculeTolerence = theNumberOfMoleculeTolerence;
    }

    public List<Integer> getNumberOfFilePathList() {
        return itsNumberOfFilePathList;
    }

    public void setNumberOfFilePathList(List<Integer> itsNumberOfFilePathList) {
        this.itsNumberOfFilePathList = itsNumberOfFilePathList;
    }

    public List<Integer> setNumberOfFilePathList() {
        return itsNumberOfFilePathList;
    }

    public Set<String> getTotalFilePathSet() {
        return itsTotalFilePathSet;
    }

    public void setTotalFilePathSet(Set<String> itsTotalFilePathSet) {
        this.itsTotalFilePathSet = itsTotalFilePathSet;
    }
    
    public Set<String> setTotalFilePathSet() {
        return itsTotalFilePathSet;
    }

    public TwoDimensionList<String> getOutlierFilePath2dList() {
        return itsOutlierFilePath2dList;
    }

    public void setOutlierFilePath2dList(TwoDimensionList<String> itsOutlierFilePath2dList) {
        this.itsOutlierFilePath2dList = itsOutlierFilePath2dList;
    }
    
    public TwoDimensionList<String> setOutlierFilePath2dList() {
        return itsOutlierFilePath2dList;
    }
    
    public void openDir(String theInputDir, String theColumnSeparator) throws FileNotFoundException, IOException {
        List<File> theFileList = Module.getFileList(theInputDir);

        this.setPeak2dList(new ArrayList<PeakList>());

        for (File theFile : theFileList) {
            this.setPeak2dList().add(this.__openFile(theFile, theColumnSeparator));
        }
    }

    private PeakList __openFile(File theInputFile, String theColumnSeparator) throws FileNotFoundException, IOException {
        BufferedReader theFileReader = new BufferedReader(new FileReader(theInputFile));
        String theFileString = new String();
        PeakList thePeakList = new PeakList();

        while ((theFileString = theFileReader.readLine()) != null) {
            String[] theSplitedString = theFileString.split(theColumnSeparator);

            thePeakList.addPeak(new PeakUnit(Double.parseDouble(theSplitedString[this.MW_INDEX]), Double.parseDouble(theSplitedString[this.INTENSITY_INDEX]), theInputFile.toString()));
        }

        return thePeakList;
    }

    public List<PeakList> generateCommonPeak2dList(String theInputDir, String theColumnSeparator) throws FileNotFoundException, IOException {
        this.openDir(theInputDir, theColumnSeparator);

        return this.generateCommonPeak2dList();
    }

    public List<PeakList> generateCommonPeak2dList() {
        PeakList theTotalPeakList = this.__getTotalPeakList();
        
        this.setCommonPeak2dList(new ArrayList<PeakList>());

        for (int pi = 0, pEnd = theTotalPeakList.size() - this.getPeak2dList().size(); pi <= pEnd; pi++) {
            PeakList theCommonPeakList = this.__getCommonPeakList(theTotalPeakList, pi, this.getPeak2dList().size());
            
            if (!this.__containsSameList(this.getCommonPeak2dList(), theCommonPeakList)) {
                this.__generateCommonPeak2dList(theCommonPeakList, this.getPeak2dList().size());
            }
        }

        return this.getCommonPeak2dList();
    }

    private boolean __containsSameList(List<PeakList> theCommonPeak2dList, PeakList theTestPeakList) {
        for (PeakList thePeakList : theCommonPeak2dList) {
            if (thePeakList.size() == theTestPeakList.size() && this.__containsSameList(thePeakList, theTestPeakList)) {
                return true;
            }
        }

        return false;
    }

    private boolean __containsSameList(PeakList theFirstPeakList, PeakList theSecondPeakList) {
        for (int pi = 0, pEnd = theFirstPeakList.size(); pi < pEnd; pi++) {
            if (theFirstPeakList.get(pi).getWeight() != theSecondPeakList.get(pi).getWeight()) {
                return false;
            }
        }

        return true;
    }

    private PeakList __getCommonPeakList(PeakList thePeakList, int theStartIndex, int theNumberOfFile) {
        PeakList theCommonPeakList = new PeakList();

        for (int pi = theStartIndex, pEnd = theStartIndex + theNumberOfFile; pi < pEnd; pi++) {
            theCommonPeakList.addPeak(thePeakList.get(pi));
        }

        return theCommonPeakList;
    }

    private void __generateCommonPeak2dList(PeakList theTestPeakList, int theNumberOfFile) {
        Set<String> theExperimentFilePathSet = new HashSet<>();

        for (PeakUnit thePeak : theTestPeakList.getPeakList()) {
            theExperimentFilePathSet.add(thePeak.getExperimentFile());
        }

        if (theExperimentFilePathSet.size() >= theNumberOfFile - this.itsNumberOfMoleculeTolerence && this.__containsRange(theTestPeakList)) {
            this.setCommonPeak2dList().add(theTestPeakList);
            this.setNumberOfFilePathList().add(theExperimentFilePathSet.size());
            this.setOutlierFilePath2dList().add(this.__getOutlierFilePathList(theExperimentFilePathSet));
        }
    }

    private List<String> __getOutlierFilePathList(Set<String> theExperimentFilePathSet) {
        List<String> theOutlierFilePathList = new ArrayList<>();
        
        for(String theFilePath : this.getTotalFilePathSet()) {
            if(!theExperimentFilePathSet.contains(theFilePath)) {
                theOutlierFilePathList.add(theFilePath);
            }
        }
        
        return theOutlierFilePathList;
    }
    
    private boolean __containsRange(PeakList thePeakList) {
        Double theMinimumWeight = thePeakList.get(this.FIRST_INDEX).getWeight();
        Double theMaximumWeight = thePeakList.get(thePeakList.size() - 1).getWeight();

        return theMaximumWeight - theMinimumWeight < this.getTolerence();
    }

    private PeakList __getTotalPeakList() {
        PeakList theTotalPeakList = new PeakList();

        for (PeakList thePeakList : this.getPeak2dList()) {
            theTotalPeakList.addAll(thePeakList);
        }

        theTotalPeakList.sort();

        return theTotalPeakList;
    }
    
     private void __generateTotalFilePathSet() {
         this.setTotalFilePathSet(new HashSet<String>());
         
         for(PeakList thePeakList : this.getPeak2dList()) {
             for(PeakUnit thePeak : thePeakList.getPeakList()) {
                 this.setTotalFilePathSet().add(thePeak.getExperimentFile());
             }
         }
     }
}
