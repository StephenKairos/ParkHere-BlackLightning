package com.blacklightning.parkhere;

/**
 * Created by Jason Liu on 11/9/2017.
 */
import junit.framework.TestCase;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import static org.junit.Assert.*;

public class CreateParkingSpotTest extends TestCase {
    CreateParkingSpot cps;
    String stAddress = "1125 A St. Apt #4";
    String City = "San Jose";
    String State = "California";
    String ZipCode ="11423";
    String Rate = "10.25";
    String StartDate = "10/24/2017";
    String EndDate = "10/27/2017";
    String StartTime = "10:20";
    String EndTime = "10:20";

    public CreateParkingSpotTest(){

    }
    @BeforeClass
    protected void setUp(){
        cps = new CreateParkingSpot();
    }

    @Before
    public static void setupForEachTest(){

    }

    @Test
    public void testCheckField(){

    }

    @Test
    public void testNullField(){

    }

    @Test
    public void testValidDateInputs(){

    }

    @Test
    public void testInvalidDateInputs(){

    }

    @After
    public static void undoSetupForEachTest(){

    }

    @AfterClass
    public static void undoSetup(){

    }
}
