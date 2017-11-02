/**
 * BattleFinder
 * Date: 02.05.2017
 * search for battles in DBPedia
 * 
 * @author  Lucas Lange
 */
package com.swt.aprt17.Auto_Slides.Controller.Finder;

import java.util.List;

import org.apache.jena.query.QueryExecution;
import org.apache.jena.query.QueryExecutionFactory;
import org.apache.jena.query.QueryFactory;
import org.apache.jena.query.QuerySolution;
import org.apache.jena.query.ResultSet;
import org.apache.jena.query.ResultSetFactory;
import org.apache.jena.query.ResultSetFormatter;

import com.swt.aprt17.Auto_Slides.Topics.SlideFactories.SlideFactory;

public class BattleFinder extends Finder {
	/**
	 * DBPedia property to sort the results
	 */
	private static final String CASUALTIES = "casualties";
	/* 
	 * identifier for the battles 
	 */
	private static final String DATE = "date";
	/** 
	 * types for battle search 
	 */
	public static final String[] TYPES = 
		{ "http://dbpedia.org/class/yago/Battle100953559" }; // "http://dbpedia.org/ontology/MilitaryConflict"
	/** 
	 * SPARQL query String
	 */
	private String searchQuery;
	/** 
	 * term for regex filter
	 */
	private String containsTerm;
	/** 
	 * query results as ResultSet
	 */
	private ResultSet result;
	/** 
	 * query results as StringArry
	 */
	private String[] resultStringArray;
	/** 
	 * query results as StringArry for printing
	 */
	private String[] resultPrintStringArray;
	
	/**
	 * constructor method
	 * @param String search term 
	 */    
    public BattleFinder(String search) {
    	containsTerm = "\"" + search + "\"";
    }
	
    //=====================================================================================================
	
	/**
	 * create the search query
	 */     
    @Override
    public void createSearchQuery() {
    	/* generic query start with prefixes and properties that are to be selected */
    	searchQuery = 	  "PREFIX dbo:          <http://dbpedia.org/ontology/> \n"
    					+ "PREFIX rdfs:         <http://www.w3.org/2000/01/rdf-schema#> \n"
  						+ "\n"
  						+ "SELECT ?Battle ?"+DATE+" \n"
  						+ "WHERE { \n"
  						+ " SELECT ?Battle ?"+CASUALTIES+" \n"
  						+ "        ( IF( BOUND(?"+DATE+"Value), str( ?"+DATE+"Value ), 'null' ) AS ?"+DATE+") \n"
  						+ " WHERE { \n"
  						+ "        {SELECT ?Battle WHERE { \n"
  						+ "          { \n";
    	/* adding all the searches for the different types */
  		for(int i = 0; i < TYPES.length; i++){
  			String typeSearch =   "            ?Battle a <"+TYPES[i]+"> . \n";	/* making sure its the type */		
  					
  			if( i == 0){
  				searchQuery += typeSearch;
  			}
  			else{
  				searchQuery += 	  "          } UNION { \n"
  									+ typeSearch;
  			}				
  		}  
  		
  		searchQuery += 	  "          } \n"
  						+ "         } \n"
  						+ "        } \n"
  						+ "        ?Battle rdfs:label ?searchableLabel . \n"
						+ "        FILTER (lang(?searchableLabel) = 'en') . \n";	/* only getting the english resources */
		if(containsTerm.length() > 3) {
			searchQuery += "        FILTER regex(?searchableLabel, "+containsTerm+", 'i') . \n";	/* only resources that contain the wanted term */
		}
  		
  		searchQuery += 	  "        OPTIONAL { ?Battle dbo:"+DATE+" ?"+DATE+"Value . } \n"
  						+ "  } \n"
  						+ "}  \n"
  						+ "ORDER BY DESC( ?"+CASUALTIES+"Sum ) \n"
  						+ "LIMIT 3 \n"; /* only yield limited results */
    }
	
    //=====================================================================================================
	
	/**
	 * run the search query
	 */     
    @Override
    public void runSearchQuery() {
    	if(searchQuery == null) { /* MUST get query */
    		System.err.println("ERROR: no query set in method 'runSearchQuery' (BattleFinder.java)");
    		return;
    	}
    	/* Apache Jena libaries are used here */
        QueryExecution exec = QueryExecutionFactory.sparqlService( "http://dbpedia.org/sparql", QueryFactory.create( searchQuery ) );

        result = ResultSetFactory.copyResults( exec.execSelect() ); /* copy resultSet to use after QueryExecution closed */
        
        exec.close();
        
        /*
	 	* create a string array containing the query results
	 	*/
        
		List<QuerySolution> resultList = ResultSetFormatter.toList(result); /* List of query results */
		
		resultStringArray = new String[resultList.size()];
		resultPrintStringArray = new String[resultStringArray.length];
		
		for( int i = 0; i < resultStringArray.length; i++ ) {
			/* results are links, that get cleaned here 
			 * also the print format 'Battle (Date)' is achieved
			 * */
			resultStringArray[i] = cleanLink(resultList.get(i).get("Battle").toString());
			
			String dateString = resultList.get(i).get(DATE).toString();
			if(dateString.equals("null")) {
				dateString = "";
			}
			else {
				dateString = " (" + SlideFactory.formatDate(dateString) + ")";
			}
			resultPrintStringArray[i] = resultStringArray[i] + dateString;
		}
    }
	
    //=====================================================================================================
	
	/**
	 * get string array of query results
	 * @return results in StringArray
	 */ 
    @Override
	public String[] getResultStringArray(){
		return resultStringArray;
	}
	
    //=====================================================================================================
	
	/**
	 * get string array of query results in print format
	 * @return results in print-ready StringArray
	 */ 
	@Override
	public String[] getResultPrintStringArray(){
		return resultPrintStringArray;
	}
}
