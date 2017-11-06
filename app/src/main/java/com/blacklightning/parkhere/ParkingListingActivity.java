package com.blacklightning.parkhere;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListAdapter;
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
    ListAdapter listAdapter;
    ParkingSpace[] listings;
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
        listings = getSpaces();
        listViewer = (ListView) findViewById(R.id.parkingListings);
        listViewer.setAdapter(listAdapter );
        if(currentUser != null) {

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
    public ParkingSpace[] getSpaces(){

    }
    @Override
    public void onClick(View v) {
        Log.d("TEST","Something Clicked");

        if(v == bSearch){


        }
    }
}