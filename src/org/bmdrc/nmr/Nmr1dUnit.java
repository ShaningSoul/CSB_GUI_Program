package org.bmdrc.nmr;

/**
 *
 * @author SungBo Hwang, CSB
 * 
 */
public class Nmr1dUnit extends NMR{
    private Double itsChemicalShift;
    private Double itsMinimum;
    private Double itsMaximum;
    private Double itsIntensity;
    private Integer itsAnnotatedAtomNumber;
    
    public Nmr1dUnit() {
    }
    
    public Nmr1dUnit(Nmr1dUnit thePeak) {
        this.setChemicalShift(thePeak.getChemicalShift());
        this.setIntensity(thePeak.getIntensity());
        this.setMaxOfRange(thePeak.getMaxOfRange());
        this.setMinOfRange(thePeak.getMinOfRange());
    }
    
    public Double getChemicalShift() {
        return itsChemicalShift;
    }

    public void setChemicalShift(Double theChemicalShift) {
        this.itsChemicalShift = theChemicalShift;
    }

    public Double getMinOfRange() {
        return itsMinimum;
    }

    public void setMinOfRange(Double theMinimum) {
        this.itsMinimum = theMinimum;
    }

    public Double getMaxOfRange() {
        return itsMaximum;
    }

    public void setMaxOfRange(Double theMaximum) {
        this.itsMaximum = theMaximum;
    }

    public Double getIntensity() {
        return itsIntensity;
    }

    public void setIntensity(Double theIntensity) {
        this.itsIntensity = theIntensity;
    }
    
    public void setRange(double theMinimum, double theMaximum) {
        this.setMinOfRange(theMinimum);
        this.setMaxOfRange(theMaximum);
    }

    public int getAnnotatedAtomNumber() {
        return itsAnnotatedAtomNumber;
    }

    public void setAnnotatedAtomNumber(int theAnnotatedAtomNumber) {
        this.itsAnnotatedAtomNumber = theAnnotatedAtomNumber;
    }
    
    @Override
    public String toString() {
        StringBuilder theString = new StringBuilder();
        
        theString.append("Chemical Shift        : ").append(this.getChemicalShift()).append("\n");
        theString.append("Minimum               : ").append(this.getMinOfRange()).append("\n");
        theString.append("Maximum               : ").append(this.getMaxOfRange()).append("\n");
        theString.append("Intensity             : ").append(this.getIntensity()).append("\n");
        theString.append("Annotated Atom Number : ").append(this.getAnnotatedAtomNumber()).append("\n");
        theString.append("Solvent               : ").append(this.getSolvent()).append("\n");
        theString.append("Temperature           : ").append(this.getTemperature()).append("\n");
        theString.append("Field strength        : ").append(this.getFieldStrength()).append("\n");
        
        return theString.toString();
    }
}
