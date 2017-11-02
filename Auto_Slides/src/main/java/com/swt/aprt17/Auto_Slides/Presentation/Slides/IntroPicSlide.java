/**
 * IntroPicSlide
 * Date: 28.03.2017
 * stores the content of a picture slide
 * 
 * @author Johannes Braeuer
 */

package com.swt.aprt17.Auto_Slides.Presentation.Slides;

import java.awt.Color;
import java.awt.Rectangle;
import java.util.Set;

import org.apache.poi.xslf.usermodel.SlideLayout;

import com.swt.aprt17.Auto_Slides.Presentation.PowerPoint;

public class IntroPicSlide extends Slide {
	
	/**
	 * String object of the URL of the image
	 */
	private String imageURLString;
	
	/**
	 * byte array that stores the image data
	 */
	private byte[] image;

	/**
	 * constructor method
	 * @param text text of the slide
	 * @param imageURLString source link of the image
	 * @param sources sources of the slide
	 */
	public IntroPicSlide(String text, String imageURLString, Set<String> sources){
		this.textString = text;
		this.sources = sources;
		this.imageURLString = imageURLString;
		sources.add(imageURLString);
		
		if(!imageURLString.equals("")){
			image = imageURLStringToByteArray(imageURLString);
		}
	}
	
	@Override
	public void create(PowerPoint pres){
		slide = pres.createSlide(pres.getSlideMasters().get(0).getLayout(SlideLayout.BLANK));
		
//		double fontSize = calculateFontSize(text.length(), 100, 24.0, 412, 14.0);
		double fontSize = calcAreaFontSize(textString.length(), 300, 300, 12.0, 28.0);
		double fontSizeSource = calculateFontSize(imageURLString.length(), 60, 8.0, 260, 5.3);
		
//		addText(introPicSlide, "Intro", 14.0, Color.GRAY, new Rectangle(45, 45, 320, 50));
		verticalCenteredFlag = true;
		addText(textString, fontSize, Color.DARK_GRAY, new Rectangle(45, 115, 300, 300));
		addImage(image, pres, 320, 390, 150, -16);
		if(!imageURLString.equals("") && image != null){
			smallSpaceFlag = true;
			horizontalCenteredFlag = true;
			addText(imageURLString, fontSizeSource, null, new Rectangle(360, 445, 300, 45));
		}
		addLines();
		addHeader(pres, "Intro");
		addFooter(pres);
		addNote(sources, pres);
	}
}
