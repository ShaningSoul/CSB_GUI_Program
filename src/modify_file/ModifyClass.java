/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package modify_file;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import org.bmdrc.mass.tool.PeakMatcher;
import org.bmdrc.util.Module;
import org.bmdrc.util.SDFWriter;
import org.openscience.cdk.DefaultChemObjectBuilder;
import org.openscience.cdk.MoleculeSet;
import org.openscience.cdk.atomtype.CDKAtomTypeMatcher;
import org.openscience.cdk.exception.CDKException;
import org.openscience.cdk.interfaces.IAtom;
import org.openscience.cdk.interfaces.IAtomContainer;
import org.openscience.cdk.interfaces.IAtomType;
import org.openscience.cdk.interfaces.IMolecularFormula;
import org.openscience.cdk.interfaces.IMolecule;
import org.openscience.cdk.interfaces.IMoleculeSet;
import org.openscience.cdk.io.iterator.IteratingMDLReader;
import org.openscience.cdk.tools.CDKHydrogenAdder;
import org.openscience.cdk.tools.manipulator.AtomContainerManipulator;
import org.openscience.cdk.tools.manipulator.AtomTypeManipulator;
import org.openscience.cdk.tools.manipulator.MolecularFormulaManipulator;
import uk.ac.ebi.jmzml.model.mzml.BinaryDataArray;
import uk.ac.ebi.jmzml.model.mzml.CV;
import uk.ac.ebi.jmzml.model.mzml.CVList;
import uk.ac.ebi.jmzml.model.mzml.MzML;
import uk.ac.ebi.jmzml.model.mzml.Spectrum;
import uk.ac.ebi.jmzml.xml.io.MzMLObjectIterator;
import uk.ac.ebi.jmzml.xml.io.MzMLUnmarshaller;

/**
 *
 * @author labwindows
 */
public class ModifyClass {

    public void calculation(String[] args) throws FileNotFoundException, IOException, CDKException {

        /*
         do it in this way.
         */
        String theFGDBDir = "database";
        File[] theKEGGMoleculeFolder = new File(theFGDBDir + "\\kegg").listFiles();
        File[] theKTKPMoleculeFolder = new File(theFGDBDir + "\\ktkp").listFiles();
        File[] theHERBMoleculeFolder = new File(theFGDBDir + "\\herb").listFiles();
        File theGinsengMoleculeFolder = new File(theFGDBDir + "\\ginseng");
        File theLamMoleculeFolder = new File("E:\\FG\\REAXYS\\2");
        File theScutellariaMoleculeFolder = new File("E:\\FG\\REAXYS\\8");
        File theOphiopogonMoleculeFolder = new File("E:\\FG\\REAXYS\\11");
        File thePoriaMoleculeFolder = new File("E:\\FG\\REAXYS\\15");
        
        
        ArrayList<File[]> theFileLists = new ArrayList<>();
        
        theFileLists.add(theHERBMoleculeFolder);
        theFileLists.add(theKTKPMoleculeFolder);
        theFileLists.add(theKEGGMoleculeFolder);
        
        File theBSInformationFile = new File(theFGDBDir + "\\BS.txt");
        File theFileInformationFile = new File(theFGDBDir + "\\FileList.txt");
        HashMap<String, ArrayList<String>> thePKLFileList = ModifyClass.readFileListcsv(theFileInformationFile);
        
        File theInputMoleculeFile = new File("database\\herb\\HerbDB_Outputfile.sdf");
        File theAdductFile = new File("database\\Adductpeak.txt");
        File thePeakListFile = new File("database\\pkl\\pkl3.csv");
        File outputFile = new File("database\\output\\keggResultFor33.sdf");
        String outputDir = "database\\output\\keggResult\\";
        String pklDir = "E:\\FG\\FTICRDATA\\140815";
        ArrayList<String> theBSPropertyNames = new ArrayList<>();
        theBSPropertyNames.add("SciName");
        theBSPropertyNames.add("Biological_Source");

        IMoleculeSet theResultMoleculeSet = new MoleculeSet();
        double theTolerance = 0.0005;
        IMoleculeSet inputMolSet = new MoleculeSet();
        
        inputMolSet.add(ModifyClass.readFolderWithTag(theGinsengMoleculeFolder, "SciName", "Panax ginseng"));
        inputMolSet.add(ModifyClass.readFolderWithTag(theLamMoleculeFolder, "SciName", "Laminaria japonica"));
        inputMolSet.add(ModifyClass.readFolderWithTag(theScutellariaMoleculeFolder, "SciName", "Scutellaria baicalensis"));
        inputMolSet.add(ModifyClass.readFolderWithTag(thePoriaMoleculeFolder, "SciName", "Poria cocos"));
        inputMolSet.add(ModifyClass.readFolderWithTag(theOphiopogonMoleculeFolder, "SciName", "Ophiopogon japonicus"));
        
        
        
        
        for (File[] theInputMoleculeFolder : theFileLists) {
            String theDBSourceText = theInputMoleculeFolder[0].getParent().split("\\\\")[3];
            for (File inputSDF : theInputMoleculeFolder) {
                try {
                    IteratingMDLReader reader = new IteratingMDLReader(new FileInputStream(inputSDF), DefaultChemObjectBuilder.getInstance());
                    while (reader.hasNext()) {
                        inputMolSet.addMolecule((IMolecule) reader.next());
                        //inputMolSet.getAtomContainer(inputMolSet.getMoleculeCount()).setProperty("DBSourceText", theDBSourceText);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        
        
        /*
         try {
         IteratingMDLReader reader = new IteratingMDLReader(new FileInputStream(theInputMoleculeFile), DefaultChemObjectBuilder.getInstance());
         while (reader.hasNext()) {
         inputMolSet.addMolecule((IMolecule) reader.next());
         }
         } catch (Exception e) {
         e.printStackTrace();
         }
        

         String[] drg = {"Platycodon grandiflorum"};
         String[] sbc = {"Saururus chinensis", "Houttuynia cordata"};
         String[] hso = {"Polygonum multiflorum"};
         */
        //Main.calculateExactMass(inputMolSet, "IDE.MF");
        Module.addHydrogen(inputMolSet, "cdkErrors.txt");
        ModifyClass.setMF(inputMolSet);
        ModifyClass.calculateExactMass(inputMolSet, "Molecular_Formula");
        //Main.calculateExactMass(inputMolSet, "Formula");
        //inputMolSet = Main.getMoleculesWithMuchInfo(inputMolSet);
        //inputMolSet = Main.filterMoleculeByBiologicalSource(inputMolSet, "SciName", drg);
        System.out.println("found " + inputMolSet.getMoleculeCount());
        //inputMolSet = Main.filterMoleculeByBiologicalSource(inputMolSet, "Biological_Source", sbc);
        /*
        for (String[] theBSText : Main.readBScsv(theBSInformationFile)){
            ArrayList<String> theFilterString = new ArrayList<>();
            for (int i = 2; i<theBSText.length; i++){
                theFilterString.add(theBSText[i]);
            }
            theResultMoleculeSet = Main.filterMoleculeByBiologicalSource(inputMolSet, theBSPropertyNames, theFilterString);
            File resFile = new File(theFGDBDir + "\\speciesInfoFiles\\" + theBSText[0] + ".sdf");
            SDFWriter.writeSDFile(theResultMoleculeSet, resFile);
            if (thePKLFileList.get(theBSText[0]).size() > 0){
                for (int i = 0; i < thePKLFileList.get(theBSText[0]).size();i++){
                    File thePklFile = new File(pklDir + "\\" + thePKLFileList.get(theBSText[0]).get(i));
                    
                    File theOutFile = new File(theFGDBDir + "\\resultBySpecies\\" + theBSText[0] + "_" + i + "_" + thePklFile.getName() + ".sdf" );
                    PeakMatcher thePeakMatcher = new PeakMatcher((MoleculeSet) theResultMoleculeSet, thePklFile, theAdductFile, theTolerance);
                    theResultMoleculeSet = thePeakMatcher.matchBetweenMoleculeSetAndPeakList();
                    SDFWriter.writeSDFile(theResultMoleculeSet, theOutFile);
                }
            }
            //PeakMatcher thePeakMatcher = new PeakMatcher((MoleculeSet) theResultMoleculeSet, thePeakListFile, theAdductFile, theTolerance);
        }
        */
        
        File[] pklFiles = new File(pklDir).listFiles();
        int count = 0;
        for (File thePklFile: pklFiles){
            count++;
            System.out.println("Screening file: " + thePklFile.getName());
            PeakMatcher thePeakMatcher = new PeakMatcher((MoleculeSet) inputMolSet, thePklFile, theAdductFile, theTolerance);
            theResultMoleculeSet = thePeakMatcher.matchBetweenMoleculeSetAndPeakList();
            File theOutFile = new File(theFGDBDir + "\\result_All\\" + thePklFile.getName() + ".sdf" );
            SDFWriter.writeSDFile(theResultMoleculeSet, theOutFile);
        }
        
        //PeakMatcher thePeakMatcher = new PeakMatcher((MoleculeSet) inputMolSet, thePeakListFile, theAdductFile, theTolerance);
        //theResultMoleculeSet = thePeakMatcher.matchBetweenMoleculeSetAndPeakList();

        /*
         for (int i = 0; i < 30; i++) {
         IMoleculeSet tempSet = Main.filterMoleculeByDoubleProperty(theResultMoleculeSet, "peakIntensity", ((Integer) i).doubleValue());
         SDFWriter.writeSDFile(tempSet, new File(outputDir + ((Integer) i).toString() + ".sdf"));
         }*/
        //SDFWriter.writeSDFile(theResultMoleculeSet, outputFile);
    }
    public static void addHydrogen(IMoleculeSet molSet) throws IOException {
        File theCdkErrors = new File("cdkErrors.txt");
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

    public static void setMF(IMoleculeSet molSet) {
        for (IAtomContainer theMolecule : molSet.molecules()) {
            theMolecule.setProperty("Molecular_Formula", MolecularFormulaManipulator.getString(MolecularFormulaManipulator.getMolecularFormula(theMolecule)));
        }
    }
    public static IMoleculeSet readFolderWithTag(File folder, String tagPropertyName, String tag){
        IMoleculeSet resultMol = new MoleculeSet();
        for (File inputSDF : folder.listFiles()) {
                try {
                    IteratingMDLReader reader = new IteratingMDLReader(new FileInputStream(inputSDF), DefaultChemObjectBuilder.getInstance());
                    while (reader.hasNext()) {
                        resultMol.addMolecule((IMolecule) reader.next());
                        //inputMolSet.getAtomContainer(inputMolSet.getMoleculeCount()).setProperty("DBSourceText", theDBSourceText);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        for (int i =0; i<resultMol.getMoleculeCount(); i++){
            resultMol.getMolecule(i).setProperty(tagPropertyName, tag);
        }
        return resultMol;
    }

    public static ArrayList<String[]> readBScsv(File theBScsv) throws FileNotFoundException, IOException {
        ArrayList<String[]> result = new ArrayList<>();
        BufferedReader theReader = new BufferedReader(new FileReader(theBScsv));
        String line = "";
        while ((line = theReader.readLine()) != null) {
            result.add(line.split("\t"));
        }
        return result;
    }
    public static HashMap<String, ArrayList<String>> readFileListcsv(File theFileListcsv) throws FileNotFoundException, IOException{
        HashMap<String, ArrayList<String>> result = new HashMap<String,ArrayList<String>>();
        BufferedReader theReader = new BufferedReader(new FileReader(theFileListcsv));
        String line = "";
        while ((line = theReader.readLine()) != null) {
            ArrayList<String> theStringList = new ArrayList<String>();
            String[] temp = line.split("\t");
            if (temp.length>1){
                for (int i = 2; i<temp.length;i++){
                    theStringList.add(temp[i]);
                }
                result.put(temp[0], theStringList);
            }            
        }
        return result;
    }

    public static void calculateExactMass(IMoleculeSet molSet, String formulaStringPropertyName) {
        double exactMass = 0.0;
        IMolecularFormula molecularFormula;
        //CDKHydrogenAdder ha = CDKHydrogenAdder.getInstance(DefaultChemObjectBuilder.getInstance());
        for (int i = 0; i < molSet.getAtomContainerCount(); i++) {
            if (!(((String) molSet.getMolecule(i).getProperty(formulaStringPropertyName)).isEmpty())) {
                molecularFormula = MolecularFormulaManipulator.getMolecularFormula((String) molSet.getMolecule(i).getProperty(formulaStringPropertyName), DefaultChemObjectBuilder.getInstance());
            } else {
                molecularFormula = null;
            }
            //molecularFormula = MolecularFormulaManipulator.getMolecularFormula(molSet.getMolecule(i));
            try {
                exactMass = MolecularFormulaManipulator.getTotalExactMass(molecularFormula);
            } catch (NullPointerException e) {
                exactMass = 0.0;
                e.printStackTrace();
            }
            //molSet.getAtomContainer(i).setProperty("MolecularFormula", MolecularFormulaManipulator.getString(molecularFormula));
            molSet.getAtomContainer(i).setProperty("exact mass", exactMass);
        }
    }

    public static IMoleculeSet getMoleculesWithMuchInfo(IMoleculeSet molSet) {
        IMoleculeSet resultSet = new MoleculeSet();
        for (IAtomContainer eachMol : molSet.molecules()) {
            if (eachMol.getProperties().keySet().size() > 30) {
                resultSet.addMolecule((IMolecule) eachMol);
            }
        }
        return resultSet;
    }

    public static IMoleculeSet filterMoleculeByBiologicalSource(IMoleculeSet molSet, String BSPropertyName, String[] filterString) {
        IMoleculeSet resultSet = new MoleculeSet();
        for (IAtomContainer eachMol : molSet.molecules()) {
            for (int i = 0; i < filterString.length; i++) {
                if (((String) eachMol.getProperty(BSPropertyName)).toLowerCase().contains(filterString[i].toLowerCase())) {
                    resultSet.addMolecule((IMolecule) eachMol);
                }
            }
        }
        return resultSet;
    }

    public static IMoleculeSet filterMoleculeByBiologicalSource(IMoleculeSet molSet, String BSPropertyName, ArrayList<String> filterString) {
        IMoleculeSet resultSet = new MoleculeSet();
        for (IAtomContainer eachMol : molSet.molecules()) {
            if (eachMol.getProperty(BSPropertyName) != null) {
                for (int i = 0; i < filterString.size(); i++) {
                    if (((String) eachMol.getProperty(BSPropertyName)).toLowerCase().contains(filterString.get(i).toLowerCase())) {
                        resultSet.addMolecule((IMolecule) eachMol);
                    }
                }
            }
        }
        return resultSet;
    }
    public static IMoleculeSet filterMoleculeByBiologicalSource(IMoleculeSet molSet, ArrayList<String> BSPropertyName, ArrayList<String> filterString){
        IMoleculeSet resultSet = new MoleculeSet();
        for (String a : BSPropertyName){
            if (resultSet != null){
                resultSet.add(ModifyClass.filterMoleculeByBiologicalSource(molSet, a, filterString));
            }
        }
        return resultSet;
    }

    public static IMoleculeSet filterMoleculeByStringProperty(IMoleculeSet molSet, String propertyName, String filterString) {
        IMoleculeSet resultSet = new MoleculeSet();
        for (IAtomContainer eachMol : molSet.molecules()) {
            for (int i = 0; i < filterString.length(); i++) {
                if (((String) eachMol.getProperty(propertyName)).toLowerCase().contains(filterString.toLowerCase())) {
                    resultSet.addMolecule((IMolecule) eachMol);
                }
            }
        }
        return resultSet;
    }

    public static IMoleculeSet filterMoleculeByDoubleProperty(IMoleculeSet molSet, String propertyName, Double filterThreshold) {
        IMoleculeSet resultSet = new MoleculeSet();
        for (IAtomContainer eachMol : molSet.molecules()) {
            Boolean isRemaining = false;
            for (String eachString : ((String) eachMol.getProperty(propertyName)).split("\n")) {
                if (Double.parseDouble(eachString) >= filterThreshold) {
                    isRemaining = true;
                }
            }
            if (isRemaining) {
                resultSet.addMolecule((IMolecule) eachMol);
            }
        }
        return resultSet;
    }

    public static IMoleculeSet filterMoleculeByIntegerProperty(IMoleculeSet molSet, String propertyName, Double filterThreshold) {
        return new MoleculeSet();
    }
}

/*
 String fgPath = "E:\\FG\\";
 File mzmlFile = new File(fgPath + "FTICRDATA\\140716\\140715BMDRC_PMs1Nma15TKKH_FG_5_100scan_000002.mzML");
 File outputFile = new File(fgPath +"logFile.txt");
 ArrayList<Double> peakArray = new ArrayList<>();
 ArrayList<Double> intensityArray = new ArrayList<>();
 BufferedWriter out = new BufferedWriter(new FileWriter(outputFile));
 MzMLUnmarshaller unmarshaller = new MzMLUnmarshaller(mzmlFile);
 MzMLObjectIterator<Spectrum> spectrumIterator = unmarshaller.unmarshalCollectionFromXpath("/run/spectrumList/spectrum", Spectrum.class);
        
 while (spectrumIterator.hasNext()) {
 Spectrum spectrum = spectrumIterator.next();
 for (Number a : spectrum.getBinaryDataArrayList().getBinaryDataArray().get(0).getBinaryDataAsNumberArray()){
 peakArray.add(a.doubleValue());
 }
 for (Number a : spectrum.getBinaryDataArrayList().getBinaryDataArray().get(1).getBinaryDataAsNumberArray()){
 intensityArray.add(a.doubleValue());
 }                
 System.out.println("Spectrum ID: " + spectrum.getId());
 }
        
 for (int i = 0; i < peakArray.size(); i++){
 out.write(String.valueOf(peakArray.get(i)) + "\t" + String.valueOf(intensityArray.get(i)) + "\n");
 }
 out.close();
 */
