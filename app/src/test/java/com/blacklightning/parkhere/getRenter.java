package com.blacklightning.parkhere;

import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;
import org.junit.Before;
import org.junit.Test;

public class getRenter {
    Renter renter;

    @Before
    public void setUp() throws Exception {
        renter = new Renter("123456", "Phuong", "82828288282");
    }


    @Test
    public void trip() throws Exception {
        String id = renter.getTripID();
        assertThat(id, is("123456"));
    }

    @Test
    public void receipt() throws Exception {
        String receipt = renter.getRecipientID();
        assertThat(receipt, is("Phuong"));
    }

    @Test
    public void list() throws Exception {
        String list = renter.getListingID();
        assertThat(list, is("82828288282"));
    }


}
