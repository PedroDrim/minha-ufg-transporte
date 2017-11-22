package com.transporte.ufg.minha.minha_ufgtransporte.view;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.transporte.ufg.minha.minha_ufgtransporte.R;
import com.transporte.ufg.minha.minha_ufgtransporte.service.AuthListener;
import com.transporte.ufg.minha.minha_ufgtransporte.service.ConfiguracaoFirebase;

public class MainActivity extends AppCompatActivity {

    private FirebaseAuth autenticacao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void authUser(View view){

        EditText edtEmail = findViewById(R.id.edtEmail);
        EditText edtSenha = findViewById(R.id.edtSenha);

        boolean validation1 = !edtEmail.getText().toString().equals("");
        boolean validation2 = !edtSenha.getText().toString().equals("");

        if ( validation1 && validation2) {

            String email = edtEmail.getText().toString();
            String senha = edtSenha.getText().toString();

            validarLogin(email, senha);
        } else {
            String text = "Preencha os campos de e-mail e senha!";
            Toast.makeText( MainActivity.this, text, Toast.LENGTH_SHORT ).show();
        }

    }

    private void validarLogin(String email, String senha) {

        autenticacao = ConfiguracaoFirebase.getFirebaseAuth();
        autenticacao.signInWithEmailAndPassword(email, senha)
                .addOnCompleteListener(new AuthListener(this));
    }

}
