package com.transporte.ufg.minha.minha_ufgtransporte.view;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.transporte.ufg.minha.minha_ufgtransporte.R;
import com.transporte.ufg.minha.minha_ufgtransporte.model.Local;
import com.transporte.ufg.minha.minha_ufgtransporte.service.AsyncLocal;
import com.transporte.ufg.minha.minha_ufgtransporte.service.ConfiguracaoFirebase;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.Set;

public class LocalListActivity extends AppCompatActivity {

    private String tag = "MINHAUFG";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        AsyncLocal asyncTask = new AsyncLocal();
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
    public void onEvent(Set<Local> setLocal){

        for(Local local : setLocal){
            Log.d(this.tag, "ID: " + local.getIdentificador());
            Log.d(this.tag, " -> Latitude: " + local.getLatitude());
            Log.d(this.tag, " -> Longitude: " + local.getLongitude());
        }

    }
}
