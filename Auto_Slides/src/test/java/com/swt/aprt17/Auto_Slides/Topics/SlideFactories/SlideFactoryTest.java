/**
 * SlideFactoryTest
 * Date: 09.04.2017
 * Last Update: 09.04.2017
 * 
 * @author Christian Staudte 
 */

package com.swt.aprt17.Auto_Slides.Topics.SlideFactories;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.HashSet;

import com.swt.aprt17.Auto_Slides.Presentation.SlideGroup;
import com.swt.aprt17.Auto_Slides.Sources.DBPedia.Properties.Property;
import com.swt.aprt17.Auto_Slides.Topics.Data.BattleData;
import com.swt.aprt17.Auto_Slides.Topics.Data.CityData;
import com.swt.aprt17.Auto_Slides.Topics.Data.MainData;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;
 
/**
 * Unit test for SlideFactory.
 */
public class SlideFactoryTest extends TestCase{
    /**
     * Create the test case
     * @param testName name of the test case
     */
    public SlideFactoryTest( String testName ){
        super( testName );
    }

    /**
     * @return the suite of tests being tested
     */
    public static Test suite(){
        return new TestSuite( SlideFactoryTest.class );
    }
    
    /**
     * TEST 1: Only create Slides for SlideGroups that have information
     */
    public void testIfSlideGroupsWithInformationHaveSlides(){
    	CityData cityData = new CityData("Leipzig");
		cityData.tapSources();
		
		CitySlideFactory slF = new CitySlideFactory(cityData);
		slF.fillSlides();
		
		for(SlideGroup sg : cityData.getSlideGroups()){
			for(Property p : sg.getProperties()){
				if(p.getComplex()){
					if(p.getComplexValues().size() != 0){ /* slidegroup has properties with information */
						assertTrue(sg.getSlideCount() > 0); /* -> at least 1 slide */
						break;
					}
				}else{
					if(p.getValues().size() != 0){ /* slidegroup has properties with information */
						assertTrue(sg.getSlideCount() > 0); /* -> at least 1 slide */
						break;
					}
				}
			}
		}
    }
    
    /**
   	 * TEST 2: Set<String> collectSourcesFromProperties(List<Property> properties)
   	 * 
   	 * Puts all sources of a list of Properties into one set.
   	 */
   	public void testCollectSourcesFromProperties() {
   		MainData md = new BattleData("Test");
       	BattleSlideFactory bsf = new BattleSlideFactory(md);
   		
   		Property prop1 = new Property("test1", "Test1", false, false, "foo");
   		Property prop2 = new Property("test2", "Test2", false, false, "foo");
   		prop1.setSource("source1");
   		prop2.setSource("source2");
   		
   		List<Property> list = new ArrayList<Property>();
   		list.add(prop1);
   		list.add(prop2);
   		
   		Set<String> sources = new HashSet<String>();
   		sources.add("source1");
   		sources.add("source2");
   		
   		assertTrue(bsf.collectSourcesFromProperties(list).equals(sources));		
   	}
   	
   	/**
   	 * TEST 3: String thousandSplitter(String number)
   	 * 
   	 * takes a number as String and puts ' ' between the thousands
   	 */
   	public void testThousandSplitter() {
   		MainData md = new CityData("Ankh-Morpork");
   		CitySlideFactory csf = new CitySlideFactory(md);
       	
       	String number = "111000111000";
       	String not = "this method doesn't care";
       	
       	String number_result = "111,000,111,000";
       	String not_result = "thi,s m,eth,od ,doe,sn',t c,are";
       	
       	assertTrue(csf.thousandSplitter(number).equals(number_result));
       	assertTrue(csf.thousandSplitter(not).equals(not_result));
   	}
   	
    /**
     * TEST 4: String getValue(Property property, int index)
     * 
     * Gets one value of a property, if it exists; if not, returns empty String.
     */
    public void testGetValue() {
    	MainData md = new CityData("Ankh-Morpork");
    	CitySlideFactory csf = new CitySlideFactory(md);
    	
    	Property sam = new Property("Vimes", "Samuel Vimes titles", false, false, null);
    	Property nobby = new Property("Nobby", "Nobby's race", false, false, null);
    	
    	ArrayList<String> titles = new ArrayList<String>();
    	titles.add("Duke");
    	
    	sam.setValues(titles);
    	
    	assertTrue(csf.getValue(sam, 0).equals("Duke"));
    	assertTrue(csf.getValue(sam, 1).equals(""));
    	assertTrue(csf.getValue(nobby, 0).equals(""));    	
    }
    
    /**
     * TEST 5: String lineWithColon(Property property, String value)
     * 
     * Given a property and its value, returns a line (e.g. for a bullet point) 
	 * formatted as [Property]: [value].
	 * If the parameter value is an empty string, it will appear as "unknown". 
     */
    public void testLineWithColon() {
    	MainData md = new CityData("Ankh-Morpork");
    	CitySlideFactory csf = new CitySlideFactory(md);
    	
    	Property sam = new Property("Vimes", "Samuel Vimes full name and title", false, false, null);
    	Property nobby = new Property("Nobby", "Nobby's race", false, false, null);
    	
    	String samTitle = "His Grace, His Excellency, The Duke of Ankh; Commander Sir Samuel Vimes";
    	String samCorrect = "Samuel Vimes full name and title: His Grace, His Excellency, The Duke of Ankh; Commander Sir Samuel Vimes";
    	String nobbyCorrect = "Nobby's race: unknown";
    	
    	assertTrue(csf.lineWithColon(sam, samTitle).equals(samCorrect));
    	assertTrue(csf.lineWithColon(nobby, "").equals(nobbyCorrect));
    }
    
	/**
	 * TEST 6: String listToCommata(Property property)
	 * 
	 * Given a property, this method puts all values in its List<String> values 
	 * separated with commata in one String
	 * e.g.: "value1, value2, value3"
	 */
	public void testListToCommata() {
		MainData md = new CityData("Neverland");
    	CitySlideFactory csf = new CitySlideFactory(md);
    	
    	Property prop1 = new Property("test1", "Test1", false, false, "foo");
    	
    	assertTrue(csf.listToCommata(prop1).equals(""));
    	
    	ArrayList<String> values1 = new ArrayList<String>();
    	values1.add("ein Hut");
    	values1.add("ein Stock");
    	values1.add("ein Regenschirm");    	
    	prop1.setValues(values1);
    	
    	assertTrue(csf.listToCommata(prop1).equals("ein Hut, ein Stock, ein Regenschirm"));    	
	}
    
	/**
	 * TEST 7: String formatArea(String area)
	 * 
	 * Converts the area from m^2 to km^2 and adds spaces. 
	 * ex.: 1000123456 -> 1 000 km²
	 */
	public void testFormatArea() {
		MainData md = new CityData("Islands");
    	CitySlideFactory csf = new CitySlideFactory(md);
    	
    	String average = "1000123456";
    	String double_ = "1000123456.7";
    	String tiny = "123";
    	
    	String average_r = "1,000 km²";
    	String double_r = average_r;
    	String tiny_r = "< 1 km²";
    	
    	assertTrue(csf.formatArea(average).equals(average_r));
    	assertTrue(csf.formatArea(double_).equals(double_r));
    	assertTrue(csf.formatArea(tiny).equals(tiny_r));
	}
    
    
    /**
     * TEST 8: String formatDate(String date)
     * 
     * Transforms date from ISO 8601 (DBPedia format) into 
	 * the DMY format commonly used in Europe and most of the world.
     */
    public void  testFormatDate() {
		String issus = "-333-11-05";
		String lechfeld = "0955-08-10";
		String nonsense = "not a date";
		
	    assertTrue(SlideFactory.formatDate(issus).equals("05.11.333 BC"));
	    assertTrue(SlideFactory.formatDate(lechfeld).equals("10.08.955"));
	    assertTrue(SlideFactory.formatDate(nonsense).equals("not a date"));    
    }
    
   
}
