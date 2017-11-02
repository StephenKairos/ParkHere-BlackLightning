package com.blacklightning.parkhere;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

/**
 * Created by Jason Liu on 11/1/2017.
 */

public class ProfileActivity extends AppCompatActivity{
        Button bViewParkingSpots;
        Button bBookParkingSpots;
        Button bCreateParkingSpots;
        Button bEditProfile;
        EditText firstNameText;
        EditText lastNameText;
        EditText userName;
        EditText emailText;
        EditText password;
        EditText reEnterPassword;

        protected void onCreate(Bundle savedInstanceState){
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_profile);
            bViewParkingSpots = (Button) findViewById(R.id.viewYourParkingSpots);
            bBookParkingSpots = (Button) findViewById(R.id.bookParkingSpots);
            bCreateParkingSpots = (Button) findViewById(R.id.createParkingSpots);
            bEditProfile = (Button) findViewById(R.id.editProfile);

            bCreateParkingSpots.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent profileIntent = new Intent(ProfileActivity.this, CreateParkingSpot.class);
                    ProfileActivity.this.startActivity(profileIntent);
                }
            });
        }

    }











