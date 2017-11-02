/**
 * Property
 * Date: 27.03.2017
 * Last Update: 19.05.2017
 * 
 * @author  Christian Staudte
 */
package com.swt.aprt17.Auto_Slides.Sources.DBPedia.Properties;

import java.util.ArrayList;
import java.util.HashMap;

public class Property {
	
	protected String name;
	
	/**
	 * name of the property that is used for slides, e.g.: populationTotal -> total population
	 */
	protected String cleanName;
	
	/**
	 * if there are many values, only choose a few (up to 5)
	 */
	protected int maxNumberValues;
	
	/**
	 * type that is used in DBPedia, in front of properties
	 */
	protected String type;
	
	/**
	 * source of information retrieved
	 */
	protected String source;
	
	/**
	 * used for properties that are like: "is p-name of"
	 */
	protected boolean inverse;
	
	/**
	 * deletes link-part of a resource
	 */
	protected boolean cleanLink;
	
	/**
	 * check if values also are resources, if yes -> complex 
	 */
	protected boolean complex = false;
	
	/**
	 * in case a property is complex, it contains properties
	 */
	protected ArrayList<Property> depthSearchProperties;
	
	/**
	 * when dephSearchProperties == null, use simple string array for values
	 */
	protected ArrayList<String> values;
	
	/**
	 * when dephSearchProperties exists, use these values
	 */
	protected HashMap<String, ArrayList<Property>> complexValues;
	
	/**
	 * constructor for properties
	 * @param name
	 * @param inverse
	 */
	public Property(String name, String cleanName, boolean inverse, boolean cleanLink, String type){
		this.name 		= name;
		this.cleanName 	= cleanName;
		this.maxNumberValues = -1;
		this.type = type;
		
		this.cleanLink = cleanLink;
		this.inverse = inverse;
		this.values = new ArrayList<String>();
	}
	
	/**
	 * constructor for complex properties
	 * @param name
	 * @param deepProperties
	 */
	public Property(String name, String cleanName, boolean inverse, boolean cleanLink, ArrayList<Property> deepProperties, String type){
		this.name 		= name;
		this.cleanName 	= cleanName;
		this.maxNumberValues = -1;
		this.type = type;
		
		this.cleanLink = cleanLink;
		this.inverse = inverse;
		
		this.depthSearchProperties = deepProperties;
		this.complexValues = new HashMap<String, ArrayList<Property>>();
		if(deepProperties != null){
			this.complex = true;
		}
	}
	
	/**
	 * constructor for properties with max number
	 * @param name
	 * @param inverse
	 */
	public Property(String name, String cleanName, int maxNumberValues, boolean inverse, boolean cleanLink, String type){
		this.name 				= name;
		this.cleanName 			= cleanName;
		this.maxNumberValues 	= maxNumberValues;
		this.type = type;
		
		this.cleanLink = cleanLink;
		this.inverse = inverse;
		this.values = new ArrayList<String>();
	}
	
	/**
	 * constructor for complex properties with max number
	 * @param name
	 * @param deepProperties
	 */
	public Property(String name, String cleanName, int maxNumberValues, boolean inverse, boolean cleanLink, ArrayList<Property> deepProperties, String type){
		this.name 				= name;
		this.cleanName 			= cleanName;
		this.maxNumberValues 	= maxNumberValues;
		this.type = type;
		
		this.cleanLink = cleanLink;
		this.inverse = inverse;
		
		this.depthSearchProperties = deepProperties;
		this.complexValues = new HashMap<String, ArrayList<Property>>();
		if(deepProperties != null){
			this.complex = true;
		}
	}
	
	/**
	 * special function to copy the complexValues map
	 * @param key
	 */
	public void createDepthSearchproperties(String key){
		complexValues.put(key, copyPropertyList());
	}
	
	/**
	 * special function to copy a property list
	 * @return
	 */
	public ArrayList<Property> copyPropertyList(){
		ArrayList<Property> copyList = new ArrayList<Property>();
		for(Property p : depthSearchProperties){
			Property p2 = p.copyProperty();
			copyList.add(p2);
		}
		return copyList;
	}
	
	/**
	 * special function to copy a normal property
	 * @return
	 */
	protected Property copyProperty(){
		Property p = null;
		if(this.getMaxNumberValues() != -1){
			if(this.getComplex()){
				p = new Property(this.getName(), this.getCleanName(), this.getInverse(), this.getCleanLink(), this.getDepthSearchProperties(), this.getType());
			}else{
				p = new Property(this.getName(), this.getCleanName(), this.getInverse(), this.getCleanLink(), this.getType());
			}
		}else{
			if(this.getComplex()){
				p = new Property(this.getName(), this.getCleanName(), this.getMaxNumberValues(), this.getInverse(), this.getCleanLink(), this.getDepthSearchProperties(), this.getType());
			}else{
				p = new Property(this.getName(), this.getCleanName(), this.getMaxNumberValues(), this.getInverse(), this.getCleanLink(), this.getType());
			}
		}
		return p;
	}
	
	
	
	//==========================================================================
	//Get- and Set Methods 
	public void setComplexProperties(HashMap<String, ArrayList<Property>> complexValues){
		this.complexValues = complexValues;
	}
	public void setValues(ArrayList<String> values){
		this.values = values;
	}
	public void setSource(String source){
		this.source = source;
	}
	
	public boolean getInverse() {
		return inverse;
	}
	public String getName(){
		return name;
	}
	public boolean getCleanLink(){
		return cleanLink;
	}
	public ArrayList<Property> getDepthSearchProperties(){
		return depthSearchProperties;
	}
	public ArrayList<String> getValues(){
		return values;
	}
	public int getMaxNumberValues(){
		return maxNumberValues;
	}
	public String getCleanName(){
		return cleanName;
	}
	public boolean getComplex(){
		return complex;
	}
	public String getSource(){
		return source;
	}
	public String getType(){
		return type;
	}
	public HashMap<String, ArrayList<Property>> getComplexValues(){
		return complexValues;
	}
}
