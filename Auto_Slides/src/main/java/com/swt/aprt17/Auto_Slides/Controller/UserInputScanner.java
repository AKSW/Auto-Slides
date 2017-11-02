/**
 * ResourceScanner
 * Date: 07.04.2017
 * user interface 
 * 
 * @author  Francesco Mandry
 */

package com.swt.aprt17.Auto_Slides.Controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import com.swt.aprt17.Auto_Slides.Controller.Finder.BattleFinder;
import com.swt.aprt17.Auto_Slides.Controller.Finder.CityFinder;
import com.swt.aprt17.Auto_Slides.Controller.Finder.Finder;

public class UserInputScanner {
	
	/**
	 * open channel between user and program
	 */
	static Scanner sc = new Scanner(System.in);

	/**
	 * list of [topic and title]s of the presentations the user wants 
	 */
	static List<String []> listOfTopicTitlePairs = new ArrayList<String []>();
	
	/**
	 * constructor
	 * calls on main method of this class
	 */
	public UserInputScanner() {
		System.out.println("Welcome to AutoSlides.");	
		mainMenu();
	}
	
	//=====================================================================================================
	
	/**
	 * saves the topics and titles chosen by the user in listOfTopicTitlePairs
	 */
	public void mainMenu() {
		while(true){

			String [] pair = selectPair();
			
			// pair[1] == null -> user aborted selection, see selectTitle(...)
			if(pair[1] != null) {
				if(isInList(pair)) {
					System.out.println("Nothing to save: "+pair[0]+" has already been selected.");
				}
				else {
				listOfTopicTitlePairs.add(pair);
				System.out.println("The selected "+pair[0]+" was successfully saved.");
				}
			}
			System.out.print("\n"
					+ "[0] create more presentations\n"
					+ "[1] end selection\n"
					+ "Your choice: ");
			
			String mainMenuSelection = readNumIn(1);
			
			if(mainMenuSelection.equals("1")){
				break;
			}
		}
	}
	
	//=====================================================================================================
	
	/**
	 * calls on methods to interact with user
	 * @return String[2] [topic, title] chosen by the user 
	 */
	public String[] selectPair() {
		
		String [] selectedPair = new String[2];
		
		String topic = selectTopic();
		selectedPair[0] = topic;
		
		String title = selectTitle(selectedPair[0]);
		selectedPair[1] = title;
		
		return selectedPair;
	}
	
	//=====================================================================================================
	
	/**
	 * prints list of topics on screen; user chooses topic of presentation
	 * @return String the topic
	 */
	public String selectTopic() {
		System.out.print("\n"
				+ "Please select a topic.\n"
				+ "[0] city 	\n"
				+ "[1] battle	\n"
				+ "Your choice: ");
	
		String topicSelection = readNumIn(1);
	
		String topic = "";
	
		if(topicSelection.equals("0") ){ topic = "city";}
		else if(topicSelection.equals("1") ){ topic = "battle";}
		
		return topic;
	}
	
	//=====================================================================================================
	
	/**
	 * user inputs the title of the presentation (e.g. "Leipzig")
	 * @param topic as chosen in selectTopic()
	 * @return String the title of the presentation as entered by the user
	 */
	public String selectTitle(String topic) {
		while(true) {
			System.out.print("\n"
					+ "Choose title.\n"
					+ "Enter a search term (separate words with spaces, use umlauts); or\n"
					+ "[0] enter a Wikipedia/DBPedia link\n"
					+ "[1] return to main menu\n"
					+ "Your choice: ");
		
			String titleInputSelection = sc.nextLine();
			String title;
			
			if(titleInputSelection.equals("0")){
				title = selectWithLink();
				return title;
			}
			
			if(titleInputSelection.equals("1") ){ 
				
				System.out.println("Returning to main menu ...");
				return null;
			}
			
			/* if search term was given */
			title = selectWithFinder(topic, titleInputSelection);
			
			if(title == null || !title.equals("//TRYAGAIN")) {
				return title;
			}
		}
	}
	
	//=====================================================================================================
	
	/**
	 * lets a user enter a DBPedia link and checks if it is valid 
	 * @param topic the topic the link is about, e.g. "city"
	 * @return String the title of the link entered by the user, e.g. "Leipzig"
	 */
	public String selectWithLink() {
		while(true){
			System.out.print("Please use an English source.\n"
					+ "Enter the link: ");
			
			String linkInput = sc.nextLine();
			
			String wikiPageTitle = "";
			
			if(Finder.DBPediaExistenceCheck(linkInput)){
				
				wikiPageTitle = Finder.cleanLink(linkInput);
				System.out.println("\n"
						+ "Found: "+wikiPageTitle);
				return wikiPageTitle;
			}
			else{
				System.out.print("\n"
						+ "Page not found.\n"
						+ "[0] try again\n"
						+ "[1] return to main menu\n"
						+ "Your choice: ");
				
				String linkNotFoundSelection = readNumIn(1);
				
				if(linkNotFoundSelection.equals("1") ){
					
					System.out.println("Returning to main menu ...");
					return null;
				}
			}
		}
	}
	
	//=====================================================================================================
	
	/**
	 * allows user to select the correct title among the ones found in DBPedia matching his input
	 * @param topic the topic at hand
	 * @param title the title as entered by the user
	 * @return the title as defined in DBPedia, usable for package GetInformationFromDBPedia 
	 * or null if search unsuccessful aborted by user 
	 * or //TRYAGAIN if user wants to try again
	 */
	public String selectWithFinder(String topic, String title) {
		TimedPeriodOutputter tpo = new TimedPeriodOutputter();
		System.out.print("Searching ");
		tpo.start(); /* start outputter */
		
		String [] proposals = null;							// cities found in DBPedia matching the user input
		String [] proposalsWithCountry = null;				// same cities with countries, to be printed for the user
		
		/* using the correct Finder and running query */
		if(topic.equals("city")) { 
			Finder finder = new CityFinder( title );
			finder.createSearchQuery();
			finder.runSearchQuery();
			proposals = finder.getResultStringArray();
			proposalsWithCountry  = finder.getResultPrintStringArray();
		}
		else if(topic.equals("battle")) { /* other topics */
			Finder finder = new BattleFinder( title );
			finder.createSearchQuery();
			finder.runSearchQuery();
			proposals = finder.getResultStringArray();
			proposalsWithCountry  = finder.getResultPrintStringArray();
 		}
		
		tpo.stop(); /* stop outputter */
		
		if(proposals.length == 0){
		
			System.out.print("\n"
					+ "\nSearch term gave no result.\n"
					+ "[0] try again\n"
					+ "[1] return to main menu\n"
					+ "Your choice: ");
			
			String noSearchResultSelection = readNumIn(1);
			
			if(noSearchResultSelection.equals("1") ){ 	
				System.out.println("Returning to main menu ...\n");
				return null;
			}
			else {
				return "//TRYAGAIN";
			}
			
		}
		else if(proposals.length == 1){
			System.out.print("\n"
					+ "\nIs " + proposalsWithCountry[0] + " the " + topic + " you are looking for?\n"
					+ "[0] yes\n"
					+ "[1] no, try again\n"
					+ "Your choice: ");
			
			String oneProposalSelection = readNumIn(1);
			
			if(oneProposalSelection.equals("0")){
				return proposals[0];
			}
			else {
				return "//TRYAGAIN";
			}
			
		}
		else{
			System.out.println("\n\nSearch term had more than one match.");
			
			for(int i = 0; i < proposals.length; i++){	
				System.out.println("["+i+"] "+proposalsWithCountry[i]);
			}
			System.out.println("["+( proposals.length )+"] not mentioned");		
			System.out.print("Your choice: ");
			
			String multiProposalSelection = readNumIn(proposals.length);
			int parsedNum = Integer.parseInt(multiProposalSelection);
			
			if(parsedNum == proposals.length){
				
				System.out.print("[0] try again\n"
						+ "[1] return to main menu\n"
						+ "Your choice: ");
				
				String notMentionedSelection = readNumIn(1);
				
				if(notMentionedSelection.equals("1") ){ 
					System.out.println("Returning to main menu ...");
					return null;
				}
				else {
					return "//TRYAGAIN";
				}
				
			}
			else{
			
				return proposals[parsedNum];
			}
		}
	}
	
	//=====================================================================================================
	
	/**
	 * Checks if a chosen topic/title is already in the list of presentations. 
	 * @param pair the topic/title pair to be added
	 * @return true, if the pair is already in listOfTitleTopicPairs; false otherwise
	 */
	private boolean isInList(String[] newPair) {
		String [] listedPair;
		for(int i = 0; i < listOfTopicTitlePairs.size(); i++) {
			listedPair = listOfTopicTitlePairs.get(i);
			if(listedPair[1].equals(newPair[1]) && listedPair[0].equals(newPair[0])) {
				return true;
			}
		}
		return false;
	}
	
	//=====================================================================================================
	
	/**
	 * checks whether a number entered by a user is valid (in range, integer) and converts it to String
	 * @param max the highest number the user can enter
	 * @return String the number entered
	 */
	public String readNumIn(int max) {
		
		String numIn = "-1";
		
		while(true){
			
			boolean breakable = false;
			
			numIn = sc.nextLine();
			
			for(int i = 0; i < max+1; i++){
				
				String testString = Integer.toString(i);
				
				if(numIn.equals(testString)){ breakable = true;}
			}
			
			if(breakable){
				
				break;
			}
			
			System.out.print("Input must be a number between 0 and "+max+". Try again: ");
		}
		
		return numIn;
	}
	
	//=====================================================================================================
	
	/**
	 * getter: returns list of topics and titles
	 * @return list of topics and titles
	 */
	public List<String[]> getListOfPairs(){
		
		return listOfTopicTitlePairs;
	}
}