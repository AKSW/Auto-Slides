/**
 * DataTest
 * Date: 26.03.2017
 * Last Update: 06.04.2017
 * 
 * @author  Christian Staudte
 */

package com.swt.aprt17.Auto_Slides.Topics.Data;


import com.swt.aprt17.Auto_Slides.Sources.Content;
import com.swt.aprt17.Auto_Slides.Sources.DBPedia.Properties.Property;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;
 
/**
 * Unit test for Data.
 */
public class DataTest extends TestCase{
    /**
     * Create the test case
     * @param testName name of the test case
     */
    public DataTest( String testName ){
        super( testName );
    }

    /**
     * @return the suite of tests being tested
     */
    public static Test suite(){
        return new TestSuite( DataTest.class );
    }


    /**
     * TEST 1: print city test
     */
    public void testCityPrint(){
    	System.out.println("\n=================== TEST 1 ===================\n");
    	String town = "Leipzig";
    	CityData cityData = new CityData(town);
    	cityData.tapSources();
    	
    	String text = cityData.toString();
    	assertTrue(!text.equals(""));
    	System.out.println(text);
    }
    
    /**
     * TEST 2: print battle test
     */
    public void testBattlePrint(){
    	System.out.println("\n=================== TEST 2 ===================\n");
    	String battle = "Battle_of_Verdun";
    	BattleData battleData = new BattleData(battle);
    	battleData.tapSources();
    	
    	String text = battleData.toString();
    	assertTrue(!text.equals(""));
    	System.out.println(text);
    }
    
    /**
     * TEST 3: test if content from properties isn't in simplepedia/wikipedia and content
     * 			from simplepedia is not in wikipedia
     */
    public void testForSingleContent(){
    	System.out.println("\n=================== TEST 3 ===================\n");
    	String battle = "Battle_of_Stalingrad";
    	BattleData battleData = new BattleData(battle);
    	battleData.tapSources();
    	
    	//property test:
    	System.out.println("Properties: ");
    	for(Property p : battleData.getProperties()){
    		System.out.println(p.getName() + " | " + p.getCleanName());
    		
    		for(Content c : battleData.getSimplePediaTexts()){
    			assertTrue(!c.getTitle().equals((p.getName())));
    			assertTrue(!c.getTitle().equals((p.getCleanName())));
    		}
    		
    		for(Content c : battleData.getWikiPediaTexts()){
    			assertTrue(!c.getTitle().equals((p.getName())));
    			assertTrue(!c.getTitle().equals((p.getCleanName())));
    		}
    	}
    	
    	System.out.println("\n");
    	System.out.println("Simple Pedia: ");
    	
    	//simple pedia test:
    	for(Content c : battleData.getSimplePediaTexts()){
    		System.out.println(c.getTitle());
    		for(Content c2 : battleData.getWikiPediaTexts()){
    			assertTrue(!c2.getTitle().equals((c.getTitle())));
    		}
    	}
    	
    	System.out.println("\n");
    	System.out.println("Wiki Pedia: ");
    	for(Content c : battleData.getWikiPediaTexts()){
    		System.out.println(c.getTitle());
    	}
    }
}