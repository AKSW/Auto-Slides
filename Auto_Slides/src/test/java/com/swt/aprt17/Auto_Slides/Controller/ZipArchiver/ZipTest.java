package com.swt.aprt17.Auto_Slides.Controller.ZipArchiver;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * Unit test for simple App.
 */
public class ZipTest 
    extends TestCase
{
    /**
     * Create the test case
     *
     * @param testName name of the test case
     */
    public ZipTest( String testName )
    {
        super( testName );
    }

    /**
     * @return the suite of tests being tested
     */
    public static Test suite()
    {
        return new TestSuite( ZipTest.class );
    }

    /**
     * TEST 1
     * test for zip-archive being existent
     */
    public void testZip(){
    	byte[] testarray;
		try{
			Path path = Paths.get("src/testpptx.pptx");
			testarray = Files.readAllBytes(path);
		}
		catch(IOException e){
			throw new RuntimeException(e);
		}
		
		Zip z_test = new Zip();
		z_test.addData("testitestTest", testarray);
		
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyyMMdd.HHmmss");
		String check_name = "pres_" +dtf.format(LocalDateTime.now()) + ".zip";
		
		z_test.zipCreate();
		
		Path path = Paths.get(check_name);
		
		File check_test = new File(check_name);
		if (check_test.exists()){
			assertTrue(true);
		}
		
		try {
			Files.delete(path);
		} catch (IOException e) {
			e.printStackTrace();
		}
    }
    
    /**
     * TEST 2
     * tests for get-method getting correct information
     */
    public void testGet(){
    	byte[] testarray;
		try{
			Path path = Paths.get("src/testpptx.pptx");
			testarray = Files.readAllBytes(path);
		}
		catch(IOException e){
			throw new RuntimeException(e);
		}
		
		Zip z_test = new Zip();
		z_test.addData("testitestTest", testarray);
		
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyyMMdd.HHmmss");
		String check_name = "pres_" +dtf.format(LocalDateTime.now()) + ".zip";
		
		z_test.zipCreate();
		
		if (z_test.getZName().equals(check_name)){
			assertTrue(true);
		}
    }
}
