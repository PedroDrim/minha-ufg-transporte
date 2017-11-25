package com.transporte.ufg.minha.minha_ufgtransporte.presenter;

import android.content.Context;
import android.content.Intent;

import com.transporte.ufg.minha.minha_ufgtransporte.R;
import com.transporte.ufg.minha.minha_ufgtransporte.model.Flag;
import com.transporte.ufg.minha.minha_ufgtransporte.view.CrudMapActivity;
import com.transporte.ufg.minha.minha_ufgtransporte.view.MapActivity;

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
        Intent intentAbrirTelaPrincipal = new Intent( context, CrudMapActivity.class );
        intentAbrirTelaPrincipal.putExtra(flagKey, flag.valor);
        context.startActivity(intentAbrirTelaPrincipal);
    }

    /**
     * Abre a MapActivity
     * @param context contexto da aplicacao
     * @see MapActivity
     */
    public static void openMapActivity(Context context){
        Intent intentAbrirTelaPrincipal = new Intent( context, MapActivity.class );
        context.startActivity(intentAbrirTelaPrincipal);
    }

}
