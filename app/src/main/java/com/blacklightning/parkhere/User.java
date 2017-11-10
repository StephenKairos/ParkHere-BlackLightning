package com.blacklightning.parkhere;

public class User {

    String userId;
    String email;
    String firstName;
    String lastName;
    String location;
    String phoneNumber;


    public User(String userId, String email, String firstName, String lastName, String phoneNumber) {
        this.userId = userId;
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
        this.phoneNumber = phoneNumber;
    }
    public String getUserId(){
        return userId;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }



    public String getPhoneNumber() {
        return phoneNumber;
    }



}