package com.transporte.ufg.minha.minha_ufgtransporte.presenter;

import android.util.Log;
import android.view.View;

import com.transporte.ufg.minha.minha_ufgtransporte.model.RecyclerClickListener;

/**
 * Created by pedro on 23/11/17.
 */

public class MyPlaceClickListener implements RecyclerClickListener{
    @Override
    public void onItemClick(int position, View v) {
        Log.d("MINHAUFG", "Voce clicou no item: " + position);
    }
}
