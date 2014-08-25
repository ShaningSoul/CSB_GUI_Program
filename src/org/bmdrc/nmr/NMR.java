/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.bmdrc.nmr;

/**
 *
 * @author labwindows
 */
public class NMR {
    private String itsSolvent;
    private String itsFieldStrength;
    private String itsTemperature;

    public String getSolvent() {
        return itsSolvent;
    }

    public void setSolvent(String theSolvent) {
        this.itsSolvent = theSolvent;
    }

    public String getFieldStrength() {
        return itsFieldStrength;
    }

    public void setFieldStrength(String theFieldStrength) {
        this.itsFieldStrength = theFieldStrength;
    }

    public String getTemperature() {
        return itsTemperature;
    }

    public void setTemperature(String theTemperature) {
        this.itsTemperature = theTemperature;
    }
}
