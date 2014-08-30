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
import java.util.HashMap;
import java.util.LinkedHashMap;

/**
 *
 * @author grinn
 */
public class Main {
    public static void main(String[] args) throws FileNotFoundException, IOException{
        File barSeparatedFile = new File ("E:\\Dropbox\\Lab Study\\DNP_VALUE_NUMERIC.txt");
        BufferedReader reader = new BufferedReader(new FileReader(barSeparatedFile));
        String separator = "\\|";
        String theSep = "|";
        String in ="";
        String[] tempLine;
        Integer count = 0;
        LinkedHashMap<String, LinkedHashMap<String,String>> theString = new LinkedHashMap<>();
        while ((in = reader.readLine())!=null){
            tempLine = in.split(separator);
            LinkedHashMap<String, String> theStringMap = new LinkedHashMap<>();
            theStringMap.put(tempLine[1], tempLine[2]);
            reader.mark(3000);
            while (((in = reader.readLine()) != null)&&(in.contains(tempLine[0]))){
                tempLine = in.split(separator);
                theStringMap.put(tempLine[1], tempLine[2]);
                reader.mark(3000);
            }
            reader.reset();
            theString.put(tempLine[0], theStringMap);
        }
        File outputFile = new File ("E:\\Dropbox\\Lab Study\\DNP_VALUE_NUMERIC_out.txt");
        BufferedWriter writer = new BufferedWriter (new FileWriter(outputFile));
        for (String key : theString.keySet()){
            writer.write(Main.getString(key, theString, theSep));
            writer.write("\n");
        }
        writer.close();
    }
    
    public static String getString(String theKey,LinkedHashMap<String, LinkedHashMap<String, String>> theMap, String separator){
        StringBuilder mapString = new StringBuilder();
        //setting attributes to print.
        ArrayList<String> keyList = new ArrayList<String> (Arrays.asList("ACCURATE_MASS", "MOLECULAR_WEIGHT"));
        //making a string of a single record.
        for(String key : keyList){
            try{
            mapString.append(separator);
            mapString.append(theMap.get(theKey).get(key));
            } catch(NullPointerException e){
                mapString.append(separator);
                mapString.append("null");
            }            
        }
        return theKey + mapString.toString();
    }
}
