package com.transporte.ufg.minha.minha_ufgtransporte.service;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.transporte.ufg.minha.minha_ufgtransporte.view.MainActivity;
import com.transporte.ufg.minha.minha_ufgtransporte.view.MapActivity;

/**
 * Created by pedro on 22/11/17.
 */

public class AuthListener implements OnCompleteListener<AuthResult>{

    private Context context;

    public AuthListener(Context context) {
        this.context = context;
    }

    @Override
    public void onComplete(@NonNull Task<AuthResult> task) {

        if (task.isSuccessful()){
            this.abrirActivityMaps();
            this.showToast( "Login efetuado com sucesso" );
        }else {
            this.showToast( "Usuário ou senha inválidos" );
        }
    }

    private void abrirActivityMaps(){
        Intent intentAbrirTelaPrincipal =
                new Intent( this.context, MapActivity.class );

        this.context.startActivity(intentAbrirTelaPrincipal);
    }

    private void showToast(String text){
        Toast.makeText( this.context, text, Toast.LENGTH_SHORT ).show();
    }
}
