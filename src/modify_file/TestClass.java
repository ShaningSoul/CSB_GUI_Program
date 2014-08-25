/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modify_file;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.bmdrc.util.SDFReader;
import org.bmdrc.util.SDFWriter;
import org.openscience.cdk.DefaultChemObjectBuilder;
import org.openscience.cdk.MoleculeSet;
import org.openscience.cdk.interfaces.IAtomContainer;
import org.openscience.cdk.interfaces.IMolecule;
import org.openscience.cdk.interfaces.IMoleculeSet;
import org.openscience.cdk.io.iterator.IteratingMDLReader;

/**
 *
 * @author GiBeom, Shin, CSB
 */
public class TestClass {

    public void TestClass(String[] args) throws MalformedURLException, IOException {
        File[] theInputFolder = new File("E:\\FG\\DB\\ppinput2resultDir").listFiles();
        String theOutputDir = "E:\\FG\\DB\\resultBySpecies\\InChIResult\\";
        String activityName = "antioxidant";
        String inchiPropertyName = "InChI";
        UrlParser theUrlParser = new UrlParser();
        
        new File(theOutputDir).mkdir();
        for (File theInputMoleculeFile : theInputFolder) {
            File theOutputMoleculeFile = new File(theOutputDir + theInputMoleculeFile.getName());
            IMoleculeSet inputMolSet = SDFReader.openMoleculeFile(theInputMoleculeFile);

            for (IAtomContainer eachMol : inputMolSet.molecules()) {
                List<String> cidList = theUrlParser.getCIDList("\"" + (String) eachMol.getProperty(inchiPropertyName) + "\"[INCHI]");
                for (String cid : cidList) {
                    StringBuilder propertyString = new StringBuilder();
                    for (String activityString : theUrlParser.getPubchemCSV(cid, new ArrayList<String>(Arrays.asList("active", activityName)))) {
                        if (!activityString.contains("Nohitsfor:")) {
                            propertyString.append("\"" + cid + "\"," + activityString + "\n");
                        }
                    }
                    eachMol.setProperty(activityName.toString(), propertyString);
                    System.out.println(propertyString);
                }

                //((String) eachMol.getProperty("InChI"))
            }
            SDFWriter.writeSDFile(inputMolSet, theOutputMoleculeFile);
        }
        //File theInputMoleculeFile = new File("E:\\FG\\DB\\resultBySpecies\\InChI\\10_0_140806NaTFA_PMs1Nma15TKKH_15_500scan_000002.mzMLlist1.csv.sdf");
        //System.out.println(theInputMoleculeFile.getPath());
        //File theOutputMoleculeFile = new File("E:\\FG\\DB\\resultBySpecies\\InChI\\10_0_140806NaTFA_PMs1Nma15TKKH_15_500scan_000002.mzMLlist1.csv_antioxidant.sdf");
    }
}