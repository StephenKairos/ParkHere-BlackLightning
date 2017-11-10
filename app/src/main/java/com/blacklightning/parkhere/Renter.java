package com.blacklightning.parkhere;



public class Renter {

    private String tripID;
    private String recipient;
    private String listingID;


    public Renter() {
    }

    public Renter(String tripID, String recipient, String listingID){
        this.tripID = tripID;
        this.recipient = recipient;
        this.listingID = listingID;

    }

    public String getTripID() {
        return tripID;
    }

    public String getRecipientID() {
        return recipient;
    }


    public String getListingID() {
        return listingID;
    }

    public void setTripID(String tripID) {
        this.tripID = tripID;
    }

    public void setRecipientID(String recipient) {
        this.recipient = recipient;
    }

    public void setListingID(String listingID) {
        this.listingID = listingID;
    }

}