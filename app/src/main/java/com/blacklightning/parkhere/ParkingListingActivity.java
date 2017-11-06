package com.blacklightning.parkhere;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

/**
 * Created by Jason Liu on 11/1/2017.
 */

public class ParkingListingActivity extends AppCompatActivity implements View.OnClickListener{
    Button bSearch;
    ListView listViewer;

    private FirebaseAuth firebaseAuth;
    private FirebaseUser currentUser;
    private DatabaseReference mDB;


    public String queryResult;

    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_parking_spot);

        firebaseAuth = FirebaseAuth.getInstance();
        currentUser = FirebaseAuth.getInstance().getCurrentUser();
        mDB = FirebaseDatabase.getInstance().getReference();

        if(currentUser != null) {

            mDB.child("users").child(currentUser.getUid()).child("userName").addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot snapshot) {
                    userName = snapshot.getValue().toString();
                    userNameText.setText("User: " + userName);
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });

            mDB.child("users").child(currentUser.getUid()).child("firstNameText").addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot snapshot) {
                    firstName = snapshot.getValue().toString();
                    firstNameText.setText("First Name: " + firstName);
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });

            mDB.child("users").child(currentUser.getUid()).child("lastNameText").addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot snapshot) {
                    lastName = snapshot.getValue().toString();
                    lastNameText.setText("Last Name: " + lastName);
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });

            mDB.child("users").child(currentUser.getUid()).child("emailText").addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot snapshot) {
                    email = snapshot.getValue().toString();
                    emailText.setText("Email Address: " + email);
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });

            mDB.child("users").child(currentUser.getUid()).child("phoneNumber").addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot snapshot) {
                    phoneNumber = snapshot.getValue().toString();
                    phoneText.setText("Phone Number: " + phoneNumber);
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });

        }

        bSearch = (Button) findViewById(R.id.searchButton);
    }

    @Override
    public void onClick(View v) {
        Log.d("TEST","Something Clicked");

        if(v == bSearch){


        }
    }
}











