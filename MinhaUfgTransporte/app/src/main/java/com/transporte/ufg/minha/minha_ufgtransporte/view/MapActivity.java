package com.transporte.ufg.minha.minha_ufgtransporte.view;

import android.annotation.SuppressLint;
import android.location.Location;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.google.android.gms.location.places.ui.PlaceAutocompleteFragment;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.firebase.auth.FirebaseUser;
import com.transporte.ufg.minha.minha_ufgtransporte.R;
import com.transporte.ufg.minha.minha_ufgtransporte.presenter.OpenActivity;
import com.transporte.ufg.minha.minha_ufgtransporte.presenter.GpsInstance;
import com.transporte.ufg.minha.minha_ufgtransporte.service.FirebaseConfiguration;
import com.transporte.ufg.minha.minha_ufgtransporte.service.UfgPlaceSelectListener;


public class MapActivity extends AppCompatActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private GpsInstance gpsInstance;

    public MapActivity(){
        this.gpsInstance = new GpsInstance(this);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        this.gpsInstance.checkGPSPermission();

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    @SuppressLint("MissingPermission")
    @Override
    public void onMapReady(GoogleMap googleMap) {

        this.mMap = googleMap;
        this.mMap.setMinZoomPreference(10.0f);

        LatLng goiania;
        Location lastKnownLocation = this.gpsInstance.getLastKnownLocation();

        if(this.gpsInstance.isEnable()) {
            this.mMap.setMyLocationEnabled(true);
            this.mMap.getUiSettings().setMyLocationButtonEnabled(true);

            goiania = new LatLng(lastKnownLocation.getLatitude(), lastKnownLocation.getLongitude());
        } else {
            goiania = new LatLng(-16.665136, -49.286041);
        }

        this.mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(goiania, 15));

        this.initializePlacesAutoComplete(lastKnownLocation);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_locais, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.crud_locais:

                FirebaseUser user = FirebaseConfiguration.getFirebaseAuth().getCurrentUser();

                if(user == null) {
                    OpenActivity.openLoginActivity(this);
                } else {
                    OpenActivity.openPlaceListActivity(this);
                }
                return (true);
        }

        return (super.onOptionsItemSelected(item));
    }

    private void initializePlacesAutoComplete(Location lastKnownLocation) {

        PlaceAutocompleteFragment autocompleteFragment = (PlaceAutocompleteFragment)
                getFragmentManager().findFragmentById(R.id.place_autocomplete_fragment);

        LatLng start = new LatLng(-16.810330, -49.375050);
        LatLng end = new LatLng(-16.580921, -49.164289);

        autocompleteFragment.setBoundsBias(new LatLngBounds(start, end));
        autocompleteFragment.setOnPlaceSelectedListener(
                new UfgPlaceSelectListener(this.mMap, lastKnownLocation, this)
        );
    }
}

