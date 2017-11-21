package com.transporte.ufg.minha.minha_ufgtransporte.service;

import android.os.AsyncTask;

/**
 * Created by pedro on 21/11/17.
 */

public class AsyncLocal extends AsyncTask<Void, Void, Void> {

    private FirebaseDatabaseService firebaseDatabaseService;

    public AsyncLocal(){
        this.firebaseDatabaseService = new FirebaseDatabaseService();
    }

    @Override
    protected Void doInBackground(Void... voids) {
        String userId = ConfiguracaoFirebase.getFirebaseAuth().getUid();
        this.firebaseDatabaseService.eventGetLocal(userId);
        return null;
    }
}
