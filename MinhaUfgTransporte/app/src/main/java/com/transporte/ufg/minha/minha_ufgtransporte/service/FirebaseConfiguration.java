package com.transporte.ufg.minha.minha_ufgtransporte.service;

import android.content.Context;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.transporte.ufg.minha.minha_ufgtransporte.R;

/**
 * Classe que configura os servicos do firebase utilizados
 */
public class FirebaseConfiguration {

    /**
     * Referencia do banco de dados
     */
    private static DatabaseReference referenciaFirebase;

    /**
     * Referencia da autenticacao
     */
    private static FirebaseAuth autenticacao;

    /**
     * Obtem o no do usuario do banco de dados firebase
     * @param context Contexto da aplicacao
     * @return Referencia do banco de dados
     */
    public static DatabaseReference getFirebaseDatabase(Context context) {
        if (referenciaFirebase == null){
            referenciaFirebase = FirebaseDatabase.getInstance().getReference();

            if(autenticacao != null){
                referenciaFirebase = referenciaFirebase
                        .child(context.getString(R.string.firebase_node))
                        .child(FirebaseConfiguration.getFirebaseAuth().getUid());
            }
        }
        return referenciaFirebase;
    }

    /**
     * Obtem a autenticacao do usuario
     * @return Autenticacao do usuario
     */
    public static FirebaseAuth getFirebaseAuth() {
        if (autenticacao == null) {
            autenticacao = FirebaseAuth.getInstance();
        }
        return autenticacao;
    }
}
