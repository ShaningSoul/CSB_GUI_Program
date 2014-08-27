package org.bmdrc.mass.tool;

import java.io.Serializable;
import org.bmdrc.mass.PeakList;
import org.bmdrc.mass.PeakUnit;

/**
 *
 * @author SungBo Hwang, CSB
 */
public class NovelPeakFinder implements Serializable{
    private static final long serialVersionUID = -6381629968938410711L;
    private PeakList itsOriginalPeakList;
    private PeakList itsTestPeakList;

    public NovelPeakFinder(PeakList theOriginalPeakList, PeakList theTestPeakList) {
        this.setOriginalPeakList(theOriginalPeakList);
        this.setTestPeakList(theTestPeakList);
    }
    
    public PeakList getOriginalPeakList() {
        return itsOriginalPeakList;
    }

    public void setOriginalPeakList(PeakList theOriginalPeakList) {
        this.itsOriginalPeakList = new PeakList(theOriginalPeakList);
    }

    public PeakList setOriginalPeakList() {
        return itsOriginalPeakList;
    }
    
    public PeakList getTestPeakList() {
        return itsTestPeakList;
    }

    public void setTestPeakList(PeakList theTestPeakList) {
        this.itsTestPeakList = new PeakList(theTestPeakList);
    }
    
    public PeakList setTestPeakList() {
        return itsTestPeakList;
    }

    public PeakList calculateNovelPeakList() {
        PeakList theNovelPeakList = new PeakList();
        
       for(PeakUnit theTestPeak : this.getTestPeakList().getPeakList()) {
           if(!this.getOriginalPeakList().containsWeight(theTestPeak)) {
               theNovelPeakList.addPeak(theTestPeak);
           }
       }
        
        return theNovelPeakList;
    }
}
