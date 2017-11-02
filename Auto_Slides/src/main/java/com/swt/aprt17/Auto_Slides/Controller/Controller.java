/**
 * Controller
 * Date: 17.03.2017
 * connect all classes together
 * 
 * @author  Francesco Mandry
 */

package com.swt.aprt17.Auto_Slides.Controller;


import java.util.List;

import com.swt.aprt17.Auto_Slides.Controller.ZipArchiver.Zip;
import com.swt.aprt17.Auto_Slides.Topics.Data.BattleData;
import com.swt.aprt17.Auto_Slides.Topics.Data.CityData;
import com.swt.aprt17.Auto_Slides.Topics.Data.MainData;
import com.swt.aprt17.Auto_Slides.Topics.SlideFactories.BattleSlideFactory;
import com.swt.aprt17.Auto_Slides.Topics.SlideFactories.CitySlideFactory;
import com.swt.aprt17.Auto_Slides.Topics.SlideFactories.SlideFactory;

public class Controller {
	/*
	 * Zip object containing the presentations
	 */
	private Zip zip;
	/**
	 * runControl
	 * run the input 
	 * 
	 */
	public void runUserInput() {
		UserInputScanner inputScanner = new UserInputScanner(); /* read inputs */
		List<String[]> listOfTopicTitlePairs = inputScanner.getListOfPairs();
		runControl(listOfTopicTitlePairs, true);
	}
	
	/**
	 * runControl
	 * run the file input
	 * 
	 */
	public void runFileInput(String filePath) {
		FileInputScanner inputScanner = new FileInputScanner(filePath); /* read inputs */
		List<String[]> listOfTopicTitlePairs = inputScanner.getListOfPairs();
		runControl(listOfTopicTitlePairs, true);
	}
	
	/**
	 * runControl
	 * run the necessary methods  
	 * 
	 */
	public void runControl(List<String[]> listOfTopicTitlePairs, boolean save) {
		zip = new Zip();
		if(listOfTopicTitlePairs.size() == 0) { /* no presentation needed */
			System.out.println("\nNo title selected. The program will be shut down."); 
			return;
		}
		TimedPeriodOutputter tpo = new TimedPeriodOutputter();
		System.out.print("\nWorking ");
		tpo.start(); /* start outputter */
		for(int i = 0; i < listOfTopicTitlePairs.size(); i++){
			
			String [] topicTitlePair = listOfTopicTitlePairs.get(i);
			String topic_str = topicTitlePair[0];
			String title_str = topicTitlePair[1];
			
			// if no title, skip this presentation and continue with the next one in the list
			if(title_str.equals("")) continue;
			
			String title = title_str.replaceAll(" ", "_");
			
			MainData mainData;
			SlideFactory slideFactory;			
			if(topic_str.equals("city")) {
				mainData = new CityData(title);
				mainData.tapSources();
				slideFactory = new CitySlideFactory(mainData);
				slideFactory.fillSlides();
			} else if(topic_str.equals("battle")) {
				mainData = new BattleData(title);
				mainData.tapSources();
				slideFactory = new BattleSlideFactory(mainData);
				slideFactory.fillSlides();
			}
			else continue;						// shouldn't happen if UserInputScanner.java is properly maintained
			
			byte[] pptxData = slideFactory.getMainData().createPowerPoint();
			
			if(pptxData == null) {
				System.err.print("\nCould not add '"+title_str+"'  "+(i+1)+"/"+listOfTopicTitlePairs.size()+" ");
			}
			else {
				zip.addData(title_str, pptxData); 
				System.out.print("\nAdded '"+title_str+"' "+(i+1)+"/"+listOfTopicTitlePairs.size()+" ");
			}
		}
		/* using the save boolean for test creations */
		if(save == true) {
			if(zip.getData().size() > 0) {
				zip.zipCreate();
				tpo.stop(); /* stop outputter */
				System.out.println("\n\nSuccess."
						+ "\nPath: "+zip.getSave() + "\\"
						+ "\nFile: "+zip.getZName());
			}
			else {
				tpo.stop(); /* stop outputter */
				System.out.println("\n\nTerminated with no created presentation.");
			}
		}
		else {
			tpo.stop(); /* stop outputter */
			System.out.println("\n\nFinished run. (saving deactivated)");
		}
	}
	
	/**
	 * getZip
	 * get the zip object 
	 * 
	 */
	public Zip getZip() {
		return zip;
	}
}
