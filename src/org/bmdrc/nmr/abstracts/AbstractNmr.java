/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.bmdrc.nmr.abstracts;

/**
 *
 * @author labwindows
 */
public abstract class AbstractNmr {
    private double itsNMRResolution;
    private double itsSignalToNoise;

    public double getNMRResolution() {
        return itsNMRResolution;
    }

    public void setNMRResolution(double theNMRResolution) {
        this.itsNMRResolution = theNMRResolution;
    }

    public double getSignalToNoise() {
        return itsSignalToNoise;
    }

    public void setSignalToNoise(double theSignalToNoise) {
        this.itsSignalToNoise = theSignalToNoise;
    }
    
    
}
