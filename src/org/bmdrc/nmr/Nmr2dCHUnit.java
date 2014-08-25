package org.bmdrc.nmr;

/**
 *
 * @author SungBo Hwang, CSB
 */
public class Nmr2dCHUnit extends Nmr2dUnit{
    
    public Double getCarbonShift() {
        return this.getFirstShift();
    }

    public void setCarbonShift(Double theCarbonShift) {
        this.setFirstShift(theCarbonShift);
    }

    public Double getHydrogenShift() {
        return this.getSecondShift();
    }

    public void setHydrogenShift(Double theHydrogenShift) {
        this.setSecondShift(theHydrogenShift);
    }

    public Double getCarbonMaxOfRange() {
        return this.getFirstMaxOfRange();
    }

    public void setCarbonMaxOfRange(Double theCarbonMaxOfRange) {
        this.setFirstMaxOfRange(theCarbonMaxOfRange);
    }

    public Double getCarbonMinOfRange() {
        return this.getFirstMinOfRange();
    }

    public void setCarbonMinOfRange(Double theCarbonMinOfRange) {
        this.setFirstMinOfRange(theCarbonMinOfRange);
    }

    public Double getHydrogenMaxOfRange() {
        return this.getSecondMaxOfRange();
    }

    public void setHydrogenMaxOfRange(Double theHydrogenMaxOfRange) {
        this.setSecondMaxOfRange(theHydrogenMaxOfRange);
    }

    public Double getHydrogenMinOfRange() {
        return this.getSecondMinOfRange();
    }

    public void setHydrogenMinOfRange(Double theHydrogenMinOfRange) {
        this.setSecondMinOfRange(theHydrogenMinOfRange);
    }

    public void setPeak(Double theCarbonShift, Double theHydrogenShift) {
        this.setCarbonShift(theCarbonShift);
        this.setHydrogenShift(theHydrogenShift);
    }
    
    public boolean equalShift(Nmr2dCHUnit thePeak) {
        if(this.getCarbonShift() == thePeak.getCarbonShift() && this.getHydrogenShift() == thePeak.getHydrogenShift()) {
            return true;
        }
        
        return false;
    }
}
