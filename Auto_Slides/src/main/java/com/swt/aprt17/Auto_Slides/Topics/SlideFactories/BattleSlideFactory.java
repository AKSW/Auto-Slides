/**
 * BattleSlideFactory
 * Date: 29.04.2017
 * 
 * Creates all battle-specific slides; extends SlideFactory.
 * 
 * @author Andrea Niebuhr
 */

package com.swt.aprt17.Auto_Slides.Topics.SlideFactories;

import com.swt.aprt17.Auto_Slides.Topics.Data.*;
import com.swt.aprt17.Auto_Slides.Sources.DBPedia.Properties.*;
import com.swt.aprt17.Auto_Slides.Presentation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;
import java.util.HashSet;

public class BattleSlideFactory extends SlideFactory {
	
	//============================================================================ constructor

	/**
	 * constructor, calls on SlideFactory constructor
	 * @param mainData the Main Data Object with the information this class is supposed to format
	 */
	public BattleSlideFactory(MainData mainData) {
		super(mainData);
		this.topic = "battle";
	}
	
	//============================================================================ main Methods
	
	@Override
	protected void fillTopicSlides() {
		fillIntroSlide();
		fillGeneralInformationSlide();
		fillMapImageSlide();
		fillNumbersSlide();
		fillCommanderSlides();
		fillTroopMovementSlides();
		fillMiscSlides();
		fillImpressionsSlides();
	}
	
	/**
	 * Fills Introduction slide; 
	 * one slide, with picture and text.
	 */
	private void fillIntroSlide() {
		SlideGroup sg = mainData.getSlideGroup("Intro");

		String abs = getValue(sg.getProperty("abstract"), 0);
		String imageURLString = getValue(sg.getProperty("thumbnail"), 0);
		
		if(abs.length() == 0 && imageURLString.length() == 0) return;	// no content, no slide
		
		Set<String> sgSources = collectSourcesFromProperties(sg.getProperties());
		sources.addAll(sgSources);
		sg.newIntroPicSlide(abs, imageURLString, sgSources);
	}
	
	/**
	 * Fills General Information slide; 
	 * one slide, with bullets points on various facts 
	 */
	private void fillGeneralInformationSlide(){
		SlideGroup sg = mainData.getSlideGroup("General Information");
		ArrayList<String> lines = new ArrayList<String>();
		
		/* combatant */
		Property combatant = sg.getProperty("combatant");		
		String combatant_value = listToCommata(combatant);		
		if(combatant_value.length() > 0)
			lines.add(lineWithColon(combatant, combatant_value));
		
		/* isPartOfMilitaryConflict */
		Property ipomc = sg.getProperty("isPartOfMilitaryConflict");
		String ipomc_value = getValue(ipomc, 0);
		if(ipomc_value.length() > 0)
			lines.add(lineWithColon(ipomc, ipomc_value));
				
		/* place */
		Property place = sg.getProperty("place");
		String place_value = listToCommata(place);
		if(place_value.length() > 0)
			lines.add(lineWithColon(place, place_value));
		
		/* result */
		Property result = sg.getProperty("result");
		String result_value = getValue(result, 0);
		if(result_value.length() > 0)
			lines.add(lineWithColon(result, result_value));
		
		/* date */
		Property date = sg.getProperty("date");
		String date_value = getValue(date, 0);
		if(date_value.length() > 0)
			lines.add(lineWithColon(date, formatDate(date_value)));
		
		/* put it together */
		if(lines.size() == 0) return;		// no content, no slide
		
		Set<String> sgSources = collectSourcesFromProperties(sg.getProperties());
		sources.addAll(sgSources);
		sg.newBulletsSlide(sg.getName(), lines, sgSources, sg.getName());
	}
	

	
	/**
	 * Fills numbers slide; 
	 * one slide, with army strength and casualties
	 */
	private void fillNumbersSlide(){
		SlideGroup sg = mainData.getSlideGroup("Numbers");
		
		/* strength */
		Property strength = sg.getProperty("strength");
		if( ! strength.getValues().isEmpty() ) {
			ArrayList<String> strengthValues = strength.getValues();
			
			/* if more than 9 entries: cut to 8 and add "(...)" (+ total strength) --> 2 slides à 5 bullet points */
			if(strengthValues.size() > 9) {
				while (strengthValues.size() > 8) {
					strengthValues.remove(8);
				}
				strengthValues.add("(...)");
			}
			
			/* add total strength (sum of participants)  */
			int strengthTotal = ((BattleData) mainData).getStrengthNumber();
			if (strengthTotal > 0){
				String printStrengthTotal = "total strength: " + thousandSplitter(Integer.toString(strengthTotal));
				strengthValues.add(printStrengthTotal);
			}
			Set<String> strengthSources = new HashSet<String>();
			strengthSources.add(strength.getSource());
			sources.add(strength.getSource());
			sg.newBulletsSlide(strength.getCleanName(), strengthValues, strengthSources, sg.getName());
			
		}

		/* casualties */
		Property casualties = sg.getProperty("causalties");		
		if( ! casualties.getValues().isEmpty() ) {
			ArrayList<String> casValues = casualties.getValues();
			
			/* if more than 9 entries: cut to 8 and add "(...)" (+ total casualties) --> 2 slides à 5 bullet points */
			if(casValues.size() > 9) {
				while (casValues.size() > 8) {
					casValues.remove(8);
				}
				casValues.add("(...)");
			}
			
			/* add total casualties (sum of dead and wounded)  */
			int casTotal = ((BattleData) mainData).getCasualtiesNumber();
			if (casTotal > 0){
				String printCasTotal = "total casualties: " + thousandSplitter(Integer.toString(casTotal));
				casValues.add(printCasTotal);
			}			
			Set<String> casSources = new HashSet<String>();
			casSources.add(casualties.getSource());
			sources.add(casualties.getSource());
			sg.newBulletsSlide(casualties.getCleanName(), casValues, casSources, sg.getName());
		}
	}
	
	/**
	 * Fills commander Slides; 
	 * one person slide per commander for the first four commanders, 
	 * if more than four commanders: also a list of all commanders
	 */
	private void fillCommanderSlides(){
		SlideGroup sg = mainData.getSlideGroup("Commanders");
		Property commanders_p = sg.getProperty("commander");
		
		if(commanders_p.getComplexValues().isEmpty()) return;	// no content, no slide
		
		HashMap<String, ArrayList<Property>> commanders = commanders_p.getComplexValues();
		
		/* if 5+ commanders: add a slide listing all commanders */
		if(commanders.size() > 4){
			ArrayList<String> names = new ArrayList<String>(commanders.keySet());
			HashSet<String> slideSources = new HashSet<String>();
			slideSources.add(commanders_p.getSource());
			
			sg.newBulletsSlide(sg.getName(), names, slideSources, sg.getName());
		}
		
		fillPersonSlide("Commanders", 4);
		
	}
	
	/**
	 * Fills troop movement slides; 
	 * one slide per image
	 */
	private void fillTroopMovementSlides(){
		
		if(((BattleData) mainData).getBattleMap().isEmpty()) return;		// no content, no slide
		
		SlideGroup sg = mainData.getSlideGroup("Troop Movements");
		Set<String> battleMaps = ((BattleData) mainData).getBattleMap();
		for (String map : battleMaps)
			sg.newPictureSlide(sg.getName(), map);
		
		sources.add(((BattleData) mainData).getBattleMapLink());
		
	}
}