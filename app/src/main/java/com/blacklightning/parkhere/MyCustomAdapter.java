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

import java.util.ArrayList;

import static android.support.v4.content.ContextCompat.startActivity;

/**
 * Created by Jason Liu on 12/3/2017.
 */

public class MyCustomAdapter extends BaseAdapter implements ListAdapter{
    private ArrayList<String> parkingList = new ArrayList<String>();
    private ArrayList<String> parkingIDList = new ArrayList<String>();
    private String userID ;
    private Context context;



    public MyCustomAdapter(ArrayList<String> parkinglist,ArrayList<String> parkingIDlist, String userid, Context context) {
        this.parkingList = parkinglist;
        this.parkingIDList = parkingIDlist;
        this.userID=userid;
        this.context = context;
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
                for(int j = 0; j < parkingList.size(); j++){
                    if(parkingList.get(j).equals(listItemText.getText().toString())){
                        Intent ownedParkingSpot = new Intent(context, ParkingSpotActivity.class);
                        ownedParkingSpot.putExtra("userID", userID);
                        String parkingSpotID = parkingIDList.get(j);
                        ownedParkingSpot.putExtra("pSpotID", parkingSpotID);
                        context.startActivity(ownedParkingSpot);
                    }
                }
            }
        });

        deleteBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                //do something
                parkingList.remove(position); //or some other task
                notifyDataSetChanged();
            }
        });
        editBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                //do something

                notifyDataSetChanged();
            }
        });

        return view;
    }
}
