package com.transporte.ufg.minha.minha_ufgtransporte.service;

import android.content.Context;
import android.support.annotation.NonNull;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.transporte.ufg.minha.minha_ufgtransporte.R;
import com.transporte.ufg.minha.minha_ufgtransporte.presenter.OpenActivity;

/**
 * Classe que autentica a entrada do usuario
 */
public class AuthListener implements OnCompleteListener<AuthResult>{

    /**
     * Contexto da aplicacao
     */
    private Context context;

    /**
     * Construtor que define o contexto da aplicacao
     * @param context Contexto da aplicacao
     */
    public AuthListener(Context context) {
        this.context = context;
    }

    @Override
    public void onComplete(@NonNull Task<AuthResult> task) {

        if (task.isSuccessful()){
            OpenActivity.openPlaceListActivity(this.context);
        }else {
            String text = context.getString(R.string.invalid_login);
            Toast.makeText( this.context, text, Toast.LENGTH_SHORT ).show();
        }
    }

}
