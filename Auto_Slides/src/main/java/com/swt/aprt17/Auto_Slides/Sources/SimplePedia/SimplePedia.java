/**
 * SimplePedia
 * Date: 10.04.2017
 * Last Update: 22.04.2017
 * 
 * Extract table of content and contents of the simple english
 * version of wikipedia
 * 
 * REASON: dbpedia doesn't provide structural format for simple english
 * wikipedia
 * 
 * @author  Han Tek Foo
 */

package com.swt.aprt17.Auto_Slides.Sources.SimplePedia;

import java.io.IOException;
import java.util.ArrayList;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class SimplePedia {
	/**
	 * the wikipedia page HTML document
	 */
	private Document doc;
	
	/**
	 * the page title
	 */
	private String title;

	/**
	 * constructor
	 * 
	 * @param query
	 */
	public SimplePedia(String query) {
		this.title = query;
		try {
			this.doc = Jsoup.connect(getCompleteUrl()).get();
		} catch (IOException e) { }
	}

	/**
	 * return link of the simplePedia
	 * 
	 * @return
	 */
	protected String getCompleteUrl() {
		// Simple english wikipedia
		String url = "https://simple.wikipedia.org/wiki/" + title;
		return url;
	}
	
	/**
	 * gets the table of contents of the English SimplePedia site as an ArrayList. 
	 * contains a String[2] for every section title: 
	 * [0] is the print version (e.g. "Secondary missions") 
	 * [1] is the version used in the HTML code (e.g. "Secondary_missions")
	 * 
	 * @return the table of contents
	 */
	protected ArrayList<String[]> getTableOfContent() {
		ArrayList<String[]> tableOfContent = new ArrayList<String[]>();

		try {
			Elements sectionTitles = doc.select("span.mw-headline");
			
			for (int i = 0 ; i < sectionTitles.size() ; i++) {
				String[] sectionTitle = new String[2];
				sectionTitle[0] = sectionTitles.get(i).text();
				sectionTitle[1] = sectionTitles.get(i).id();
				tableOfContent.add(sectionTitle);
			}
		} catch (Exception e) {	}
		
		return tableOfContent;
	}
	
	/**
	 * This is the essence of this class First, it returns the content of the
	 * page, by category, we call it @param key then the second part search for
	 * the bullet point content
	 * 
	 * @return
	 */
	protected String getContent(String key) {
		String contents = "";
		
		try {			
			// text
			Elements elements = doc.select("span.mw-headline, h2~p");

			boolean inContentSection = false;
			for (Element elem : elements) {
				if (elem.className().equals("mw-headline")) {
					// It's a headline
					inContentSection = elem.id().equals(key);
				} else {
					// It's a paragraph
					if (inContentSection) {
						// get the String content and replace [1] number
						contents += elem.text().replaceAll("\\[\\d+\\]", "") + " ";
					}
				}
			}
			
			// bullet points
			Elements elementss = doc.select("span.mw-headline, h2 ~ p ~ ul li, h2 ~ p ~ dl dd");
			
			for (Element elemm : elementss) {
				{
					if (elemm.className().equals("mw-headline")) {
						// It's a headline
						inContentSection = elemm.id().equals(key);
					} else {
						// It's a paragraph
						if (inContentSection) {
							contents += elemm.text().replaceAll("\\[\\d+\\]", "") + ". ";
						}
					}
				}
			}
			
		} catch (Exception e) { }
		
		return contents;
	}
}
