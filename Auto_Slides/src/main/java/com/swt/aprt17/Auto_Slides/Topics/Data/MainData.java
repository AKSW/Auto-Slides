/**
 * MainData
 * Date: 26.03.2017
 * Last Update: 18.05.2017
 * Layout for Presentations.
 * First create all properties without information
 * Then create all SlideGroups and connect specific groups with certain properties
 * Slidegroupes are going to take care of format related stuff
 * Yet, when mainData is created, it has no information at all, you first need to call "tapSources"
 * 
 * 
 * @author  Christian Staudte
 */
package com.swt.aprt17.Auto_Slides.Topics.Data;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

import com.swt.aprt17.Auto_Slides.Presentation.PowerPoint;
import com.swt.aprt17.Auto_Slides.Presentation.SlideGroup;
import com.swt.aprt17.Auto_Slides.Sources.Content;
import com.swt.aprt17.Auto_Slides.Sources.DBPedia.ValueCleaner;
import com.swt.aprt17.Auto_Slides.Sources.DBPedia.ValueGetter;
import com.swt.aprt17.Auto_Slides.Sources.DBPedia.Properties.Property;
import com.swt.aprt17.Auto_Slides.Sources.DBPedia.Properties.TextProperty;
import com.swt.aprt17.Auto_Slides.Sources.Flickr.ExtractFlickrImages;
import com.swt.aprt17.Auto_Slides.Sources.OpenStreetMap.MapImageGetter;
import com.swt.aprt17.Auto_Slides.Sources.SimplePedia.SimplePediaReader;
import com.swt.aprt17.Auto_Slides.Sources.WikiMedia.ExtractBeautifulWikiImage;
import com.swt.aprt17.Auto_Slides.Sources.WikiMedia.ExtractWikiImage;
import com.swt.aprt17.Auto_Slides.Sources.WikiPedia.ExtractCoordinate;
import com.swt.aprt17.Auto_Slides.Sources.WikiPedia.WikiPediaReader;

public abstract class MainData {
	
	/**
	 * mainData name
	 */
	protected String name;
	
	/**
	 * mainData name without underscores 
	 */
	protected String cleanName;
	
	/**
	 * list of all slidegroupes
	 */
	protected List<SlideGroup> slideGroups;
	
	/**
	 * array, that takes care of all properties
	 */
	protected Property[] properties;
	
	/**
	 * all texts from SimplePedia with name and bulletpoints
	 */
	protected ArrayList<Content> simplePediaTexts;
	
	/**
	 * all texts from SimplePedia with name and bulletpoints
	 */
	protected ArrayList<Content> wikiPediaTexts;
	
	/**
	 * simplePediaSource
	 */
	protected String simplePediaSource;
	
	/**
	 * wikiPediaSource
	 */
	protected String wikiPediaSource;
	
	/**
	 * links for first impression Slide
	 */
	protected HashSet<String> impressionLinks1;
	
	/**
	 * links for second impression Slide
	 */
	protected HashSet<String> impressionLinks2;
	
	/**
	 * links for third impression Slide
	 */
	protected HashSet<String> impressionLinks3;
	
	/**
	 * final presentation that first must be created by createPowerPoint()
	 */
	protected PowerPoint powerpoint;
	
	/**
	 * information for map that is created
	 */
	protected byte[] mapImageByte;
	
	/**
	 * link to map
	 */
	protected String mapImageLink;
	
	/**
	 * list for things not wanted in wikipedia/simplepedia
	 */
	protected ArrayList<String> notWanted = new ArrayList<String>();
	
	
	/**
	 * constructor, initialize properties and slidegroupes. prepare everything for incoming information
	 * @param name headline of presentation
	 */
	public MainData(String name){
		this.name 					= name;
		this.cleanName				= name.replace("_", " ");
		this.slideGroups 			= new ArrayList<SlideGroup>();
		
		this.impressionLinks1 		= new HashSet<String>();
		this.impressionLinks2 		= new HashSet<String>();
		this.impressionLinks3 		= new HashSet<String>();
		
		initProperties();
		createSlideGroupes();
		
		wikiPediaTexts 		= new ArrayList<Content>();
		simplePediaTexts 	= new ArrayList<Content>();
		
		notWanted.add("Footnotes");
		notWanted.add("Notes");
		notWanted.add("References");
		notWanted.add("External links");
		notWanted.add("Gallery");
		notWanted.add("Citations");
		notWanted.add("Bibliography");
		notWanted.add("Further Reading");
		
		notWanted.add("See also");
		notWanted.add("References");
		notWanted.add("Footnotes");
		notWanted.add("Citations");
		notWanted.add("Bibliography");
		notWanted.add("Further reading");
		notWanted.add("External links");
		notWanted.add("Notes");
		notWanted.add("References");
		notWanted.add("Other websites");
		notWanted.add("Related pages");
	}
	
	
	/**
	 * initialize properties from DBPedia that are needed for this mainData
	 */
	protected abstract void initProperties();
	
	/**
	 * create all slide groups with name, add them to list slideGroups
	 */
	protected abstract void initSlideGroups();
	
	/**
	 * get all information from sources
	 */
	public abstract void tapSources();
	
	
	/**
	 * information retrieval for DBPedia
	 */
	protected void tapDBPedia(){
		//properties
		ValueGetter vg = new ValueGetter(name, getPropertiesAsList());
		vg.createQuery();
		vg.runQuery();
		
		ValueCleaner vc = new ValueCleaner(vg, this);
		vc.clean();
	}
	
	/**
	 * information retrieval for Street Map
	 */
	protected void tapOpenStreetMap(){
		MapImageGetter mig;
		/* try to extract coords from Wikipedia  */
		ExtractCoordinate bc = new ExtractCoordinate(name);
		double[] coords = new double[2];
		coords[0] = bc.getLat();
		coords[1] = bc.getLon();
		if(coords[0] == 361 || coords[1] == 361) {
			/* 
			 * no coords from Wikipedia
			 * try OpenStreetMapSearch
			 */
			mig = new MapImageGetter(name);
		}
		else {
			/* use found coords from Wikipedia */
			mig = new MapImageGetter(coords);
		}
		
		this.mapImageByte = mig.getMapImage();
		this.mapImageLink = mig.getLastSource();
	}
	
	/**
	 * information retrieval for Flickr
	 */
	protected void tapFlickr(){
		ExtractFlickrImages efi = new ExtractFlickrImages(name);
		try {
			this.impressionLinks1 = efi.getImagesUrl();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * information retrieval for WikiMedia
	 */
	protected void tapWikiMedia(){
		ExtractBeautifulWikiImage ebwi 	= new ExtractBeautifulWikiImage(name, 6, 650, 650);
		ExtractWikiImage ewi 			= new ExtractWikiImage(name, 6);
		
		try {
			this.impressionLinks2 = ebwi.getImagesUrl();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		try {
			this.impressionLinks3 = ewi.scrapImage();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * information retrieval for SimplePedia
	 */
	protected void tapSimplePedia(ArrayList<String> notWantedContent){
		SimplePediaReader s 	= new SimplePediaReader(name, notWantedContent);
		this.simplePediaTexts 	= s.getSimplePediaTexts();
		this.simplePediaSource = s.getSource();
	}
	
	/**
	 * information retrieval for WikiPedia
	 */
	protected void tapWikiPedia(ArrayList<String> notWantedContent){
		
		/* to avoid duplicates, only call on WikiPedia if SimplePedia didn't yield results or wasn't called on */
		if(this.simplePediaTexts.size() > 0) return;
		
		WikiPediaReader w 		= new WikiPediaReader(name, notWantedContent);
		this.wikiPediaTexts 	= w.getWikiPediaTexts();
		this.wikiPediaSource 	= w.getSource();
	}
	
	/**
	 * get a battle map
	 */
	protected void tapBattleMap(){
		
	}
	
	/**
	 * Creates SlideGroups. Every presentation looks like this:
	 * 1. TitleGroup
	 * 2. contentGroup
	 * 3. - n. individualGroups (defined in sub classes)
	 * n+1. SourcesGroup
	 */
	private void createSlideGroupes(){
		SlideGroup titleSlides;
		titleSlides = new SlideGroup("Title");
		slideGroups.add(titleSlides);
		
		SlideGroup contentsSlides;
		contentsSlides = new SlideGroup("Content");
		slideGroups.add(contentsSlides);
		
		//use first two properties with abstract and thumbnail
		SlideGroup introSlides;
		introSlides = new SlideGroup("Intro");
		introSlides.add(properties[0]);
		introSlides.add(properties[1]);
		slideGroups.add(introSlides);
		
		initSlideGroups();
		
		SlideGroup sourceSlides;
		sourceSlides = new SlideGroup("Sources");
		slideGroups.add(sourceSlides);
	}
	
	/**
	 * when there are duplicates concerning contents 
	 * choose from which source to use
	 * therefor the following order is used:
	 * 
	 * 1. if a content is in property, don't use this one from SImplePedia nor WikiPedia
	 * 2. if a content is in SimplePedia, don't use it in WikiPedia
	 */
	public void deleteDublicateSlides(){
		for(Property p : properties){
			//properties are most important. not samestuff in wikipedia nor simplepedia
			String propertyTitle 		= p.getName().toLowerCase();
			String propertyTitleClean 	= p.getCleanName().toLowerCase();
			
			
			ArrayList<Content> newS 	= new ArrayList<Content>();
			//clean SimplePedia
			for(Content c : simplePediaTexts){
				String title = c.getTitle().toLowerCase();
				if(!(title.equals(propertyTitle) || title.equals(propertyTitleClean))){
					newS.add(c);
				}
			}
			simplePediaTexts = newS;
			
			
			ArrayList<Content> newW 	= new ArrayList<Content>();
			//clean WikiPedia
			for(Content c : wikiPediaTexts){
				String title = c.getTitle().toLowerCase();
				if(!(title.equals(propertyTitle) || title.equals(propertyTitleClean))){
					newW.add(c);	
				}
			}
			wikiPediaTexts = newW;
		}
		ArrayList<Content> copyWikiPediaTexts = new ArrayList<Content>();
		for(Content c1 : wikiPediaTexts){
			copyWikiPediaTexts.add(c1);
		}
		for(Content c : simplePediaTexts){
			//simplePedia second important, stuff not in wikipedia
			
			if(wikiPediaTexts.contains(c)){
				copyWikiPediaTexts.remove(c);
			}
		}
		wikiPediaTexts = copyWikiPediaTexts;
	}
	
	/**
	 * create PowerPoint with all slide groups
	 */
	public byte[] createPowerPoint(){
		this.powerpoint = new PowerPoint(this.name);
		byte[] pptxData = null;
		
		for(SlideGroup sg : slideGroups){
			powerpoint.addSlides(sg);
		}
		try{
			pptxData = powerpoint.toByteArray();
		}catch(Exception e){
			System.err.println("Powerpoint could not be saved: ");
			e.printStackTrace();
			pptxData = null;
		}
		return pptxData;
	}
	
	/**
	 * Creates an list of Properties for a person, 
	 * for use as depthSearchProperties in complex Properties. 
	 * Includes abstract, picture and (optional) a party, title 
	 * or similar (will appear below the name on the slide).
	 * @param nameParty DBPedia search term for an organization the person belongs to, e.g. "party"; "" if none desired 
	 * @param cleanNameParty name of the organization that is used for slides, e.g. "Party"; "" if none desired
	 * @return ArrayList<Property>, ready to be added as depthSearchProperties
	 */
	protected static ArrayList<Property> personList(String nameParty, String cleanNameParty) {
		ArrayList<Property> list = new ArrayList<Property>();
		list.add(new TextProperty("abstract", "Abstract", false, false, "dbo:", 1));
		list.add(new Property("thumbnail","Image", 1, false, false, "dbo:"));	
		
		if(nameParty.length() > 0 && cleanNameParty.length() > 0)
			list.add(new Property(nameParty, cleanNameParty, false, true, "dbo:"));
		
		return list;
	}
	
	/**
	 * Creates an list of Properties for a person, 
	 * @return ArrayList<Property>, ready to be added as depthSearchProperties
	 */
	protected static ArrayList<Property> personList() {
		ArrayList<Property> list = new ArrayList<Property>();
		list.add(new TextProperty("abstract", "Abstract", false, false, "dbo:", 1));
		list.add(new Property("thumbnail","Image", 1, false, false, "dbo:"));		
		return list;
	}
	
	/**
	 * helping method, to find out about all information in city
	 * @return String
	 */
	@Override
	public String toString(){
		String s = "";
		s += "#######################################\n";
		s += "         All slideGroup names:         \n\n";
		for(SlideGroup sg : slideGroups){
			s += " -> \"" + sg.getName() + "\" > ";
			for(int i = 0; i < sg.getProperties().size(); i++){
				Property p = sg.getProperties().get(i);
				if( i < sg.getProperties().size()-1){
					s += p.getName() + ", ";
				}else{
					s += p.getName();
				}
				
			}
			s += "\n";
		}
		s += "\nAmount of groups: " + slideGroups.size() + "\n";
		s += "\n#######################################\n";
		s += "\nProperties for this mainData: \n\n";
		
		s += printProperties(getPropertiesAsList(), "");
		s +=  "\n#######################################\n\n";
		s += "Images for an extra slide:\n";
		s += printImpressionLinks();
		s += "\n#######################################\n";
		s += "\nContent from SimplePedia: \n\n";
		s += printSimplePediaContent();
		
		s += "\n#######################################\n";
		s += "\nContent from WikiPedia: \n\n";
		s += printWikiPediaContent();
		
		s += "\n#######################################\n";
		s += "\nMap:\n";
		s += printMap();
		
		s += "\n#######################################\n";
		s += "\nBattleMap:\n";
		s += printBattleMap();
		return s;
	}
	
	/**
	 * create String of cleaned properties
	 * @param propertyList
	 * @param add, used for recursion, calculate how many tabulators are needed
	 * @return cleaned string
	 */
	public String printProperties(List<Property> propertyList, String add){
		String s = "";
		for(Property p : propertyList){
			s += add + "> " + p.getName() + " -> ";
			
			if(!p.getComplex()){ 	// print not-complex propertoes 
				for(String values : p.getValues()){
					if(p instanceof TextProperty){
						s += "("+values.length()+") ";
					}
					s += values + " | ";
				}
				if(p.getName().equals("strength")){
					if(this instanceof BattleData){
						s += "~ " + ((BattleData)this).getStrengthNumber();
					}
				}
				if(p.getValues().size() == 0){
					s += "size() = 0";
				}
				s += "\n";
			}else{	// print complex properties 
				s += "\n";
				for(String key : p.getComplexValues().keySet()){
					s += add + "\t" + key + "\n";
					s += printProperties(p.getComplexValues().get(key), add + "\t\t");
				}
				if(p.getComplexValues().size() == 0){
					s += add + "\t" + "size() = 0 \n";
				}
			}
		}
		return s;
	}
	
	/**
	 * outs all links for extra images into a formated String
	 * @return links as String
	 */
	public String printImpressionLinks(){
		String s = "";
		int counter = 0;
		
		s += "\nFlickr Images:\n";
		for(String link : impressionLinks1){
			link += "Image " + counter++ + ": " + link + "\n";
		}
		counter = 0;
		
		s += "\nWikimedia Images:\n";
		for(String link : impressionLinks2){
			s += "Image " + counter++ + ": " + link + "\n";
		}
		counter = 0;
		
		s += "\n";
		for(String link : impressionLinks3){
			s += "Image " + counter++ + ": " + link + "\n";
		}
		counter = 0;
		
		return s;
	}
	
	/**
	 * put content from SimplePedia in a formated String
	 * @return contents as String
	 */
	public String printSimplePediaContent(){
		String s = "";
		if(simplePediaTexts == null) return "No SimplePedia found";
		for(Content c : simplePediaTexts){
			s += c.getTitle() + "\n";
			for(String bullet : c.getText()){
				s += "\t- " + bullet + "\n";
			}
			
		}
		return s;
	}
	
	/**
	 * put content from SimplePedia in a formated String
	 * @return contents as String
	 */
	public String printWikiPediaContent(){
		String s = "";
		if(wikiPediaTexts == null) return "No WikiPedia found";
		for(Content c : wikiPediaTexts){
			s += c.getTitle() + "\n";
			for(String text : c.getText()){
            	s += text + "\n";
            }
            s += "\n";
			
		}
		return s;
	}
	
	/**
	 * print information of a map
	 * @return
	 */
	public String printMap(){
		String s = "";
		
		if(mapImageLink != null) 							s += "\tSource: " + mapImageLink + "\n";
		if(mapImageByte != null) 							s += "\tSize: " + mapImageByte.length + "Byte\n";
		if(mapImageByte == null && mapImageLink == null) 	s += "\tNo Map required";
		
		return s;
	}
	
	/**
	 * print special information of a battle map
	 * @return
	 */
	public String printBattleMap(){
		String s = "";
		
		s += "\tNo Battle Map\n";
		return s;
	}
	
	/**
	 * find slideGroup by name
	 * @param groupName
	 * @return slideGroup, if not found: null
	 */
	public SlideGroup getSlideGroup(String groupName){
		for(SlideGroup sg : slideGroups){
			if(sg.getName().toLowerCase().equals(groupName.toLowerCase())){
				return sg;
			}
		}
		return null;
	}
	
	//==========================================================================
	//Get- and Set Methods 
	public String getName(){
		return name;
	}
	public String getCleanName(){
		return cleanName;
	}
	public List<SlideGroup> getSlideGroups(){
		return slideGroups;
	}
	public PowerPoint getPowerPoint(){
		return powerpoint;
	}
	public Property[] getProperties(){
		return properties;
	}
	public List<Property> getPropertiesAsList(){
		return Arrays.asList(properties);
	}
	public HashSet<String> getImpressionLinks1(){
		return new HashSet<String>(impressionLinks1);
	}
	public HashSet<String> getImpressionLinks2(){
		return new HashSet<String>(impressionLinks2);
	}
	public HashSet<String> getImpressionLinks3(){
		return new HashSet<String>(impressionLinks3);
	}
	public ArrayList<Content> getSimplePediaTexts(){
		return simplePediaTexts;
	}
	public ArrayList<Content> getWikiPediaTexts(){
		return wikiPediaTexts;
	}
	public byte[] getMapImageByte(){
		return mapImageByte;
	}
	public String getMapImageLink(){
		return mapImageLink;
	}
	public String getSimplePediaSource(){
		return this.simplePediaSource;
	}
	public String getWikiPediaSource(){
		return this.wikiPediaSource;
	}
}