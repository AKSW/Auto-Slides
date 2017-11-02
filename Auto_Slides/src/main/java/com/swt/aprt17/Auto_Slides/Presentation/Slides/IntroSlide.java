/**
 * IntroSlide
 * Date: 27.03.2017
 * stores the introduction sentence(s)
 * 
 * @author  Johannes Braeuer
 */

package com.swt.aprt17.Auto_Slides.Presentation.Slides;

import java.awt.Color;
import java.awt.Rectangle;
import java.util.Set;

import org.apache.poi.xslf.usermodel.SlideLayout;

import com.swt.aprt17.Auto_Slides.Presentation.PowerPoint;

public class IntroSlide extends Slide{

	/**
	 * constructor method
	 * @param text text of the slide
	 * @param sources sources of the slide
	 */
	public IntroSlide(String text, Set<String> sources){
		this.textString = text;
		this.sources = sources;
	}
	
	@Override
	public void create(PowerPoint pres){
		slide = pres.createSlide(pres.getSlideMasters().get(0).getLayout(SlideLayout.BLANK));
		
		addText("Intro", 14.0, Color.GRAY, new Rectangle(45, 45, 320, 50));
		addText(bullets, 28.0, Color.DARK_GRAY, new Rectangle(54, 180, 612, 130));
		addLines();
		addHeader(pres, "Intro");
		addFooter(pres);
		addNote(sources, pres);
	}
}
