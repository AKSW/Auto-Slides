/**
 * SimplePediaReader
 * Date: 14.4.2017
 * Last Update: 18.05.2017
 * Saves all contents of a specific topic that can be received from SimplePedia.
 * Normal texts and texts with bullets are separated. Source is saved in a String.
 * 
 * @author  Christian Staudte
 */
package com.swt.aprt17.Auto_Slides.Sources.SimplePedia;

import java.util.ArrayList;

import com.swt.aprt17.Auto_Slides.Sources.Content;
import com.swt.aprt17.Auto_Slides.Sources.DBPedia.ValueCleaner;

public class SimplePediaReader{
	
	/**
	 * actual topic searched for
	 */
	private String topic;
	
	/**
	 * source, in this case a simplepedia link
	 */
	private String source;
	
	/**
	 * all texts with title
	 */
	private ArrayList<Content> simplePediaTexts;
	
	/**
	 * constructor
	 * @param topic
	 * @param notWantedContent, stop saving things you don't want in your presentation!
	 */
	public SimplePediaReader(String topic, ArrayList<String> notWantedContent){
		this.topic 				= topic;
		this.simplePediaTexts 	= new ArrayList<Content>();
		init(notWantedContent);
	}
	
	/**
	 * save normal texts and text with bullets in a Map/List
	 * If you want to use all contents, just set noWantedContent null
	 * @param notWantedContent
	 */
	private void init(ArrayList<String> notWantedContent){
		SimplePedia simplePediaConnection = new SimplePedia(topic);
		this.source = simplePediaConnection.getCompleteUrl(); /* save link */
		
		 ArrayList<String[]> tableOfContent = simplePediaConnection.getTableOfContent();
		 
		 for(String[] section : tableOfContent){
			
			//i.e. Gallery for cities is not wanted
			if(notWantedContent != null && notWantedContent.contains(section[0])) continue;	
			
			String value = simplePediaConnection.getContent(section[1]);			
			
			ArrayList<String> rawValueList = ValueCleaner.splitSentences(value);
			ArrayList<String> finishedValueList = new ArrayList<String>();
			
			for(String v : rawValueList){
				v = startCleaner(v);
				
				if(!v.trim().equals("")){
					finishedValueList.add(v);
				}
			}
			
			if(finishedValueList.size() != 0){
				simplePediaTexts.add(new Content(section[0], finishedValueList));
			}
			
		}
	}
	
	/**
	 * change things that are at the beginning of single sentences 
	 * and are annoying
	 * @param value
	 * @return
	 */
	private String startCleaner(String value){
		String prefix = ":p.";
		int pos = prefix.length();
		
		if(value.startsWith(prefix)){
			for(int i = pos+1; i < value.length(); i++){
				try{
					Integer.parseInt(value.substring(i, i+1));
				}catch(Exception e){
					pos = i;
					break;
				}
			}
			return value.substring(pos, value.length()).trim();
		}else{
			return value;
		}
	}
	
	//==========================================================================
	//Get- and Set Methods 
	public ArrayList<Content>  getSimplePediaTexts(){
		return simplePediaTexts;
	}
	public String getSource(){
		return source;
	}
}