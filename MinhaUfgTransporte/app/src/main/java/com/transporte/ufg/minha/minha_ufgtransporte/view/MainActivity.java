package com.transporte.ufg.minha.minha_ufgtransporte.view;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.transporte.ufg.minha.minha_ufgtransporte.R;
import com.transporte.ufg.minha.minha_ufgtransporte.model.Usuario;
import com.transporte.ufg.minha.minha_ufgtransporte.service.ConfiguracaoFirebase;

public class MainActivity extends AppCompatActivity {

    private EditText edtEmail;
    private EditText edtSenha;
    private Button btnLogar;
    private FirebaseAuth autenticacao;
    private Usuario usuario;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        edtEmail = findViewById(R.id.edtEmail);
        edtSenha = findViewById(R.id.edtSenha);
        btnLogar = findViewById(R.id.btnLogar);

        btnLogar.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                boolean validation1 = !edtEmail.getText().toString().equals("");
                boolean validation2 = !edtSenha.getText().toString().equals("");

                if ( validation1 && validation2) {

                    String email = edtEmail.getText().toString();
                    String senha = edtSenha.getText().toString();
                    usuario = new Usuario();
                    usuario.setEmail(email);
                    usuario.setSenha(senha);

                    validarLogin();
                } else {
                    Toast.makeText(
                            MainActivity.this,
                            "Preencha os campos de e-mail e senha!",
                            Toast.LENGTH_SHORT
                    ).show();
                }
            }
        });

    }

    private void validarLogin() {

        autenticacao = ConfiguracaoFirebase.getFirebaseAuth();
        autenticacao.signInWithEmailAndPassword(usuario.getEmail(), usuario.getSenha())
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {

                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        if (task.isSuccessful()){

                            abrirActivityMaps();
                            Toast.makeText(
                                    MainActivity.this,
                                    "Login efetuado com sucesso",
                                    Toast.LENGTH_SHORT
                            ).show();
                        }else {
                            Toast.makeText(
                                    MainActivity.this,
                                    "Usuário ou senha inválidos",
                                    Toast.LENGTH_SHORT
                            ).show();
                        }
                    }
                });
    }

    private void abrirActivityMaps(){
        Intent intentAbrirTelaPrincipal = new Intent(
                MainActivity.this,
                MapActivity.class
        );
        startActivity(intentAbrirTelaPrincipal);
    }

}
