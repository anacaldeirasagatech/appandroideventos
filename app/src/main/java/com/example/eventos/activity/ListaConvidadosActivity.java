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
import com.example.eventos.adapter.AdapterListEventos;
import com.example.eventos.model.Convidado;
import com.example.eventos.model.Convite;
import com.example.eventos.model.Evento;
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
    ListView listItemView;
    FloatingActionButton fab;
    private List<Convite> convites;
    private List<Convidado> convidados;
    private List<String> convidadoLista;
    private ListView listViewEventos;
    private FirebaseFirestore database;
    private CollectionReference convitesRef;
    private AdapterListConvidados adapterListConvidados;
    ArrayAdapter<String> adapterEventos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_convidados);
        setTitle("Lista de Convidados");
        evento = null;
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            evento = (Evento) bundle.getSerializable("evento");
        }
        listItemView = (ListView)findViewById(R.id.listView);
        listItemView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(getApplicationContext(), CadastroConvidadoActivity.class);
                Convidado convidado = convidados.get(i);
                intent.putExtra("evento", evento);
                intent.putExtra("convidado", convidado);
                startActivity(intent);
            }
        });
        fab = (FloatingActionButton)findViewById(R.id.btnAddEvento);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Toast.makeText(ListaConvidadosActivity.this, "Fab Clicked", Toast.LENGTH_LONG).show();
            }
        });
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
        convitesRef
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(QuerySnapshot documentSnapshots, FirebaseFirestoreException e) {
                        if (e != null) {
                            return;
                        }
                        convites = documentSnapshots.toObjects(Convite.class);
                        convidados = new ArrayList<>();
                        for (Convite convite : convites) {
                            //cria a lista de convidados para recuperar seus dados dentro do Adapter
                            Convidado convidado = new Convidado();
                            convidado.setIdConvidado(convite.getIdConvidado());
                            convidados.add(convidado);
                        }
                        startListView();
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
}
