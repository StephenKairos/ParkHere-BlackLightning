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


import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.sql.Time;
import java.util.Calendar;

public class CreateParkingSpot extends AppCompatActivity implements View.OnClickListener {

    private FirebaseAuth firebaseAuth;
    private FirebaseAuth.AuthStateListener authListener;
    private DatabaseReference fBase;
    private FirebaseUser currentUser;

    private String currentSpotID;
    Button bCreate;
    EditText etStAddress;
    EditText etCity;
    EditText etState;
    EditText etZipCode;
    EditText etRate;
    TextView tvTimeStart;
    TextView tvTimeEnd;
    TextView tvDateStart;
    TextView tvDateEnd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_parking_spot);

        etStAddress = (EditText) findViewById(R.id.StreetAddress);
        etCity = (EditText) findViewById(R.id.City);
        etState = (EditText) findViewById(R.id.State);
        etZipCode = (EditText) findViewById(R.id.ZipCode);
        etRate = (EditText) findViewById(R.id.HourlyRate);
        tvTimeStart = (TextView) findViewById(R.id.startTime);
        tvTimeEnd = (TextView)findViewById(R.id.endTime);
        tvDateStart = (TextView) findViewById(R.id.startDate);
        tvDateEnd = (TextView) findViewById(R.id.endDate);
        bCreate = (Button) findViewById(R.id.CreateParkingButton);

        currentSpotID="";
        fBase = FirebaseDatabase.getInstance().getReference();
        firebaseAuth = FirebaseAuth.getInstance();
        currentUser = firebaseAuth.getCurrentUser();
        tvTimeStart.setOnClickListener(this);
        tvTimeEnd.setOnClickListener(this);
        tvDateStart.setOnClickListener(this);
        tvDateEnd.setOnClickListener(this);

        bCreate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createParkingSpot();
                Intent CreatePSIntent = new Intent(CreateParkingSpot.this, ParkingSpotActivity.class);
                CreatePSIntent.putExtra("pSpotID", currentSpotID);

                startActivity(CreatePSIntent);
                finish();
            }
        });


    }

    private void createParkingSpot(){
        if(!checkField()){
            return;
        }
        currentUser = firebaseAuth.getCurrentUser();
        createSpot();

    }

    public void createSpot() {
        String stAddress = etStAddress.getText().toString().trim();
        String City = etCity.getText().toString().trim();
        String State = etState.getText().toString().trim();
        int ZipCode = Integer.parseInt(etZipCode.getText().toString().trim());
        double rate = Double.parseDouble(etRate.getText().toString().trim());
        String startDate = tvDateStart.getText().toString().trim();
        String endDate = tvDateEnd.getText().toString().trim();
        String startTime = tvTimeStart.getText().toString().trim();
        String endTime = tvTimeEnd.getText().toString().trim();


        ParkingSpace parkingSpace = new ParkingSpace(stAddress, City, State, ZipCode, rate,
                startDate, endDate,startTime,endTime);
        String parkingID = parkingSpace.getId();
        fBase.child("parkingspot").child(currentUser.getUid()).child(parkingID).setValue(parkingSpace);
        currentSpotID=parkingID;

    }

    /**
     * Checks if Field entries are empty
     * @return true if filled out
     */
    private boolean checkField(){
        boolean clear = true;
        String stAddress = etStAddress.getText().toString().trim();
        String City = etCity.getText().toString().trim();
        String State = etState.getText().toString().trim();
        String ZipCode =etZipCode.getText().toString().trim();
        String Rate = etRate.getText().toString().trim();
        String StartDate = tvDateStart.getText().toString().trim();
        String EndDate = tvDateEnd.getText().toString().trim();
        String StartTime = tvTimeStart.getText().toString().trim();
        String EndTime = tvTimeEnd.getText().toString().trim();

        if(TextUtils.isEmpty(stAddress)){
            clear = false;
            Toast.makeText(CreateParkingSpot.this, "Missing Street Address",Toast.LENGTH_LONG ).show();
            return clear;
        }
        if(TextUtils.isEmpty(City)){
            clear = false;
            Toast.makeText(CreateParkingSpot.this, "Missing City",Toast.LENGTH_LONG ).show();
            return clear;
        }
        if(TextUtils.isEmpty(State)){
            clear = false;
            Toast.makeText(CreateParkingSpot.this, "Missing State",Toast.LENGTH_LONG ).show();
            return clear;
        }
        if(TextUtils.isEmpty(ZipCode)){
            clear = false;
            Toast.makeText(CreateParkingSpot.this, "Missing Zip Code",Toast.LENGTH_LONG ).show();
            return clear;
        }
        if(TextUtils.isEmpty(Rate)){
            clear = false;
            Toast.makeText(CreateParkingSpot.this, "Missing Hourly Rate",Toast.LENGTH_LONG ).show();
            return clear;
        }
        if(TextUtils.isEmpty(StartDate)){
            clear = false;
            Toast.makeText(CreateParkingSpot.this, "Missing Start Date",Toast.LENGTH_LONG ).show();
            return clear;
        }
        if(TextUtils.isEmpty(EndDate)){
            clear = false;
            Toast.makeText(CreateParkingSpot.this, "Missing End Date",Toast.LENGTH_LONG ).show();
            return clear;
        }
        if(TextUtils.isEmpty(StartTime)){
            clear = false;
            Toast.makeText(CreateParkingSpot.this, "Missing Start Time",Toast.LENGTH_LONG ).show();
            return clear;
        }
        if(TextUtils.isEmpty(EndTime)){
            clear = false;
            Toast.makeText(CreateParkingSpot.this, "Missing End Time",Toast.LENGTH_LONG ).show();
            return clear;
        }
        return clear;
    }

    @Override
    public void onClick(View v) {
        final Calendar mcurrentTime = Calendar.getInstance();
        int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
        int minute = mcurrentTime.get(Calendar.MINUTE);
        int day = mcurrentTime.get(Calendar.DAY_OF_MONTH);
        int month = mcurrentTime.get(Calendar.MONTH);
        int year = mcurrentTime.get(Calendar.YEAR);
        if(v == tvDateStart){
            DatePickerDialog mDatePicker;
            mDatePicker = new DatePickerDialog(CreateParkingSpot.this, new DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                    tvDateStart.setText((month+1)+"/"+dayOfMonth+"/"+year);
                }
            }, year, month,day);
            mDatePicker.getDatePicker().setMinDate(mcurrentTime.getTimeInMillis()-1000);
            mDatePicker.setTitle("Select Date");
            mDatePicker.show();
        }
        else if(v == tvDateEnd){
            DatePickerDialog mDatePicker;
            mDatePicker = new DatePickerDialog(CreateParkingSpot.this, new DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                    tvDateEnd.setText((month+1)+"/"+dayOfMonth+"/"+year);
                }

            }, year, month,day);
            mDatePicker.getDatePicker().setMinDate(mcurrentTime.getTimeInMillis()-1000);
            mDatePicker.setTitle("Select Date");
            mDatePicker.show();
        }
        else if(v == tvTimeStart){
            TimePickerDialog mTimePicker;
            mTimePicker = new TimePickerDialog(CreateParkingSpot.this, new TimePickerDialog.OnTimeSetListener() {
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
                }
            }, hour, minute, true);//Yes 24 hour time
            mTimePicker.setTitle("Select Time");
            mTimePicker.show();
        }
        else if(v == tvTimeEnd){
            TimePickerDialog mTimePicker;
            mTimePicker = new TimePickerDialog(CreateParkingSpot.this, new TimePickerDialog.OnTimeSetListener() {
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
                }
            }, hour, minute, true);//Yes 24 hour time
            mTimePicker.setTitle("Select Time");
            mTimePicker.show();
        }

    }
}
