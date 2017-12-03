package com.transporte.ufg.minha.minha_ufgtransporte.view;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.places.ui.PlaceAutocompleteFragment;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseUser;
import com.transporte.ufg.minha.minha_ufgtransporte.R;
import com.transporte.ufg.minha.minha_ufgtransporte.model.LocationTypesConverter;
import com.transporte.ufg.minha.minha_ufgtransporte.presenter.OpenActivity;
import com.transporte.ufg.minha.minha_ufgtransporte.presenter.GpsInstance;
import com.transporte.ufg.minha.minha_ufgtransporte.service.FirebaseConfiguration;
import com.transporte.ufg.minha.minha_ufgtransporte.service.UfgPlaceSelectListener;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.w3c.dom.Text;


public class MapActivity extends AppCompatActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private GpsInstance gpsInstance;
    CardView cardView;
    TextView busLineView;
    TextView travelTimeView;
    LayoutInflater inflater;
    public LinearLayout routeDetailsView;

    public MapActivity(){
        this.gpsInstance = new GpsInstance(this);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTitle("Minha-UFG Transporte");
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_map);

        inflater = LayoutInflater.from(this);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        this.gpsInstance.checkGPSPermission();

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        routeDetailsView = findViewById(R.id.route_details);

        busLineView = findViewById(R.id.bus_line);
        travelTimeView = findViewById(R.id.travel_time);
        cardView = findViewById(R.id.route_information);
        cardView.setVisibility(View.GONE);

    }


    @Override
    public void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    public void onStop() {
        EventBus.getDefault().unregister(this);
        super.onStop();
    }

    @SuppressLint("MissingPermission")
    @Override
    public void onMapReady(GoogleMap googleMap) {

        this.mMap = googleMap;
        this.mMap.setMinZoomPreference(10.0f);

        if(this.gpsInstance.isEnable()) {
            this.mMap.setMyLocationEnabled(true);
            this.mMap.getUiSettings().setMyLocationButtonEnabled(true);
        }
        else {
            LatLng goiania = new LatLng(-16.665136, -49.286041);
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(goiania, 15));
        }

        getDeviceLocation();
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
                OpenActivity.openPlaceListActivity(this);
                return (true);
        }

        return (super.onOptionsItemSelected(item));
    }

    @Subscribe
    public void initializePlacesAutoComplete(Location lastKnownLocation) {

        PlaceAutocompleteFragment autocompleteFragment = (PlaceAutocompleteFragment)
                getFragmentManager().findFragmentById(R.id.place_autocomplete_fragment);

        LatLng start = new LatLng(-16.810330, -49.375050);
        LatLng end = new LatLng(-16.580921, -49.164289);

        autocompleteFragment.setBoundsBias(new LatLngBounds(start, end));
        autocompleteFragment.setOnPlaceSelectedListener(
                new UfgPlaceSelectListener(this.mMap, lastKnownLocation, this, this)
        );
    }

    public void getDeviceLocation() {
    /*
     * Get the best and most recent location of the device, which may be null in rare
     * cases when a location is not available.
     */

        FusedLocationProviderClient mFusedLocationProviderClient =
                new FusedLocationProviderClient(this);

        try {

            if (this.gpsInstance.isEnable()) {

                Task locationResult = mFusedLocationProviderClient.getLastLocation();
                locationResult.addOnCompleteListener(this, new OnCompleteListener() {

                    @Override
                    public void onComplete(@NonNull Task task) {

                        if (task.isSuccessful()) {
                            // Set the map's camera position to the current location of the device.
                            Location mLastKnownLocation = (Location) task.getResult();
                            EventBus.getDefault().post(mLastKnownLocation);

                            LatLng latlng = LocationTypesConverter
                                    .locationToAndroidLatLng(mLastKnownLocation);

                            Log.i("-->User location lat", String.valueOf(latlng.latitude));
                            Log.i("-->User location lng", String.valueOf(latlng.longitude));

                            mMap.moveCamera(
                                    CameraUpdateFactory.newLatLngZoom(latlng, 15));
                        } else {
                            LatLng goiania = new LatLng(-16.665136, -49.286041);
                            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(goiania, 15));
                            mMap.getUiSettings().setMyLocationButtonEnabled(false);
                        }

                    }

                });
            }

        } catch(SecurityException e)  {
            Log.e("Exception: %s", e.getMessage());
        }
    }

    public void makeCardVisible (String travelTime) {

//        addWalkDetail(firstWalk);
//
//        addArrow();
//
//        addBusDetail(busLine);
//
//        addArrow();
//
//        addWalkDetail(secondWalk);

        travelTimeView.setText(travelTime);

        cardView.setVisibility(View.VISIBLE);
    }

    public void addBusDetail(String busLine, int busCounter) {
        View busDetail = inflater.inflate(R.layout.bus_content, null, false);
        if (busCounter < 3) {
            TextView busLineView = busDetail.findViewById(R.id.bus_line);
            busLineView.setText(busLine);
        }
        routeDetailsView.addView(busDetail);
    }

    public void addWalkDetail(String walkMin) {
        View walkDetail = inflater.inflate(R.layout.walk_content, null, false);
        TextView walkMinView = walkDetail.findViewById(R.id.walk_min);
        walkMinView.setText(walkMin);
        routeDetailsView.addView(walkDetail);
    }

    public void addArrow() {
        View arrow = inflater.inflate(R.layout.arrow_right, null, false);
        routeDetailsView.addView(arrow);
    }
}

