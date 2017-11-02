/**
 * @author Francesco Mandry
 */

package com.swt.aprt17.Auto_Slides.Controller;
import java.util.ArrayList;
import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

public class ControllerTest extends TestCase {
	
	/**
	 * @param testName
	 */
	public ControllerTest(String testName) {
		super(testName);
		
	}

	 /**
     * @return the suite of tests being tested
     */
    public static Test suite(){
        return new TestSuite( ControllerTest.class );
    }
    
    /**
     * TEST 1
     * Test byte Array, if there will always be a city presentation which should be
     */
    public void testIfEveryCityPresentationIsCreatedNotDependingOnTopicName(){
    	
    	String[] p =  {"city", "Leipzig"};
    	
    	String[] p2 =  {"city", "City that doesn't exists"};
    	
    	testIfEveryPresentationIsCreatedNotDependingOnTopicName(p, p2);
    }
    
    /**
     * TEST 2
     * Test byte Array, if there will always be a battle presentation which should be
     */
    public void testIfEveryBattlePresentationIsCreatedNotDependingOnTopicName(){
    	
    	String[] p =  {"battle", "Attack on Sydney Harbour"};
    	
    	String[] p2 =  {"battle", "Battle that doesn't exists"};
    	
    	testIfEveryPresentationIsCreatedNotDependingOnTopicName(p, p2);
    }
    
    /**
     * Runs runCotrol Method with dynamic inputs
     * 
     */
    public void testIfEveryPresentationIsCreatedNotDependingOnTopicName(String[] p, String[] p2) {
    	
    	Controller controller = new Controller();
    	
    	ArrayList<String[]> pairList = new ArrayList<String[]>();
    	
    	pairList.add(p);
    	
    	controller.runControl(pairList, false);
    	
    	assertTrue(controller.getZip().getData().size() > 0);
    	
     	pairList.remove(p);
     	
    	pairList.add(p2);
    	
    	controller.runControl(pairList, false);

    	assertTrue(controller.getZip().getData().size() > 0);
    }
}