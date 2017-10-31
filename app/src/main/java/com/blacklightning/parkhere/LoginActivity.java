package com.blacklightning.parkhere;

import android.content.Intent;
import android.os.AsyncTask;
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
import com.google.firebase.database.*;

import java.util.concurrent.Semaphore;


/**
 * A login screen that offers login via email/password.
 */
public class LoginActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;

    private static final String TAG = "LoginActivity";

    Button bLogin;
    AutoCompleteTextView etUserName;
    EditText etPassword;
    Button bRegisterButton;

    @Override
    protected  void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        mAuth = FirebaseAuth.getInstance();

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

        class LoginTask extends AsyncTask<String, Void, Void> {

            @Override
            protected Void doInBackground(String... params) {

                final Semaphore mSemaphore = new Semaphore(0);
                mAuth.signInWithEmailAndPassword(params[0], params[1])
                        .addOnCompleteListener(LoginActivity.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                Log.d(TAG, "signInWithEmail:onComplete:" + task.isSuccessful());

                                // If sign in fails, display a message to the user. If sign in succeeds
                                // the auth state listener will be notified and logic to handle the
                                // signed in user can be handled in the listener.
                                if (!task.isSuccessful()) {
                                    Log.w(TAG, "signInWithEmail:failed", task.getException());
                                    Toast.makeText(LoginActivity.this, "Incorrect email or password. Please try again.",
                                            Toast.LENGTH_SHORT).show();
                                }
                                mSemaphore.release();

                            }
                        });
                try {
                    mSemaphore.acquire();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                return null;
            }
        }


        bLogin.setOnClickListener(new View.OnClickListener(){
            public void attemptLogin(){
                String email = etUserName.getText().toString();
                String password = etPassword.getText().toString();

                if(email.isEmpty() || password.isEmpty()){
                    return;
                }

                // check firebase login
                new LoginTask().execute(email,password);
            }

            @Override
            public void onClick(View view) {


            }
        });
    }


}
