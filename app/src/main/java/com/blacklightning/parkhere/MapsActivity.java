package com.blacklightning.parkhere;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback{
    private static final float ZOOM_DELTA = 2.0f;
    private static final float DEFAULT_MIN_ZOOM = 2.0f;
    private static final float DEFAULT_MAX_ZOOM = 22.0f;
    private static final float DEFAULT_CURRENT_ZOOM = 10.0f;
    private GoogleMap mMap;
    private float mMinZoom;
    private float mMaxZoom;
    private float currentZoom;

    // default lat and long is for Sydney, Australia
    //TODO: set the default location to user's current location
    private double currentLatitude = -34;
    private double currentLongitute = 151;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        resetMinMaxZoom();

        /**
         * TODO: request for user location
         * TODO: set markers for locations
         * TODO: use latLng instead of the currentLat and currentLong variables
         */


        //sets a bunch of UI settings to true
        mMap.getUiSettings().setZoomControlsEnabled(true);
        mMap.getUiSettings().setScrollGesturesEnabled(true);
        mMap.getUiSettings().setMyLocationButtonEnabled(true);

        // Add a marker in Sydney and move the camera
        LatLng sydney = new LatLng(currentLatitude, currentLongitute);
        mMap.addMarker(new MarkerOptions().position(sydney).title("Current Location"));
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(sydney, currentZoom));
    }

    private void toast(String msg) {
        Toast.makeText(getBaseContext(), msg, Toast.LENGTH_SHORT).show();
    }

    //Checks if the map is ready or not
    private boolean checkReady() {
        if (mMap == null) {
            Toast.makeText(this, "Map is not ready", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    // Resets max and min zoom preferences
    private void resetMinMaxZoom() {
        mMinZoom = DEFAULT_MIN_ZOOM;
        mMaxZoom = DEFAULT_MAX_ZOOM;
    }

    //Sets min zoom preference
    public void onSetMinZoomClamp(View view) {
        if (!checkReady()) {
            return;
        }
        mMinZoom += ZOOM_DELTA;
        // Constrains the minimum zoom level.
        mMap.setMinZoomPreference(mMinZoom);
        toast("Min zoom preference set to: " + mMinZoom);
    }

    //Sets max zoom preference
    public void onSetMaxZoomClamp(View view) {
        if (!checkReady()) {
            return;
        }
        mMaxZoom -= ZOOM_DELTA;
        // Constrains the maximum zoom level.
        mMap.setMaxZoomPreference(mMaxZoom);
        toast("Max zoom preference set to: " + mMaxZoom);
    }

    public void setCurrentZoomOut(View view){
        if(!checkReady()){
            return;
        }
        if((currentZoom += ZOOM_DELTA)<= mMaxZoom) {
            currentZoom += ZOOM_DELTA;
        }
    }

    public void setCurrentZoomIn(View view){
        if(!checkReady()){
            return;
        }
        if((currentZoom -= ZOOM_DELTA) >= mMinZoom){
            currentZoom -= ZOOM_DELTA;
        }

    }

    public void resetCurrenZoom(View view){
        if(!checkReady()){
            return;
        }
        currentZoom = DEFAULT_CURRENT_ZOOM;
    }
    //Resets max and min zoom preferences
    public void onMinMaxZoomClampReset(View view) {
        if (!checkReady()) {
            return;
        }
        resetMinMaxZoom();
        mMap.resetMinMaxZoomPreference();
        toast("Min/Max zoom preferences reset.");
    }


    //sets current lat and long and moves to that locations
    public void setCurrentLatLng(double lat, double longi){
        currentLatitude = lat;
        currentLongitute = longi;
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(currentLatitude, currentLongitute), currentZoom));
    }

    //adds marker for parking space
    public void getLatLngParking(ParkingSpace space){
        LatLng newLatLng= space.getLocationFromAddress(this);
        mMap.addMarker(new MarkerOptions().position(newLatLng).title(space.getStAddress()));
    }
}
