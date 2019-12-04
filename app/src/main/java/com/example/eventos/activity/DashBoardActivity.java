package com.example.eventos.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.eventos.R;
import com.example.eventos.adapter.AdapterListEventos;
import com.example.eventos.model.Evento;
import com.example.eventos.model.Usuario;
import com.example.eventos.utils.ManagerFirebase;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class DashBoardActivity extends AppCompatActivity {

    private List<Evento> eventos;
    private ListView listViewEventos;
    private FirebaseFirestore database;
    private CollectionReference eventosRef;
    private AdapterListEventos adapterListEventos;
    private FloatingActionButton btnNovoEvento;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dash_board);
        database = ManagerFirebase.getFireStore();
        listViewEventos = (ListView) findViewById(R.id.lista_eventos_usuario);

        btnNovoEvento = findViewById(R.id.btnAddEvento);
        btnNovoEvento.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), CadastroEventoActivity.class);
                startActivity(i);
            }
        });
        carregarEventosUsuario();
    }

    private void carregarEventosUsuario() {
        eventos = new ArrayList<>();
        eventosRef = database.collection("evento");
        eventosRef
                .whereEqualTo("userId", Usuario.getUserID())
                .orderBy("data", Query.Direction.DESCENDING)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            eventos.clear();
                            for (DocumentSnapshot ds : task.getResult()) {
                                Evento evento = ds.toObject(Evento.class);
                                eventos.add(evento);
                            }
                            startListView();
                        } else {
                            Log.d("ErroEventoList", "Error getting documents: ", task.getException());
                        }
                    }
                });
        eventosRef
                .whereEqualTo("userId", Usuario.getUserID())
                .orderBy("data", Query.Direction.DESCENDING)
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(QuerySnapshot documentSnapshots, FirebaseFirestoreException e) {
                        if (e != null) {
                            return;
                        }
                        eventos = documentSnapshots.toObjects(Evento.class);
                        startListView();
                    }
                });
    }

    private void startListView() {
        try {
            adapterListEventos = new AdapterListEventos(getApplicationContext(), R.layout.listview_eventos_usuario, eventos);
            listViewEventos.setAdapter(adapterListEventos);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
