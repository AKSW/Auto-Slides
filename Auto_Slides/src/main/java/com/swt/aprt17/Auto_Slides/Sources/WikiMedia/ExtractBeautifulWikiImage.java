/**
 * ExtractBeautifulWikiImage
 * Date: 10.04.2017
 * Last Update: 22.04.2017
 * 
 * Extract images links from JSON API from Wikipedia
 * 
 * @author  Han Tek Foo
 */

package com.swt.aprt17.Auto_Slides.Sources.WikiMedia;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashSet;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class ExtractBeautifulWikiImage {
	private String cityName;
	private int numberOfPicture;
	private int imageWidth;
	private int imageHeight;

	/**
	 * constructor
	 * 
	 * @param cityName
	 * @param numberOfPicture
	 * @param imageWidth
	 * @param imageHeight
	 */
	public ExtractBeautifulWikiImage(String cityName, int numberOfPicture, int imageWidth, int imageHeight) {
		this.cityName = cityName;
		this.numberOfPicture = numberOfPicture;
		this.imageWidth = imageWidth;
		this.imageHeight = imageHeight;
	}

	/**
	 * return String of the JSON Url of Wikipedia
	 * 
	 * @return
	 */
	public String getCompleteUrl() {
		
		String city;
		String wikipedia = null;
		try {
			//parse city name into URL encoded utf 8, for Münich or Vatican City (Spacing)
			city = URLEncoder.encode(cityName, "UTF-8");
			// iiurlwidth means width of image
			wikipedia = "https://en.wikipedia.org/w/api.php?action=query&generator=images&titles=" + city
					+ "&prop=imageinfo&&iiprop=url&iiurlwidth=" + imageWidth + "&iiurlheight=" + imageHeight
					+ "&format=json";
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return wikipedia;
	}

	/**
	 * return 6 images in HashSet
	 * contains JSON Object and JSON Array
	 * @return
	 * @throws Exception
	 */

	public HashSet<String> getImagesUrl() {
		HashSet<String> imageLinks = new HashSet<String>();
		try{
		URL url = new URL(getCompleteUrl());

		HttpURLConnection request = (HttpURLConnection) url.openConnection();
		request.connect();

		JsonParser jp = new JsonParser();
		JsonElement root = jp.parse(new InputStreamReader((InputStream) request.getContent()));

		JsonObject query = root.getAsJsonObject();

		numberOfPicture *= -1;

		//start with picture number 2 because sometimes no.1 and no.2 has the same image despite slightly different links
		for (int i = -2; i >= numberOfPicture - 1; i--) {
			JsonObject imageInfo = query.getAsJsonObject("query").getAsJsonObject("pages");
			
			// if there is no image, proceed. GOAL: avoid NullPointer Exception
			if (imageInfo.getAsJsonObject().has(Integer.toString(i))) {
				JsonArray image = imageInfo.getAsJsonObject(Integer.toString(i)).getAsJsonArray("imageinfo");
				JsonObject allUrl = image.get(0).getAsJsonObject();

				String imageLink = allUrl.get("thumburl").toString().replace("\"", "");
				imageLinks.add(imageLink);
			}
		}
		} catch(Exception e){
			
		}
		return imageLinks;
	}
}
