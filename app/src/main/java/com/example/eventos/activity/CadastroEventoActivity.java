package com.example.eventos.activity;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.eventos.R;
import com.example.eventos.model.Evento;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;
import java.util.UUID;

public class CadastroEventoActivity extends AppCompatActivity {

    private int mYear, mMonth, mDay;
    private EditText txtNome, txtLocal, txtDescricao, txtData;
    private Button btn, btCalendario;
    private static final int DATE_DIALOG_ID = 0;
    private FirebaseAuth auth = FirebaseAuth.getInstance();
    private Evento evento;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_evento_cadastro);
        evento = null;
        Bundle bundle = getIntent().getExtras();
        txtNome = findViewById(R.id.txtNome);
        txtLocal = findViewById(R.id.txtLocal);
        txtDescricao = findViewById(R.id.txtDescricao);
        txtData = findViewById(R.id.txtData);
        btn = findViewById(R.id.btnEventoSalvar);
        btCalendario = findViewById(R.id.btnCalendarioEvento);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                salvar();
            }
        });
        btCalendario.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                showDialog(DATE_DIALOG_ID);
            }
        });
        if (bundle != null) {
            evento = (Evento) bundle.getSerializable("evento");
            if (evento != null) {
                txtNome.setText(evento.getNomeEvento());
                txtLocal.setText(evento.getLocal());
                txtDescricao.setText(evento.getDescricao());
                txtData.setText(evento.getData());
            }
        }
    }

    public void salvar() {
        try {
            if (evento == null) {
                evento = new Evento();
                evento.setIdEvento(UUID.randomUUID().toString());
            }
            evento.setNomeEvento(txtNome.getText().toString());
            evento.setLocal(txtLocal.getText().toString());
            evento.setDescricao(txtDescricao.getText().toString());
            evento.setData(txtData.getText().toString());
            evento.setUserId(auth.getCurrentUser().getUid());

            salvar(evento);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected Dialog onCreateDialog(int id) {
        Calendar calendar = Calendar.getInstance();

        mYear = calendar.get(Calendar.YEAR);
        mMonth = calendar.get(Calendar.MONTH);
        mDay = calendar.get(Calendar.DAY_OF_MONTH);

        switch (id) {
            case DATE_DIALOG_ID:
                return new DatePickerDialog(this, mDateSetListener, mYear, mMonth, mDay);
        }
        return null;
    }

    private DatePickerDialog.OnDateSetListener mDateSetListener = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
            // TODO Auto-generated method stub
            Calendar myCalendar = Calendar.getInstance();
            myCalendar.set(Calendar.YEAR, year);
            myCalendar.set(Calendar.MONTH, monthOfYear);
            myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            String myFormat = "dd/MM/yyyy"; //In which you need put here
            SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
            txtData.setText(sdf.format(myCalendar.getTime()));
        }
    };

    private void salvar(Evento evento) {
        FirebaseFirestore firestore = FirebaseFirestore.getInstance();
        DocumentReference ref = firestore.collection("evento").document(evento.getIdEvento());
        ref.set(evento).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    Toast.makeText(CadastroEventoActivity.this, "Cadastro efetuado com sucesso!", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(CadastroEventoActivity.this, "Não foi possível efetuar o cadastro. Tente novamente conferindo todos os dados!", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return false;
    }
}
