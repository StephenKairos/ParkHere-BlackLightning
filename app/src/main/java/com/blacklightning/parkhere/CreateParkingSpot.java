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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_parking_spot);


        etStAddress = (EditText) findViewById(R.id.StreetAddress);
        etCity = (EditText) findViewById(R.id.City);
        etState = (EditText) findViewById(R.id.State);
        etZipCode = (EditText) findViewById(R.id.ZipCode);
        etRate = (EditText) findViewById(R.id.HourlyRate);
        //etTimeStart = (EditText) findViewById(R.id.TimeStart);
        //etTimeEnd = (EditText)findViewById(R.id.TimeEnd);
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
        createProfile();

    }

    public void createProfile() {
        //Reconfirm User
        Log.d("Logged in User is", currentUser.getUid());
        //Registration successful, let's add the user's info into the database

        fBase.child("parkingspot").child(currentUser.getUid()).child("StAddress").setValue(etStAddress.getText().toString());
        fBase.child("parkingspot").child(currentUser.getUid()).child("City").setValue(etCity.getText().toString());
        fBase.child("parkingspot").child(currentUser.getUid()).child("State").setValue(etState.getText().toString());
        fBase.child("parkingspot").child(currentUser.getUid()).child("ZipCode").setValue(etZipCode.getText().toString());
        fBase.child("parkingspot").child(currentUser.getUid()).child("Hourly Rate").setValue(etRate.getText().toString());
        // fBase.child("parkingspot").child(currentUser.getUid()).child("Time Start").setValue(etTimeStart.getText().toString());
        // fBase.child("parkingspot").child(currentUser.getUid()).child("Time End").setValue(etTimeEnd.getText().toString());

    }

    private boolean checkField(){
        boolean clear = true;
        String stAddress = etStAddress.getText().toString().trim();
        String City = etCity.getText().toString().trim();
        String State = etState.getText().toString().trim();
        String ZipCode = etZipCode.getText().toString().trim();
        String rate = etRate.getText().toString().trim();

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
        if(TextUtils.isEmpty(rate)){
            clear = false;
            Toast.makeText(CreateParkingSpot.this, "Missing Hourly Rate",Toast.LENGTH_LONG ).show();
            return clear;
        }
        return clear;
    }
}
