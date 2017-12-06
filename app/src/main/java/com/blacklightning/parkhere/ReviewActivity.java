package com.blacklightning.parkhere;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ReviewActivity extends AppCompatActivity {
    private static FirebaseAuth firebaseAuth;
    private static FirebaseUser currentUser;
    private static DatabaseReference mDB;
    Button bSubmit;
    private String userID, pSpotID, concatenate;
    EditText rating, review;
    ListView allReview;
    ArrayList<String> reviewString;
    ArrayAdapter<String> listAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        firebaseAuth = FirebaseAuth.getInstance();
        currentUser = FirebaseAuth.getInstance().getCurrentUser();
        mDB = FirebaseDatabase.getInstance().getReference();
        setContentView(R.layout.activity_review);
        Bundle extra = getIntent().getExtras();
        userID = extra.getString("userID");
        pSpotID = extra.getString("parkingID");
        concatenate = currentUser.getUid() + pSpotID;
        rating = (EditText) findViewById(R.id.intRate);
        review = (EditText) findViewById(R.id.Description);
        bSubmit = (Button) findViewById(R.id.Submit);
        allReview = (ListView) findViewById(R.id.rentedList);
        reviewString = new ArrayList<>();
        bSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               DatabaseReference ref = mDB.child("parkingspot").child(userID).child(pSpotID).child("ratings").child(concatenate);
               ref.child("rating").setValue(rating.getText().toString());
               ref.child("review").setValue(review.getText().toString());
            }
        });

        DatabaseReference ref = mDB.child("parkingspot").child(userID).child(pSpotID).child("ratings");
        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot spaceItem : dataSnapshot.getChildren()){
                    String rating = spaceItem.child("rating").getValue().toString();
                    String review = spaceItem.child("review").getValue().toString();
                    //System.out.println("Review: " + review);
                    String blah = "STAR: " + rating + "\nDESCRIPTION: " + review;
                    //System.out.println("Rating: " + blah);
                    reviewString.add(blah);
                }
                System.out.println("Length review list: " + reviewString.size());
                listAdapter = new ArrayAdapter<>(ReviewActivity.this, android.R.layout.simple_list_item_1, reviewString);
                allReview.setAdapter(listAdapter);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


    }

}
