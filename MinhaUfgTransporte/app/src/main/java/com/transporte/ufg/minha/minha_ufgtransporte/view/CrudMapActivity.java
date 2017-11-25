package com.transporte.ufg.minha.minha_ufgtransporte.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.transporte.ufg.minha.minha_ufgtransporte.Manifest;
import com.transporte.ufg.minha.minha_ufgtransporte.R;
import com.transporte.ufg.minha.minha_ufgtransporte.model.MyPlace;
import com.transporte.ufg.minha.minha_ufgtransporte.presenter.GpsPresenter;

import org.greenrobot.eventbus.EventBus;

import static android.Manifest.permission.ACCESS_FINE_LOCATION;
import static android.Manifest.permission.READ_CONTACTS;
import static com.transporte.ufg.minha.minha_ufgtransporte.Manifest.*;
import static com.transporte.ufg.minha.minha_ufgtransporte.Manifest.permission.*;

public class CrudMapActivity extends AppCompatActivity implements OnMapReadyCallback {

    private GoogleMap mMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        MyPlace myPlace = EventBus.getDefault().getStickyEvent(MyPlace.class);

        if(myPlace != null)
            Log.d("MINHAUFG", "PushKey: " + myPlace.getPushKey());

        Log.d("MINHAUFG", "Flag: " + this.getIntent().getStringExtra("FLAG"));

        setContentView(R.layout.activity_crud_map);

        MapFragment mapFragment = (MapFragment) getFragmentManager()
                .findFragmentById(R.id.crud_map);

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
    @SuppressLint("MissingPermission")
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mMap.setMinZoomPreference(10.0f);

        GpsPresenter.enableGPSPermission(this);
        mMap.setMyLocationEnabled(true);
        mMap.getUiSettings().setMyLocationButtonEnabled(true);

        LatLng goiania = new LatLng(-16.665136, -49.286041);

        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(goiania, 15));
    }
}

