/**
 * Finder
 * Date: 29.03.2017
 * parent class for finders with some utility functions
 * 
 * @author  Lucas Lange
 */
package com.swt.aprt17.Auto_Slides.Controller.Finder;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;

public abstract class Finder {
    
	/**
	 * create the search query
	 */     
    public abstract void createSearchQuery();
    
    /**
	 * run the search query
	 */     
    public abstract void runSearchQuery();
    
    /**
	 * get string array of query results
	 * @return results in StringArray
	 */ 
    public abstract String[] getResultStringArray();
    
    /**
	 * get string array of query results in print format
	 * @return results in print-ready StringArray
	 */ 
    public abstract String[] getResultPrintStringArray();
	
    /**
	 * transforms given Wikipedia links into DBPedia ones
	 * @param wikiLink
	 * @return String DBPedia link
	 */    
    public static String wikiToDBPediaLink(String wikiLink) {
    	/* only wiki links wanted */
    	if(!wikiLink.contains("wikipedia.")) { 
    		System.err.println("ERROR: incompatible link ("+wikiLink+") in method 'wikiToDBPediaLink' (Finder.java)");
			return "";
    	}
    	/* get the Index of the last '/wiki/', needed offset and the end of the link */
    	int slashIndex = wikiLink.lastIndexOf("/wiki/");
    	int offset = 6;
    	int endIndex = wikiLink.length();
    	/* return the newly created DBPedia link */
    	return "http://dbpedia.org/page/" + wikiLink.substring(slashIndex + offset, endIndex);
    }	
   
    /**
	 * cleans given DBPedia or Wikipedia links
	 * @param link from DBPedia or Wikipedia
	 * @return String cleaned link
	 */    
    public static String cleanLink(String link) {
    	/* only DBPedia links wanted */
    	if(link.contains("wikipedia.")) { 
    		link = wikiToDBPediaLink(link);
    	}
    	/* get the Index of '/page/' or '/resource/', needed offset to not take these strings in
    	 * and the wanted end index in the specific situations 
    	 * can not use lastIndexOf("/") because e.g. 'http://dbpedia.org/page/Wünschendorf/Elster' exists
    	 * */
    	int slashIndex = 0;
    	int offset = 0;
    	
    	if(link.contains("/page/")){
    		slashIndex = link.lastIndexOf("/page/");
    		offset = 6;
    	}
    	else if(link.contains("/resource/")){
    		slashIndex = link.lastIndexOf("/resource/");
    		offset = 10;
    		}
    		else { /* the link is not meant to be cleaned here */
    			System.err.println("ERROR: incompatible link ("+link+") in method 'cleanLink' (Finder.java)");
    			return link;
    		}
    	int endIndex = link.length();
    	if(link.startsWith("( ?")) {	/* only used for the format of yielded results which has specific start */
    		endIndex = link.length() - 3; /* -3 for '" )' */
    	}
    	/* return the wanted part */
    	return link.substring(slashIndex + offset, endIndex).replaceAll("_", " ");
    }
	
	/**
	 * checks given DBPedia or Wikipedia links for the existence information
	 * @param urlToCheck from DBPedia or Wikipedia
	 * @return boolean, true when existing
	 */  
    public static boolean DBPediaExistenceCheck(String urlToCheck) {
    	/* only DBPedia links wanted */
    	if(urlToCheck.contains("wikipedia.")) {
    		urlToCheck = wikiToDBPediaLink(urlToCheck);
    	}
    	if(urlToCheck.contains("http://dbpedia.org/page/") || urlToCheck.contains("http://dbpedia.org/resource/")) {
    		try {
    			/* creating the url */
    			URL url = new URL(urlToCheck);
    			/* getting the source code from the page */
    			InputStreamReader isr = new InputStreamReader(url.openConnection().getInputStream());
    			/* read in source code from url and check for existence of information */
    			BufferedReader br = new BufferedReader(isr);
    			String line ="";
    			while((line = br.readLine()) != null)
    			{
    				if(line.contains("No further information is available. (The requested entity is unknown)")) {
    		        	return false;
    		        }
    			}
    			/* close readers */
    			br.close();
    			isr.close();
    		} catch (MalformedURLException e) {
    			return false;
    		} catch (IOException e) {
    			/* source to stream not found */
    			return false;
    		}
            /* existence verified */
    		return true;
    	}
    	else { /* not a working DBPedia or Wikipedia link */
    		return false;
    	}
    }
}
