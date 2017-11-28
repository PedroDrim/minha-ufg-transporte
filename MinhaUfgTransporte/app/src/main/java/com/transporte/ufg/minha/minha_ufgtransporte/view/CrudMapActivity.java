package com.transporte.ufg.minha.minha_ufgtransporte.view;

import android.annotation.SuppressLint;
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
    private MyPlaceDAO myPlaceDAO;
    private MyPlace myPlace;
    private int flag;

    public CrudMapActivity(){
        this.crudMapClickListener = new CrudMapClickListener();
        this.gpsInstance = new GpsInstance(this);
        this.myPlace = EventBus.getDefault().getStickyEvent(MyPlace.class);
        EventBus.getDefault().removeStickyEvent(MyPlace.class);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crud_map);

        String flagKey = this.getString(R.string.flag_key);
        this.flag = this.getIntent().getIntExtra(flagKey, -1);
        this.myPlaceDAO = new MyPlaceDAO(this);
        this.textView = findViewById(R.id.identificador);
        this.gpsInstance.checkGPSPermission();

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.crud_map);
        mapFragment.getMapAsync(this);
    }

    public void getPoint(View view){

        String text;
        LatLng clickPosition = this.crudMapClickListener.getClickPosition();

        if(clickPosition != null) {

            if (this.flag == Flag.UPDATE.valor) {
                this.updateMyPlace(clickPosition);
                text = this.getString(R.string.update_myPlace);
            } else {
                this.createMyPlace(clickPosition);
                text = this.getString(R.string.insert_myPlace);
            }

        } else {
            text = this.getString(R.string.none_myPlace);
        }

        Toast.makeText(CrudMapActivity.this, text, Toast.LENGTH_SHORT).show();
        this.finish();
    }

    private void updateMyPlace(LatLng position){
        MyPlace newMyPlace = new MyPlace( this.textView.getText().toString(), position );
        this.myPlaceDAO.updateMyPlace(this.myPlace.getPushKey(), newMyPlace);
    }

    private void createMyPlace(LatLng position){
        MyPlace newMyPlace = new MyPlace( this.textView.getText().toString(), position );
        this.myPlaceDAO.createMyPlace(newMyPlace);
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

        if(this.myPlace != null) {
            this.textView.setText(this.myPlace.getIdentificador());
            this.mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(myPlace.toLatLng(), 15));

        } else {
            LatLng goiania = new LatLng(-16.665136, -49.286041);
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(goiania, 15));

        }

        if(this.flag == Flag.UPDATE.valor){
            this.mMap.addMarker( new MarkerOptions().position(myPlace.toLatLng()) );
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

