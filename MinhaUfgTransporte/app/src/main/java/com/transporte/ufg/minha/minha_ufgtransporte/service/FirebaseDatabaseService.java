package com.transporte.ufg.minha.minha_ufgtransporte.service;

import com.google.firebase.database.DatabaseReference;
import com.transporte.ufg.minha.minha_ufgtransporte.presenter.MyPlaceEventListener;

/**
 * Classe que gerencia os servicos de banco de dados do firebase
 */
public class FirebaseDatabaseService {

    /**
     * Referencia do no do banco de dados
     */
    private DatabaseReference databaseReference;

    /**
     * Construtor que inicializa o no do banco de dados
     * @param databaseReference Referencia do no
     */
    public FirebaseDatabaseService(DatabaseReference databaseReference){
        this.databaseReference = databaseReference;
    }

    /**
     * Busca a lista de locais no banco de dados
     */
    public void eventGetLocal(){
        MyPlaceEventListener listener = new MyPlaceEventListener();
        this.databaseReference.addValueEventListener(listener);
        this.databaseReference.keepSynced(true);
    }
}
