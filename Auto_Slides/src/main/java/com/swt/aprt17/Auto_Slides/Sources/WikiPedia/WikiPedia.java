/**
 * Wikipedia
 * Date: 10.04.2017
 * Last Update: 17.05.2017
 * 
 * Extract table of content and contents of the wikipedia
 * 
 * REASON: dbpedia doesn't provide structural format for wikipedia
 * 
 * @author  Han Tek Foo
 */

package com.swt.aprt17.Auto_Slides.Sources.WikiPedia;

import java.io.IOException;
import java.util.ArrayList;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class WikiPedia {
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
	public WikiPedia(String query) {
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
		String wikipedia = "https://en.wikipedia.org/wiki/" + title;
		return wikipedia;
	}
	
	/**
	 * gets the table of contents of the English Wikipedia site as an ArrayList. 
	 * contains a String[2] for every section title: 
	 * [0] is the print version (e.g. "USS Chicago's survival") 
	 * [1] is the version used in the HTML code (e.g. "USS_Chicago.27s_survival")
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
		} catch (Exception e) { }
		
		return contents;
	}
}
