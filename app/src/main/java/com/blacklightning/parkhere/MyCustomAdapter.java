package com.blacklightning.parkhere;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListAdapter;
import android.widget.Button;

import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;


/**
 * Created by Jason Liu on 12/3/2017.
 */

public class MyCustomAdapter extends BaseAdapter implements ListAdapter{
    private ArrayList<String> parkingList = new ArrayList<String>();
    private ArrayList<String> parkingIDList = new ArrayList<String>();
    private String userID ;
    private Context context;
    private FirebaseAuth firebaseAuth;
    private FirebaseUser currentUser;
    private DatabaseReference mDB;


    public MyCustomAdapter(ArrayList<String> parkinglist,ArrayList<String> parkingIDlist, String userid, Context context) {
        this.parkingList = parkinglist;
        this.parkingIDList = parkingIDlist;
        this.userID=userid;
        this.context = context;

        currentUser = FirebaseAuth.getInstance().getCurrentUser();
        mDB = FirebaseDatabase.getInstance().getReference();
        firebaseAuth = FirebaseAuth.getInstance();

    }

    @Override
    public int getCount() {
        return parkingList.size();
    }

    @Override
    public Object getItem(int pos) {
        return parkingList.get(pos);
    }

    @Override
    public long getItemId(int pos) {
        return 0;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        View view = convertView;
        if (view == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.custom_layout, null);
        }

        //Handle TextView and display string from your list
        final TextView listItemText = (TextView)view.findViewById(R.id.parkingSpaceString);
        listItemText.setText(parkingList.get(position));

        //Handle buttons and add onClickListeners
        Button deleteBtn = (Button)view.findViewById(R.id.deleteButton);
        Button editBtn = (Button)view.findViewById(R.id.editButton);
        listItemText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent ownedParkingSpot = new Intent(context, ParkingSpotActivity.class);
                ownedParkingSpot.putExtra("userID", userID);
                String parkingSpotID = parkingIDList.get(position);
                ownedParkingSpot.putExtra("pSpotID",parkingSpotID);
                context.startActivity(ownedParkingSpot);
            }
        });

        deleteBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                String dParkingID = parkingIDList.get(position) ;
                ListofParkingSpotOwned.deleteEntry(parkingIDList.get(position), userID);
                parkingList.remove(position);
                parkingIDList.remove(position);//or some other task
                notifyDataSetChanged();
            }
        });
        editBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {

                Intent editParkingSpot = new Intent(context, EditParkingSpotActivity.class);
                editParkingSpot.putExtra("userID", userID);
                String parkingSpotID = parkingIDList.get(position);
                editParkingSpot.putExtra("pSpotID",parkingSpotID);
                context.startActivity(editParkingSpot);
                notifyDataSetChanged();
            }
        });

        return view;
    }
}
