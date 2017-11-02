/**
 *ZipArchiver
 * 
 *@author Kilian
 */
 
package com.swt.aprt17.Auto_Slides.Controller.ZipArchiver;

import java.io.FileOutputStream;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class Zip {
	
	/**
	 * String objects for ZIP name
	 */
	private String z_name;
	
	/**
	 * String object for save Location
	 */
	private String saveLoc;
	
	/**
	 * ArrayList with ZipObjects
	 */
	private ArrayList<ZipObject> data = new ArrayList<ZipObject>();
	
	/**
	 * HashSet with .pptx names
	 */
	private HashSet<String> nameSet = new HashSet<String>();
	
	/**
	 * constructor
	 */
	public Zip(){	
	}
	
	/**
	 * adds byte arrays to ArrayList
	 * @param name
	 * @param data
	 */
	public void addData(String name, byte[] data){
		/* no duplicate names */
		boolean added = false;
		int number = 1;
		String uniqueName = name.replaceAll("/", "_"); /* not unwanted subfolders */
		
		while(!added) {
			if(nameSet.add(uniqueName)) {
				added = true;
			}
			else {
				uniqueName = name + "(" + number + ")";
				number++;
			}
		}

		ZipObject obj = new ZipObject(uniqueName, data);
		this.data.add(obj);
	}
	
	/**
	 * writes data into file into ZIP archive
	 */
	public void zipCreate(){
		if(data.size() == 0) {
			System.err.println("ERROR: No Data given in method 'zipCreate' (Zip.java)");
		}
		else {
			//defines DateTime format
			DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyyMMdd.HHmmss");
			
			//takes local time and date and formats it
			this.z_name = "pres_" +dtf.format(LocalDateTime.now()) + ".zip";

			try{
				FileOutputStream f_out = new FileOutputStream(z_name);
				ZipOutputStream z_out = new ZipOutputStream(f_out);
				
				for (int i = 0; i<data.size();i++){
					ZipEntry z_e = new ZipEntry(data.get(i).getName() + ".pptx");
					z_out.putNextEntry(z_e);
					
					z_out.write(data.get(i).getData(), 0, data.get(i).getData().length);
					z_out.closeEntry();
				}
				z_out.close();
				saveLoc = System.getProperty("user.dir");
			}
			catch(IOException ex){
				ex.printStackTrace();
			}
		}	
	}
	
	/**
	 * returns String object
	 * @return z_name
	 */
	public String getZName(){
		
		return z_name;
	}
	
	/**
	 * returns String object
	 * @return saveLoc
	 */
	public String getSave(){
		return saveLoc;
	}
	
	/**
	 * returns ArrayList<ZipObject> object
	 * @return data
	 */
	public ArrayList<ZipObject> getData() {
		return data;
	}
}
