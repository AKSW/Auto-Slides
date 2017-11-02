/**
 * Main
 * Date: 17.03.2017
 * run the Controller 
 * 
 * @author  Francesco Mandry
 */
package com.swt.aprt17.Auto_Slides;

import java.io.IOException;

import com.swt.aprt17.Auto_Slides.Controller.Controller;

public class Main {
	
	/**
	 * main method for running the controller
	 * @param args
	 */
	public static void main(String[] args) throws IOException {
		
		Controller controller = new Controller();
		
		if(args.length > 0) { // path to file is given
			controller.runFileInput(args[0]);
		}
		else { // standard user interface
			controller.runUserInput();
		}
	}
}
