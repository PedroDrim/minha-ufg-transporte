package com.transporte.ufg.minha.minha_ufgtransporte.model;

import android.location.Location;

import com.google.android.gms.maps.model.LatLng;

/**
 * Created by pedro on 25/11/17.
 */

public class LocationTypesConverter {

    public static com.google.maps.model.LatLng locationToMapsLatLng(Location location){
        return(new com.google.maps.model.LatLng(location.getLatitude(), location.getLongitude()));
    }

    public static LatLng locationToAndroidLatLng(Location location){
        return(new LatLng(location.getLatitude(), location.getLongitude()));
    }

    public static LatLng MapsLatLngToAndroidLatLng(com.google.maps.model.LatLng latLng){
        return(new LatLng(latLng.lat, latLng.lng));
    }

    public static com.google.maps.model.LatLng AndroidLatLngToMapsLatLng(LatLng latLng){
        return(new com.google.maps.model.LatLng(latLng.latitude, latLng.longitude));
    }
}
