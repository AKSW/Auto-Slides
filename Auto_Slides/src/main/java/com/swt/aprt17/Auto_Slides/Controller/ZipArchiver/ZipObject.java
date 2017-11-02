/**
 * Objects for later archiving
 * 
 * @author Kilian
 */
 
package com.swt.aprt17.Auto_Slides.Controller.ZipArchiver;

public class ZipObject {
	
	/**
	 * String object containing name
	 */
	private String name;
	
	/**
	 * byte array containing file data
	 */
	private byte[] data;
	
	/**
	 * constructor
	 * @param name
	 * @param data
	 */
	public ZipObject(String name, byte[] data){
		this.name = name;
		this.data = data;
	}
	
	/**
	 * returns String object
	 * @return name
	 */
	public String getName(){
		return this.name;
	}
	
	/**
	 * returns byte array
	 * @return data
	 */
	public byte[] getData(){
		return this.data;
	}
}
