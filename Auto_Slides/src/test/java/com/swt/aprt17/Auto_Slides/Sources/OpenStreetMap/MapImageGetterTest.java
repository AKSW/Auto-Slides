package com.swt.aprt17.Auto_Slides.Sources.OpenStreetMap;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * Unit test for simple App.
 */
public class MapImageGetterTest extends TestCase{
	
    /**
     * Create the test case
     *
     * @param testName name of the test case
     */
    public MapImageGetterTest( String testName ){
        super( testName );
    }

    /**
     * @return the suite of tests being tested
     */
    public static Test suite(){
        return new TestSuite( MapImageGetterTest.class );
    }
    
    /**
     * TEST 1
     * tests if bad url is handled properly
     */
    public void testForNoCoordsIsNull(){
    	MapImageGetter mig = new MapImageGetter("Location_that_doesn't_exists");
    	double[] coords = mig.getCoords();
    	
		assertEquals(null, coords);
    }
    
    /**
     * TEST 2
     * tests if correct coordinates returned
     */
    public void testForCorrectCoordinates(){
    	MapImageGetter mig = new MapImageGetter("Rio");
    	double[] coords = mig.getCoords();
    	System.out.println(coords[0]+" "+coords[1]);
		assertEquals(-22, (int) coords[0]);
		assertEquals(-43, (int) coords[1]);
    }

}
