/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modify_file;

import java.util.ArrayList;
import org.openscience.cdk.Molecule;
import org.openscience.cdk.MoleculeSet;
import org.openscience.cdk.interfaces.IAtomContainer;
import org.openscience.cdk.interfaces.IAtomContainerSet;
import org.openscience.cdk.interfaces.IMoleculeSet;

/**
 *
 * @author grinn
 */
public class MoleculeSetFilter {

    public static IMoleculeSet getMoleculesByStringMatch(IMoleculeSet theInputMoleculeSet, String thePropertyName, String theMatchString) {
        IMoleculeSet theResultMoleculeSet = new MoleculeSet();
        for (IAtomContainer theMolecule : theInputMoleculeSet.molecules()) {
            try {
                if ( theMolecule.getProperty(thePropertyName).toString().toLowerCase().contains(theMatchString.toLowerCase())) {
                    theResultMoleculeSet.addMolecule((Molecule) theMolecule);
                }
            } catch (NullPointerException e) {
                //System.err.println("there's no field named " + propertyName);
            }
        }
        return theResultMoleculeSet;
    }

    public static IMoleculeSet getMoleculesByAllStringMatch(IMoleculeSet inputMoleculeSet, String propertyName, ArrayList<String> matchString) {
        boolean hasAllStrings;
        IMoleculeSet result = new MoleculeSet();
        for (IAtomContainer theMolecule : inputMoleculeSet.molecules()) {
            hasAllStrings = true;
            for (String theString : matchString) {
                if (!(((String) theMolecule.getProperty(propertyName)).toLowerCase().contains(theString.toLowerCase()))) {
                    hasAllStrings = false;
                }
            }
            if (hasAllStrings) {
                result.addMolecule((Molecule) theMolecule);
            }
        }
        return result;
    }

    public static IMoleculeSet getMoleculesByORStringMatch(IMoleculeSet inputMoleculeSet, String propertyName, ArrayList<String> matchString) {
        boolean hasAnyOfStrings;
        IMoleculeSet result = new MoleculeSet();
        for (IAtomContainer theMolecule : inputMoleculeSet.molecules()) {
            hasAnyOfStrings = false;
            for (String theString : matchString) {
                if (((String) theMolecule.getProperty(propertyName)).toLowerCase().contains(theString.toLowerCase())) {
                    hasAnyOfStrings = true;
                }
            }
            if (hasAnyOfStrings) {
                result.addMolecule((Molecule) theMolecule);
            }
        }
        return result;
    }

    public static IMoleculeSet getMoleculesByORStringMatch(IMoleculeSet inputMoleculeSet, ArrayList<String> propertyNameList, ArrayList<String> matchString) {
        IMoleculeSet result = new MoleculeSet();
        for (String thePropName : propertyNameList) {
            MoleculeSetFilter.getMoleculesByORStringMatch(inputMoleculeSet, thePropName, matchString);
        }
        return result;
    }
}
