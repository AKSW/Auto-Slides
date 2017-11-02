/**
 * CityFinder
 * Date: 29.03.2017
 * search for cites in DBPedia
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

public class CityFinder extends Finder {
	
	/** 
	 * first DBPedia property to sort the results
	 */
	private static final String BIRTH_PLACE = "birthPlace";
	/** 
	 * second DBPedia property to sort the results 
	 */
	private static final String POPULATION_TOTAL = "populationTotal";
	/** 
	 * third DBPedia property to sort the results 
	 */
	private static final String AREA_TOTAL = "areaTotal";
	/** 
	 * identifier for the cities 
	 */
	private static final String COUNTRY = "country";
	/** 
	 * types for city search 
	 */
	public static final String[] TYPES = 
		{ "http://dbpedia.org/ontology/Settlement", 
				"http://dbpedia.org/ontology/City", 
				"http://dbpedia.org/ontology/Town", 
				"http://dbpedia.org/ontology/Village", 
				"http://dbpedia.org/class/yago/City108524735" }; // "http://dbpedia.org/ontology/PopulatedPlace"
	/** 
	 * SPARQL query String
	 */
	private String searchQuery;
	/** 
	 * term for regex filter
	 */
	private String startWithTerm;
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
    public CityFinder(String search) {
    	startWithTerm = "\"^" + search + "\"";
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
  						+ "SELECT DISTINCT ?City ?"+COUNTRY+" WHERE { \n"
  						+ " SELECT ?City (COUNT(DISTINCT ?"+BIRTH_PLACE+") AS ?"+BIRTH_PLACE+"Counter) ?"+POPULATION_TOTAL+" ?"+AREA_TOTAL+" ?"+COUNTRY+" \n"
  						+ " WHERE { \n"
  						+ "  SELECT ?City ?"+BIRTH_PLACE+" \n"
  						+ "         ( IF( BOUND(?"+POPULATION_TOTAL+"Value), str( ?"+POPULATION_TOTAL+"Value ), 'null' ) AS ?"+POPULATION_TOTAL+") \n"
  						+ "         ( IF( BOUND(?"+AREA_TOTAL+"Value), str( round( ?"+AREA_TOTAL+"Value / 1000000) ) , 'null' ) AS ?"+AREA_TOTAL+") \n"
  						+ "         ( IF( BOUND(?"+COUNTRY+"Value), str( ?"+COUNTRY+"Value ), 'null' ) AS ?"+COUNTRY+") \n"
  						+ "  WHERE { \n"
  						+ "         {SELECT ?City WHERE { \n"
  						+ "           { \n";
    	/* adding all the searches for the different types */
  		for(int i = 0; i < TYPES.length; i++){
  			String typeSearch =   "             ?City a <"+TYPES[i]+"> . \n";	/* making sure its the type */		
  					
  			if( i == 0){
  				searchQuery += typeSearch;
  			}
  			else{
  				searchQuery += 	  "           } UNION { \n"
  									+ typeSearch;
  			}				
  		}  
  		
  		searchQuery += 	  "           } \n"
  						+ "          } \n"
  						+ "         } \n"
  						+ "         ?City rdfs:label ?searchableLabel . \n"
						+ "         FILTER (lang(?searchableLabel) = 'en') . \n";	/* only getting the english resources */
		if(startWithTerm.length() > 3) {
			searchQuery += "         FILTER regex(?searchableLabel, "+startWithTerm+", 'i') . \n";	/* only resources that have the wanted start */
		}
  		
  		searchQuery += 	  "         OPTIONAL { ?"+BIRTH_PLACE+" dbo:"+BIRTH_PLACE+" ?City . } \n" /* getting the sort properties */
						+ "         OPTIONAL { ?City dbo:"+POPULATION_TOTAL+" ?"+POPULATION_TOTAL+"Value . } \n"
						+ "         OPTIONAL { ?City dbo:"+AREA_TOTAL+" ?"+AREA_TOTAL+"Value . } \n"
						+ "         OPTIONAL { ?City dbo:"+COUNTRY+" ?"+COUNTRY+"Value . } \n"
  						+ "   } \n"
  						+ " }  \n"
  						+ " GROUP BY ?City ?"+POPULATION_TOTAL+" ?"+AREA_TOTAL+" ?"+COUNTRY+" \n"
  						+ " ORDER BY DESC( ?"+BIRTH_PLACE+"Counter ) DESC( ?"+POPULATION_TOTAL+" ) DESC( ?"+AREA_TOTAL+" ) \n"
  						+ "} \n"
  						+ "LIMIT 3 \n"; /* only yield limited results */
    }
	
    //=====================================================================================================
	
	/**
	 * run the search query
	 */     
    @Override
    public void runSearchQuery() {
    	if(searchQuery == null) { /* MUST get query */
    		System.err.println("ERROR: no query set in method 'runSearchQuery' (CityFinder.java)");
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
			 * also the print format 'City (Country)' is achieved
			 * */
			resultStringArray[i] = cleanLink(resultList.get(i).get("City").toString());
			
			String countryString = resultList.get(i).get(COUNTRY).toString();
			if(countryString.equals("null")) {
				countryString = "";
			}
			else {
				countryString = " (" + cleanLink(countryString) + ")";
			}
			
			resultPrintStringArray[i] = resultStringArray[i] + countryString;
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
