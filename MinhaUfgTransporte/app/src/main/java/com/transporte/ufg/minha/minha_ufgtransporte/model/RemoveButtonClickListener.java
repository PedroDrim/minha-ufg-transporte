package com.transporte.ufg.minha.minha_ufgtransporte.model;

import android.view.View;
import android.view.View.OnClickListener;

import com.transporte.ufg.minha.minha_ufgtransporte.presenter.MyPlaceDAO;

/**
 * Created by pedro on 27/11/17.
 */

public class RemoveButtonClickListener implements OnClickListener{

    private String pushKey;

    public RemoveButtonClickListener(String pushKey){
        this.pushKey = pushKey;
    }

    @Override
    public void onClick(View v) {
        new MyPlaceDAO(v.getContext()).removeMyPlace(this.pushKey);
    }
}
