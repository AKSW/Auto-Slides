package com.swt.aprt17.Auto_Slides.Controller;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

public class FileInputScannerTest extends TestCase {
	
	/**
	 * @param testName
	 */
	public FileInputScannerTest(String testName) {
		super(testName);
		
	}

	 /**
     * @return the suite of tests being tested
     */
    public static Test suite(){
        return new TestSuite( FileInputScannerTest.class );
    }
    
    /**
     * TEST 1
     * Test if input is read/checked correctly
     */
    public void testIfEveryCityPresentationIsCreatedNotDependingOnTopicName(){
    	String testInput = "//cities\n"
    			+ "#city\n"
    			+ "Leipzig\n"
    			+ "//London\n"
    			+ "#battle\n"
    			+ "Battle_of_Leipzig\n"
    			+ "//testing\n"
    			+ "\n"
    			+ "Testersten\n"
    			+ "#test";
    	String[] inputs = testInput.split("\n");
    	FileInputScanner fis = new FileInputScanner("#test");
    	int i = 0;
    	for(String str : inputs) {
    		i++;
    		inputs[i-1] = fis.checkLine(str, i);
    		String tab = "\t\t";
    		if(i == 6) {
    			tab = "\t";
    		}
    		if(i == 8) {
    			tab = "\t\t\t";
    		}
    		System.out.println(i+" - "+str+" "+tab+"= \t"+inputs[i-1]);
    	}
    	assertEquals("#continue",inputs[0]);
    	assertEquals("#continue",inputs[1]);
    	assertEquals("#title",inputs[2]);
    	assertEquals("#continue",inputs[3]);
    	assertEquals("#continue",inputs[4]);
    	assertEquals("#title",inputs[5]);
    	assertEquals("#continue",inputs[6]);
    	assertEquals("#continue",inputs[7]);
    	assertEquals("#continue",inputs[8]);
    	assertEquals("#return",inputs[9]);
    }
}