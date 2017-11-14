package com.blacklightning.parkhere;

import android.content.Context;
import android.location.Address;
import android.location.Geocoder;

import com.google.android.gms.maps.model.LatLng;

import java.util.List;

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

    public LatLng getLocationFromAddress(Context context){
        String strAddress = StAddress + ", " + City + ", " + State;
        Geocoder coder = new Geocoder(context);
        List<Address> address;
        LatLng p1 = null;

        try {
            address = coder.getFromLocationName(strAddress,5);
            if (address==null) {
                return null;
            }
            Address location=address.get(0);

            p1 = new LatLng(location.getLatitude(), location.getLongitude());

            return p1;
        }
        catch(Exception e){

        }
        return p1;
    }

}
