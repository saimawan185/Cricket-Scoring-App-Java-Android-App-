package com.example.cricketscoringapp;

import android.app.AlertDialog;
import android.app.Service;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;

import android.os.Bundle;
import android.os.IBinder;
import android.provider.Settings;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;


public class TrackGPS extends Service implements LocationListener {

    private final Context ctxt; // reference to current Activity
    boolean checkGPS = false; // check if GPS is available

    Location mylocation; // variable to store current location
    protected LocationManager locationManager;
    double latitude,longitude, altitude;

    private static final long MINDELAY = 1000 * 60; // minimum time between updates
    private static final long MINDISTANCE = 10; // minimum distance between updates

    public TrackGPS(Context ctxt){
        this.ctxt = ctxt;
        getLocation();
    }

    private Location getLocation(){
        try{
            locationManager = (LocationManager) ctxt.getSystemService(LOCATION_SERVICE);
            // get GPS status
            checkGPS = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
            if (checkGPS){
                try {
                    locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, MINDELAY, MINDISTANCE, this);
                    if (locationManager != null){
                        mylocation = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
                        if (mylocation != null){
                            latitude = mylocation.getLatitude();
                            longitude = mylocation.getLongitude();
                            altitude = mylocation.getAltitude();
                        }
                    }
                } catch (SecurityException e) {
                    Toast.makeText(ctxt, "No permission to access GPS", Toast.LENGTH_SHORT).show();
                }
            }
            else {
                Toast.makeText(ctxt, "No service provider available", Toast.LENGTH_SHORT).show();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return mylocation;
    }

    public double getLatitude(){
        if (mylocation != null) return mylocation.getLatitude();
        return latitude;
    }

    public double getLongitude(){
        if (mylocation != null) return mylocation.getLongitude();
        return longitude;
    }

    public double getAltitude(){
        if (mylocation != null) return mylocation.getAltitude();
        return altitude;
    }

    public boolean canGetLocation(){
        return this.checkGPS;
    }

    public void showAlert() {
        AlertDialog.Builder dialog = new AlertDialog.Builder(ctxt);
        dialog.setTitle("GPS disabled");
        dialog.setMessage("Do you want to turn on GPS?");
        dialog.setPositiveButton("YES", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                ctxt.startActivity(intent);
            }
        });
        dialog.setNegativeButton("NO",new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        dialog.show();
    }

    //Stops the use of GPS
    public void stopGPS() {
        if (locationManager != null) {
            try {
                locationManager.removeUpdates(TrackGPS.this);
            } catch (SecurityException e) {
                Toast.makeText(ctxt, "No permission to access GPS", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onLocationChanged(@NonNull Location location) {

    }
    public void onStatusChanged(String provider, int status, Bundle extras){

    }

}