package com.transporte.ufg.minha.minha_ufgtransporte.presenter;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;
import com.transporte.ufg.minha.minha_ufgtransporte.model.MyPlace;

import org.greenrobot.eventbus.EventBus;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by pedro on 21/11/17.
 */

public class MyPlaceEventListener implements ValueEventListener {

    @Override
    public void onDataChange(DataSnapshot dataSnapshot) {

        Set<MyPlace> instance = new HashSet<>();
        for(DataSnapshot snapshot : dataSnapshot.getChildren()){

            MyPlace myPlace = snapshot.getValue(MyPlace.class);
            myPlace.setPushKey(snapshot.getKey());
            instance.add(myPlace);
        }

        EventBus.getDefault().post(instance);
    }

    @Override
    public void onCancelled(DatabaseError databaseError) {

    }
}
