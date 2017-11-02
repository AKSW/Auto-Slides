/**
 * SourceSlide
 * Date: 27.03.2017
 * stores sources
 * 
 * @author  Johannes Braeuer
 */

package com.swt.aprt17.Auto_Slides.Presentation.Slides;

import java.awt.Rectangle;
import java.util.List;

import org.apache.poi.xslf.usermodel.SlideLayout;

import com.swt.aprt17.Auto_Slides.Presentation.PowerPoint;

public class SourceSlide extends Slide {
	
	/**
	 * constructor method
	 * @param title title of the slide
	 * @param bullets bullets of the slide
	 */
	public SourceSlide(String title, List<String> bullets){
		this.title = title;
		this.bullets = bullets;
	}
	
	@Override
	public void create(PowerPoint pres){
		slide = pres.createSlide(pres.getSlideMasters().get(0).getLayout(SlideLayout.BLANK));
		
		addTitle(title, 44.0, true);
		bulletsFlag = true;
		addText(bullets, 10.5, null, new Rectangle(36, 118, 648, 356));
		addLines();
		addHeader(pres, "Sources");
		addFooter(pres);
		addNote("", pres);
	}
}
