/**
 * TestSimplePedia
 * Date: 21.04.2017
 * Last Update: 28.04.2017
 * 
 * @author  Han Tek Foo
 */
package com.swt.aprt17.Auto_Slides.Sources.SimplePedia;

import java.util.ArrayList;

import junit.framework.TestCase;

public class TestSimplePedia extends TestCase {
	/**
	 * TEST 1: ArrayList<String[]> getTableOfContent() 
	 *  
	 * test if
	 * [0] is the print version (e.g. "See also") 
	 * [1] is the version used in the HTML code (e.g. "See_also")
	 */
	public void testGetTableOfContent() {
		SimplePedia sp1 = new SimplePedia("Leipzig");
		ArrayList<String[]> toc1 = sp1.getTableOfContent();
		
		assertTrue(toc1.size() > 0);
		
		for (int i = 0 ; i < toc1.size() ; i++) {
			if(toc1.get(i)[0].equals("Other websites")) {
				
				assertTrue(toc1.get(i)[1].equals("Other_websites"));
				break;
			}
		}
		
		SimplePedia sp2 = new SimplePedia("this is not a page");
		ArrayList<String[]> toc2 = sp2.getTableOfContent();
		
		assertTrue(toc2.size() == 0);
	}
	
	/**
	 * Test 2: String getContent(String key)
	 * 
	 * test if it gets a String when there is content 
	 */
	public void testGetContent() {
		SimplePedia sp = new SimplePedia("Leipzig");
		String content = sp.getContent("History");
		
		assertFalse(content.equals(""));
	}
}
