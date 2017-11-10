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

import java.util.Calendar;
import java.util.Date;

import static org.junit.Assert.*;

public class CreateParkingSpotTest extends TestCase {
    CreateParkingSpot cps = new CreateParkingSpot();
    String stAddress = "1125 A St. Apt #4";
    String City = "San Jose";
    String State = "California";
    Date startDate;
    Date endDate;        String ZipCode ="11423";
    String Rate = "10.25";
    String StartDate = "10/24/2017";
    String EndDate = "10/27/2017";
    String StartTime = "10:20";
    String EndTime = "10:20";
    int year= 2017;
    int month= 11;
    int startDay1=10;
    int startDay2=14;
    int endDay=12;
    Calendar mc = Calendar.getInstance();
    public CreateParkingSpotTest(){


    }
    @BeforeClass
    protected void setUp(){
        mc.set(year,month,startDay1,0,0);
        startDate=mc.getTime();
        mc.set(year,month,endDay,0,1);
        endDate=mc.getTime();


    }

    @Before
    public static void setupForEachTest(){

    }

    @Test
    public void testValidDateFields(){
        assertTrue(cps.checkCalendarDate(startDate,endDate));
    }

    @Test
    public void testInvalidDateInputs(){
        mc.set(year,month,startDay2,0,1);
        startDate=mc.getTime();
        assertFalse(cps.checkCalendarDate(startDate,endDate));
    }


    @After
    public static void undoSetupForEachTest(){

    }

    @AfterClass
    public static void undoSetup(){

    }
}
