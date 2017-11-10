package com.blacklightning.parkhere;

import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;
import org.junit.Before;
import org.junit.Test;



public class getUser {

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
        String email = testUser.getEmail();
        assertThat(email, is("admin@sjsu.edu"));
    }

    @Test
    public void firstName() throws Exception {
        String firstName = testUser.getFirstName();
        assertThat(firstName, is("Phuong"));
    }

    @Test
    public void lastName() throws Exception {
        String returnedLastName = testUser.getLastName();
        assertThat(returnedLastName, is("Tran"));
    }

    @Test
    public void phone() throws Exception {
        String returnedPhoneNumber = testUser.getPhoneNumber();
        assertThat(returnedPhoneNumber, is("408-408-4008"));
    }


}