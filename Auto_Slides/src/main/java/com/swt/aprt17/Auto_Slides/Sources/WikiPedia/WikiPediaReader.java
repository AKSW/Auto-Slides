/**
 * WikiPediaReader
 * Date: 05.05.2017
 * Last Update: 21.05.2017
 * 
 * Saves all contents of a specific topic that can be received from WikiPedia.
 * 
 * @author  Christian Staudte
 */

package com.swt.aprt17.Auto_Slides.Sources.WikiPedia;

import java.util.ArrayList;

import com.swt.aprt17.Auto_Slides.Sources.Content;

import net.sf.classifier4J.summariser.SimpleSummariser;

public class WikiPediaReader {


    /**
     * actual topic searched for
     */
    private String topic;

    /**
     * source, in this case a wikipedia link
     */
    private String source;   

    /**
     * all texts (value) with title (key)
     */
    private ArrayList<Content> wikiPediaTexts;

    /**
     * constructor
     * @param topic
     * @param notWantedContent
     */
    public WikiPediaReader(String topic, ArrayList<String> notWantedContent){
        this.topic              = topic;
        this.wikiPediaTexts     = new ArrayList<Content>();
        init(notWantedContent);
    }

    /**
     * save normal texts and text with bullets in a Map/List
     * If you want to use all contents, just set noWantedContent null
     * max 8
     * @param notWantedContent
     */    
    private void init(ArrayList<String> notWantedContent){
        WikiPedia wp = new WikiPedia(topic);
        ArrayList<String[]> tableOfContent = wp.getTableOfContent();
        this.source = wp.getCompleteUrl();
        
        for(String[] section : tableOfContent){
            //i.e. Gallery for cities is not wanted
            if(notWantedContent != null && notWantedContent.contains(section[0])) continue;    
            //get text for actual key (content)
            String value = wp.getContent(section[1]);
            
            //get information by using nlp
            String cleanSentences = "";
            if(! value.equals("")) {	            
	            try{
	                cleanSentences = NLPExtractSentences(value);
	            }catch(Exception e){
	                continue;
	            }
            }
            
            //save cleaned information
            ArrayList<String> text = new ArrayList<String>();
            text.add(cleanSentences);
            wikiPediaTexts.add(new Content(section[0], text));
        }
    }
    
    /**
     * Uses natural language processing (nlp) to summarize a text in 3 sentences; 
     * uses API Classifier4J
     * @param text the text to summarize
     * @return summary of the text
     */
    private String NLPExtractSentences(String text){
        SimpleSummariser simsum = new SimpleSummariser();
        String summary = simsum.summarise(text, 3);         // summarize text in 3 sentences
        return summary;
    }

    @Override
    public String toString(){
        String s = "";
        s += "All WikiPedia Texts: \n\n";
        for(Content c : wikiPediaTexts){
            s += " > " + c.getTitle() + "\n";
            
            for(String text : c.getText()){
            	s += text + "\n";
            }
            s += "\n";
           
        }
        return s;
    }

    //==========================================================================
    //Get- and Set Methods 
    public String getSource(){
        return source;
    }
    public ArrayList<Content> getWikiPediaTexts(){
        return wikiPediaTexts;
    }
}