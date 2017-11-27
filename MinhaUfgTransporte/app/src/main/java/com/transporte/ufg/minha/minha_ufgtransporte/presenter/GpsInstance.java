package com.transporte.ufg.minha.minha_ufgtransporte.presenter;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;

import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;

import com.transporte.ufg.minha.minha_ufgtransporte.view.MapActivity;

/**
 * Created by pedro on 25/11/17.
 */

public class GpsInstance extends AppCompatActivity{

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
                Manifest.permission.ACCESS_FINE_LOCATION
        );

        if (gps != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(activity,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION);
        } else {
            this.enable = true;
        }
    }

    public boolean isEnable(){
        return this.enable;
    }


    @Override
    public void onRequestPermissionsResult(
            int requestCode, String permissions[], int[] grantResults) {

        switch (requestCode) {
            case PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION: {
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    this.enable = true;

                    MapActivity map = (MapActivity) activity;
                    map.getDeviceLocation();
                } else {
                    this.enable = false;
                }

                return;
            }
        }
    }
}
