/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modify_file;

import org.openscience.cdk.DefaultChemObjectBuilder;
import org.openscience.cdk.Molecule;
import org.openscience.cdk.MoleculeSet;
import org.openscience.cdk.exception.CDKException;
import org.openscience.cdk.formula.MolecularFormula;
import org.openscience.cdk.interfaces.IAtomContainer;
import org.openscience.cdk.interfaces.IMolecularFormula;
import org.openscience.cdk.interfaces.IMolecule;
import org.openscience.cdk.interfaces.IMoleculeSet;
import org.openscience.cdk.tools.CDKHydrogenAdder;
import org.openscience.cdk.tools.manipulator.AtomContainerManipulator;
import org.openscience.cdk.tools.manipulator.MolecularFormulaManipulator;

/**
 *
 * @author grinn
 */
public class MolecularPropertyCalculator {

    private IMoleculeSet itsMoleculeSet;
    private IMoleculeSet itsFailedMoleculeSet;
    public final static String MOLECULAR_FORMULA_PROPERTY_NAME = "Molecular_Formula";
    public final static String EXACT_MASS_PROPERTY_NAME = "exact mass";

    public MolecularPropertyCalculator(IMoleculeSet inputMoleculeSet) throws CDKException {
        this.setMoleculeSet(inputMoleculeSet);
        this.setFailedMoleculeSet(new MoleculeSet());
        this.__addHydrogens();
        this.__setMF();
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

    public IMoleculeSet getFailedMoleculeSet() {
        return itsFailedMoleculeSet;
    }

    public void setFailedMoleculeSet(IMoleculeSet theFailedMoleculeSet) {
        this.itsFailedMoleculeSet = theFailedMoleculeSet;
    }

    public IMoleculeSet setFailedMoleculeSet() {
        return itsFailedMoleculeSet;
    }

    private void __addFailedMoleculeSet(IMolecule theFailedMolecule) {
        if (this.getFailedMoleculeSet() == null) {
            this.setFailedMoleculeSet(new MoleculeSet());
            this.setFailedMoleculeSet().addMolecule(theFailedMolecule);
        } else {
            this.setFailedMoleculeSet().addMolecule(theFailedMolecule);
        }
    }

    public void setMF(int theMoleculeIndex, IMolecularFormula theFormula) {
        this.setMoleculeSet().getMolecule(theMoleculeIndex).setProperty(this.MOLECULAR_FORMULA_PROPERTY_NAME, MolecularFormulaManipulator.getString(theFormula));
    }

    private void __setMF() {
        for (IAtomContainer theMolecule : this.setMoleculeSet().molecules()) {
            theMolecule.setProperty(this.MOLECULAR_FORMULA_PROPERTY_NAME, MolecularFormulaManipulator.getString(MolecularFormulaManipulator.getMolecularFormula(theMolecule)));
        }
    }

    private void __addHydrogens() {
        for (IAtomContainer theMolecule : this.setMoleculeSet().molecules()) {
            try {
                AtomContainerManipulator.percieveAtomTypesAndConfigureAtoms(theMolecule);
                CDKHydrogenAdder adder = CDKHydrogenAdder.getInstance(theMolecule.getBuilder());
                adder.addImplicitHydrogens(theMolecule);
            } catch (CDKException e) {
                e.printStackTrace();
                this.setMoleculeSet().removeAtomContainer(theMolecule);
                this.__addFailedMoleculeSet((Molecule) theMolecule);
            }
        }
    }

    public void calculateExactMass() {
        for (IAtomContainer theMolecule : this.getMoleculeSet().molecules()) {
            try {
                theMolecule.setProperty(MolecularPropertyCalculator.EXACT_MASS_PROPERTY_NAME, 
                        MolecularFormulaManipulator.getMajorIsotopeMass(MolecularFormulaManipulator.getMolecularFormula(theMolecule)));
            } catch (NullPointerException e) {
                theMolecule.setProperty(MolecularPropertyCalculator.EXACT_MASS_PROPERTY_NAME, -1);
                this.setMoleculeSet().removeAtomContainer(theMolecule);
                this.__addFailedMoleculeSet((Molecule) theMolecule);
            }
        }
    }
}