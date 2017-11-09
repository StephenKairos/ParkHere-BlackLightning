package com.blacklightning.parkhere;

import org.junit.BeforeClass;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by flipsahoy on 11/8/2017.
 */
public class ParkingSpaceTest {
    ParkingSpace forGets = new ParkingSpace("1 Washington St.", "San Jose", "CA", 94133, 10,
            "11/8/17", "11/10/17", "10:00", "23:00");


    @Test
    public void testGetID() throws Exception{
        assertEquals("ID should be 1WashingtonSt94133","1WashingtonSt94133", forGets.getId());
    }

    @Test
    public void testGetStAddress() throws Exception{
        assertEquals("St Address should be 1 Washington St.","1 Washington St.",forGets.getStAddress());
    }

    @Test
    public void testGetCity() throws Exception{
        assertEquals("City should be San Jose","San Jose", forGets.getCity());
    }

    @Test
    public void testGetState() throws Exception{
        assertEquals("State should be CA","CA", forGets.getState());
    }

    @Test
    public void testGetZip() throws Exception{
        assertEquals("Zip code should be 94133",94133, forGets.getZip());
    }

    @Test
    public void testGetRate() throws Exception{
        assertEquals("Rate should be 10",10, forGets.getRate(), 0);
    }
    @Test
    public void testGetStartDate() throws Exception{
        assertEquals("Start date should be 11/8/17","11/8/17", forGets.getStartDate());
    }
    @Test
    public void testGetEndDate() throws Exception{
        assertEquals("End date should be 11/10/17","11/10/17", forGets.getEndDate());
    }
    @Test
    public void testGetStartTime() throws Exception{
        assertEquals("Start time should be 10:00","10:00" ,forGets.getStartTime());
    }

    @Test
    public void testGetEndTime() throws Exception{
        assertEquals("End time should be 23:00","23:00", forGets.getEndTime());
    }
}