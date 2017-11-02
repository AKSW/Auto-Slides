package com.swt.aprt17.Auto_Slides.Sources.WikiPedia;

import java.util.ArrayList;

import junit.framework.TestCase;

public class TestWikiPedia extends TestCase {
	
	/**
	 * TEST 1: ArrayList<String[]> getTableOfContent() 
	 *  
	 * test if
	 * [0] is the print version (e.g. "See also") 
	 * [1] is the version used in the HTML code (e.g. "See_also")
	 */
	public void testGetTableOfContent() {
		WikiPedia wp = new WikiPedia("Leipzig");
		ArrayList<String[]> toc = wp.getTableOfContent();
		
		assertTrue(toc.size() > 0);
		
		for (int i = 0 ; i < toc.size() ; i++) {
			if(toc.get(i)[0].equals("See also")) {
				
				assertTrue(toc.get(i)[1].equals("See_also"));
				break;
			}
		}
		
		WikiPedia wp2 = new WikiPedia("this is not a page");
		ArrayList<String[]> toc2 = wp2.getTableOfContent();
		
		assertTrue(toc2.size() == 0);
	}
	
	/**
	 * Test 2: String getContent(String key)
	 * 
	 * test if it gets a String when there is content 
	 */
	public void testGetContent() {
		WikiPedia wp = new WikiPedia("Leipzig");
		String content = wp.getContent("Name");
		
		assertFalse(content.equals(""));
	}
}
