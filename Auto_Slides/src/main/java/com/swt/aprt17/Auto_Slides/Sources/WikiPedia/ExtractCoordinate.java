/**
 * ExtractCoordinate
 * Date: 20.04.2017
 * Last Update: 17.05.2017
 * 
 * Extract Coordinate from Wikipedia site
 * 
 * @author  Han Tek Foo
 */

package com.swt.aprt17.Auto_Slides.Sources.WikiPedia;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

public class ExtractCoordinate {
	String query;
	String dms1;
	String dms2;
	double lat;
	double lon;

	/**
	 * constructor
	 * 
	 * @param query
	 */
	public ExtractCoordinate(String query) {
		this.query = query;
	}

	/**
	 * return String url of wikipedia page
	 * 
	 * @return
	 */
	public String getCompleteUrl() {
		String url = "https://en.wikipedia.org/wiki/" + query;
		return url;
	}
	
	/**
	 * @return latitude  
	 */
	public double getLat(){
		Document doc = null;
		try {
			doc = Jsoup.connect(getCompleteUrl()).get();

			Elements elements = doc.body().select("span.latitude");	
			dms1 = elements.first().text();
			} catch(Exception e){
				return 361;
			}
		lat = (dmsToDecimalLa(dms1));
		return lat;
	}
	
	/**
	 * @return longitude 
	 */
	public double getLon(){
		Document doc = null;
		try {
			doc = Jsoup.connect(getCompleteUrl()).get();

			Elements elements = doc.body().select("span.longitude");	
			dms2 = elements.first().text();
			} catch(Exception e){
				return 361;
			}

		lon = (dmsToDecimalLon(dms2));
		return lon;
	}
	
	
	/**
	 * convert DMS value to Decimal for Latitude
	 * @return 
	 */
	private double dmsToDecimalLa(String dms){
		double dd = 361;
		double mult = 1;
		//example 51°15'00"N
		String[] decimal = dms.split("°|\\′|″");
		int last = decimal.length;
		
		if(last > 1) {
			String d = decimal[0];	
			dd = Math.signum(Integer.parseInt(d));
			mult = Math.abs(Integer.parseInt(d));
		}
		if(last > 2) {
			String m = decimal[1];
			 mult += (Integer.parseInt(m) / 60.0);
		}
		if(last > 3) {
			String s = decimal[2];
			mult += (Integer.parseInt(s) / 3600.0);
		}
		if(dd != 361) {
			dd *= mult;
			if(decimal[last-1].equals("S")){
				dd*=-1;
			}
		}
		
		return dd;
	}
	
	/**
	 * convert DMS value to Decimal for Longitude
	 * @return 
	 */	
	private double dmsToDecimalLon(String dms){
		double dd = 361;
		double mult = 1;
		//example 51°15'00"N
		String[] decimal = dms.split("°|\\′|″");
		int last = decimal.length;
		
		if(last > 1) {
			String d = decimal[0];	
			dd = Math.signum(Integer.parseInt(d));
			mult = Math.abs(Integer.parseInt(d));
		}
		if(last > 2) {
			String m = decimal[1];
			 mult += (Integer.parseInt(m) / 60.0);
		}
		if(last > 3) {
			String s = decimal[2];
			mult += (Integer.parseInt(s) / 3600.0);
		}
		if(dd != 361) {
			dd *= mult;
			if(decimal[last-1].equals("W")){
				dd*=-1;
			}
		}
		
		return dd;
	}
}
