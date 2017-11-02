/**
 * CitySlideFactory
 * Date: 27.04.2017
 * Last Update: 27.04.2017
 * Create all cityspecific Slides for every Slidegroup and fill Slides with information using Strings
 * 
 * @author  Francesco Mandry
 */

package com.swt.aprt17.Auto_Slides.Topics.SlideFactories;
import java.util.*;
import com.swt.aprt17.Auto_Slides.Presentation.SlideGroup;
import com.swt.aprt17.Auto_Slides.Sources.DBPedia.Properties.Property;
import com.swt.aprt17.Auto_Slides.Topics.Data.MainData;

public class CitySlideFactory extends SlideFactory {
	
	public CitySlideFactory(MainData mainData) {
		
		super(mainData);
		this.topic = "city";
	}
	
	/**
	 * Method: fillTopicSlides
	 * call up all topic specific methods
	 */
	protected void fillTopicSlides() {
					
		fillIntroSlide(); 							/* Intro */
		fillGeneralInformationSlide(); 				/* General Information */
		fillMapImageSlide();						/* Map Image */
		fillPersonSlide("Leader", 1); 				/* Leader */
		fillPersonSlide("Born in this City", 4); 	/* Persons Born in  */
		fillMiscSlides(); 							/* Miscellaneous */
		fillImpressionsSlides(); 					/* Impression */
	}
	
	/**
	 * Method: fillIntroSlide
	 * Slidegroup: Intro
	 * fill the SlideGroup Intro with IntroPicSlide
	 * @return sources as String
	 */
	public void fillIntroSlide() {
		
		SlideGroup intro = mainData.getSlideGroup("Intro");
		List<Property> introProperties = intro.getProperties();
		
		if(!introProperties.get(0).getValues().isEmpty() && !introProperties.get(1).getValues().isEmpty()){
			
			Set<String> sgSources = collectSourcesFromProperties(introProperties);
			sources.addAll(sgSources);
			intro.newIntroPicSlide(introProperties.get(0).getValues().get(0), introProperties.get(1).getValues().get(0), sgSources); /* creates slide */
		}
	}
	
	/**
	 * Method: fillGeographySlide
	 * Slidegroup: Geography
	 * cleans every single general information property and put them together to one string for the notes
	 * then fill the SlideGroup GeneralInformation with TextSlide(notes)
	 * @return sources as String
	 */
	public void fillGeneralInformationSlide() {
		
		List<String> notes = new ArrayList<String>();
		SlideGroup generalInformation = mainData.getSlideGroup("General Information");
		List<Property> generalInformationProperties = generalInformation.getProperties();
		
		/* Country */
		Property country_p = generalInformationProperties.get(0);
		if(!(country_p.getValues().isEmpty())){ /* check which information is given */
			notes.add("Country: "+country_p.getValues().get(0));
		}
		
		/* Total Population and Population as of */
		if(!(generalInformationProperties.get(1).getValues().isEmpty()) || !(generalInformationProperties.get(2).getValues().isEmpty())) { /* check which information is given */
			
			if(!(generalInformationProperties.get(1).getValues().isEmpty()) && !(generalInformationProperties.get(2).getValues().isEmpty())) {	
				
				String populationAsOf = formatDate(generalInformationProperties.get(2).getValues().get(0));
				
				notes.add("Population: "+thousandSplitter(generalInformationProperties.get(1).getValues().get(0))+" (as of "+populationAsOf+")");
			}else if(!(generalInformationProperties.get(1).getValues().isEmpty()) && generalInformationProperties.get(2).getValues().isEmpty()) {
				
				notes.add("Population: "+thousandSplitter(generalInformationProperties.get(1).getValues().get(0)));
			}	
		}
		
		/* Area Code */
		Property areaCode_p = generalInformationProperties.get(3);
		if(!(areaCode_p.getValues().isEmpty())) { /* check if information is given */
			
			if(areaCode_p.getValues().size() == 2) {
				
				notes.add("Area code: "+areaCode_p.getValues().get(0)+", "+areaCode_p.getValues().get(1));
			}else if(areaCode_p.getValues().size() == 1) {
				
				notes.add("Area code: "+areaCode_p.getValues().get(0));
			}
		}
		
		/* Area Total */
		Property areaTotal = generalInformation.getProperty("areaTotal");
		if(!areaTotal.getValues().isEmpty()) { /* check if information is given */
			
			String tempNote = areaTotal.getCleanName() + ": ";
			for(String v : areaTotal.getValues()) {
				
				long area = 0;
				try {
					
					area = (long)Double.parseDouble(v);
                    area = area/1000000;
                    v = thousandSplitter(String.valueOf(area)) + " km²";
					tempNote += v;
				}catch(Exception e) {
					
					System.err.println("Could not change to square kilometers " + e);
				}
			}
			notes.add(tempNote);
		}
		Set<String> sgSources = collectSourcesFromProperties(generalInformationProperties);
		sources.addAll(sgSources);
		if(!notes.isEmpty()) { /* only create slide if information are given */
			
			generalInformation.newBulletsSlide("General Information", notes, sgSources, generalInformation.getName()); /* create slide */
		}
	}
}
