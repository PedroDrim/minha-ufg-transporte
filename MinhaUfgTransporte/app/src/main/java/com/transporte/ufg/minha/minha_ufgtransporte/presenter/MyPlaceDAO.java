package com.transporte.ufg.minha.minha_ufgtransporte.presenter;

import android.content.Context;

import com.google.firebase.database.DatabaseReference;
import com.transporte.ufg.minha.minha_ufgtransporte.model.MyPlace;
import com.transporte.ufg.minha.minha_ufgtransporte.service.FirebaseConfiguration;

/**
 * Classe que gerencia o CRUD dos locais no firebase
 */
public class MyPlaceDAO {

    /**
     * Referencia do banco de dados
     */
    private DatabaseReference databaseReference;

    /**
     * Construtor que inicializa a referencia do banco de dados
     * @param context Contexto da aplicacao
     */
    public MyPlaceDAO(Context context){
        this.databaseReference = FirebaseConfiguration.getFirebaseDatabase(context);
    }

    /**
     * Adiciona um novo local ao banco de dados firebase
     * @param myPlace Local a ser adicionado
     */
    public void createMyPlace(MyPlace myPlace){
        this.databaseReference.push().setValue(myPlace);
    }

    /**
     * Atualiza um local ao banco de dados firebase
     * @param pushKey Chave de no do dado a ser atualizado
     * @param myPlace Local a ser adicionado
     */
    public void updateMyPlace(String pushKey, MyPlace myPlace){
        this.databaseReference.child(pushKey).setValue(myPlace);
    }

    /**
     * Remove um local ao banco de dados firebase
     * @param pushKey Chave de no do dado a ser removido
     */
    public void removeMyPlace(String pushKey){
        this.databaseReference.child(pushKey).removeValue();
    }
}
