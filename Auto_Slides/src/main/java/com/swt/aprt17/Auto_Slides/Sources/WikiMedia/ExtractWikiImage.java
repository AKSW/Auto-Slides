/**
 * ExtractFlickrImages
 * Date: 14.4.2017
 * Last Update: 22.04.2017
 * 
 * Scrap Images from Wikipedia through JSoup
 * 6 Images are returned in HashSet
 * Url of the Wikimedia page is returned
 * 
 * @author  Han Tek Foo
 */

package com.swt.aprt17.Auto_Slides.Sources.WikiMedia;

import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.HashSet;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

/*
 * This class might not be a good solution for returning 6 images links,
 * Because:
 * I feel like scraping all the img tag is not the optimal solution.
 * 1) because there are repetitions (though can be eliminated through removing px)
 * 2) image size is not consistent
 * 3) image might not be relevant
 * 
 */
public class ExtractWikiImage {

	private String cityName;
	private int numberOfPicture;
	
    /**
     * Create the test case
     * @param cityName
     * @param numberOfPicture
     */
	public ExtractWikiImage(String cityName, int numberOfPicture) {
		this.cityName = cityName;
		this.numberOfPicture = numberOfPicture;
	}
	
	/* If you want to scrap from wikipedia use this link
	*  String url = "https://de.wikipedia.org/wiki/" + cityName;
	*  else just scrap from default and better option, wikimedia which is
	*  mainly used for image storage hub for wikipedia
	*/
	private String completeUrl() {
		String wikimedia = "https://commons.wikimedia.org/wiki/" + cityName;
		return wikimedia;
	}

	/**
	 * HashSet with 6 images
	 * @return
	 */
	public HashSet<String> scrapImage() {
		HashSet<String> images = new HashSet<String>();

		try {
			// Connect to the website and get the html
			Document doc = Jsoup.connect(completeUrl()).get();

			// Get all elements with img tag ,
			Elements img = doc.select("img[src~=(?i)\\.(jpe?g)]");

			int limit = 0;
			for (Element el : img) {
				if (limit < numberOfPicture) {
					String src = el.absUrl("src");
					int width = 500;
					StringBuilder sb = new StringBuilder(src);
					boolean success = false;
					String link = src;
					boolean skip = false;
					while(!success) {
						
						sb.replace(link.lastIndexOf("/")+1, link.indexOf("px-", link.lastIndexOf("/")+1), Integer.toString(width));
						link = sb.toString();
						try {
							new InputStreamReader(new URL(link).openConnection().getInputStream());
							success = true;
						} catch(IOException e) {
							width = width-50;
							if(width < 300) {
								skip = true;
								break;
							}
						}
					}
					if(!skip) {
						images.add(link);
					}
					else {
						continue;
					}
				} else {
					break;
				}
				limit++;
			}
			//System.out.println("Total images found: " + limit);

		} catch (IOException ex) {
			//Logger.getLogger(ExtractWikiImage.class.getName()).log(Level.DEBUG, null, ex);
		}
		return images;
	}
}
