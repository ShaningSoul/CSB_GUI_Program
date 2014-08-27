package org.bmdrc.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.Serializable;
import org.openscience.cdk.DefaultChemObjectBuilder;
import org.openscience.cdk.MoleculeSet;
import org.openscience.cdk.interfaces.IMolecule;
import org.openscience.cdk.interfaces.IMoleculeSet;
import org.openscience.cdk.io.iterator.IteratingMDLReader;

/**
 *
 * @author SungBo Hwang, CSB
 * @author GiBum Shin
 */
public abstract class SDFReader implements Serializable{
    private static final long serialVersionUID = -2191073842377518778L;
    
    public static IMoleculeSet openMoleculeFile(File theSdFile) {
        IMoleculeSet theMoleculeSet = new MoleculeSet();
        
        try {
            IteratingMDLReader reader = new IteratingMDLReader(
                    new FileInputStream(theSdFile),
                    DefaultChemObjectBuilder.getInstance());
            while (reader.hasNext()) {
                theMoleculeSet.addMolecule((IMolecule) reader.next());
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        
        return theMoleculeSet;
    }
}
