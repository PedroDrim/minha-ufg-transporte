package com.transporte.ufg.minha.minha_ufgtransporte.view.component;

import android.content.Context;
import android.content.Intent;
import android.support.v4.widget.SwipeRefreshLayout;

import com.transporte.ufg.minha.minha_ufgtransporte.service.IntentMyPlace;

/**
 * Classe que gerencia as acoes de refresh do SwipeLayout e o service
 * @see com.transporte.ufg.minha.minha_ufgtransporte.view.CrudMapActivity
 * @see IntentMyPlace
 */
public class MyPlaceRefreshListener implements SwipeRefreshLayout.OnRefreshListener {

    /**
     * Contexto da aplicacao
     */
    private Context context;

    /**
     * Construtor que inicializa o contexto da aplicacao
     * @param context Contexto da aplicacao
     */
    public MyPlaceRefreshListener(Context context){
        this.context = context;
        this.startIntent();
    }

    /**
     * Inicia o servico de busca do banco de dados
     */
    private void startIntent(){
        Intent firebaseIntent = new Intent(this.context, IntentMyPlace.class);
        this.context.startService(firebaseIntent);
    }

    @Override
    public void onRefresh() {
        this.startIntent();
    }

}
