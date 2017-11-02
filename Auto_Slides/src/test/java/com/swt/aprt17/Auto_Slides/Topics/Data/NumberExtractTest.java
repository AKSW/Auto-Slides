/**
 * DataTest
 * Date: 18.05.2017
 * Last Update: 18.05.2017
 * 
 * @author  Christian Staudte
 */

package com.swt.aprt17.Auto_Slides.Topics.Data;


import java.util.ArrayList;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;
 
/**
 * Unit test for Extracting numbers
 */
public class NumberExtractTest extends TestCase{
    /**
     * Create the test case
     * @param testName name of the test case
     */
    public NumberExtractTest( String testName ){
        super( testName );
    }

    /**
     * @return the suite of tests being tested
     */
    public static Test suite(){
        return new TestSuite( NumberExtractTest.class );
    }


    /**
     * TEST 1: simple test
     */
    public void testSimpleNumber(){
    	System.out.println("=================== TEST 1 ===================");
    	ArrayList<String> texts = new ArrayList<String>();
    	texts.add("Das ist ein 10,000 test");
    	texts.add("a hjghewdw asdh 1,000 sdkjhs 100");
    	texts.add("1");
    	texts.add("10 test");
    	ArrayList<Integer> realNumbers = BattleData.getIntegersFromText(texts);
    	
    	int sum = 0;
    	for(int n : realNumbers){
    		sum += n;
    	}
    	System.out.println("Number is " + sum);
    	System.out.println("asserted: 11111");
    	assertTrue(sum == 11111);
    }
    
    /**
     * TEST 2: brackets
     */
    public void testBrackets(){
    	System.out.println("=================== TEST 2 ===================");
    	ArrayList<String> texts = new ArrayList<String>();
    	texts.add("2000 people (10 of them were dead) + 100 animals");
    	ArrayList<Integer> realNumbers = BattleData.getIntegersFromText(texts);
    	
    	int sum = 0;
    	for(int n : realNumbers){
    		sum += n;
    	}
    	System.out.println("Number is " + sum);
    	System.out.println("asserted: 2100");
    	assertTrue(sum == 2100);
    }
    
    /**
     * TEST 3: total
     */
    public void testTotal(){
    	System.out.println("=================== TEST 3 ===================");
    	ArrayList<String> texts = new ArrayList<String>();
    	texts.add("2000 people (10 of them were dead) + 100 animals");
    	texts.add("2000 people (10 of them were dead) + 100 animals");
    	texts.add("2000 people (10 of them were dead) + 100 animals");
    	texts.add("2000 people (10 of them were dead) + 100 animals");
    	texts.add("Total of 200,000");
    	texts.add("And Total of 20,000");
    	texts.add("And of 20,000");
    	texts.add("And Total of 30,000");
    	ArrayList<Integer> realNumbers = BattleData.getIntegersFromText(texts);
    	
    	int sum = 0;
    	for(int n : realNumbers){
    		sum += n;
    	}
    	System.out.println("Number is " + sum);
    	System.out.println("asserted: 250000");
    	assertTrue(sum == 250000);
    }
    
    /**
     * TEST 4: month
     */
    public void testMonths(){
    	System.out.println("=================== TEST 4 ===================");
    	ArrayList<String> texts = new ArrayList<String>();
    	texts.add("October 16–17: 225,000");
    	texts.add("June 18–19: 430,000");
    	ArrayList<Integer> realNumbers = BattleData.getIntegersFromText(texts);
    	
    	int sum = 0;
    	for(int n : realNumbers){
    		sum += n;
    	}
    	System.out.println("Number is " + sum);
    	System.out.println("asserted: 655000");
    	assertTrue(sum == 655000);
    }
    
    /**
     * TEST 4: average values
     */
    public void testAverage(){
    	System.out.println("=================== TEST 5 ===================");
    	ArrayList<String> texts = new ArrayList<String>();
    	texts.add("100 to 200");
    	texts.add("Also there is like 1,000 to 2,000 in the us");
    	texts.add("and these 200");
    	ArrayList<Integer> realNumbers = BattleData.getIntegersFromText(texts);
    	
    	int sum = 0;
    	for(int n : realNumbers){
    		sum += n;
    	}
    	System.out.println("Number is " + sum);
    	System.out.println("asserted: 1850");
    	assertTrue(sum == 1850);
    }
}