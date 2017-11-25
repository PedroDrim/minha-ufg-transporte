package com.transporte.ufg.minha.minha_ufgtransporte.view;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlaceAutocompleteFragment;
import com.google.android.gms.location.places.ui.PlaceSelectionListener;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.maps.DirectionsApi;
import com.google.maps.GeoApiContext;
import com.google.maps.android.PolyUtil;
import com.google.maps.errors.ApiException;
import com.google.maps.model.DirectionsResult;
import com.google.maps.model.TransitMode;
import com.google.maps.model.TravelMode;
import com.transporte.ufg.minha.minha_ufgtransporte.R;

import org.joda.time.DateTime;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class MapActivity extends AppCompatActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private final int PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION = 1;
    private boolean gpsPermission;
    private Location lastKnownLocation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        checkGPSPermission();

        initializePlacesAutoComplete();

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
                this.abrirActivityMeusLocais();
                return (true);
        }

        return (super.onOptionsItemSelected(item));
    }

    private void abrirActivityMeusLocais() {
        Intent intentAbrirTelaPrincipal = new Intent(
                MapActivity.this,
                PlaceListActivity.class
        );
        startActivity(intentAbrirTelaPrincipal);
    }

    private void initializePlacesAutoComplete() {
        PlaceAutocompleteFragment autocompleteFragment = (PlaceAutocompleteFragment)
                getFragmentManager().findFragmentById(R.id.place_autocomplete_fragment);

        autocompleteFragment.setBoundsBias(new LatLngBounds(
                new LatLng(-16.810330, -49.375050),
                new LatLng(-16.580921, -49.164289)));

        autocompleteFragment.setOnPlaceSelectedListener(new PlaceSelectionListener() {
            @Override
            public void onPlaceSelected(Place place) {
                LatLng destination = place.getLatLng();
                mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(destination, 10), 2000, null);
//              Log.i("place_id", place.getId());

                requestRoutes(destination.latitude, destination.longitude);
            }

            @Override
            public void onError(Status status) {
                // TODO: Solucionar o erro.
                Log.i("ERRO NO MAPS", "Ocorreu um erro: " + status);
            }
        });
    }

    private GeoApiContext getGeoContext() {
        GeoApiContext geoApiContext = new GeoApiContext();
        return geoApiContext.setQueryRateLimit(3)
                .setApiKey(getString(R.string.google_directions_key))
                .setConnectTimeout(5, TimeUnit.SECONDS)
                .setReadTimeout(5, TimeUnit.SECONDS)
                .setWriteTimeout(5, TimeUnit.SECONDS);
    }

    private void requestRoutes(double destinationLat, double destinationLng) {
        com.google.maps.model.LatLng origin = new com.google.maps.model.LatLng(lastKnownLocation.getLatitude(), lastKnownLocation.getLongitude());
        com.google.maps.model.LatLng destinationDir = new com.google.maps.model.LatLng(destinationLat, destinationLng);

        DateTime now = new DateTime();
        try {
            DirectionsResult result = DirectionsApi.newRequest(getGeoContext())
                    .mode(TravelMode.TRANSIT)
                    .origin(origin)
                    .destination(destinationDir)
                    .departureTime(now)
                    .alternatives(true)
                    .transitMode(TransitMode.BUS)
                    .await();

            addMarkersToMap(result, mMap);
            addPolyline(result, mMap);

        } catch (ApiException | InterruptedException e) {
            Toast.makeText(
                    this,
                    R.string.internet_route_error,
                    Toast.LENGTH_LONG
            ).show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private String getEndLocationTitle(DirectionsResult results){
        return  "Time :"+ results.routes[0].legs[0].duration.humanReadable + "\n Distance :" + results.routes[0].legs[0].distance.humanReadable;
    }

    private void addMarkersToMap(DirectionsResult results, GoogleMap mMap) {
        int routesNum = results.routes.length;
        for(int i = 0; i < routesNum; i++) {
            mMap.addMarker(new MarkerOptions().position(new LatLng(results.routes[0].legs[0].startLocation.lat,results.routes[0].legs[0].startLocation.lng)).title(results.routes[0].legs[0].startAddress));
            mMap.addMarker(new MarkerOptions().position(new LatLng(results.routes[0].legs[0].endLocation.lat,results.routes[0].legs[0].endLocation.lng)).title(results.routes[0].legs[0].startAddress).snippet(getEndLocationTitle(results)));
        }
    }

    private void addPolyline(DirectionsResult results, GoogleMap mMap) {
        int routesNum = results.routes.length;
        String colors[] = this.getResources().getStringArray(R.array.colors);
        for(int i = 0; i < routesNum; i++) {
            List<LatLng> decodedPath = PolyUtil.decode(results.routes[i].overviewPolyline.getEncodedPath());
            mMap.addPolyline(new PolylineOptions().addAll(decodedPath).color(Color.parseColor(colors[i]))).setZIndex(i);
        }
    }

    private void checkGPSPermission(){
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.READ_CONTACTS)
                != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION);

        }
    }

    @SuppressLint("MissingPermission")
    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    gpsPermission = true;

                    lastKnownLocation = getLastKnownLocation();
                    Log.i("LAST-KNOWN-LOCATION", "" + lastKnownLocation);

                    SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                            .findFragmentById(R.id.map);
                    mapFragment.getMapAsync(this);

                } else {
                    gpsPermission = false;
                }

            }
        }
    }

    private Location getLastKnownLocation() {
        LocationManager mLocationManager = (LocationManager) getApplicationContext().getSystemService(LOCATION_SERVICE);
        List<String> providers = mLocationManager.getProviders(true);
        Location bestLocation = null;
        for (String provider : providers) {
            @SuppressLint("MissingPermission") Location l = mLocationManager.getLastKnownLocation(provider);
            if (l == null) {
                continue;
            }
            if (bestLocation == null || l.getAccuracy() < bestLocation.getAccuracy()) {
                // Found best last known location: %s", l);
                bestLocation = l;
            }
        }
        return bestLocation;
    }

    @SuppressLint("MissingPermission")
    @Override
    public void onMapReady(GoogleMap googleMap) {
        LatLng goiania;
        mMap = googleMap;
        mMap.setMinZoomPreference(10.0f);

        if(gpsPermission) {
            mMap.setMyLocationEnabled(true);
            mMap.getUiSettings().setMyLocationButtonEnabled(true);

            goiania = new LatLng(lastKnownLocation.getLatitude(), lastKnownLocation.getLongitude());
        }

        else {
            goiania = new LatLng(-16.665136, -49.286041);
        }
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(goiania, 15));

    }

}

