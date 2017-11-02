/**
 * ValueCleaner
 * Date: 30.03.2017
 * Last Update: 07.04.2017
 * TextProperties
 * Have values that contain 1 or more sentences
 * 
 * @author  Christian Staudte
 */

package com.swt.aprt17.Auto_Slides.Sources.DBPedia.Properties;

import java.util.ArrayList;

public class TextProperty extends Property{
	
	/**
	 * max amount of sentences that shall be saved
	 */
	private int sentenceAmount;
	
	public TextProperty(String name, String cleanName, boolean inverse, boolean cleanLink, ArrayList<Property> deepProperties, String type, int sentenceAmount) {
		super(name, cleanName, inverse, cleanLink, deepProperties, type);
		this.sentenceAmount = sentenceAmount;
	}
	public TextProperty(String name, String cleanName, boolean inverse, boolean cleanLink, String type, int sentenceAmount) {
		super(name, cleanName, inverse, cleanLink, type);
		this.sentenceAmount = sentenceAmount;
	}
	public TextProperty(String name, String cleanName, int maxNumberValues, boolean inverse, boolean cleanLink, ArrayList<Property> deepProperties, String type, int sentenceAmount) {
		super(name, cleanName, maxNumberValues, inverse, cleanLink, deepProperties, type);
		this.sentenceAmount = sentenceAmount;
	}
	public TextProperty(String name, String cleanName, int maxNumberValues, boolean inverse, boolean cleanLink, String type, int sentenceAmount) {
		super(name, cleanName, maxNumberValues, inverse, cleanLink, type);
		this.sentenceAmount = sentenceAmount;
	}
	
	/**
	 * special function to copy a TextProperty
	 */
	@Override
	protected Property copyProperty(){
		TextProperty p = null;
		if(this.getMaxNumberValues() != -1){
			if(this.getComplex()){
				p = new TextProperty(this.getName(), this.getCleanName(), this.getInverse(), this.getCleanLink(), this.getDepthSearchProperties(), this.getType(), this.getSentenceAmount());
			}else{
				p = new TextProperty(this.getName(), this.getCleanName(), this.getInverse(), this.getCleanLink(), this.getType(), this.getSentenceAmount());
			}
		}else{
			if(this.getComplex()){
				p = new TextProperty(this.getName(), this.getCleanName(), this.getMaxNumberValues(), this.getInverse(), this.getCleanLink(), this.getDepthSearchProperties(), this.getType(), this.getSentenceAmount());
			}else{
				p = new TextProperty(this.getName(), this.getCleanName(), this.getMaxNumberValues(), this.getInverse(), this.getCleanLink(), this.getType(), this.getSentenceAmount());
			}
		}
		return p;
	}
	
	//==========================================================================
	//Get- and Set Methods 
	public int getSentenceAmount(){
		return sentenceAmount;
	}
}
