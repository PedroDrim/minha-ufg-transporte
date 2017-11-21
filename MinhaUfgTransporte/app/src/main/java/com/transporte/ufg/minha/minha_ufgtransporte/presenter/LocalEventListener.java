package com.transporte.ufg.minha.minha_ufgtransporte.presenter;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;
import com.transporte.ufg.minha.minha_ufgtransporte.model.Local;

import org.greenrobot.eventbus.EventBus;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by pedro on 21/11/17.
 */

public class LocalEventListener implements ValueEventListener {

    @Override
    public void onDataChange(DataSnapshot dataSnapshot) {

        Set<Local> instance = new HashSet<>();
        for(DataSnapshot snapshot : dataSnapshot.getChildren()){

            Local local = snapshot.getValue(Local.class);
            instance.add(local);
        }

        EventBus.getDefault().post(instance);
    }

    @Override
    public void onCancelled(DatabaseError databaseError) {

    }
}
