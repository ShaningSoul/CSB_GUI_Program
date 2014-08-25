package org.bmdrc.util;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author SungBo Hwang, CSB
 */
public class Tree <Key,Value> {
    private List<TreeNode<Key,Value>> itsTreeNodeList;
    private List<TreeLinker> itsTreeLinkerList;
    private String itsTreeName;
    private List<Integer> itsMaximumIndexEachDepth;

    public List<TreeNode<Key,Value>> getTreeNodeList() {
        return itsTreeNodeList;
    }

    public void setTreeNodeList(List<TreeNode<Key,Value>> theTreeNodeList) {
        this.itsTreeNodeList = theTreeNodeList;
    }

    public List<TreeNode<Key,Value>> setTreeNodeList() {
        return itsTreeNodeList;
    }
    
    public List<TreeLinker> getTreeLinkerList() {
        return itsTreeLinkerList;
    }

    public void setTreeLinkerList(List<TreeLinker> theTreeLinkerList) {
        this.itsTreeLinkerList = theTreeLinkerList;
    }

    public List<TreeLinker> setTreeLinkerList() {
        return itsTreeLinkerList;
    }
    
    public String getTreeName() {
        return itsTreeName;
    }

    public void setTreeName(String theTreeName) {
        this.itsTreeName = theTreeName;
    }

    public List<Integer> getMaximumIndexEachDepth() {
        return itsMaximumIndexEachDepth;
    }

    public void setMaximumIndexEachDepth(List<Integer> theMaximumIndexEachDepth) {
        this.itsMaximumIndexEachDepth = theMaximumIndexEachDepth;
    }
    
    public List<Integer> setMaximumIndexEachDepth() {
        return itsMaximumIndexEachDepth;
    }
    
    public int getMaximumDepth() {
        int theMaximumDepth = 0;
        
        for(TreeNode theTreeNode : this.getTreeNodeList()) {
            if(theMaximumDepth > theTreeNode.getDepth()) {
                theMaximumDepth = theTreeNode.getDepth();
            }
        }
        
        return theMaximumDepth;
    }
    
    public void addTreeNode(TreeNode theTreeNode) {
        this.setTreeNodeList().add(theTreeNode);
    }
    
    private int __getIndexInNewTreeNode(int theDepth) {
        if(this.getMaximumIndexEachDepth().size() < theDepth) {
            return this.getMaximumIndexEachDepth().get(theDepth) + 1;
        } else {
            return 0;
        }
    }
    
    public void addTreeNode(Key theKey, Value theValue, int theDepth) {
        TreeNode<Key, Value> theTreeNode = new TreeNode<>();
        
        theTreeNode.setKey(theKey);
        theTreeNode.setValue(theValue);
        theTreeNode.setDepth(theDepth);
        theTreeNode.setIndexInDepth(this.__getIndexInNewTreeNode(theDepth));
        
        this.setTreeNodeList().add(theTreeNode);
    }
    
    public void addTreeLinker(TreeLinker theTreeLinker) {
        this.setTreeLinkerList().add(theTreeLinker);
    }
    
    public void addTreeLinker(TreeNode thePreviousTreeNode, TreeNode thePresentTreeNode) {
        TreeLinker theTreeLinker = new TreeLinker();
        
        theTreeLinker.setIndexInPresentDepth(thePresentTreeNode.getIndexInDepth());
        theTreeLinker.setIndexInPreviousDepth(thePreviousTreeNode.getIndexInDepth());
        theTreeLinker.setPresentDepth(thePresentTreeNode.getDepth());
        theTreeLinker.setPreviousDepth(thePreviousTreeNode.getDepth());
    }
    
    public TreeNode getTreeNode(int theDepth, int theIndex) {
        for(TreeNode theTreeNode : this.getTreeNodeList()) {
            if(theTreeNode.getDepth() == theDepth && theTreeNode.getIndexInDepth() == theIndex) {
                return theTreeNode;
            }
        }
        
        System.err.println("Incorrect depth or index");
        
        return new TreeNode();
    }
    
    public List<TreeNode<Key,Value>> getTreeNodeListInDepth(int theDepth) {
        List<TreeNode<Key,Value>> theTreeNodeList = new ArrayList<>();
        
        for(TreeNode theTreeNode : this.getTreeNodeList()) {
            theTreeNodeList.add(theTreeNode);
        }
        
        return theTreeNodeList;
    }
    
    @Override
    public String toString() {
        StringBuilder theStringBuilder = new StringBuilder();
        
        theStringBuilder.append("Tree Name : ").append(this.getTreeName()).append("\n");
        theStringBuilder.append("Total Depth : ").append(this.getMaximumDepth());
        
        return theStringBuilder.toString();
    }
}
