package com.transporte.ufg.minha.minha_ufgtransporte.service;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.location.Location;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlaceSelectionListener;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.maps.DirectionsApi;
import com.google.maps.GeoApiContext;
import com.google.maps.android.PolyUtil;
import com.google.maps.errors.ApiException;
import com.google.maps.model.DirectionsLeg;
import com.google.maps.model.DirectionsResult;
import com.google.maps.model.DirectionsRoute;
import com.google.maps.model.DirectionsStep;
import com.google.maps.model.TransitMode;
import com.google.maps.model.TravelMode;
import com.transporte.ufg.minha.minha_ufgtransporte.R;
import com.transporte.ufg.minha.minha_ufgtransporte.model.LocationTypesConverter;
import com.transporte.ufg.minha.minha_ufgtransporte.view.MapActivity;

import org.joda.time.DateTime;
import org.json.JSONObject;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * Created by pedro on 25/11/17.
 */

public class UfgPlaceSelectListener extends AppCompatActivity implements PlaceSelectionListener {

    private GoogleMap mMap;
    private Context context;
    private Location lastKnownLocation;
    private MapActivity mapActivity;
    private String colors[];

    public UfgPlaceSelectListener(GoogleMap mMap, Location lastKnownLocation, Context context, MapActivity mapActivity) {
        this.mMap = mMap;
        this.context = context;
        this.lastKnownLocation = lastKnownLocation;
        this.mapActivity = mapActivity;
        this.colors = this.context.getResources().getStringArray(R.array.colors);
    }

    @Override
    public void onPlaceSelected(Place place) {
        LatLng destination = place.getLatLng();

        Log.i("-->User dest lat", String.valueOf(destination.latitude));
        Log.i("-->User dest lng", String.valueOf(destination.longitude));

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
                    .language("pt-BR")
                    .await();

            showTravelDetails(result);
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

    private void showTravelDetails(DirectionsResult result) {
        mapActivity.routeDetailsView.removeAllViews();

        int fastestRoute = findFastestRoute(result);

        DirectionsStep[] steps = result.routes[fastestRoute].legs[0].steps;
        int stepsNumber = result.routes[fastestRoute].legs[0].steps.length;
        int busCounter = 0;
        boolean addArrow = true;

        for(int i = 0; i < stepsNumber; i++) {
            addArrow = true;
            Log.i("==== Step", steps[i].htmlInstructions);
            Log.i("==== Step", steps[i].distance.humanReadable);
            Log.e("TRAVEL-MODE",  steps[i].travelMode.toString());

            if (steps[i].travelMode.toString().equals("walking")){
                int walkingDuration = (int) steps[i].duration.inSeconds/60;
                if (walkingDuration > 1) {
                    String walkMin = String.valueOf(steps[i].duration.inSeconds/60);
                    mapActivity.addWalkDetail(walkMin);

                } else addArrow = false;
            }
            if (steps[i].transitDetails != null) {
                busCounter++;
                Log.i("==== LINHA DO ONIBUS", steps[i].transitDetails.line.shortName);
                String busLine = steps[i].transitDetails.line.shortName;
                mapActivity.addBusDetail(busLine, busCounter);
            }

            if(i < stepsNumber - 1 && shouldAddArrow(steps[i+1]) && addArrow){
                mapActivity.addArrow();
            }
        }

        String travelTime = result.routes[fastestRoute].legs[0].duration.toString();
        int routeColor = Color.parseColor(colors[fastestRoute]);
        mapActivity.makeCardVisible(travelTime, routeColor);
    }

    private int findFastestRoute(DirectionsResult result) {
        int routesNumber = result.routes.length;
        long lowerDuration = Long.MAX_VALUE;
        int fastestRoute = 0;

        for(int n = 0; n < routesNumber; n++){
            if(result.routes[n].legs[0].duration.inSeconds < lowerDuration){
                lowerDuration = result.routes[n].legs[0].duration.inSeconds;
                fastestRoute = n;
                Log.e("==== FASTEST ROUTE", String.valueOf(fastestRoute) );
            }
        }
        return fastestRoute;
    }

    private boolean shouldAddArrow (DirectionsStep nextStep) {
        if (nextStep.travelMode.toString().equals("walking")) {
            int walkingDuration = (int) nextStep.duration.inSeconds / 60;
            return walkingDuration > 1;
        }
        return true;
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

    private void addMarkersToMap(DirectionsResult result) {
        mMap.clear();
        int routesNum = result.routes.length;
        Marker origin = null;
        Marker destination = null;

        for(int i = 0; i < routesNum; i++) {
            DirectionsLeg leg = result.routes[i].legs[0];

            origin = this.mMap.addMarker(
                    new MarkerOptions()
                            .position(LocationTypesConverter.MapsLatLngToAndroidLatLng(leg.startLocation))
                            .title(leg.startAddress)
            );

            destination = this.mMap.addMarker(
                    new MarkerOptions()
                            .position(LocationTypesConverter.MapsLatLngToAndroidLatLng(leg.endLocation))
                            .title(leg.startAddress)
                            .snippet(getEndLocationTitle(leg))
            );
        }

        LatLngBounds.Builder builder = new LatLngBounds.Builder();
        builder.include(origin.getPosition());
        builder.include(destination.getPosition());

        LatLngBounds bounds = builder.build();

        this.mMap.animateCamera(
                CameraUpdateFactory.newLatLngBounds(bounds, 280),
                2000,
                null
        );
    }

    private void addPolyline(DirectionsResult result) {
        int routesNum = result.routes.length;

        for(int i = 0; i < routesNum; i++) {
            List<LatLng> decodedPath = PolyUtil.decode(
                    result.routes[i].overviewPolyline.getEncodedPath()
            );

            this.mMap.addPolyline(new PolylineOptions().addAll(decodedPath).color(Color.parseColor(colors[i]))).setZIndex(i);
        }
    }

}

