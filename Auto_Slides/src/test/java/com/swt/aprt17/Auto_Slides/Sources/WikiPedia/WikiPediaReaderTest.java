/**
 * SimplePediaReaderTest
 * Date: 07.05.2017
 * Last Update: 07.05.2017
 * 
 * @author  Christian Staudte
 */

package com.swt.aprt17.Auto_Slides.Sources.WikiPedia;


import java.util.ArrayList;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;
 
/**
 * Unit test for SimplePediaReader.
 */
public class WikiPediaReaderTest extends TestCase{
	
    /**
     * Create the test case
     * @param testName name of the test case
     */
    public WikiPediaReaderTest( String testName ){
        super( testName );
    }

    /**
     * @return the suite of tests being tested
     */
    public static Test suite(){
        return new TestSuite( WikiPediaReaderTest.class );
    }

    /**
     * TEST 1: check if content in saved as wanted
     */
    public void testifContentIsSaved(){
    	System.out.println("\n=================== TEST 1 ===================\n");
    	
    	ArrayList<String> notWanted = new ArrayList<String>();
    	String topic = "Battle_of_Leipzig";
    	
    	WikiPediaReader wpr = new WikiPediaReader(topic, notWanted);
    	System.out.println(wpr);
    }
}