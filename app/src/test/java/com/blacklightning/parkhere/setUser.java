package com.blacklightning.parkhere;

import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;
import org.junit.Before;
import org.junit.Test;



public class setUser {

    User testUser;

    @Before
    public void setUp() throws Exception {
        testUser = new User("123456", "admin@sjsu.edu", "Phuong", "Tran",  "408-408-4008");
    }


    @Test
    public void user() throws Exception {
        String user = testUser.getUserId();
        assertThat(user, is("123456"));
    }

    @Test
    public void email() throws Exception {
        String email = "fboi@sjsu.com";
        testUser.setEmail(email);
        assertThat(testUser.getEmail(), is(email));
    }

    @Test
    public void firstName() throws Exception {
        String firstName = "LOL";
        testUser.setEmail(firstName);
        assertThat(testUser.getEmail(), is(firstName));
    }

    @Test
    public void lastName() throws Exception {
        String lastName = "LMAO";
        testUser.setEmail(lastName);
        assertThat(testUser.getEmail(), is(lastName));
    }

    @Test
    public void phone() throws Exception {
        String phone = "22222222";
        testUser.setPhoneNumber(phone);
        assertThat(testUser.getPhoneNumber(), is(phone));
    }


}