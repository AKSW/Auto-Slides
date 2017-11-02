package com.swt.aprt17.Auto_Slides.Controller.Finder;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * Unit test for simple App.
 */
public class FinderTest extends TestCase{
	
    /**
     * Create the test case
     *
     * @param testName name of the test case
     */
    public FinderTest( String testName ){
        super( testName );
    }

    /**
     * @return the suite of tests being tested
     */
    public static Test suite(){
        return new TestSuite( FinderTest.class );
    }
    
    /**
     * TEST 1
     * tests if the links get cleaned properly
     */
    public void testForCleanLink(){
    	String leipzig = Finder.cleanLink("http://dbpedia.org/page/Leipzig");
    	String munich = Finder.cleanLink("http://dbpedia.org/resource/Munich");
    	String wrong = Finder.cleanLink("http://dbpedia.org/ontology/battle");
    	
        assertEquals( "Leipzig", leipzig );
        assertEquals( "Munich", munich );
        assertEquals( "http://dbpedia.org/ontology/battle", wrong );
    }
    
    /**
     * TEST 2
     * tests if the existence of DBPedia information is checked properly
     */
    public void testForDBPediaExistenceCheck(){
    	String CheckTestTrue1 = "http://dbpedia.org/page/Leipzig";
    	assertTrue( Finder.DBPediaExistenceCheck(CheckTestTrue1) );
    	
    	String CheckTestFalse = "http://dbpedia.org/page/leip";
    	assertFalse( Finder.DBPediaExistenceCheck(CheckTestFalse) );
    	
    	String CheckTestWikiTrue = "https://en.wikipedia.org/wiki/Leipzig";
    	assertTrue( Finder.DBPediaExistenceCheck(CheckTestWikiTrue) );
    	
    	String CheckTestOtherLinkFalse = "google.com";
    	assertFalse( Finder.DBPediaExistenceCheck(CheckTestOtherLinkFalse) );
    }
    
    /**
     * TEST 3
     * tests if Wikipedia links are converted to DBPedia links correctly
     */
    public void testForWikiToDBPediaLink(){
    	String wikiLink = "https://en.wikipedia.org/wiki/Leipzig";
    	String DBPLink = Finder.wikiToDBPediaLink(wikiLink);
    	assertEquals("http://dbpedia.org/page/Leipzig", DBPLink);
    	
    	String wrongLink = "google.com";
    	String returnedWrongLink = Finder.wikiToDBPediaLink(wrongLink);
    	assertEquals("", returnedWrongLink);
    }

}
