/**
 * ValueGetter
 * Date: 03.04.2017
 * get the values of properties for a resource from DBpedia
 * 
 * @author  Lucas Lange
 */
package com.swt.aprt17.Auto_Slides.Sources.DBPedia;

import java.util.List;

import org.apache.jena.query.QueryExecution;
import org.apache.jena.query.QueryExecutionFactory;
import org.apache.jena.query.QueryFactory;
import org.apache.jena.query.QuerySolution;
import org.apache.jena.query.ResultSet;
import org.apache.jena.query.ResultSetFactory;
import org.apache.jena.query.ResultSetFormatter;

import com.swt.aprt17.Auto_Slides.Sources.DBPedia.Properties.Property;

public class ValueGetter {
	
	/**
	 * DBPedia resource
	 */
	private String resource;
	
	/**
	 * List of DBPedia properties to search for
	 */
	private List<Property> properties;
	
	/**
	 * SPARQL query String
	 */
	private String query;
	
	/**
	 * query results as ResultSet
	 */
	private ResultSet result;
	
	/**
	 * query results as StringArry
	 */
	private String[] resultStringArray;
	
	/**
	 * constructor method
	 * @param resource
	 * @param properties
	 */
	public ValueGetter( String resource, List<Property> properties ) { 
		/* suppress Warning */
		org.apache.log4j.Logger.getRootLogger().setLevel(org.apache.log4j.Level.OFF);
		
		this.resource = resource;
		this.properties = properties;
			
	}      
	
	//=====================================================================================================
	
	/**
	 * create a query as a simple String 
	 * to ask for information from DBpedia
	 * using the SparQL-language
	 */
	public void createQuery() {
		
		if(properties.size() == 0) { /* MUST get property */
			System.err.println("ERROR: no property given in method 'createQuery' (ValueGetter.java)");
			return;
		}	
		/* String needed to trick SPARQL, because it for example won't accept ',' or ')' in a prefixed statement in the query */
		String resourceWithPrefix = "<http://dbpedia.org/resource/" + resource + ">";
		
		/* generic query start with prefixes */
		query =   "PREFIX dbo:          <http://dbpedia.org/ontology/> \n"
				+ "PREFIX prov:         <http://www.w3.org/ns/prov#> \n"
				+ "PREFIX geo:         <http://www.w3.org/2003/01/geo/wgs84_pos#> \n"
				+ "\n"
				+ "SELECT * WHERE { \n"
				+ " { \n";
	
		/* adding all important properties to the query*/
		for( int i = -1; i < properties.size(); i++) { 
			
			if( i == -1 ) { /* getting source used */
				query +=  "       SELECT (IF (BOUND(?wasDerivedFromValue), str(?wasDerivedFromValue), '') AS ?wasDerivedFrom) \n"
						+ "       WHERE { OPTIONAL { "+resourceWithPrefix+" prov:wasDerivedFrom ?wasDerivedFromValue . } \n"
						+ "       } \n";
			}
			else { /* now the given properties */
				
				String select = "       SELECT (IF (BOUND(?"+properties.get(i).getName()+"Value), str(?"+properties.get(i).getName()+"Value), 'null') AS ?"+properties.get(i).getName()+") \n";
				
				if(properties.get(i).getInverse()) { /* 'inverse' property search */
					select +=   "       WHERE { OPTIONAL { ?"+properties.get(i).getName()+"Value "+properties.get(i).getType()+properties.get(i).getName()+" "+resourceWithPrefix+" . \n";
				}
				else { /* non-inverse property search */
					select +=   "       WHERE { OPTIONAL { "+resourceWithPrefix+" "+properties.get(i).getType()+properties.get(i).getName()+" ?"+properties.get(i).getName()+"Value . \n";
				}
				/* making sure to only take the english ones and a specified maximum number (limit) */
				select +=  	    "                          BIND(datatype(?"+properties.get(i).getName()+"Value) AS ?dt) . \n"
							  + "                          FILTER( IF( isliteral(?"+properties.get(i).getName()+"Value) && !bound(?dt), langMatches(lang(?"+properties.get(i).getName()+"Value),'en'), true) ) } \n"
							  + "       } \n"
							  + "       LIMIT 50 \n";
				
				query +=	"   } UNION { \n"	/* using UNION for multiple properties */
						  + select;
			}
		}
		/* end with closing braces */
		query +=			"   } \n"
						  + "} \n";
	}
	
	//=====================================================================================================
	
	/**
	 * use the query to gather information from DBpedia
	 */
	public void runQuery() {
		
		if(query == null) { /* MUST get query */
			System.err.println("ERROR: no query set in method 'runQuery' (ValueGetter.java)");
			return;
		}
		/* Apache Jena libaries are used here */
		QueryExecution exec = QueryExecutionFactory.sparqlService("http://dbpedia.org/sparql", QueryFactory.create(query));

		result = ResultSetFactory.copyResults( exec.execSelect() ); /* copy resultSet to use after QueryExecution closed */
		
		exec.close();
		
		/*
	 	* create a string array containing the query results
	 	*/
		List<QuerySolution> resultList = ResultSetFormatter.toList(result); /* List of query results */
		resultStringArray = new String[resultList.size()];
	
		for( int i = 0; i < resultList.size(); i++ ) {
    		resultStringArray[i] = resultList.get(i).toString();
    	}
		
		/* getting the source in proper form
		 * used 'concat' in query but had problems with sources containing an apostrophe
		 * that ended the string to early
		 *  */
		if(resultStringArray[0].equals("( ?wasDerivedFrom = \"\" )")) { /* if no wiki source found */
			resultStringArray[0] = "http://dbpedia.org/resource/"+resource;
		}
		else { /* 	substring: 21 for '( ?wasDerivedFrom = "', -3 for '" )' */
			resultStringArray[0] = "http://dbpedia.org/resource/"+resource
								+" "
								+ resultStringArray[0].substring(21, resultStringArray[0].length()-3)
								+ "";
		}
	}
	
	//=====================================================================================================
	
	/**
	 * get string array of query results
	 * @return results in StringArray
	 */
	public String[] getResultStringArray() {
		return resultStringArray;
	}
	
	//=====================================================================================================
	
	/**
	 * get query string for tests
	 * @return query string
	 */
	public String getQuery() {
		return query;
	}
	
	//=====================================================================================================
	
	/**
	 * get properties
	 * @return 
	 * @return properties
	 */
	public List<Property> getProperties() {
		return properties;
	}
}