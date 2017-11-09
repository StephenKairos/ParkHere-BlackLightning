package com.blacklightning.parkhere;

import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Map;

/**
 * Created by Jason Liu on 11/1/2017.
 */

public class ParkingListingActivity extends AppCompatActivity implements View.OnClickListener{
    Button bSearch;
    ListView listViewer;
    ListAdapter listAdapter;
    private ArrayList<ParkingItem> listings;
    private static FirebaseAuth firebaseAuth;
    private static FirebaseUser currentUser;
    private static DatabaseReference mDB;
    private DataSnapshot parkingSpotList;


    public String queryResult;

    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_parking_spot);

        firebaseAuth = FirebaseAuth.getInstance();
        currentUser = FirebaseAuth.getInstance().getCurrentUser();
        mDB = FirebaseDatabase.getInstance().getReference();

        listings = new ArrayList<>();

        DatabaseReference ref = mDB.child("parkingspot");
        ref.addListenerForSingleValueEvent(
                new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        //Get map of users in datasnapshot
                        parkingSpotList = dataSnapshot;
                        for(DataSnapshot user : parkingSpotList.getChildren()){
                            for(DataSnapshot spaceItem : user.getChildren()) {
                                Log.d("Space", spaceItem.toString());
                                Log.d("Zip", spaceItem.child("stAddress").getValue().toString());

                                ParkingItem entry = new ParkingItem(spaceItem.child("stAddress").getValue().toString(), spaceItem.child("stAddress").getValue().toString(), spaceItem.child("stAddress").getValue().toString());
                                Log.d("Entry", entry.getAddress().toString());
                                listings.add(entry);
                            }
                        }

                        for(ParkingItem space : listings) {
                            Log.d("TestPark", space.getAddress());
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        //handle databaseError
                    }
                });

//        listViewer = (ListView) findViewById(R.id.park);
//        listViewer.setAdapter(listAdapter);

        bSearch = (Button) findViewById(R.id.searchButton);
    }

    @Override
    public void onClick(View v) {
        Log.d("TEST","Something Clicked");

        if(v == bSearch){


        }
    }

    public class ParkingItem {

        private String parkingID;
        private String address;
        private String zip;

        public ParkingItem(String id, String address, String zip) {
            this.parkingID = id;
            this.address = address;
            this.zip = zip;
        }

        public String getID() { return parkingID; }
        public String getAddress() { return address; }
        public String getZip() { return zip; }
    }
}