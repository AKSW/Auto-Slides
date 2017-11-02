package com.swt.aprt17.Auto_Slides.Controller.Finder;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * Unit test for simple App.
 */
public class CityFinderTest extends TestCase{
	
    /**
     * Create the test case
     *
     * @param testName name of the test case
     */
    public CityFinderTest( String testName ){
        super( testName );
    }

    /**
     * @return the suite of tests being tested
     */
    public static Test suite(){
        return new TestSuite( CityFinderTest.class );
    }

    /**
     * TEST 1
     * tests if the correct city if found
     */
    public void testForCorrectSearchResult(){
    	String testString = "new york";
    	CityFinder TestCityFinder = new CityFinder( testString );
    	TestCityFinder.createSearchQuery();
    	TestCityFinder.runSearchQuery();
        assertEquals( "New York City", TestCityFinder.getResultStringArray().clone()[0] );
        assertEquals( "New York City (United States)", TestCityFinder.getResultPrintStringArray().clone()[0] );
        
        testString = "Leipzig";
        TestCityFinder = new CityFinder( testString );
        TestCityFinder.createSearchQuery();
    	TestCityFinder.runSearchQuery();
        assertEquals( "Leipzig", TestCityFinder.getResultStringArray().clone()[0] );
        assertEquals( "Leipzig (Germany)", TestCityFinder.getResultPrintStringArray().clone()[0] );
    }
    
    /**
     * TEST 2
     * tests if resultStringArray is empty with no results found 
     * or if query is null
     */
    public void testForEmptyWithNoResult(){
    	String testString = "%";
    	CityFinder TestCityFinder = new CityFinder( testString );
        TestCityFinder.createSearchQuery();
    	TestCityFinder.runSearchQuery();
        assertTrue( TestCityFinder.getResultStringArray().length == 0 );
        
    	testString = "example";
        TestCityFinder = new CityFinder( testString );
        TestCityFinder.runSearchQuery();
    }
}
