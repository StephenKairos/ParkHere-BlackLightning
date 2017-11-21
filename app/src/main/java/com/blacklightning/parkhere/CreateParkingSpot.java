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
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.sql.Time;
import java.util.Calendar;
import java.util.Date;

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
    Calendar mcurrentTime;
    Date startDate, endDate;

    public CreateParkingSpot(){

    }
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
                boolean createdBool =createParkingSpot();
                if(createdBool && createSpot()){
                    Intent CreatePSIntent = new Intent(CreateParkingSpot.this, ParkingSpotActivity.class);
                    CreatePSIntent.putExtra("pSpotID", currentSpotID);
                    CreatePSIntent.putExtra("userID", currentUser.getUid());
                    startActivity(CreatePSIntent);
                    finish();
                }
                else{

                }
            }
        });
    }

    private boolean createParkingSpot(){
        if(!checkField()){
            return false;
        }
        currentUser = firebaseAuth.getCurrentUser();
        createSpot();
        return true;
    }
    public Date getStartDate(){
        return startDate;
    }
    public Date getEndDate(){
        return endDate;
    }
    public boolean createSpot() {
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
        LatLng test = parkingSpace.getLocationFromAddress(this);
        if(test == null){
            Toast.makeText(this, "Address is not REAL",Toast.LENGTH_LONG).show();
            return false;
        }
        else {
            String parkingID = parkingSpace.getId();
            fBase.child("parkingspot").child(currentUser.getUid()).child(parkingID).setValue(parkingSpace);
            currentSpotID=parkingID;
        }
        return true;
    }

    /**
     * Checks if Field entries are empty
     * @return true if filled out
     */
    public boolean checkField(){
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
        if(!ZipCode.matches("\\d+")){
            clear = false;
            Toast.makeText(CreateParkingSpot.this, "Zip Code must be Numeric",Toast.LENGTH_LONG ).show();
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
        if(startDate.after(endDate)){
            clear = false;
            Toast.makeText(CreateParkingSpot.this, "End Time is before Start Time",Toast.LENGTH_LONG ).show();
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
            mDatePicker = new DatePickerDialog(CreateParkingSpot.this, new DatePickerDialog.OnDateSetListener() {
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
            mDatePicker = new DatePickerDialog(CreateParkingSpot.this, new DatePickerDialog.OnDateSetListener() {
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
                    startDate.setHours(selectedHour);
                    startDate.setMinutes(selectedMinute);
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
                    endDate.setHours(selectedHour);
                    endDate.setMinutes(selectedMinute);
                }
            }, hour, minute, true);//Yes 24 hour time
            mTimePicker.setTitle("Select Time");
            mTimePicker.show();
        }

    }
    public void setEtStAddress(String eta){
        etStAddress.setText(eta);
    }
    public void setEtCity(String etc){
        etCity.setText(etc);
    }
    public void setEtState(String ets){
        etState.setText(ets);
    }
    public void setEtZipCode(String etz){
        etZipCode.setText(etz);
    }
    public void setEtRate(String etr){
        etZipCode.setText(etr);
    }
    public void setTvTimeStart(String tvts){
        etZipCode.setText(tvts);
    }
    public void setTvTimeEnd(String tvte){
        etZipCode.setText(tvte);
    }
    public void setTvDateStart(String tvds){
        etZipCode.setText(tvds);
    }
    public void setTvDateEnd(String tvde){
        etZipCode.setText(tvde);
    }
    public void setCalendarStartDate(int year, int month, int day){
        mcurrentTime = Calendar.getInstance();
        mcurrentTime.set(year,month,day,0,0);
    }
    public boolean checkCalendarDate(Date startdate, Date enddate){
        return startdate.before(enddate);
    }

}
