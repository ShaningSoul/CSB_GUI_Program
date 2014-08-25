package org.bmdrc.util;

/**
 *
 * @author SungBo Hwang, CSB
 */
public class TreeLinker {
    private int itsPreviousDepth;
    private int itsPresentDepth;
    private int itsIndexInPreviousDepth;
    private int itsIndexInPresentDepth;

    public TreeLinker() {
        this.setPreviousDepth(-1);
        this.setPresentDepth(-1);
        this.setIndexInPreviousDepth(-1);
        this.setIndexInPresentDepth(-1);
    }
    
    public TreeLinker(int thePreviousDepth, int thePresentDepth, int theIndexInPreviousDepth, int theIndexInPresentDepth) {
        this.itsPreviousDepth = thePreviousDepth;
        this.itsPresentDepth = thePresentDepth;
        this.itsIndexInPreviousDepth = theIndexInPreviousDepth;
        this.itsIndexInPresentDepth = theIndexInPresentDepth;
    }

    
    public int getPreviousDepth() {
        return itsPreviousDepth;
    }

    public void setPreviousDepth(int thePreviousDepth) {
        this.itsPreviousDepth = thePreviousDepth;
    }

    public int getPresentDepth() {
        return itsPresentDepth;
    }

    public void setPresentDepth(int thePresentDepth) {
        this.itsPresentDepth = thePresentDepth;
    }

    public int getIndexInPreviousDepth() {
        return itsIndexInPreviousDepth;
    }

    public void setIndexInPreviousDepth(int theIndexInPreviousDepth) {
        this.itsIndexInPreviousDepth = theIndexInPreviousDepth;
    }

    public int getIndexInPresentDepth() {
        return itsIndexInPresentDepth;
    }

    public void setIndexInPresentDepth(int theIndexInPresentDepth) {
        this.itsIndexInPresentDepth = theIndexInPresentDepth;
    }
}
