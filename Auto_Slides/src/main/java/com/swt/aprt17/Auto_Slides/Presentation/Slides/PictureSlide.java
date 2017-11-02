/**
 * PictureSlide
 * Date: 28.03.2017
 * stores the content of a picture slide
 * 
 * @author  Johannes Braeuer
 */

package com.swt.aprt17.Auto_Slides.Presentation.Slides;

import java.awt.Rectangle;

import org.apache.poi.xslf.usermodel.SlideLayout;

import com.swt.aprt17.Auto_Slides.Presentation.PowerPoint;

public class PictureSlide extends Slide {
	
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
	 * @param title title of the slide
	 * @param imageURLString source link of the image
	 */
	public PictureSlide(String title, String imageURLString){
		this.title = title;
		this.imageURLString = imageURLString;
		
		if(!imageURLString.equals("")){
			image = imageURLStringToByteArray(imageURLString);
		}
	}
	
	/**
	 * constructor method
	 * @param title title of the slide
	 * @param image image data
	 * @param imageURLString source link of the image
	 */
	public PictureSlide(String title, byte[] image, String imageURLString){
		this.title = title;
		this.image = image;
		this.imageURLString = imageURLString;
	}
	
	@Override
	public void create(PowerPoint pres){
		slide = pres.createSlide(pres.getSlideMasters().get(0).getLayout(SlideLayout.BLANK));
		
		addTitle(title, 30.0, false);
		addImage(image, pres, 648, 356, 0, 11);
		if(!imageURLString.equals("") && image != null){
			horizontalCenteredFlag = true;
			addText(imageURLString, 6.0, null, new Rectangle(36, 458, 648, 30));
		}
		addLines();
		addHeader(pres, title);
		addFooter(pres);
		addNote(imageURLString, pres);
	}
}
