package com.blacklightning.parkhere;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;
import com.google.firebase.auth.*;
import com.google.firebase.database.*;


public class UpdateProfileActivity extends AppCompatActivity {

    String currentUserName, currentFirstName, currentLastName, currentEmail, currentPhone;
    private DatabaseReference mDB;
    private FirebaseAuth firebaseAuth;
    private FirebaseUser currentUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_profile);
        currentUser = FirebaseAuth.getInstance().getCurrentUser();
        mDB = FirebaseDatabase.getInstance().getReference();
        firebaseAuth = FirebaseAuth.getInstance();


        final EditText userNameText = (EditText) findViewById(R.id.userNameText);
        final EditText firstNameText = (EditText) findViewById(R.id.firstNameText);
        final EditText lastNameText = (EditText) findViewById(R.id.lastNameText);
        final EditText emailText = (EditText) findViewById(R.id.emailText);
        final EditText phoneText = (EditText) findViewById(R.id.phoneText);

        mDB.child("users").child(currentUser.getUid()).child("userName").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                currentUserName = snapshot.getValue().toString();
                userNameText.setHint("User: " + currentUserName);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        mDB.child("users").child(currentUser.getUid()).child("firstNameText").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                currentFirstName = snapshot.getValue().toString();
                firstNameText.setHint("First Name: " + currentFirstName);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        mDB.child("users").child(currentUser.getUid()).child("lastNameText").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                currentLastName = snapshot.getValue().toString();
                lastNameText.setHint("Last Name: " + currentLastName);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        mDB.child("users").child(currentUser.getUid()).child("emailText").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                currentEmail = snapshot.getValue().toString();
                emailText.setHint("Email: " + currentEmail);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        mDB.child("users").child(currentUser.getUid()).child("phoneNumber").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                currentPhone = snapshot.getValue().toString();
                phoneText.setHint("Phone Number: " + currentPhone);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
}
