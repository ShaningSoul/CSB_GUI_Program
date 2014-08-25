package org.bmdrc.util;

import java.io.*;
import java.util.*;
import java.text.*;
import java.util.regex.PatternSyntaxException;
import org.openscience.cdk.exception.CDKException;
import org.openscience.cdk.interfaces.IAtomContainer;
import org.openscience.cdk.interfaces.IMoleculeSet;
import org.openscience.cdk.tools.CDKHydrogenAdder;
import org.openscience.cdk.tools.manipulator.AtomContainerManipulator;

public class Module {

    public static void addHydrogen(IMoleculeSet molSet, String theErrorFilePath) throws IOException {
        File theCdkErrors = new File(theErrorFilePath);
        BufferedWriter theFileWriter = new BufferedWriter(new FileWriter(theCdkErrors));
        for (IAtomContainer theMolecule : molSet.molecules()) {
            try{
            AtomContainerManipulator.percieveAtomTypesAndConfigureAtoms(theMolecule);            
            CDKHydrogenAdder adder = CDKHydrogenAdder.getInstance(theMolecule.getBuilder());
            adder.addImplicitHydrogens(theMolecule);
            } catch (CDKException e){
                theFileWriter.write(e.getMessage());
                molSet.removeAtomContainer(theMolecule);
                e.printStackTrace();                
            }
        }
        theFileWriter.close();
    }
    
    public static boolean deleteDirectory(File theDir) {
        if (!theDir.exists()) {
            return false;
        }

        File[] theFileArray = theDir.listFiles();

        for (File theFile : theFileArray) {
            if (theFile.isDirectory()) {
                deleteDirectory(theFile);
            } else {
                theFile.delete();
            }
        }


        return theDir.delete();
    }

    public static Integer count(String theSampleString, String theRegexString) {
        int theNumber = 0;
        int theLengthOfRegexString = theRegexString.length();
        
        for(int si = 0, sEnd = theSampleString.length() - theLengthOfRegexString ; si <= sEnd ; si++) {
            if(theSampleString.substring(si, si+theLengthOfRegexString).equals(theRegexString)) {
                theNumber++;
            }
        }
        
        return theNumber;
    }
    
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

    public static void deleteFileInPath(String theDir) {
        List<File> theFileList = getFileList(theDir);

        for (int i = 0; i < theFileList.size(); i++) {
            theFileList.get(i).delete();
        }
    }

    public static List<File> getFileList(String theWorkDirPath) {
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

    public static List<File> getDirList(String theWorkDirPath) {
        List<File> theFileList = new java.util.ArrayList<File>();
        File theWorkDir = new File(theWorkDirPath);

        if (theWorkDir.isDirectory()) {
            File theCheckFileList[] = theWorkDir.listFiles();

            for (int i = 0; i < theCheckFileList.length; i++) {
                if (theCheckFileList[i].isDirectory()) {
                    theFileList.add(theCheckFileList[i]);
                }
            }
        }

        return theFileList;
    }

    public static void getCurrentTime(long theStartTime, long theEndTime) {
        long theCurrentTime = theEndTime - theStartTime;
        long theHour = theCurrentTime / 3600000;
        long theMinute = (theCurrentTime - (theHour * 3600000)) / 60000;
        long theSecond = (theCurrentTime - (theHour * 3600000) - (theMinute * 60000)) / 1000;

        if (theHour / 10 == 0) {
            System.out.print("0");
        }
        System.out.print(theHour);
        System.out.print(":");
        if (theMinute / 10 == 0) {
            System.out.print("0");
        }
        System.out.print(theMinute);
        System.out.print(":");
        if (theSecond / 10 == 0) {
            System.out.print("0");
        }
        System.out.print(theSecond);
    }

    public static ArrayList<ArrayList<Integer>> combination(Integer theTotalNumber, Integer theSelectionNumber) {
        ArrayList<Integer> theLocalCombination = new ArrayList<Integer>();
        ArrayList<ArrayList<Integer>> theResult = new ArrayList<ArrayList<Integer>>();

        if (theSelectionNumber <= 0) {
            return theResult;
        }

        for (int i = 0; i < theSelectionNumber; i++) {
            theLocalCombination.add(i);
        }

        theResult.add((ArrayList<Integer>) theLocalCombination.clone());

        while (theLocalCombination.get(0) != theTotalNumber - theSelectionNumber) {
            theLocalCombination.set(theSelectionNumber - 1, theLocalCombination.get(theSelectionNumber - 1) + 1);
            theResult.add((ArrayList<Integer>) theLocalCombination.clone());

            for (int i = 1; i < theSelectionNumber; i++) {
                if (theLocalCombination.get(theSelectionNumber - i) == theTotalNumber - i) {
                    theLocalCombination.set(theSelectionNumber - i - 1, theLocalCombination.get(theSelectionNumber - i - 1) + 1);

                    for (int j = 1; j < i + 1; j++) {
                        theLocalCombination.set(theSelectionNumber - i - 1 + j, theLocalCombination.get(theSelectionNumber - i - 1) + j);
                    }

                    theResult.add((ArrayList<Integer>) theLocalCombination.clone());
                } else {
                    break;
                }
            }
        }

        return theResult;
    }

    public static ArrayList<ArrayList<Integer>> combinationConsideringOrder(Integer theTotalNumber, Integer theSelectionNumber) {
        ArrayList<ArrayList<Integer>> theResult = new ArrayList<ArrayList<Integer>>();

        if (theSelectionNumber <= 0 || theTotalNumber < theSelectionNumber) {
            return theResult;
        }

        for (int i = 0; i < theTotalNumber; i++) {
            ArrayList<Integer> theLocalCombination = new ArrayList<Integer>();

            theLocalCombination.add(i);
            theResult.add((ArrayList<Integer>) theLocalCombination.clone());
        }

        for (int i = 0; i < theSelectionNumber - 1; i++) {
            ArrayList<ArrayList<Integer>> theNewResult = new ArrayList<ArrayList<Integer>>();

            for (int j = 0; j < theResult.size(); j++) {
                for (int k = 0; k < theTotalNumber; k++) {
                    ArrayList<Integer> theCheckArray = (ArrayList<Integer>) theResult.get(j).clone();

                    if (!theCheckArray.contains(k)) {
                        theCheckArray.add(k);
                        theNewResult.add((ArrayList<Integer>) theCheckArray.clone());

                    }
                }
            }

            theResult = (ArrayList<ArrayList<Integer>>) theNewResult.clone();
        }

        return theResult;
    }

    public static void createDirectoryInFileParentPath(String theFilePath) {
        String[] theSplitedString = theFilePath.split("\\");
        String theParentPath = null;
        File theDir = null;

        for (int i = 0; i < theSplitedString.length; i++) {
            theParentPath = theParentPath + theSplitedString + "\\";
        }

        theDir = new File(theParentPath);

        if (!theDir.exists()) {
            theDir.mkdirs();
        }
    }

    public static java.util.ArrayList<String> readLines(String theInputFilePath) {
        java.util.ArrayList<String> theFileInformation = new java.util.ArrayList<String>();
        String theCheckString = null;

        if (theInputFilePath.length() == 0) {
            System.err.println("Check Input File!!");
            System.exit(1);
        }

        try {
            BufferedReader theFileReader = new BufferedReader(new FileReader(theInputFilePath));

            while ((theCheckString = theFileReader.readLine()) != null) {
                theFileInformation.add(theCheckString);
            }

            theFileReader.close();
        } catch (IOException e) {
            System.err.println(e); // ?�러�??�다�?메시�?출력
            System.exit(1);
        }

        return theFileInformation;
    }

    public static List<List<Double>> deepcopy2DDoubleList(final List<List<Double>> the2DDoubleList) {
        List<List<Double>> the2DList = new ArrayList<>();
        for (List<Double> e : the2DDoubleList) {
            the2DList.add(new ArrayList<>(e));
        }
        return the2DList;
    }
}
