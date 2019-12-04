package com.example.eventos.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.eventos.R;
import com.example.eventos.model.Usuario;
import com.example.eventos.utils.ManagerFirebase;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseAuthWeakPasswordException;

public class CadastroUsuarioActivity extends AppCompatActivity {

    TextInputEditText nome, email, senha;
    FloatingActionButton btSalvar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro_usuario);
        nome = findViewById(R.id.cadastroUsuarioNome);
        email = findViewById(R.id.cadastroUsuarioEmail);
        senha = findViewById(R.id.cadastroUsuarioSenha);

        btSalvar = findViewById(R.id.fbSalvarUsuario);
        btSalvar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Usuario usuario = new Usuario();
                usuario.setNomeUser(nome.getText().toString());
                usuario.setEmail(email.getText().toString());
                usuario.setSenha(senha.getText().toString());
                final FirebaseAuth auth = ManagerFirebase.getFirebaseAuth();
                auth.createUserWithEmailAndPassword(
                        usuario.getEmail(), usuario.getSenha()
                ).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        if (task.isSuccessful()) {

                            Toast.makeText(getApplicationContext(),
                                    "R.string.message_success_create_user",
                                    Toast.LENGTH_SHORT).show();
                            usuario.updateNameUserAuthFirebase(usuario.getNomeUser());

                            //finish();

                            try {
                                String uuid = auth.getUid();
                                usuario.setUserId(uuid);
                                usuario.salvar();
                                Intent intent = new Intent(getApplicationContext(), CadastroUsuarioActivity.class);
                                startActivity(intent);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }

                        }
                        else {

                            String exception;
                            try {
                                throw task.getException();
                            } catch (FirebaseAuthWeakPasswordException e) {
                                exception = "R.string.FirebaseAuthWeakPasswordException";
                            } catch (FirebaseAuthInvalidCredentialsException e) {
                                exception = "R.string.FirebaseAuthInvalidCredentialsException";
                            } catch (FirebaseAuthUserCollisionException e) {
                                exception = "R.string.FirebaseAuthUserCollisionException";
                            } catch (Exception e) {
                                exception = "R.string.FirebaseAuthException";
                                e.printStackTrace();
                            }
                            Toast.makeText(getApplicationContext(), exception, Toast.LENGTH_SHORT).show();
                        }
                        finish();
                    }
                });

            }
        });
    }

    public static class ActivityCadastroEvento extends AppCompatActivity {

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_evento_cadastro);
        }
    }
}
