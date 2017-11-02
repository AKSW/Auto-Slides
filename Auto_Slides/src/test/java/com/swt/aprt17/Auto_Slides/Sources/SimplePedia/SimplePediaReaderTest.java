/**
 * SimplePediaReaderTest
 * Date: 21.04.2017
 * Last Update: 22.04.2017
 * 
 * @author  Christian Staudte
 */

package com.swt.aprt17.Auto_Slides.Sources.SimplePedia;


import java.util.ArrayList;

import com.swt.aprt17.Auto_Slides.Sources.Content;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;
 
/**
 * Unit test for SimplePediaReader.
 */
public class SimplePediaReaderTest extends TestCase{
	
    /**
     * Create the test case
     * @param testName name of the test case
     */
    public SimplePediaReaderTest( String testName ){
        super( testName );
    }

    /**
     * @return the suite of tests being tested
     */
    public static Test suite(){
        return new TestSuite( SimplePediaReaderTest.class );
    }

    /**
     * TEST 1: check if content is not wanted, it really is not inside
     */
    public void testIfNotWantedContentReallyExcludesContent(){
    	System.out.println("\n=================== TEST 1 ===================\n");
    	ArrayList<String> notWantedContent = new ArrayList<String>();
    	notWantedContent.add("NotInTestLol");
    	notWantedContent.add("Population");
    	notWantedContent.add("Geography");
    	SimplePediaReader spr = new SimplePediaReader("Leipzig", notWantedContent);
    	
    	for(String s : notWantedContent){
    		System.out.println("not wanted: " + s);
    		
    		for(Content c : spr.getSimplePediaTexts()){
    			System.out.println("\tkey: " + c.getTitle());
    			assertTrue(!c.getTitle().equals(s));
    		}
    	}
    }
    
    /**
     * TEST 2: no noContentList used, are there problems?
     */
    public void testIfNullListAsParameterWorks(){
    	System.out.println("\n=================== TEST 2 ===================\n");
    	SimplePediaReader spr = new SimplePediaReader("Leipzig", null);
    	
    	System.out.println("All contents: ");
		for(Content c : spr.getSimplePediaTexts()){
			System.out.println("\tkey: " + c.getTitle());
		}
		assertTrue(true); // no errors so far -> null works
    }
}