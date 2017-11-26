package com.transporte.ufg.minha.minha_ufgtransporte.presenter;

import android.content.Context;
import android.content.Intent;

import com.transporte.ufg.minha.minha_ufgtransporte.R;
import com.transporte.ufg.minha.minha_ufgtransporte.model.Flag;
import com.transporte.ufg.minha.minha_ufgtransporte.view.CrudMapActivity;
import com.transporte.ufg.minha.minha_ufgtransporte.view.LoginActivity;
import com.transporte.ufg.minha.minha_ufgtransporte.view.MapActivity;
import com.transporte.ufg.minha.minha_ufgtransporte.view.PlaceListActivity;

/**
 * Classe que abre outras activities
 */
public class OpenActivity {

    /**
     * Abre a CrudMapActivity
     * @param context contexto da aplicacao
     * @param flag tipo de acao a ser realizada na activity
     * @see CrudMapActivity
     */
    public static void openCrudMapActivity(Context context, Flag flag){
        String flagKey = context.getString(R.string.flag_key);
        Intent intent = new Intent( context, CrudMapActivity.class );
        intent.putExtra(flagKey, flag.valor);
        context.startActivity(intent);
    }

    /**
     * Abre a MapActivity
     * @param context contexto da aplicacao
     * @see MapActivity
     */
    public static void openMapActivity(Context context){
        Intent intent = new Intent( context, MapActivity.class );
        context.startActivity(intent);
    }

    /**
     * Abre a PlaceListActivity
     * @param context contexto da aplicacao
     * @see com.transporte.ufg.minha.minha_ufgtransporte.view.PlaceListActivity
     */
    public static void openPlaceListActivity(Context context){
        Intent intent = new Intent( context, PlaceListActivity.class );
        context.startActivity(intent);
    }

    public static void openLoginActivity(Context context){
        Intent intent = new Intent( context, LoginActivity.class );
        context.startActivity(intent);
    }

}
