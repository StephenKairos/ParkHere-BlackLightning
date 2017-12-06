package com.blacklightning.parkhere;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;

public class ReviewActivity extends AppCompatActivity {
    private static FirebaseAuth firebaseAuth;
    private static FirebaseUser currentUser;
    private static DatabaseReference mDB;
    Button bSubmit;
    private String userID, pSpotID;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_review);
        Bundle extra = getIntent().getExtras();
        userID = extra.getString("userId");
        pSpotID =extra.getString("parkingID");
        bSubmit = (Button) findViewById(R.id.Submit);
        bSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                
            }
        });


    }

}
