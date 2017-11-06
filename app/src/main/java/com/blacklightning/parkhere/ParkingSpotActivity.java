package com.blacklightning.parkhere;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
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

public class ParkingSpotActivity extends AppCompatActivity implements View.OnClickListener{
    Button bBackToList;
    TextView pStAddress;
    TextView pCity;
    TextView pState;
    TextView pZipCode;
    TextView pRate;
    TextView pTimeStart;
    TextView pTimeEnd;
    TextView pDateStart;
    TextView pDateEnd;

    private FirebaseAuth firebaseAuth;
    private FirebaseUser currentUser;
    private DatabaseReference mDB;


    public String stAddress, city, state, zipCode, rate, timeStart, timeEnd, dateStart, dateEnd;

    private String pSpotID;

    public String queryResult;

     protected void onCreate(Bundle savedInstanceState){
         super.onCreate(savedInstanceState);

         Intent intent = getIntent();
         pSpotID = intent.getStringExtra("pSpotID");

         setContentView(R.layout.activity_parking_spot);

         firebaseAuth = FirebaseAuth.getInstance();
         currentUser = firebaseAuth.getCurrentUser();
         mDB = FirebaseDatabase.getInstance().getReference();

         pStAddress = (TextView) findViewById(R.id.pStAddress);
         pCity = (TextView) findViewById(R.id.pCity);
         pState = (TextView) findViewById(R.id.pState);
         pZipCode = (TextView) findViewById(R.id.pZipcode);
         pRate = (TextView) findViewById(R.id.pRate);
         pTimeStart = (TextView) findViewById(R.id.pTimeStart);
         pTimeEnd = (TextView) findViewById(R.id.pTimeEnd);
         pDateStart = (TextView) findViewById(R.id.pDateStart);
         pDateEnd = (TextView) findViewById(R.id.pDateEnd);

         if(currentUser != null) {

             mDB.child("parkingspot").child("7OiWhTgPrGe2Iv0zXCnNTjCHmBz2").child(pSpotID).child("stAddress").addListenerForSingleValueEvent(new ValueEventListener() {
                 @Override
                 public void onDataChange(DataSnapshot snapshot) {
                     stAddress = snapshot.getValue().toString();
                     pStAddress.setText("Address: " + stAddress);
                 }

                 @Override
                 public void onCancelled(DatabaseError databaseError) {

                 }
             });

             mDB.child("parkingspot").child("7OiWhTgPrGe2Iv0zXCnNTjCHmBz2").child(pSpotID).child("city").addListenerForSingleValueEvent(new ValueEventListener() {
                 @Override
                 public void onDataChange(DataSnapshot snapshot) {
                     city = snapshot.getValue().toString();
                     pCity.setText("City: " + city);
                 }

                 @Override
                 public void onCancelled(DatabaseError databaseError) {

                 }
             });

             mDB.child("parkingspot").child("7OiWhTgPrGe2Iv0zXCnNTjCHmBz2").child(pSpotID).child("zip").addListenerForSingleValueEvent(new ValueEventListener() {
                 @Override
                 public void onDataChange(DataSnapshot snapshot) {
                     zipCode = snapshot.getValue().toString();
                     pZipCode.setText("Zip Code: " + zipCode);
                 }

                 @Override
                 public void onCancelled(DatabaseError databaseError) {

                 }
             });

             mDB.child("parkingspot").child("7OiWhTgPrGe2Iv0zXCnNTjCHmBz2").child(pSpotID).child("state").addListenerForSingleValueEvent(new ValueEventListener() {
                 @Override
                 public void onDataChange(DataSnapshot snapshot) {
                     state = snapshot.getValue().toString();
                     pState.setText("State: " + state);
                 }

                 @Override
                 public void onCancelled(DatabaseError databaseError) {

                 }
             });

             mDB.child("parkingspot").child("7OiWhTgPrGe2Iv0zXCnNTjCHmBz2").child(pSpotID).child("rate").addListenerForSingleValueEvent(new ValueEventListener() {
                 @Override
                 public void onDataChange(DataSnapshot snapshot) {
                     rate = snapshot.getValue().toString();
                     pRate.setText("Rate: $" + rate + "per hour");
                 }

                 @Override
                 public void onCancelled(DatabaseError databaseError) {

                 }
             });

             mDB.child("parkingspot").child("7OiWhTgPrGe2Iv0zXCnNTjCHmBz2").child(pSpotID).child("startDate").addListenerForSingleValueEvent(new ValueEventListener() {
                 @Override
                 public void onDataChange(DataSnapshot snapshot) {
                     dateStart = snapshot.getValue().toString();
                     pDateStart.setText("Start Date: " + dateStart);
                 }

                 @Override
                 public void onCancelled(DatabaseError databaseError) {

                 }
             });

             mDB.child("parkingspot").child("7OiWhTgPrGe2Iv0zXCnNTjCHmBz2").child(pSpotID).child("endDate").addListenerForSingleValueEvent(new ValueEventListener() {
                 @Override
                 public void onDataChange(DataSnapshot snapshot) {
                     dateEnd = snapshot.getValue().toString();
                     pDateEnd.setText("End Date: " + dateEnd);
                 }

                 @Override
                 public void onCancelled(DatabaseError databaseError) {

                 }
             });

             mDB.child("parkingspot").child("7OiWhTgPrGe2Iv0zXCnNTjCHmBz2").child(pSpotID).child("startTime").addListenerForSingleValueEvent(new ValueEventListener() {
                 @Override
                 public void onDataChange(DataSnapshot snapshot) {
                     timeStart = snapshot.getValue().toString();
                     pTimeStart.setText("Start Time: " + timeStart);
                 }

                 @Override
                 public void onCancelled(DatabaseError databaseError) {

                 }
             });

             mDB.child("parkingspot").child("7OiWhTgPrGe2Iv0zXCnNTjCHmBz2").child(pSpotID).child("endTime").addListenerForSingleValueEvent(new ValueEventListener() {
                 @Override
                 public void onDataChange(DataSnapshot snapshot) {
                     timeEnd = snapshot.getValue().toString();
                     pTimeEnd.setText("End Time: " + timeEnd);
                 }

                 @Override
                 public void onCancelled(DatabaseError databaseError) {

                 }
             });
         }

         bBackToList = (Button) findViewById(R.id.bParkingSpotReturn);
         bBackToList.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
         Log.d("TEST","Something Clicked");

        if(v == bBackToList){
            finish();
        }
     }
}











