package com.blacklightning.parkhere;


import android.content.Intent;
import android.support.test.rule.ActivityTestRule;
import android.support.v7.app.AppCompatActivity;
import android.support.test.*;
import android.support.v7.widget.AppCompatEditText;

import org.junit.Assert;
import org.junit.Test;
import com.blacklightning.parkhere.LoginActivity;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class LoginUnitTest {
    String validEmail = "admin@sjsu.edu";
    String validPassword = "password";
    String invalidEmail = "nope@sjsu.edu";
    String invalidPass = "wrongPass";

    private FirebaseAuth auth;
    private ActivityTestRule<LoginActivity> activityRule = new ActivityTestRule<LoginActivity>(LoginActivity.class,true,false);

    public void setUp(){
        Intent intent = new Intent();

        activityRule.launchActivity(intent);
    }

    public void logInwithWrongEmail(){
        try {
            activityRule.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    ((AppCompatEditText) activityRule.getActivity().findViewById(R.id.email)).setText(invalidEmail);
                }
            });
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }

        activityRule.getActivity().logInCheck();
        Assert.assertNull(activityRule.getActivity().getCurrentUser());

    }

    public void logInwithRightEmail(){
        onView()
    }


}

