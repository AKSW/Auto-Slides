/**
 * TitleSlide
 * Date: 18.03.2017
 * stores the content of a title slide
 * 
 * @author  Johannes Braeuer
 */

package com.swt.aprt17.Auto_Slides.Presentation.Slides;

import java.awt.Color;
import java.awt.Rectangle;

import org.apache.poi.xslf.usermodel.SlideLayout;

import com.swt.aprt17.Auto_Slides.Presentation.PowerPoint;

public class TitleSlide extends Slide {

	/**
	 * constuctor method
	 * @param title title of the presentation
	 * @param text topic of the presentation e.g. "city"
	 */
	public TitleSlide(String title, String text) {
		this.title = title;
		this.textString = text;
	}
	
	@Override
	public void create(PowerPoint pres){
		slide = pres.createSlide(pres.getSlideMasters().get(0).getLayout(SlideLayout.BLANK));

		double titleFontSize = calculateFontSize(title.length(), 13, 54.0, 30, 36.0);
		horizontalCenteredFlag = true;
		verticalCenteredFlag = true;
		addText(title, titleFontSize, null, new Rectangle(20, 175, 680, 100));
		horizontalCenteredFlag = true;
		addText(textString, 40.0, Color.GRAY, new Rectangle(54, 260, 612, 80));
		addLines();
		addFooter(pres);
		addNote("", pres);
	}
}
