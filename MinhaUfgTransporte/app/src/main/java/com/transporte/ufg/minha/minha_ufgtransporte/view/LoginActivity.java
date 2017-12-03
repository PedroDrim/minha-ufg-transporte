package com.transporte.ufg.minha.minha_ufgtransporte.view;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.transporte.ufg.minha.minha_ufgtransporte.R;
import com.transporte.ufg.minha.minha_ufgtransporte.presenter.OpenActivity;
import com.transporte.ufg.minha.minha_ufgtransporte.service.AuthListener;
import com.transporte.ufg.minha.minha_ufgtransporte.service.FirebaseConfiguration;

/**
 * Activity para conectar no firebase
 */
public class LoginActivity extends AppCompatActivity {

    /**
     * Autenticacao do usuario
     */
    private FirebaseAuth autenticacao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    /**
     * Realiza o login do usuario ao sistema
     * @param view Botao definido no .xml
     */
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
            String text = this.getString(R.string.login_fill_input);
            Toast.makeText( LoginActivity.this, text, Toast.LENGTH_SHORT ).show();
        }

    }

    /**
     * Valicacao do login do usuario
     * @param email email inserido
     * @param senha senha inserida
     */
    private void validarLogin(String email, String senha) {

        autenticacao = FirebaseConfiguration.getFirebaseAuth();
        autenticacao.signInWithEmailAndPassword(email, senha)
                .addOnCompleteListener(new AuthListener(this));
    }

}
