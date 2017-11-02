/**
 * PowerPoint
 * Date: 28.03.2017
 * writes information from SlideGroup onto slides in a XMLSlideShow
 * 
 * @author  Johannes Braeuer
 */

package com.swt.aprt17.Auto_Slides.Presentation;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import org.apache.poi.xslf.usermodel.XMLSlideShow;

import com.swt.aprt17.Auto_Slides.Presentation.Slides.Slide;

public class PowerPoint extends XMLSlideShow {

	/**
	 * the title of the .pptx file
	 */
	private String title;
	
	/**
	 * increased for every added slide
	 */
	private int count = 0;
	
	/**
	 * constructor method
	 * @param title title of the presentation
	 */
	public PowerPoint(String title) {
		this.title = title;
	}
	
	/**
	 * calls method addSlide for every slide in the given SlideGroup
	 * @param SlideGroup a SlideGroup
	 */
	public void addSlides(SlideGroup slides) {
		for (int i = 0; i < slides.getSlideCount(); i++) {
			addSlide(slides.getSlide(i));
		}
	}

	/**
	 * writes information from a slide onto the next slide in the XMLSlideshow
	 * @param Slide slide to be added
	 */
	public void addSlide(Slide slide) {
		count++;
		slide.create(this);
	}
	
	/**
	 * returns the XSLFSlideShow as a byte array
	 * @return byte[] data of the XMLSlideShow
	 * @throws IOException
	 */
	public byte[] toByteArray() throws IOException{
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		this.write(baos);
		return baos.toByteArray();
	}
	
	/**
	 * get title
	 * @return String title of the presentation
	 */
	public String getTitle(){
		return title;
	}
	
	/**
	 * get count
	 * @return int number of slides in the XMLSlideShow
	 */
	public int getCount(){
		return count;
	}
}
