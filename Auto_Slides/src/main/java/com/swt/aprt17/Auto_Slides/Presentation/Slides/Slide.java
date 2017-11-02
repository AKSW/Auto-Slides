/**
 * Slide
 * Date: 18.03.2017
 * defines slides in general
 * 
 * @author  Johannes Braeuer
 */

package com.swt.aprt17.Auto_Slides.Presentation.Slides;

import java.awt.Color;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.imageio.ImageIO;

import org.apache.poi.sl.usermodel.PictureData.PictureType;
import org.apache.poi.sl.usermodel.TextParagraph.TextAlign;
import org.apache.poi.sl.usermodel.VerticalAlignment;
import org.apache.poi.xslf.usermodel.XSLFAutoShape;
import org.apache.poi.xslf.usermodel.XSLFNotes;
import org.apache.poi.xslf.usermodel.XSLFPictureData;
import org.apache.poi.xslf.usermodel.XSLFPictureShape;
import org.apache.poi.xslf.usermodel.XSLFSlide;
import org.apache.poi.xslf.usermodel.XSLFTextBox;
import org.apache.poi.xslf.usermodel.XSLFTextParagraph;
import org.apache.poi.xslf.usermodel.XSLFTextRun;
import org.apache.poi.xslf.usermodel.XSLFTextShape;

import com.swt.aprt17.Auto_Slides.Presentation.PowerPoint;

public abstract class Slide {
	
	/**
	 * XSLFSlide object of this Slide
	 */
	protected XSLFSlide slide;
	
	/**
	 * title of a slide
	 */
	protected String title;
	
	/**
	 * text of a slide
	 */
	protected String textString;
	
	/**
	 * bullets of a slide
	 */
	protected List<String> bullets;
	
	/**
	 * sources of a slide
	 */
	protected Set<String> sources;
	
	/**
	 * Date object for the date in the footer
	 */
	protected static Date date = new Date();
	
	/**
	 * DateFormat for the date in the footer
	 */
	protected static DateFormat df = DateFormat.getDateInstance();
	
	/**
	 * width of a slide
	 */
	protected final int SLIDEWIDTH = 720;
	
	/**
	 * height of a slide
	 */
	protected final int SLIDEHEIGHT = 540;
	
	/**
	 * insets will be small (for footers)
	 */
	protected boolean footerFlag = false;
	
	/**
	 * font will be italic
	 */
	protected boolean italicFlag = false;
	
	/**
	 * spaces between lines will be small
	 */
	protected boolean smallSpaceFlag = false;
	
	/**
	 * text is horizontally centered
	 */
	protected boolean horizontalCenteredFlag = false;
	
	/**
	 * text is vertically centered
	 */
	protected boolean verticalCenteredFlag = false;
	
	/**
	 * text is alligned to the right
	 */
	protected boolean rightAlignFlag = false;
	
	/**
	 * texts will have bullet points
	 */
	protected boolean bulletsFlag = false;
	
	/**
	 * create a XSLFSlide with a custom layout and add it to the PowerPoint
	 * @param pres presentation the slide will be added to
	 */
	public abstract void create(PowerPoint pres);
	
	/**
	 * resolve the URL to get the image data and return it as byte array
	 * @param imageURLString source link of the image to resolve
	 * @return image data or null, if the link can't be resolved
	 */
	public byte[] imageURLStringToByteArray(String imageURLString){
		try {
			URL imageURL = new URL(imageURLString);
			BufferedImage imageObject = ImageIO.read(imageURL);
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			if(imageObject != null){
				ImageIO.write(imageObject, "png", baos);
				return baos.toByteArray();
			}else{
				return null;
			}
		} catch (MalformedURLException e) {
//			System.out.println("FATAL ERROR: malformed image URL.\n" + imageURLString);
			imageURLString = "";
			return null;
		} catch (IOException e) {
//			System.out.println("FATAL ERROR: resolving image URL failed.\n" + imageURLString);
			imageURLString = "";
			return null;
		} catch (IllegalArgumentException e){
			imageURLString = "";
			return null;
		} catch (Exception e){		// for possible other exceptions
			imageURLString = "";
			return null;
		}
	}
	
	/**
	 * adds a formatted title to a slide
	 * @param title title to be added
	 * @param fontSize font size of the title
	 * @param fontBoldFlag boolean true if the title shall be bold
	 */
	public void addTitle(String title, double fontSize, boolean fontBoldFlag){
		XSLFTextShape titleShape = slide.createTextBox();
		titleShape.setAnchor(new Rectangle(16, 43, 688, 90));
		titleShape.setHorizontalCentered(true);
		titleShape.clearText();
		titleShape.setTopInset(15.0);
		
		XSLFTextRun titleRun = titleShape.addNewTextParagraph().addNewTextRun();
		titleRun.setFontFamily("VERDANA");
		titleRun.setBold(fontBoldFlag);
		titleRun.setFontSize(fontSize);
		titleRun.setText(title);
	}
	
	/**
	 * if addText is called with a String
	 * @param text text to be added
	 * @param fontSize font size of the text
	 * @param fontColor color of the text
	 * @param anchor position of the text box
	 */
	public void addText(String text, double fontSize, Color fontColor, Rectangle anchor){
		List<String> tmpList = new ArrayList<String>();
		tmpList.add(text);
		addText(tmpList, fontSize, fontColor, anchor);
	}
	
	/**
	 * if addText is called with a Set
	 * @param text text to be added
	 * @param fontSize font size of the text
	 * @param fontColor color of the text
	 * @param anchor position of the text box
	 */
	public void addText(Set<String> text, double fontSize, Color fontColor, Rectangle anchor){
		addText(new ArrayList<String>(text), fontSize, fontColor, anchor);
	}
	
	/**
	 * adds formatted text to a slide and resets the option flags
	 * @param text text to be added
	 * @param fontSize font size of the text
	 * @param fontColor color of the text
	 * @param anchor position of the text box
	 */
	public void addText(List<String> text, double fontSize, Color fontColor, Rectangle anchor){
		XSLFTextShape textShape = slide.createTextBox();
		textShape.setAnchor(anchor);
		textShape.setHorizontalCentered(horizontalCenteredFlag);
		textShape.clearText();
		if(footerFlag) textShape.setTopInset(8.0);
		else textShape.setTopInset(10.0);
		if(verticalCenteredFlag) textShape.setVerticalAlignment(VerticalAlignment.MIDDLE);
		
		XSLFTextParagraph textParagraph;
		XSLFTextRun textRun;
		
		for (String bullet : text) {
			
			textParagraph = textShape.addNewTextParagraph();
			textRun = textParagraph.addNewTextRun();
			
			if(!footerFlag){
				textParagraph.setLeftMargin(10.0);
				textParagraph.setRightMargin(10.0);
			}
			
			if(!smallSpaceFlag) textParagraph.setLineSpacing(130.0);
			
			if(bulletsFlag){
				textParagraph.setBullet(true);
				textParagraph.setIndent(-30.0);
				textParagraph.setLeftMargin(40.0);
			}
			if(rightAlignFlag) textParagraph.setTextAlign(TextAlign.RIGHT);
			
			textRun.setFontFamily("VERDANA");
			textRun.setFontSize(fontSize);
			textRun.setItalic(italicFlag);
			if(fontColor != null) textRun.setFontColor(fontColor);
			
			if(bullet.startsWith("http")){
				String[] splitLinks = bullet.split(" ");
				
				if(splitLinks.length == 2){
					textRun.setText(splitLinks[0]);
					textRun.createHyperlink().setAddress(splitLinks[0]);
					
					XSLFTextRun textRun1 = textParagraph.addNewTextRun();
					textRun1.setFontFamily("VERDANA");
					textRun1.setFontSize(fontSize);
					textRun1.setItalic(italicFlag);
					if(fontColor != null) textRun1.setFontColor(fontColor);
					textRun1.setText(" (");
					
					XSLFTextRun textRun2 = textParagraph.addNewTextRun();
					textRun2.setFontFamily("VERDANA");
					textRun2.setFontSize(fontSize);
					textRun2.setItalic(italicFlag);
					if(fontColor != null) textRun2.setFontColor(fontColor);
					textRun2.setText(splitLinks[1]);
					textRun2.createHyperlink().setAddress(splitLinks[1]);

					XSLFTextRun textRun3 = textParagraph.addNewTextRun();
					textRun3.setFontFamily("VERDANA");
					textRun3.setFontSize(fontSize);
					textRun3.setItalic(italicFlag);
					if(fontColor != null) textRun3.setFontColor(fontColor);
					textRun3.setText(")");
				}else{
					textRun.setText(bullet);
					textRun.createHyperlink().setAddress(bullet);
				}
			}else{
				textRun.setText(bullet);
			}
		}
		resetSettings();
	}
	
	/**
	 * scales an image down to the right size and places it CENTERED on the slide
	 * @param image image to be added
	 * @param pres presentation the image shall be added to
	 * @param maxWidth maximum width the image will have (can be less, if the height is at its maximum)
	 * @param maxHeight maximum height the image will have (can be less, if the width is at its maximum)
	 * @param xMoved image will be moved horizontally
	 * @param yMoved image will be moved vertically
	 */
	public void addImage(byte[] image, PowerPoint pres, int maxWidth, int maxHeight, int xMoved, int yMoved){
		if(image != null){
			XSLFPictureData pictureData = pres.addPicture(image, PictureType.PNG);
			XSLFPictureShape pictureShape = slide.createPicture(pictureData);
			
			double width = pictureData.getImageDimension().getWidth();
			double height = pictureData.getImageDimension().getHeight();
			
			if(width > maxWidth){
				height -= (width - maxWidth) * (height / width);
				width = maxWidth;
			}
			
			if(height > maxHeight){
				width -= (height - maxHeight) * (width / height);
				height = maxHeight;
			}
			
			double xPos = (SLIDEWIDTH - width) / 2 + xMoved;
			double yPos = (SLIDEHEIGHT - height) / 2 + yMoved;
			
			pictureShape.setAnchor(new Rectangle((int) xPos, (int) yPos, (int) width, (int) height));
		}else{
			horizontalCenteredFlag = true;
			verticalCenteredFlag = true;
			addText("No image found.", 14, Color.GRAY, new Rectangle(360-maxWidth/2+xMoved, 270-maxHeight/2+yMoved, maxWidth, maxHeight));
		}
	}
	
	/**
	 * adds two blue lines to a slide
	 */
	public void addLines(){
		XSLFAutoShape lineTop = slide.createAutoShape();
		lineTop.setAnchor(new Rectangle(35, 35, 650, 5));
		lineTop.setFillColor(Color.BLUE);
		
		XSLFAutoShape lineBottom = slide.createAutoShape();
		lineBottom.setAnchor(new Rectangle(35, 487, 650, 5));
		lineBottom.setFillColor(Color.BLUE);
	}
	
	/**
	 * adds a header to a slide
	 * @param pres presentation, so the title of the presentation can be added on the right
	 * @param slideTitle title of the slide will be added on the left
	 */
	public void addHeader(PowerPoint pres, String slideTitle){
		smallSpaceFlag = true;
		addText(slideTitle, 14.0, Color.GRAY, new Rectangle(18, 2, 330, 40));
		
		smallSpaceFlag = true;
		rightAlignFlag = true;
		addText(pres.getTitle().replace("_", " "), 14.0, Color.GRAY, new Rectangle(350, 2, 348, 40));
	}
	
	/**
	 * adds a footer to a slide
	 * @param pres presentation to get the amount of slides already added, so the page numbers can be added
	 */
	public void addFooter(PowerPoint pres){
		footerFlag = true;
		smallSpaceFlag = true;
		addText("Leipzig University | AutoSlides", 11.0, Color.GRAY, new Rectangle(30, 500, 200, 30));
		footerFlag = true;
		smallSpaceFlag = true;
		horizontalCenteredFlag = true;
		addText(df.format(date), 11.0, Color.GRAY, new Rectangle(295, 500, 130, 30));
		footerFlag = true;
		smallSpaceFlag = true;
		addText("Page " + pres.getCount(), 11.0, Color.GRAY, new Rectangle(630, 500, 60, 30));
	}
	
	/**
	 * if addNote is called with a single String
	 * @param text text to be added to the notes
	 * @param pres current presentation
	 */
	public void addNote(String text, PowerPoint pres){
		Set<String> tmpSet = new HashSet<String>();
		tmpSet.add(text);
		addNote(tmpSet, pres);
	}
	
	/**
	 * adds the sources of a slide to the notes of a slide
	 * @param text set of source links to be added to the slide notes
	 * @param pres current presentation to create the slide note sheet
	 */
	public void addNote(Set<String> text, PowerPoint pres){
		XSLFNotes notes = pres.getNotesSlide(slide);
		notes.clear();
		
		text.remove("");
		if(text.isEmpty()) return;
		
		XSLFTextBox textShape = notes.createTextBox();
		textShape.setAnchor(new Rectangle(35, 35, 470, 650));
		textShape.clearText();

		XSLFTextParagraph textParagraph;
		XSLFTextRun textRun;
		
		textParagraph = textShape.addNewTextParagraph();
		textRun = textParagraph.addNewTextRun();
		textRun.setFontFamily("VERDANA");
		textRun.setFontSize(12.0);
		textRun.setText("Sources used for this Slide:\n");
		
		for (String bullet : text) {
			textParagraph = textShape.addNewTextParagraph();
			textParagraph.setBullet(true);
			textParagraph.setIndent(-10.0);
			textParagraph.setLeftMargin(15.0);
			
			textRun = textParagraph.addNewTextRun();
			textRun.setFontFamily("VERDANA");
			textRun.setFontSize(12.0);
			
			if(bullet.startsWith("http")){
				String[] splitLinks = bullet.split(" ");
				
				if(splitLinks.length == 2){
					textRun.setText(splitLinks[0]);
					textRun.createHyperlink().setAddress(splitLinks[0]);
					
					XSLFTextRun textRun1 = textParagraph.addNewTextRun();
					textRun1.setFontFamily("VERDANA");
					textRun1.setFontSize(12.0);
					textRun1.setText(" (");
					
					XSLFTextRun textRun2 = textParagraph.addNewTextRun();
					textRun2.setFontFamily("VERDANA");
					textRun2.setFontSize(12.0);
					textRun2.setText(splitLinks[1]);
					textRun2.createHyperlink().setAddress(splitLinks[1]);

					XSLFTextRun textRun3 = textParagraph.addNewTextRun();
					textRun3.setFontFamily("VERDANA");
					textRun3.setFontSize(12.0);
					textRun3.setText(")");
				}else{
					textRun.setText(bullet);
					textRun.createHyperlink().setAddress(bullet);
				}
			}else{
				textRun.setText(bullet);
			}
		}
	}
	
	/**
	 * calculates the right fontSize for a given text length with two reference lengths and sizes,
	 * which are the max and min font sizes at the same time
	 * more precise with smaller textboxes
	 * @param thisTextLength length of the text to scale
	 * @param textLength1 first reference value for a text length
	 * @param fontSize1 first reference value for a font size, connected to fontSize1
	 * @param textLength2 second reference value for a text length
	 * @param fontSize2 second reference value for a font size, connected to fontSize2
	 * @return double fontSize
	 */
	protected double calculateFontSize(int thisTextLength, int textLength1, double fontSize1, int textLength2, double fontSize2){
		
		double slope = (fontSize2 - fontSize1) / ((double)textLength2 - (double)textLength1);
		
		double fontSize = fontSize1 + (slope * ((double)thisTextLength - (double)textLength1));
		
		if(fontSize < fontSize2) fontSize = fontSize2;
		if(fontSize > fontSize1) fontSize = fontSize1;
		
		return fontSize;
	}
	
	/**
	 * calculates the right font size for a given text length and the size of the text box the text has to fit in
	 * more pecise with bigger textboxes
	 * @param textLength length of the text to scale
	 * @param width width of the text box
	 * @param height height of the text box
	 * @param min minimum font size
	 * @param max maximum font size
	 * @return double fontSize
	 */
	protected double calcAreaFontSize(int textLength, int width, int height, double min, double max){
		
		double A = (double)width * (double)height - ((double)width * 13.6 + (double)height * 14.6);		//minus the insets
		
		double Ax = A / (double)textLength;				//thats the space every letter should fill
		
		double slope = (28.0 - 16.0) / (600.0 - 180.0);		//(big font size - small font size) / (space of big font - space of small font)
		
		double fontSize = 12 + (slope * (Ax - 162.0));
		
		if(fontSize < min) fontSize = min;
		if(fontSize > max) fontSize = max;
		
		return fontSize;
	}
	
	/**
	 * reset the booleans for addText
	 */
	private void resetSettings(){
		footerFlag 				= false;
		italicFlag 				= false;
		smallSpaceFlag 			= false;
		horizontalCenteredFlag 	= false;
		verticalCenteredFlag 	= false;
		rightAlignFlag 			= false;
		bulletsFlag 			= false;
	}
}
