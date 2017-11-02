package com.swt.aprt17.Auto_Slides.Sources.WikiPedia;

import com.swt.aprt17.Auto_Slides.Sources.WikiPedia.ExtractCoordinate;

import junit.framework.TestCase;

public class TestExtractCoordinate extends TestCase {

	public void testLeipzig(){
		ExtractCoordinate bc = new ExtractCoordinate("Siege_of_Riga_(1812)");
		System.out.println(bc.getCompleteUrl());
		System.out.println(bc.getLat());
		System.out.println(bc.getLon());			
	}
}
