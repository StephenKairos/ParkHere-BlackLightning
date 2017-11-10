package com.blacklightning.parkhere;

/**
 * Created by Jason Liu on 11/5/2017.
 */

public class ParkingSpace {
    private String StAddress;
    private String City;
    private String State;
    private int Zip;
    private double Rate;
    private String StartDate;
    private String EndDate;
    private String StartTime;
    private String EndTime;
    private String ParkingSpaceID;
    public ParkingSpace(){

    }

    public ParkingSpace(String stAddress, String city, String state, int zip, double rate, String startDate,
    String endDate, String startTime, String endTime){
        this.StAddress = stAddress;
        this.City =city;
        this.State = state;
        this.Zip = zip;
        this.Rate = rate;
        this.StartDate=startDate;
        this.EndDate=endDate;
        this.StartTime=startTime;
        this.EndTime=endTime;
        this.ParkingSpaceID=StAddress.replaceAll("[\\s\\-+.^:,#$\\[\\]]","")+Zip;
    }

    public String getId(){
        return ParkingSpaceID;
    }
    public String getStAddress(){
        return StAddress;
    }
    public String getCity(){
        return City;
    }
    public String getState(){
        return State;
    }
    public int getZip(){
        return Zip;
    }
    public double getRate (){
        return Rate;
    }
    public String getStartDate(){
        return StartDate;
    }
    public String getEndDate(){return EndDate;}
    public String getEndTime(){
        return EndTime  ;
    }
    public String getStartTime(){
        return StartTime;
    }
}
