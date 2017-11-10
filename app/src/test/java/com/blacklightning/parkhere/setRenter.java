package com.blacklightning.parkhere;


import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;
import org.junit.Before;
import org.junit.Test;



public class setRenter {

    Renter renter;

    @Before
    public void setUp() throws Exception {
        renter = new Renter("123456", "Phuong", "82828288282");
    }


    @Test
    public void id() throws Exception {
        String id = "new ID";
        renter.setTripID(id);
        assertThat(renter.getTripID(), is(id));
    }

    @Test
    public void receipt() throws Exception {
        String email = "new re";
        renter.setRecipientID(email);
        assertThat(renter.getRecipientID(), is(email));
    }

    @Test
    public void list() throws Exception {
        String email = "new List";
        renter.setListingID(email);
        assertThat(renter.getListingID(), is(email));
    }
}