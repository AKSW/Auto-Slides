/**
 * CityData
 * Date: 26.03.2017
 * Last Update: 18.05.2017
 * MainData:CityData
 * 
 * @author  Christian Staudte
 */
package com.swt.aprt17.Auto_Slides.Topics.Data;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

import com.swt.aprt17.Auto_Slides.Presentation.SlideGroup;
import com.swt.aprt17.Auto_Slides.Sources.DBPedia.Properties.Property;
import com.swt.aprt17.Auto_Slides.Sources.DBPedia.Properties.TextProperty;
import com.swt.aprt17.Auto_Slides.Sources.WikiPedia.ExtractBattleMap;

public class BattleData extends MainData{
	
	/**
	 * number of all units that fought if it exists
	 */
	private int strengthNumber = 0;
	
	/**
	 * number of all casualties
	 */
	private int casualtiesNumber = 0;
	
	/**
	 * battle map
	 */
	protected HashSet<String> battleMap;
	
	/**
	 * battle map source
	 */
	protected String battleMapLink;
	
	/**
	 * constructor
	 * @param name of town
	 */
	public BattleData(String name){
		super(name);
	}
	
	/**
	 * tap all sources that are available
	 * simplePedia needs an extra list for content, that is not wanted!
	 */
	@Override
	public void tapSources(){
		tapDBPedia();
		tapFlickr();
		tapWikiMedia();
		tapSimplePedia(notWanted);
		tapWikiPedia(notWanted);
		tapOpenStreetMap();
		tapBattleMap();
		
		deleteDublicateSlides();
	}
	
	@Override
	public void tapBattleMap(){
		try{
			ExtractBattleMap ebm = new ExtractBattleMap(name);
			this.battleMapLink = ebm.getCompleteUrl();
			this.battleMap = ebm.getMap();
		} catch(Exception e){
			System.err.println("Battle Map could not be saved. Maybe the topic name is wrong\n" + e);
		}	
	}
	
	/**
	 * split texts of an arraylist to extract all integer values that count. extract stuff like years 
	 * or when numbers are inside brackets
	 * @param texts
	 * @return
	 */
	public static ArrayList<Integer> getIntegersFromText(ArrayList<String> texts){
		ArrayList<Integer> numberList = new ArrayList<Integer>();
		
		List<String> year = Arrays.asList("january", "february", "march", "april", "may", "june", "july", "august", "september", "october", "november", "december");
		
		
		//go through texts
		int totalValue = 0;
		
		for(String text : texts){
			char[] charText = text.toCharArray();
			String number = "";
			String number2 = "";
			
			boolean hoveringANumber 	= false;
			boolean inBrackets 			= false;
			boolean hasDate 			= false;
			boolean nextIsTotal 		= false;
			boolean bindedWithTo		= false;
			
			
			if(text.toLowerCase().contains("total")){
				nextIsTotal = true;
			}
			
			for(String m : year){
				if(text.toLowerCase().contains(m)){
					hasDate = true;
					break;
				}
			}
			
			for(int i = 0; i < charText.length; i++){
				char c = charText[i];
				if(c == '(') inBrackets = true;
				if(c == ')') inBrackets = false;
				
				//c is a digit? save it!
				if(isDigit(c) && !inBrackets){
					if(!bindedWithTo){
						number += c;
					}else{
						number2 += c;
					}
					
					hoveringANumber = true;
					if(!(i == charText.length-1)) continue;
				}
				
				//use numbers after the date, typically splitted with ":"
				if(c == ':' && hasDate){
					number = "";
					hasDate = false;
				}
				
				//end of the number?
				if(hoveringANumber && c != ',' && !hasDate){

					//next number is important to calculate the average value, e.g. 100 to 200 -> 150
					if(continuesWithTo(text, i)){
						bindedWithTo = true;
						i += 3; //jump over "a to b"
						continue;
					}
					try{
						//number 2 when there is a split like "to"
						if(!number2.equals("")){
							numberList.add((Integer.parseInt(number) + Integer.parseInt(number2)) / 2);
							number2 = "";
						}else{
							numberList.add(Integer.parseInt(number));
						}
						if(nextIsTotal){
							totalValue += Integer.parseInt(number);
							nextIsTotal = false;
						}
						number = "";
						hoveringANumber = false;
					}catch(NumberFormatException nfe){
						//don't add
						number = "";
						hoveringANumber = false;
						continue;
					}
				}
			}
		}
		if(totalValue != 0){
			numberList.clear();
			numberList.add(totalValue);
		}
		//saved all numbers
		return numberList;
	}
	
	private static boolean continuesWithTo(String text, int pos){
		pos++;
		text = text.substring(pos).trim();
		if(text.startsWith("to")) return true;
		return false;
	}
	
	/**
	 * test if char is a digit
	 * @param c
	 * @return
	 */
	private static boolean isDigit(char c){
		return (c >= '0' && c <= '9');
	}
	
	
	/**
	 * create all properties:
	 */
	@Override
	protected void initProperties(){
		properties = new Property[10];
		properties[0] = new TextProperty("abstract","Abstract", false, false, "dbo:", 2);
		properties[1] = new Property("thumbnail", "Image", 	1, 	false, false, "dbo:");
		
		properties[2] = new Property("combatant", "Combatant", 3, 	false, false, "dbo:");
		properties[3] = new Property("isPartOfMilitaryConflict", "Part of military conflict", 1,	false, true, "dbo:");
		properties[4] = new Property("place", "Place", 3, false, true, "dbo:");
		properties[5] = new Property("result", "Result", 1, false, false, "dbo:");
		properties[6] = new Property("date", "Date", 1, false, false, "dbo:");
		
		properties[7] = new Property("strength", "Strength", false, false, "dbo:");
		properties[8] = new Property("causalties", "Casualties", false, false, "dbo:");
	
		properties[9] = new Property("commander", "Commander", 4, false, true, MainData.personList(), "dbo:");
	}
	
	/**
	 * create all individual slide groups and add properties for specific group
	 */
	@Override
	protected void initSlideGroups() {
		
		SlideGroup generalInformation;
		generalInformation = new SlideGroup("General Information");
		generalInformation.add(properties[2]);
		generalInformation.add(properties[3]);
		generalInformation.add(properties[4]);
		generalInformation.add(properties[5]);
		generalInformation.add(properties[6]);
		slideGroups.add(generalInformation);
		
		/*
		 * COMBATANTS EXTRA GROUP?
		 */
		
		SlideGroup mapImageGroups;
		mapImageGroups = new SlideGroup("Location");
		slideGroups.add(mapImageGroups);
		
		SlideGroup numbers;
		numbers = new SlideGroup("Numbers");
		numbers.add(properties[7]);
		numbers.add(properties[8]);
		slideGroups.add(numbers);
		
		SlideGroup leaderSlides;
		leaderSlides = new SlideGroup("Commanders");
		leaderSlides.add(properties[9]);
		slideGroups.add(leaderSlides);
		
		SlideGroup troopMovementGroup;
		troopMovementGroup = new SlideGroup("Troop Movements");
		slideGroups.add(troopMovementGroup);
		
		SlideGroup simplePediaSlides;
		simplePediaSlides = new SlideGroup("Miscellaneous");
		slideGroups.add(simplePediaSlides);
		
		SlideGroup ImpressionSlides;
		ImpressionSlides = new SlideGroup("Impressions");
		slideGroups.add(ImpressionSlides);
	}
	
	public String printBattleMap(){
		String s = "";
		
		if(battleMapLink != null) s += "\tSource: " + battleMapLink + "\n";
		if(battleMap != null){
			for(String m : battleMap){
				s += "\t" + m + "\n";
			}
		}
		
		if(battleMapLink == null && battleMap == null) {
			s += "\tNo BattleMap required";
		}
		s += "\n";
		return s;
	}
	
	//==========================================================================
    //Get- and Set Methods 
	public void setStrengthNumber(int strength){
		this.strengthNumber = strength;
	}
	public int getStrengthNumber(){
		return strengthNumber;
	}
	public int  getCasualtiesNumber(){
		return casualtiesNumber;
	}
	public HashSet<String> getBattleMap(){
		return battleMap;
	}
	public String getBattleMapLink(){
		return battleMapLink;
	}
}