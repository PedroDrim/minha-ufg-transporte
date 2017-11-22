package com.transporte.ufg.minha.minha_ufgtransporte.service;

import android.os.AsyncTask;

import com.google.firebase.database.DatabaseReference;

/**
 * Created by pedro on 21/11/17.
 */

public class AsyncMyPlace extends AsyncTask<Void, Void, Void> {

    private FirebaseDatabaseService firebaseDatabaseService;

    public AsyncMyPlace(DatabaseReference databaseReference){
        this.firebaseDatabaseService = new FirebaseDatabaseService(databaseReference);
    }

    @Override
    protected Void doInBackground(Void... voids) {
        this.firebaseDatabaseService.eventGetLocal();
        return null;
    }
}
