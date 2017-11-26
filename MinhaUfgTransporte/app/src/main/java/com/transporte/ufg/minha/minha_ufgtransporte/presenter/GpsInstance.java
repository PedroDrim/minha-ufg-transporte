package com.transporte.ufg.minha.minha_ufgtransporte.presenter;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;

import java.util.List;

import static android.content.Context.LOCATION_SERVICE;

/**
 * Created by pedro on 25/11/17.
 */

public class GpsInstance {

    public static final int PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION = 1;
    private Activity activity;
    private boolean enable;

    public GpsInstance(Activity activity){
        this.enable = false;
        this.activity = activity;
    }

    public void checkGPSPermission(){

        int gps = ContextCompat.checkSelfPermission(
                this.activity,
                Manifest.permission.READ_CONTACTS
        );

        if (gps != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(
                    activity,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION
            );

            this.enable = true;
        }
    }

    public Location getLastKnownLocation() {
        LocationManager mLocationManager =
                (LocationManager) this.activity.getApplicationContext().getSystemService(LOCATION_SERVICE);

        List<String> providers = mLocationManager.getProviders(true);
        Location bestLocation = null;

        for (String provider : providers) {

            @SuppressLint("MissingPermission")
            Location location = mLocationManager.getLastKnownLocation(provider);

            if (location == null) continue;
            if (bestLocation == null ||
                    (location.getAccuracy() < bestLocation.getAccuracy())) bestLocation = location;
        }
        return bestLocation;
    }

    public boolean isEnable(){
        return this.enable;
    }
}
