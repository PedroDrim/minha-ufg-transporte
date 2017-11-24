package com.transporte.ufg.minha.minha_ufgtransporte.service;

import android.app.IntentService;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.annotation.Nullable;

import com.google.firebase.database.DatabaseReference;

/**
 * Created by pedro on 21/11/17.
 */

public class IntentMyPlace extends IntentService {

    public IntentMyPlace(){
        super("MinhaUFGIntent");
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        DatabaseReference databaseReference = ConfiguracaoFirebase.getFirebaseDatabase();
        new FirebaseDatabaseService(databaseReference).eventGetLocal();
    }
}
