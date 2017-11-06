package com.blacklightning.parkhere;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class CreateParkingSpot extends AppCompatActivity {

    private FirebaseAuth firebaseAuth;
    private FirebaseAuth.AuthStateListener authListener;
    private DatabaseReference fBase;
    private FirebaseUser currentUser;

    Button bCreate;
    EditText etStAddress;
    EditText etCity;
    EditText etState;
    EditText etZipCode;
    EditText etRate;
    EditText etTimeStart;
    EditText etTimeEnd;
    EditText etDateStart;
    EditText etDateEnd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_parking_spot);


        etStAddress = (EditText) findViewById(R.id.StreetAddress);
        etCity = (EditText) findViewById(R.id.City);
        etState = (EditText) findViewById(R.id.State);
        etZipCode = (EditText) findViewById(R.id.ZipCode);
        etRate = (EditText) findViewById(R.id.HourlyRate);
        etTimeStart = (EditText) findViewById(R.id.startTime);
        etTimeEnd = (EditText)findViewById(R.id.endTime);
        etDateStart = (EditText) findViewById(R.id.startDate);
        etDateEnd = (EditText) findViewById(R.id.endDate);
        bCreate = (Button) findViewById(R.id.CreateParkingButton);


        fBase = FirebaseDatabase.getInstance().getReference();
        firebaseAuth = FirebaseAuth.getInstance();
        currentUser = firebaseAuth.getCurrentUser();

        bCreate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createParkingSpot();
                Intent CreatePSIntent = new Intent(CreateParkingSpot.this, ProfileActivity.class);
                startActivity(CreatePSIntent);
                finish();
            }
        });

    }
    private void createParkingSpot(){
        if(!checkField()){
            return;
        }
        currentUser = firebaseAuth.getCurrentUser();
        createSpot();

    }

    public void createSpot() {
        String stAddress = etStAddress.getText().toString().trim();
        String City = etCity.getText().toString().trim();
        String State = etState.getText().toString().trim();
        int ZipCode = Integer.parseInt(etZipCode.getText().toString().trim());
        double rate = Double.parseDouble(etRate.getText().toString().trim());
        String startDate = etDateStart.getText().toString().trim();
        String endDate = etDateEnd.getText().toString().trim();
        String startTime = etTimeStart.getText().toString().trim();
        String endTime = etTimeEnd.getText().toString().trim();


        ParkingSpace parkingSpace = new ParkingSpace(stAddress, City, State, ZipCode, rate, startDate,
                endDate,startTime,endTime);
        String parkingID = parkingSpace.getId();
        fBase.child("parkingspot").child(currentUser.getUid()).child(parkingID).setValue(parkingSpace);


    }

    private boolean checkField(){
        boolean clear = true;
        String stAddress = etStAddress.getText().toString().trim();
        String City = etCity.getText().toString().trim();
        String State = etState.getText().toString().trim();
        String ZipCode =etZipCode.getText().toString().trim();
        String Rate = etRate.getText().toString().trim();
        String StartDate = etDateStart.getText().toString().trim();
        String EndDate = etDateEnd.getText().toString().trim();
        String StartTime = etTimeStart.getText().toString().trim();
        String EndTime = etTimeEnd.getText().toString().trim();

        if(TextUtils.isEmpty(stAddress)){
            clear = false;
            Toast.makeText(CreateParkingSpot.this, "Missing Street Address",Toast.LENGTH_LONG ).show();
            return clear;
        }
        if(TextUtils.isEmpty(City)){
            clear = false;
            Toast.makeText(CreateParkingSpot.this, "Missing City",Toast.LENGTH_LONG ).show();
            return clear;
        }
        if(TextUtils.isEmpty(State)){
            clear = false;
            Toast.makeText(CreateParkingSpot.this, "Missing State",Toast.LENGTH_LONG ).show();
            return clear;
        }
        if(TextUtils.isEmpty(ZipCode)){
            clear = false;
            Toast.makeText(CreateParkingSpot.this, "Missing Zip Code",Toast.LENGTH_LONG ).show();
            return clear;
        }
        if(TextUtils.isEmpty(Rate)){
            clear = false;
            Toast.makeText(CreateParkingSpot.this, "Missing Hourly Rate",Toast.LENGTH_LONG ).show();
            return clear;
        }
        if(TextUtils.isEmpty(StartDate)){
            clear = false;
            Toast.makeText(CreateParkingSpot.this, "Missing Start Date",Toast.LENGTH_LONG ).show();
            return clear;
        }
        if(TextUtils.isEmpty(EndDate)){
            clear = false;
            Toast.makeText(CreateParkingSpot.this, "Missing End Date",Toast.LENGTH_LONG ).show();
            return clear;
        }
        if(TextUtils.isEmpty(StartTime)){
            clear = false;
            Toast.makeText(CreateParkingSpot.this, "Missing Start Time",Toast.LENGTH_LONG ).show();
            return clear;
        }
        if(TextUtils.isEmpty(EndTime)){
            clear = false;
            Toast.makeText(CreateParkingSpot.this, "Missing End Time",Toast.LENGTH_LONG ).show();
            return clear;
        }
        return clear;
    }
}
