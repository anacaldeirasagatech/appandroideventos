package com.example.eventos.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.eventos.R;
import com.example.eventos.model.Usuario;
import com.example.eventos.utils.ManagerFirebase;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthInvalidUserException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.auth.User;

public class LoginActivity extends AppCompatActivity {

    private EditText textLoginEmail, textLoginPassword;
    private Button btnLogin, btnRegister;
    private TextView forgotPassword;
    private Usuario userLogin;
    private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        userLogin = new Usuario();
        auth = ManagerFirebase.getFirebaseAuth();
        textLoginEmail = findViewById(R.id.txtInputLoginEmail);
        textLoginPassword = findViewById(R.id.txtInputLoginSenha);
        btnLogin = findViewById(R.id.btnLogin);
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                login();
            }
        });
        btnRegister = findViewById(R.id.btnCadastrarUsuario);
        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openRegisterActivity();
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseUser user = userLogin.getCurrentUser();
        if (user != null) {
            openMainActivity();
        }
    }
    public void openMainActivity() {
        Intent intent = new Intent(this, DashBoardActivity.class);
        startActivity(intent);
        finish();
    }
    private boolean validateLogin() {
        boolean response = true;
        if (userLogin == null)
            return false;
        if (userLogin.getEmail().isEmpty())
            response = false;
        if (userLogin.getSenha().isEmpty())
            response = false;
        return response;
    }
    private void login() {
        userLogin.setEmail(textLoginEmail.getText().toString());
        userLogin.setSenha(textLoginPassword.getText().toString());

        if (!validateLogin()) {
            Toast.makeText(this, "R.string.message_validation_login", Toast.LENGTH_LONG).show();
            return;
        }
        auth.signInWithEmailAndPassword(
                userLogin.getEmail(), userLogin.getSenha()
        ).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {

                if (task.isSuccessful()) {
                    openMainActivity();
                } else {

                    String exception;
                    try {
                        throw task.getException();
                    } catch (FirebaseAuthInvalidUserException e) {
                        exception = "R.string.FirebaseAuthInvalidUserException";
                    } catch (FirebaseAuthInvalidCredentialsException e) {
                        exception = "R.string.FirebaseAuthInvalidLoginCredentialsException";
                    } catch (Exception e) {
                        exception = "R.string.FirebaseAuthInvalidLoginUserException";
                        e.printStackTrace();
                    }
                    Toast.makeText(LoginActivity.this,
                            exception,
                            Toast.LENGTH_SHORT).show();
                }

            }
        });
    }

    public void openRegisterActivity() {
        Intent intent = new Intent(this, CadastroUsuarioActivity.class);
        startActivity(intent);
    }

    private void forgot() {
        if(textLoginEmail.getText().toString().isEmpty() || textLoginEmail.getText().length() < 6) {
            Toast.makeText(this, "getText(R.string.recover_username)", Toast.LENGTH_SHORT).show();
            return;
        }
        FirebaseAuth auth = ManagerFirebase.getFirebaseAuth();
        String emailAddress = textLoginEmail.getText().toString();

        auth.sendPasswordResetEmail(emailAddress).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                Toast.makeText(getApplicationContext(), "getText(R.string.message_email_reset_send)", Toast.LENGTH_SHORT).show();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(getApplicationContext(), "getText(R.string.message_email_reset_send_fail)", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
