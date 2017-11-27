package com.transporte.ufg.minha.minha_ufgtransporte.service;

import android.content.Context;
import android.graphics.Color;
import android.location.Location;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlaceSelectionListener;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.maps.DirectionsApi;
import com.google.maps.GeoApiContext;
import com.google.maps.android.PolyUtil;
import com.google.maps.errors.ApiException;
import com.google.maps.model.DirectionsLeg;
import com.google.maps.model.DirectionsResult;
import com.google.maps.model.TransitMode;
import com.google.maps.model.TravelMode;
import com.transporte.ufg.minha.minha_ufgtransporte.R;
import com.transporte.ufg.minha.minha_ufgtransporte.model.LocationTypesConverter;

import org.joda.time.DateTime;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * Created by pedro on 25/11/17.
 */

public class UfgPlaceSelectListener implements PlaceSelectionListener {

    private GoogleMap mMap;
    private Context context;
    private Location lastKnownLocation;

    public UfgPlaceSelectListener(GoogleMap mMap, Location lastKnownLocation, Context context) {
        this.mMap = mMap;
        this.context = context;
        this.lastKnownLocation = lastKnownLocation;
    }

    @Override
    public void onPlaceSelected(Place place) {
        LatLng destination = place.getLatLng();
        this.mMap.animateCamera(
                CameraUpdateFactory.newLatLngZoom(destination, 10),
                2000,
                null
        );

        this.requestRoutes(destination);
    }

    @Override
    public void onError(Status status) {
        // TODO: Solucionar o erro.
        Log.i("ERRO NO MAPS", "Ocorreu um erro: " + status);
    }

    private void requestRoutes(LatLng destination) {

        DateTime now = new DateTime();
        try {

            DirectionsResult result = DirectionsApi.newRequest(getGeoContext())
                    .mode(TravelMode.TRANSIT)
                    .origin(
                            LocationTypesConverter.locationToMapsLatLng(this.lastKnownLocation)
                    )
                    .destination(
                            LocationTypesConverter.AndroidLatLngToMapsLatLng(destination)
                    )
                    .departureTime(now)
                    .alternatives(true)
                    .transitMode(TransitMode.BUS)
                    .await();

            addMarkersToMap(result);
            addPolyline(result);

        } catch (ApiException | InterruptedException e) {
            Toast.makeText(
                    this.context,
                    R.string.internet_route_error,
                    Toast.LENGTH_LONG
            ).show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private GeoApiContext getGeoContext() {
        GeoApiContext geoApiContext = new GeoApiContext();
        return geoApiContext.setQueryRateLimit(3)
                .setApiKey(this.context.getString(R.string.google_directions_key))
                .setConnectTimeout(5, TimeUnit.SECONDS)
                .setReadTimeout(5, TimeUnit.SECONDS)
                .setWriteTimeout(5, TimeUnit.SECONDS);
    }

    private String getEndLocationTitle(DirectionsLeg leg){
        StringBuilder builder = new StringBuilder();
        builder.append("Time : ");
        builder.append(leg.duration.humanReadable);
        builder.append("\n");
        builder.append("Distance : ");
        builder.append(leg.distance.humanReadable);
        return  builder.toString();
    }

    private void addMarkersToMap(DirectionsResult results) {
        int routesNum = results.routes.length;

        for(int i = 0; i < routesNum; i++) {
            DirectionsLeg leg = results.routes[i].legs[0];

            this.mMap.addMarker(
                    new MarkerOptions()
                            .position(LocationTypesConverter.MapsLatLngToAndroidLatLng(leg.startLocation))
                            .title(leg.startAddress)
            );

            this.mMap.addMarker(
                    new MarkerOptions()
                            .position(LocationTypesConverter.MapsLatLngToAndroidLatLng(leg.endLocation))
                            .title(leg.startAddress)
                            .snippet(getEndLocationTitle(leg))
            );
        }
    }

    private void addPolyline(DirectionsResult results) {
        int routesNum = results.routes.length;
        String colors[] = this.context.getResources().getStringArray(R.array.colors);

        for(int i = 0; i < routesNum; i++) {
            List<LatLng> decodedPath = PolyUtil.decode(
                    results.routes[i].overviewPolyline.getEncodedPath()
            );

            this.mMap.addPolyline(new PolylineOptions().addAll(decodedPath).color(Color.parseColor(colors[i]))).setZIndex(i);
        }
    }

}
