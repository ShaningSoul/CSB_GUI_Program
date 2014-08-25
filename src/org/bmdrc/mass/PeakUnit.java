package org.bmdrc.mass;

/**
 *
 * @author SungBo Hwang, CSB
 * @author GiBum Shin, CSB
 */
public class PeakUnit {
    Double itsWeight;
    Double itsIntensity;
    String itsExperimentFile;

    public PeakUnit() {
        this.setWeight(Double.NaN);
        this.setIntensity(Double.NaN);
        this.setExperimentFile(new String());
    }
    
    public PeakUnit(Double theWeight, Double theIntensity, String theExperimentFilePath) {
        this.setWeight(theWeight);
        this.setIntensity(theIntensity);
        this.setExperimentFile(theExperimentFilePath);
    }

    public PeakUnit(PeakUnit thePeak) {
        this.setWeight(thePeak.getWeight());
        this.setIntensity(thePeak.getIntensity());
        this.setExperimentFile(thePeak.getExperimentFile());
    }
    
    public Double getWeight() {
        return itsWeight;
    }

    public void setWeight(Double theWeight) {
        this.itsWeight = theWeight;
    }

    public Double getIntensity() {
        return itsIntensity;
    }

    public void setIntensity(Double theIntensity) {
        this.itsIntensity = theIntensity;
    }

    public String getExperimentFile() {
        return itsExperimentFile;
    }

    public void setExperimentFile(String itsExperimentFile) {
        this.itsExperimentFile = itsExperimentFile;
    }
}
