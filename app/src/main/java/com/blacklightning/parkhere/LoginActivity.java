package com.blacklightning.parkhere;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
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

                // Use semaphore lock to prevent lock user.
                final Semaphore lock = new Semaphore(0);
                mAuth.signInWithEmailAndPassword(params[0], params[1])
                        .addOnCompleteListener(LoginActivity.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> loginTask) {
                                Log.d(TAG, "signInWithEmail:onComplete:" + loginTask.isSuccessful());


                                if (!loginTask.isSuccessful()) {
                                    Log.w(TAG, "signInWithEmail:failed", loginTask.getException());
                                    Toast.makeText(LoginActivity.this, "Wrong email or password.",
                                            Toast.LENGTH_SHORT).show();
                                }
                                lock.release();

                            }
                        });
                try {
                    lock.acquire();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return null;
            }
        }


        bLogin.setOnClickListener(new View.OnClickListener(){
            public void attemptLogin(){
                String email = etUserName.getText().toString();
                String password = etPassword.getText().toString();

                if(TextUtils.isEmpty(email) || TextUtils.isEmpty(password)){
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
