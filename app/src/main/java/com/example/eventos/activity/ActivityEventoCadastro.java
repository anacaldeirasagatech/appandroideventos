package com.example.eventos.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ComponentActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.eventos.R;
import com.example.eventos.model.Evento;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

public class ActivityEventoCadastro extends AppCompatActivity {
    private EditText txtNome, txtLocal, txtDescricao, txtData;
    private Button btn, btData;
    private static final int DATE_DIALOG_ID = 0;
    private FirebaseAuth auth = FirebaseAuth.getInstance();

    //private DatabaseReference referencia = FirebaseData
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_evento_cadastro);

        txtNome = findViewById(R.id.txtNome);
        txtLocal = findViewById(R.id.txtLocal);
        txtDescricao = findViewById(R.id.txtDescricao);
        txtData = findViewById(R.id.txtData);
        btn = findViewById(R.id.button);

        auth.signInWithEmailAndPassword("joao@hotmail.com", "123456789").addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful())
                {
                    Toast.makeText(ActivityEventoCadastro.this, "Login efetuado com sucesso!", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    Toast.makeText(ActivityEventoCadastro.this, "Não foi possível efetuar o login. Tente novamente conferindo todos os dados!", Toast.LENGTH_SHORT).show();
                }
            }
        });

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Evento evento = new Evento();
                try
                {
                    evento.setIdEvento("1");
                    evento.setNomeEvento(txtNome.getText().toString());
                    evento.setLocal(txtLocal.getText().toString());
                    evento.setDescricao(txtDescricao.getText().toString());
                    evento.setData(txtData.getText().toString());
                    evento.setUserId(auth.getCurrentUser().getUid());

                    salvar(evento);
                }
                catch (Exception e)
                {
                    e.printStackTrace();
                }

            }
        });
    }

    public void salvar(Evento evento)
    {
        FirebaseFirestore firestore = FirebaseFirestore.getInstance();

        DocumentReference ref = firestore.collection("evento").document();

        ref.set(evento).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful())
                {
                    Toast.makeText(ActivityEventoCadastro.this, "Cadastro efetuado com sucesso!", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    Toast.makeText(ActivityEventoCadastro.this, "Não foi possível efetuar o cadastro. Tente novamente conferindo todos os dados!", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
