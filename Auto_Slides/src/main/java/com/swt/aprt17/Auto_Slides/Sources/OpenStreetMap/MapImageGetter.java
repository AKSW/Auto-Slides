/**
 * MapImageGetter
 * Date: 13.04.2017
 * get the image of a Map with mark for given location
 * 
 * @author  Lucas Lange
 */
package com.swt.aprt17.Auto_Slides.Sources.OpenStreetMap;

import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.imageio.ImageIO;

public class MapImageGetter {
	/**
	 * double array containing 
	 * the coordinates of a location
	 * [0] as latitude and [1] as longitude
	 */
	private double[] coords;
	/**
	 * String of last used source
	 */
	private String lastSource;
	
	/**
	 * constructor method with String of location
	 * no space, replace with underscores!
	 * @param String location
	 */
	public MapImageGetter(String location) {
		lastSource = "";
		setCoords( searchOSMCoords(location) );
	}
	
	//=====================================================================================================
	
	/**
	 * constructor method with coordinates of location
	 * @param double[2] containing latitude and longitude
	 */
	public MapImageGetter(double[] coordinates) {
		lastSource = "";
		setCoords( coordinates );
	}
	
	//=====================================================================================================
	
	/**
	 * search for the coordinates of a location
	 * using a tool for openstreetmap.org
	 * @param String location
	*/
	private double[] searchOSMCoords(String location) {
		/*
		 * searching data from openstreetmap.org
		 * using http://nominatim.openstreetmap.org/
		 * 
		 */
			
		double[] searchCoords = new double[2];
		String query = "q";
//		if(citiesOnly) { // if only cities (inconsistent)
//			query += "city";
//		}
		/* url escape sequences */
		location = location.replaceAll("%", "%%").replaceAll("_", "%20");
		/* form search string:  query for location, format as xml, language is english, limit to one result */
		String nominatimString = "http://nominatim.openstreetmap.org/search/"
				+ "?"+query+"="+location+""
				+ "&format=xml"
				+ "&accept-language=en"
				+ "&limit=1";
				
		/* get the coords */
        URL url;
        InputStreamReader isr;
        String line;
        String xml = "";
		try {
			url = new URL(nominatimString);
			/* getting the xml code from the page and search the content */
			isr = new InputStreamReader(url.openConnection().getInputStream());
			BufferedReader br = new BufferedReader(isr);
			while((line = br.readLine()) != null)
			{
				xml += line;
			}
			/* getting lat and lon values using regex */
			Pattern pattern = Pattern.compile("lat='(.*?)' lon='(.*?)'");
			Matcher matcher = pattern.matcher(xml);
			if (matcher.find()) {
				searchCoords[0] = Double.parseDouble( matcher.group(1) ); /* lat */
				searchCoords[1] = Double.parseDouble( matcher.group(2) ); /* lon */
			}
			else {
				return null;
			}
			/* close readers */
			br.close();
			isr.close();
		} catch (MalformedURLException e) {
			System.out.println("Error in in method 'searchCoordinates' (MapImageGetter.java):\n"
					+ e);
			return null;
		} catch (IOException e) {
			System.out.println("Error in in method 'searchCoordinates' (MapImageGetter.java):\n"
					+ e);
			return null;
		} 
		
		return searchCoords;
	}
	
	//=====================================================================================================
	
	/**
	 * get the byte[] of the map image
	 * @return byte[] of map image
	*/
	public byte[] getMapImage() {
		/*
		 * map data from openstreetmap.org
		 * using github.com/dfacts/staticmaplite 
		 * found on wiki.openstreetmap.org/wiki/Static_map_images
		 * 
		 */
		
		byte[] imageBytes = null;
		String mapImageLink = "";
		
		/* no cordinates, no image  */
		if(coords == null) { 
			imageBytes = null;
		}
		else {
			try {
				/* prepare map image link */
				String coordsString = coords[0] + "," + coords[1];
				mapImageLink = 
						"http://staticmap.openstreetmap.de/staticmap.php?zoom=4&size=700x500&center="+coordsString+"&markers="+coordsString+",ol-marker";
				/* try to read image from url into buffer */
				URL mapImageURL = new URL(mapImageLink);
				BufferedImage bufferedImage = ImageIO.read(mapImageURL);
				/* get the image as byte[] */
				ByteArrayOutputStream baos = new ByteArrayOutputStream();
				ImageIO.write(bufferedImage, "jpg", baos );
				imageBytes = baos.toByteArray();
			
			} catch (MalformedURLException e) {
				System.err.println("ERROR: in method 'getMapImage' (MapImageGetter.java): " + e);
				imageBytes = null;
			} catch (IOException e) {
				System.err.println("ERROR: in method 'getMapImage' (MapImageGetter.java): " + e);
				imageBytes = null;
			}
		}
		
		/* setting last source */
		if(imageBytes == null && !lastSource.equals("")) {
			lastSource = "";
		}
		else if(imageBytes != null) {
			lastSource = mapImageLink;
		}
		
		return imageBytes;
	}
	
	//=====================================================================================================
	
	/**
	 * set coordinates of the location
	 * @param double[2] containing latitude and longitude
	*/
	private void setCoords(double[] coords) {
		if( coords != null && coords.length > 2 ) {
			System.err.println("ERROR: too many coordinates given in method 'setCoords' (MapImageGetter.java): ");
			this.coords = null;
		}
		else {
			this.coords = coords;
		}
	}
	
	//=====================================================================================================
	
	/**
	 * get currently set coordinates
	 * @return double[2] containing latitude and longitude
	*/
	public double[] getCoords() {
		return coords;
	}
	
	//=====================================================================================================
	
	/**
	 * get the source of the last created image
	 * @return String lastSource
	*/
	public String getLastSource() {
		return lastSource;
	}
}
