package com.transporte.ufg.minha.minha_ufgtransporte.service;

import com.google.firebase.database.DatabaseReference;
import com.transporte.ufg.minha.minha_ufgtransporte.presenter.LocalEventListener;

/**
 * Created by pedro on 21/11/17.
 */

public class FirebaseDatabaseService {

    public void eventGetLocal(String userId){

        LocalEventListener listener = new LocalEventListener();
        DatabaseReference databaseReference = ConfiguracaoFirebase.getFirebaseDatabase();
        databaseReference
                .child("lista")
                .child(userId)
                .addValueEventListener(listener);

        databaseReference.keepSynced(true);
    }
}
