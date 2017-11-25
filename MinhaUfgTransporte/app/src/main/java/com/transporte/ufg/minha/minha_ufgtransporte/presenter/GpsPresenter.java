package com.transporte.ufg.minha.minha_ufgtransporte.presenter;

import android.app.Activity;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;

import static android.Manifest.permission.ACCESS_FINE_LOCATION;
import static android.Manifest.permission.READ_CONTACTS;

/**
 * Created by pedro on 24/11/17.
 */

public class GpsPresenter {

    public static void checkGPSPermission(Activity activity){
        int permission = ContextCompat.checkSelfPermission(activity, READ_CONTACTS);
        int PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION = 1;

        if ( permission != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(
                    activity,
                    new String[]{ACCESS_FINE_LOCATION},
                    PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION
            );
        }
    }

    public static void enableGPSPermission(Activity activity){

        boolean fineLocation =
                ActivityCompat.checkSelfPermission(activity, ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED;
        boolean coarseLocation =
                ActivityCompat.checkSelfPermission(activity, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED;

        if ( fineLocation && coarseLocation) {
            return;
        }
    }

}
