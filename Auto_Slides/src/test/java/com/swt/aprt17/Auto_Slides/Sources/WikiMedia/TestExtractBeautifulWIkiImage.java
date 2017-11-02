/**
 * TestExtractBeautifulWikiImages
 * Date: 21.04.2017
 * Last Update: 28.04.2017
 * 
 * @author  Han Tek Foo
 */

package com.swt.aprt17.Auto_Slides.Sources.WikiMedia;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;

import junit.framework.TestCase;

public class TestExtractBeautifulWIkiImage extends TestCase {

	/**
	 * City Name with normal characters (Expected input) TEST 1: should return 6
	 * images links.
	 */
	public void testExpectationInput() {
		System.out.println("\n=================== Test 1 ===================\n");
		System.out.println("\n=================== GENERAL TEST 8 Cities ===================\n");
		HashSet<String> citySet = new HashSet<String>();
		ArrayList<String> cityName = new ArrayList<String>(citySet);


		// it should not throw error messages for 8 cities

		cityName.addAll(Arrays.asList("Leipzig", "Vatican_City", "Vatican City", "Kingston", "London", "Moscow", 
				"Moskau", "Penang", "Munich", "Copenhagen"));

		for (String city : cityName) {
			System.out.print(cityName.get(cityName.indexOf(city)) + "  , ");

			ExtractBeautifulWikiImage ebwi = new ExtractBeautifulWikiImage(city, 6, 300, 300);
			try {

				System.out.println("Number of images found: " + ebwi.getImagesUrl().size());
				System.out.println("Link of image: " + ebwi.getCompleteUrl());

				// Check for Null Exception
				assertNotNull(ebwi.getImagesUrl());
			} catch (Exception e1) {
				System.err.println("Wikipedia Error:" + e1);
			}
		}
	}
	
	/**
	 * test unexpected input
	 */
	public void testUnexpectedInput() {
		System.out.println("\n=================== Test 2 ===================\n");
		System.out.println("\n=================== CITY NAME WITH SPACING, SMALL Cities AND SPECIAL CHARACTER===================\n");
		HashSet<String> citySet = new HashSet<String>();

		ArrayList<String> cityName = new ArrayList<String>(citySet);

		// it should not throw error messages for 8 cities

		cityName.addAll(Arrays.asList("München", "Vatican_City", "Prai", " ", "lalala", "Weida,_Thuringia"));
		
		for (String city : cityName) {
			System.out.print(cityName.get(cityName.indexOf(city)) + "  , ");

			ExtractBeautifulWikiImage ebwi = new ExtractBeautifulWikiImage(city, 6, 300, 300);
			try {

				System.out.println("Number of images found: " + ebwi.getImagesUrl().size());
				System.out.println("Link of image: " + ebwi.getCompleteUrl());

				// Check for Null Exception
				assertNotNull(ebwi.getImagesUrl());
			} catch (Exception e1) {
				System.err.println("Wikipedia Error:" + e1);
			}
		}
	}
	
	/**
	 * test less than 6 images
	 */
	public void testLessThan6Images() {
		System.out.println("\n=================== Test 3 ===================\n");
		System.out.println("\n=================== CITY WITH LESS THAN 6 IMAGES===================\n");
		
		HashSet<String> citySet = new HashSet<String>();

		ArrayList<String> cityName = new ArrayList<String>(citySet);

		// it should not throw error messages for 8 cities

		cityName.addAll(Arrays.asList("Battle_of_Quipaipan"));
		
		for (String city : cityName) {
			System.out.print(cityName.get(cityName.indexOf(city)) + "  , ");

			ExtractBeautifulWikiImage ebwi = new ExtractBeautifulWikiImage(city, 6, 300, 300);
			try {

				System.out.println("Number of images found: " + ebwi.getImagesUrl().size());
				// Check for Null Exception
				assertNotNull(ebwi.getImagesUrl());
			} catch (Exception e1) {
				System.err.println("Wikipedia Error:" + e1);
			}
		}
	}
}
