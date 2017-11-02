/**
 * SlideFactory
 * Date: 01.04.2017
 * Last Update: 09.04.2017
 * Create all Slides for every Slidegroup and fill Slides with information using Strings
 * 
 * @author  Francesco Mandry
 */

package com.swt.aprt17.Auto_Slides.Topics.SlideFactories;
import java.util.*;
import com.swt.aprt17.Auto_Slides.Presentation.SlideGroup;
import com.swt.aprt17.Auto_Slides.Sources.DBPedia.Properties.Property;
import com.swt.aprt17.Auto_Slides.Sources.Content;
import com.swt.aprt17.Auto_Slides.Topics.Data.MainData;

public abstract class SlideFactory {
	
	/**
	 * mainData mainData
	 */
	protected MainData mainData;
	
	/**
	 * Set where all global sources will be stored in
	 */
	protected Set<String> sources;
	
	/**
	 * Topic string, to print on the title slide, will be declared in the Subclass Constructor
	 */
	protected String topic; 
	
	/**
	 * Constructor: SlideFactory
	 * @param MainData mainData 
	 */
	public SlideFactory(MainData mainData) {
		
		this.mainData = mainData;
		this.sources = new HashSet<String>();
	}
	
	/**
	 * Method: fillSlides
	 * call up all fill-methods
	 */
	public void fillSlides() {
		
		fillTitleSlide(); 				/* Title */
		fillTopicSlides();				/* topic specific slides */
		fillSourceSlide(); 				/* Source */
		
		//this needs to be called on last, so only SlideGroups that were actually created appear on the content slide
		fillContentsSlide(); 			/* Contents */
	}
	
	/**
	 * Method: fillTopicSlides
	 * call up all topic specific methods
	 */
	protected abstract void fillTopicSlides();
	
	/**
	 * Method: fillTitleSlide
	 * Slidegroup: Title
	 * fill the SlideGroup Title with TitleSlide
	 */
	protected void fillTitleSlide() {
		SlideGroup title = mainData.getSlideGroup("Title");
		title.newTitleSlide(mainData.getCleanName(), topic); /* creates slide */
	}
	
	/**
	 * Method: fillContentsSlide
	 * Slidegroup: Contents
	 * check if SlideGroup is filled with information
	 * then fill the SlideGroup Contents with ContentsSlide
	 */
	protected void fillContentsSlide() {
		
		List<String> contentsList = new ArrayList<String>();
		List<SlideGroup> slideGroups = mainData.getSlideGroups();
		
		for(SlideGroup sl : slideGroups) {
			
			if( sl.getSlideCount() > 0 && !sl.getName().equals("Title")) { 
				
				contentsList.add(sl.getName());
			}
		}
		
		/* this happens if the topic is invalid (e.g. a non-existing city) */
		if (contentsList.isEmpty()) return;
		
		SlideGroup contents = mainData.getSlideGroup("Content");
		contents.newContentsSlide(contentsList); /* creates slide */
	}
	
	/**
	 * Fills map slide; 
	 * one slide, with a map showing the location of the battle
	 */
	protected void fillMapImageSlide(){
		SlideGroup sg = mainData.getSlideGroup("Location");
		byte[] mapImageByte = mainData.getMapImageByte();
		String mapImageLink = mainData.getMapImageLink();
		
		if(mapImageByte == null || mapImageLink == null) return;		// no content, no slide
		
		sources.add(mapImageLink);
		sg.newPictureSlide(sg.getName(), mapImageByte, mapImageLink);
	}
	
	/**
	 * Method: fillPersonSlide
	 * fills person slides for any SlideGroup containing one Property, 
	 * which in turn contains a personList (see MainData)
	 * @param String kindOfPerson Name of the SlideGroup these persons belong to
	 * @param maxSlides maximum amount of slides to create 
	 * @return sources as String
	 */
	protected void fillPersonSlide(String kindOfPerson, int maxSlides) {
		
		SlideGroup personGroup = mainData.getSlideGroup(kindOfPerson);
		
		if(personGroup.getProperties().get(0).getComplexValues().isEmpty()) return;	// no content, no slide
		
		/* key = person name, value = person properties */
		HashMap<String, ArrayList<Property>> personMap = personGroup.getProperties().get(0).getComplexValues();
		
		int slideNumber = 0;
		if(maxSlides < 0) maxSlides = 0; 
		
		for(String name : personMap.keySet()) {
			if(slideNumber == maxSlides) break;
			
			List<Property> personPropertiesList = personMap.get(name);
			List<String> personPropertiesStringList = new ArrayList<String>();
			/* fill the ArrayList with property values of "". if there is no information to this property, it will stay an empty String */
			personPropertiesStringList.add(""); /* property 1: abstract @ ArrayList.get(0) */
			personPropertiesStringList.add(""); /* property 2: thumbnail @ ArrayList.get(1) */ 
			personPropertiesStringList.add(""); /* property 3: party @ ArrayList.get(2) */
			for(int i = 0; i < personPropertiesList.size(); i++) {
				
				if(!personPropertiesList.get(i).getValues().isEmpty()) { 
					
					personPropertiesStringList.set(i, personPropertiesList.get(i).getValues().get(0));
				}
			}
			Set<String> sgSource = collectSourcesFromProperties(personPropertiesList);
			sources.addAll(sgSource);
			personGroup.newTextPicSlide(kindOfPerson, name, personPropertiesStringList.get(2), personPropertiesStringList.get(0), personPropertiesStringList.get(1), sgSource); /* create slide */
			slideNumber++;
		}
	}
	
	/**
	 * Fills miscellaneous (SimplePedia and WikiPedia) slides; 
	 * one slide per wiki article section: 
	 * text slide for texts, bullet slide for bullet points
	 */
	protected void fillMiscSlides() {
		
		boolean noSimplePedia = mainData.getSimplePediaTexts().isEmpty();
		boolean noWikiPedia = mainData.getWikiPediaTexts().isEmpty();
		
		if(	noSimplePedia && noWikiPedia) return;	// no content, no slide

		SlideGroup sg = mainData.getSlideGroup("Miscellaneous");
		Set<String> sgSources = new HashSet<String>();
		
		/* SimplePedia */
		if(! noSimplePedia) {
			ArrayList<Content> sp = mainData.getSimplePediaTexts();		
			for(Content content : sp) {
				String title = content.getTitle();
				ArrayList<String> text = content.getText();
				
				if(text.get(0).length() == 0) continue;		// no content, no slide
				
				if(text.size() == 1)
					sg.newTextSlide(title, text.get(0), sgSources, sg.getName());
				else
					sg.newBulletsSlide(title, text, sgSources, sg.getName());
			}
			sgSources.add(mainData.getSimplePediaSource());
		}
		
		/* WikiPedia */
		if(! noWikiPedia) {
			ArrayList<Content> sp = mainData.getWikiPediaTexts();		
			for(Content content : sp) {
				String title = content.getTitle();
				ArrayList<String> text = content.getText();

				if(text.get(0).length() == 0) continue;		// no content, no slide
				
				if(text.size() == 1)
					sg.newTextSlide(title, text.get(0), sgSources, sg.getName());
				else
					sg.newBulletsSlide(title, text, sgSources, sg.getName());
			}
			sgSources.add(mainData.getWikiPediaSource());
		}
		sources.addAll(sgSources);
	}
	
	/**
	 * Method: fillImpressionsSlide
	 * Slidegroup: Impression
	 * fill the SlideGroups Impressions and Source with ImpressionSlides and SourceSlides 
	 */
	protected void fillImpressionsSlides() {
		
		SlideGroup impressionSlides  = mainData.getSlideGroup("Impressions");
		SlideGroup sources 			 = mainData.getSlideGroup("Sources");
		
		Set<String> impressionLinks = new HashSet<String>();
		for(int i = 1; i < 4; i++) {
				 if(i == 1) impressionLinks = mainData.getImpressionLinks1();
			else if(i == 2) impressionLinks = mainData.getImpressionLinks2();
			else if(i == 3) impressionLinks = mainData.getImpressionLinks3();
			
			if(impressionLinks.isEmpty()) continue;		// no content, no slide
			
			impressionSlides.newImpressionSlide(impressionLinks, i); /* create slide */
			sources.newSourceSlide("Impressions "+i+" Sources", impressionLinks); /* create slide */
		}
	}
	
	/**
	 * Method: fillSourceSlide
	 * Slidegroup: Sources
	 * fill the SlideGroup Sources with SourceSlide
	 */
	protected void fillSourceSlide() {
		
		/* this happens if the topic is invalid (e.g. a non-existing city) */
		if (sources.isEmpty()) return;
		
		SlideGroup source = mainData.getSlideGroup("Sources");		 
		source.newSourceSlide("Sources", sources);
	}
	
	/**
	 * Method: collectSourcesFromProperties
	 * Puts all sources of a list of Properties into one set.
	 * @param List<Property> properties, list of Properties
	 * @return Set<String> sgSources, all sources in those Properties 
	 */
	protected Set<String> collectSourcesFromProperties(List<Property> properties) {	
		
		Set<String> sgSources = new HashSet<String>();
		for (int i = 0 ; i < properties.size(); i++) {
			
			sgSources.add(properties.get(i).getSource());
		}
		return sgSources;
	}
	
	/**
	 * Method: thousandSplitter
	 * takes a number as String and puts ',' between the thousands
	 * @param String number the number, e.g. "1000"
	 * @return the number with commata, e.g. "1,000"
	 */
	protected String thousandSplitter(String number) {
		
		StringBuilder sb = new StringBuilder(number);
		int offset = sb.length();
		while (offset > 3) {
			
			offset -= 3;
			sb.insert(offset, ",");			
		}
		
		return sb.toString();	
	}
	
	/**
	 * Gets one value of a property, if it exists; if not, returns empty String.
	 * @param property the Property of which to get the value
	 * @param index index of ArrayList<String> values at which the desired value is 
	 * @return the value as a String if it exists, empty String otherwise
	 */	
	protected String getValue(Property property, int index) {
		if(property.getValues().isEmpty())
			return "";
		else if (property.getValues().size() <= index )	// avoid IndexOutOfBoundsException
			return "";
		else
			return property.getValues().get(index);
	}
	
	/**
	 * Given a property and its value, returns a line (e.g. for a bullet point) 
	 * formatted as [Property]: [value].
	 * If the parameter value is an empty string, it will appear as "unknown". 
	 * @param property the property this is about
	 * @param value the value the property has
	 * @return a formatted String such as "Country: Spain"
	 */
	protected String lineWithColon(Property property, String value) {
		if (value.length() == 0) value = "unknown";
		
		return property.getCleanName() + ": " + value;
	}
	
	/**
	 * Given a property, this method puts all values in its List<String> values 
	 * separated with commata in one String
	 * e.g.: "value1, value2, value3"
	 * @param property Property whose values are to be listed with commata
	 * @return the values in a String, separated by commata
	 */
	protected String listToCommata(Property property) {
		String value = getValue(property, 0);		
		for (int i = 1 ; i < property.getValues().size() ; i++)
			value += ", " + getValue(property, i);
		
		return value;
	}
	
	/**
	 * Transforms date from ISO 8601 (DBPedia format) into 
	 * the DMY format commonly used in Europe and most of the world. 
	 * Static because Finder uses it.
	 * @param date a date in ISO 8601 (yyyy-mm-dd)
	 * @return a date in common form (dd.mm.yyyy)
	 */
	public static String formatDate (String dateString) {
		String[] date = dateString.split("-");
		int last = date.length;
		
		if(last < 3 || last > 4)	// unexpected format
			return dateString;
		
		if(last == 4)	// year is BC, like -333-11-05
			date[last-3] = date[last-3] + " BC";
		
		while(date[last-3].startsWith("0", 0))	// cut leading 0s off year
			date[last-3] = date[last-3].substring(1);

		dateString = 
				  date[last-1] // day
				+ "."
				+ date[last-2] // month
				+ "."
				+ date[last-3]; // year
		
		return dateString;
	}
	
	/**
	 * Converts the area from m^2 to km^2 and adds commata. 
	 * ex.: 1000123456 -> 1,000 km²
	 * @param area the area in m^2
	 * @return the area in km^2 with blanks separating the thousands
	 */
	protected String formatArea(String area) {
		
		/* area is in double format in DBPedia, might carry decimal places -> cut those off */
		int i = area.lastIndexOf(".");
		if (i > -1)
			area = area.substring(0, i);
		
		if(area.length() < 7)
			return "< 1 km²";
		
		/* convert m^2 to km^2 -> divide by 1,000,000 */
		area = area.substring(0, area.length()-6);
		
		return thousandSplitter(area) + " km²";		
	}
	
	/**
	 * Method: getMainData
	 * returns MainData 
	 * @return MainData mainData
	 */
	public MainData getMainData() {
		
		return mainData;
	}
}