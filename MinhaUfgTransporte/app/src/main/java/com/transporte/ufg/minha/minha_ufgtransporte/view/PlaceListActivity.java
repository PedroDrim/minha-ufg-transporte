package com.transporte.ufg.minha.minha_ufgtransporte.view;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.google.android.gms.location.places.Place;
import com.google.firebase.database.DatabaseReference;
import com.transporte.ufg.minha.minha_ufgtransporte.R;
import com.transporte.ufg.minha.minha_ufgtransporte.model.MyPlace;
import com.transporte.ufg.minha.minha_ufgtransporte.presenter.MyPlaceDAO;
import com.transporte.ufg.minha.minha_ufgtransporte.service.AsyncMyPlace;
import com.transporte.ufg.minha.minha_ufgtransporte.service.ConfiguracaoFirebase;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.Set;

public class PlaceListActivity extends AppCompatActivity {

    private String tag = "MINHAUFG";
    private DatabaseReference databaseReference;

    public PlaceListActivity(){
        this.databaseReference = ConfiguracaoFirebase.getFirebaseDatabase()
                .child("lista")
                .child(ConfiguracaoFirebase.getFirebaseAuth().getUid());
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        AsyncMyPlace asyncTask = new AsyncMyPlace(this.databaseReference);
        asyncTask.execute();

        setContentView(R.layout.activity_local_list);
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

    @Subscribe
    public void onEvent(Set<MyPlace> setMyPlace){

        for(MyPlace myPlace : setMyPlace){
            Log.d(this.tag, "* " + myPlace.getPushKey());
            Log.d(this.tag, " -> Identificador: " + myPlace.getIdentificador());
            Log.d(this.tag, " -> Latitude: " + myPlace.getLatitude());
            Log.d(this.tag, " -> Longitude: " + myPlace.getLongitude());
        }

    }
}
