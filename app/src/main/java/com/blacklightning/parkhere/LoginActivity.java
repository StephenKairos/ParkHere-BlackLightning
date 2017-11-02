package com.blacklightning.parkhere;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.*;


/**
 * A login screen that offers login via email/password.
 */
public class LoginActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;

    private static final String TAG = "LoginActivity";

    Button bLogin;
    AutoCompleteTextView email;
    EditText etPassword;
    Button bRegisterButton;
    Button bResetButton;
    FirebaseAuth authentication;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        authentication = FirebaseAuth.getInstance();

        email = (AutoCompleteTextView) findViewById(R.id.email);
        etPassword = (EditText) findViewById(R.id.password);
        bLogin = (Button) findViewById(R.id.email_sign_in_button);
        bRegisterButton = (Button) findViewById(R.id.buttonRegister);
        bResetButton = (Button) findViewById(R.id.buttonReset);

        // Click Reset Pass button
        bResetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                resetPass();
            }
        });

        // Click Login Button, check then start Profile activity
        bLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                logInCheck();
            }
        });

        // Click Register Button, start register activity
        bRegisterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent registerIntent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(registerIntent);
            }
        });
    }

    // Send paassword reset to email
    private  void resetPass(){
        String emailCheck = email.getText().toString().trim();

        if(emailCheck.isEmpty()){
            Toast.makeText(this, "Don't leave Email empty!",Toast.LENGTH_SHORT).show();
        }
        else{
            authentication.sendPasswordResetEmail(emailCheck);
            Toast.makeText(this, "Reset Link sent",Toast.LENGTH_SHORT).show();
        }
    }

    // check login info
    private void logInCheck(){
        String emailCheck = email.getText().toString().trim();
        String passCheck = etPassword.getText().toString().trim();

        if(emailCheck.isEmpty() || passCheck.isEmpty()){
            Toast.makeText(this, "Don't leave any field empty!",Toast.LENGTH_SHORT).show();
        }

        authentication.signInWithEmailAndPassword(emailCheck, passCheck).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    // Sign in success, update UI with the signed-in user's information
                    Log.d(TAG, "createUserWithEmail:success");
                    FirebaseUser user = mAuth.getCurrentUser();
                    Intent profileIntent = new Intent(LoginActivity.this, ProfileActivity.class);
                    startActivity(profileIntent);
                } else {
                    // If sign in fails, display a message to the user.
                    Log.w(TAG, "createUserWithEmail:failure", task.getException());
                    Toast.makeText(LoginActivity.this, "Authentication failed.", Toast.LENGTH_SHORT).show();
                }

                // ...
            }
        });
    }

}
