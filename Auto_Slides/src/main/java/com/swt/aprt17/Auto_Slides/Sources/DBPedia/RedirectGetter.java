/**
 * RedirectGetter
 * Date: 08.04.2017
 * get the link of redirecting query links from DBpedia
 * 
 * @author  Lucas Lange
 */
package com.swt.aprt17.Auto_Slides.Sources.DBPedia;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.SocketTimeoutException;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

public class RedirectGetter {
	
	/**
	 * get the link a thumbnail link
	 * containing query redirects to
	 * @param String queryLink (thumbnail link)
	 */
	public static String getThumbnailLink(String queryLink) {
		/* create a https url and change query to get wanted width */
		String httpsLink = queryLink.replace("http://", "https://").replace("?width=300", "?width=650");
		
		HttpsURLConnection httpsCon;
		String thumbnailURL = "null";
		int timeout = 0;
		
		while(timeout==0 || timeout<5) {
			try {
				/* connect over https url */
				httpsCon = (HttpsURLConnection) new URL( httpsLink ).openConnection();
				/* fires a connect with the query link */
				httpsCon.getInputStream();
				/* after the connect the wanted url is set and gettable */
				thumbnailURL = httpsCon.getURL().toString();
				
				return thumbnailURL;
			} catch (MalformedURLException e) {
				// new URL failed
				if(queryLink.equals("null")) {
					System.err.println("ERROR: \"null\" given in method 'getThumbnailLink' (RedirectGetter.java)");
				}
				else {
					System.err.println("ERROR: link given is no legit url in method 'getThumbnailLink' (RedirectGetter.java)");
				}
				return "null";
			} catch (FileNotFoundException e) {
				// thumbnail does not exist
				return "null";
			} catch (SocketTimeoutException e) {
				// stream connection timeout
				timeout++;
			} catch (IOException e) {
				// stream connection failed
				System.err.println("ERROR: stream connection failed in method 'getThumbnailLink' (RedirectGetter.java)");
				//System.err.println(e);
				return "null";
			}
			
			if(timeout == 5) {
				// five tries are enough
				System.err.println("ERROR: connection timeout in method 'getThumbnailLink' (RedirectGetter.java)");
				return "null";
			}
		}
		
		return thumbnailURL;
	}
}