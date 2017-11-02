/**
 * TextSlide
 * Date: 18.03.2017
 * stores the content of a text slide
 * 
 * @author  Johannes Braeuer
 */

package com.swt.aprt17.Auto_Slides.Presentation.Slides;

import java.awt.Rectangle;
import java.util.Set;

import org.apache.poi.xslf.usermodel.SlideLayout;

import com.swt.aprt17.Auto_Slides.Presentation.PowerPoint;

public class TextSlide extends Slide {

	/**
	 * String object that shows up in the header on the left
	 */
	private String headerTitle;
	
	/**
	 * constructor method
	 * @param title title of the slide
	 * @param text text of the slide
	 * @param sources sources of the slide
	 * @param headerTitle title of the slide that shows up in the header
	 */
	public TextSlide(String title, String text, Set<String> sources, String headerTitle){
		this.title = title;
		this.textString = text;
		this.sources = sources;
		this.headerTitle = headerTitle;
	}
	
	@Override
	public void create(PowerPoint pres){
		slide = pres.createSlide(pres.getSlideMasters().get(0).getLayout(SlideLayout.BLANK));
		
		double fontSizeTitle = calculateFontSize(title.length(), 20, 44.0, 32, 31.0);
		addTitle(title, fontSizeTitle, true);
		
//		double fontSize = calculateFontSize((textString.length(), 200, 28.0, 1000, 12.0);
		double fontSize = calcAreaFontSize(textString.length(), 648, 356, 12.0, 28.0);
		addText(textString, fontSize, null, new Rectangle(36, 118, 648, 356));

		addLines();
		addHeader(pres, headerTitle);
		addFooter(pres);
		addNote(sources, pres);
	}
}
