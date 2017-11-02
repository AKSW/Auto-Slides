/**
 * ExtractBattleMap
 * Date: 20.04.2017
 * Last Update: 17.05.2017
 * 
 * Extract battle map from wikipedia site
 * 
 * @author  Han Tek Foo
 */

package com.swt.aprt17.Auto_Slides.Sources.WikiPedia;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.HashSet;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class ExtractBattleMap {
	String query;
	
	/**
	 * constructor
	 * 
	 * @param query
	 */
	public ExtractBattleMap(String query) {
		this.query = query;
	}
	
	/**
	 * return link of the Wikipedia
	 * 
	 * @return
	 */
	public String getCompleteUrl() {
		String battle;
		String wikipedia = "";
		try {
			/** decode so that spacing in 
			 * @parameter query
			 * will change to underscore _
			 */
			battle = URLDecoder.decode(query, "UTF-8");
			wikipedia = "https://en.wikipedia.org/wiki/" + battle;

		} catch (UnsupportedEncodingException e) {
			System.err.println("Invalid url");
		}
		return wikipedia;
	}
	
	
	/**
	 * return url of the battle map
	 * 
	 * @return
	 */
	public HashSet<String> getMap() {
		HashSet<String> images = new HashSet<String>();

		Document doc;
		try {
			doc = Jsoup.connect(getCompleteUrl()).get();
			Elements img = doc.select("img[src~=(?i)\\.(svg)|\\.(png)|\\.(gif)]");
			
			for (Element el : img) {
				String src = el.absUrl("src");

				if (src.toLowerCase().contains("map")
						|| src.toLowerCase().contains("battle")) {
					String optiWidth = getOptimalWidth(el.attr("width"), el.attr("height")) + "px";
					src = src.replaceAll("\\d+px", optiWidth);
					images.add(src);
				}
			}
		} catch (IOException e) {
			System.err.println("Wikipedia url error");
		}
		return images;
	}
	
	/**
	 * Calculates the maximal width an image can have to still fit onto a picture slide. 
	 * The numbers for maxWidth (684) and maxHeight (356) are taken from 
	 * Presentation.Slides.PictureSlide. Yes, this is bad style, but this was a last 
	 * minute fix, so I'll leave it as it is.
	 * @param height
	 * @param width
	 * @return
	 */
	private String getOptimalWidth(String height, String width) {
		int h = Integer.parseInt(height);
		int w = Integer.parseInt(width);
		
		int wScaled = 356/h * w;
		
		String result = "";
		if(wScaled < w)	return result += wScaled;
		else 			return result += 684;
	}
}
