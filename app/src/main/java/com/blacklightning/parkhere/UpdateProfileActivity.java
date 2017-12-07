package com.blacklightning.parkhere;

import android.content.Intent;
import android.nfc.Tag;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.*;
import com.google.firebase.database.*;

import static android.widget.Toast.LENGTH_SHORT;


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
        final EditText confirmPassText = (EditText) findViewById(R.id.confirmPassword);
        final Button submitButton = (Button) findViewById(R.id.Confirm);

        mDB.child("users").child(currentUser.getUid()).child("userName").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                currentUserName = snapshot.getValue().toString();
                //userNameText.setHint("User: " );//+ currentUserName);
                userNameText.setText(currentUserName);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        mDB.child("users").child(currentUser.getUid()).child("firstNameText").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                currentFirstName = snapshot.getValue().toString();
                firstNameText.setText(currentFirstName);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        mDB.child("users").child(currentUser.getUid()).child("lastNameText").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                currentLastName = snapshot.getValue().toString();
                lastNameText.setText(currentLastName);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        mDB.child("users").child(currentUser.getUid()).child("emailText").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                currentEmail = snapshot.getValue().toString();
                emailText.setText(currentEmail);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        mDB.child("users").child(currentUser.getUid()).child("phoneNumber").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                currentPhone = snapshot.getValue().toString();
                phoneText.setText(currentPhone);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        /*
        userNameText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if(charSequence.toString().trim().length() == 0){
                    confirmPassText.setEnabled(false);
                    submitButton.setEnabled(false);
                }
                else{
                    confirmPassText.setEnabled(true);
                    submitButton.setEnabled(true);
                }
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        firstNameText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if(charSequence.toString().trim().length() == 0){
                    confirmPassText.setEnabled(false);
                    submitButton.setEnabled(false);
                }
                else{
                    confirmPassText.setEnabled(true);
                    submitButton.setEnabled(true);
                }
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        lastNameText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if(charSequence.toString().trim().length() == 0){
                    confirmPassText.setEnabled(false);
                    submitButton.setEnabled(false);
                }
                else{
                    confirmPassText.setEnabled(true);
                    submitButton.setEnabled(true);
                }
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        emailText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if(charSequence.toString().trim().length() == 0){
                    confirmPassText.setEnabled(false);
                    submitButton.setEnabled(false);
                }
                else{
                    confirmPassText.setEnabled(true);
                    submitButton.setEnabled(true);
                }
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        phoneText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if(charSequence.toString().trim().length() == 0){
                    confirmPassText.setEnabled(false);
                    submitButton.setEnabled(false);
                }
                else{
                    confirmPassText.setEnabled(true);
                    submitButton.setEnabled(true);
                }
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        */
        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(TextUtils.isEmpty(userNameText.getText().toString()) ||
                        TextUtils.isEmpty(firstNameText.getText().toString()) ||
                        TextUtils.isEmpty(lastNameText.getText().toString()) ||
                        TextUtils.isEmpty(emailText.getText().toString()) ||
                        TextUtils.isEmpty(phoneText.getText().toString())) {

                    Toast.makeText(UpdateProfileActivity.this, "Don't leave any field empty", LENGTH_SHORT).show();

                } else if(TextUtils.isEmpty(confirmPassText.getText().toString())){
                        Toast.makeText(UpdateProfileActivity.this, "Enter Password to Confirm", Toast.LENGTH_LONG).show();
                } else {

                    //Re-authenticate the user

                    currentUser = firebaseAuth.getCurrentUser();

                    AuthCredential credential = EmailAuthProvider.getCredential(emailText.getText().toString(), confirmPassText.getText().toString());

                    currentUser.reauthenticate(credential).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            Log.d("Authentication Check", "User re-authenticated.");

                            mDB.child("users").child(currentUser.getUid()).child("userName").setValue(userNameText.getText().toString());
                            mDB.child("users").child(currentUser.getUid()).child("emailText").setValue(emailText.getText().toString());
                            mDB.child("users").child(currentUser.getUid()).child("firstNameText").setValue(firstNameText.getText().toString());
                            mDB.child("users").child(currentUser.getUid()).child("lastNameText").setValue(lastNameText.getText().toString());
                            mDB.child("users").child(currentUser.getUid()).child("phoneNumber").setValue(phoneText.getText().toString());

                            Intent profileIntent = new Intent(UpdateProfileActivity.this, ProfileActivity.class);
                            startActivity(profileIntent);
                            finish();
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(UpdateProfileActivity.this, "Authentication Failed. Try again.", Toast.LENGTH_LONG).show();
                            Intent editPro5Intent = new Intent(UpdateProfileActivity.this, UpdateProfileActivity.class);
                            startActivity(editPro5Intent);
                            finish();
                        }
                    });



                    /*
                    mDB.child("users").child(currentUser.getUid()).child("userName").setValue(userNameText.getText().toString());
                    mDB.child("users").child(currentUser.getUid()).child("emailText").setValue(emailText.getText().toString());
                    mDB.child("users").child(currentUser.getUid()).child("firstNameText").setValue(firstNameText.getText().toString());
                    mDB.child("users").child(currentUser.getUid()).child("lastNameText").setValue(lastNameText.getText().toString());
                    mDB.child("users").child(currentUser.getUid()).child("phoneNumber").setValue(phoneText.getText().toString());
                    */
                }
            }
        });


    }
}
