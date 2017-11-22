package com.transporte.ufg.minha.minha_ufgtransporte.presenter;

import com.google.firebase.database.DatabaseReference;
import com.transporte.ufg.minha.minha_ufgtransporte.model.MyPlace;

/**
 * Created by pedro on 22/11/17.
 */

public class MyPlaceDAO {

    private DatabaseReference databaseReference;

    public MyPlaceDAO(DatabaseReference databaseReference){
        this.databaseReference = databaseReference;
    }

    public void createMyPlace(MyPlace myPlace){
        this.databaseReference.push().setValue(myPlace);
    }

    public void updateMyPlace(String pushKey, MyPlace myPlace){
        this.databaseReference.child(pushKey).setValue(myPlace);
    }

    public void removeMyPlace(String pushKey){
        this.databaseReference.child(pushKey).removeValue();
    }
}