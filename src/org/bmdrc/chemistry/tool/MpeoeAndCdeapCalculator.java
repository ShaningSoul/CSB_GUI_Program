/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.bmdrc.chemistry.tool;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JTextField;
import org.bmdrc.gui.MainFrame;
import org.bmdrc.interfaces.IStringConstant;
import org.bmdrc.util.Module;
import org.bmdrc.util.SDFReader;
import org.bmdrc.util.SDFWriter;
import org.bmdrc.util.TwoDimensionList;
import org.openscience.cdk.MoleculeSet;
import org.openscience.cdk.interfaces.IMolecule;
import org.openscience.cdk.interfaces.IMoleculeSet;

/**
 *
 * @author labwindows
 */
public class MpeoeAndCdeapCalculator implements IStringConstant, Serializable {
    private static final long serialVersionUID = -2819438163659103658L;

    private IMoleculeSet itsMoleculeSet;
    private TwoDimensionList<Double> itsMpeoe2dList;
    private TwoDimensionList<Double> itsCdeap2dList;
    //constant String variable
    private final String TOTAL_TEMP_DIR = "temp";
    private final String TEMP_DIR = "temp\\temp\\";
    private final String TEMP_PROPERTY_DIR = "temp\\property\\";
    private final String MOLECULE_SUFFIX = ".sdf";
    private final String TEMP_FILE_NAME = "temp";
    private final String MOLECULE_INFORMATION_END_MARK = "$$$$";
    private final String ATOM_AND_BOND_STRING_END_MARK = "M  END\n";
    private final String ADDING_ATOM_STRING = "  0  0  0  0  0\n";
    private final String ADDING_BOND_STRING = "  0  0  0\n";
    private final String NOT_CALCULATED_VALUE = "nan";
    private final String INFINTE_VALUE = "inf";
    private final String MPEOE_AND_CDEAP_CALCULATOR_COMMAND = "D:\\Users\\labwindows\\Documents\\NetBeansProjects\\NMR_Processing\\exe_program\\calculateMPEOEandCDEAP.exe";
    private final String RESULT_FILE_NAME_CALCULATED_MPEOE_AND_CDEAP = "property";
    private final String TEXT_SUFFIX = ".txt";
    private final String MPEOE_DESCRIPTOR = "MPEOE";
    private final String CDEAP_DESCRIPTOR = "CDEAP";
    //constant int variable
    private final int LINE_INDEX_HAVING_NUMBER_OF_ATOM_AND_BOND_INFORMATION = 3;
    private final int STRING_START_INDEX = 0;
    private final int ATOM_STRING_ATOM_SYMBOL_END_INDEX = 33;
    private final int ATOM_STRING_END_INDEX = 48;
    private final int BOND_STRING_TYPE_END_INDEX = 9;
    private final int NUMBER_OF_ATOM_END_INDEX_IN_FILE = 3;
    private final int NUMBER_OF_BOND_END_INDEX_IN_fILE = 6;
    private final int MPEOE_INDEX = 1;
    private final int CDEAP_INDEX = 3;
    //constant double variable
    private final double NOT_CONTAIN_PROPERTY_VALUE = Double.NaN;

    public MpeoeAndCdeapCalculator() {
        this.setMoleculeSet(new MoleculeSet());
        this.setMpeoe2dList(new TwoDimensionList<Double>());
        this.setCdeap2dList(new TwoDimensionList<Double>());
        this.__makeNewDir(this.TOTAL_TEMP_DIR);
    }

    public MpeoeAndCdeapCalculator(File theMoleculeFile) throws FileNotFoundException, IOException {
        this.__fixToFormatByMoleculeFile(theMoleculeFile);
        this.setMoleculeSet(SDFReader.openMoleculeFile(theMoleculeFile));
    }

    public IMoleculeSet getMoleculeSet() {
        return itsMoleculeSet;
    }

    public void setMoleculeSet(IMoleculeSet theMoleculeSet) {
        this.itsMoleculeSet = theMoleculeSet;
    }

    public IMoleculeSet setMoleculeSet() {
        return itsMoleculeSet;
    }

    public TwoDimensionList<Double> getMpeoe2dList() {
        return itsMpeoe2dList;
    }

    public void setMpeoe2dList(TwoDimensionList<Double> theMpeoe2dList) {
        this.itsMpeoe2dList = theMpeoe2dList;
    }

    public TwoDimensionList<Double> setMpeoe2dList() {
        return itsMpeoe2dList;
    }

    public TwoDimensionList<Double> getCdeap2dList() {
        return itsCdeap2dList;
    }

    public void setCdeap2dList(TwoDimensionList<Double> theCdeap2dList) {
        this.itsCdeap2dList = theCdeap2dList;
    }

    public TwoDimensionList<Double> setCdeap2dList() {
        return itsCdeap2dList;
    }

    private void __fixToFormatByMoleculeFile(File theMoleculeFile) throws FileNotFoundException, IOException {
        BufferedReader theFileReader = new BufferedReader(new FileReader(theMoleculeFile));

        this.__makeNewDir(this.TOTAL_TEMP_DIR);
        this.__makeNewDir(this.TEMP_DIR);

        for (int theNumberOfFile = 0, theMaximumNumberOfFile = this.getMoleculeSet().getMoleculeCount(); theNumberOfFile <= theMaximumNumberOfFile; theNumberOfFile++) {
            StringBuilder theResultFilePath = new StringBuilder();
            String theResultString = this.__fixToFormatByMoleculeFile(theFileReader);

            theResultFilePath.append(this.TEMP_DIR).append(this.TEMP_FILE_NAME).append(this.UNDER_BAR).append(String.format("%04d", theNumberOfFile)).append(this.MOLECULE_SUFFIX);
            BufferedWriter theFileWriter = new BufferedWriter(new FileWriter(theResultFilePath.toString()));

            theFileWriter.flush();
            theFileWriter.write(theResultString);
            theFileWriter.close();

            IMoleculeSet theMoleculeSet = SDFReader.openMoleculeFile(new File(theResultFilePath.toString()));
            SDFWriter.writeSDFile(theMoleculeSet, new File(theResultFilePath.toString()));
        }

        theFileReader.close();
    }

    private void __makeNewDir(String theDirPath) {
        File theFile = new File(theDirPath);

        if (theFile.exists()) {
            List<File> theFileList = Module.getFileList(theDirPath);

            for (File thePreviousFile : theFileList) {
                thePreviousFile.delete();
            }
        }

        theFile.mkdir();
    }

    private String __fixToFormatByMoleculeFile(BufferedReader theFileReader) throws IOException {
        StringBuilder theStringBuilder = new StringBuilder();
        String theString = new String();
        int theLineIndex = 0;
        int theNumberOfMolecule = 0;

        while ((theString = theFileReader.readLine()) != null) {
            if (theLineIndex < this.LINE_INDEX_HAVING_NUMBER_OF_ATOM_AND_BOND_INFORMATION) {
                theStringBuilder.append(this.__fixToFormatFromFirstLineToThirdLine(theString, theLineIndex, theNumberOfMolecule));
                theLineIndex++;
            } else if (theString.contains(this.MOLECULE_INFORMATION_END_MARK)) {
                theStringBuilder.append(theString).append(this.END_LINE);
                theLineIndex = 0;
                theNumberOfMolecule++;
            } else if (theLineIndex > this.LINE_INDEX_HAVING_NUMBER_OF_ATOM_AND_BOND_INFORMATION) {
                continue;
            } else {
                theStringBuilder.append(this.__fixToFormatInAtomAndBondInformationLine(theFileReader, theString));
                theLineIndex++;
            }
        }

        return theStringBuilder.toString();
    }

    private String __fixToFormatFromFirstLineToThirdLine(String theString, int theLineIndex, int theNumberOfMolecule) {
        StringBuilder theStringBuilder = new StringBuilder();

        if (theLineIndex != 0 || !theString.isEmpty()) {
            theStringBuilder.append(theString).append(this.END_LINE);
        } else {
            theStringBuilder.append(theNumberOfMolecule).append(this.END_LINE);
        }

        return theStringBuilder.toString();
    }

    private String __fixToFormatInAtomAndBondInformationLine(BufferedReader theFileReader, String theString) throws IOException {
        StringBuilder theStringBuilder = new StringBuilder();

        theStringBuilder.append(theString).append(this.END_LINE);
        theStringBuilder.append(this.__fixToFormatInAtomInformation(theFileReader, theString));
        theStringBuilder.append(this.__fixToFormatInBondInformation(theFileReader, theString));

        theString = theFileReader.readLine();
        theStringBuilder.append(this.ATOM_AND_BOND_STRING_END_MARK);

        return theStringBuilder.toString();
    }

    private String __fixToFormatInAtomInformation(BufferedReader theFileReader, String theString) throws IOException {
        StringBuilder theStringBuilder = new StringBuilder();
        int theNumberOfAtom = Integer.parseInt(theString.substring(this.STRING_START_INDEX, this.NUMBER_OF_ATOM_END_INDEX_IN_FILE).trim());

        for (int i = 0; i < theNumberOfAtom; i++) {
            theString = theFileReader.readLine();
            if (theString.length() > this.ATOM_STRING_END_INDEX) {
                theStringBuilder.append(theString.substring(this.STRING_START_INDEX, this.ATOM_STRING_END_INDEX)).append(this.END_LINE);
            } else {
                theStringBuilder.append(theString.substring(this.STRING_START_INDEX, this.ATOM_STRING_ATOM_SYMBOL_END_INDEX)).append(this.ADDING_ATOM_STRING);
            }
        }

        return theStringBuilder.toString();
    }

    private String __fixToFormatInBondInformation(BufferedReader theFileReader, String theString) throws IOException {
        StringBuilder theStringBuilder = new StringBuilder();
        int theNumberOfBond = Integer.parseInt(theString.substring(this.NUMBER_OF_ATOM_END_INDEX_IN_FILE, this.NUMBER_OF_BOND_END_INDEX_IN_fILE).trim());

        for (int i = 0; i < theNumberOfBond; i++) {
            theString = theFileReader.readLine();
            theStringBuilder.append(theString.substring(this.STRING_START_INDEX, this.BOND_STRING_TYPE_END_INDEX)).append(this.ADDING_BOND_STRING);
        }

        return theStringBuilder.toString();
    }

    public void generateMpeoeAndCdeapListByMoleculeFile(File theMoleculeFile, boolean theExistedTempFile) throws FileNotFoundException, IOException {
        if (!theExistedTempFile) {
            this.__calculateMpeoeAndCdeap(theMoleculeFile);
        }
        List<File> theFileList = Module.getFileList(this.TEMP_PROPERTY_DIR);

        for (File theFile : theFileList) {
            this.__inputMpeoeAndCdeapListByOneResultFile(theFile);
        }
    }

    public static void generateCalculateMpeoeAndCdeapFilePathBox(MainFrame theFrame) {
        final int theInputBoxVerticalMargin = 5;

        if (theFrame.setInputArea() != null) {
            theFrame.setInputArea().removeAll();
        } else {
            theFrame.setInputArea(Box.createVerticalBox());
        }

        theFrame.setInputArea().setFocusable(true);
        theFrame.setFilePathTextFieldList(new ArrayList<JTextField>());
        theFrame.setInputArea().add(theFrame.getTemplateFilePathBox());
        theFrame.setInputArea().add(theFrame.generateInputBox(new JLabel("Input File : "), new JTextField(20), new JButton("select")));
        theFrame.setInputArea().add(Box.createVerticalStrut(theInputBoxVerticalMargin));
        theFrame.setInputArea().add(theFrame.generateInputBox(new JLabel("Result File : "), new JTextField(20), new JButton("select")));
    }

    public void writeMpeoeAndCdeapInMoleculeFile(File theResultFilePath) {
        for (int mi = 0, mEnd = this.getMoleculeSet().getMoleculeCount(); mi < mEnd; mi++) {
            this.__writeMpeoeAndCdeapInMoleculeFile(mi);
        }
        
        SDFWriter.writeSDFile(this.getMoleculeSet(), theResultFilePath);
    }

    private void __writeMpeoeAndCdeapInMoleculeFile(int theMoleculeIndex) {
        StringBuilder theMpeoeDescriptor = new StringBuilder();
        StringBuilder theCdeapDescriptor = new StringBuilder();

        for (int ai = 0, aEnd = this.getMoleculeSet().getMolecule(theMoleculeIndex).getAtomCount(); ai < aEnd; ai++) {
            theMpeoeDescriptor.append(this.getMpeoe2dList().get(theMoleculeIndex, ai));
            theCdeapDescriptor.append(this.getCdeap2dList().get(theMoleculeIndex, ai));

            if (ai < this.getMoleculeSet().getMolecule(theMoleculeIndex).getAtomCount() - 1) {
                theMpeoeDescriptor.append(this.TAB_STRING);
                theCdeapDescriptor.append(this.TAB_STRING);
            }
        }
        
        this.setMoleculeSet().getMolecule(theMoleculeIndex).setProperty(this.MPEOE_DESCRIPTOR, theMpeoeDescriptor.toString());
        this.setMoleculeSet().getMolecule(theMoleculeIndex).setProperty(this.CDEAP_DESCRIPTOR, theCdeapDescriptor.toString());
    }

    private void __inputMpeoeAndCdeapListByOneResultFile(File theMpeoeAndCdeapFile) throws FileNotFoundException, IOException {
        String theMpeoeAndCdeapInformation = this.__readAllLine(theMpeoeAndCdeapFile);
        String[] theSplitedMpeoeAndCdeapInformation = theMpeoeAndCdeapInformation.split(this.END_LINE + this.END_LINE);

        for (String theString : theSplitedMpeoeAndCdeapInformation) {
            if (!theString.trim().isEmpty()) {
                List<Double> theMPEOEList = this.__inputMpeoeByOneResultFile(theString);
                List<Double> theCDEAPList = this.__inputCdeapByOneResultFile(theString);

                this.setMpeoe2dList().add(this.__inputMpeoeByOneResultFile(theString));
                this.setCdeap2dList().add(this.__inputCdeapByOneResultFile(theString));
            }
        }

    }

    private List<Double> __inputMpeoeByOneResultFile(String theMpeoeInformation) {
        List<Double> theMPEOEList = new ArrayList<>();
        String[] theSplitedMpeoeInformation = theMpeoeInformation.split(this.END_LINE);
        int theLineIndex = 0;

        for (String theString : theSplitedMpeoeInformation) {
            if (theLineIndex != 0) {
                String[] theSplitedString = theString.split(this.SPACE_STRING);

                if (theSplitedString.length <= this.CDEAP_INDEX || theLineIndex == 0) {
                    theLineIndex++;
                    continue;
                } else if (this.__containsNonNumberValue(theSplitedString[this.CDEAP_INDEX])) {
                    theMPEOEList.add(this.NOT_CONTAIN_PROPERTY_VALUE);
                    continue;
                }

                theMPEOEList.add(Double.parseDouble(theSplitedString[this.MPEOE_INDEX]));

            }
            theLineIndex++;
        }

        return theMPEOEList;
    }

    private List<Double> __inputCdeapByOneResultFile(String theCdeapInformation) {
        List<Double> theCDEAPList = new ArrayList<>();
        String[] theSplitedMpeoeInformation = theCdeapInformation.split(this.END_LINE);
        int theLineIndex = 0;

        for (String theString : theSplitedMpeoeInformation) {
            String[] theSplitedString = theString.split(this.SPACE_STRING);

            if (theSplitedString.length <= this.CDEAP_INDEX || theLineIndex == 0) {
                theLineIndex++;
                continue;
            } else if (this.__containsNonNumberValue(theSplitedString[this.CDEAP_INDEX])) {
                theCDEAPList.add(this.NOT_CONTAIN_PROPERTY_VALUE);
                continue;
            }

            theCDEAPList.add(Double.parseDouble(theSplitedString[this.CDEAP_INDEX]));

            theLineIndex++;
        }

        return theCDEAPList;
    }

    private void __calculateMpeoeAndCdeap(File theMoleculeFile) throws FileNotFoundException, IOException {
        List<File> theFileList = Module.getFileList(this.TEMP_DIR);

        this.__fixToFormatByMoleculeFile(theMoleculeFile);
        this.__makeNewDir(this.TEMP_PROPERTY_DIR);
        this.setMoleculeSet(SDFReader.openMoleculeFile(theMoleculeFile));
        this.setMpeoe2dList(new TwoDimensionList<Double>());
        this.setCdeap2dList(new TwoDimensionList<Double>());

        for (int fi = 0, fEnd = theFileList.size(); fi < fEnd; fi++) {
            this.__excuteExeFile(theFileList.get(fi), fi);
        }
    }

    private String __readAllLine(File theFile) throws IOException {
        BufferedReader theFileReader = new BufferedReader(new FileReader(theFile));
        StringBuilder theStringBuilder = new StringBuilder();
        String theString = new String();

        while ((theString = theFileReader.readLine()) != null) {
            theStringBuilder.append(theString).append(this.END_LINE);
        }

        return theStringBuilder.toString();
    }

    private boolean __containsNonNumberValue(String theCdeapValue) {
        if (theCdeapValue.contains(this.NOT_CALCULATED_VALUE) || theCdeapValue.contains(this.INFINTE_VALUE)) {
            return true;
        }

        return false;
    }

    private void __excuteExeFile(File theFile, int theNumberOfFile) {
        try {
            StringBuilder theExecuteString = new StringBuilder();

            theExecuteString.append(this.MPEOE_AND_CDEAP_CALCULATOR_COMMAND).append(this.SPACE_STRING).append(theFile.toString()).append(this.SPACE_STRING)
                    .append(this.TEMP_PROPERTY_DIR).append(this.RESULT_FILE_NAME_CALCULATED_MPEOE_AND_CDEAP).append(this.UNDER_BAR).append(String.format("%04d", theNumberOfFile))
                    .append(this.TEXT_SUFFIX);
            //System.out.println(theExecuteString.toString());
            Runtime theRunTime = Runtime.getRuntime();
            Process theProcess = theRunTime.exec(theExecuteString.toString());

            this.__exitExeFile(theProcess);

            theProcess.waitFor();
        } catch (IOException ex) {
            ex.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void __exitExeFile(Process theProcess) throws IOException {
        BufferedReader theOutStream = new BufferedReader(new InputStreamReader(theProcess.getInputStream()));
        BufferedReader theErrorStream = new BufferedReader(new InputStreamReader(theProcess.getErrorStream()));

        theOutStream.close();
        theErrorStream.close();
    }
}
