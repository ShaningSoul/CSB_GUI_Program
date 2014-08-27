/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.bmdrc.nmr.abstracts;

import java.io.Serializable;

/**
 *
 * @author labwindows
 */
public abstract class AbstractNmr implements Serializable{
    private static final long serialVersionUID = 5706688163884223272L;
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
