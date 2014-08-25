/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.bmdrc.tools;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 *
 * @author labwindows
 */
public class SFECalculator {

    public static void calculateSFE(String theInputFilePath, String theResultFilePath) throws IOException, InterruptedException {
        StringBuilder theCommandString = new StringBuilder();

        theCommandString.append("exe_program\\calculateSFE.exe ").append(theInputFilePath).append(" ").append(theResultFilePath);
        Runtime theRunTime = Runtime.getRuntime();
        Process theProcess = theRunTime.exec(theCommandString.toString());

        BufferedReader theOutStream = new BufferedReader(new InputStreamReader(theProcess.getInputStream()));
        BufferedReader theErrorStream = new BufferedReader(new InputStreamReader(theProcess.getErrorStream()));

        theOutStream.close();
        theErrorStream.close();
        
        theProcess.waitFor();
    }
}
