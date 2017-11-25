package com.transporte.ufg.minha.minha_ufgtransporte.presenter;

import android.content.Context;
import android.content.Intent;
import android.view.View;

import com.transporte.ufg.minha.minha_ufgtransporte.model.Flag;
import com.transporte.ufg.minha.minha_ufgtransporte.model.MyPlace;
import com.transporte.ufg.minha.minha_ufgtransporte.model.RecyclerClickListener;
import com.transporte.ufg.minha.minha_ufgtransporte.view.CrudMapActivity;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

/**
 * Classe que executa o evento de entrar na activity de detalhamento dos locais
 * @see RecyclerClickListener
 */
public class MyPlaceClickListener implements RecyclerClickListener{

    /**
     * Lista de locais
     */
    private List<MyPlace> myPlaceList;

    /**
     * Construtor para instanciar a lista de locais
     * @param myPlaceList lista de locais existentes no firebase
     */
    public MyPlaceClickListener(List<MyPlace> myPlaceList){
        this.myPlaceList = myPlaceList;
    }

    /**
     * Acao de click da View
     * @param position posicao da View clicada
     * @param v View clicada
     * @see RecyclerClickListener
     */
    @Override
    public void onItemClick(int position, View v) {
        MyPlace myPlace = this.myPlaceList.get(position);
        EventBus.getDefault().postSticky(myPlace);

        OpenActivity.openCrudMapActivity(v.getContext(), Flag.UPDATE);
    }
}
