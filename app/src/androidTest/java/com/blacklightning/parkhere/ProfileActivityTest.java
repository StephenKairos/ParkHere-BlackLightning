package com.blacklightning.parkhere;

/**
 * Created by franc on Nov/9/2017.
 */

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.SystemClock;
import android.support.annotation.NonNull;
import android.support.test.rule.ActivityTestRule;
import android.support.test.rule.UiThreadTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.support.v7.widget.AppCompatEditText;
import com.blacklightning.parkhere.ProfileActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.junit.Assert;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import android.util.Log;
import android.widget.TextView;

@RunWith(AndroidJUnit4.class)
public class ProfileActivityTest {
    private String userName, firstName, lastName, email, phoneNumber;
    private String id;
    private FirebaseAuth firebaseAuth;
    private FirebaseUser currentUser;
    private DatabaseReference mDB;
    private Boolean loggedIn;

    @Rule
    public ActivityTestRule<ProfileActivity> activityRule = new ActivityTestRule<ProfileActivity>(ProfileActivity.class, true, false);

    @Before
    public void setUp(){
        loggedIn = false;

        firebaseAuth = FirebaseAuth.getInstance();
        mDB = FirebaseDatabase.getInstance().getReference();
        currentUser = firebaseAuth.getCurrentUser();

        firebaseAuth.signInWithEmailAndPassword("admin@sjsu.edu","password");

        firebaseAuth.addAuthStateListener(new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                if(firebaseAuth.getCurrentUser() == null){
                    Log.i("LOG: ", "Not login");
                } else{
                    firebaseAuth = FirebaseAuth.getInstance();
                    currentUser = firebaseAuth.getCurrentUser();
                    mDB = FirebaseDatabase.getInstance().getReference();
                    boolean loggedIn = true;
                }
            }
        });
    }

    @Test
    public void displayInfo(){
        mDB.child("users").child(currentUser.getUid()).child("userName").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                userName = snapshot.getValue().toString();
                //userNameText.setHint("User: " );//+ currentUserName);
                Log.i("user: ", userName);
                Assert.assertEquals("admin", userName);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
}