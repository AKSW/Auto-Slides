/**
 * Content
 * Date: 05.05.2017
 * Last Update: 18.05.2017
 * 
 * @author  Christian Staudte
 */

package com.swt.aprt17.Auto_Slides.Sources;

import java.util.ArrayList;

public class Content {
	
	/**
	 * title of one content
	 */
	private String title;
	
	/**
	 * text of content, typically sentences
	 */
	private ArrayList<String> text;
	
	/**
	 * constructor
	 * @param title
	 * @param text
	 */
	public Content(String title, ArrayList<String> text){
		this.title = title;
		this.text = text;
	}
	
	/**
	 * compare titles, use them for equals
	 * @param c
	 * @return
	 */
	@Override
	public boolean equals(Object o){
		Content c = (Content)o;
		return title.toLowerCase().equals(c.getTitle().toLowerCase());
	}
	
	//==========================================================================
    //Get- and Set Methods 
	public String getTitle(){
		return title;
	}
	public ArrayList<String> getText(){
		return text;
	}
}