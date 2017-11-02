package com.swt.aprt17.Auto_Slides.Controller.Finder;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * Unit test for simple App.
 */
public class BattleFinderTest extends TestCase{
    /**
     * Create the test case
     *
     * @param testName name of the test case
     */
    public BattleFinderTest( String testName ){
        super( testName );
    }

    /**
     * @return the suite of tests being tested
     */
    public static Test suite(){
        return new TestSuite( BattleFinderTest.class );
    }

    /**
     * TEST 1
     * tests if the correct battle if found
     */
    public void testForCorrectSearchResult(){
    	String testString = "leipzig";
    	BattleFinder TestBattleFinder = new BattleFinder( testString );
    	TestBattleFinder.createSearchQuery();
    	TestBattleFinder.runSearchQuery();
        assertEquals( "Battle of Leipzig", TestBattleFinder.getResultStringArray().clone()[0] );
        assertEquals( "Battle of Leipzig (19.10.1813)", TestBattleFinder.getResultPrintStringArray().clone()[0] );
        
        testString = "Battle of Actium";
        TestBattleFinder = new BattleFinder( testString );
        TestBattleFinder.createSearchQuery();
    	TestBattleFinder.runSearchQuery();
        assertEquals( "Battle of Actium", TestBattleFinder.getResultStringArray().clone()[0] );
        assertEquals( "Battle of Actium (02.09.31 BC)", TestBattleFinder.getResultPrintStringArray().clone()[0] );
    }
    
    /**
     * TEST 2
     * tests if resultStringArray is empty with no results found 
     * or if query is null
     */
    public void testForEmptyWithNoResult(){
    	String testString = "%%%%%%";
    	BattleFinder TestBattleFinder = new BattleFinder( testString );
        TestBattleFinder.createSearchQuery();
    	TestBattleFinder.runSearchQuery();
        assertTrue( TestBattleFinder.getResultStringArray().length == 0 );
        
        testString = "example";
        TestBattleFinder = new BattleFinder( testString );
        TestBattleFinder.runSearchQuery();
    }
}
