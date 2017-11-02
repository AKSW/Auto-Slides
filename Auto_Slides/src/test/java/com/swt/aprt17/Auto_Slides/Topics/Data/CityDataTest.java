/**
 * CityDataTest
 * Date: 26.03.2017
 * Last Update: 06.04.2017
 * 
 * @author  Christian Staudte
 */

package com.swt.aprt17.Auto_Slides.Topics.Data;


import com.swt.aprt17.Auto_Slides.Presentation.SlideGroup;
import com.swt.aprt17.Auto_Slides.Sources.DBPedia.Properties.Property;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;
 
/**
 * Unit test for CityData.
 */
public class CityDataTest extends TestCase{
    /**
     * Create the test case
     * @param testName name of the test case
     */
    public CityDataTest( String testName ){
        super( testName );
    }

    /**
     * @return the suite of tests being tested
     */
    public static Test suite(){
        return new TestSuite( CityDataTest.class );
    }


    /**
     * TEST 1: If try to get Slidegroups by name that doesn't exists, method returns null, else slidegroup.
     * Also names are NOT case sensitive
     */
    public void testForNullSlideGroups(){
    	System.out.println("\n=================== TEST 1 ===================\n");
    	String town = "New_York_CityData";
    	CityData cityData = new CityData(town);
    	System.out.println("Test for town: " + town);
    	SlideGroup a = cityData.getSlideGroup("Title"); /* normal SlideGroup name */
    	System.out.println("Title Group: " + a);
    	assertNotNull(a);
    	a = cityData.getSlideGroup("title"); /* not case sensitive */
    	System.out.println("title Group: " + a);
    	assertNotNull(a);
    	a = cityData.getSlideGroup("NotASlideGroupName"); /* wrong name */
    	System.out.println("NotASlideGroupName Group: " + a);
    	assertNull(a);
    }
    
    /**
     * TEST 2: If cityData parameters are created
     */
    public void testAnyStringParameterForCityData(){
    	System.out.println("\n=================== TEST 2 ===================\n");
    	String town = "New_York_City";
    	CityData cityData = new CityData(town);
    	cityData.tapSources();
    	System.out.println("Town " + town + "exists: " + cityData.hashCode());
    	assertNotNull(cityData);
    	System.out.println("Properties: " + cityData.getProperties().hashCode());
    	assertNotNull(cityData.getProperties());
    	System.out.println("SlideGroups: " + cityData.getSlideGroups().hashCode());
    	assertNotNull(cityData.getSlideGroups());
    }
    
    /**
     * TEST 3: Test if there are complex properties, 
     * that every new resource has individual properties, not references!
     */
    public void testIfComplexResourcesHaveIndividualProperties(){
    	System.out.println("\n=================== TEST 3 ===================\n");
    	String town = "Leipzig";
    	CityData cityData = new CityData(town);
    	cityData.tapSources();
    	SlideGroup s = cityData.getSlideGroup("Born in this City");
    	if(s == null) System.out.println("SlideGroup doesn't exist");
    	System.out.println("Test with town: " + town);
    	
    	System.out.println(s.getProperties());
		for(Property p : s.getProperties()){
    		if(p.getComplex()){
    			for(String key : p.getComplexValues().keySet()){
    				System.out.println("look for key: " + key);
    				for(String key2 : p.getComplexValues().keySet()){
    					if(!key.equals(key2)){
    						System.out.println("\tCompare to: " + key2);
    						for(int i = 0; i < p.getComplexValues().get(key).size(); i++){
    							/* only works because all complex properties have same sub-properties here */
    							Property p_a = p.getComplexValues().get(key).get(i);
    							Property p_b = p.getComplexValues().get(key2).get(i);
    							System.out.println("\t\t"+p_a.getName() + " ("+p_a.hashCode()+") | " + p_b.getName() + " ("+p_b.hashCode()+")");
    							assertFalse(p_a.hashCode() == p_b.hashCode());
    						}
    					}
    				}
    			}
    		}
    	}
    }
}
