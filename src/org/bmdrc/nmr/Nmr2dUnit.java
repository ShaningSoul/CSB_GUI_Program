package org.bmdrc.nmr;

/**
 *
 * @author SungBo Hwang, CSB
 */
public class Nmr2dUnit extends NMR {
    private Double itsFirstShift;
    private Double itsSecondShift;
    private Double itsFirstMaxOfRange;
    private Double itsFirstMinOfRange;
    private Double itsSecondMaxOfRange;
    private Double itsSecondMinOfRange;
    private Double itsIntensity;

    public Nmr2dUnit() {
        this.itsFirstShift = -1.0;
        this.itsSecondShift = -1.0;
        this.itsFirstMaxOfRange = -1.0;
        this.itsFirstMinOfRange = -1.0;
        this.itsSecondMaxOfRange = -1.0;
        this.itsSecondMinOfRange = -1.0;
        this.itsIntensity = -1.0;
    }

    public Nmr2dUnit(Double theFirstShift, Double theSecondShift, Double theFirstMaxOfRange, Double theFirstMinOfRange, Double theSecondMaxOfRange, Double theSecondMinOfRange, 
            Double theIntensity) {
        this.itsFirstShift = itsFirstShift;
        this.itsSecondShift = itsSecondShift;
        this.itsFirstMaxOfRange = itsFirstMaxOfRange;
        this.itsFirstMinOfRange = itsFirstMinOfRange;
        this.itsSecondMaxOfRange = itsSecondMaxOfRange;
        this.itsSecondMinOfRange = itsSecondMinOfRange;
        this.itsIntensity = itsIntensity;
    }

    public Nmr2dUnit(Nmr2dUnit theNmr2dUnit) {
        this.itsFirstShift = theNmr2dUnit.getFirstShift();
        this.itsSecondShift = theNmr2dUnit.getSecondShift();
        this.itsFirstMaxOfRange = theNmr2dUnit.getFirstMaxOfRange();
        this.itsFirstMinOfRange = theNmr2dUnit.getFirstMinOfRange();
        this.itsSecondMaxOfRange = theNmr2dUnit.getSecondMaxOfRange();
        this.itsSecondMinOfRange = theNmr2dUnit.getSecondMinOfRange();
        this.itsIntensity = theNmr2dUnit.getIntensity();
    }
    
    public Double getFirstShift() {
        return itsFirstShift;
    }

    public void setFirstShift(Double theFirstShift) {
        this.itsFirstShift = theFirstShift;
    }

    public Double getSecondShift() {
        return itsSecondShift;
    }

    public void setSecondShift(Double theSecondShift) {
        this.itsSecondShift = theSecondShift;
    }

    public Double getFirstMaxOfRange() {
        return itsFirstMaxOfRange;
    }

    public void setFirstMaxOfRange(Double theFirstMaxOfRange) {
        this.itsFirstMaxOfRange = theFirstMaxOfRange;
    }

    public Double getFirstMinOfRange() {
        return itsFirstMinOfRange;
    }

    public void setFirstMinOfRange(Double theFirstMinOfRange) {
        this.itsFirstMinOfRange = theFirstMinOfRange;
    }

    public Double getSecondMaxOfRange() {
        return itsSecondMaxOfRange;
    }

    public void setSecondMaxOfRange(Double theSecondMaxOfRange) {
        this.itsSecondMaxOfRange = theSecondMaxOfRange;
    }

    public Double getSecondMinOfRange() {
        return itsSecondMinOfRange;
    }

    public void setSecondMinOfRange(Double theSecondMinOfRange) {
        this.itsSecondMinOfRange = theSecondMinOfRange;
    }

    public Double getIntensity() {
        return itsIntensity;
    }

    public void setIntensity(Double theIntensity) {
        this.itsIntensity = theIntensity;
    }
}
