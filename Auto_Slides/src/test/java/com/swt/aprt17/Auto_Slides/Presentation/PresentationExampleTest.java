/**
 * PresentationExampleTest
 * Date: 28.03.2017
 * test class to show how the .pptx is made
 * 
 * @author Johannes Braeuer
 */

package com.swt.aprt17.Auto_Slides.Presentation;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.swt.aprt17.Auto_Slides.Controller.ZipArchiver.Zip;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

public class PresentationExampleTest extends TestCase {
	
	/**
	 * Create the test case
	 *
	 * @param testName name of the test case
	 */
	public PresentationExampleTest(String testName) {
		super(testName);
	}

	/**
	 * @return the suite of tests being tested
	 */
	public static Test suite() {
		return new TestSuite(PresentationExampleTest.class);
	}
	
	/**
	 * TEST 1
	 * test a custom creation
	 * @throws Exception
	 */
	public void testCreation() throws Exception {
		
		SlideGroup slides = new SlideGroup("xyz");
		PowerPoint pres = new PowerPoint("Leipzig");
		addExampleSlides(slides);

		pres.addSlides(slides);
		Zip zip = new Zip();
		zip.addData("testPres", pres.toByteArray());
		zip.zipCreate();
		pres.close();
	}

	public static void addExampleSlides(SlideGroup slides) throws Exception {
		
		slides.newTitleSlide(
				"Leipzig",
				"City"
				);
		
		Set<String> test1 = new HashSet<String>();
		test1.add("sourceA");
		test1.add("sourceB");
		test1.add("sourceC");
		slides.newIntroPicSlide(
				"Leipzig (/ˈlaɪpsɪɡ/; [ˈlaɪptsɪç]) is the largest city in the federal state of Saxony, Germany. With its population of 544,479 inhabitants (1,001,220 residents in the larger urban zone) Leipzig, one of Germany's top 15 cities by population, is located about 160 kilometers (99 miles) southwest of Berlin at the confluence of the White Elster, Pleisse, and Parthe rivers at the southerly end of the North German Plain.",
				"https://upload.wikimedia.org/wikipedia/commons/f/f3/Leipzig_City_von_Osten.jpg",
				test1
				);
		
		Set<String> test2 = new HashSet<String>();
		test2.add("sourceA");
		test2.add("sourceB");
		test2.add("sourceC");
		slides.newIntroPicSlide(
				"Leipzig (/ˈlaɪpsɪɡ/; [ˈlaɪptsɪç]) is the largest city in the federal state of Saxony, Germany. With its population of 544,479 inhabitants (1,001,220 residents in the larger urban zone) Leipzig, one of Germany's top 15 cities by population, is located about 160 ",
				"https://upload.wikimedia.org/wikipedia/commons/f/f3/Leipzig_City_von_Osten.jpg",
				test2
				);
		
		List<String> test3 = new ArrayList<String>();
		test3.add("History");
		test3.add("zweite");
		test3.add("sources");
		test3.add("vier");
		test3.add("fünf");
		test3.add("sechs");
		test3.add("sieben");
		test3.add("acht");
		test3.add("neun");
		test3.add("zehn");
		slides.newContentsSlide(
				test3
				);
		
		List<String> test4 = new ArrayList<String>();
		test4.add("first note dies ist ein extra langer stichpunkt um den umbruch von stichpunken zu korrigieren");
		test4.add("second note iugasisg dfiuGS HOFUIGp iwfgZWG Fhasid hIAU HSDO UGZap sidhAUOZ GSDOU");
		test4.add("third note UZG UWEZG UUEFDUWZEG UZD UDEZG");
		test4.add("fourth note");
		test4.add("fifth note");
		test4.add("sixth");
		test4.add("seventh");
		Set<String> test5 = new HashSet<String>();
		test5.add("sourceD");
		test5.add("sourceE");
		test5.add("sourceF");
		slides.newBulletsSlide(
				"History",
				test4,
				test5,
				"headertitel!"
				);
		
		Set<String> test7 = new HashSet<String>();
		test7.add("test.de/example");
		test7.add("source zwei");
		slides.newTextSlide(
				"Midget submarine operation",
				"das ist jetzt ein relativ langer text den ich hier schreib und deshalb ist mir schon jetzt etwas langweilig und ich bekomme probleme ideen zu finden, die ich hier auf dieser testfolie verewigen möchte xD okay also was gibts zu erzählen? ich sollte propably mal schauen, wie viele schriftzeichen das jetzt schon sind.. nich dass ich mehr als tausend siebenhundert und fünfzig schreibe, weil das dann mehr wäre, als mein programm maximal supportet derzeit.. also ca bin gleich wieder da. oky bis zum letzten punkt warens so etwa 485",
				test7,
				"headertitel!"
				);
		
		Set<String> test9 = new HashSet<String>();
		test9.add("test.de/example");
		test9.add("source zwei");
		slides.newTextSlide(
				"Thats a Text Slide",
				"das ist jetzt ein relativ langer text den ich hier schreib und deshalb ist mir schon jetzt etwas langweilig und ich bekomme probleme ideen zu finden, die ich hier auf dieser testfolie verewigen möchte xD okay also was gibts zu erzählen? ich sollte propably mal schauen, wie viele schriftzeichen das jetzt schon sind.. nich dass ich mehr als tausend siebenhundert und fünfzig schreibe, weil das dann mehr wäre, als mein programm maximal supportet derzeit.. also ca bin gleich wieder da. oky bis zum letzten punkt warens so etwa 485das ist jetzt ein relativ langer text den ich hier schreib und deshalb ist mir schon jetzt etwas langweilig und ich bekomme probleme ideen zu finden, die ich hier auf dieser testfolie verewigen möchte xD okay also was gibts zu erzählen? ich sollte propably mal schauen, wie viele schriftzeichen das jetzt schon sind.. nich dass ich mehr als tausend siebenhundert und fünfzig schreibe, weil das dann mehr wäre, als mein programm maximal supportet derzeit.. also ca bin gleich wieder da. oky bis zum letzten punkt warens so etwa 485das ist jetzt ein relativ langer text den ich hier schreib und deshalb ist mir schon jetzt etwas langweilig und ich bekomme probleme ideen zu finden, die ich hier auf dieser testfolie verewigen möchte xD okay also was gibts zu erzählen? ich sollte propably mal schauen, wie viele schriftzeichen das jetzt schon sind.. nich dass ich mehr als tausend siebenhundert und fünfzig schreibe, weil das dann mehr wäre, als mein programm maximal supportet derzeit.. also ca bin gleich wieder da. oky bis zum letzten punkt warens so etwa 485",
				test9,
				"headertitel!"
				);
		
		Set<String> test10 = new HashSet<String>();
		test10.add("sourceG");
		test10.add("sourceH");
		test10.add("sourceI");
		slides.newTextPicSlide(
				"Leader",
				"Kurt Summers, Jr.",
				"Social Democratic Party of Germany",
				"Alexander Boris de Pfeffel Johnson (born 19 June 1964) is a British politician, popular historian and journalist who has served as Mayor of London since 2008 and as Member of Parliament (MP) for Uxbridge and South Ruislip since 2015.",
				"https://s-media-cache-ak0.pinimg.com/736x/92/9d/3d/929d3d9f76f406b5ac6020323d2d32dc.jpg",
				test10
				);
		
		Set<String> test11 = new HashSet<String>();
		test11.add("sourceJ");
		test11.add("sourceK");
		test11.add("sourceL");
		slides.newTextPicSlide(
				"Leader",
				"Louis IV, Holy Roman Emperor",
				"Social Democratic Party of Germany",
				"Adolf Hitler (German: [ˈadɔlf ˈhɪtlɐ] ( listen); 20 April 1889 – 30 April 1945) was a German politician who was the leader of the Nazi Party (Nationalsozialistische Deutsche Arbeiterpartei; NSDAP), Chancellor of Germany from 1933 to 1945, and Führer (\"Leader\") of Nazi Germany from 1934 to 1945. As dictator of the German Reich, he initiated World Wa",
				"https://upload.wikimedia.org/wikipedia/commons/thumb/8/81/Bundesarchiv_Bild_183-1987-0820-045%2C_Martina_Hellmann.jpg/650px-Bundesarchiv_Bild_183-1987-0820-045%2C_Martina_Hellmann.jpg",
				test11
				);
		
		Set<String> test12 = new HashSet<String>();
		test12.add("sourceM");
		test12.add("sourceN");
		test12.add("sourceO");
		slides.newTextPicSlide(
				"Leader",
				"Kurt Summers, Jr.",
				"",
				"Alexander Boris de Pfeffel Johnson (born 19 June 1964) is a British politician, popular historian and journalist who has served as Mayor of London since 2008 and as Member of Parliament (MP) for Uxbridge and South Ruislip since 2015.",
				"https://s-media-cache-ak0.pinimg.com/736x/92/9d/3d/929d3d9f76f406b5ac6020323d2d32dc.jpg",
				test12
				);
		
		slides.newPictureSlide(
				"besonders hohes bild",
				"http://pic-style.com/wp-content/uploads/2016/06/PiC_41227.jpg"
				);
		
		slides.newPictureSlide(
				"besonders weites bild",
				"http://www.parkhotel-gunten.swiss/images/content/z_slider_1820_x_640_winter/parkhotel_gunten_0038_Z.jpg"
				);
		
		Set<String> urls = new HashSet<String>();
		urls.add("https://upload.wikimedia.org/wikipedia/commons/3/35/Bundesarchiv_Bild_183-91200-0394%2C_Leipzig%2C_%22Neues_Rathaus%22%2C_Parkpl%C3%A4tze.jpg");
		urls.add("https://cdn.getyourguide.com/niwziy2l9cvz/UClSXcPKy28uS2kGYyi6o/93bcc58a52175f4c270e6c72a41dc4ca/leipzig-city-1112x630__6_.jpg");
		urls.add("http://www.ehrenamtssuche.de/ort/img/leipzig.jpg");
		urls.add("http://www.immobilienbewertung.ws/wp-content/uploads/2012/12/Immobilienbewertung-Leipzig.jpg");
		urls.add("https://media.prinz.de/articles/2016-11/silvester_leipzig_skyline_michael_bader_leipzigtravel_.jpg");
		urls.add("https://upload.wikimedia.org/wikipedia/commons/3/35/Bundesarchiv_Bild_183-91200-0394%2C_Leipzig%2C_%22Neues_Rathaus%22%2C_Parkpl%C3%A4tze.jpg");
		slides.newImpressionSlide(urls, 2);
		
		slides.newImpressionSlide(new HashSet<String>(), 3);
		
		Set<String> test13 = new HashSet<String>();
		test13.add("www.test.com/oiyjsfpiuh");
		test13.add("https://en.wikipedia.org/wiki/Leipzig");
		slides.newSourceSlide(
				"Sources",
				test13
				);
	}
}
