package com.blacklightning.parkhere;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.*;



/**
 * A login screen that offers login via email/password.
 */
public class LoginActivity extends AppCompatActivity {

    Button bLogin;
    AutoCompleteTextView etUserName;
    EditText etPassword;
    Button bRegisterButton;

    @Override
    protected  void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        etUserName = (AutoCompleteTextView) findViewById(R.id.email);
        etPassword = (EditText) findViewById(R.id.password);
        bLogin = (Button) findViewById(R.id.email_sign_in_button);
        bRegisterButton = (Button) findViewById(R.id.buttonRegister);

        bRegisterButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Intent registerIntent = new Intent(LoginActivity.this, RegisterActivity.class);
                LoginActivity.this.startActivity(registerIntent);
            }
        });


        bLogin.setOnClickListener(new View.OnClickListener(){
            public void attemptLogin(){
                String email = etUserName.getText().toString();
                String password = etPassword.getText().toString();
            }

            @Override
            public void onClick(View view) {


            }
        });
    }


}
