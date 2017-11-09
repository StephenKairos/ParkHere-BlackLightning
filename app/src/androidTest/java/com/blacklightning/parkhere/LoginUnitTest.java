package com.blacklightning.parkhere;

import org.junit.Test;
import org.junit.runner.RunWith;
import android.content.Intent;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.support.v7.widget.AppCompatEditText;

import com.google.firebase.auth.FirebaseAuth;

import org.junit.Assert;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;

@RunWith(AndroidJUnit4.class)
public class LoginUnitTest {
    String validEmail = "admin@sjsu.edu";
    String validPassword = "password";
    String invalidEmail = "nope@sjsu.edu";
    String invalidPass = "wrongPass";

    private FirebaseAuth auth;
    private ActivityTestRule<LoginActivity> activityRule = new ActivityTestRule<LoginActivity>(LoginActivity.class,true,false);

    public void setUp(){
        Intent intent = new Intent();
        activityRule.getActivity();
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

    public void logInwithWrongEmail_blackBox(){
        onView(withId(R.id.email)).perform(typeText(invalidEmail));
        onView(withId(R.id.password)).perform(typeText(validPassword));
        onView(withId(R.id.email_sign_in_button)).perform(click());
        onView(withId(R.id.layoutLogin)).check(matches(isDisplayed()));
        activityRule.getActivity().finish();
    }

    public void logInwithRightEmail(){
        onView(withId(R.id.email)).perform(typeText(validEmail));
        onView(withId(R.id.password)).perform(typeText(validPassword));
        onView(withId(R.id.email_sign_in_button)).perform(click());
        onView(withId(R.id.layoutLogin)).check(matches(isDisplayed()));
    }


}

