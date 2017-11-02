/**
 * PowerPointTest
 * Date: 09.04.2017
 * creates a Power Point Presentation from slidegroups
 * 
 * @author Johannes Braeuer
 */

package com.swt.aprt17.Auto_Slides.Presentation;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.poi.xslf.usermodel.XSLFShape;
import org.apache.poi.xslf.usermodel.XSLFSlide;
import org.apache.poi.xslf.usermodel.XSLFTextShape;

import com.swt.aprt17.Auto_Slides.Presentation.Slides.BulletsSlide;
import com.swt.aprt17.Auto_Slides.Presentation.Slides.ContentsSlide;
import com.swt.aprt17.Auto_Slides.Presentation.Slides.IntroPicSlide;
import com.swt.aprt17.Auto_Slides.Presentation.Slides.SourceSlide;
import com.swt.aprt17.Auto_Slides.Presentation.Slides.TextPicSlide;
import com.swt.aprt17.Auto_Slides.Presentation.Slides.TitleSlide;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * Unit test for simple App.
 */
public class PowerPointTest extends TestCase {
	
	/**
	 * Create the test case
	 *
	 * @param testName name of the test case
	 */
	public PowerPointTest(String testName) {
		super(testName);
	}

	/**
	 * @return the suite of tests being tested
	 */
	public static Test suite() {
		return new TestSuite(PowerPointTest.class);
	}

	/**
	 * TEST 1
	 * test, if the added slides get added and counted correctly
	 * @throws IOException 
	 */
	public void testCounter() throws IOException {
		boolean working = true;
		
		PowerPoint pres = new PowerPoint("testtitle");
		SlideGroup slides = new SlideGroup("slidegroupname");
		
		slides.newTitleSlide("abc", "topic");
		Set<String> testset1 = new HashSet<String>();
		testset1.add("http://www.example.de/");
		slides.newIntroPicSlide("def", "http://www.destination-languedoc.de/docs/1665-9-pic-st-loup-herault-le-languedoc.gif", testset1);
		List<String> testlist1 = new ArrayList<String>();
		testlist1.add("ghi");
		slides.newContentsSlide(testlist1);
		Set<String> testset2 = new HashSet<String>();
		testset2.add("http://www.example.de/");
		slides.newTextSlide("jkl", "mno", testset2, "headertitle");
		slides.newPictureSlide("pqr", "http://www.destination-languedoc.de/docs/1665-9-pic-st-loup-herault-le-languedoc.gif");
		
		pres.addSlides(slides);
		
		if(pres.getCount() != 5){
			working = false;
		}

		pres.close();
		
		assertTrue(working);
	}
	
	/**
	 * TEST 2
	 * test, if the content on all used slide-types in a pptx is correct
	 * @throws FileNotFoundException
	 * @throws IOException
	 */
	public void testContent() throws FileNotFoundException, IOException{
		boolean working = true;
		
		PowerPoint pres = new PowerPoint("name");
		
		/*
		 * Date Object to check the String for the Date in the footer
		 */
		Date date = new Date();
		DateFormat df = DateFormat.getDateInstance();

		/*
		 * String list that contains the correct Strings that should appear on the SlideShow
		 */
		List<String> correctStrings = new ArrayList<String>();
		
		pres.addSlide(new TitleSlide("big title", "topic"));
		correctStrings.addAll(Arrays.asList("big title", "topic"));																														//first slide
		correctStrings.addAll(Arrays.asList("", "","Leipzig University | AutoSlides",df.format(date),"Page " + pres.getCount()));														//two blue lines and footer
		
		
		pres.addSlide(new ContentsSlide(Arrays.asList("one", "two", "three"), 1));
		correctStrings.addAll(Arrays.asList("Table of Contents", "one\ntwo\nthree"));																									//second slide
		correctStrings.addAll(Arrays.asList("", "", "Table of Contents", "name", "Leipzig University | AutoSlides",df.format(date),"Page " + pres.getCount()));							//two blue lines, header and footer

		Set<String> testset1 = new HashSet<String>();
		testset1.add("http://www.example.de/");
		pres.addSlide(new IntroPicSlide("test test", "http://www.destination-languedoc.de/docs/1665-9-pic-st-loup-herault-le-languedoc.gif", testset1));
		correctStrings.addAll(Arrays.asList("test test", "http://www.destination-languedoc.de/docs/1665-9-pic-st-loup-herault-le-languedoc.gif"));	//third slide
		correctStrings.addAll(Arrays.asList("", "", "Intro", "name", "Leipzig University | AutoSlides",df.format(date),"Page " + pres.getCount()));		//two blue lines, header and footer
		
		pres.addSlide(new BulletsSlide("titletest", Arrays.asList("text1", "text2", "text3"), testset1, "titletest"));
		correctStrings.addAll(Arrays.asList("titletest", "text1\ntext2\ntext3"));	//fourth slide
		correctStrings.addAll(Arrays.asList("", "", "titletest", "name", "Leipzig University | AutoSlides",df.format(date),"Page " + pres.getCount()));		//two blue lines, header and footer
		
		pres.addSlide(new TextPicSlide("typex", "titlex", "partyx", "textxyz", "http://www.destination-languedoc.de/docs/1665-9-pic-st-loup-herault-le-languedoc.gif", testset1));
		correctStrings.addAll(Arrays.asList("titlex", "partyx", "textxyz", "http://www.destination-languedoc.de/docs/1665-9-pic-st-loup-herault-le-languedoc.gif"));	//fifth slide
		correctStrings.addAll(Arrays.asList("", "", "typex", "name", "Leipzig University | AutoSlides",df.format(date),"Page " + pres.getCount()));		//two blue lines, header and footer
		
		pres.addSlide(new SourceSlide("Sources", Arrays.asList("eins", "zwei", "drei")));
		correctStrings.addAll(Arrays.asList("Sources", "eins\nzwei\ndrei"));	//sixth slide
		correctStrings.addAll(Arrays.asList("", "", "Sources", "name", "Leipzig University | AutoSlides",df.format(date),"Page " + pres.getCount()));		//two blue lines, header and footer

		//==========================================================================
		
		int i = 0;	//index for the current read String
		for (XSLFSlide slide : pres.getSlides()) {
			for (XSLFShape sh : slide.getShapes()) {
				if (sh instanceof XSLFTextShape) {
					XSLFTextShape shape = (XSLFTextShape) sh;
					if(!shape.getText().equals(correctStrings.get(i))){
						working = false;
					}
//					System.out.println(shape.getText());
//					System.out.println(correctStrings.get(i));
					i++;
				}
			}
		}
		pres.close();

		assertTrue(working);
	}
	
	/**
	 * TEST 3
	 * test, if the toByteArray method works by checking if null is returned
	 * @throws IOException
	 */
	public void testToByteArray() throws IOException{
		SlideGroup slides = new SlideGroup("xyz");
		PowerPoint pres = new PowerPoint("zyx");
		
		Set<String> testset1 = new HashSet<String>();
		testset1.add("http://www.example.de/");
		slides.newTextSlide("title", "text", testset1, "headertitle");
		
		pres.addSlides(slides);
		
		assertTrue(pres.toByteArray() != null);
		
		pres.close();
	}
}