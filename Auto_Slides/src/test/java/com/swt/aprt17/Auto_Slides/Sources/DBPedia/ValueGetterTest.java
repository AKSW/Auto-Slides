package com.swt.aprt17.Auto_Slides.Sources.DBPedia;

import java.util.ArrayList;
import java.util.List;

import com.swt.aprt17.Auto_Slides.Sources.DBPedia.Properties.Property;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * Unit test for simple App.
 */
public class ValueGetterTest extends TestCase{
	
    /**
     * Create the test case
     *
     * @param testName name of the test case
     */
    public ValueGetterTest( String testName ){
        super( testName );
    }

    /**
     * @return the suite of tests being tested
     */
    public static Test suite(){
        return new TestSuite( ValueGetterTest.class );
    }

    /**
     * TEST 1
     * test if value is set to 'null' if none exist
     */
    public void testSetNullForNoValue(){
    	String testResource = "London_(European_Parliament_constituency)";
    	List<Property> testPropertyList = new ArrayList<Property>();
    	testPropertyList.add( new Property("mah", "", false, false, "dbo:") );	
    	ValueGetter testValueGetter = new ValueGetter(testResource, testPropertyList);
    	testValueGetter.createQuery();
    	testValueGetter.runQuery();
        assertEquals( "( ?mah = \"null\" )", testValueGetter.getResultStringArray().clone()[1] );
    }
  
    /**
     * TEST 2
     * no query if no property given
     */
    public void testNoPropertyNoQuery(){
    	String testResource = "Munich";
    	List<Property> testPropertyList = new ArrayList<Property>();
    	ValueGetter testValueGetter = new ValueGetter(testResource, testPropertyList);
    	testValueGetter.createQuery();
    	testValueGetter.runQuery();
        assertEquals( null, testValueGetter.getQuery() );
        assertEquals( null, testValueGetter.getResultStringArray() );
    }
    
    /**
     * TEST 3
     * multiple results if multiple values exist
     */
    public void testMulitpleValues(){
    	String testResource = "Weida,_Thuringia";
    	List<Property> testPropertyList = new ArrayList<Property>();
    	testPropertyList.add( new Property("areaCode", "", false, false, "dbo:") );
    	ValueGetter testValueGetter = new ValueGetter(testResource, testPropertyList);
    	testValueGetter.createQuery();
    	testValueGetter.runQuery();
        assertTrue( testValueGetter.getResultStringArray().length > 2 );
    } 
    
    /**
     * TEST 4
     * query gathers the correct results with normal properties
     */
    public void testCorrectResults(){
    	String testResource = "Munich";
    	List<Property> testPropertyList = new ArrayList<Property>();
    	testPropertyList.add( new Property("populationTotal", "", false, false, "dbo:") );
    	testPropertyList.add( new Property("populationAsOf", "", false, false, "dbo:") );
    	ValueGetter testValueGetter = new ValueGetter(testResource, testPropertyList);
    	testValueGetter.createQuery();
    	testValueGetter.runQuery();
        assertEquals( "( ?populationTotal = \"1517868\" )", testValueGetter.getResultStringArray().clone()[1] );
        assertEquals( "( ?populationAsOf = \"2015-10-31\" )", testValueGetter.getResultStringArray().clone()[2] );
    }
    
    /**
     * TEST 5
     * query gathers the correct results with inverted properties
     */
    public void testCorrectResultsWithInvertedProperties(){
    	String testResource = "Weida,_Thuringia";
    	List<Property> testPropertyList = new ArrayList<Property>();
    	testPropertyList.add( new Property("birthPlace", "", true, false, "dbo:") );
    	ValueGetter testValueGetter = new ValueGetter(testResource, testPropertyList);
    	testValueGetter.createQuery();
    	testValueGetter.runQuery();
        assertEquals( "( ?birthPlace = \"http://dbpedia.org/resource/Günther_Brendel\" )", testValueGetter.getResultStringArray().clone()[1] );
    }
  
    /**
     * TEST 6
     * query gathers the correct source
     */
    public void testCorrectSource(){
    	String testResource = "Weida,_Thuringia";
    	List<Property> testPropertyList = new ArrayList<Property>();
    	testPropertyList.add( new Property("populationTotal", "", false, false, "dbo:") );
    	ValueGetter testValueGetter = new ValueGetter(testResource, testPropertyList);
    	testValueGetter.createQuery();
    	testValueGetter.runQuery();
        assertEquals( "http://dbpedia.org/resource/Weida,_Thuringia http://en.wikipedia.org/wiki/Weida,_Thuringia?oldid=689006353", testValueGetter.getResultStringArray().clone()[0] );
    }
    
    /**
     * TEST 7
     * query gathers the correct results with mixed properties
     */
    public void testCorrectResultsWithMixedProperties(){
    	String testResource = "Weida,_Thuringia";
    	List<Property> testPropertyList = new ArrayList<Property>();
    	testPropertyList.add( new Property("populationTotal", "", false, false, "dbo:") );
    	testPropertyList.add( new Property("birthPlace", "", true, false, "dbo:") );
    	ValueGetter testValueGetter = new ValueGetter(testResource, testPropertyList);
    	testValueGetter.createQuery();
    	testValueGetter.runQuery();
        assertEquals( "( ?populationTotal = \"8724\" )", testValueGetter.getResultStringArray().clone()[1] );
        assertEquals( "( ?birthPlace = \"http://dbpedia.org/resource/Günther_Brendel\" )", testValueGetter.getResultStringArray().clone()[2] );
    }
    
    /**
     * TEST 8
     * query gathers english text only
     */
    public void testEnglishTextOnly(){
    	String testResource = "Leipzig";
    	List<Property> testPropertyList = new ArrayList<Property>();
    	testPropertyList.add( new Property("abstract", "", false, false, "dbo:") );
    	ValueGetter testValueGetter = new ValueGetter(testResource, testPropertyList);
    	testValueGetter.createQuery();
    	testValueGetter.runQuery();
    	assertTrue( testValueGetter.getResultStringArray().length == 2 );
    } 
}
