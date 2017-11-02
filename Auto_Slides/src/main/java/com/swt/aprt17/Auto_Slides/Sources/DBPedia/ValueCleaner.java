/**
 * ValueCleaner
 * Date: 16.03.2017
 * Last Update: 18.05.2017
 * use ValueGetter and clean all properties with specific parameters
 * 
 * @author  Christian Staudte
 */

package com.swt.aprt17.Auto_Slides.Sources.DBPedia;

import java.text.BreakIterator;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

import com.swt.aprt17.Auto_Slides.Sources.DBPedia.Properties.Property;
import com.swt.aprt17.Auto_Slides.Sources.DBPedia.Properties.TextProperty;
import com.swt.aprt17.Auto_Slides.Topics.Data.BattleData;
import com.swt.aprt17.Auto_Slides.Topics.Data.MainData;

public class ValueCleaner {
	
	/**
	 * valueGetter that reads informationf rom DBPedia
	 */
	private ValueGetter valueGetter;
	
	/**
	 * all properties from value getter
	 */
	private List<Property> properties; /* all properties from valueGetter */
	
	/**
	 * source (link)
	 */
	private String source;
	
	/**
	 * topic data that is used
	 */
	private MainData mainData;
	
	/**
	 * constructor: gets all properties from valueGetter
	 * @param valueGetter
	 */
	public ValueCleaner(ValueGetter valueGetter, MainData mainData){
		this.valueGetter = valueGetter;
		this.mainData = mainData;
		this.properties = valueGetter.getProperties();
	}
	
	/**
	 * constructor: can select specific amount of properties from valueGetter
	 * @param valueGetter
	 * @param allProperties 
	 */
	public ValueCleaner(ValueGetter valueGetter, ArrayList<Property> properties, MainData mainData){
		this.valueGetter = valueGetter;
		this.properties = properties;
		this.mainData = mainData;
	}
	
	/**
	 * saves and cleans values for properties
	 */
	public void clean(){
		fillPropertiesWithValues();
		cleanProperties();
	}
	
	/**
	 * Look through whole String[] from valueGetter, split every entry into "property" and "value".
	 * Save source from ValueGetter, which is first value by definition, in special attribute.
	 * Save values for normal properties simply by adding new values to ArrayList
	 * For complex properties, save values (new resources) and create own valueCleaner
	 */
	public void fillPropertiesWithValues(){
		
		int counter = 0;
		if(valueGetter.getResultStringArray() == null) return;
		for(String s : valueGetter.getResultStringArray()){
			if(counter == 0){
				counter++;
				source = s;
				continue;
			}
			
			s = s.substring(3, s.length()-2);
			int pos = s.indexOf("=") + 1;
			
			String prop = "";
			String val = "";
			
			prop = s.substring(0, pos-2);
			val = s.substring(pos+2, s.length()-1);
			//give properties information
			for(Property p : properties){
				p.setSource(source);
				if(!p.getComplex()){
					if(p.getName().equals(prop)){
						if(p.getMaxNumberValues() != -1){ /* value limit */
							if(p.getMaxNumberValues() > p.getValues().size()){
								if(!val.equals("null")) p.getValues().add(val);
							}
						}else{ /* no value limit */
							if(!val.equals("null")) p.getValues().add(val);
						}
					}
				}else{ /* complex -> first just put resource name in map */
					if(p.getName().equals(prop)){
						if(p.getMaxNumberValues() != -1){ /* no value limit */
							if(p.getMaxNumberValues() > p.getComplexValues().size()){
								if(!val.equals("null")) p.createDepthSearchproperties(val);
							}
						}else{ /* value limit */
							if(!val.equals("null")) p.createDepthSearchproperties(val);
						}
					}
				}
			}
		}
		
		//create values for complex properties
		for(Property p : properties){
			if(p.getComplex()){
				for(String value : p.getComplexValues().keySet()){ /* look through all keys */
					String v = value;
					if(p.getCleanLink()){
						v = cleanLink(value);
					}
					
					ValueGetter tmpGetter = new ValueGetter(v, p.getComplexValues().get(value));
					tmpGetter.createQuery();
					tmpGetter.runQuery();
					ValueCleaner vc = new ValueCleaner(tmpGetter, this.mainData);
					vc.clean();
				}
			}
		}
	}
	
	/**
	 * Clean properties:
	 * First look for complex properties where links must be cleaned, change key-values with cleaned links.
	 * Second look though properties that save texts (TextProperties) and shorten number of sentences.
	 * Yet this function is only available for normal properties, not keys for complexValues (maybe later if needed)
	 */
	public void cleanProperties(){
		for(Property p : properties){
			
			//clean all links
			if(p.getComplex() && p.getCleanLink()){
				HashMap<String, ArrayList<Property>> map = new HashMap<String, ArrayList<Property>>();
				for(String key : p.getComplexValues().keySet()){
					map.put(cleanLink(key).replaceAll("_", " "), p.getComplexValues().get(key));
				}
				p.setComplexProperties(map);
				
			}else if(!p.getComplex() && p.getCleanLink()){
				ArrayList<String> newValues = new ArrayList<String>();
				for(String value: p.getValues()){
					newValues.add(cleanLink(value).replaceAll("_", " "));
				}
				p.setValues(newValues);
			}
			
			//clean all texts
			if(p.getComplex() && p instanceof TextProperty){
				//  for later if needed
			}else if(!p.getComplex() && p instanceof TextProperty){
				int sentences = ((TextProperty)p).getSentenceAmount();
				ArrayList<String> newValues = new ArrayList<String>();
				if(p.getValues().size() != 0){
					for(String value : p.getValues()){
						String newText = cutText(value, sentences);
						newText = newText.replace("\\", "");
						newText = StringUtils.unescapeHtml3(newText);
						newValues.add(newText);
					} 
				}
				p.setValues(newValues);
			}
			
			if(!p.getComplex() && p.getName().equals("thumbnail")){
				ArrayList<String> newValues = new ArrayList<String>();
				if(p.getValues().size() != 0){
					for(String v : p.getValues()){
						try{
							String link = RedirectGetter.getThumbnailLink(v);
							if(!link.equals("null")) newValues.add(link);
						}catch(Exception e){
							System.err.println("One thumbnail could not be loaded... " + e);
						}
						
					}
					p.setValues(newValues);
				}
			}
			
			//clean all properties with missing blanks, link 700soldiers -> 700 soldiers
			if(!p.getComplex() && !p.getName().equals("thumbnail")){
				ArrayList<String> newValues = new ArrayList<String>();
				
				for(String value : p.getValues()){
					String newValue = splitNumbersFromText(value);
					newValue = StringUtils.unescapeHtml3(newValue);
					newValues.add(newValue);
				}
				
				p.setValues(newValues);
			}
			
			dataSpecificClean(p);
		}
	}
	
	/**
	 * for special topics (...Data) construct special cleaning functions,
	 * so there are more options to change information
	 * @param p
	 */
	public void dataSpecificClean(Property p){
		if(mainData instanceof BattleData){
			//problematic stuff from battle, STRENGTH, COMBATANT, CAUSALTIES
			if(p.getName().equals("strength") || 
					p.getName().equals("combatant") ||
					p.getName().equals("causalties")){
				
				ArrayList<String> newValues = new ArrayList<String>();
				for(String v : p.getValues()){
					String nv = v.replace("*", "");
					if(nv.contains("[[#")) continue;
					//Strings should'nt end with ; , .
					if(nv.matches(".*[;,.]")) nv = nv.substring(0,  nv.length()-1);
					nv = StringUtils.unescapeHtml3(nv);
					if(!nv.trim().equals("") && !nv.contains("px")) newValues.add(nv);
				}
				p.setValues(newValues);
				
				if(p.getName().equals("strength")){
					ArrayList<Integer> numbers = BattleData.getIntegersFromText(p.getValues());
					int strength = 0;
					for(int number : numbers){
						strength += number;
					}
					((BattleData) mainData).setStrengthNumber(strength);
				}
			}
		}
	}
	
	/**
	 * clean link-part of an property, meaning only the last bit of a link
	 * @param link
	 * @return cleaned link (value)
	 */
	public String cleanLink(String link){
		if(link.contains("/resource/")){
    		int slashIndex = link.lastIndexOf("/resource/");
    		int offset = 10;
    		return link.substring(slashIndex + offset);
    	}else{
    		return link;
    	}
	}
	
	/**
	 * shorten text to amount of sentences that is desired
	 * @param text
	 * @param sentences, max number
	 * @return shortened text
	 */
	public static String cutText(String text, int sentences){
		ArrayList<String> split = ValueCleaner.splitSentences(text);
		String newText = "";
		
		for(int i = 0; i < split.size() && i < sentences; i++){
			newText += split.get(i);
		}

		return newText;
	}
	
	/**
	 * split a text into sentences and save it as arrayList with String Values.
	 * @param text
	 * @return sentences list
	 */
	public static ArrayList<String> splitSentences(String text){
		ArrayList<String> sentences = new ArrayList<String>();
		
		BreakIterator iterator = BreakIterator.getSentenceInstance(Locale.US);
		iterator.setText(text);
		int start = iterator.first();
		
		for (int end = iterator.next(); end != BreakIterator.DONE; start = end, end = iterator.next()) {
			sentences.add(text.substring(start,end));
		}
		
		return sentences;
	}
	
	/**
	 * if there are numbers written onto text like "test 23test" split it
	 * @param text
	 * @return
	 */
	public static String splitNumbersFromText(String text){
		String s = "";
		boolean lastCharIsNumber = false;
		
		for(int i = 0; i < text.length(); i++){
			//get letter
			String letter = text.substring(i, i+1);
			
			/*
			 * check if letter is a number or not.
			 * if yes, go to the next letter, check again. as long as next letter is not element of a-z,A-Z continue
			 * if next letter is element of -+;* etc. don't add a blank
			 * if next letter is a real letter, add a blank symbol
			 * 
			 */
			try{
				Integer.parseInt(letter);
				lastCharIsNumber = true;
			}catch(Exception e){
				if(lastCharIsNumber){
					if(letter.matches("[a-zA-Z]"))
						s += " ";
					lastCharIsNumber = false;
				}
			}
			s += letter;
		}
		return s;
	}
}
