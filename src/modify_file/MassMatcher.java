/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package modify_file;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import org.bmdrc.gui.MainFrame;
import org.bmdrc.gui.listeners.MassMatcherDeleteButtonActionListener;
import org.bmdrc.gui.listeners.MassMatchingAddDirButtonActionListener;
import org.bmdrc.util.SDFReader;
import org.openscience.cdk.MoleculeSet;
import org.openscience.cdk.exception.CDKException;
import org.openscience.cdk.interfaces.IAtomContainer;
import org.openscience.cdk.interfaces.IMoleculeSet;

/**
 *
 * @author SungBo Hwang, CSB
 * @author GiBum Shin, CSB
 */
public class MassMatcher {

    public static void matchBetweenMassAndMoleculeInDB(MainFrame theFrame) /*throws IOException, CDKException*/ {
        final String SPECIES_PROPERTY_NAME = "SciName";
        final String DB_PROPERTY_NAME = "DBSource";

        IMoleculeSet theInputMoleculeSet = new MoleculeSet();

        File theSDFile = new File("E:\\FG\\DB\\ppinput2resultDir\\dasima.sdf");//Input

        String theFGDBDir = "E:\\FG\\DB";
        File theKEGGMoleculeFolder = new File(theFGDBDir + "\\KEGG");
        File theKTKPMoleculeFolder = new File(theFGDBDir + "\\KTKP");
        File theHERBMoleculeFolder = new File(theFGDBDir + "\\HERB");
        File theGinsengMoleculeFolder = new File(theFGDBDir + "\\ginseng");
        File theLamMoleculeFolder = new File("E:\\FG\\REAXYS\\2");
        File theScutellariaMoleculeFolder = new File("E:\\FG\\REAXYS\\8");
        File theOphiopogonMoleculeFolder = new File("E:\\FG\\REAXYS\\11");
        File thePoriaMoleculeFolder = new File("E:\\FG\\REAXYS\\15");

        theInputMoleculeSet.add(MassMatcher.__readFolderWithTag(theHERBMoleculeFolder, DB_PROPERTY_NAME, "HERB"));
        theInputMoleculeSet.add(MassMatcher.__readFolderWithTag(theGinsengMoleculeFolder, DB_PROPERTY_NAME, "ginseng"));
        theInputMoleculeSet.add(MassMatcher.__readFolderWithTag(theKTKPMoleculeFolder, DB_PROPERTY_NAME, "KTKP"));
        theInputMoleculeSet.add(MassMatcher.__readFolderWithTag(theKEGGMoleculeFolder, DB_PROPERTY_NAME, "KEGG"));
        theInputMoleculeSet.add(MassMatcher.__readFolderWithTag(theGinsengMoleculeFolder, "SciName", "Panax ginseng"));
        theInputMoleculeSet.add(MassMatcher.__readFolderWithTag(theLamMoleculeFolder, "SciName", "Laminaria japonica"));
        theInputMoleculeSet.add(MassMatcher.__readFolderWithTag(theScutellariaMoleculeFolder, "SciName", "Scutellaria baicalensis"));
        theInputMoleculeSet.add(MassMatcher.__readFolderWithTag(thePoriaMoleculeFolder, "SciName", "Poria cocos"));
        theInputMoleculeSet.add(MassMatcher.__readFolderWithTag(theOphiopogonMoleculeFolder, "SciName", "Ophiopogon japonicus"));

        theFrame.setLogTextArea().append(Integer.toString(theInputMoleculeSet.getAtomContainerCount()));
        //inputMolSet = SDFReader.openMoleculeFile(theSDFile);
        theFrame.setLogTextArea().append("read molecule done");
        try {
            MolecularPropertyCalculator thePropertyCalculator = new MolecularPropertyCalculator(theInputMoleculeSet);

            theFrame.setLogTextArea().append(Integer.toString(theInputMoleculeSet.getAtomContainerCount()));
            thePropertyCalculator.calculateExactMass();
            theFrame.setLogTextArea().append(Integer.toString(theInputMoleculeSet.getAtomContainerCount()));
            theInputMoleculeSet = MoleculeSetFilter.getMoleculesByStringMatch(thePropertyCalculator.getMoleculeSet(), SPECIES_PROPERTY_NAME, "Panax ginseng");
            theFrame.setLogTextArea().append(Integer.toString(theInputMoleculeSet.getAtomContainerCount()));
        } catch (CDKException e) {
            JOptionPane.showMessageDialog(null, "CDKException in MolecularPropertyCaluculator!!", "Error", JOptionPane.ERROR_MESSAGE);
        }
        for (IAtomContainer theMolecule : theInputMoleculeSet.molecules()) {
            theFrame.setLogTextArea().append(theMolecule.getProperty(MolecularPropertyCalculator.MOLECULAR_FORMULA_PROPERTY_NAME).toString());
        }
    }

    private static IMoleculeSet __readFolderWithTag(File theDir, String tagPropertyName, String theTag) {
        IMoleculeSet theResultMoleculeSet = new MoleculeSet();
        File[] theFileArray = theDir.listFiles();

        for (File theFile : theFileArray) {
            theResultMoleculeSet.add(SDFReader.openMoleculeFile(theFile));
        }

        for (int mi = 0, mEnd = theResultMoleculeSet.getMoleculeCount(); mi < mEnd; mi++) {
            theResultMoleculeSet.getMolecule(mi).setProperty(tagPropertyName, theTag);
        }

        return theResultMoleculeSet;
    }

    public static Box generateInputBox(MainFrame theFrame) {
        Box theInputBox = theFrame.generateInputBox(new JLabel("Input DB Dir : "), new JTextField(20), new JButton("select"));
        JButton theDeleteButton = new JButton("Delete");
        
        theDeleteButton.addActionListener(new MassMatcherDeleteButtonActionListener(theFrame, theFrame.getFilePathTextFieldList().size()));
        theInputBox.setName(Integer.toString(theFrame.getFilePathTextFieldList().size()));
        theInputBox.add(theDeleteButton);
        
        return theInputBox;
    }
    public static void generatematchBetweenMassAndMoleculeInDBFilePathBox(MainFrame theFrame) {
        JButton theAddDBDirButton = new JButton("Add Dir");

        theAddDBDirButton.addActionListener(new MassMatchingAddDirButtonActionListener(theFrame));
        theFrame.setInputArea().removeAll();
        theFrame.setFilePathTextFieldList(new ArrayList<JTextField>());
        theFrame.setInputArea().add(theFrame.getTemplateFilePathBox());
        theFrame.setInputArea().add(MassMatcher.generateInputBox(theFrame));
        theFrame.setInputArea().add(theAddDBDirButton);
    }
}
