/**
 * SlideGroup
 * Date: 28.03.2017
 * stores all slides in a list
 * 
 * @author  Johannes Braeuer
 */

package com.swt.aprt17.Auto_Slides.Presentation;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import com.swt.aprt17.Auto_Slides.Presentation.Slides.BulletsSlide;
import com.swt.aprt17.Auto_Slides.Presentation.Slides.ContentsSlide;
import com.swt.aprt17.Auto_Slides.Presentation.Slides.ImpressionSlide;
import com.swt.aprt17.Auto_Slides.Presentation.Slides.IntroPicSlide;
import com.swt.aprt17.Auto_Slides.Presentation.Slides.IntroSlide;
import com.swt.aprt17.Auto_Slides.Presentation.Slides.PictureSlide;
import com.swt.aprt17.Auto_Slides.Presentation.Slides.Slide;
import com.swt.aprt17.Auto_Slides.Presentation.Slides.SourceSlide;
import com.swt.aprt17.Auto_Slides.Presentation.Slides.TextPicSlide;
import com.swt.aprt17.Auto_Slides.Presentation.Slides.TextSlide;
import com.swt.aprt17.Auto_Slides.Presentation.Slides.TitleSlide;
import com.swt.aprt17.Auto_Slides.Sources.DBPedia.Properties.Property;

public class SlideGroup {
	
	/**
	 * name of the SlideGroup
	 */
	private String name;

	/**
	 * List where the slides are stored
	 */
	private List<Slide> slides = new ArrayList<Slide>();
	
	/**
	 * List where the properties of the slide group are stored
	 */
	private List<Property> properties = new ArrayList<Property>();
	
	/**
	 * used for the numbering of the bullet points on the contents slide
	 */
	private int pointCount = 1;
	
	/**
	 * constructor method
	 * @param name name of the slide group
	 */
	public SlideGroup(String name){
		this.name = name;
	}
	
	/**
	 * add a property to the list
	 * @param property property to be added to the list
	 */
	public void add(Property property){
		properties.add(property);
	}
	
	/**
	 * stores title and subtitle for a title slide
	 * @param title title of the presentation
	 * @param text topic of the presentation e.g. "city"
	 */
	public void newTitleSlide(String title, String text) {
		slides.add(new TitleSlide(title, text));
	}
	
	/**
	 * stores text and their sources for an intro slide
	 * @param text text of the slide
	 * @param sources sources of the slide
	 */
	public void newIntroSlide(String text, Set<String> sources) {
		slides.add(new IntroSlide(text, sources));
	}

	/**
	 * stores text and image for an intro picture slide
	 * @param text text of the slide
	 * @param imageURLString source link of the image
	 * @param sources sources of the slide
	 */
	public void newIntroPicSlide(String text, String imageURLString, Set<String> sources) {
		slides.add(new IntroPicSlide(text, imageURLString, sources));
	}
	
	/**
	 * stores text (contents) for a table of contents slide
	 * automatically adds an additional slide, if there are more than 6 bullets on a slide
	 * @param bullets list of bullets
	 */
	public void newContentsSlide(List<String> bullets){
		
		int cutOff = cutOff(bullets, 8, 440);
		
		slides.add(new ContentsSlide(bullets.subList(0, cutOff), pointCount));
		if(bullets.size() > cutOff){
			pointCount += cutOff;
			newContentsSlide(bullets.subList(cutOff, bullets.size()));
		}
	}
	
	/**
	 * stores six picture URLs for an impression slide
	 * @param imageURLStrings source link of the image
	 */
	public void newImpressionSlide(Set<String> imageURLStrings, int index){
		slides.add(new ImpressionSlide(imageURLStrings, index));
	}
	
	/**
	 * stores title and text for a text slide
	 * automatically adds an additional slide, if there are more than 440 symbols on a slide
	 * @param title title of the slide
	 * @param text text on the slide
	 * @param sources sources of the slide
	 * @param headerTitle title placed in the header
	 */
	public void newTextSlide(String title, String text, Set<String> sources, String headerTitle){
		int cutOff = cutOff(text, 700);
		
		if(cutOff > 0){
			slides.add(new TextSlide(title, text.substring(0, cutOff), sources, headerTitle));
			newTextSlide(title, text.substring(cutOff), sources, headerTitle);
		}else{
			slides.add(new TextSlide(title, text, sources, headerTitle));
		}
	}
	
	/**
	 * stores title and bullets for a bullets slide
	 * automatically adds an additional slide, if there are more than 5 bullets on a slide
	 * @param title title of the slide
	 * @param bullets list of bullets
	 * @param sources sources of the slide
	 * @param headerTitle title placed in the header
	 */
	public void newBulletsSlide(String title, List<String> bullets, Set<String> sources, String headerTitle) {

		int cutOff = cutOff(bullets, 5, 400);
		
		slides.add(new BulletsSlide(title, bullets.subList(0, cutOff), sources, headerTitle));
		if(bullets.size() > cutOff){
			newBulletsSlide(title, bullets.subList(cutOff, bullets.size()), sources, headerTitle);
		}
	}
	
	/**
	 * stores title, text and pictureURL for a text picture slide
	 * @param type type of the person, placed in the header
	 * @param title name of the person
	 * @param party party of the person, placed beneath the name
	 * @param text person info
	 * @param imageURLString source link of the image
	 * @param sources sources of the slide
	 */
	public void newTextPicSlide(String type, String title, String party, String text, String imageURLString, Set<String> sources){
		slides.add(new TextPicSlide(type, title, party, text, imageURLString, sources));
	}

	/**
	 * stores title and picture URL for a picture slide
	 * @param title title of the slide
	 * @param imageURLString source link of the image
	 */
	public void newPictureSlide(String title, String imageURLString) {
		slides.add(new PictureSlide(title, imageURLString));
	}
	
	/**
	 * stores title and image data for a picture slide
	 * @param title title of the slide
	 * @param image byte array that contains the image data
	 * @param imageURLString source link of the image
	 */
	public void newPictureSlide(String title, byte[] image, String imageURLString){
		slides.add(new PictureSlide(title, image, imageURLString));
	}
	
	/**
	 * if newSourceSlide is called with a Set
	 * @param title title of the slide
	 * @param bullets list of bullets
	 */
	public void newSourceSlide(String title, Set<String> bullets){
		newSourceSlide(title, new ArrayList<String>(bullets));
	}
	
	/**
	 * stores bullets (sources) for a source slide
	 * automatically adds an additional slide, if there are more than 6 bullets on a slide
	 * @param title title of the slide
	 * @param bullets list of bullets
	 */
	public void newSourceSlide(String title, List<String> bullets){
		
		int cutOff = cutOff(bullets, 11, 1300);
		
		slides.add(new SourceSlide(title, bullets.subList(0, cutOff)));
		if(bullets.size() > cutOff){
			newSourceSlide(title, bullets.subList(cutOff, bullets.size()));
		}
	}
	
	/**
	 * to calculate where to split a list of bullets points so it doesn't get too long for one slide
	 * ex: lst = {"abc","defg","h"), cutOff(lst, 3, 6) = 1, cutOff(lst, 1, 8) = 1   
	 * @param bullets List of bullet points
	 * @param maxBullets maximum amount of bullet points desired on a slide
	 * @param maxLetters maximum amount of letters desired on a slide
	 * @return index of the bullet point that would put more letters or bullet points on the slide than desired
	 */
	public int cutOff(List<String> bullets, int maxBullets, int maxLetters){
		int cutOff = bullets.size();
		
		/* check number of bullets */
		if(cutOff == maxBullets + 1)  maxBullets--;		// if there is just one more bullet than allowed, make the cut one bullet earlier
		if(cutOff > maxBullets) cutOff = maxBullets;	// cutOff = min(cutOff, maxBullets)
		
		/* check number of letters */
		int allLetters = 0;
		int i = 0;
		while(i < cutOff){
			allLetters += bullets.get(i).length();
			if (allLetters >= maxLetters) cutOff = i;
			i++;
		}
		
		if(cutOff < 1) return 1;
		return cutOff;
	}
	
	/**
	 * to calculate where to split the text, so it has at most a length of maxLetters
	 * and so it's split at the End of a sentence
	 * @param text the text that will be split
	 * @param maxLetters maximum amount of letters desired on a slide
	 * @return index of the last occurrence of the sequence ". " before maxLetters or -1 if the text is not as long as maxLetters
	 */
	public int cutOff(String text, int maxLetters){
		if(text.length() >= maxLetters) return text.substring(0, maxLetters).lastIndexOf(". ") + 2;		//plus 2 because the length of ". " is 2
		else return -1;
	}

	/**
	 * get the amount of slides stored
	 * @return int amount of slides in this SlideGroup
	 */
	public int getSlideCount(){
		return slides.size();
	}
	
	/**
	 * returns a Property with a specific name and null if there is none
	 * @param propertyName name of the desired property
	 * @return Property prop, if the name is the same, null else
	 */
	public Property getProperty(String propertyName){
		for(Property prop : properties){
			if(prop.getName().equals(propertyName)){
				return prop;
			}
		}
		return null;
	}

	/**
	 * get the property list
	 * @return List<Property> list of properties
	 */
	public List<Property> getProperties(){
		return properties;
	}
	
	/**
	 * get slide x
	 * @param index specified index of the desired slide
	 * @return Slide slide of a specified index
	 */
	public Slide getSlide(int index) {
		return slides.get(index);
	}
	
	/**
	 * get name
	 * @return String name of the SlideGroup
	 */
	public String getName(){
		return name;
	}
}
