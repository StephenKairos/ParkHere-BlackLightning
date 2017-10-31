package com.blacklightning.parkhere;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

public class CreateParkingSpot extends AppCompatActivity {

    Button bCreate;
    EditText StAddressText;
    EditText CityText;
    EditText StateText;
    EditText ZipCodeText;
    

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_parking_spot);
    }
}
