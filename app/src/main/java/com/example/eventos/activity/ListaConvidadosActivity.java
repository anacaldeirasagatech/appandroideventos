package com.example.eventos.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.eventos.R;
import com.example.eventos.adapter.AdapterListConvidados;
import com.example.eventos.model.Convidado;
import com.example.eventos.model.Convite;
import com.example.eventos.model.Evento;
import com.example.eventos.utils.ManagerFirebase;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class ListaConvidadosActivity extends AppCompatActivity {
    private Evento evento;
    FloatingActionButton btnaddConvidado;
    private List<Convite> convites;
    private List<Convidado> convidados;
    private ListView listViewEventos;
    private FirebaseFirestore database;
    private CollectionReference convitesRef;
    private AdapterListConvidados adapterListConvidados;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_convidados);
        setTitle("Lista de Convidados");
        database = ManagerFirebase.getFireStore();

        listViewEventos = (ListView)findViewById(R.id.listView);
        listViewEventos.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(getApplicationContext(), CadastroConvidadoActivity.class);
                Convidado convidado = convidados.get(i);
                intent.putExtra("evento", evento);
                intent.putExtra("convidado", convidado);
                startActivity(intent);
            }
        });
        btnaddConvidado = (FloatingActionButton)findViewById(R.id.btnAddConvidado);

        btnaddConvidado.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), CadastroConvidadoActivity.class);
                intent.putExtra("evento", evento);
                startActivity(intent);
            }
        });
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            evento = (Evento) bundle.getSerializable("evento");
            if(evento != null)
                carregarConviteUsuarios();
            else
                finish();
        }
    }
    @Override
    public void onResume() {
        super.onResume();

    }

    @Override
    protected void onStart() {
        super.onStart();

    }

    private void carregarConviteUsuarios() {
        convites = new ArrayList<>();
        convidados = new ArrayList<>();
        convitesRef = database.collection("convite");
        convitesRef
                .whereEqualTo("idEvento", evento.getIdEvento())
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            convites.clear();
                            convidados.clear();
                            for (DocumentSnapshot ds : task.getResult()) {
                                Convite convite = ds.toObject(Convite.class);
                                convites.add(convite);
                                //cria a lista de convidados para recuperar seus dados dentro do Adapter
                                Convidado convidado = new Convidado();
                                convidado.setIdConvidado(convite.getIdConvidado());
                                convidados.add(convidado);
                            }
                            startListView();
                        } else {
                            Log.d("ErroEventoList", "Error getting documents: ", task.getException());
                        }
                    }
                });
    }

    private void startListView() {
        try {
            adapterListConvidados = new AdapterListConvidados(getApplicationContext(), R.layout.listview_convidados_evento, convidados);
            listViewEventos.setAdapter(adapterListConvidados);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return false;
    }
}
