package com.transporte.ufg.minha.minha_ufgtransporte.presenter;

import android.content.Context;

import com.transporte.ufg.minha.minha_ufgtransporte.model.MyPlace;
import com.transporte.ufg.minha.minha_ufgtransporte.view.component.MyPlaceAdapter;

/**
 * Created by pedro on 28/11/17.
 */

public class DataView {
    
    private MyPlaceAdapter adapter;
    private MyPlaceDAO myPlaceDAO;
    
    public DataView(Context context, MyPlaceAdapter adapter){
        this.adapter = adapter;
        this.myPlaceDAO = new MyPlaceDAO(context);
    }
    
    public void remove(MyPlace myPlace){
        this.myPlaceDAO.removeMyPlace(myPlace.getPushKey());
        this.adapter.deleteItem(myPlace);
    }
    
    public void update(MyPlace old, MyPlace myPlace){
        
    }
    
}
