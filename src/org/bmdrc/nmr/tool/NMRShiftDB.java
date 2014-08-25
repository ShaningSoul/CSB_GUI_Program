package org.bmdrc.nmr.tool;

import org.bmdrc.tools.SFECalculator;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.bmdrc.nmr.interfaces.IAtomicNumber;
import org.bmdrc.nmr.Nmr1dUnit;
import org.bmdrc.nmr.Nmr1dUnitList;
import org.bmdrc.util.Module;
import org.bmdrc.util.SDFReader;
import org.bmdrc.util.SDFWriter;
import org.bmdrc.interfaces.IStringConstant;
import org.bmdrc.util.TwoDimensionList;
import org.openscience.cdk.Molecule;
import org.openscience.cdk.MoleculeSet;
import org.openscience.cdk.interfaces.IAtom;
import org.openscience.cdk.interfaces.IMolecule;
import org.openscience.cdk.interfaces.IMoleculeSet;

/**
 *
 * @author SungBo Hwang, CSB
 * @see Warning!! the Molecules inputed in this class must have hydrogen and
 * don't have salt molecule
 *
 */
public class NMRShiftDB implements IStringConstant, IAtomicNumber {

    private IMoleculeSet itsMoleculeSet;
    private TwoDimensionList<Nmr1dUnitList> itsHydrogenPeakListByMolecules;
    private TwoDimensionList<Nmr1dUnitList> itsCarbonPeakListByMolecules;
    private TwoDimensionList<Double> itsMPEOEListByMolecules;
    private TwoDimensionList<Double> itsCDEAPListByMolecules;
    private List<String> itsMoleculeNameList;
    private List<String> itsHydrogenSpectrumKeyList;
    private List<String> itsCarbonSpectrumKeyList;
    private List<Integer> itsUsedAtomicNumberList;
    private List<Double> itsSFEList;
    private String itsResultDir;
    private String itsTempMoleculeFilePath;
    //constant string variable
    private final String MOLECULE_INFORMATION_END_MARK = "$$$$";
    private final String ADDING_ATOM_STRING = "  0  0  0  0  0\n";
    private final String ADDING_BOND_STRING = "  0  0  0\n";
    private final String ATOM_AND_BOND_STRING_END_MARK = "M  END\n";
    private final String TEMP_FILE_NAME = "temp";
    private final String TEMP_MOLECULE_FILE_NAME = "molecule_temp";
    private final String HYDROGEN_SPECTRUM_KEY = "Spectrum 1H ";
    private final String CARBON_SPECTRUM_KEY = "Spectrum 13C ";
    private final String TEMPERATURE_KEY = "Temperature [K]";
    private final String FIELD_STRENGTH_KEY = "Field Strength [MHz]";
    private final String SOLVENT_KEY = "Solvent";
    private final String CRITERION_DIVIDED_PEAK = "\\|";
    private final String CRITERION_DIVIDED_PEAK_INFORMATION = ";";
    private final String MPEOE_AND_CDEAP_CALCULATOR_COMMAND = "D:\\Users\\labwindows\\Documents\\NetBeansProjects\\NMR_Processing\\exe_program\\calculateMPEOEandCDEAP.exe";
    private final String RESULT_FILE_NAME_CALCULATED_MPEOE_AND_CDEAP = "property";
    private final String TEXT_SUFFIX = ".txt";
    private final String MOLECULE_SUFFIX = ".sd";
    private final String TOTAL_TEMP_DIR = "Temp\\";
    private final String TEMP_DIR = this.TOTAL_TEMP_DIR + "temp\\";
    private final String TEMP_MOLECULE_DIR = this.TOTAL_TEMP_DIR + "molecule_temp\\";
    private final String TEMP_PROPERTY_DIR = this.TOTAL_TEMP_DIR + "property_temp\\";
    private final String TEMP_SFE_DIR = this.TOTAL_TEMP_DIR + "SFE_temp\\";
    private final String NOT_CALCULATED_VALUE = "nan";
    private final String INFINTE_VALUE = "inf";
    private final String CRITERION_DIVIDED_EXPERIMENT_ENVIRONMENT_INFORMATION = "[0-9]{1,2}:";
    private final String CRITERION_DIVIDED_EXPERIMENT_ENVIRONMENT_INDEX = "\\:\\s?\\S+\\s?";
    private final String CRITERION_DIVIDED_INTENSITY = "^\\d+.\\d+";
    private final String PATTERN_EXPERIMENT_ENVIRONMENT_INFORMATION = "\\d{1,2}:\\s?";
    private final String PREDICTED_PROGRAM_KEY = "Program";
    private final String CHEMICAL_SHIFT_COLUMN_NAME_IN_RESULT_FILE = "Chemical shift";
    private final String MPEOE_COLUMN_NAME_IN_RESULT_FILE = "MPEOE";
    private final String CDEAP_COLUMN_NAME_IN_RESULT_FILE = "CDEAP";
    private final String SFE_COULUMN_NAME_IN_RESULT_FILE = "SFE";
    private final String CRITERION_DIVIDED_COLUMN_IN_RESULT_FILE = "\t";
    private final String FUNCTIONAL_GROUP_KEY = "Functional_Group";
    private final String CRITERION_DIVIDED_FUNCTIONAL_GROUP = "\t";
    private final String UNKNOWN_SOLVENT = "Unknown";
    private final String UNREPORT_SOLVENT = "Unreported";
    private final String NMRSHIFT_ID_KEY = "nmrshiftdb2 ID";
    private final String HOSE_CODE_KEY = "HOSE_CODE_";
    //constant double variable
    private final double NOT_CONTAIN_PROPERTY_VALUE = Double.NaN;
    private final double INCORRECT_VALUE = Double.NaN;
    //constant int variable
    private final int STRING_START_INDEX = 0;
    private final int ATOM_STRING_ATOM_SYMBOL_END_INDEX = 33;
    private final int ATOM_STRING_END_INDEX = 48;
    private final int BOND_STRING_TYPE_END_INDEX = 9;
    private final int NUMBER_OF_ATOM_END_INDEX_IN_FILE = 3;
    private final int NUMBER_OF_BOND_END_INDEX_IN_fILE = 6;
    private final int LINE_INDEX_HAVING_NUMBER_OF_ATOM_AND_BOND_INFORMATION = 3;
    private final int MAXIMUM_NUMBER_OF_HYDROGEN_SPECTRUM_KEY = 10;
    private final int MAXIMUM_NUMBER_OF_CARBON_SPECTRUM_KEY = 10;
    private final int CHEMICAL_SHIFT_INDEX = 0;
    private final int INTENSITY_INDEX = 1;
    private final int ANNOTATED_ATOM_NUMBER_INDEX = 2;
    private final int NUMBER_OF_MOLECULE_IN_FILE = 1;
    private final int MPEOE_AND_CDEAP_INFORMATION_STARTING_LINE_INDEX = 1;
    private final int MPEOE_INDEX = 1;
    private final int CDEAP_INDEX = 3;
    private final int MAXIMUM_NUMBER_OF_ATOM = 80;
    private final int TEMPERATURE_INDEX = 0;
    private final int FIELD_STRENGTH_INDEX = 1;
    private final int SOLVENT_INDEX = 2;
    private final int FIRST_INDEX = 0;
    private final int CONNECTED_ATOM_INDEX_IN_HYDROGEN = 0;
    private final int INCORRECT_ATOM_NUMBER = -1;
    private final int VALUE_INDEX_IN_SFE_FILE = 1;

    public NMRShiftDB() {
        this.setMoleculeSet(new MoleculeSet());
        this.setHydrogenPeakListByMolecules(new TwoDimensionList<Nmr1dUnitList>());
        this.setCarbonPeakListByMolecules(new TwoDimensionList<Nmr1dUnitList>());
        this.setMPEOEListByMolecules(new TwoDimensionList<Double>());
        this.setCDEAPListByMolecules(new TwoDimensionList<Double>());
        this.__generateHydrogenSpectrumKeyList();
        this.__generateCarbonSpectrumKeyList();
        this.__generateUsedAtomicNumberList();
        this.__setTempMoleculeFilePath(this.TEMP_MOLECULE_DIR + this.TEMP_MOLECULE_FILE_NAME + this.MOLECULE_SUFFIX);
        this.__makeNewDir(this.TOTAL_TEMP_DIR);
    }

    public NMRShiftDB(File theMoleculeFile) throws FileNotFoundException, IOException, InterruptedException {
        this.__makeNewDir(this.TOTAL_TEMP_DIR);
        this.__setTempMoleculeFilePath(this.TEMP_MOLECULE_DIR + this.TEMP_MOLECULE_FILE_NAME + this.MOLECULE_SUFFIX);
        this.__generateHydrogenSpectrumKeyList();
        this.__generateCarbonSpectrumKeyList();
        this.__generateUsedAtomicNumberList();
        this.__sortUsualMolecule(theMoleculeFile, false);
        this.__inputMpeoeAndCdeapListByMoleculeFile(theMoleculeFile, false);
        this.__inputPeakListByMolecule();
        this.__generateSFEList(false);
    }
    
    public NMRShiftDB(File theMoleculeFile, boolean theExistedTempFile) throws FileNotFoundException, IOException, InterruptedException {
        this.__makeNewDir(this.TOTAL_TEMP_DIR);
        this.__setTempMoleculeFilePath(this.TEMP_MOLECULE_DIR + this.TEMP_MOLECULE_FILE_NAME + this.MOLECULE_SUFFIX);
        this.__generateHydrogenSpectrumKeyList();
        this.__generateCarbonSpectrumKeyList();
        this.__generateUsedAtomicNumberList();
        this.__sortUsualMolecule(theMoleculeFile, theExistedTempFile);
        this.__inputMpeoeAndCdeapListByMoleculeFile(theMoleculeFile, theExistedTempFile);
        this.__inputPeakListByMolecule();
        this.__generateSFEList(theExistedTempFile);
    }

    public NMRShiftDB(NMRShiftDB theNMRShiftDB) throws CloneNotSupportedException {
        this.setMoleculeSet((MoleculeSet) theNMRShiftDB.getMoleculeSet().clone());
        this.setHydrogenPeakListByMolecules(new TwoDimensionList<>(theNMRShiftDB.getHydrogenPeakListByMolecules()));
        this.setCarbonPeakListByMolecules(new TwoDimensionList<>(theNMRShiftDB.getCarbonPeakListByMolecules()));
        this.setMPEOEListByMolecules(new TwoDimensionList<>(theNMRShiftDB.getMPEOEListByMolecules()));
        this.setCDEAPListByMolecules(new TwoDimensionList<>(theNMRShiftDB.getCDEAPListByMolecules()));
        this.setSFEList(new ArrayList<>(theNMRShiftDB.getSFEList()));
        this.__generateHydrogenSpectrumKeyList();
        this.__generateCarbonSpectrumKeyList();
        this.__generateUsedAtomicNumberList();
        this.__setTempMoleculeFilePath(this.TEMP_MOLECULE_DIR + this.TEMP_MOLECULE_FILE_NAME + this.MOLECULE_SUFFIX);
        this.__makeNewDir(this.TOTAL_TEMP_DIR);
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

    public TwoDimensionList<Nmr1dUnitList> getHydrogenPeakListByMolecules() {
        return itsHydrogenPeakListByMolecules;
    }

    public void setHydrogenPeakListByMolecules(TwoDimensionList<Nmr1dUnitList> thePeakListByMolecules) {
        this.itsHydrogenPeakListByMolecules = thePeakListByMolecules;
    }

    public TwoDimensionList<Nmr1dUnitList> setHydrogenPeakListByMolecules() {
        return itsHydrogenPeakListByMolecules;
    }

    public TwoDimensionList<Double> getMPEOEListByMolecules() {
        return itsMPEOEListByMolecules;
    }

    public void setMPEOEListByMolecules(TwoDimensionList<Double> theMPEOEListByMolecules) {
        this.itsMPEOEListByMolecules = theMPEOEListByMolecules;
    }

    public TwoDimensionList<Double> setMPEOEListByMolecules() {
        return itsMPEOEListByMolecules;
    }

    public TwoDimensionList<Double> getCDEAPListByMolecules() {
        return itsCDEAPListByMolecules;
    }

    public void setCDEAPListByMolecules(TwoDimensionList<Double> theCDEAPListByMolecules) {
        this.itsCDEAPListByMolecules = theCDEAPListByMolecules;
    }

    public TwoDimensionList<Double> setCDEAPListByMolecules() {
        return itsCDEAPListByMolecules;
    }

    private List<String> __getHydrogenSpectrumKeyList() {
        return itsHydrogenSpectrumKeyList;
    }

    private void __setHydrogenSpectrumKeyList(List<String> itsHydrogenSpectrumKeyList) {
        this.itsHydrogenSpectrumKeyList = itsHydrogenSpectrumKeyList;
    }

    private List<String> __setHydrogenSpectrumKeyList() {
        return itsHydrogenSpectrumKeyList;
    }

    private List<Integer> __getUsedAtomicNumberList() {
        return itsUsedAtomicNumberList;
    }

    private void __setUsedAtomicNumberList(List<Integer> theUsedAtomicNumberList) {
        this.itsUsedAtomicNumberList = theUsedAtomicNumberList;
    }

    private List<Integer> __setUsedAtomicNumberList() {
        return itsUsedAtomicNumberList;
    }

    private String __getTempMoleculeFilePath() {
        return itsTempMoleculeFilePath;
    }

    private void __setTempMoleculeFilePath(String theTempMoleculeFilePath) {
        this.itsTempMoleculeFilePath = theTempMoleculeFilePath;
    }

    private List<String> __getCarbonSpectrumKeyList() {
        return itsCarbonSpectrumKeyList;
    }

    private void __setCarbonSpectrumKeyList(List<String> itsCarbonSpectrumKeyList) {
        this.itsCarbonSpectrumKeyList = itsCarbonSpectrumKeyList;
    }

    private List<String> __setCarbonSpectrumKeyList() {
        return itsCarbonSpectrumKeyList;
    }

    public TwoDimensionList<Nmr1dUnitList> getCarbonPeakListByMolecules() {
        return itsCarbonPeakListByMolecules;
    }

    public void setCarbonPeakListByMolecules(TwoDimensionList<Nmr1dUnitList> itsCarbonPeakListByMolecules) {
        this.itsCarbonPeakListByMolecules = itsCarbonPeakListByMolecules;
    }

    public TwoDimensionList<Nmr1dUnitList> setCarbonPeakListByMolecules() {
        return itsCarbonPeakListByMolecules;
    }

    public List<String> getMoleculeNameList() {
        return itsMoleculeNameList;
    }

    public void setMoleculeNameList(List<String> theMoleculeNameList) {
        this.itsMoleculeNameList = theMoleculeNameList;
    }

    public List<String> setMoleculeNameList() {
        return itsMoleculeNameList;
    }

    public List<Double> getSFEList() {
        return itsSFEList;
    }

    public void setSFEList(List<Double> theSFEList) {
        this.itsSFEList = theSFEList;
    }

    public List<Double> setSFEList() {
        return itsSFEList;
    }

    private void __generateSFEList(boolean theExistedTempFile) throws IOException, InterruptedException {
        if (!theExistedTempFile) {
            this.__generateSFETempFile();
        }

        this.setSFEList(new ArrayList<Double>());

        List<File> theFileList = Module.getFileList(this.TEMP_SFE_DIR);

        for (File theFile : theFileList) {
            this.__generateSFEList(theFile);
        }
    }

    private void __generateSFEList(File theSFEFile) throws FileNotFoundException, IOException {
        BufferedReader theFileReader = new BufferedReader(new FileReader(theSFEFile));
        String theFileString = new String();

        while ((theFileString = theFileReader.readLine()) != null) {
            String[] theSplitedFileString = theFileString.split(this.COLON_REGEX);

            if (!theSplitedFileString[theSplitedFileString.length - 1].contains(this.NOT_CALCULATED_VALUE)
                    && !theSplitedFileString[theSplitedFileString.length - 1].contains(this.INFINTE_VALUE)) {
                double theSFEValue = Double.parseDouble(theSplitedFileString[theSplitedFileString.length - 1]);

                this.setSFEList().add(theSFEValue);
            } else {
                this.setSFEList().add(this.INCORRECT_VALUE);
            }
        }

        theFileReader.close();
    }

    private void __generateSFETempFile() throws IOException, InterruptedException {
        List<File> theFileList = Module.getFileList(this.TEMP_DIR);
        File theSFEDir = new File(this.TEMP_SFE_DIR);

        if (!theSFEDir.exists()) {
            theSFEDir.mkdir();
        }

        for (File theFile : theFileList) {
            StringBuilder theResultFilePath = new StringBuilder();

            try {
                theResultFilePath.append(this.TEMP_SFE_DIR).append(theFile.getName().split(this.DOT_REGEX)[this.FIRST_INDEX]).append(this.TEXT_SUFFIX);
                SFECalculator.calculateSFE(theFile.toString(), theResultFilePath.toString());
            } catch (ArrayIndexOutOfBoundsException e) {
                e.printStackTrace();
                System.err.println(theFile.getName());
            }
        }
    }

    private void __generateUsedAtomicNumberList() {
        this.__setUsedAtomicNumberList(new ArrayList<Integer>());

        this.__setUsedAtomicNumberList().add(this.H_ATOMIC_NUMBER);
        this.__setUsedAtomicNumberList().add(this.C_ATOMIC_NUMBER);
        this.__setUsedAtomicNumberList().add(this.N_ATOMIC_NUMBER);
        this.__setUsedAtomicNumberList().add(this.O_ATOMIC_NUMBER);
        this.__setUsedAtomicNumberList().add(this.P_ATOMIC_NUMBER);
        this.__setUsedAtomicNumberList().add(this.S_ATOMIC_NUMBER);
        this.__setUsedAtomicNumberList().addAll(this.__generateHalogenAtomicNumberList());
    }

    private List<Integer> __generateHalogenAtomicNumberList() {
        List<Integer> theHalogenAtomicNumberList = new ArrayList<>();

        theHalogenAtomicNumberList.add(this.F_ATOMIC_NUMBER);
        theHalogenAtomicNumberList.add(this.Cl_ATOMIC_NUMBER);
        theHalogenAtomicNumberList.add(this.Br_ATOMIC_NUMBER);
        theHalogenAtomicNumberList.add(this.I_ATOMIC_NUMBER);

        return theHalogenAtomicNumberList;
    }

    private void __generateHydrogenSpectrumKeyList() {
        this.__setHydrogenSpectrumKeyList(new ArrayList<String>());

        for (int ki = 0; ki <= this.MAXIMUM_NUMBER_OF_HYDROGEN_SPECTRUM_KEY; ki++) {
            this.__setHydrogenSpectrumKeyList().add(this.HYDROGEN_SPECTRUM_KEY + ki);
        }
    }

    private void __generateCarbonSpectrumKeyList() {
        this.__setCarbonSpectrumKeyList(new ArrayList<String>());

        for (int ki = 0; ki <= this.MAXIMUM_NUMBER_OF_CARBON_SPECTRUM_KEY; ki++) {
            this.__setCarbonSpectrumKeyList().add(this.CARBON_SPECTRUM_KEY + ki);
        }
    }

    private void __sortUsualMolecule(File theMoleculeFile, boolean theExistedTempFile) {
        IMoleculeSet theMoleculeSet = SDFReader.openMoleculeFile(theMoleculeFile);
        StringBuilder theTempMoleculeFilePath = new StringBuilder();
        int theNumberOfMolecule = 0;

        this.setMoleculeSet(new MoleculeSet());

        for (int mi = 0, mEnd = theMoleculeSet.getMoleculeCount(); mi < mEnd; mi++) {
            if (this.__isUsualMolecule(theMoleculeSet.getMolecule(mi))) {
                this.setMoleculeSet().addMolecule(theMoleculeSet.getMolecule(mi));
            }
        }

        if (!theExistedTempFile) {
            this.__makeNewDir(this.TEMP_MOLECULE_DIR);
            SDFWriter.writeSDFile(this.getMoleculeSet(), new File(this.__getTempMoleculeFilePath()));
        }
    }

    private boolean __containOnlyHalogen(IMolecule theMolecule) {
        List<Integer> theHalogenAtomicNumberList = this.__generateHalogenAtomicNumberList();

        for (int ai = 0, aEnd = theMolecule.getAtomCount(); ai < aEnd; ai++) {
            if (!theHalogenAtomicNumberList.contains(theMolecule.getAtom(ai).getAtomicNumber())) {
                return false;
            }
        }

        return true;
    }

    private boolean __containOnlyUnknownSolvent(IMolecule theMolecule) {
        if (!theMolecule.getProperties().keySet().contains(this.SOLVENT_KEY)) {
            return true;
        } else {
            String theSolventValue = theMolecule.getProperty(this.SOLVENT_KEY).toString();
            String[] theSplitedSolventValue = theSolventValue.split(this.CRITERION_DIVIDED_EXPERIMENT_ENVIRONMENT_INFORMATION);

            for (String theSolvent : theSplitedSolventValue) {
                if (!theSolvent.contains(this.UNKNOWN_SOLVENT) && !theSolvent.contains(this.UNREPORT_SOLVENT)) {
                    return false;
                }
            }
        }

        return true;
    }

    private boolean __isUsualMolecule(IMolecule theMolecule) {
        if (theMolecule.getAtomCount() > this.MAXIMUM_NUMBER_OF_ATOM || theMolecule.getAtomCount() == 0) {
            return false;
        } else if (this.__containOnlyHalogen(theMolecule)) {
            return false;
        } else if (this.__containOnlyUnknownSolvent(theMolecule)) {
            return false;
        }

        for (int ai = 0, aEnd = theMolecule.getAtomCount(); ai < aEnd; ai++) {
            if (!this.__getUsedAtomicNumberList().contains(theMolecule.getAtom(ai).getAtomicNumber())) {
                return false;
            }
        }

        return true;
    }

    private List<String> __generateExperimentEnvironmentList(IMolecule theMolecule, int theIndex) {
        List<String> theExperimentEnvironmentList = new ArrayList<>();
        String theTemperatureInformation = theMolecule.getProperty(this.TEMPERATURE_KEY).toString();
        String theFieldStrengthInformation = theMolecule.getProperty(this.FIELD_STRENGTH_KEY).toString();
        String theSolventInformation = theMolecule.getProperty(this.SOLVENT_KEY).toString();

        theExperimentEnvironmentList.add(this.__getValueInIndexOfExperimentEnvironment(theTemperatureInformation, theIndex));
        theExperimentEnvironmentList.add(this.__getValueInIndexOfExperimentEnvironment(theFieldStrengthInformation, theIndex));
        theExperimentEnvironmentList.add(this.__getValueInIndexOfExperimentEnvironment(theSolventInformation, theIndex));

        return theExperimentEnvironmentList;
    }

    private String __getValueInIndexOfExperimentEnvironment(String theExperimentEnvrionmentInformation, int theIndex) {
        List<String> theEnvironmentList = this.__getEnvironmentList(theExperimentEnvrionmentInformation);
        List<String> theEnvironemtIndexList = this.__getEnvironmentIndexList(theExperimentEnvrionmentInformation);
        String theString = new String();

        try {
            theString = theEnvironmentList.get(theEnvironemtIndexList.indexOf(Integer.toString(theIndex)));
        } catch (ArrayIndexOutOfBoundsException e) {
            e.printStackTrace();
            System.err.println(theIndex);
            System.err.println(theEnvironemtIndexList);
            System.err.println(theExperimentEnvrionmentInformation);
        }

        return theString;
    }

    private List<String> __getEnvironmentList(String theExperimentEnvironmentInformation) {
        List<String> theEnvironmentList = new ArrayList<>();
        String[] theSplitedString = theExperimentEnvironmentInformation.split(this.CRITERION_DIVIDED_EXPERIMENT_ENVIRONMENT_INFORMATION);

        Collections.addAll(theEnvironmentList, theSplitedString);

        theEnvironmentList.remove(this.FIRST_INDEX);//first value is empty

        return theEnvironmentList;
    }

    private List<String> __getEnvironmentIndexList(String theExperimentEnvironmentInformation) {
        List<String> theEnvironmentIndexList = new ArrayList<>();
        String[] theSplitedString = theExperimentEnvironmentInformation.split(this.CRITERION_DIVIDED_EXPERIMENT_ENVIRONMENT_INDEX);

        for (int ai = 0, aEnd = theSplitedString.length; ai < aEnd; ai++) {
            String[] theCheckStringArray = theSplitedString[ai].split(this.SPACE_STRING);

            if (theCheckStringArray.length > 1) {
                theSplitedString[ai] = theCheckStringArray[theCheckStringArray.length - 1];
            }
        }

        Collections.addAll(theEnvironmentIndexList, theSplitedString);

        return theEnvironmentIndexList;
    }

    private void __inputPeakListByMolecule() {
        this.setHydrogenPeakListByMolecules(new TwoDimensionList<Nmr1dUnitList>());
        this.setCarbonPeakListByMolecules(new TwoDimensionList<Nmr1dUnitList>());

        for (int mi = 0, mEnd = this.getMoleculeSet().getMoleculeCount(); mi < mEnd; mi++) {
            this.setHydrogenPeakListByMolecules().add(this.__inputHydrogenPeakListByMolecule(this.getMoleculeSet().getMolecule(mi)));
            this.setCarbonPeakListByMolecules().add(this.__inputCarbonPeakListByMolecule(this.getMoleculeSet().getMolecule(mi)));
        }
    }

    private int __getKeyIndex(String theKey) {
        String[] theSplitedString = theKey.split(this.SPACE_STRING);

        return Integer.parseInt(theSplitedString[theSplitedString.length - 1]);
    }

    private boolean __matchBetweenPeakListIndexAndProgramIndex(IMolecule theMolecule, int thePeakListIndex) {
        List<String> theProgramIndexList = this.__getEnvironmentIndexList(theMolecule.getProperty(this.PREDICTED_PROGRAM_KEY).toString());

        if (theProgramIndexList.contains(Integer.toString(thePeakListIndex))) {
            return true;
        }

        return false;
    }

    private boolean __isPredictedPeakList(IMolecule theMolecule, String theKey) {
        int theKeyIndex = this.__getKeyIndex(theKey);

        if (!theMolecule.getProperties().keySet().contains(this.PREDICTED_PROGRAM_KEY)) {
            return false;
        } else if (!this.__matchBetweenPeakListIndexAndProgramIndex(theMolecule, theKeyIndex)) {
            return false;
        }

        return true;
    }

    private List<Nmr1dUnitList> __inputHydrogenPeakListByMolecule(IMolecule theMolecule) {
        List<Nmr1dUnitList> thePeak2dList = new ArrayList<>();

        for (int ki = 0, kEnd = this.__getHydrogenSpectrumKeyList().size(); ki < kEnd; ki++) {
            if (theMolecule.getProperties().keySet().contains(this.__getHydrogenSpectrumKeyList().get(ki))
                    && !this.__isPredictedPeakList(theMolecule, this.__getHydrogenSpectrumKeyList().get(ki))) {
                List<String> theExperimentEnvironmentList = this.__generateExperimentEnvironmentList(theMolecule, ki);

                if (!theExperimentEnvironmentList.get(this.SOLVENT_INDEX).contains(this.UNKNOWN_SOLVENT) && !theExperimentEnvironmentList.get(this.SOLVENT_INDEX).contains(this.UNREPORT_SOLVENT)) {
                    thePeak2dList.add(this.__inputPeakListByMolecule(theMolecule, theMolecule.getProperty(this.__getHydrogenSpectrumKeyList().get(ki)).toString(),
                            theExperimentEnvironmentList, false));
                }
            }
        }

        return thePeak2dList;
    }

    private List<Nmr1dUnitList> __inputCarbonPeakListByMolecule(IMolecule theMolecule) {
        List<Nmr1dUnitList> thePeak2dList = new ArrayList<>();

        for (int ki = 0, kEnd = this.__getCarbonSpectrumKeyList().size(); ki < kEnd; ki++) {
            if (theMolecule.getProperties().keySet().contains(this.__getCarbonSpectrumKeyList().get(ki))
                    && !this.__isPredictedPeakList(theMolecule, this.__getCarbonSpectrumKeyList().get(ki))) {
                try {
                    List<String> theExperimentEnvironmentList = this.__generateExperimentEnvironmentList(theMolecule, ki);

                    thePeak2dList.add(this.__inputPeakListByMolecule(theMolecule, theMolecule.getProperty(this.__getCarbonSpectrumKeyList().get(ki)).toString(),
                            theExperimentEnvironmentList, true));
                } catch (ArrayIndexOutOfBoundsException e) {
                    e.printStackTrace();
                    System.err.println(theMolecule.getProperties().keySet());
                    System.err.println(this.__getCarbonSpectrumKeyList().get(ki));
                    System.err.println(theMolecule.getProperty("nmrshiftdb2 ID"));
                }
            }
        }

        return thePeak2dList;
    }

    private Nmr1dUnitList __inputPeakListByMolecule(IMolecule theMolecule, String theSpectrumInformation, List<String> theExperimentEnvironmentList, boolean theJugmentOfCarbonPeak) {
        Nmr1dUnitList thePeakList = new Nmr1dUnitList();
        String[] theSplitedInformation = theSpectrumInformation.split(this.CRITERION_DIVIDED_PEAK);
        List<String> theList = new ArrayList<>();

        for (String thePeakInformation : theSplitedInformation) {
            if (!thePeakInformation.isEmpty()) {
                thePeakList.addAllPeaks(this.__generatePeakListInformation(theMolecule, thePeakInformation, theExperimentEnvironmentList, theJugmentOfCarbonPeak));
            }
        }

        return thePeakList;
    }

    private Nmr1dUnitList __generatePeakListInformation(IMolecule theMolecule, String thePeakInformation, List<String> theExperimentEnvironmentList, boolean theJugmentOfCarbonPeak) {
        Nmr1dUnitList thePeakList = new Nmr1dUnitList();
        String[] theSplitedPeakInformation = thePeakInformation.split(this.CRITERION_DIVIDED_PEAK_INFORMATION);

        theSplitedPeakInformation[this.ANNOTATED_ATOM_NUMBER_INDEX] =
                this.__getCorrectAnnotatedAtomNumber(theMolecule, theSplitedPeakInformation[this.ANNOTATED_ATOM_NUMBER_INDEX], theJugmentOfCarbonPeak);

        thePeakList.addPeak(this.__generatePeakInformation(theSplitedPeakInformation, theExperimentEnvironmentList));

        return thePeakList;
    }

    private String __getCorrectAnnotatedAtomNumber(IMolecule theMolecule, String theAnnotatedAtomNumber, boolean theJugmentOfCarbonPeak) {
        if (theJugmentOfCarbonPeak) {
            return theAnnotatedAtomNumber;
        } else {
            List<IAtom> theConnectedAtomList = theMolecule.getConnectedAtomsList(theMolecule.getAtom(Integer.parseInt(theAnnotatedAtomNumber)));

            for (IAtom theAtom : theConnectedAtomList) {
                if (theAtom.getAtomicNumber() == this.H_ATOMIC_NUMBER) {
                    return Integer.toString(theMolecule.getAtomNumber(theAtom));
                }
            }
        }

        return Integer.toString(this.INCORRECT_ATOM_NUMBER);
    }

    private Nmr1dUnit __generatePeakInformation(String[] thePeakInformationArray, List<String> theExperimentEnvironmentList) {
        Nmr1dUnit thePeak = new Nmr1dUnit();

        thePeak.setChemicalShift(Double.parseDouble(thePeakInformationArray[this.CHEMICAL_SHIFT_INDEX]));
        thePeak.setIntensity(this.__convertIntensityValue(thePeakInformationArray[this.INTENSITY_INDEX]));
        thePeak.setAnnotatedAtomNumber(Integer.parseInt(thePeakInformationArray[this.ANNOTATED_ATOM_NUMBER_INDEX]));
        thePeak.setTemperature(theExperimentEnvironmentList.get(this.TEMPERATURE_INDEX));
        thePeak.setFieldStrength(theExperimentEnvironmentList.get(this.FIELD_STRENGTH_INDEX));
        thePeak.setSolvent(theExperimentEnvironmentList.get(this.SOLVENT_INDEX).trim());

        return thePeak;
    }

    private double __convertIntensityValue(String theIntensityInformation) {
        String[] theSplitedString = theIntensityInformation.split(this.CRITERION_DIVIDED_INTENSITY);
        int theSizeOfNonNumericValue = 0;
        int theSizeOfIntensityInformation = theIntensityInformation.length();

        for (String theString : theSplitedString) {
            theSizeOfNonNumericValue += theString.length();
        }

        double theDouble = 0;
        try {
            theDouble = Double.parseDouble(theIntensityInformation.substring(0, theSizeOfIntensityInformation - theSizeOfNonNumericValue));
        } catch (NumberFormatException e) {
            e.printStackTrace();
            System.err.println(theIntensityInformation);
        }

        return theDouble;
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

    private void __fixToFormatByMoleculeFile() throws FileNotFoundException, IOException {
        BufferedReader theFileReader = new BufferedReader(new FileReader(this.__getTempMoleculeFilePath()));

        this.__makeNewDir(this.TEMP_DIR);

        for (int theNumberOfFile = 0, theMaximumNumberOfFile = this.getMoleculeSet().getMoleculeCount() / this.NUMBER_OF_MOLECULE_IN_FILE + 1;
                theNumberOfFile < theMaximumNumberOfFile; theNumberOfFile++) {
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
                if (theNumberOfMolecule == this.NUMBER_OF_MOLECULE_IN_FILE) {
                    break;
                }
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

    private void __inputMpeoeAndCdeapListByMoleculeFile(File theMoleculeFile, boolean theExistedTempFile) throws FileNotFoundException, IOException {
        if (!theExistedTempFile) {
            this.__calculateMpeoeAndCdeap(theMoleculeFile);
        }
        List<File> theFileList = Module.getFileList(this.TEMP_PROPERTY_DIR);

        this.setMPEOEListByMolecules(new TwoDimensionList<Double>());
        this.setCDEAPListByMolecules(new TwoDimensionList<Double>());
        this.setMoleculeNameList(new ArrayList<String>());

        for (File theFile : theFileList) {
            this.__inputMpeoeAndCdeapListByOneResultFile(theFile);
        }
    }

    private void __inputMpeoeAndCdeapListByOneResultFile(File theMpeoeAndCdeapFile) throws FileNotFoundException, IOException {
        String theMpeoeAndCdeapInformation = this.__readAllLine(theMpeoeAndCdeapFile);
        String[] theSplitedMpeoeAndCdeapInformation = theMpeoeAndCdeapInformation.split(this.END_LINE + this.END_LINE);

        for (String theString : theSplitedMpeoeAndCdeapInformation) {
            if (!theString.trim().isEmpty()) {
                List<Double> theMPEOEList = this.__inputMpeoeByOneResultFile(theString);
                List<Double> theCDEAPList = this.__inputCdeapByOneResultFile(theString);

                this.setMPEOEListByMolecules().add(this.__inputMpeoeByOneResultFile(theString));
                this.setCDEAPListByMolecules().add(this.__inputCdeapByOneResultFile(theString));
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
                    if (theLineIndex == 0) {
                        this.setMoleculeNameList().add(theString);
                    }

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

    private boolean __containsNonNumberValue(String theCdeapValue) {
        if (theCdeapValue.contains(this.NOT_CALCULATED_VALUE) || theCdeapValue.contains(this.INFINTE_VALUE)) {
            return true;
        }

        return false;
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

        this.__fixToFormatByMoleculeFile();
        this.__makeNewDir(this.TEMP_PROPERTY_DIR);

        for (int fi = 0, fEnd = theFileList.size(); fi < fEnd; fi++) {
            if (!theFileList.get(fi).getName().contains(this.TEMP_MOLECULE_FILE_NAME)) {
                this.__excuteExeFile(theFileList.get(fi), fi);
            }
        }
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

    private String __readAllLine(File theFile) throws IOException {
        BufferedReader theFileReader = new BufferedReader(new FileReader(theFile));
        StringBuilder theStringBuilder = new StringBuilder();
        String theString = new String();

        while ((theString = theFileReader.readLine()) != null) {
            theStringBuilder.append(theString).append(this.END_LINE);
        }

        return theStringBuilder.toString();
    }

    public void deleteTempDirectory() {
        Module.deleteDirectory(new File(this.TOTAL_TEMP_DIR));
    }

    public void writeChemicalShiftAndMpeoeAndCdeapBySDFormat(String theResultFilePath, String theFunctionalGroup) {
        IMoleculeSet theMoleculeSet = new MoleculeSet();

        for (int li = 0, lEnd = this.getHydrogenPeakListByMolecules().size(); li < lEnd; li++) {
            theMoleculeSet.add(this.__getChemicalShiftAndMpeoeAndCdeapBySDFormat(this.getHydrogenPeakListByMolecules().get(li), li, theFunctionalGroup));
        }

        SDFWriter.writeSDFile(theMoleculeSet, new File(theResultFilePath));
    }

    private IMoleculeSet __getChemicalShiftAndMpeoeAndCdeapBySDFormat(List<Nmr1dUnitList> thePeak2dList, int theMoleculeIndex, String theFunctionalGroup) {
        IMoleculeSet theMoleculeSet = new MoleculeSet();

        for (Nmr1dUnitList thePeakList : thePeak2dList) {
            theMoleculeSet.add(this.__getChemicalShiftAndMpeoeAndCdeapBySDFormat(thePeakList, theMoleculeIndex, theFunctionalGroup));
        }

        return theMoleculeSet;
    }

    private IMoleculeSet __getChemicalShiftAndMpeoeAndCdeapBySDFormat(Nmr1dUnitList thePeakList, int theMoleculeIndex, String theFunctionalGroup) {
        IMoleculeSet theMoleculeSet = new MoleculeSet();
        List<String> theFunctionalGroupList = this.__getFunctionalGroupList(this.getMoleculeSet().getMolecule(theMoleculeIndex));

        for (Nmr1dUnit thePeak : thePeakList.getPeakList()) {
            if (thePeak.getAnnotatedAtomNumber() != this.INCORRECT_ATOM_NUMBER) {
                int theFunctionalGroupIndex = this.__getFunctionalGroupIndex(thePeak.getAnnotatedAtomNumber(), theMoleculeIndex);

                if (theFunctionalGroupList.contains(theFunctionalGroup)) {
                    theMoleculeSet.addMolecule(this.__getChemicalShiftAndMpeoeAndCdeapBySDFormat(thePeak, theMoleculeIndex));
                }
            }
        }

        return theMoleculeSet;
    }

    private IMolecule __getChemicalShiftAndMpeoeAndCdeapBySDFormat(Nmr1dUnit thePeak, int theMoleculeIndex) {
        IMolecule theMolecule = new Molecule();
        int theAnnotatedAtomNumber = thePeak.getAnnotatedAtomNumber();

        theMolecule.setProperty(this.CHEMICAL_SHIFT_COLUMN_NAME_IN_RESULT_FILE, thePeak.getChemicalShift());
        theMolecule.setProperty(this.MPEOE_COLUMN_NAME_IN_RESULT_FILE, this.getMPEOEListByMolecules().get(theMoleculeIndex).get(theAnnotatedAtomNumber));
        theMolecule.setProperty(this.CDEAP_COLUMN_NAME_IN_RESULT_FILE, this.getCDEAPListByMolecules().get(theMoleculeIndex).get(theAnnotatedAtomNumber));

        return theMolecule;
    }

    public void writeChemicalShiftAndMpeoeEAndCdeapByTextFormat(String theResultFilePath, String theFunctionalGroup) throws IOException {
        BufferedWriter theFileWriter = new BufferedWriter(new FileWriter(theResultFilePath));
        String theFileString = this.__getChemicalShiftAndMpeoeAndCdeapByTextFormat(theFunctionalGroup);

        theFileWriter.flush();
        theFileWriter.write(theFileString);
        theFileWriter.close();
    }

    private String __getChemicalShiftAndMpeoeAndCdeapByTextFormat(String theFunctionalGroup) {
        StringBuilder theStringBuilder = new StringBuilder();

        theStringBuilder.append(this.CHEMICAL_SHIFT_COLUMN_NAME_IN_RESULT_FILE).append(this.CRITERION_DIVIDED_COLUMN_IN_RESULT_FILE).append(this.MPEOE_COLUMN_NAME_IN_RESULT_FILE)
                .append(this.CRITERION_DIVIDED_COLUMN_IN_RESULT_FILE).append(this.CDEAP_COLUMN_NAME_IN_RESULT_FILE).append(this.CRITERION_DIVIDED_COLUMN_IN_RESULT_FILE)
                .append(this.SFE_COULUMN_NAME_IN_RESULT_FILE).append(this.END_LINE);

        for (int li = 0, lEnd = this.getHydrogenPeakListByMolecules().size(); li < lEnd; li++) {
            theStringBuilder.append(this.__getChemicalShiftAndMpeoeAndCdeapByTextFormat(this.getHydrogenPeakListByMolecules().get(li), li, theFunctionalGroup));
        }

        return theStringBuilder.toString();
    }

    private String __getChemicalShiftAndMpeoeAndCdeapByTextFormat(List<Nmr1dUnitList> thePeak2dList, int theMoleculeIndex, String theFunctionalGroup) {
        StringBuilder theStringBuilder = new StringBuilder();

        for (Nmr1dUnitList thePeakList : thePeak2dList) {
            theStringBuilder.append(this.__getChemicalShiftAndMpeoeAndCdeapByTextFormat(thePeakList, theMoleculeIndex, theFunctionalGroup));
        }

        return theStringBuilder.toString();
    }

    private String __getChemicalShiftAndMpeoeAndCdeapByTextFormat(Nmr1dUnitList thePeakList, int theMoleculeIndex, String theFunctionalGroup) {
        StringBuilder theStringBuilder = new StringBuilder();
        List<String> theFunctionalGroupList = this.__getFunctionalGroupList(this.getMoleculeSet().getMolecule(theMoleculeIndex));
        List<Integer> theUsedAtomicNumberList = new ArrayList<>();

        for (Nmr1dUnit thePeak : thePeakList.getPeakList()) {
            if (thePeak.getAnnotatedAtomNumber() != this.INCORRECT_ATOM_NUMBER && !theUsedAtomicNumberList.contains(thePeak.getAnnotatedAtomNumber())) {
                int theFunctionalGroupIndex = this.__getFunctionalGroupIndex(thePeak.getAnnotatedAtomNumber(), theMoleculeIndex);

                try {
                    if (theFunctionalGroupList.get(theFunctionalGroupIndex).contains(theFunctionalGroup)) {
                        theStringBuilder.append(this.__getChemicalShiftAndMpeoeAndCdeapByTextFormat(thePeak, theMoleculeIndex));
                        theUsedAtomicNumberList.add(thePeak.getAnnotatedAtomNumber());
                    }
                } catch (IndexOutOfBoundsException e) {
                    e.printStackTrace();
                    System.err.println(this.getMoleculeSet().getMolecule(theMoleculeIndex).getProperty("nmrshiftdb2 ID").toString());
                }
            }
        }

        return theStringBuilder.toString();
    }

    private int __getFunctionalGroupIndex(int theAnnotatedAtomNumber, int theMoleculeIndex) {
        int theFunctionalGroupIndex = theAnnotatedAtomNumber;

        try {
            if (this.getMoleculeSet().getMolecule(theMoleculeIndex).getAtom(theAnnotatedAtomNumber).getAtomicNumber() == this.H_ATOMIC_NUMBER) {
                List<IAtom> theConnectedAtomNumberList = this.getMoleculeSet().getMolecule(theMoleculeIndex)
                        .getConnectedAtomsList(this.getMoleculeSet().getMolecule(theMoleculeIndex).getAtom(theAnnotatedAtomNumber));

                theFunctionalGroupIndex = this.getMoleculeSet().getMolecule(theMoleculeIndex)
                        .getAtomNumber(theConnectedAtomNumberList.get(this.CONNECTED_ATOM_INDEX_IN_HYDROGEN));
            }
        } catch (ArrayIndexOutOfBoundsException e) {
            e.printStackTrace();
            System.err.println(theAnnotatedAtomNumber);
        }

        return theFunctionalGroupIndex;
    }

    private String __getChemicalShiftAndMpeoeAndCdeapByTextFormat(Nmr1dUnit thePeak, int theMoleculeIndex) {
        StringBuilder theStringBuilder = new StringBuilder();
        int theAnnotatedAtomNumber = thePeak.getAnnotatedAtomNumber();

        if (this.getMPEOEListByMolecules().get(theMoleculeIndex).get(theAnnotatedAtomNumber) != this.NOT_CONTAIN_PROPERTY_VALUE
                && !this.getSFEList().get(theMoleculeIndex).equals(this.INCORRECT_VALUE)) {
            theStringBuilder.append(thePeak.getChemicalShift()).append(this.CRITERION_DIVIDED_COLUMN_IN_RESULT_FILE)
                    .append(this.getMPEOEListByMolecules().get(theMoleculeIndex).get(theAnnotatedAtomNumber)).append(this.CRITERION_DIVIDED_COLUMN_IN_RESULT_FILE)
                    .append(this.getCDEAPListByMolecules().get(theMoleculeIndex).get(theAnnotatedAtomNumber)).append(this.CRITERION_DIVIDED_COLUMN_IN_RESULT_FILE)
                    .append(this.getSFEList().get(theMoleculeIndex)).append(this.END_LINE);
        }

        return theStringBuilder.toString();
    }

    private List<String> __getFunctionalGroupList(IMolecule theMolecule) {
        List<String> theFunctionalGroupList = new ArrayList<>();
        String theFunctionalGroupValue = theMolecule.getProperty(this.FUNCTIONAL_GROUP_KEY).toString();
        String[] theFunctionalGroupArray = theFunctionalGroupValue.split(this.CRITERION_DIVIDED_FUNCTIONAL_GROUP);

        Collections.addAll(theFunctionalGroupList, theFunctionalGroupArray);

        return theFunctionalGroupList;
    }

    public void writeChemicalShiftAndMpeoeEAndCdeapBySolvent(String theResultDir, String theFunctionalGroup) throws IOException {
        NMRShiftDBSolventList theSolventList = new NMRShiftDBSolventList();

        for (String theSolvent : theSolventList.getSolventList()) {
            this.__writeChemicalShiftAndMpeoeEAndCdeapBySolvent(theResultDir, theFunctionalGroup, theSolvent);
        }
    }

    private void __writeChemicalShiftAndMpeoeEAndCdeapBySolvent(String theResultDir, String theFunctionalGroup, String theSolvent) throws IOException {
        StringBuilder theResultFilePath = new StringBuilder();
        String theFileString = this.__getFileStringBySolvent(theFunctionalGroup, theSolvent);

        theResultFilePath.append(theResultDir);

        if (!theResultDir.substring(theResultDir.length() - 1).equals("\\")) {
            theResultFilePath.append(this.BACK_SLASH_STRING);
        }

        theResultFilePath.append(theFunctionalGroup).append(this.UNDER_BAR).append(theSolvent).append(this.TEXT_SUFFIX);
        BufferedWriter theFileWriter = new BufferedWriter(new FileWriter(theResultFilePath.toString()));

        theFileWriter.flush();
        theFileWriter.write(theFileString);
        theFileWriter.close();
    }

    private String __getFileStringBySolvent(String theFunctionalGroup, String theSolvent) {
        StringBuilder theStringBuilder = new StringBuilder();
        int theMoleculeIndex = 0;

        theStringBuilder.append(this.CHEMICAL_SHIFT_COLUMN_NAME_IN_RESULT_FILE).append(this.CRITERION_DIVIDED_COLUMN_IN_RESULT_FILE).append(this.MPEOE_COLUMN_NAME_IN_RESULT_FILE)
                .append(this.CRITERION_DIVIDED_COLUMN_IN_RESULT_FILE).append(this.CDEAP_COLUMN_NAME_IN_RESULT_FILE).append(this.CRITERION_DIVIDED_COLUMN_IN_RESULT_FILE)
                .append(this.SFE_COULUMN_NAME_IN_RESULT_FILE).append(this.END_LINE);

        for (List<Nmr1dUnitList> thePeak2dList : this.getHydrogenPeakListByMolecules().get2dList()) {
            theStringBuilder.append(this.__getFileStringBySolvent(thePeak2dList, theMoleculeIndex, theFunctionalGroup, theSolvent));
            theMoleculeIndex++;
        }

        return theStringBuilder.toString();
    }

    private String __getFileStringBySolvent(List<Nmr1dUnitList> thePeak2dListByMolecule, int theMoleculeIndex, String theFunctionalGroup, String theSolvent) {
        StringBuilder theStringBuilder = new StringBuilder();

        for (Nmr1dUnitList thePeakList : thePeak2dListByMolecule) {
            theStringBuilder.append(this.__getFileStringBySolvent(thePeakList, theMoleculeIndex, theFunctionalGroup, theSolvent));
        }

        return theStringBuilder.toString();
    }

    private String __getFileStringBySolvent(Nmr1dUnitList thePeakListByMoleucle, int theMoleculeIndex, String theFunctionalGroup, String theSolvent) {
        StringBuilder theStringBuilder = new StringBuilder();
        NMRShiftDBSolventList theSolventList = new NMRShiftDBSolventList();
        List<String> theFunctionalGroupList = this.__getFunctionalGroupList(this.getMoleculeSet().getMolecule(theMoleculeIndex));
        List<Integer> theUsedAtomNumberList = new ArrayList<>();

        try {
            for (Nmr1dUnit thePeak : thePeakListByMoleucle.getPeakList()) {
                if (thePeak.getAnnotatedAtomNumber() != this.INCORRECT_ATOM_NUMBER && !theUsedAtomNumberList.contains(thePeak.getAnnotatedAtomNumber())) {
                    int theFunctionalGroupIndex = this.__getFunctionalGroupIndex(thePeak.getAnnotatedAtomNumber(), theMoleculeIndex);

                    if (theFunctionalGroupList.get(theFunctionalGroupIndex).equals(theFunctionalGroup)
                            && theSolventList.modifyStandardSolventName(thePeak.getSolvent()).equals(theSolvent)) {
                        theStringBuilder.append(this.__getChemicalShiftAndMpeoeAndCdeapByTextFormat(thePeak, theMoleculeIndex));
                        theUsedAtomNumberList.add(thePeak.getAnnotatedAtomNumber());
                    }
                }
            }
        } catch (IndexOutOfBoundsException e) {
            e.printStackTrace();
            System.err.println(theFunctionalGroupList.size());
            System.err.println(this.getMoleculeSet().getMolecule(theMoleculeIndex).getAtomCount());
            System.err.println(theMoleculeIndex);
        }

        return theStringBuilder.toString();
    }

    public IMoleculeSet findMatchedMoleculeForChemicalShiftAndMpeoeAndCdeap(double theChemicalShift, double theMpeoe, double theCdeap) {
        IMoleculeSet theMoleculeSet = new MoleculeSet();

        for (int mi = 0, mEnd = this.getMoleculeSet().getMoleculeCount(); mi < mEnd; mi++) {
            if (this.__isMatchedMoleculeForChemicalShiftAndMpeoeAndCdeap(mi, theChemicalShift, theMpeoe, theCdeap)) {
                theMoleculeSet.addMolecule(this.getMoleculeSet().getMolecule(mi));
            }
        }

        return theMoleculeSet;
    }

    private boolean __isMatchedMoleculeForChemicalShiftAndMpeoeAndCdeap(int theMoleculeIndex, double theChemicalShift, double theMpeoe, double theCdeap) {
        List<String> theFunctionalGroupList = this.__getFunctionalGroupList(this.getMoleculeSet().getMolecule(theMoleculeIndex));

        for (Nmr1dUnitList thePeakList : this.getHydrogenPeakListByMolecules().get(theMoleculeIndex)) {
            for (Nmr1dUnit thePeak : thePeakList.getPeakList()) {
                if (thePeak.getAnnotatedAtomNumber() != this.INCORRECT_ATOM_NUMBER) {
                    int theAnnotatedAtomNumber = thePeak.getAnnotatedAtomNumber();

                    if (thePeak.getChemicalShift().equals(theChemicalShift) && this.getMPEOEListByMolecules().get(theMoleculeIndex).get(theAnnotatedAtomNumber).equals(theMpeoe)
                            && this.getCDEAPListByMolecules().get(theMoleculeIndex).get(theAnnotatedAtomNumber).equals(theCdeap)) {
                        System.out.println(this.getMoleculeSet().getMolecule(theMoleculeIndex).getProperty(this.NMRSHIFT_ID_KEY).toString() + this.SPACE_STRING + theAnnotatedAtomNumber);
                        return true;
                    }
                }
            }
        }

        return false;
    }

    public void writeHoseCodeDescriptor(String theResultFilePath, int theHoseCodeLevel, String theFunctionalGroup) throws IOException {
        BufferedWriter theFileWriter = new BufferedWriter(new FileWriter(theResultFilePath));
        String theFileString = this.__getHoseCodeDescriptor(theHoseCodeLevel, theFunctionalGroup);

        theFileWriter.flush();
        theFileWriter.write(theFileString);
        theFileWriter.close();
    }

    private String __getHoseCodeDescriptor(int theHoseCodeLevel, String theFunctionalGroup) {
        StringBuilder theStringBuilder = new StringBuilder();
        HoseCodeDescriptor theHoseCodeDescriptor = new HoseCodeDescriptor();

        theStringBuilder.append(this.CHEMICAL_SHIFT_COLUMN_NAME_IN_RESULT_FILE).append(this.CRITERION_DIVIDED_COLUMN_IN_RESULT_FILE).append(this.MPEOE_COLUMN_NAME_IN_RESULT_FILE)
                .append(this.CRITERION_DIVIDED_COLUMN_IN_RESULT_FILE).append(this.CDEAP_COLUMN_NAME_IN_RESULT_FILE).append(this.CRITERION_DIVIDED_COLUMN_IN_RESULT_FILE)
                .append(this.SFE_COULUMN_NAME_IN_RESULT_FILE).append(this.CRITERION_DIVIDED_COLUMN_IN_RESULT_FILE)
                .append(this.__getHoseCodeDescriptorName(theHoseCodeDescriptor.generateHoseCodeNameList(theHoseCodeLevel))).append(this.END_LINE);

        for (int mi = 0, mEnd = this.getHydrogenPeakListByMolecules().size(); mi < mEnd; mi++) {
            for (Nmr1dUnitList thePeakList : this.getHydrogenPeakListByMolecules().get(mi)) {
                theStringBuilder.append(this.__getHoseCodeDescriptor(thePeakList, theHoseCodeLevel, theFunctionalGroup, mi));
            }
        }

        return theStringBuilder.toString();
    }

    private String __getHoseCodeDescriptor(Nmr1dUnitList thePeakList, int theHoseCodeLevel, String theFunctionalGroup, int theMoleculeIndex) {
        StringBuilder theStringBuilder = new StringBuilder();
        List<String> theFunctionalGroupList = this.__getFunctionalGroupList(this.getMoleculeSet().getMolecule(theMoleculeIndex));
        NMRShiftDBSolventList theSolventList = new NMRShiftDBSolventList();
        List<Integer> theUsedAtomNumberList = new ArrayList<Integer>();

        for (Nmr1dUnit thePeak : thePeakList.getPeakList()) {
            if (thePeak.getAnnotatedAtomNumber() != this.INCORRECT_ATOM_NUMBER && !theUsedAtomNumberList.contains(thePeak.getAnnotatedAtomNumber())) {
                int theFunctionalGroupIndex = this.__getFunctionalGroupIndex(thePeak.getAnnotatedAtomNumber(), theMoleculeIndex);

                if (theFunctionalGroupList.get(theFunctionalGroupIndex).equals(theFunctionalGroup)) {
                    theStringBuilder.append(this.__getHoseCodeDescriptor(thePeak, theHoseCodeLevel, theMoleculeIndex));
                    theUsedAtomNumberList.add(thePeak.getAnnotatedAtomNumber());
                }

            }
        }

        return theStringBuilder.toString();
    }

    private String __getHoseCodeDescriptor(Nmr1dUnit thePeak, int theHoseCodeLevel, int theMoleculeIndex) {
        StringBuilder theStringBuilder = new StringBuilder();
        int theAnnotatedAtomNumber = thePeak.getAnnotatedAtomNumber();
        HoseCodeDescriptor theHoseCodeDescriptor = new HoseCodeDescriptor(this.getMoleculeSet().getMolecule(theMoleculeIndex), theHoseCodeLevel);

        if (this.getMPEOEListByMolecules().get(theMoleculeIndex).get(theAnnotatedAtomNumber) != this.NOT_CONTAIN_PROPERTY_VALUE
                && !this.getSFEList().get(theMoleculeIndex).equals(this.INCORRECT_VALUE)) {
            theStringBuilder.append(thePeak.getChemicalShift()).append(this.CRITERION_DIVIDED_COLUMN_IN_RESULT_FILE)
                    .append(this.getMPEOEListByMolecules().get(theMoleculeIndex).get(theAnnotatedAtomNumber)).append(this.CRITERION_DIVIDED_COLUMN_IN_RESULT_FILE)
                    .append(this.getCDEAPListByMolecules().get(theMoleculeIndex).get(theAnnotatedAtomNumber)).append(this.CRITERION_DIVIDED_COLUMN_IN_RESULT_FILE)
                    .append(this.getSFEList().get(theMoleculeIndex)).append(this.CRITERION_DIVIDED_COLUMN_IN_RESULT_FILE)
                    .append(this.__getHoseCodeDescriptor(theHoseCodeDescriptor.getHoseCodeValue2dList().get(theAnnotatedAtomNumber))).append(this.END_LINE);
        }

        return theStringBuilder.toString();
    }

    private String __getHoseCodeDescriptorName(List<String> theDescriptorNameList) {
        StringBuilder theStringBuilder = new StringBuilder();

        for (int ni = 0, nEnd = theDescriptorNameList.size(); ni < nEnd; ni++) {
            theStringBuilder.append(theDescriptorNameList.get(ni));

            if (ni < nEnd - 1) {
                theStringBuilder.append(this.CRITERION_DIVIDED_COLUMN_IN_RESULT_FILE);
            }
        }

        return theStringBuilder.toString();
    }

    private String __getHoseCodeDescriptor(List<Integer> theDescriptorValueList) {
        StringBuilder theStringBuilder = new StringBuilder();

        for (int vi = 0, vEnd = theDescriptorValueList.size(); vi < vEnd; vi++) {
            theStringBuilder.append(theDescriptorValueList.get(vi));

            if (vi < vEnd - 1) {
                theStringBuilder.append(this.CRITERION_DIVIDED_COLUMN_IN_RESULT_FILE);
            }
        }

        return theStringBuilder.toString();
    }

    public void writeHoseCodeDescriptorBySolvent(String theResultDir, int theHoseCodeLevel, String theFunctionalGroup) throws IOException {
        NMRShiftDBSolventList theSolventList = new NMRShiftDBSolventList();

        for (String theSolvent : theSolventList.getSolventList()) {
            this.__writeHoseCodeDescriptorBySolvent(theResultDir, theFunctionalGroup, theSolvent, theHoseCodeLevel);
        }
    }

    private void __writeHoseCodeDescriptorBySolvent(String theResultDir, String theFunctionalGroup, String theSolvent, int theHoseCodeLevel) throws IOException {
        StringBuilder theResultFilePath = new StringBuilder();
        String theFileString = this.__writeHoseCodeDescriptorBySolvent(theFunctionalGroup, theSolvent, theHoseCodeLevel);

        theResultFilePath.append(theResultDir);

        if (!theResultDir.substring(theResultDir.length() - 1).equals("\\")) {
            theResultFilePath.append(this.BACK_SLASH_STRING);
        }

        theResultFilePath.append(theFunctionalGroup).append(this.UNDER_BAR).append(theSolvent).append(this.TEXT_SUFFIX);
        BufferedWriter theFileWriter = new BufferedWriter(new FileWriter(theResultFilePath.toString()));

        theFileWriter.flush();
        theFileWriter.write(theFileString);
        theFileWriter.close();
    }

    private String __writeHoseCodeDescriptorBySolvent(String theFunctionalGroup, String theSolvent, int theHoseCodeLevel) {
        StringBuilder theStringBuilder = new StringBuilder();
        HoseCodeDescriptor theHoseCodeDescriptor = new HoseCodeDescriptor();

        theStringBuilder.append(this.CHEMICAL_SHIFT_COLUMN_NAME_IN_RESULT_FILE).append(this.CRITERION_DIVIDED_COLUMN_IN_RESULT_FILE).append(this.MPEOE_COLUMN_NAME_IN_RESULT_FILE)
                .append(this.CRITERION_DIVIDED_COLUMN_IN_RESULT_FILE).append(this.CDEAP_COLUMN_NAME_IN_RESULT_FILE).append(this.CRITERION_DIVIDED_COLUMN_IN_RESULT_FILE)
                .append(this.SFE_COULUMN_NAME_IN_RESULT_FILE).append(this.CRITERION_DIVIDED_COLUMN_IN_RESULT_FILE)
                .append(this.__getHoseCodeDescriptorName(theHoseCodeDescriptor.generateHoseCodeNameList(theHoseCodeLevel))).append(this.END_LINE);

        for (int mi = 0, mEnd = this.getHydrogenPeakListByMolecules().size(); mi < mEnd; mi++) {
            for (Nmr1dUnitList thePeakList : this.getHydrogenPeakListByMolecules().get(mi)) {
                theStringBuilder.append(this.__getHoseCodeDescriptorBySolvent(thePeakList, theHoseCodeLevel, theFunctionalGroup, mi, theSolvent));
            }
        }

        return theStringBuilder.toString();
    }

    private String __getHoseCodeDescriptorBySolvent(Nmr1dUnitList thePeakList, int theHoseCodeLevel, String theFunctionalGroup, int theMoleculeIndex, String theSolvent) {
        StringBuilder theStringBuilder = new StringBuilder();
        List<String> theFunctionalGroupList = this.__getFunctionalGroupList(this.getMoleculeSet().getMolecule(theMoleculeIndex));
        NMRShiftDBSolventList theSolventList = new NMRShiftDBSolventList();
        List<Integer> theUsedAtomNumberList = new ArrayList<Integer>();

        for (Nmr1dUnit thePeak : thePeakList.getPeakList()) {
            if (!theSolvent.equals(theSolventList.modifyStandardSolventName(thePeak.getSolvent()))) {
                return new String();
            } else if (thePeak.getAnnotatedAtomNumber() != this.INCORRECT_ATOM_NUMBER && !theUsedAtomNumberList.contains(thePeak.getAnnotatedAtomNumber())) {
                int theFunctionalGroupIndex = this.__getFunctionalGroupIndex(thePeak.getAnnotatedAtomNumber(), theMoleculeIndex);

                if (theFunctionalGroupList.get(theFunctionalGroupIndex).equals(theFunctionalGroup)) {
                    theStringBuilder.append(this.__getHoseCodeDescriptor(thePeak, theHoseCodeLevel, theMoleculeIndex));
                    theUsedAtomNumberList.add(thePeak.getAnnotatedAtomNumber());
                }
            }
        }

        return theStringBuilder.toString();
    }

    public void writeHoseCodeDescriptorBySolventForSDFormat(String theResultDir, int theHoseCodeLevel, String theFunctionalGroup) throws IOException {
        NMRShiftDBSolventList theSolventList = new NMRShiftDBSolventList();

        for (String theSolvent : theSolventList.getSolventList()) {
            this.__writeHoseCodeDescriptorBySolventForSDFormat(theResultDir, theFunctionalGroup, theSolvent, theHoseCodeLevel);
        }
    }

    private void __writeHoseCodeDescriptorBySolventForSDFormat(String theResultDir, String theFunctionalGroup, String theSolvent, int theHoseCodeLevel) throws IOException {
        StringBuilder theResultFilePath = new StringBuilder();
        IMoleculeSet theMoleculeSet = this.__getHoseCodeDescriptorBySolventForSDForamt(theFunctionalGroup, theSolvent, theHoseCodeLevel);

        theResultFilePath.append(theResultDir);

        if (!theResultDir.substring(theResultDir.length() - 1).equals("\\")) {
            theResultFilePath.append(this.BACK_SLASH_STRING);
        }

        theResultFilePath.append(theFunctionalGroup).append(this.UNDER_BAR).append(theSolvent).append(this.MOLECULE_SUFFIX);
        SDFWriter.writeSDFile(theMoleculeSet, new File(theResultFilePath.toString()));
    }

    private IMoleculeSet __getHoseCodeDescriptorBySolventForSDForamt(String theFunctionalGroup, String theSolvent, int theHoseCodeLevel) {
        IMoleculeSet theMoleculeSet = new MoleculeSet();

        for (int mi = 0, mEnd = this.getHydrogenPeakListByMolecules().size(); mi < mEnd; mi++) {
            if (this.__calculateMolecularWeight(this.getMoleculeSet().getMolecule(mi)) > 200) {
                for (Nmr1dUnitList thePeakList : this.getHydrogenPeakListByMolecules().get(mi)) {
                    theMoleculeSet.add(this.__getHoseCodeDescriptorBySolventForSDForamt(thePeakList, theHoseCodeLevel, theFunctionalGroup, mi, theSolvent));
                }
            }
        }

        return theMoleculeSet;
    }

    private IMoleculeSet __getHoseCodeDescriptorBySolventForSDForamt(Nmr1dUnitList thePeakList, int theHoseCodeLevel, String theFunctionalGroup, int theMoleculeIndex, String theSolvent) {
        IMoleculeSet theMoleculeSet = new MoleculeSet();
        List<String> theFunctionalGroupList = this.__getFunctionalGroupList(this.getMoleculeSet().getMolecule(theMoleculeIndex));
        NMRShiftDBSolventList theSolventList = new NMRShiftDBSolventList();
        List<Integer> theUsedAtomNumberList = new ArrayList<Integer>();

        for (Nmr1dUnit thePeak : thePeakList.getPeakList()) {
            if (!theSolvent.equals(theSolventList.modifyStandardSolventName(thePeak.getSolvent()))) {
                return new MoleculeSet();
            } else if (thePeak.getAnnotatedAtomNumber() != this.INCORRECT_ATOM_NUMBER && !theUsedAtomNumberList.contains(thePeak.getAnnotatedAtomNumber())) {
                int theFunctionalGroupIndex = this.__getFunctionalGroupIndex(thePeak.getAnnotatedAtomNumber(), theMoleculeIndex);

                if (theFunctionalGroupList.get(theFunctionalGroupIndex).equals(theFunctionalGroup)) {
                    theMoleculeSet.addMolecule(this.__getHoseCodeDescriptorBySDFormat(thePeak, theHoseCodeLevel, theMoleculeIndex));
                    theUsedAtomNumberList.add(thePeak.getAnnotatedAtomNumber());
                }
            }
        }

        return theMoleculeSet;
    }

    private IMolecule __getHoseCodeDescriptorBySDFormat(Nmr1dUnit thePeak, int theHoseCodeLevel, int theMoleculeIndex) {
        IMolecule theMolecule = new Molecule();
        int theAnnotatedAtomNumber = thePeak.getAnnotatedAtomNumber();

        if (this.getMPEOEListByMolecules().get(theMoleculeIndex).get(theAnnotatedAtomNumber) != this.NOT_CONTAIN_PROPERTY_VALUE
                && !this.getSFEList().get(theMoleculeIndex).equals(this.INCORRECT_VALUE)) {
            HoseCodeDescriptor theHoseCodeDescriptor = new HoseCodeDescriptor(this.getMoleculeSet().getMolecule(theMoleculeIndex), theHoseCodeLevel);

            theMolecule.setProperty(this.CHEMICAL_SHIFT_COLUMN_NAME_IN_RESULT_FILE, thePeak.getChemicalShift());
            theMolecule.setProperty(this.MPEOE_COLUMN_NAME_IN_RESULT_FILE, this.getMPEOEListByMolecules().get(theMoleculeIndex).get(theAnnotatedAtomNumber));
            theMolecule.setProperty(this.CDEAP_COLUMN_NAME_IN_RESULT_FILE, this.getCDEAPListByMolecules().get(theMoleculeIndex).get(theAnnotatedAtomNumber));
            theMolecule.setProperty(this.SFE_COULUMN_NAME_IN_RESULT_FILE, this.getSFEList().get(theMoleculeIndex));
            theMolecule.setProperties(this.__getHoseCodeDescriptorBySDFormat(this.getMoleculeSet().getMolecule(theMoleculeIndex), theAnnotatedAtomNumber, theHoseCodeLevel));
        }

        return theMolecule;
    }

    private Map<Object, Object> __getHoseCodeDescriptorBySDFormat(IMolecule theMolecule, int theAnnotatedAtomNumber, int theHoseCodeLevel) {
        HoseCodeDescriptor theHoseCodeDescriptor = new HoseCodeDescriptor(theMolecule, theHoseCodeLevel);
        IMolecule theDataMolecule = new Molecule();

        for (int pi = 0, pEnd = theHoseCodeDescriptor.getHoseCodeNameList().size(); pi < pEnd; pi++) {
            theDataMolecule.setProperty(theHoseCodeDescriptor.getHoseCodeNameList().get(pi), theHoseCodeDescriptor.getHoseCodeValue2dList().get(theAnnotatedAtomNumber).get(pi));
        }

        return theDataMolecule.getProperties();
    }

    private double __calculateMolecularWeight(IMolecule theMolecule) {
        double theMolecularWeight = 0;

        for (int ai = 0, aEnd = theMolecule.getAtomCount(); ai < aEnd; ai++) {
            theMolecularWeight += theMolecule.getAtom(ai).getExactMass();
        }

        return theMolecularWeight;
    }
}
