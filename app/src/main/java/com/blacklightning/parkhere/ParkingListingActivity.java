package com.blacklightning.parkhere;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.model.LatLng;
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
import java.util.Locale;
import java.util.Map;

/**
 * Created by Jason Liu on 11/1/2017.
 */

public class ParkingListingActivity extends AppCompatActivity implements View.OnClickListener{
    Button bSearch;
    ListView listViewer ;
    ArrayAdapter<String> listAdapter;
    private ArrayList<ParkingItem> listings;
    private ArrayList<ParkingItem> filteredListings;
    private ArrayList<Integer> parkingPopularityCounters;
    private static FirebaseAuth firebaseAuth;
    private static FirebaseUser currentUser;
    private static DatabaseReference mDB;
    private DataSnapshot parkingSpotList;

    public String queryResult;
    public static final double MAX_DISTANCE = 10000;
    public static final int COLOR_CONSTANT = 16;

    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_parking_spot);

        firebaseAuth = FirebaseAuth.getInstance();
        currentUser = FirebaseAuth.getInstance().getCurrentUser();
        mDB = FirebaseDatabase.getInstance().getReference();

        listings = new ArrayList<>();
        filteredListings = new ArrayList<>();

        bSearch = (Button) findViewById(R.id.searchButton);
        bSearch.setOnClickListener(this);

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

                                String stAddress = spaceItem.child("stAddress").getValue().toString();
                                String city = spaceItem.child("city").getValue().toString();
                                String state = spaceItem.child("state").getValue().toString();

                                int counter = Integer.parseInt(spaceItem.child("counter").getValue().toString());

                                String latlngAddress = stAddress + ", " + city + ", " + state;
                                Context context = ParkingListingActivity.this;

                                Geocoder geocoder = new Geocoder(context);
                                try {
                                    List<Address> coordinateList = geocoder.getFromLocationName(latlngAddress, 5);
                                    if(coordinateList.size()>0) {
                                        Address coor = coordinateList.get(0);
                                        ParkingItem entry = new ParkingItem(user.getKey(), spaceItem.child("id").getValue().toString(), coor, counter);
                                        listings.add(entry);

                                    }
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                            }
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        //handle databaseError
                    }
                });

        listViewer = (ListView) findViewById(R.id.parkingListings);
        //listViewer.setAdapter(listAdapter);
        listViewer.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String selection = listAdapter.getItem(i);
                ParkingItem pItem = null;

                for(ParkingItem item : filteredListings) {

                    Log.d("Current ID", item.getUserID());

                    if(item.getAddress().getAddressLine(0).equals(selection)) {


                        pItem = item;
                        break;
                    }
                }

                if(pItem != null) {
                    Toast.makeText(ParkingListingActivity.this, "Selected item: " + pItem.getID(), Toast.LENGTH_SHORT).show();
                    Intent parkingSpotIntent = new Intent(ParkingListingActivity.this, ParkingSpotActivity.class);

                    if(pItem.getID() == null) {
                        System.out.println("PID is NULL");
                    }

                    Log.d("Parking Spot", pItem.getID());
                    Log.d("User", pItem.getUserID());

                    parkingSpotIntent.putExtra("pSpotID", pItem.getID());
                    parkingSpotIntent.putExtra("userID", pItem.getUserID());

                    startActivity(parkingSpotIntent);
                }
            }
        });
    }



    @Override
    public void onClick(View v) {
        Log.d("TEST","Something Clicked");

        ListView parkingListings = (ListView) findViewById(R.id.parkingListings);

        System.out.println("Listings: " + listings.size());

        if(v == bSearch && listings != null && !listings.isEmpty()){
            System.out.println("Start Filter");

            EditText zipSearch = (EditText) findViewById(R.id.searchText);

            if(zipSearch != null) {
                try {
                    Geocoder geocoder = new Geocoder(ParkingListingActivity.this, Locale.getDefault());
                    List<Address> addresses = geocoder.getFromLocationName(zipSearch.getText().toString(), 1);

                    System.out.println("Dafuq: " + addresses.size());
                    if (addresses != null && !addresses.isEmpty()) {
                        Address zipAddr = addresses.get(0);
                        System.out.println("LatLongCheck: " + zipAddr.getLatitude() + " and " + zipAddr.getLongitude());
                        filteredListings = new ArrayList<>();
                        ArrayList<String> listStrings = new ArrayList<>();

                        for (ParkingItem item : listings) { // Goes through all listings in query
                            double distance = MAX_DISTANCE + 1;
                            float[] result = new float[3];
                            Location.distanceBetween(zipAddr.getLatitude(), zipAddr.getLongitude(), item.getAddress().getLatitude(), item.getAddress().getLongitude(), result);
                            distance = (double) result[0];
                            if(distance <= MAX_DISTANCE) { // Filters listings closest to user's query (aka ZIP or Address)
                                filteredListings.add(item);
                                listStrings.add(item.getAddress().getAddressLine(0));
                            }
                        }

                        System.out.println("Filtered: " + filteredListings.size());

                        listAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, listStrings) {
                            @Override
                            public View getView(int position, View convertView, ViewGroup parent) {
                                View v = super.getView(position, convertView, parent);
                                TextView tv = (TextView) v.findViewById(android.R.id.text1);

                                int currentCounter = filteredListings.get(position).getCounter();
                                String testColor = "Position: " + position + " Counter: " + currentCounter;
                                Log.d("ColorAtPos", testColor);

                                int colorValue = COLOR_CONSTANT * (COLOR_CONSTANT - currentCounter);

                                if(colorValue < 0) { colorValue = 0; } else if(colorValue > 255) { colorValue = 255; }

                                if(currentCounter >= COLOR_CONSTANT/2) { // If the background color is darker than the middle threshold
                                    tv.setTextColor(Color.WHITE);
                                }



                                v.setBackgroundColor(Color.rgb(colorValue, colorValue, colorValue));

                                return v;
                            }
                        };

                        System.out.println("Adapter: " + listAdapter.getCount());
                        parkingListings.setAdapter(listAdapter);
//                        if(listAdapter.getCount() >0 ){
//                            for(int i=0; i<listAdapter.getCount(); i++){
//                                if(parkingListings.getChildAt(i)!= null) System.out.println("exists");
//                            }
//                        }


                    }
                } catch (IOException e) {
                    // handle exception
                }
            }
        }
    }

        public class ParkingItem {

            private String userID;
            private String parkingID;
            private Address addr;
            private int counter;

            public ParkingItem(String userID, String id, Address addr, int counter) {
                this.userID = userID;
                this.parkingID = id;
                this.addr = addr;
                this.counter = counter;
            }

            public String getID() { return parkingID; }
            public String getUserID() { return userID; }
            public Address getAddress() { return addr; }
            public int getCounter() { return counter; }
        }
}