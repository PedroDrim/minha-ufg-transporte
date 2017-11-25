package com.transporte.ufg.minha.minha_ufgtransporte.service;

import android.app.IntentService;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.Nullable;

import com.google.firebase.database.DatabaseReference;

/**
 * Servico para buscar a lista de locais no banco de dados
 */
public class IntentMyPlace extends IntentService {

    /**
     * Construtor para inicializar o servico
     */
    public IntentMyPlace(){
        // Como evitar o hardcoded em um intent service?
        super("MinhaUFGIntent");
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        Context context = this.getApplicationContext();
        DatabaseReference databaseReference = FirebaseConfiguration.getFirebaseDatabase(context);
        new FirebaseDatabaseService(databaseReference).eventGetLocal();
    }
}
