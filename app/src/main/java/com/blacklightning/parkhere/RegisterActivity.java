package com.blacklightning.parkhere;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.*;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

/**
 * Created by franc on Oct/31/2017.
 */

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener{
    Button buttonRegister;
    EditText firstNameText;
    EditText lastNameText;
    EditText userName;
    EditText emailText;
    EditText password;
    EditText reEnterPassword;

    // Firebase References
    private FirebaseAuth firebaseAuth;
    private DatabaseReference fBase;
    private FirebaseUser currentUser;

    private ProgressDialog progressDialog;

    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        // Establish Database and User
        fBase = FirebaseDatabase.getInstance().getReference();
        currentUser = FirebaseAuth.getInstance().getCurrentUser();
        Log.d("User before: ", currentUser.getUid());

        buttonRegister = (Button) findViewById(R.id.bRegister);
        firstNameText = (EditText) findViewById(R.id.firstNameText);
        lastNameText = (EditText) findViewById(R.id.lastNameText);
        userName = (EditText) findViewById(R.id.userName);
        emailText = (EditText) findViewById(R.id.emailText);
        password = (EditText) findViewById(R.id.passwordEditText);
        reEnterPassword = (EditText) findViewById(R.id.reEnterPassword);

        buttonRegister.setOnClickListener(this);
        progressDialog = new ProgressDialog(this);
        firebaseAuth = FirebaseAuth.getInstance();

    }
    private void registerUser(){
        String email = emailText.getText().toString().trim();
        String pw = password.getText().toString().trim();
        if(TextUtils.isEmpty(email)){
            Toast.makeText(this, "email is empty", Toast.LENGTH_LONG).show();
            return;
        }
        if(TextUtils.isEmpty(pw)){
            Toast.makeText(this, "pw is empty", Toast.LENGTH_LONG).show();
            return;
        }

        progressDialog.setMessage("Registering...");
        progressDialog.show();
        firebaseAuth.createUserWithEmailAndPassword(email,pw)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            Toast.makeText(RegisterActivity.this, "reg. succ", Toast.LENGTH_LONG).show();
                            //return;
                            createProfile();
                        }
                        else{
                            Toast.makeText(RegisterActivity.this, "reg. fail", Toast.LENGTH_LONG).show();
                            //return;
                        }
                    }
                });
    }
    @Override
    public void onClick(View view) {
        if(view == this.buttonRegister){
            registerUser();
            startActivity(new Intent(this, LoginActivity.class));
        }
    }

    public void createProfile() {
        //Reconfirm User
        currentUser = FirebaseAuth.getInstance().getCurrentUser();
        Log.d("User before: ", currentUser.getUid().toString());

        //Registration successful, let's add the user's info into the database
    }
}









