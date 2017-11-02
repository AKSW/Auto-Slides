/**
 * ValueCleanerTest
 * Date: 24.03.2017
 * Last Update: 22.04.2017
 * 
 * @author  Christian Staudte
 */

package com.swt.aprt17.Auto_Slides.Sources.DBPedia;

import java.util.ArrayList;

import com.swt.aprt17.Auto_Slides.Sources.DBPedia.Properties.Property;
import com.swt.aprt17.Auto_Slides.Sources.DBPedia.Properties.TextProperty;
import com.swt.aprt17.Auto_Slides.Topics.Data.CityData;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * Unit test for ValueCleaner.
 */
public class ValueCleanerTest extends TestCase{
	
	private ValueGetter valueGetter;
	private ArrayList<Property> testPropertyList = new ArrayList<Property>();
	
	/**
     * Create the test case
     * @param testName name of the test case
     */
    public ValueCleanerTest( String testName ){
        super( testName );
        
        ArrayList<Property> extra = new ArrayList<Property>();
		extra.add(new TextProperty("abstract", "Abstract", false, false, "dbo:", 2));
		
		ArrayList<Property> birthList = new ArrayList<Property>();
		birthList.add(new TextProperty("abstract", "Abstract", false, false, "dbo:", 2));
		birthList.add(new Property("thumbnail","Image", 2, false, false, "dbo:"));
		birthList.add(new Property("successor","Successor", 1, true, true, extra, "dbo:"));
		
		ArrayList<Property> leaderList = new ArrayList<Property>();
		leaderList.add(new TextProperty("abstract", "Abstract", false, false, "dbo:", 2));
		leaderList.add(new Property("party", "Party", false, false, "dbo:"));
		leaderList.add(new Property("thumbnail","Image", 2, false, false, "dbo:"));
		
		this.testPropertyList.add(new TextProperty("abstract",		"Abstract", 		 	false, false, "dbo:", 2));
		this.testPropertyList.add(new Property("country", 			"Country", 			1, 	false, true,  "dbo:"));
		this.testPropertyList.add(new Property("populationTotal", 	"Total population", 1, 	false, false, "dbo:"));
		this.testPropertyList.add(new Property("populationAsOf", 	"Population as of", 1,	false, false, "dbo:"));
		this.testPropertyList.add(new Property("areaCode",			"Area code", 		2,	false, false, "dbo:"));
		this.testPropertyList.add(new Property("leaderName", 	 	"Name of leader", 	1,	false, true, leaderList, "dbo:"));
		this.testPropertyList.add(new Property("thumbnail", 		"Image", 			1, 	false, false, "dbo:"));
		this.testPropertyList.add(new Property("invalidProperty", 	"trolololol", 			false, false, "dbo:"));
		this.testPropertyList.add(new Property("birthPlace",		"Birth place of", 	4,  true, true,  birthList, "dbo:"));
		
		CityData c = new CityData("Leipzig");
		this.valueGetter = new ValueGetter(c.getName(), testPropertyList); /* create valueGetter with testproeprtyList */
        valueGetter.createQuery();
        valueGetter.runQuery();
        
    	ValueCleaner v = new ValueCleaner(this.valueGetter, c);
		v.clean();
    }

    /**
     * @return the suite of tests being tested
     */
    public static Test suite(){
        return new TestSuite( ValueCleanerTest.class );
    }

    /**
     * TEST 1
     * check if invalid properties aren't saved
     */
    public void testIfInvalidPropertiesArentSaved(){
    	System.out.println("\n=================== TEST 1 ===================\n");
		for(Property p : testPropertyList){
			System.out.println(p.getName() + " ("+p.getCleanName()+")");
			if(p.getName().equals("invalidProperty")){
				System.out.println("\tthis property has no values! check so:");
				if(p.getComplex()){
					System.out.println("\tvalues size: "+ p.getComplexValues().size());
					assertTrue(p.getComplexValues().size() == 0);
				}else{
					System.out.println("\tvalues size: "+ p.getValues().size());
					assertTrue(p.getValues().size() == 0);
				}
			}
		}
    }
    
    /**
     * TEST 2
     * check if max values works
     */
   public void testIfMaxValuesGivesMaxValue(){
	   System.out.println("\n=================== TEST 2 ===================\n");
		for(Property p : testPropertyList){
			System.out.println(p.getName() + " ("+p.getCleanName()+")");
			if(p.getComplex() && p.getMaxNumberValues() != -1){
				System.out.println("\thas a max value of "+ p.getMaxNumberValues());
				System.out.println("\tamount of saved values: " + p.getComplexValues().size());
				assertTrue(p.getComplexValues().size() <= p.getMaxNumberValues());
			}else if(!p.getComplex() && p.getMaxNumberValues() != -1){
				System.out.println("\thas a max value of "+ p.getMaxNumberValues());
				System.out.println("\tamount of saved values: " + p.getValues().size());
				assertTrue(p.getValues().size() <= p.getMaxNumberValues());
			}
		}
    }
}
