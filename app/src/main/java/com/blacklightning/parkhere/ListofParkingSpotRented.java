package com.blacklightning.parkhere;

import android.content.Context;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ListofParkingSpotRented extends AppCompatActivity {

    private ArrayList<String> listings;
    private ArrayList<IDContainer> idListings;

    //private ArrayList<String> parkingIDArrayList;
    private DataSnapshot parkingSpotList;
    ArrayAdapter<String> listAdapter;
    private String userID;
    ListView listView;
    private ArrayList<String> parkingIDArrayList;
    private static FirebaseAuth firebaseAuth;
    private static FirebaseUser currentUser;
    private static DatabaseReference mDB;
    private String parkingUserID;
    private String parkingID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listof_parking_spot_rented);
        firebaseAuth = FirebaseAuth.getInstance();
        currentUser = FirebaseAuth.getInstance().getCurrentUser();
        Intent intent = getIntent();
        mDB = FirebaseDatabase.getInstance().getReference();
        userID = intent.getStringExtra("userID");

        listView = (ListView) findViewById(R.id.rentedList);
        listings = new ArrayList<>();
        idListings = new ArrayList<>();
        parkingIDArrayList = new ArrayList<>();
        if(currentUser != null){
            System.out.println("Able to run");
            DatabaseReference ref = mDB.child("users").child(userID).child("rented");
            ref.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {

                    parkingSpotList = dataSnapshot;
                    for(DataSnapshot spaceItem : parkingSpotList.getChildren()){
                        parkingID = spaceItem.child("id").getValue().toString();
                        parkingUserID = spaceItem.child("userId").getValue().toString();

                        String testCheck = parkingUserID + ": " + parkingID;

                        IDContainer container = new IDContainer(parkingUserID, parkingID);
                        idListings.add(container);

                        Log.d("UserAndParking", testCheck);

                        DatabaseReference toParking = mDB.child("parkingspot").child(parkingUserID).child(parkingID);
                        toParking.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {
                                parkingIDArrayList.add(dataSnapshot.getKey().toString());
                                String stAddress = dataSnapshot.child("stAddress").getValue().toString();
                                String city = dataSnapshot.child("city").getValue().toString();
                                String state = dataSnapshot.child("state").getValue().toString();

                                String latlngAddress = stAddress + ", " + city + ", " + state;

                                Context context = ListofParkingSpotRented.this;
                                Geocoder geocoder = new Geocoder(context);

                                try {
                                    List<Address> coordinateList = geocoder.getFromLocationName(latlngAddress, 5);
                                    if(coordinateList.size()>0) {
                                        System.out.println("Length: " + coordinateList.size());
                                        Address coor = coordinateList.get(0);
                                        listings.add(latlngAddress);

                                        System.out.println("Length listings 2: " + listings.size());
                                    }
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                                System.out.println("Length listings: " + listings.size());
                                listAdapter = new ArrayAdapter<>(ListofParkingSpotRented.this, android.R.layout.simple_list_item_1, listings);
                                listView.setAdapter(listAdapter);
                            }

                            @Override
                            public void onCancelled(DatabaseError databaseError) {

                            }
                        });


                    }

                    }



                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });
        }
    listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
            String selection = listAdapter.getItem(i);
            System.out.println("Address Rented: " + selection);

            for(int x = 0; x < idListings.size(); x++) {
                String testCheck = idListings.get(i).getUserID() + ": " + idListings.get(i).getID();

                Log.d("IDArray", testCheck);
            }

            System.out.println("Length ParkingID: " + parkingIDArrayList.size());
            for(int j = 0; j < listings.size(); j++){
                if(listings.get(j).equals(selection)){
                    Intent ownedParkingSpot = new Intent(ListofParkingSpotRented.this, ParkingSpotActivity.class);

                    ownedParkingSpot.putExtra("userID", idListings.get(j).getUserID());
                    String parkingSpotID = parkingIDArrayList.get(j);

                    ownedParkingSpot.putExtra("pSpotID", idListings.get(j).getID());
                    startActivity(ownedParkingSpot);
                }
            }
        }
    });

    }

    public class IDContainer {

        private String userID;
        private String parkingID;

        public IDContainer(String userID, String id) {
            this.userID = userID;
            this.parkingID = id;
        }

        public String getID() { return parkingID; }
        public String getUserID() { return userID; }
    }
}
