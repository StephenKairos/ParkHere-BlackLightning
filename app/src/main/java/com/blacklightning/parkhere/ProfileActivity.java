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

public class ProfileActivity extends AppCompatActivity implements View.OnClickListener{
    Button bViewParkingSpots;
    Button bBookParkingSpots;
    Button bCreateParkingSpots;
    Button bEditProfile;
    Button bLogOut;
    TextView firstNameText;
    TextView lastNameText;
    TextView userNameText;
    TextView emailText;
    TextView phoneText;

    private FirebaseAuth firebaseAuth;
    private FirebaseUser currentUser;
    private DatabaseReference mDB;


    public String userName, firstName, lastName, email, phoneNumber;

    public String queryResult;

     protected void onCreate(Bundle savedInstanceState){
         super.onCreate(savedInstanceState);
         setContentView(R.layout.activity_profile);

         firebaseAuth = FirebaseAuth.getInstance();
         currentUser = FirebaseAuth.getInstance().getCurrentUser();
         mDB = FirebaseDatabase.getInstance().getReference();

         userNameText = (TextView) findViewById(R.id.userName);
         firstNameText = (TextView) findViewById(R.id.firstName);
         lastNameText = (TextView) findViewById(R.id.lastName);
         emailText = (TextView) findViewById(R.id.emailAddress);
         phoneText = (TextView) findViewById(R.id.phoneNumber);

         // Log.d("TEST TAG", "created user with id: " + currentUser.getUid());

         // Retrieve Username
//         userName = queryDB("userName");
//         Log.d("Check after query: ", "test string");
//         userNameText.setText("User: " + userName);

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

         bViewParkingSpots = (Button) findViewById(R.id.viewYourParkingSpots);
         bBookParkingSpots = (Button) findViewById(R.id.bookParkingSpots);
         bCreateParkingSpots = (Button) findViewById(R.id.createParkingSpots);
         bEditProfile = (Button) findViewById(R.id.editProfile);
         bLogOut = (Button) findViewById(R.id.logOut);

         bEditProfile.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View view) {
                 Intent editPro5Intent = new Intent(ProfileActivity.this, UpadteProfileActivity.class);
                 startActivity(editPro5Intent);
             }
         });
     }



}











