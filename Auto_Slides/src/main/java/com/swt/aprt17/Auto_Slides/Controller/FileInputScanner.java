/**
 * FileInputScanner
 * Date: 06.05.2017
 * reading titles from file
 * 
 * @author  Lucas Lange
 */
package com.swt.aprt17.Auto_Slides.Controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.InvalidPathException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.jena.query.QueryExecution;
import org.apache.jena.query.QueryExecutionFactory;
import org.apache.jena.query.QueryFactory;
import org.apache.jena.query.ResultSet;
import org.apache.jena.query.ResultSetFactory;

import com.swt.aprt17.Auto_Slides.Controller.Finder.BattleFinder;
import com.swt.aprt17.Auto_Slides.Controller.Finder.CityFinder;
import com.swt.aprt17.Auto_Slides.Controller.Finder.Finder;

public class FileInputScanner {
	/*
	 * possible topics
	 */
	private static final String[] TOPICS = { "city", "battle" }; 
	/*
	 * topic of a presentation
	 */
	private String topic;
	/*
	 * title of a presentation
	 */
	private String title;
	/*
	 * path to file
	 */
	private Path path;
	/**
	 * list of [topic and title]s of the presentations the user wants 
	 */
	private List<String[]> listOfTopicTitlePairs = new ArrayList<String[]>();
	
	/**
	 * constructor
	 * creates Path and starts the reading method
	 * @param String path to file
	 */
	public FileInputScanner(String filePath) {
		if(filePath.equals("#test")) { /* for testing */
			return;
		}
		if(filePath == null || filePath.equals("")) {
			System.err.println("No path given.");
			return;
		}
		path = null;
		try {
			path = Paths.get(filePath);
			readInput(); /* try reading from file */
		} catch(InvalidPathException e) {
			System.err.println("Not a valid path: "+e);
		}
	}
	
	/**
	 * reads the file and saves the 
	 * given titles and their topics
	 */
	private void readInput() {
		if(!Files.exists(path)) {
			System.err.println("Not an existing path.");
			return;
		}
		System.out.println("Reading from file.");
		Set<String> setOfMainDataTitlePairs = new HashSet<String>();
		try (BufferedReader reader = Files.newBufferedReader(path)) {
		    String line = null;
		    topic = null;
		    String setPair = null;
		    int lineNumber = 0;
		    /* read every line */
		    while ((line = reader.readLine()) != null) {
		    	lineNumber++;
		    	/* check the line and handle it accordingly */
		    	String lineCheck = checkLine(line, lineNumber);
		    	if(lineCheck.equals("#continue")) {
		    		continue;
		    	}
		    	else if(lineCheck.equals("#return")) {
		    		return;
		    	}
		    	else if(lineCheck.equals("#title")) {
		    		setPair = topic + "~" + title;
		    		/* check if duplicates in set and add if not */
		    		if(setOfMainDataTitlePairs.add(setPair)) {
			        	String[] pair = setPair.split("~");
						listOfTopicTitlePairs.add(pair);
			        }
		    	}
		    }
		    System.out.println("Read "+listOfTopicTitlePairs.size()+" titles.\n");
		} catch (IOException e) {
		    System.err.println("IOException: "+e);
		}
	}
	
	/**
	 * check the meaning of a given line from the file
	 * @param String line from file
	 * @param int lineNumber in file
	 * @return String containing what to do
	 */
	public String checkLine(String line, int lineNumber) {
		/* check the different possible
		 * line setting and return
		 * the the needed 'command'
		 *  */
		if(line.equals("")) { /* empty lines, continue */
        	return "#continue";
        }
        if(line.startsWith("//")) { /* comments, continue */
        	return "#continue";
        }
        if(line.startsWith("#")) { /* topic selections */
        	line = line.substring(1);
        	boolean wrongTopic = true;
        	/* test if topic exits */
        	for(String str : TOPICS) {
        		if(line.equals(str)) {
        			topic = str;
        			wrongTopic = false;
        			break;
        		}
        	}
        	if(wrongTopic) { /* topic doesnt exist, abort */
        		System.err.println("Wrong topic given in file(:"+lineNumber+").");
	        	listOfTopicTitlePairs = null;
	        	return "#return";
        	}
        	else { /* topic exists, continue */
        		return "#continue";
        	}	        	
        }
        if(topic == null) { /* no topic was selected but it is a title, abort */
        	System.err.println("No topic given before title in file(:"+lineNumber+").");
        	return "#return";
        }
        title = line.replaceAll(" ", "_");
        /* first check existence on DBPedia, continue */
        if(!Finder.DBPediaExistenceCheck("http://dbpedia.org/page/"+title)) {
        	System.err.println("Not a working title in file(:"+lineNumber+").");
        	return "#continue";
        } /* then check if the title has the correct topic, continue */
        else if(!checkType()) {
        	System.err.println("Title is no '"+topic+"' in file(:"+lineNumber+").");
        	return "#continue";
        }
        /* it is a working title! */
        return "#title";
	}
	
	/**
	 * test if title has correct topic
	 * @return boolean, true if correct topic for title
	 */
	private boolean checkType() {
		/* get the types from DBPedia */
    	String searchQuery = 	  "PREFIX dbo:          <http://dbpedia.org/ontology/> \n"
    					+ "PREFIX rdfs:         <http://www.w3.org/2000/01/rdf-schema#> \n"
  						+ "PREFIX yago-city:    <http://dbpedia.org/class/yago/City108524735> \n"
  						+ "\n"
  						+ "SELECT ?type WHERE { \n"
						+ "  <http://dbpedia.org/resource/"+title+"> a ?type \n"
  						+ "} \n";	
    	/* Apache Jena libaries are used here */
        QueryExecution exec = QueryExecutionFactory.sparqlService( "http://dbpedia.org/sparql", QueryFactory.create( searchQuery ) );
        ResultSet results = ResultSetFactory.copyResults( exec.execSelect() ); /* copy resultSet to use after QueryExecution closed */
        exec.close();
        String[] types = null;
        /*
		 * pick correct types array
		 */
		if(topic.equals("city")) {
			types = CityFinder.TYPES;
		}
		else if(topic.equals("battle")) {
			types = BattleFinder.TYPES;
		}
        /*
         * go through results and check the types
         */
		while( types != null && results.hasNext() ) {
			String result = results.next().get("type").toString();
			for(String str : types) {
				if(str.equals( result ))  {
					return true; /* it has the correct topic */
				}
			}
		}
		/* not the correct topic */
		return false;
	}
	
	/**
	 * get the topic title pairs as list
	 * @return List<String[]> listOfTopicTitlePairs
	 */
	public List<String[]> getListOfPairs() {
		return listOfTopicTitlePairs;
	}
}
