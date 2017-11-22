package com.transporte.ufg.minha.minha_ufgtransporte.service;

import com.google.firebase.database.DatabaseReference;
import com.transporte.ufg.minha.minha_ufgtransporte.presenter.MyPlaceEventListener;

/**
 * Created by pedro on 21/11/17.
 */

public class FirebaseDatabaseService {

    private DatabaseReference databaseReference;

    public FirebaseDatabaseService(DatabaseReference databaseReference){
        this.databaseReference = databaseReference;
    }

    public void eventGetLocal(){

        MyPlaceEventListener listener = new MyPlaceEventListener();
        this.databaseReference.addValueEventListener(listener);
        this.databaseReference.keepSynced(true);
    }
}
