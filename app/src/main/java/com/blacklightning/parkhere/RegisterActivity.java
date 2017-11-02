package com.blacklightning.parkhere;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

/**
 * Created by franc on Oct/31/2017.
 */

public class RegisterActivity extends AppCompatActivity {
    Button bRegister;
    EditText firstNameText;
    EditText lastNameText;
    EditText userName;
    EditText emailText;
    EditText password;
    EditText reEnterPassword;

    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        bRegister = (Button) findViewById(R.id.buttonRegister);
        firstNameText = (EditText) findViewById(R.id.firstNameText);
        lastNameText = (EditText) findViewById(R.id.lastNameText);
        userName = (EditText) findViewById(R.id.userName);
        emailText = (EditText) findViewById(R.id.emailText);
        password = (EditText) findViewById(R.id.password);
        reEnterPassword = (EditText) findViewById(R.id.reEnterPassword);



    }

}









