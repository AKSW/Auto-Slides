package com.swt.aprt17.Auto_Slides.Sources.DBPedia;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * Unit test for simple App.
 */
public class RedirectGetterTest extends TestCase{
	
    /**
     * Create the test case
     *
     * @param testName name of the test case
     */
    public RedirectGetterTest( String testName ){
        super( testName );
    }

    /**
     * @return the suite of tests being tested
     */
    public static Test suite(){
        return new TestSuite( RedirectGetterTest.class );
    }
    
    /**
     * TEST 1
     * tests if bad url is handled properly
     */
    public void testBadURL(){
    	String redirectNull = "null";
		String redirectBad = "commons.wikimedia.org/wiki/";

		String nullThumb = RedirectGetter.getThumbnailLink(redirectNull);
		String badThumb = RedirectGetter.getThumbnailLink(redirectBad);
		
		assertEquals("null", nullThumb);
		assertEquals("null", badThumb);
    }
    
    /**
     * TEST 2
     * tests if correct redirects are returned
     */
    public void testForCorrectThumbnailRedirects(){
    	String redirectLeipzig = "http://commons.wikimedia.org/wiki/Special:FilePath/Flag_of_Leipzig.svg?width=300";
		String redirectParis = "http://commons.wikimedia.org/wiki/Special:FilePath/Paris_montage.jpg?width=300";
		String redirectBerlin = "http://commons.wikimedia.org/wiki/Special:FilePath/Coat_of_arms_of_Berlin.svg?width=300";
		
		String redirectedLeipzig = "https://upload.wikimedia.org/wikipedia/commons/thumb/f/f4/Flag_of_Leipzig.svg/650px-Flag_of_Leipzig.svg.png";
		String redirectedParis = "https://upload.wikimedia.org/wikipedia/commons/thumb/6/62/Paris_montage.jpg/650px-Paris_montage.jpg";
		String redirectedBerlin = "https://upload.wikimedia.org/wikipedia/commons/thumb/d/d9/Coat_of_arms_of_Berlin.svg/650px-Coat_of_arms_of_Berlin.svg.png";
		
		String leipzigThumb = RedirectGetter.getThumbnailLink(redirectLeipzig);
		String parisThumb = RedirectGetter.getThumbnailLink(redirectParis);
		String berlinThumb = RedirectGetter.getThumbnailLink(redirectBerlin);
		
		assertTrue(leipzigThumb.equals(redirectedLeipzig));
		assertTrue(parisThumb.equals(redirectedParis));
		assertTrue(berlinThumb.equals(redirectedBerlin));
    }

}
