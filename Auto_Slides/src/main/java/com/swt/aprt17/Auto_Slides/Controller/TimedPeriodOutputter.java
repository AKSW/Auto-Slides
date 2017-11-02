/**
 * TimedPeriodOutputter
 * Date: 20.04.2017
 * prints '.' every 3 seconds
 * 
 * @author  Lucas Lange
 */
package com.swt.aprt17.Auto_Slides.Controller;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class TimedPeriodOutputter {
	/*
	 * Runnable object
	 */
	private Runnable stillRunningRunnable;
	/*
	 * ScheduledExecutorService object
	 */
	private ScheduledExecutorService executor;
	
	/**
	 * constructor method
	 */
	public TimedPeriodOutputter() {
		/**
		 * define output
		 */
		stillRunningRunnable = new Runnable() {
		    public void run() {
		        System.out.print(".");
		    }
		};
		/**
		 * executor with one thread in pool
		 */
		executor = Executors.newScheduledThreadPool(1);
	}
	
	//=====================================================================================================
	
	/**
	 * start the output
	 */
	public void start() {
		/* print every 3 seconds starting 'now' */
		executor.scheduleAtFixedRate(stillRunningRunnable, 0, 3, TimeUnit.SECONDS);
	}
	
	//=====================================================================================================
	
	/**
	 * stop the output
	 */
	public void stop() {
		executor.shutdown();
	}
}
