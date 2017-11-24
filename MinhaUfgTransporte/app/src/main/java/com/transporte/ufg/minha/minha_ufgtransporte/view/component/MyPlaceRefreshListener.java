package com.transporte.ufg.minha.minha_ufgtransporte.view.component;

import android.content.Context;
import android.content.Intent;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;

import com.transporte.ufg.minha.minha_ufgtransporte.service.IntentMyPlace;

/**
 * Created by pedro on 23/11/17.
 */

public class MyPlaceRefreshListener implements SwipeRefreshLayout.OnRefreshListener {

    private Context context;

    public MyPlaceRefreshListener(Context context){
        this.context = context;
        this.startIntent();
    }

    @Override
    public void onRefresh() {
        Log.d("MINHAUFG", "refreshing");
        this.startIntent();
    }

    private void startIntent(){
        Intent firebaseIntent = new Intent(this.context, IntentMyPlace.class);
        this.context.startService(firebaseIntent);
    }
}
