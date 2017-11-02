/**
 * ContentSlide
 * Date: 27.03.2017
 * stores the table of content
 * 
 * @author  Johannes Braeuer
 */

package com.swt.aprt17.Auto_Slides.Presentation.Slides;

import java.awt.Rectangle;
import java.util.List;

import org.apache.poi.xslf.usermodel.SlideLayout;

import com.swt.aprt17.Auto_Slides.Presentation.PowerPoint;

public class ContentsSlide extends Slide {
	
	
	/**
	 * used for the numbering of the bullet points on the contents slide
	 */
	private int pointCount;
	
	/**
	 * constructor method
	 * @param bullets bullets of the slide
	 * @param pointCount the numbering will start here
	 */
	public ContentsSlide(List<String> bullets, int pointCount){
		this.title = "Table of Contents";
		this.bullets = bullets;
		this.pointCount = pointCount;		//only needed if the bullet points on the contents slide shall be numbered
		
	}
	
	@Override
	public void create(PowerPoint pres){
		slide = pres.createSlide(pres.getSlideMasters().get(0).getLayout(SlideLayout.BLANK));
		
		addTitle(title, 44.0, true);
//		addText(text, 28.0, null, new Rectangle(36, 108, 648, 364));
		addText(bullets, 28.0, null, new Rectangle(50, 108, 634, 364));
		addLines();
		addHeader(pres, title);
		addFooter(pres);
		addNote("", pres);
	}
	
	public int getPointCount(){
		return pointCount;
	}
}
