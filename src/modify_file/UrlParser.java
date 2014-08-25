/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modify_file;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import org.bmdrc.interfaces.IStringConstant;

/**
 *
 * @author GiBeom Shin, CSB
 */
public class UrlParser implements IStringConstant {

    private String itsUrlString;
    private LinkedHashMap<String, String> itsUrlComponents;
    private String itsStartingPoint;
    private String itsEndPoint;
    private String itsSeparator;
    //constant string variable
    private final String CSV_STARTING_POINT = "</form>";
    private final String CSV_SEPARATOR = this.TAB_STRING;
    private final String CSV_END_POINT = "no end point";
    private final String CID_LIST_STARTING_POINT = "<IdList>";
    private final String CID_LIST_END_POINT = "</IdList>";
    private final String CID_LIST_SEPARATOR = "";
    private final String CID_LIST_AS_LIST_REGEX = "\\s";
    private final String ASSAY_STARTING_POINT = "more detailed data";
    private final String ASSAY_END_POINT = "<input type=\"hidden";
    private final String ASSAY_SEPARATOR = this.TAB_STRING;
    private final String HTML_TAG_FIRST_REGEX = "<(/)?([a-zA-Z]*)(\\s[a-zA-Z]*=[^>]*)?(\\s)*(/)?>";
    private final String HTML_TAG_SECOND_REGEX = "\\&nbsp;";
    //constant int variable
    private final Integer OK_RESPONSE_CODE = 200;

    UrlParser() {
    }

    UrlParser(String inputString, String theStartingPoint, String theSeparator, String theEndPoint) {
        this.itsUrlString = inputString;
        this.itsStartingPoint = theStartingPoint;
        this.itsSeparator = theSeparator;
        this.itsEndPoint = theEndPoint;
    }

    UrlParser(LinkedHashMap<String, String> theUrlComponentMap) {
        this.setUrlParser(theUrlComponentMap, "</form>", "\t");
    }

    UrlParser(LinkedHashMap<String, String> theUrlComponentMap, String theStartingPoint, String theSeparator) {
        this.setUrlComponents(theUrlComponentMap);
        this.setUrlParser(theUrlComponentMap, theStartingPoint, theSeparator);
    }

    public String getUrlString() {
        return itsUrlString;
    }

    public void setUrlString(String itsUrlString) {
        this.itsUrlString = itsUrlString;
    }

    public LinkedHashMap<String, String> getUrlComponents() {
        return itsUrlComponents;
    }

    public void setUrlComponents(LinkedHashMap<String, String> theUrlComponents) {
        this.itsUrlComponents = theUrlComponents;
    }

    public LinkedHashMap<String, String> setUrlComponents() {
        return itsUrlComponents;
    }

    public String getStartingPoint() {
        return itsStartingPoint;
    }

    public void setStartingPoint(String theStartingPoint) {
        this.itsStartingPoint = theStartingPoint;
    }

    public String getEndPoint() {
        return itsEndPoint;
    }

    public void setEndPoint(String theEndPoint) {
        this.itsEndPoint = theEndPoint;
    }

    public String getSeparator() {
        return itsSeparator;
    }

    public void setSeparator(String theSeparator) {
        this.itsSeparator = theSeparator;
    }

    public void setUrlParser(LinkedHashMap<String, String> theUrlComponentMap) {
        this.setUrlComponents(theUrlComponentMap);
        this.setUrlString(theUrlComponentMap);
    }

    public void setUrlParser(LinkedHashMap<String, String> theUrlComponentMap, String theStartingPoint, String theSeparator) {
        this.setStartingPoint(theStartingPoint);
        this.setSeparator(theSeparator);
        this.setUrlString(theUrlComponentMap);
    }

    public void setUrlString(LinkedHashMap<String, String> theUrlComponentMap) {
        StringBuilder theUrlStringBuilder = new StringBuilder();

        for (String constant : theUrlComponentMap.keySet()) {
            theUrlStringBuilder.append(constant).append(theUrlComponentMap.get(constant));
        }

        this.setUrlString(theUrlStringBuilder.toString());
    }

    public String getCSV(String cid) throws MalformedURLException, IOException {
        UrlParser theParser = new UrlParser("http://pubchem.ncbi.nlm.nih.gov/rest/pug/compound/cid/" + cid + "/assaysummary/CSV", this.CSV_STARTING_POINT, this.CSV_SEPARATOR, this.CSV_END_POINT);
        URLConnection theUrlConnection = new URL("http://pubchem.ncbi.nlm.nih.gov/rest/pug/compound/cid/" + cid + "/assaysummary/CSV").openConnection();
        BufferedReader theUrlReader = new BufferedReader(new InputStreamReader(theUrlConnection.getInputStream()));
        String line = new String();
        StringBuilder result = new StringBuilder();

        while ((line = theUrlReader.readLine()) != null) {
            result.append(line).append(this.END_LINE);
        }
        return result.toString();
    }

    public List<String> getPubchemCSV(String cid) throws MalformedURLException, IOException {
        URLConnection theUrlConnection = new URL("http://pubchem.ncbi.nlm.nih.gov/rest/pug/compound/cid/" + cid + "/assaysummary/CSV").openConnection();
        BufferedReader theUrlReader = new BufferedReader(new InputStreamReader(theUrlConnection.getInputStream()));
        String theFileString = new String();
        List<String> thePubchemCSVList = new ArrayList<>();

        theUrlReader.readLine();

        while ((theFileString = theUrlReader.readLine()) != null) {
            thePubchemCSVList.add(theFileString);
        }

        return thePubchemCSVList;
    }

    public List<String> getPubchemCSV(String cid, List<String> theFilter) throws MalformedURLException, IOException {
        HttpURLConnection theUrlConnection = (HttpURLConnection) new URL("http://pubchem.ncbi.nlm.nih.gov/rest/pug/compound/cid/" + cid + "/assaysummary/CSV").openConnection();
        int theResponseCode = theUrlConnection.getResponseCode();
        List<String> thePubchemCsvList = new ArrayList<String>();

        if (theResponseCode == this.OK_RESPONSE_CODE) {
            BufferedReader theUrlReader = new BufferedReader(new InputStreamReader(theUrlConnection.getInputStream()));
            String theFileString;

            theUrlReader.readLine();

            while ((theFileString = theUrlReader.readLine()) != null) {
                if (this.containsAll(theFileString, theFilter)) {
                    thePubchemCsvList.add(theFileString);
                }
            }
        } else {
            thePubchemCsvList.add("no page for " + cid);
        }

        return thePubchemCsvList;
    }

    public boolean containsAll(String theString, List<String> theFilter) {
        for (String element : theFilter) {
            if (!theString.contains(element)) {
                return false;
            }
        }
        return true;
    }

    public String getSynonymListToString(String theCid) throws MalformedURLException, IOException {
        UrlParser theParser = new UrlParser("https://pubchem.ncbi.nlm.nih.gov/summary/summary.cgi?q=nama&cid=" + theCid, this.CSV_STARTING_POINT, this.CSV_SEPARATOR, this.CSV_END_POINT);

        return theParser.__getURLTextWithoutTag(false);
    }

    public String getCIDListToString(String theQuery) throws MalformedURLException {
        UrlParser theParser = new UrlParser("http://www.ncbi.nlm.nih.gov/entrez/eutils/esearch.fcgi?db=pccompound&retmax=1000&term=" + theQuery, this.CID_LIST_STARTING_POINT,
                this.CID_LIST_SEPARATOR, this.CID_LIST_END_POINT);

        return theParser.__getURLTextWithoutTag(true);
    }

    public List<String> getCIDList(String theQuery) throws MalformedURLException {
        return Arrays.asList(this.getCIDListToString(theQuery).split(this.CID_LIST_AS_LIST_REGEX));
    }

    public String getAssayStrings(String cid) throws MalformedURLException {
        UrlParser theParser = new UrlParser("https://pubchem.ncbi.nlm.nih.gov/assay/assay.cgi?q=da&ocfilter=act&cid=" + cid, this.ASSAY_STARTING_POINT, 
                this.ASSAY_SEPARATOR, this.ASSAY_END_POINT);
        
        return theParser.__getURLTextWithoutTag(false);
    }

    private String __getURLTextWithoutTag(Boolean theHasEndPoint) throws MalformedURLException {

        String theResultString = new String();
        URL theTargetUrl = new URL(this.getUrlString());
        URLConnection theUrlConnection;

        try {
            theUrlConnection = theTargetUrl.openConnection();

            BufferedReader theUrlReader = new BufferedReader(new InputStreamReader(theUrlConnection.getInputStream()));
            // skip lines until it reached to intended lines. loop ends at starting point.
            this.__skipLines(theUrlReader, this.getStartingPoint());
            //Starting readLine();

            if (this.__isAtStartingPoint(theUrlReader, this.getStartingPoint())) {
                if (theHasEndPoint) {
                    theResultString = this.__getURLStringWithoutTag(theUrlReader, this.getSeparator(), this.getEndPoint());
                } else {
                    theResultString = this.__getURLStringWithoutTag(theUrlReader, this.getSeparator(), null);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            theResultString = "Nohitsfor:" + this.getUrlString();
        }
        return theResultString;
    }

    private String __getURLText() throws MalformedURLException {
        String result = new String();
        URL target = new URL(this.getUrlString());
        URLConnection theUrlConnection;

        try {
            theUrlConnection = target.openConnection();

            BufferedReader theUrlReader = new BufferedReader(new InputStreamReader(theUrlConnection.getInputStream()));
            this.__skipLines(theUrlReader, this.getStartingPoint());
            result = this.__getURLString(theUrlReader);
        } catch (Exception e) {
            System.err.println(e);
            result = "No hits for " + this.getUrlString();
        }
        return result;
    }

    private String __removeTags(String theInputLine, String theSeparator) {
        return theInputLine.replaceAll(this.HTML_TAG_FIRST_REGEX, theSeparator).replaceAll(this.HTML_TAG_SECOND_REGEX, this.EMPTY_STRING);
    }

    private void __skipLines(BufferedReader theUrlReader, String theString2MatchWith) throws IOException {
        while (!(theUrlReader.readLine().contains(theString2MatchWith))) {
            theUrlReader.mark(10000);
        }
        theUrlReader.reset();
    }

    private String __getURLString(BufferedReader theUrlReader) throws Exception {
        StringBuilder theUrlStringBuilder = new StringBuilder();
        String theFileString = new String();
        
        while ((theFileString = theUrlReader.readLine()) != null) {
            if (theFileString.contains(this.getEndPoint())) {
                return theUrlStringBuilder.toString();
            }
            theUrlStringBuilder.append(theFileString).append(this.END_LINE);

        }
        return theUrlStringBuilder.toString();
    }

    private String __getURLStringWithoutTag(BufferedReader theUrlReader, String theSeparator, String theEndPoint) throws Exception {
        StringBuilder theUrlStringBuilder = new StringBuilder();
        String theTxtWithoutTag = new String();
        String theFileString = new String();
        
        while ((theFileString = theUrlReader.readLine()) != null) {
            if (theFileString.contains(theEndPoint)) {
                return theUrlStringBuilder.toString();
            }
            theTxtWithoutTag = this.__removeTags(theFileString, theSeparator);
            theUrlStringBuilder.append(theTxtWithoutTag);
            theUrlStringBuilder.append(this.END_LINE);
        }
        return theUrlStringBuilder.toString();
    }

    private String __getURLStringWithoutTag(BufferedReader theUrlReader, String theSeparator) throws Exception {
        StringBuilder theUrlStringBuilder = new StringBuilder();
        String theTxtWithoutTag = new String();
        String theFileString = new String();
        
        while ((theFileString = theUrlReader.readLine()) != null) {
            theTxtWithoutTag = this.__removeTags(theFileString, theSeparator);
            theUrlStringBuilder.append(theTxtWithoutTag);
            theUrlStringBuilder.append(this.END_LINE);
        }
        return theUrlStringBuilder.toString();
    }

    private Boolean __isAtStartingPoint(BufferedReader in, String startingPoint) throws IOException {
        return in.readLine().contains(startingPoint);
    }
}
