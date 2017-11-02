/**
 * BulletsSlide
 * Date: 01.05.2017
 * stores the content of a bullets slide
 * 
 * @author  Johannes Braeuer
 */

package com.swt.aprt17.Auto_Slides.Presentation.Slides;

import java.awt.Rectangle;
import java.util.List;
import java.util.Set;

import org.apache.poi.xslf.usermodel.SlideLayout;

import com.swt.aprt17.Auto_Slides.Presentation.PowerPoint;

public class BulletsSlide extends Slide {

	/**
	 * String object that shows up in the header on the left
	 */
	private String headerTitle;
	
	/**
	 * constructor method
	 * @param title title of the slide
	 * @param bullets bullets of the slide
	 * @param sources sources of the slide
	 */
	public BulletsSlide(String title, List<String> bullets, Set<String> sources, String headerTitle){
		this.title = title;
		this.bullets = bullets;
		this.sources = sources;
		this.headerTitle = headerTitle;
	}
	
	@Override
	public void create(PowerPoint pres){
		slide = pres.createSlide(pres.getSlideMasters().get(0).getLayout(SlideLayout.BLANK));
		
		double fontSizeTitle = calculateFontSize(title.length(), 20, 44.0, 32, 31.0);
		addTitle(title, fontSizeTitle, true);
		
		int bulletsLength = 0;
		for(String str : bullets) bulletsLength += str.length();
		double fontSize = calculateFontSize(bulletsLength, 110, 28.0, 200, 22.0);
//		double fontSize = calcFontSize(bulletsLength, 648, 356, 12.0, 28.0);
		bulletsFlag = true;
		addText(bullets, fontSize, null, new Rectangle(36, 118, 648, 356));
		
		addLines();
		addHeader(pres, headerTitle);
		addFooter(pres);
		addNote(sources, pres);
	}
}
