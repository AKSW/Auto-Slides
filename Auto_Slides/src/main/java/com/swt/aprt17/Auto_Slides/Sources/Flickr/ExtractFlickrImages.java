/**
 * ExtractFlickrImages
 * Date: 14.4.2017
 * Last Update: 22.04.2017
 * Extract Images from Flickr.com
 * 6 Images are returned in HashSet
 * Url of the JSON is returned
 * 
 * @author  Han Tek Foo
 */

package com.swt.aprt17.Auto_Slides.Sources.Flickr;

import java.io.IOException;
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

public class ExtractFlickrImages {
	private String cityName;

	/**
	 * constructor
	 * 
	 * @param cityName
	 */
	public ExtractFlickrImages(String cityName) {
		this.cityName = cityName;
	}

	/**
	 * return json by using the Flickr API Key
	 * 
	 * @return json
	 */
	public String getCompleteUrl() {
		String city;
		String flickr = null;
		try {
			city = URLEncoder.encode(cityName, "UTF-8");
			String apiKey = "184d8d119c7807a44afc2be6565f9ede"; // API Key of
																// Flickr

			flickr = "https://api.flickr.com/services/rest/?method=flickr.photos.search" + "&text=" + city // City
																											// Name
																											// from
																											// Constructor
					+ "&format=json" // return JSON API
					+ "&nojsoncallback=1" // remove HEADING in JSON
					+ "&api_key=" + apiKey // API Key of Flickr (required for
											// public
											// search call)
					+ "&extras=url_m" // Show URL in JSON
					+ "&sort=interestingness-desc" // Sort by interesting
					+ "&per_page=6" // 6 results are shown
					+ "&license=1,2,3,4,5,6,7";
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}

		return flickr;
	}

	/**
	 * 6 images in HashSet through JSON
	 * 
	 * @return
	 */
	public HashSet<String> getImagesUrl() {
		HashSet<String> imageLinks = new HashSet<String>();

		try {
			URL url = new URL(getCompleteUrl());
			HttpURLConnection request = (HttpURLConnection) url.openConnection();

			request.connect();
			JsonParser jp = new JsonParser();
			JsonElement root = jp.parse(new InputStreamReader((InputStream) request.getContent()));
			JsonObject query = root.getAsJsonObject();
			JsonArray imageInfo = query.getAsJsonObject("photos").getAsJsonArray("photo");
			for (int i = 0; i < imageInfo.size(); i++) {
				JsonObject allUrl = imageInfo.get(i).getAsJsonObject();
				String imageLink = allUrl.get("url_m").toString().replace("\"", "");
				imageLinks.add(imageLink);
			}
		} catch (IOException e) {
		}
		return imageLinks;
	}
}
