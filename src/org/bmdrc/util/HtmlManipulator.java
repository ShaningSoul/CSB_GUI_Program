package org.bmdrc.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;

/**
 *
 * @author SungBo Hwnag, CSB
 */
public class HtmlManipulator {
    private String itsURL;

    public String getURL() {
        return itsURL;
    }

    public void setURL(String theURL) {
        this.itsURL = theURL;
    }
    
    public String savePage() throws IOException {
        String theLine = new String();
        StringBuilder thePageSource = new StringBuilder();
        URL theUrl = null;
        BufferedReader theReader = null;
        
        try {
            theUrl = new URL(this.getURL());
            theReader = new BufferedReader(new InputStreamReader(theUrl.openStream()));

            while ((theLine = theReader.readLine()) != null) {
                thePageSource.append(theLine);
            }
        } finally {
            if (theReader != null) {
                theReader.close();
            }
        }

        return thePageSource.toString();
    }
}
