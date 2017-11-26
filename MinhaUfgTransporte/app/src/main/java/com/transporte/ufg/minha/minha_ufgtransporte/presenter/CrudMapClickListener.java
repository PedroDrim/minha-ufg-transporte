package com.transporte.ufg.minha.minha_ufgtransporte.presenter;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

/**
 * Created by pedro on 26/11/17.
 */

public class CrudMapClickListener implements GoogleMap.OnMapClickListener {

    private LatLng latLng;
    private GoogleMap mMap;

    @Override
    public void onMapClick(LatLng latLng) {
        this.latLng = latLng;
        this.mMap.clear();
        this.mMap.addMarker(new MarkerOptions().position(latLng));
    }

    public void setGoogleMap(GoogleMap mMap){
        this.mMap = mMap;
    }

    public LatLng getClickPosition(){
        return this.latLng;
    }
}
