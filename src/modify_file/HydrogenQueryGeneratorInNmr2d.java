/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this file,
 * You can obtain one at http://mozilla.org/MPL/2.0/.
 *
 * Copyright (C) 2014, SungBo Hwang <tyamazaki@naver.com>.
 */
package modify_file;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import org.bmdrc.gui.MainFrame;
import org.bmdrc.interfaces.IStringConstant;
import org.bmdrc.nmr.Nmr1dUnit;
import org.bmdrc.nmr.Nmr1dUnitList;
import org.bmdrc.nmr.Nmr2dCHUnit;
import org.bmdrc.nmr.Nmr2dCHUnitList;
import org.bmdrc.nmr.tool.HydrogenQuery;
import org.bmdrc.util.ArrayListManipulator;
import org.bmdrc.util.TwoDimensionList;

/**
 *
 * @author SungBo Hwang <tyamazaki@naver.com>
 */
public class HydrogenQueryGeneratorInNmr2d implements Serializable, IStringConstant {

    private static final long serialVersionUID = 3533835931823998700L;
    private MainFrame itsFrame;
    private Double itsNmrResolution;
    private Double itsCoefficientOfSN;
    private String itsHMBCFilePath;
    private String itsHMQCFilePath;
    private String its1HNMRFilePath;
    private String its13CNMRFilePath;
    private Nmr1dUnitList its1HPeakList;
    private Nmr1dUnitList its13CPeakList;
    private Nmr2dCHUnitList itsHMQCPeakList;
    private Nmr2dCHUnitList itsStartPeakListInHMBC;
    private Nmr2dCHUnitList itsStartPeakListInHMQC;
    private TwoDimensionList<Integer> itsIndexArrayOfMatchedCarbonPeakInNotHetero;
    //constant string variable
    private final String UNUSUAL_STRING = "?-?";
    //constant int variable
    private final Integer CARBON_CHEMICAL_SHIFT_2D_INDEX_IN_SPARKY = 0;
    private final Integer CARBON_CHEMICAL_SHIFT_2D_INDEX_IN_ACD = 2;
    private final Integer HYDROGEN_CHEMICAL_SHIFT_2D_INDEX_IN_SPARKY = 1;
    private final Integer HYDROGEN_CHEMICAL_SHIFT_2D_INDEX_IN_ACD = 1;
    private final Integer INTENSITY_2D_INDEX_IN_SPARKY = 2;
    private final Integer INTENSITY_2D_INDEX_IN_ACD = 5;
    private final Integer CHEMICAL_SHIFT_1D_INDEX_IN_ACD = 1;
    private final Integer INTENSITY_1D_INDEX_IN_ACD = 2;
    private final Integer MIN_CHEMICAL_SHIFT_1D_IN_ACD = 0;
    private final Integer MAX_CHEMICAL_SHIFT_1D_IN_ACD = 2;
    private final Integer SPARKY_FILE_FORMAT = 0;
    private final Integer ACD_FILE_FORMAT = 1;
    private final Integer FIRST_INDEX = 0;
    //constant double variable
    private final Double MAXIMUM_CARBON_NMR_CHEMICAL_SHIFT = 200.0;
    private final Double MAXIMUM_HYDROGEN_NMR_CHEMICAL_SHIFT = 200.0;

    public HydrogenQueryGeneratorInNmr2d() {
        this.set1HNMRFilePath(new String());
        this.set13CNMRFilePath(new String());
        this.setHMQCFilePath(new String());
        this.setHMBCFilePath(new String());
        this.set1HPeakList(new Nmr1dUnitList());
        this.set13CPeakList(new Nmr1dUnitList());
        this.setHMQCPeakList(new Nmr2dCHUnitList());
        this.__setStartPeakListInHMBC(new Nmr2dCHUnitList());
        this.__setStartPeakListInHMQC(new Nmr2dCHUnitList());
    }

    public HydrogenQueryGeneratorInNmr2d(MainFrame itsFrame) {
        this.itsFrame = itsFrame;
    }

    public MainFrame getFrame() {
        return itsFrame;
    }

    public void setFrame(MainFrame theFrame) {
        this.itsFrame = theFrame;
    }

    public MainFrame setFrame() {
        return itsFrame;
    }

    public String getHMBCFilePath() {
        return itsHMBCFilePath;
    }

    public void setHMBCFilePath(String theHMBCFilePath) {
        this.itsHMBCFilePath = theHMBCFilePath;
    }

    public String getHMQCFilePath() {
        return itsHMQCFilePath;
    }

    public void setHMQCFilePath(String theHMQCFilePath) {
        this.itsHMQCFilePath = itsHMQCFilePath;
    }

    public String get1HNMRFilePath() {
        return its1HNMRFilePath;
    }

    public void set1HNMRFilePath(String the1HNMRFilePath) {
        this.its1HNMRFilePath = the1HNMRFilePath;
    }

    public String get13CNMRFilePath() {
        return its13CNMRFilePath;
    }

    public void set13CNMRFilePath(String the13CNMRFilePath) {
        this.its13CNMRFilePath = the13CNMRFilePath;
    }

    public Nmr1dUnitList get1HPeakList() {
        return its1HPeakList;
    }

    public void set1HPeakList(Nmr1dUnitList the1HPeakList) {
        this.its1HPeakList = the1HPeakList;
    }

    public Nmr1dUnitList get13CPeakList() {
        return its13CPeakList;
    }

    public void set13CPeakList(Nmr1dUnitList the13CPeakList) {
        this.its13CPeakList = the13CPeakList;
    }

    public Nmr2dCHUnitList getHMQCPeakList() {
        return itsHMQCPeakList;
    }

    public void setHMQCPeakList(Nmr2dCHUnitList theHMQCPeakList) {
        this.itsHMQCPeakList = theHMQCPeakList;
    }

    public TwoDimensionList<Integer> getIndexArrayOfMatchedCarbonPeakInNotHetero() {
        return itsIndexArrayOfMatchedCarbonPeakInNotHetero;
    }

    public void setIndexArrayOfMatchedCarbonPeakInNotHetero(TwoDimensionList<Integer> itsIndexArrayOfMatchedCarbonPeakInNotHetero) {
        this.itsIndexArrayOfMatchedCarbonPeakInNotHetero = itsIndexArrayOfMatchedCarbonPeakInNotHetero;
    }

    public TwoDimensionList<Integer> setIndexArrayOfMatchedCarbonPeakInNotHetero() {
        return itsIndexArrayOfMatchedCarbonPeakInNotHetero;
    }

    public Double getNmrResolution() {
        return itsNmrResolution;
    }

    public void setNmrResolution(Double theNmrResolution) {
        this.itsNmrResolution = theNmrResolution;
    }

    public Double getCoefficientOfSN() {
        return itsCoefficientOfSN;
    }

    public void setCoefficientOfSN(Double theCoefficientOfSN) {
        this.itsCoefficientOfSN = theCoefficientOfSN;
    }

    private Nmr2dCHUnitList __getStartPeakListInHMBC() {
        return itsStartPeakListInHMBC;
    }

    private void __setStartPeakListInHMBC(Nmr2dCHUnitList theStartPeakListInHMBC) {
        this.itsStartPeakListInHMBC = theStartPeakListInHMBC;
    }

    private Nmr2dCHUnitList __setStartPeakListInHMBC() {
        return itsStartPeakListInHMBC;
    }

    private Nmr2dCHUnitList __getStartPeakListInHMQC() {
        return itsStartPeakListInHMQC;
    }

    private void __setStartPeakListInHMQC(Nmr2dCHUnitList theStartPeakListInHMQC) {
        this.itsStartPeakListInHMQC = theStartPeakListInHMQC;
    }

    private Nmr2dCHUnitList __setStartPeakListInHMQC() {
        return itsStartPeakListInHMQC;
    }

    public List<HydrogenQuery> get2DNMRHydrogenQuery(List<Integer> theIndexArrayOfSorted3DArray, List<Integer> theNumberArrayOfBoundHydrogen, Integer theTotalNumberOfCarbon,
            Integer theTotalNumberOfHydrogen, Integer theTypeOfInputFileFormat) throws FileNotFoundException, IOException {
        this.set1HPeakList(this.__read1DNMRACDLabFileFormat(this.get1HNMRFilePath(), theTotalNumberOfHydrogen));
        this.set13CPeakList(this.__read1DNMRACDLabFileFormat(this.get13CNMRFilePath(), theTotalNumberOfCarbon));
        this.setHMQCPeakList(this.__convertMedianPeakInHMQC(theTypeOfInputFileFormat));

        Nmr2dCHUnitList theMedianPeakInMergeHMQCAndHMBC = this.__convertMedianPeakInMergeHMBCAndHMQC(theTotalNumberOfCarbon, theTotalNumberOfHydrogen, theTypeOfInputFileFormat);
        List<Nmr2dCHUnitList> theSortedByCarbonPeak = this.__sortedByShift(theMedianPeakInMergeHMQCAndHMBC, this.get13CPeakList(), Nmr2dCHUnit.CARBON_SHIFT_INDEX);
        List<Nmr2dCHUnitList> theSortedByHydrogenPeak = this.__sortedByShift(theMedianPeakInMergeHMQCAndHMBC, this.get1HPeakList(), Nmr2dCHUnit.HYDROGEN_SHIFT_INDEX);

        this.__setStartPeak(theMedianPeakInMergeHMQCAndHMBC);
        this.__printInitializeCondition(theSortedByCarbonPeak, theSortedByHydrogenPeak);

        theIndexArrayOfSorted3DArray = this.__getIndexArrayOfSorted3DArray(theSortedByCarbonPeak);
        this.__setNumberArrayOfBoundHydrogen(theNumberArrayOfBoundHydrogen, theIndexArrayOfSorted3DArray);

        return this.__makeHydrogenQueryList(theSortedByHydrogenPeak, theSortedByCarbonPeak, theIndexArrayOfSorted3DArray, theNumberArrayOfBoundHydrogen);
    }

    private List<HydrogenQuery> __makeHydrogenQueryList(List<Nmr2dCHUnitList> theSortedByHydrogenPeak, List<Nmr2dCHUnitList> theSortedByCarbonPeak, List<Integer> theIndexArrayOfSorted3DArray,
            List<Integer> theNumberArrayOfBoundHydrogen) {
        List<HydrogenQuery> theHydrogenTypeMatrixIn2DNMR = new ArrayList<>();

        for (int i = this.__getStartPeakListInHMBC().size() - 1; i >= 0; i--) {
            if (this.__isSameHydrogenPeak(theSortedByHydrogenPeak, i)) {
                this.__getStartPeakListInHMBC().remove(i);
            } else {
                List<Integer> theArrayMatchedSameHydrogenChemicalShift = new ArrayList<>();
                HydrogenQuery theHydrogenTypeArray = this.__makeHydrogenQuery(theSortedByHydrogenPeak, theSortedByCarbonPeak, theIndexArrayOfSorted3DArray,
                        theNumberArrayOfBoundHydrogen, this.__getStartPeakListInHMBC().get(i));

                theHydrogenTypeMatrixIn2DNMR.add(theHydrogenTypeArray);
            }
        }

        return theHydrogenTypeMatrixIn2DNMR;
    }

    private HydrogenQuery __makeHydrogenQuery(List<Nmr2dCHUnitList> theSortedByHydrogenPeak, List<Nmr2dCHUnitList> theSortedByCarbonPeak, List<Integer> theIndexArrayOfSorted3DArray,
            List<Integer> theNumberArrayOfBoundHydrogen, Nmr2dCHUnit theStartPeakInHMBC) {
        List<Integer> theArrayMatchedSameHydrogenChemicalShift = new ArrayList<>();
        HydrogenQuery theHydrogenQuery = new HydrogenQuery();
        int theIndexOfSortedByHydrogenPeak = this.__getIndexOfSamePeak(theSortedByHydrogenPeak, theStartPeakInHMBC);

        for (int j = 0; j < theSortedByHydrogenPeak.get(theIndexOfSortedByHydrogenPeak).size(); j++) {
            int theIndexOfSortedByCarbonPeak = this.__getIndexOfSamePeak(theSortedByCarbonPeak, theSortedByHydrogenPeak.get(theIndexOfSortedByHydrogenPeak).get(j));

            theArrayMatchedSameHydrogenChemicalShift.add(theIndexOfSortedByCarbonPeak);

            if (!theIndexArrayOfSorted3DArray.contains(theIndexOfSortedByCarbonPeak)) {
                theHydrogenQuery.setNumberOfRHGroup(theHydrogenQuery.getNumberOfRHGroup() + 1);
            } else {
                int theIndexOfArray = theIndexArrayOfSorted3DArray.indexOf(theIndexOfSortedByCarbonPeak);
                int theIndex = theNumberArrayOfBoundHydrogen.get(theIndexOfArray) - 1;

                theHydrogenQuery.set(theIndex, theHydrogenQuery.get(theIndex) + 1);
            }
        }

        this.setIndexArrayOfMatchedCarbonPeakInNotHetero().add(theArrayMatchedSameHydrogenChemicalShift);

        return theHydrogenQuery;
    }

    private void __setNumberArrayOfBoundHydrogen(List<Integer> theNumberArrayOfBoundHydrogen, List<Integer> theIndexArrayOfSorted3DArray) {
        theNumberArrayOfBoundHydrogen = this.__getNumberArrayOfBoundHydrogen(theIndexArrayOfSorted3DArray);

        for (Object the1HPeak : this.get1HPeakList()) {
            List<Integer> theIndexArrayMatchedBetween1HAndHMBCPeak = this.__getIndexArrayMatchedBetween1HAndHMBCPeak((Nmr1dUnit) the1HPeak);
            int theNumberOfHydrogenInPeak = (int) Math.round(((Nmr1dUnit) the1HPeak).getIntensity());

            if (!theIndexArrayMatchedBetween1HAndHMBCPeak.isEmpty()) {
                this.__setNumberArrayOfBoundHydrogen((Nmr1dUnit) the1HPeak, theIndexArrayMatchedBetween1HAndHMBCPeak, theNumberArrayOfBoundHydrogen, theNumberOfHydrogenInPeak);
            }
        }
    }

    private void __setNumberArrayOfBoundHydrogen(Nmr1dUnit the1HPeak, List<Integer> theIndexArrayMatchedBetween1HAndHMBCPeak, List<Integer> theNumberArrayOfBoundHydrogen,
            Integer theNumberOfHydrogenInPeak) {
        if (theNumberOfHydrogenInPeak % theIndexArrayMatchedBetween1HAndHMBCPeak.size() == 0 && (theNumberOfHydrogenInPeak / theIndexArrayMatchedBetween1HAndHMBCPeak.size()) < 4) {
            for (int j = 0; j < theIndexArrayMatchedBetween1HAndHMBCPeak.size(); j++) {
                theNumberArrayOfBoundHydrogen.set(theIndexArrayMatchedBetween1HAndHMBCPeak.get(j),
                        theNumberArrayOfBoundHydrogen.get(theIndexArrayMatchedBetween1HAndHMBCPeak.get(j)) + (theNumberOfHydrogenInPeak / theIndexArrayMatchedBetween1HAndHMBCPeak.size()));
            }
        } else {
            this.setFrame().setLogTextArea().append("Number of Hydrogen Error!!");
            this.setFrame().setLogTextArea().append(the1HPeak.toString() + "\n");
            this.setFrame().setLogTextArea().append(theIndexArrayMatchedBetween1HAndHMBCPeak.toString() + " ");
            this.setFrame().setLogTextArea().append(theNumberOfHydrogenInPeak % theIndexArrayMatchedBetween1HAndHMBCPeak.size() + " Error!!\n");
        }
    }

    private List<Integer> __getIndexArrayMatchedBetween1HAndHMBCPeak(Nmr1dUnit the1HNmrPeak) {
        List<Integer> theIndexArrayMatchedBetween1HAndHMBCPeak = new ArrayList<>();

        for (int j = 0; j < this.__getStartPeakListInHMBC().size(); j++) {
            if (this.__containHydrogenRange(this.__getStartPeakListInHMBC().get(j), the1HNmrPeak)) {
                theIndexArrayMatchedBetween1HAndHMBCPeak.add(j);
            }
        }

        return theIndexArrayMatchedBetween1HAndHMBCPeak;
    }

    private List<Integer> __getNumberArrayOfBoundHydrogen(List<Integer> theIndexArrayOfSorted3DArray) {
        List<Integer> theNumberArrayOfBoundHydrogen = new ArrayList<>();

        for (int i = 0; i < theIndexArrayOfSorted3DArray.size(); i++) {
            theNumberArrayOfBoundHydrogen.add(0);
        }

        return theNumberArrayOfBoundHydrogen;
    }

    private List<Integer> __getIndexArrayOfSorted3DArray(List<Nmr2dCHUnitList> theSortedByCarbonShift) {
        List<Integer> theIndexArrayOfSorted3DArray = new ArrayList<>();

        for (Object thePeak : this.__getStartPeakListInHMBC()) {
            theIndexArrayOfSorted3DArray.add(this.__getIndexOfSamePeakIn3D(theSortedByCarbonShift, (Nmr2dCHUnit) thePeak));
        }

        return theIndexArrayOfSorted3DArray;
    }

    private Integer __getIndexOfSamePeakIn3D(List<Nmr2dCHUnitList> thePeak2dList, Nmr2dCHUnit theCheckPeak) {
        int theIndex = 0;

        for (Object thePeakList : thePeak2dList) {
            for (Object thePeak : (Nmr2dCHUnitList) thePeakList) {
                if (((Nmr2dCHUnit) thePeak).toString().equals(theCheckPeak.toString())) {
                    return theIndex;
                }
            }
            theIndex++;
        }

        return -1;
    }

    private Integer __getMinimumSizeOfSameCarbonPeakSet(List<Nmr2dCHUnitList> theSortedByCarbonShift) {
        Integer theMinimumSizeOfSameCarbonPeakSet = 0;

        for (int i = 0; i < theSortedByCarbonShift.size(); i++) {
            if (i == 0 || theMinimumSizeOfSameCarbonPeakSet > theSortedByCarbonShift.get(i).size()) {
                theMinimumSizeOfSameCarbonPeakSet = theSortedByCarbonShift.get(i).size();
            }
        }

        return theMinimumSizeOfSameCarbonPeakSet;
    }

    private void __printInitializeCondition(List<Nmr2dCHUnitList> theSortedByCarbonShift, List<Nmr2dCHUnitList> theSortedByHydrogenPeak) {
        this.setFrame().setLogTextArea().append("The Number of Carbon Class : ");
        this.setFrame().setLogTextArea().append(Integer.toString(theSortedByCarbonShift.size()));
        this.setFrame().setLogTextArea().append(this.END_LINE);
        this.setFrame().setLogTextArea().append("The Number of Hydrogen Class : ");
        this.setFrame().setLogTextArea().append(Integer.toString(theSortedByHydrogenPeak.size()));
        this.setFrame().setLogTextArea().append(this.END_LINE);
    }

    private void __setStartPeak(Nmr2dCHUnitList theMedianPeakInMergeHMQCAndHMBC) {
        Nmr2dCHUnitList theStartPeakSetInHMQC = new Nmr2dCHUnitList();

        for (Object thePeak : this.getHMQCPeakList()) {
            Nmr2dCHUnit theClosestPeak = this.__getClosestPeak(theMedianPeakInMergeHMQCAndHMBC, (Nmr2dCHUnit) thePeak);

            this.__setStartPeakListInHMBC().addPeak(theClosestPeak);
            this.__setStartPeakListInHMQC().addPeak((Nmr2dCHUnit) thePeak);
        }
    }

    private Nmr2dCHUnitList __readNMRDataFile(String thePeakDataFilePath, Integer theTypeOfInputFileFormat) throws FileNotFoundException, IOException {
        List<String> theFileInformationList = this.__readLines(thePeakDataFilePath);
        String theResultFileInformation = new String();
        Nmr2dCHUnitList thePeakData = new Nmr2dCHUnitList();
        int theLine = 2;

        theFileInformationList.remove(this.FIRST_INDEX);
        theFileInformationList.remove(this.FIRST_INDEX);

        for (String theFileString : theFileInformationList) {
            if (!theFileString.isEmpty()) {
                if (theTypeOfInputFileFormat == this.SPARKY_FILE_FORMAT) {
                    thePeakData.addPeak(this.__getPeakDataInSparkFormat(theFileString));
                } else if (theTypeOfInputFileFormat == this.ACD_FILE_FORMAT) {
                    try {
                        thePeakData.addPeak(this.__readACDLabFileFormat(theFileString));
                    } catch (NumberFormatException e) {
                        this.setFrame().setLogTextArea().append(thePeakDataFilePath + " " + theLine++ + "th Line is Error!!\n");
                    }
                }
            }
        }

        return thePeakData;
    }

    private Nmr2dCHUnit __getPeakDataInSparkFormat(String theFileString) {
        String[] theSplitedString = theFileString.split(this.SPACE_STRING);
        Nmr2dCHUnit thePeak = new Nmr2dCHUnit();

        thePeak.setCarbonShift(Double.parseDouble(theSplitedString[this.CARBON_CHEMICAL_SHIFT_2D_INDEX_IN_SPARKY]));
        thePeak.setHydrogenShift(Double.parseDouble(theSplitedString[this.HYDROGEN_CHEMICAL_SHIFT_2D_INDEX_IN_SPARKY]));
        thePeak.setIntensity(Double.parseDouble(theSplitedString[this.INTENSITY_2D_INDEX_IN_SPARKY]));

        return thePeak;
    }

    private Nmr2dCHUnit __readACDLabFileFormat(String theFileString) throws NumberFormatException {
        String[] theSplitedString = theFileString.split(this.TAB_STRING);
        Nmr2dCHUnit thePeak = new Nmr2dCHUnit();

        if (!theFileString.isEmpty() && theSplitedString.length >= 8) {
            thePeak.setCarbonShift(Double.parseDouble(theSplitedString[this.CARBON_CHEMICAL_SHIFT_2D_INDEX_IN_ACD]));
            thePeak.setHydrogenShift(Double.parseDouble(theSplitedString[this.HYDROGEN_CHEMICAL_SHIFT_2D_INDEX_IN_ACD]));
            thePeak.setIntensity(Double.parseDouble(theSplitedString[this.INTENSITY_2D_INDEX_IN_ACD]));
        }

        return thePeak;
    }

    private List<String> __readLines(String theInputFilePath) throws FileNotFoundException, IOException {
        List<String> theFileStringList = new ArrayList<>();
        BufferedReader theFileReader = new BufferedReader(new FileReader(theInputFilePath));
        String theFileString = new String();

        while ((theFileString = theFileReader.readLine()) != null) {
            theFileStringList.add(theFileString);
        }

        return theFileStringList;
    }

    private Nmr2dCHUnitList __convertMedianPeakInMergeHMBCAndHMQC(Integer theNumberOfCarbon, Integer theNumberOfHydrogen, Integer theTypeOfInputFileFormat) {
        Nmr2dCHUnitList theHMQCPeakData = new Nmr2dCHUnitList();
        Nmr2dCHUnitList theHMBCPeakData = new Nmr2dCHUnitList();
        Nmr1dUnitList the1HPeakData = this.__read1DNMRACDLabFileFormat(this.get1HNMRFilePath(), theNumberOfHydrogen);;
        Nmr1dUnitList the13CPeakData = this.__read1DNMRACDLabFileFormat(this.get13CNMRFilePath(), theNumberOfCarbon);
        Nmr2dCHUnitList theMedianPeakInHMBC = new Nmr2dCHUnitList();
        Nmr2dCHUnitList theMedianPeakInHMQC = new Nmr2dCHUnitList();

        List<Nmr1dUnitList> theSortedByCarbonPeak = new ArrayList<>();
        List<Nmr1dUnitList> theSortedByHydrogenPeak = new ArrayList<>();
        try {
            theHMQCPeakData = this.__readNMRDataFile(this.getHMQCFilePath(), theTypeOfInputFileFormat);
            theHMBCPeakData = this.__readNMRDataFile(this.getHMBCFilePath(), theTypeOfInputFileFormat);
        } catch (FileNotFoundException ex) {
            JOptionPane.showMessageDialog(null, "2D NMR File is not existed!!", "Error", JOptionPane.ERROR_MESSAGE);
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(null, "convertMedianPeakInMergeHMBCAndHMQC IOException!!", "Error", JOptionPane.ERROR_MESSAGE);
        }


        //theHMBCPeakData.addAll(theHMQCPeakData);
        theMedianPeakInHMBC = convertMedianPeak(theHMBCPeakData, the1HPeakData, the13CPeakData);
        theMedianPeakInHMQC = convertMedianPeak(theHMQCPeakData, the1HPeakData, the13CPeakData);

        for (int i = theMedianPeakInHMBC.size() - 1; i >= 0; i--) {
            if (this.__isNoisePeak(theMedianPeakInHMBC.get(i), theNumberOfCarbon, theNumberOfHydrogen)) {
                theMedianPeakInHMBC.remove(i);
            }
        }

        theMedianPeakInHMBC.addPeakList(theMedianPeakInHMQC);

        return theMedianPeakInHMBC;
    }

    private Nmr1dUnitList __read1DNMRACDLabFileFormat(String theFilePath, Integer theNumberOfAtom) {
        Nmr1dUnitList theNMRData = new Nmr1dUnitList();
        Nmr1dUnitList theCopiedNMRData = new Nmr1dUnitList();

        try {
            theNMRData = this.__getPeakInACDFormat(new File(theFilePath));
            Comparator<Nmr1dUnit> theComparator = new Comparator() {
                @Override
                public int compare(Object theFirstPeak, Object theSecondPeak) {
                    return ((Nmr1dUnit) theFirstPeak).getIntensity().compareTo(((Nmr1dUnit) theSecondPeak).getIntensity());
                }
            };

            Collections.sort(theNMRData.getPeakList(), theComparator);
        } catch (FileNotFoundException ex) {
            ex.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return this.__modifyNmrData(theNMRData, theNumberOfAtom);
    }

    private Nmr1dUnitList __modifyNmrData(Nmr1dUnitList theNMRData, Integer theNumberOfAtom) {
        for (Object thePeak : theNMRData) {
            Nmr1dUnitList theCopiedNMRData = new Nmr1dUnitList(theNMRData);
            double theMinimumIntensity = ((Nmr1dUnit) thePeak).getIntensity();
            int theNumberOfHydrogenInCase = 0;


            for (Object theCopiedPeak : theCopiedNMRData) {
                ((Nmr1dUnit) theCopiedPeak).setIntensity(((Nmr1dUnit) theCopiedPeak).getIntensity() / theMinimumIntensity);
                theNumberOfHydrogenInCase += (int) Math.round(((Nmr1dUnit) theCopiedPeak).getIntensity());
            }

            if (theNumberOfAtom >= theNumberOfHydrogenInCase) {
                return theCopiedNMRData;
            }
        }

        return new Nmr1dUnitList();
    }

    private Nmr1dUnitList __getPeakInACDFormat(File theACDFile) throws FileNotFoundException, IOException {
        BufferedReader theFileReader = new BufferedReader(new FileReader(theACDFile));
        String theFileInformationByLine = new String();
        Nmr1dUnitList thePeakList = new Nmr1dUnitList();

        theFileReader.readLine();
        theFileReader.readLine();

        while ((theFileInformationByLine = theFileReader.readLine()) != null) {
            thePeakList.addPeak(this.__getPeakInACDFormat(theFileInformationByLine));
        }

        return thePeakList;
    }

    private Nmr1dUnit __getPeakInACDFormat(String theFileInformation) {

        String[] theSplitedString = theFileInformation.split(this.TAB_STRING);
        String[] theRangeArray = null;
        Nmr1dUnit thePeak = new Nmr1dUnit();

        if (theSplitedString.length == 6) {
            theRangeArray = theSplitedString[5].substring(1, theSplitedString[5].length() - 1).split(this.SPACE_STRING);
        } else {
            for (int theIndexOfRange = 6; !theSplitedString[theIndexOfRange].contains("["); theIndexOfRange++) {
                theRangeArray = theSplitedString[theIndexOfRange].substring(1, theSplitedString[theIndexOfRange].length() - 1).split(this.SPACE_STRING);
            }
        }

        thePeak.setChemicalShift(Double.parseDouble(theSplitedString[this.CHEMICAL_SHIFT_1D_INDEX_IN_ACD]));
        thePeak.setMinOfRange(Double.parseDouble(theRangeArray[this.MIN_CHEMICAL_SHIFT_1D_IN_ACD]));
        thePeak.setMaxOfRange(Double.parseDouble(theRangeArray[this.MAX_CHEMICAL_SHIFT_1D_IN_ACD]));
        thePeak.setIntensity(Double.parseDouble(theSplitedString[this.INTENSITY_1D_INDEX_IN_ACD]));

        return thePeak;
    }

    private Double __getTolerenceOfHydrogenInHMBC() {
        return (double) 40 / this.getNmrResolution();
    }

    private Double __getTolerenceOfCarbonInHMBC() {
        return (double) 1000 / this.getNmrResolution();
    }

    private Nmr2dCHUnitList convertMedianPeak(Nmr2dCHUnitList the2dPeakList, Nmr1dUnitList the1HNmrPeakList, Nmr1dUnitList the13CNmrPeakList) {
        Nmr2dCHUnitList theMedianPeakData = new Nmr2dCHUnitList();
        Nmr2dCHUnitList theCopiedPeakData = new Nmr2dCHUnitList(the2dPeakList);
        List<Nmr2dCHUnitList> theMedianPeakSet = new ArrayList<>();

        //실질적으로 Median Peak를 뽑아냄
        for (Object the1HPeak : the1HNmrPeakList) {
            for (Object the13CPeak : the13CNmrPeakList) {
                Nmr2dCHUnitList theClusterPeakList = this.__getClusterPeakList((Nmr1dUnit) the1HPeak, (Nmr1dUnit) the13CPeak, theCopiedPeakData);

                if (!theClusterPeakList.isEmpty()) {
                    theMedianPeakData.addPeak(this.__getMedianPosition(theClusterPeakList));
                }
            }
        }

        return theMedianPeakData;
    }

    private Nmr2dCHUnitList __getClusterPeakList(Nmr1dUnit the1HPeak, Nmr1dUnit the13CPeak, Nmr2dCHUnitList theCopiedPeakData) {
        Nmr2dCHUnit theClassStandardPeak = this.__getClassStandardPeak(the1HPeak, the13CPeak);
        Nmr2dCHUnit theStartPeak = this.__getClosestPeak(theCopiedPeakData, theClassStandardPeak);
        Nmr2dCHUnitList thePeakList = new Nmr2dCHUnitList();

        if (this.__containRange(theStartPeak, theClassStandardPeak) && this.__containHydrogenRange(theStartPeak, (Nmr1dUnit) the1HPeak)) {
            int theIndexOfStartPeak = this.__getIndexOfSamePeakIn2D(theCopiedPeakData, theStartPeak);

            theCopiedPeakData.remove(theIndexOfStartPeak);
            thePeakList.addPeak(theStartPeak);

            this.__getClusterPeakList(theCopiedPeakData, thePeakList);
        }

        return thePeakList;
    }

    private void __getClusterPeakList(Nmr2dCHUnitList theCopiedPeakData, Nmr2dCHUnitList thePeakList) {
        while (true) {
            boolean theJugment = true;

            for (int k = theCopiedPeakData.size() - 1; k >= 0; k--) {
                if (this.__isClusterMember(thePeakList, theCopiedPeakData.get(k))) {
                    thePeakList.addPeak(theCopiedPeakData.get(k));
                    theCopiedPeakData.remove(k);
                    theJugment = false;
                }
            }

            if (theJugment) {
                break;
            }
        }
    }

    private Nmr2dCHUnit __getClassStandardPeak(Nmr1dUnit the1HPeak, Nmr1dUnit the13CPeak) {
        Nmr2dCHUnit theClassStandardPeak = new Nmr2dCHUnit();

        theClassStandardPeak.setCarbonShift(the13CPeak.getChemicalShift());
        theClassStandardPeak.setHydrogenShift(the1HPeak.getChemicalShift());

        return theClassStandardPeak;
    }

    private boolean __containRange(Nmr2dCHUnit theStartPeak, Nmr2dCHUnit theClassStandardPeak) {
        double theTolerenceOfCarbon = this.__getTolerenceOfCarbonInHMBC();
        double theTolerenceOfHydrogen = this.__getTolerenceOfHydrogenInHMBC();

        if (Math.abs(theStartPeak.getHydrogenShift() - theClassStandardPeak.getHydrogenShift()) > theTolerenceOfHydrogen) {
            return false;
        } else if (Math.abs(theStartPeak.getCarbonShift() - theClassStandardPeak.getCarbonShift()) > theTolerenceOfCarbon) {
            return false;
        }

        return true;
    }

    private boolean __containHydrogenRange(Nmr2dCHUnit the2dPeak, Nmr1dUnit theHydrogenPeak) {
        if (the2dPeak.getHydrogenShift() >= theHydrogenPeak.getMinOfRange() && the2dPeak.getHydrogenShift() <= theHydrogenPeak.getMaxOfRange()) {
            return true;
        }

        return false;
    }

    private Nmr2dCHUnit __getClosestPeak(Nmr2dCHUnitList thePeakData, Nmr2dCHUnit theCheckPeak) {
        Nmr2dCHUnit theClosestPeak = new Nmr2dCHUnit();
        double theMinimumDistance = Math.sqrt(2.0);

        for (Object thePeak : thePeakData) {
            if (!this.__isSamePeak((Nmr2dCHUnit) thePeak, theCheckPeak)) {
                double theDistance = this.__calculateDistance((Nmr2dCHUnit) thePeak, theCheckPeak);

                if (theMinimumDistance > theDistance) {
                    theMinimumDistance = theDistance;
                    theClosestPeak = (Nmr2dCHUnit) thePeak;
                }
            }
        }

        return theClosestPeak;
    }

    private Double __calculateDistance(Nmr2dCHUnit thePeak, Nmr2dCHUnit theCheckPeak) {
        return Math.sqrt(Math.pow(thePeak.getHydrogenShift() / this.MAXIMUM_HYDROGEN_NMR_CHEMICAL_SHIFT - theCheckPeak.getHydrogenShift() / this.MAXIMUM_HYDROGEN_NMR_CHEMICAL_SHIFT, 2.0)
                + Math.pow(thePeak.getCarbonShift() / this.MAXIMUM_CARBON_NMR_CHEMICAL_SHIFT - theCheckPeak.getCarbonShift() / this.MAXIMUM_CARBON_NMR_CHEMICAL_SHIFT, 2.0));
    }

    private boolean __isSamePeak(Nmr2dCHUnit thePeak, Nmr2dCHUnit theCheckPeak) {
        if (!thePeak.getCarbonShift().equals(theCheckPeak.getCarbonShift())) {
            return false;
        } else if (!thePeak.getHydrogenShift().equals(theCheckPeak.getHydrogenShift())) {
            return false;
        }

        return true;
    }

    private Boolean __isNoisePeak(Nmr2dCHUnit theMedianPeak, Integer theNumberOfCarbon, Integer theNumberOfHydrogen) {
        Nmr1dUnitList the1HPeakList = this.__read1DNMRACDLabFileFormat(this.get1HNMRFilePath(), theNumberOfHydrogen);
        Nmr1dUnitList the13CPeakList = this.__read1DNMRACDLabFileFormat(this.get13CNMRFilePath(), theNumberOfCarbon);
        Double theTolerenceOfHydrogen = this.__getTolerenceOfHydrogenInHMBC();
        Double theTolerenceOfCarbon = this.__getTolerenceOfCarbonInHMBC();
        List<Boolean> theJugmentArray = new ArrayList<>();
        final Integer CARBON_JUGMENT = 0;
        final Integer HYDROGEN_JUGMENT = 1;

        theJugmentArray.add(false);
        theJugmentArray.add(false);

        for (Object the1HPeak : the1HPeakList) {
            if (Math.abs(theMedianPeak.getHydrogenShift() - ((Nmr1dUnit) the1HPeak).getChemicalShift()) <= theTolerenceOfHydrogen) {
                theJugmentArray.set(CARBON_JUGMENT, true);
                break;
            }
        }

        for (Object the13CPeak : the13CPeakList) {
            if (Math.abs(theMedianPeak.getCarbonShift() - ((Nmr1dUnit) the13CPeak).getChemicalShift()) <= theTolerenceOfCarbon) {
                theJugmentArray.set(HYDROGEN_JUGMENT, true);
                break;
            }
        }

        return theJugmentArray.contains(false);
    }

    private Integer __getIndexOfSamePeakIn2D(Nmr2dCHUnitList thePeakList, Nmr2dCHUnit the2dPeak) {
        for (int pi = 0, pEnd = thePeakList.size(); pi < pEnd; pi++) {
            if (thePeakList.getPeak(pi).getHydrogenShift() == the2dPeak.getHydrogenShift() && thePeakList.getPeak(pi).getCarbonShift() == the2dPeak.getCarbonShift()) {
                return pi;
            }
        }

        return -1;
    }

    private Boolean __isClusterMemberForHydrogen(Nmr2dCHUnitList thePeakList, Nmr2dCHUnit theCheckPeak) {
        double theTolerenceOfHydrogen = this.__getTolerenceOfHydrogenInHMBC();
        double theMinimumDistanceInCheckData = -1;
        double theMinimumDistanceInPeakData = -1;
        int theIndexOfMatchedCheckData = -1;
        int theIndexOfMatchedPeakData = -1;

        for (Object the2dPeak : thePeakList) {
            for (int pi = 0, pEnd = this.get1HPeakList().size(); pi < pEnd; pi++) {
                if (this.__containHydrogenRange(theCheckPeak, this.get1HPeakList().getPeak(pi))
                        || Math.abs(this.get1HPeakList().getPeak(pi).getChemicalShift() - theCheckPeak.getHydrogenShift()) <= theTolerenceOfHydrogen) {
                    theMinimumDistanceInCheckData = Math.abs(this.get1HPeakList().getPeak(pi).getChemicalShift() - theCheckPeak.getHydrogenShift());
                    theIndexOfMatchedCheckData = pi;
                }

                if (this.__containHydrogenRange((Nmr2dCHUnit) the2dPeak, this.get1HPeakList().getPeak(pi))
                        || Math.abs(this.get1HPeakList().getPeak(pi).getChemicalShift() - ((Nmr2dCHUnit) the2dPeak).getHydrogenShift()) <= theTolerenceOfHydrogen) {
                    theMinimumDistanceInPeakData = Math.abs(this.get1HPeakList().getPeak(pi).getChemicalShift() - ((Nmr2dCHUnit) the2dPeak).getHydrogenShift());
                    theIndexOfMatchedPeakData = pi;
                }
            }
            if ((theIndexOfMatchedCheckData != theIndexOfMatchedPeakData) || theIndexOfMatchedCheckData == -1 || theIndexOfMatchedPeakData == -1) {
                return false;
            }
        }

        return true;
    }

    private Boolean __isClusterMemberForCarbon(Nmr2dCHUnitList thePeakList, Nmr2dCHUnit theCheckPeak) {
        double theTolerenceOfCarbon = this.__getTolerenceOfCarbonInHMBC();
        double theMinimumDistanceInCheckData = -1;
        double theMinimumDistanceInPeakData = -1;
        int theIndexOfMatchedCheckData = -1;
        int theIndexOfMatchedPeakData = -1;

        for (Object the2dPeak : thePeakList) {
            for (int pi = 0, pEnd = this.get13CPeakList().size(); pi < pEnd; pi++) {
                if ((theMinimumDistanceInCheckData == -1 || theMinimumDistanceInCheckData > Math.abs(this.get13CPeakList().getPeak(pi).getChemicalShift() - theCheckPeak.getCarbonShift()))
                        && Math.abs(this.get13CPeakList().getPeak(pi).getChemicalShift() - theCheckPeak.getCarbonShift()) <= theTolerenceOfCarbon) {
                    theMinimumDistanceInCheckData = Math.abs(this.get13CPeakList().getPeak(pi).getChemicalShift() - theCheckPeak.getCarbonShift());
                    theIndexOfMatchedCheckData = pi;
                }

                if ((theMinimumDistanceInPeakData == -1 || theMinimumDistanceInPeakData > Math.abs(this.get13CPeakList().getPeak(pi).getChemicalShift() - ((Nmr2dCHUnit) the2dPeak).getCarbonShift()))
                        && Math.abs(this.get13CPeakList().getPeak(pi).getChemicalShift() - ((Nmr2dCHUnit) the2dPeak).getCarbonShift()) <= theTolerenceOfCarbon) {
                    theMinimumDistanceInPeakData = Math.abs(this.get13CPeakList().getPeak(pi).getChemicalShift() - ((Nmr2dCHUnit) the2dPeak).getCarbonShift());
                    theIndexOfMatchedPeakData = pi;
                }
            }

            if ((theIndexOfMatchedCheckData != theIndexOfMatchedPeakData) || theIndexOfMatchedCheckData == -1 || theIndexOfMatchedPeakData == -1) {
                return false;
            }
        }

        return true;
    }

    private Boolean __isClusterMember(Nmr2dCHUnitList thePeakList, Nmr2dCHUnit theCheckPeak) {
        if (!this.__isClusterMemberForHydrogen(thePeakList, theCheckPeak)) {
            return false;
        } else if (!this.__isClusterMemberForCarbon(thePeakList, theCheckPeak)) {
            return false;
        }

        return true;
    }

    private Nmr2dCHUnit __getMedianPosition(Nmr2dCHUnitList thePeakList) {
        Nmr2dCHUnit theMedianPosition = new Nmr2dCHUnit();
        double theSumOfCarbonShift = 0;
        double theSumOfHydrogenShift = 0;

        for (int i = 0; i < thePeakList.size(); i++) {
            theSumOfCarbonShift += thePeakList.get(i).getCarbonShift();
            theSumOfHydrogenShift += thePeakList.get(i).getHydrogenShift();
        }

        theMedianPosition.setCarbonShift(theSumOfCarbonShift / thePeakList.size());
        theMedianPosition.setHydrogenShift(theSumOfHydrogenShift / thePeakList.size());

        return theMedianPosition;
    }

    private List<Nmr2dCHUnitList> __sortedByShift(Nmr2dCHUnitList thePeakList, Nmr1dUnitList theStandard1DPeakList, Integer theTypeIndexOfAtom) {
        List<Nmr2dCHUnitList> theSortedByShift = this.__initializeSortedGroup(theStandard1DPeakList);

        for (Object the2dPeak : thePeakList) {
            theSortedByShift.get(this.__getIndexOfTypeOfPeak(theStandard1DPeakList, (Nmr2dCHUnit) the2dPeak, theTypeIndexOfAtom)).addPeak(((Nmr2dCHUnit) the2dPeak));
        }

        this.__removeEmptyIndex(theSortedByShift);

        return theSortedByShift;
    }

    private void __removeEmptyIndex(List<Nmr2dCHUnitList> theSortedByShift) {
        for (int i = theSortedByShift.size() - 1; i >= 0; i--) {
            if (theSortedByShift.get(i).isEmpty()) {
                theSortedByShift.remove(i);
            }
        }
    }

    private List<Nmr2dCHUnitList> __initializeSortedGroup(Nmr1dUnitList theStandard1DPeakList) {
        List<Nmr2dCHUnitList> theSortedByShift = new ArrayList<>();

        for (int pi = 0, pEnd = theStandard1DPeakList.size(); pi < pEnd; pi++) {
            theSortedByShift.add(new Nmr2dCHUnitList());
        }

        return theSortedByShift;
    }

    private Integer __getIndexOfTypeOfPeak(Nmr1dUnitList theStandard1DPeakList, Nmr2dCHUnit the2dPeak, Integer theTypeIndexOfAtom) {
        double theMinimumDistance = -1;
        int theIndexOfTypeOfPeak = -1;

        for (int pi = 0, pEnd = theStandard1DPeakList.size(); pi < pEnd; pi++) {
            if (theMinimumDistance == -1 || theMinimumDistance > Math.abs(theStandard1DPeakList.getPeak(pi).getChemicalShift() - the2dPeak.getChemicalShift(theTypeIndexOfAtom))) {
                theMinimumDistance = Math.abs(theStandard1DPeakList.getPeak(pi).getChemicalShift() - the2dPeak.getChemicalShift(theTypeIndexOfAtom));
                theIndexOfTypeOfPeak = pi;
                break;
            }
        }

        return theIndexOfTypeOfPeak;
    }

    private Nmr2dCHUnitList __convertMedianPeakInHMQC(Integer theTypeOfInputFileFormat) throws FileNotFoundException, IOException {
        Nmr2dCHUnitList thePeakList = this.__readNMRDataFile(this.getHMQCFilePath(), theTypeOfInputFileFormat);
        Nmr2dCHUnitList theMedianPeakData = new Nmr2dCHUnitList();
        Nmr2dCHUnitList thePeakData = new Nmr2dCHUnitList(thePeakList);
        List<Nmr2dCHUnitList> theSortedByCarbonPeak = this.__sortedByShift(thePeakData, this.get13CPeakList(), Nmr2dCHUnit.CARBON_SHIFT_INDEX);

        for (int i = 0; i < theSortedByCarbonPeak.size(); i++) {
            theMedianPeakData.addPeak(this.__getMedianPosition(theSortedByCarbonPeak.get(i)));
        }

        return theMedianPeakData;
    }

    private Boolean __isSameHydrogenPeak(List<Nmr2dCHUnitList> thePeak2dList, Integer theIndexOfStartPeakArray) {
        List<String> theIndexArrayOfStartPeak = new ArrayList<>();

        for (int i = 0; i < this.__getStartPeakListInHMBC().size(); i++) {
            for (int j = 0; j < thePeak2dList.size(); j++) {
                if (this.__containSamePeak(thePeak2dList.get(j), this.__getStartPeakListInHMBC().get(i))) {
                    theIndexArrayOfStartPeak.add(Integer.toString(j));
                    break;
                }
            }
        }

        if (ArrayListManipulator.count(theIndexArrayOfStartPeak, theIndexArrayOfStartPeak.get(theIndexOfStartPeakArray)) > 1) {
            return true;
        }

        return false;
    }

    private Boolean __containSamePeak(Nmr2dCHUnitList thePeakData, Nmr2dCHUnit theCheckPeak) {
        for (int i = 0; i < thePeakData.size(); i++) {
            if (thePeakData.get(i).getCarbonShift().equals(theCheckPeak.getCarbonShift()) && thePeakData.get(i).getHydrogenShift().equals(theCheckPeak.getHydrogenShift())) {
                return true;
            }
        }

        return false;
    }

    private Integer __getIndexOfSamePeak(List<Nmr2dCHUnitList> thePeak2dList, Nmr2dCHUnit theCheckPeak) {
        for (int i = 0; i < thePeak2dList.size(); i++) {
            if (this.__containSamePeak(thePeak2dList.get(i), theCheckPeak)) {
                return i;
            }
        }

        return -1;
    }
}
