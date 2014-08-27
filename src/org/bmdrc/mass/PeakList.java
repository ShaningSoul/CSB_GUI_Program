package org.bmdrc.mass;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;
import org.bmdrc.util.Module;

/**
 *
 * @author SungBo Hwang, CSB
 * @author GiBum Shin, CSB
 */
public class PeakList implements Iterable, Serializable{
    private static final long serialVersionUID = 8228792090260590067L;

    private List<PeakUnit> itsPeakList;
    private final int INDEX_OF_WEIGHT = 0;
    private final int INDEX_OF_INTENSITY = 1;

    public PeakList() {
        this.setPeakList(new ArrayList<PeakUnit>());
    }

    public PeakList(PeakList thePeakList) {
        this.setPeakList(new ArrayList<>(thePeakList.getPeakList()));
    }

    public PeakList(File theFile, String theColumnSeparator) throws FileNotFoundException, IOException {
        this.setPeakList(new ArrayList<PeakUnit>());
        this.open(theFile, theColumnSeparator);
    }
    public List<PeakUnit> getPeakList() {
        return itsPeakList;
    }

    public void setPeakList(List<PeakUnit> thePeakList) {
        this.itsPeakList = thePeakList;
    }

    public List<PeakUnit> setPeakList() {
        return itsPeakList;
    }

    public void addPeak(PeakUnit thePeak) {
        this.setPeakList().add(thePeak);
    }

    public List<Double> getWeightList() {
        List<Double> theWeightList = new ArrayList<>();

        for (PeakUnit thePeak : this.getPeakList()) {
            theWeightList.add(thePeak.getWeight());
        }

        return theWeightList;
    }

    public List<Double> getIntensityList() {
        List<Double> theIntensityList = new ArrayList<>();

        for (PeakUnit thePeak : this.getPeakList()) {
            theIntensityList.add(thePeak.getIntensity());
        }

        return theIntensityList;
    }

    public void open(File theFile, String theColumnSeparator) throws FileNotFoundException, IOException {
        BufferedReader theFileReader = new BufferedReader(new FileReader(theFile));
        String theFileString = new String();

        this.setPeakList(new ArrayList<PeakUnit>());

        while ((theFileString = theFileReader.readLine()) != null) {
            String[] theSplitedString = theFileString.split(theColumnSeparator);

            this.setPeakList().add(new PeakUnit(Double.parseDouble(theSplitedString[this.INDEX_OF_WEIGHT]), Double.parseDouble(theSplitedString[this.INDEX_OF_INTENSITY]), theFile.toString()));
        }
    }

    public int size() {
        return this.getPeakList().size();
    }

    public boolean contains(PeakUnit thePeak) {
        return this.getPeakList().contains(thePeak);
    }

    public PeakUnit get(int theIndex) {
        return this.getPeakList().get(theIndex);
    }

    public void remove(int theIndex) {
        this.setPeakList().remove(theIndex);
    }

    public void shiftWeight(double theShift) {
        for (PeakUnit thePeak : this.getPeakList()) {
            thePeak.setWeight(thePeak.getWeight() + theShift);
        }
    }

    public void addAll(PeakList thePeakList) {
        this.setPeakList().addAll(thePeakList.getPeakList());
    }

    public void sort() {
        Set<Double> theSortedWeightList = new TreeSet<>(this.getWeightList());
        List<PeakUnit> theSortedPeakList = new ArrayList<>();

        for (Double theWeight : theSortedWeightList) {
            if (Module.count(this.getWeightList(), theWeight) == 1) {
                int theIndex = this.getWeightList().indexOf(theWeight);

                theSortedPeakList.add(this.getPeakList().get(theIndex));
            } else {
                for(PeakUnit thePeak : this.getPeakList()) {
                    if(thePeak.getWeight().equals(theWeight)) {
                        theSortedPeakList.add(new PeakUnit(thePeak));
                    }
                }
            }
        }

        this.setPeakList(theSortedPeakList);
    }
    
    public boolean containsWeight(PeakUnit thePeak) {
        return this.getWeightList().contains(thePeak.getWeight());
    }
    
    public void add(PeakUnit thePeak) {
        this.setPeakList().add(thePeak);
    }
    
    public int countPeakByWeight(Double theWeight) {
        int theNumberOfPeak = 0;
        
        for(PeakUnit thePeak : this.getPeakList()) {
            if(thePeak.getWeight().equals(theWeight))
            theNumberOfPeak++;
        }
        
        return theNumberOfPeak;
    }
    
    @Override
    public String toString() {
        StringBuilder theStringBuilder = new StringBuilder();
        
        theStringBuilder.append(this.getWeightList());
        
        return theStringBuilder.toString();
    }

    @Override
    public Iterator iterator() {
        return this.getPeakList().iterator();
    }
}
