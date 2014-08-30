/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modify_file;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

/**
 *
 * @author grinn
 */
public class Main_2 {

    public static void main(String[] args) throws FileNotFoundException, IOException {
        String dropboxDir = "D:\\Users\\labwindows\\Dropbox";
        File barSeparatedFile = new File(dropboxDir + "\\Lab Study\\DNP_VALUE.txt");
        BufferedReader reader = new BufferedReader(new FileReader(barSeparatedFile));
        String separator = "\\|";
        String theSep = "|";
        String in;
        String[] tempLine;
        Integer count = 0;
        HashMap<String, Map<String, String>> theStringHashMap = new LinkedHashMap<>();

        ArrayList<String[]> inputStrings = new ArrayList<>();

        Comparator<String[]> sort = new Comparator<String[]>() {
            public int compare(String[] s1, String[] s2) {
                Integer theFirstIndex = Integer.parseInt(s1[0]);
                Integer theSecondIndex = Integer.parseInt(s2[0]);
                return theFirstIndex.compareTo(theSecondIndex);
            }
        };


        //read native file.
        long startTime = System.currentTimeMillis();

        while ((in = reader.readLine()) != null) {
            inputStrings.add(in.split(separator));
        }
        reader.close();

        long endTime = System.currentTimeMillis();
        long itTime = endTime - startTime;
        System.out.println("read file done!");
        System.out.println("Time: " + itTime);

        //sort strings by chemical id
        startTime = System.currentTimeMillis();
        Collections.sort(inputStrings, sort);
        endTime = System.currentTimeMillis();
        itTime = endTime - startTime;
        System.out.println("sort file done!");
        System.out.println("Time: " + itTime);

        //
        startTime = System.currentTimeMillis();
        File temp = new File(dropboxDir + "\\Lab Study\\DNP_VALUE_sorted.txt");
        BufferedWriter theWriter = new BufferedWriter(new FileWriter(temp));
        for (String[] theStringArray : inputStrings) {
            StringBuilder theSB = new StringBuilder();
            theSB.append(theStringArray[0]);
            for (String eachString : theStringArray) {
                theSB.append(theSep);
                theSB.append(eachString);
            }
            theWriter.write(theSB.toString());
        }
        theWriter.close();
        endTime = System.currentTimeMillis();
        itTime = endTime - startTime;

        System.out.println("write file done!");
        System.out.println("Time: " + itTime);

        startTime = System.currentTimeMillis();
        reader = new BufferedReader(new FileReader(temp));


        //Set 'theStringMap'(Table) from ArrayList.
        Iterator<String[]> theInputStringIterator = inputStrings.iterator();
        String thePreviousIndexString = new String();
        String[] thePreviousArray = null;
        String[] theStringArray = theInputStringIterator.next();
        String theIndex = theStringArray[0];

        while (theInputStringIterator.hasNext()) {
            Map<String, String> theStringMap = new LinkedHashMap<>();
            theStringMap.put(theStringArray[1], theStringArray[2]);

            while (Main_2.hasSameID(thePreviousArray, theStringArray) && theInputStringIterator.hasNext()) {
                theStringMap.put(theStringArray[1], theStringArray[2]);
                thePreviousArray = theStringArray;
                theStringArray = theInputStringIterator.next();
            }

            theStringHashMap.put(thePreviousArray[0], theStringMap);
        }


        //Set 'theStringMap'(Table) from File
//        while ((in = reader.readLine())!=null){
//            tempLine = in.split(separator);
//            LinkedHashMap<String, String> theStringMap = new LinkedHashMap<>();
//            theStringMap.put(tempLine[1], tempLine[2]);
//            reader.mark(3000);
//            while (((in = reader.readLine()) != null)&&(in.contains(tempLine[0]))){
//                tempLine = in.split(separator);
//                theStringMap.put(tempLine[1], tempLine[2]);
//                reader.mark(3000);
//            }
//            reader.reset();
//            theStringHashMap.put(tempLine[0], theStringMap);
//        }

        //write outputFile.
        File outputFile = new File(dropboxDir + "\\Lab Study\\DNP_VALUE_2_out.txt");
        BufferedWriter writer = new BufferedWriter(new FileWriter(outputFile));
        for (String key : theStringHashMap.keySet()) {
            writer.write(Main_2.getString(key, theStringHashMap, theSep));
            writer.write("\n");
        }
        writer.close();
        endTime = System.currentTimeMillis();
        itTime = endTime - startTime;
        System.out.println("write file done!");
        System.out.println("Time: " + itTime);
    }

    public static String getString(String theKey, Map<String, Map<String, String>> theMap, String separator) {
        StringBuilder mapString = new StringBuilder();
        //setting attributes to print.
        ArrayList<String> keyList = new ArrayList<String>(Arrays.asList("ACCURATE_MASS", "MOLECULAR_FORMULA", "BIOLOGICAL_SOURCE", "INCHI", "NAME"));
        //making a string of a single record.
        for (String key : keyList) {
            try {
                mapString.append(separator);
                mapString.append(theMap.get(theKey).get(key));
            } catch (NullPointerException e) {
                mapString.append(separator);
                mapString.append("null");
            }
        }
        return theKey + mapString.toString();
    }

    public static boolean hasSameID(String[] thePreviousString, String[] theInputString) {
        if (thePreviousString == null) {
            return true;
        }
        return thePreviousString[0].equals(theInputString[0]);
    }
}
