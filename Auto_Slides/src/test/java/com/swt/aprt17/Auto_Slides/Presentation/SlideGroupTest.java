/**
 * SlideGroupTest
 * Date: 09.04.2017
 * stores slides and the content of them
 * 
 * @author Johannes Braeuer
 */

package com.swt.aprt17.Auto_Slides.Presentation;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * Unit test for simple App.
 */
public class SlideGroupTest extends TestCase {
	
	/**
	 * Create the test case
	 *
	 * @param testName name of the test case
	 */
	public SlideGroupTest(String testName) {
		super(testName);
	}

	/**
	 * @return the suite of tests being tested
	 */
	public static Test suite() {
		return new TestSuite(SlideGroupTest.class);
	}
	
	/**
	 * TEST 1
	 * test, if there is a new Slide created, if there are more than 5 bullets on a slide
	 */
	public void testAutomaticNewSlides(){
		SlideGroup group = new SlideGroup("test");
		
		Set<String> set1 = new HashSet<String>();
		set1.add("http://www.example.de/");
		group.newBulletsSlide("testtitle", Arrays.asList("abc", "def", "ghi", "jkl", "mno", "pqr", "stu", "vwx", "yz"), set1, "headertitle");	//add a slide with 9 bullets, so there should be 2 slides (first with 4, second with 5 bullets)
		
		if(group.getSlideCount() == 2) assertTrue(true);
		else assertTrue(false);
	}
}
