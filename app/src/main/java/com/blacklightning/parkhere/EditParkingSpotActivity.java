package com.blacklightning.parkhere;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;


import com.google.android.gms.maps.model.LatLng;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.sql.Time;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class EditParkingSpotActivity extends AppCompatActivity implements View.OnClickListener {

    private FirebaseAuth firebaseAuth;
    private FirebaseAuth.AuthStateListener authListener;
    private DatabaseReference mDB;
    private FirebaseUser currentUser;
    private String stAddress, city, state, zipCode, rate, timeStart, timeEnd, dateStart, dateEnd;

    private String currentSpotID;
    Button bSubmit;
    EditText etStAddress;
    EditText etCity;
    EditText etState;
    EditText etZipCode;
    EditText etRate;
    TextView tvTimeStart;
    TextView tvTimeEnd;
    TextView tvDateStart;
    TextView tvDateEnd;
    Calendar mcurrentTime;
    Date startDate, endDate;
    String userID;
    String parkingID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_parking_spot);

        etStAddress = (EditText) findViewById(R.id.EditStreetAddress);
        etCity = (EditText) findViewById(R.id.EditCity);
        etState = (EditText) findViewById(R.id.EditState);
        etZipCode = (EditText) findViewById(R.id.EditZipCode);
        etRate = (EditText) findViewById(R.id.EditHourlyRate);
        tvTimeStart = (TextView) findViewById(R.id.EditStartTime);
        tvTimeEnd = (TextView)findViewById(R.id.EditEndTime);
        tvDateStart = (TextView) findViewById(R.id.EditStartDate);
        tvDateEnd = (TextView) findViewById(R.id.EditEndDate);
        bSubmit = (Button) findViewById(R.id.EditSubmitButton);

        mDB = FirebaseDatabase.getInstance().getReference("parkingspot");
        firebaseAuth = FirebaseAuth.getInstance();
        currentUser = firebaseAuth.getCurrentUser();

        tvTimeStart.setOnClickListener(this);
        tvTimeEnd.setOnClickListener(this);
        tvDateStart.setOnClickListener(this);
        tvDateEnd.setOnClickListener(this);
        Intent intent = getIntent();
        userID = intent.getStringExtra("userID");
        parkingID = intent.getStringExtra("parkingID");

        mDB.child(userID).child(parkingID).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                HashMap space = (HashMap)dataSnapshot.getValue();
                city = space.get("city").toString();
                stAddress = space.get("stAddress").toString();
                dateStart = space.get("startDate").toString();
                dateEnd = space.get("endDate").toString();
                timeStart = space.get("startTime").toString();
                timeEnd = space.get("endTime").toString();
                rate = space.get("rate").toString();
                zipCode = space.get("zip").toString();
                state = space.get("state").toString();

                etStAddress.setText(stAddress);
                etState.setText(state);
                etCity.setText(city);
                etRate.setText(rate);
                etZipCode.setText(zipCode);
            //    tvDateEnd.setText(dateEnd);
            //    tvDateStart.setText(dateStart);
            //    tvTimeStart.setText(timeStart);
            //    tvTimeEnd.setText(timeEnd);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
        bSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean editBool =editParkingSpot();
                if(editBool && editSpot()){
                    Intent EditPSIntent = new Intent(EditParkingSpotActivity.this, ParkingSpotActivity.class);
                    EditPSIntent.putExtra("pSpotID", parkingID);
                    EditPSIntent.putExtra("userID", userID);
                    startActivity(EditPSIntent);
                    finish();
                }
                else{

                }

            }
        });
    }

    private boolean editParkingSpot(){
        if(!checkField()){
            return false;
        }
        currentUser = firebaseAuth.getCurrentUser();
        editSpot();
        return true;
    }

    public boolean editSpot() {
        ParkingSpace parkingSpace = new ParkingSpace(stAddress, city, state,
                Integer.parseInt(zipCode), Double.parseDouble(rate),
                dateStart, dateEnd,timeStart,timeEnd);
        LatLng test = parkingSpace.getLocationFromAddress(this);
        if(test == null){
            Toast.makeText(this, "Address is not REAL",Toast.LENGTH_LONG).show();
            return false;
        }
        else {
            //mDB.child(userID).child(parkingID).setValue(parkingSpace);
            System.out.println(parkingSpace.getStartDate());
            mDB.child(userID).child(parkingID).child("counter").setValue(0);
            mDB.child(userID).child(parkingID).setValue(parkingSpace);
        }
        return true;
    }

    /**
     * Checks if Field entries are empty
     * @return true if filled out
     */
    public boolean checkField(){
        boolean clear = true;
        stAddress = etStAddress.getText().toString().trim();
        city = etCity.getText().toString().trim();
        state = etState.getText().toString().trim();
        zipCode =etZipCode.getText().toString().trim();
        rate = etRate.getText().toString().trim();
        dateStart = tvDateStart.getText().toString().trim();
        dateEnd = tvDateEnd.getText().toString().trim();
        timeStart = tvTimeStart.getText().toString().trim();
        timeEnd = tvTimeEnd.getText().toString().trim();
        if(TextUtils.isEmpty(stAddress)){
            clear = false;
            Toast.makeText(EditParkingSpotActivity.this, "Missing Street Address",Toast.LENGTH_LONG ).show();
            return clear;
        }
        if(TextUtils.isEmpty(city)){
            clear = false;
            Toast.makeText(EditParkingSpotActivity.this, "Missing City",Toast.LENGTH_LONG ).show();
            return clear;
        }
        if(TextUtils.isEmpty(state)){
            clear = false;
            Toast.makeText(EditParkingSpotActivity.this, "Missing State",Toast.LENGTH_LONG ).show();
            return clear;
        }
        if(TextUtils.isEmpty(zipCode)){
            clear = false;
            Toast.makeText(EditParkingSpotActivity.this, "Missing Zip Code",Toast.LENGTH_LONG ).show();
            return clear;
        }
        if(!zipCode.matches("\\d+")){
            clear = false;
            Toast.makeText(EditParkingSpotActivity.this, "Zip Code must be Numeric",Toast.LENGTH_LONG ).show();
            return clear;
        }
        if(TextUtils.isEmpty(rate)){
            clear = false;
            Toast.makeText(EditParkingSpotActivity.this, "Missing Hourly Rate",Toast.LENGTH_LONG ).show();
            return clear;
        }
        if(TextUtils.isEmpty(dateStart)){
            clear = false;
            Toast.makeText(EditParkingSpotActivity.this, "Missing Start Date",Toast.LENGTH_LONG ).show();
            return clear;
        }
        if(TextUtils.isEmpty(dateEnd)){
            clear = false;
            Toast.makeText(EditParkingSpotActivity.this, "Missing End Date",Toast.LENGTH_LONG ).show();
            return clear;
        }
        if(TextUtils.isEmpty(timeStart)){
            clear = false;
            Toast.makeText(EditParkingSpotActivity.this, "Missing Start Time",Toast.LENGTH_LONG ).show();
            return clear;
        }
        if(TextUtils.isEmpty(timeEnd)){
            clear = false;
            Toast.makeText(EditParkingSpotActivity.this, "Missing End Time",Toast.LENGTH_LONG ).show();
            return clear;
        }
        if(startDate.after(endDate)){
            clear = false;
            Toast.makeText(EditParkingSpotActivity.this, "End Time is before Start Time",Toast.LENGTH_LONG ).show();
            return clear;
        }

        return clear;
    }

    @Override
    public void onClick(View v) {
        mcurrentTime = Calendar.getInstance();
        int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
        int minute = mcurrentTime.get(Calendar.MINUTE);
        int day = mcurrentTime.get(Calendar.DAY_OF_MONTH);
        int month = mcurrentTime.get(Calendar.MONTH);
        int year = mcurrentTime.get(Calendar.YEAR);
        if(v == tvDateStart){
            DatePickerDialog mDatePicker;
            mDatePicker = new DatePickerDialog(EditParkingSpotActivity.this, new DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                    tvDateStart.setText((month+1)+"/"+dayOfMonth+"/"+year);
                    mcurrentTime.set(year, month, dayOfMonth, 0, 0);
                    startDate = mcurrentTime.getTime();
                }
            }, year, month,day);
            mDatePicker.getDatePicker().setMinDate(mcurrentTime.getTimeInMillis()-1000);
            mDatePicker.setTitle("Select Date");
            mDatePicker.show();
        }
        else if(v == tvDateEnd){
            DatePickerDialog mDatePicker;
            mDatePicker = new DatePickerDialog(EditParkingSpotActivity.this, new DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                    tvDateEnd.setText((month+1)+"/"+dayOfMonth+"/"+year);
                    mcurrentTime.set(year,month,dayOfMonth,0,1);
                    endDate = mcurrentTime.getTime();
                }

            }, year, month,day);
            mDatePicker.getDatePicker().setMinDate(mcurrentTime.getTimeInMillis()-1000);
            mDatePicker.setTitle("Select Date");
            mDatePicker.show();
        }
        else if(v == tvTimeStart){
            TimePickerDialog mTimePicker;
            mTimePicker = new TimePickerDialog(EditParkingSpotActivity.this, new TimePickerDialog.OnTimeSetListener() {
                @Override
                public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                    if(selectedHour<10 & selectedMinute<10){
                        tvTimeStart.setText("0"+selectedHour + ":" + "0"+selectedMinute);}
                    else if(selectedMinute<10){
                        tvTimeStart.setText(selectedHour + ":" + "0"+selectedMinute);}
                    else if(selectedHour<10){
                        tvTimeStart.setText("0"+selectedHour + ":"+selectedMinute);}
                    else{
                        tvTimeStart.setText(selectedHour + ":" + selectedMinute);}
                    startDate.setHours(selectedHour);
                    startDate.setMinutes(selectedMinute);
                }
            }, hour, minute, true);//Yes 24 hour time
            mTimePicker.setTitle("Select Time");
            mTimePicker.show();
        }
        else if(v == tvTimeEnd){
            TimePickerDialog mTimePicker;
            mTimePicker = new TimePickerDialog(EditParkingSpotActivity.this, new TimePickerDialog.OnTimeSetListener() {
                @Override
                public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                    if(selectedHour<10 & selectedMinute<10){
                        tvTimeEnd.setText("0"+selectedHour + ":" + "0"+selectedMinute);}
                    else if(selectedMinute<10){
                        tvTimeEnd.setText(selectedHour + ":" + "0"+selectedMinute);}
                    else if(selectedHour<10){
                        tvTimeEnd.setText("0"+selectedHour + ":"+selectedMinute);}
                    else{
                        tvTimeEnd.setText(selectedHour + ":" + selectedMinute);}
                    endDate.setHours(selectedHour);
                    endDate.setMinutes(selectedMinute);
                }
            }, hour, minute, true);//Yes 24 hour time
            mTimePicker.setTitle("Select Time");
            mTimePicker.show();
        }

    }

}