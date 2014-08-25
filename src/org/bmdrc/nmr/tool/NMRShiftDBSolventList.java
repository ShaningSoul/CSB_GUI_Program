/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.bmdrc.nmr.tool;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author labwindows
 */
public class NMRShiftDBSolventList {

    private List<String> itsSolventList;
    //constant int variable
    private final int TOLUENE_INDEX = 0;
    private final int ACETONE_INDEX = 1;
    private final int CCL4_INDEX = 2;
    private final int WATER_INDEX = 3;
    private final int METHANOL_INDEX = 4;
    private final int CD2CL2_INDEX = 5;
    private final int DMSO_INDEX = 6;
    private final int PYRIDIN_INDEX = 7;
    private final int BENEZENE_INDEX = 8;
    private final int CDCL3_INDEX = 9;
    private final int TFA_INDEX = 10;
    private final int CD3CN_INDEX = 11;

    public NMRShiftDBSolventList() {
        this.__generateSolventList();
    }

    public List<String> getSolventList() {
        return itsSolventList;
    }

    public void setSolventList(List<String> theSolventList) {
        this.itsSolventList = theSolventList;
    }

    public List<String> setSolventList() {
        return itsSolventList;
    }

    private void __generateSolventList() {
        this.setSolventList(new ArrayList<String>());

        this.setSolventList().add("Toluene");
        this.setSolventList().add("Acetone");
        this.setSolventList().add("TETRACHLORO-METHANE (CCl4)");
        this.setSolventList().add("Water");
        this.setSolventList().add("Methanol (MeOH)");
        this.setSolventList().add("Methylenchloride-D2 (CD2Cl2)");
        this.setSolventList().add("Dimethylsulphoxide-D6 (DMSO-D6, C2D6SO)");
        this.setSolventList().add("Pyridin");
        this.setSolventList().add("Benzene");
        this.setSolventList().add("Chloroform-D1 (CDCl3)");
        this.setSolventList().add("TFA");
        this.setSolventList().add("Acetonitrile-D3(CD3CN)");
    }

    public String modifyStandardSolventName(String theSampleSolvent) {
        if (this.__isToluene(theSampleSolvent)) {
            return this.getSolventList().get(this.TOLUENE_INDEX);
        } else if (this.__isAcetone(theSampleSolvent)) {
            return this.getSolventList().get(this.ACETONE_INDEX);
        } else if (this.__isCCL4(theSampleSolvent)) {
            return this.getSolventList().get(this.CCL4_INDEX);
        } else if (this.__isMethanol(theSampleSolvent)) {
            return this.getSolventList().get(this.METHANOL_INDEX);
        } else if (this.__isCD2Cl2(theSampleSolvent)) {
            return this.getSolventList().get(this.CD2CL2_INDEX);
        } else if (this.__isDMSO(theSampleSolvent)) {
            return this.getSolventList().get(this.DMSO_INDEX);
        } else if (this.__isPyridin(theSampleSolvent)) {
            return this.getSolventList().get(this.PYRIDIN_INDEX);
        } else if (this.__isBenzene(theSampleSolvent)) {
            return this.getSolventList().get(this.BENEZENE_INDEX);
        } else if (this.__isCDCl3(theSampleSolvent)) {
            return this.getSolventList().get(this.CDCL3_INDEX);
        } else if (this.__isTFA(theSampleSolvent)) {
            return this.getSolventList().get(this.TFA_INDEX);
        } else if (this.__isCD3CN(theSampleSolvent)) {
            return this.getSolventList().get(this.CD3CN_INDEX);
        } else if(this.__isWater(theSampleSolvent)) {
            return this.getSolventList().get(this.WATER_INDEX);
        }

        return new String();
    }

    private boolean __isToluene(String theSampleSolvent) {
        if (theSampleSolvent.equals("Toluene") || theSampleSolvent.equals("toluene")) {
            return true;
        }

        return false;
    }

    private boolean __isAcetone(String theSampleSolvent) {
        if (theSampleSolvent.equals("acetone-d6 + 1 drop D2O") || theSampleSolvent.equals("Acetone-D6/D2O")) {
            return true;
        } else if (theSampleSolvent.equals("DMCO-d6") || theSampleSolvent.equals("Acetone-D6 ((CD3)2CO)") || theSampleSolvent.equals("CD3COCD")) {
            return true;
        }

        return false;
    }

    private boolean __isCCL4(String theSampleSolvent) {
        if (theSampleSolvent.equals("TETRACHLORO-METHANE (CCl4)")) {
            return true;
        }

        return false;
    }

    private boolean __isWater(String theSampleSolvent) {
        if (theSampleSolvent.equals("Water(H2O)") || theSampleSolvent.equals("Water(D2O)") || theSampleSolvent.equals("Water (D2O)") || theSampleSolvent.equals("Water (H2O)")) {
            return true;
        } else if (theSampleSolvent.equals("Deuteriumoxide (D2O)")) {
            return true;
        }

        return false;
    }

    private boolean __isMethanol(String theSampleSolvent) {
        if (theSampleSolvent.equals("MeOH-d4") || theSampleSolvent.equals("MeOH") || theSampleSolvent.equals("Methanol-D4 (CD3OD)")) {
            return true;
        }

        return false;
    }

    private boolean __isCD2Cl2(String theSampleSolvent) {
        if (theSampleSolvent.equals("Methylenchloride-D2 (CD2Cl2)")) {
            return true;
        }

        return false;
    }

    private boolean __isDMSO(String theSampleSolvent) {
        if (theSampleSolvent.equals("Dimethylsulphoxide-D6 (DMSO-D6, C2D6SO))") || theSampleSolvent.equals("(CD3)2SO")) {
            return true;
        }

        return false;
    }

    private boolean __isPyridin(String theSampleSolvent) {
        if (theSampleSolvent.equals("Pyridin-D5 (C5D5N)")) {
            return true;
        }

        return false;
    }

    private boolean __isBenzene(String theSampleSolvent) {
        if (theSampleSolvent.equals("Benzene-D6 (C6D6)")) {
            return true;
        }

        return false;
    }

    private boolean __isCDCl3(String theSampleSolvent) {
        if (theSampleSolvent.equals("CDCL3,TMS") || theSampleSolvent.equals("Chloroform-D1 (CDCl3)") || theSampleSolvent.equals("CHCl3")) {
            return true;
        }

        return false;
    }

    private boolean __isTFA(String theSampleSolvent) {
        if (theSampleSolvent.equals("TFA") || theSampleSolvent.equals("Trifluoroacetic Acid (TFA-D1, C2DF3O2)")) {
            return true;
        }

        return false;
    }

    private boolean __isCD3CN(String theSampleSolvent) {
        if (theSampleSolvent.equals("Acetonitrile-D3(CD3CN)")) {
            return true;
        }

        return false;
    }
}
