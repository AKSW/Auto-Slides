/**
 * ImpressionSlide
 * Date: 10.04.2017
 * stores the content of a impression slide
 * 
 * @author Johannes Braeuer
 */

package com.swt.aprt17.Auto_Slides.Presentation.Slides;

import java.awt.Color;
import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.apache.poi.xslf.usermodel.SlideLayout;

import com.swt.aprt17.Auto_Slides.Presentation.PowerPoint;

public class ImpressionSlide extends Slide {

	/**
	 * stores, which impression slide this is
	 */
	private int index;
	
	/**
	 * ArrayList of the data of the images for the impression slides
	 */
	private List<byte[]> images;
	
	/**
	 * constructor method
	 * @param sources souces of the images of the slide
	 * @param index index of the impression slide; will be shown in title
	 */
	public ImpressionSlide(Set<String> sources, int index){
		images = new ArrayList<byte[]>();
		this.sources = sources;
		this.index = index;
		
		for(String url : sources){
			if(!url.equals("")){
				images.add(imageURLStringToByteArray(url));
			}
		}
	}
	
	@Override
	public void create(PowerPoint pres){
		slide = pres.createSlide(pres.getSlideMasters().get(0).getLayout(SlideLayout.BLANK));
		
		if(images.size() > 0) addImage(images.get(0), pres, 210, 200, -220, -105-7);
		if(images.size() > 1) addImage(images.get(1), pres, 210, 200, 0, -105-7);
		if(images.size() > 2) addImage(images.get(2), pres, 210, 200, 220, -105-7);
		if(images.size() > 3) addImage(images.get(3), pres, 210, 200, -220, 105-7);
		if(images.size() > 4) addImage(images.get(4), pres, 210, 200, 0, 105-7);
		if(images.size() > 5) addImage(images.get(5), pres, 210, 200, 220, 105-7);
		
		footerFlag = true;
		smallSpaceFlag = true;
		addText("sources on next slide", 8.0, Color.LIGHT_GRAY, new Rectangle(570, 464, 130, 25));
		addLines();
		addHeader(pres, "Impressions " + index);
		addFooter(pres);
		addNote(sources, pres);
	}
}
