package com.transporte.ufg.minha.minha_ufgtransporte.view;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.transporte.ufg.minha.minha_ufgtransporte.R;
import com.transporte.ufg.minha.minha_ufgtransporte.model.Flag;
import com.transporte.ufg.minha.minha_ufgtransporte.model.MyPlace;
import com.transporte.ufg.minha.minha_ufgtransporte.presenter.CrudMapClickListener;
import com.transporte.ufg.minha.minha_ufgtransporte.presenter.GpsInstance;
import com.transporte.ufg.minha.minha_ufgtransporte.presenter.MyPlaceDAO;

import org.greenrobot.eventbus.EventBus;


public class CrudMapActivity extends AppCompatActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private GpsInstance gpsInstance;
    private CrudMapClickListener crudMapClickListener;
    private TextView textView;

    public CrudMapActivity(){
        this.crudMapClickListener = new CrudMapClickListener();
        this.gpsInstance = new GpsInstance(this);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crud_map);

        this.textView = findViewById(R.id.identificador);
        this.gpsInstance.checkGPSPermission();

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.crud_map);
        mapFragment.getMapAsync(this);
    }

    public void getPoint(View view){

        LatLng clickPosition = this.crudMapClickListener.getClickPosition();

        if(clickPosition != null) {
            MyPlaceDAO myPlaceDAO = new MyPlaceDAO(this);

            String text;
            String flagKey = this.getString(R.string.flag_key);
            int flag = this.getIntent().getIntExtra(flagKey, -1);

            if (flag == Flag.UPDATE.valor) {

                MyPlace oldMyPlace = EventBus.getDefault().getStickyEvent(MyPlace.class);
                MyPlace newMyPlace = new MyPlace(
                        this.textView.getText().toString(),
                        clickPosition.latitude,
                        clickPosition.longitude
                );

                myPlaceDAO.updateMyPlace(oldMyPlace.getPushKey(), newMyPlace);
                text = this.getString(R.string.update_myPlace);
            } else {

                myPlaceDAO.createMyPlace(
                        new MyPlace(textView.getText().toString(),
                                clickPosition.latitude, clickPosition.longitude)
                );
                text = this.getString(R.string.insert_myPlace);
            }

            Toast.makeText(CrudMapActivity.this, text, Toast.LENGTH_SHORT).show();
        }

        this.finish();
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

        MyPlace myPlace = EventBus.getDefault().getStickyEvent(MyPlace.class);

        if(myPlace != null) {
            this.textView.setText(myPlace.getIdentificador());

            this.mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(
                    new LatLng(myPlace.getLatitude(), myPlace.getLongitude()),
                    15));

        }
        else {

            LatLng goiania = new LatLng(-16.665136, -49.286041);
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(goiania, 15));
        }

        String flagKey = this.getString(R.string.flag_key);
        int flag = this.getIntent().getIntExtra(flagKey, -1);

        if(flag == Flag.UPDATE.valor){
            this.mMap.addMarker(
                    new MarkerOptions().position(myPlace.toLatLng())
            );
        }

        this.crudMapClickListener.setGoogleMap(this.mMap);
        this.mMap.setOnMapClickListener(this.crudMapClickListener);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_locais, menu);
        return true;
    }

}

