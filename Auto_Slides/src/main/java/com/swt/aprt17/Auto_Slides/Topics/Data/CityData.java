/**
 * CityData
 * Date: 26.03.2017
 * Last Update: 24.04.2017
 * MainData:CityData
 * 
 * @author  Christian Staudte
 */
package com.swt.aprt17.Auto_Slides.Topics.Data;

import com.swt.aprt17.Auto_Slides.Presentation.SlideGroup;
import com.swt.aprt17.Auto_Slides.Sources.DBPedia.Properties.Property;
import com.swt.aprt17.Auto_Slides.Sources.DBPedia.Properties.TextProperty;

public class CityData extends MainData{
	
	/**
	 * constructor
	 * @param name of town
	 */
	public CityData(String name){
		super(name);
	}
	
	/**
	 * tap all sources that are available
	 * simplePedia needs an extra list for content, that is not wanted!
	 */
	public void tapSources(){
		tapDBPedia();
		tapFlickr();
		tapWikiMedia();
		tapOpenStreetMap();
		
		tapSimplePedia(notWanted);
		tapWikiPedia(notWanted);
		
		deleteDublicateSlides();
	}
	
	/**
	 * create all properties:
	 */
	@Override
	protected void initProperties(){
		properties = new Property[9];
		properties[0] = new TextProperty("abstract",	"Abstract", 		 	false, false, "dbo:", 2);
		properties[1] = new Property("thumbnail", 		"Image", 			1, 	false, false, "dbo:");
		properties[2] = new Property("country", 		"Country", 			1, 	false, true, "dbo:");
		properties[3] = new Property("populationTotal", "Total population", 1,	false, false, "dbo:");
		properties[4] = new Property("populationAsOf", 	"Population as of", 1,	false, false, "dbo:");
		properties[5] = new Property("areaCode",		"Area code", 		2,	false, false, "dbo:");
		properties[6] = new Property("leaderName", 	 	"Name of leader", 	1,	false, true, MainData.personList("party", "Party"), "dbo:");
		properties[7] = new Property("birthPlace",		"Birth place of", 	4,  true, true, MainData.personList(), "dbo:");
		properties[8] = new Property("areaTotal",		"Total Area", 		1,	false, false, "dbo:");
	}
	
	/**
	 * create all individual slide groups and add properties for specific group
	 */
	@Override
	protected void initSlideGroups() {
		
		
		SlideGroup geographySlides;
		geographySlides = new SlideGroup("General Information");
		geographySlides.add(properties[2]);
		geographySlides.add(properties[3]);
		geographySlides.add(properties[4]);
		geographySlides.add(properties[5]);
		geographySlides.add(properties[8]);
		slideGroups.add(geographySlides);
		
		SlideGroup mapImageGroups;
		mapImageGroups = new SlideGroup("Location");
		slideGroups.add(mapImageGroups);
		
		SlideGroup leaderSlides;
		leaderSlides = new SlideGroup("Leader");
		leaderSlides.add(properties[6]);
		slideGroups.add(leaderSlides);
		
		SlideGroup birthSlides;
		birthSlides = new SlideGroup("Born in this City");
		birthSlides.add(properties[7]);
		slideGroups.add(birthSlides);
		
		SlideGroup simplePediaSlides;
		simplePediaSlides = new SlideGroup("Miscellaneous");
		slideGroups.add(simplePediaSlides);
		
		SlideGroup ImpressionSlides;
		ImpressionSlides = new SlideGroup("Impressions");
		slideGroups.add(ImpressionSlides);
	}
}