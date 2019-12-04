package com.example.eventos.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.eventos.R;
import com.example.eventos.model.Convidado;
import com.example.eventos.model.Evento;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputEditText;

import java.util.UUID;

public class CadastroConvidadoActivity extends AppCompatActivity {

    private Evento evento;
    private Convidado convidado;
    private TextInputEditText nome, email, telefone;
    private FloatingActionButton btnSalvar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro_convidado);
        evento = null;
        convidado = null;
        Bundle bundle = getIntent().getExtras();

        nome = findViewById(R.id.txtInputConvidadoNome);
        email = findViewById(R.id.txtInputConvidadoEmail);
        telefone = findViewById(R.id.txtInputConvidadoTelefone);
        btnSalvar = findViewById(R.id.btnSalvarConvidado);
        btnSalvar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                salvar();
                toastAlerta("Convidado adicionado ao Evento com Sucesso!");
                finish();
            }
        });

        if (bundle != null) {
            evento = (Evento) bundle.getSerializable("evento");
            if(evento == null)
            {
                toastAlerta("Você precisa ter um evento criado para conseguir adicionar convidados!");
                finish();
            }
            convidado = (Convidado) bundle.getSerializable("convidado");
            if (convidado != null) {
                nome.setText(convidado.getNome());
                email.setText(convidado.getEmail());
                telefone.setText(convidado.getTelefone());
            }
        } else {
            toastAlerta("Você precisa ter um evento criado para conseguir adicionar convidados!");
            finish();
        }
    }

    private void toastAlerta(String mensagem) {
        Toast.makeText(this, mensagem, Toast.LENGTH_LONG).show();
        try {
            Thread.sleep(1000 * 3);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void salvar() {

        if (convidado == null)
            convidado.setIdConvidado(UUID.randomUUID().toString());

        convidado.setNome(nome.getText().toString());
        convidado.setEmail(email.getText().toString());
        convidado.setTelefone(telefone.getText().toString());
        convidado.salvar(evento.getIdEvento());
    }
}
