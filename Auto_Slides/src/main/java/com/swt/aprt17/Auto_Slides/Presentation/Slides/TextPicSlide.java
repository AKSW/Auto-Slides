/**
 * TextPicSlide
 * Date: 07.03.2017
 * stores the content of a text-picture slide
 * 
 * @author  Johannes Braeuer
 */

package com.swt.aprt17.Auto_Slides.Presentation.Slides;

import java.awt.Rectangle;
import java.util.Set;

import org.apache.poi.xslf.usermodel.SlideLayout;

import com.swt.aprt17.Auto_Slides.Presentation.PowerPoint;

public class TextPicSlide extends Slide{
	
	/**
	 * String object of the URL of the image
	 */
	private String imageURLString;
	
	/**
	 * String object that stores the party the title belongs to
	 */
	private String party;
	
	/**
	 * String object that stores the type the title belongs to
	 */
	private String type;
	
	/**
	 * byte array that stores the image data
	 */
	private byte[] image;

	/**
	 * constructor method
	 * @param type type of the person, placed in the header
	 * @param title name of the person
	 * @param party party of the person, placed beneath the name
	 * @param text person info
	 * @param imageURLString source link of the image
	 * @param sources sources of the slide
	 */
	public TextPicSlide(String type, String title, String party, String text, String imageURLString, Set<String> sources){
		this.type = type;
		this.title = title;
		this.party = party;
		this.textString = text;
		this.imageURLString = imageURLString;
		this.sources = sources;
		sources.add(imageURLString);
		
		if(!imageURLString.equals("")){
			image = imageURLStringToByteArray(imageURLString);
		}
	}
	
	@Override
	public void create(PowerPoint pres){
		slide = pres.createSlide(pres.getSlideMasters().get(0).getLayout(SlideLayout.BLANK));
		
		double fontSizeTitle = calculateFontSize(title.length(), 4, 28.0, 36, 15.5);
		double fontSizeText = calculateFontSize(textString.length(), 150, 22.0, 350, 15.5);
		double fontSizeSource = calculateFontSize(imageURLString.length(), 60, 8.0, 260, 5.3);
		
		if(!party.equals("")){
			addText(title, fontSizeTitle, null, new Rectangle(45, 60, 320, 70));
			italicFlag = true;
			addText(party, 14.0, null, new Rectangle(45, 110, 320, 50));
			addText(textString, fontSizeText, null, new Rectangle(45, 160, 320, 270));
		}else{
			addText(title, fontSizeTitle, null, new Rectangle(45, 80, 320, 70));
			addText(textString, fontSizeText, null, new Rectangle(45, 140, 320, 270));
		}
		addImage(image, pres, 300, 390, 160, -16);
		if(!imageURLString.equals("") && image != null){
			smallSpaceFlag = true;
			addText(imageURLString, fontSizeSource, null, new Rectangle(375, 443, 280, 30));
		}
		addLines();
		addHeader(pres, type);
		addFooter(pres);
		addNote(sources, pres);
	}
}
