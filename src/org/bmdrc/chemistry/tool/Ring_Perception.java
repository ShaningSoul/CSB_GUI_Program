/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.bmdrc.chemistry.tool;

import java.util.*;
import java.io.*;
import java.lang.Iterable;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.openscience.cdk.io.iterator.IteratingMDLReader;
import org.openscience.cdk.DefaultChemObjectBuilder;
import org.openscience.cdk.interfaces.IMolecule;
import org.openscience.cdk.interfaces.IAtom;
import org.openscience.cdk.interfaces.IBond;
import org.openscience.cdk.interfaces.IRingSet;
import org.openscience.cdk.interfaces.IRing;
import org.openscience.cdk.ringsearch.AllRingsFinder;
import org.openscience.cdk.interfaces.IAtomContainer;
import org.openscience.cdk.interfaces.IAtomContainerSet;
import org.openscience.cdk.aromaticity.AromaticityCalculator;
import org.openscience.cdk.io.SDFWriter;
import org.openscience.cdk.Molecule;
import org.openscience.cdk.exception.CDKException;
import org.openscience.cdk.interfaces.IBond.Order;
import org.openscience.cdk.interfaces.IRing;

/**
 *
 * @author labwindows
 */
public class Ring_Perception implements Serializable{
    private static final long serialVersionUID = 1L;

    public static void setAromaticityBond(ArrayList<IMolecule> theMolecules) throws CDKException {
        for (int i = 0; i < theMolecules.size(); i++) {
            setAromaticityBond(theMolecules.get(i));
        }
    }

    public static void setAromaticityBond(IMolecule theMolecule) throws CDKException {
        checkRingStructure(theMolecule);
        checkAromaticity(theMolecule);
    }

    public static java.util.ArrayList<File> getFileList(String theWorkDirPath) {
        java.util.ArrayList<File> theFileList = new java.util.ArrayList<File>();
        File theWorkDir = new File(theWorkDirPath);

        if (theWorkDir.isDirectory()) {
            File theCheckFileList[] = theWorkDir.listFiles();

            for (int i = 0; i < theCheckFileList.length; i++) {
                if (theCheckFileList[i].isFile() && theCheckFileList[i].getName().contains("sd")) {
                    theFileList.add(theCheckFileList[i]);
                }
            }
        }


        return theFileList;
    }

    public static java.util.ArrayList<IMolecule> getMoleculeList(String theFileName) {
        java.util.ArrayList<IMolecule> theMoleculeList = new java.util.ArrayList<IMolecule>();
        try {
            IteratingMDLReader theReader = new IteratingMDLReader(
                    new FileReader(theFileName),
                    DefaultChemObjectBuilder.getInstance());
            while (theReader.hasNext()) {
                theMoleculeList.add((IMolecule) theReader.next());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return theMoleculeList;
    }

    public static void writeMDLFile(String theFileName, java.util.List<IMolecule> theMoleculeList) {
        try {
            SDFWriter theWriter = new SDFWriter(new FileWriter(theFileName));
            for (IMolecule theMolecule : theMoleculeList) {
                theWriter.write(theMolecule);
            }
            theWriter.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void checkAromaticity(java.util.List<IMolecule> theMoleculeList) {
        java.util.ArrayList<IMolecule> theAddedMoleculeList = new java.util.ArrayList<IMolecule>();
        for (IMolecule theMolecule : theMoleculeList) {
            try {
                checkAromaticity(theMolecule);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public static void checkAromaticity(IMolecule theMolecule) throws CDKException {
        AromaticityCalculator theCalculator = new org.openscience.cdk.aromaticity.AromaticityCalculator();
        IRingSet theRingSet = new org.openscience.cdk.RingSet();
        IRingSet theModifiedRingSet = new org.openscience.cdk.RingSet();

        theRingSet = ringPerception(theMolecule);

        for (int i = 0, s = theRingSet.getAtomContainerCount(); i < s; i++) {
            IRing theRing = new org.openscience.cdk.Ring();

            theRing.add(theRingSet.getAtomContainer(i));

            if (theCalculator.isAromatic(theRing, theMolecule)) {
                theModifiedRingSet.addAtomContainer(theRing);
            }
        }

        if (!theModifiedRingSet.isEmpty()) {
            theMolecule.setProperty("Aromatic_Ring", getcheckAromaticityDataFormat(theMolecule, theModifiedRingSet));
        }


    }

    public static String getcheckAromaticityDataFormat(IMolecule theMolecule, IRingSet theRingSet) {
        java.lang.StringBuffer theStringBuilder = new java.lang.StringBuffer();

        try {
            for (int i = 0; i < theRingSet.getAtomContainerCount(); i++) {
                for (int j = 0; j < theRingSet.getAtomContainer(i).getAtomCount(); j++) {
                    theStringBuilder.append(theMolecule.getAtomNumber(theRingSet.getAtomContainer(i).getAtom(j)));
                    theStringBuilder.append("/");
                }
                theStringBuilder.append(theMolecule.getAtomNumber(theRingSet.getAtomContainer(i).getAtom(0)));
                if (i < theRingSet.getAtomContainerCount() - 1) {
                    theStringBuilder.append("\t");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return theStringBuilder.toString();
    }

    public static void checkRingStructure(java.util.List<IMolecule> theMoleculeList) {
        java.util.ArrayList<IMolecule> theAddedMoleculeList = new java.util.ArrayList<IMolecule>();
        for (IMolecule theMolecule : theMoleculeList) {
            try {
                checkRingStructure(theMolecule);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public static void checkRingStructure(IMolecule theMolecule) throws CDKException {
        IRingSet theRingSet = new org.openscience.cdk.RingSet();
        theRingSet = ringPerception(theMolecule);

        theMolecule.setProperty("Ring_Perception", getRingPerceptionDataFormat(theMolecule, theRingSet));
    }

    public static IRingSet ringPerception(IMolecule theMolecule) throws CDKException {
        AllRingsFinder theFinder = new org.openscience.cdk.ringsearch.AllRingsFinder();
        IRingSet theRingSet = new org.openscience.cdk.RingSet();

        theRingSet = theFinder.findAllRings(theMolecule);

        for (int i = theRingSet.getAtomContainerCount() - 1; i >= 0; i--) {
            if (theRingSet.getAtomContainer(i).getAtomCount() > 6) {
                theRingSet.removeAtomContainer(i);
            }
        }

        return theRingSet;
    }

    public static String getRingPerceptionDataFormat(IMolecule theMolecule, IRingSet theRingSet) {
        java.lang.StringBuilder theStringBuilder = new java.lang.StringBuilder();

        try {
            for (int i = 0; i < theRingSet.getAtomContainerCount(); i++) {
                for (int j = 0; j < theRingSet.getAtomContainer(i).getAtomCount(); j++) {
                    theStringBuilder.append(theMolecule.getAtomNumber(theRingSet.getAtomContainer(i).getAtom(j)));
                    if (theRingSet.getAtomContainer(i).getAtomCount() - 1 > j) {
                        theStringBuilder.append("/");
                    }
                }
                if (i < theRingSet.getAtomContainerCount() - 1) {
                    theStringBuilder.append("\t");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return theStringBuilder.toString();
    }
}
