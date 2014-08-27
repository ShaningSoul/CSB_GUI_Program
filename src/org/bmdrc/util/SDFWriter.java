package org.bmdrc.util;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Serializable;
import org.openscience.cdk.exception.CDKException;
import org.openscience.cdk.interfaces.IMoleculeSet;

/**
 *
 * @author SungBo Hwang, CSB
 * @author Gi Bum Shin, CSB
 */
public abstract class SDFWriter implements Serializable {
    private static final long serialVersionUID = -2297477678943575687L;
    
    public static void writeSDFile(IMoleculeSet molSet, File outputFile) {
        try {
            FileWriter out = new FileWriter(outputFile);
            org.openscience.cdk.io.SDFWriter sdfW = new org.openscience.cdk.io.SDFWriter(out);
            sdfW.write(molSet);
            sdfW.close();
            out.close();
        } catch (CDKException | IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            System.exit(-1);
        }
    }
}
