/**
 * TestExtractWikiImages
 * Date: 21.04.2017
 * Last Update: 28.04.2017
 * 
 * @author  Han Tek Foo
 */

package com.swt.aprt17.Auto_Slides.Sources.WikiMedia;

import java.util.ArrayList;
import java.util.Arrays;

import junit.framework.TestCase;

public class TestExtractWikiImage extends TestCase {

	/**
	 * TEST 8 Cities GOAL: some cities have 6 images, some return none. Make
	 * sure program still proceeds without prompting error and stop user
	 */
	public void testExpectedInput() {
		System.out.println("\n=================== TEST 1 ===================\n");
		ArrayList<String> cityName = new ArrayList<String>();

		cityName.addAll(
				Arrays.asList("Leipzig", "Vatican City", "Kingston", "London", "Moscow", "Penang",
						"Munich"));

		for (String city : cityName) {
			System.out.print(cityName.get(cityName.indexOf(city)) + "  , ");
			ExtractWikiImage ewi = new ExtractWikiImage(city, 6);
			try {
				//print all images links
				//System.out.println(ewi.scrapImage());
				System.out.println("Total image found: " + ewi.scrapImage().size() + "\n");
			} catch (Exception e1) {
				System.err.println("Wikipedia Error:" + e1);
			}
			try {
				// assertEquals(ewi.scrapImage().size(), 6);

			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * test unexpected input
	 */
	public void testUnexpectedInput() {
		System.out.println("\n=================== TEST 2 ===================\n");
		ArrayList<String> cityName = new ArrayList<String>();

		// it should work for 10 cities
		//Spacing, Umlaut, Underscore, Empty String, Non-City-Name
		cityName.addAll(Arrays.asList("München", "Vatican_City", "Vatican City", "Prai", " ", "lalala"
				,"Moskau", "Weida,_Thuringia"));

		for (String city : cityName) {
			System.out.print(cityName.get(cityName.indexOf(city)) + "  , ");
			ExtractWikiImage ewi = new ExtractWikiImage(city, 6);
			try {
				// System.out.println(ewi.scrapImage());
				System.out.println("Total image found: " + ewi.scrapImage().size() + "\n");
			} catch (Exception e1) {
				System.err.println("Wikipedia Error:" + e1);
			}
			try {
				// assertEquals(ewi.scrapImage().size(), 6);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
}
