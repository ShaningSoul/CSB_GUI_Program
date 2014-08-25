package org.bmdrc.util;

/**
 *
 * @author SungBo Hwang, CSB
 */
public class TreeNode <Key, Value> {
    private Key itsKey;
    private Value itsValue;
    private int itsDepth;
    private int itsIndexInDepth;

    public TreeNode() {
        this.setDepth(-1);
        this.setIndexInDepth(-1);
    }
    
    public TreeNode(Key theKey, Value theValue, int theDepth, int theIndexInDepth) {
        this.itsKey = theKey;
        this.itsValue = theValue;
        this.itsDepth = theDepth;
        this.itsIndexInDepth = theIndexInDepth;
    }

    
    public Key getKey() {
        return itsKey;
    }

    public void setKey(Key theKey) {
        this.itsKey = theKey;
    }

    public Value getValue() {
        return itsValue;
    }

    public void setValue(Value theValue) {
        this.itsValue = theValue;
    }

    public int getDepth() {
        return itsDepth;
    }

    public void setDepth(int theDepth) {
        this.itsDepth = theDepth;
    }

    public int getIndexInDepth() {
        return itsIndexInDepth;
    }

    public void setIndexInDepth(int theIndexInDepth) {
        this.itsIndexInDepth = theIndexInDepth;
    }
}
