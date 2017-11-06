package com.blacklightning.parkhere;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;



import com.google.firebase.auth.FirebaseAuth;

public class CreateParkingSpot extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;

    Button bCreate;
    EditText etStAddress;
    EditText etCity;
    EditText etState;
    EditText etZipCode;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_parking_spot);


        etStAddress = (EditText) findViewById(R.id.StreetAddress);
        etCity = (EditText) findViewById(R.id.City);
        etState = (EditText) findViewById(R.id.State);
        etZipCode = (EditText) findViewById(R.id.ZipCode);
        bCreate = (Button) findViewById(R.id.buttonRegister);


        

    }
}
