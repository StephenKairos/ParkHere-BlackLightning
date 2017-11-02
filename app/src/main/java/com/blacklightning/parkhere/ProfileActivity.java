package com.blacklightning.parkhere;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.auth.FirebaseAuth;

/**
 * Created by Jason Liu on 11/1/2017.
 */

public class ProfileActivity extends AppCompatActivity implements View.OnClickListener{
    Button bViewParkingSpots;
    Button bBookParkingSpots;
    Button bCreateParkingSpots;
    Button bEditProfile;
    Button bLogOut;
    EditText firstNameText;
    EditText lastNameText;
    EditText userName;
    EditText emailText;
    EditText password;
    EditText reEnterPassword;
    private FirebaseAuth firebaseAuth;

     protected void onCreate(Bundle savedInstanceState){
         super.onCreate(savedInstanceState);
         setContentView(R.layout.activity_profile);

         firebaseAuth = FirebaseAuth.getInstance();
         if(firebaseAuth.getCurrentUser()==null){
             finish();
             startActivity(new Intent(this, LoginActivity.class));
         }
         bViewParkingSpots = (Button) findViewById(R.id.viewYourParkingSpots);
         bBookParkingSpots = (Button) findViewById(R.id.bookParkingSpots);
         bCreateParkingSpots = (Button) findViewById(R.id.createParkingSpots);
         bEditProfile = (Button) findViewById(R.id.editProfile);
         bLogOut = (Button) findViewById(R.id.logOut);
         bCreateParkingSpots.setOnClickListener(this);
         bLogOut.setOnClickListener(this);
     }

    @Override
    public void onClick(View view) {
         if(view == bLogOut){
             firebaseAuth.signOut();
             finish();
             startActivity(new Intent(this, LoginActivity.class));
         }
         if(view == bViewParkingSpots){

        }
        if(view == bBookParkingSpots){

        }
        if(view == bCreateParkingSpots){
            startActivity(new Intent(this, CreateParkingSpot.class));
        }
        if(view == bEditProfile){

        }
    }
}











