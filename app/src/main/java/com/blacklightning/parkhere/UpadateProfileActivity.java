package com.blacklightning.parkhere;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;
import com.google.firebase.auth.*;
import com.google.firebase.database.*;


public class UpadateProfileActivity extends AppCompatActivity {

    String currentUserName, currentFirstName, currentLastName, currentEmail, currentPhone;
    private DatabaseReference mDB;
    private FirebaseAuth firebaseAuth;
    private FirebaseUser currentUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upadte_profile);
        currentUser = FirebaseAuth.getInstance().getCurrentUser();
        mDB = FirebaseDatabase.getInstance().getReference();
        firebaseAuth = FirebaseAuth.getInstance();


        final EditText userNameText = (EditText) findViewById(R.id.userNameText);
        EditText firstNameText = (EditText) findViewById(R.id.firstNameText);
        EditText lastNameText = (EditText) findViewById(R.id.lastNameText);
        EditText emailText = (EditText) findViewById(R.id.emailText);
        EditText phoneText = (EditText) findViewById(R.id.phoneText);

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
    }
}
