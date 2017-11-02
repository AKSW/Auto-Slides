/**
 * TestExtractFlickrImages
 * Date: 20.04.2017
 * Last Update: 28.04.2017
 * 
 * @author  Han Tek Foo
 */

package com.swt.aprt17.Auto_Slides.Sources.Flickr;

import java.util.ArrayList;
import java.util.Arrays;

import junit.framework.TestCase;

public class TestExtractFlickrImages extends TestCase {

	/**
	 * TEST 1: should return 6 images links.
	 */
	public void testExpectedInput() {
		System.out.println("\n=================== TEST 1 ===================\n");
		ArrayList<String> cityName = new ArrayList<String>();

		cityName.addAll(Arrays.asList("Leipzig", "Vatican_City", "Vatican City", "Kingston", "London", "Moscow", 
				"Moskau", "Penang", "Munich", "Copenhagen"));
	
		for (String city : cityName) {
			System.out.print((city +  "  , "));
			ExtractFlickrImages efi = new ExtractFlickrImages(city);
			try{
				System.out.println("Number of images found: " + efi.getImagesUrl().size());
				assertNotNull(efi.getImagesUrl());
			} catch (Exception e){
				System.err.println("Only show in TEST: " + e);
			}
		}
	}
	/**
	 * test unexpected input / city name
	 */
	public void testUnexpectedInput(){
		System.out.println("\n=================== TEST 2 ===================\n");
		ArrayList<String> cityName = new ArrayList<String>();
		cityName.addAll(Arrays.asList("München", "Vatican_City", "Prai", " ", "lalala", "Weida,_Thuringia"));

		for (String city : cityName) {
			System.out.print((city +  "  , "));
			ExtractFlickrImages efi = new ExtractFlickrImages(city);			try{
				System.out.println("Number of images found: " + efi.getImagesUrl().size());
				assertNotNull(efi.getImagesUrl());
			} catch (Exception e){
				System.err.println("Only show in TEST: " + e);
			}
		}
	}
}
