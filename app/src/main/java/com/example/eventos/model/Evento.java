package com.example.eventos.model;


import android.widget.Toast;

import androidx.annotation.NonNull;

import com.example.eventos.activity.CadastroEventoActivity;
import com.example.eventos.utils.ManagerFirebase;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.io.Serializable;
import java.util.UUID;

public class Evento implements Serializable {
    private String idEvento;
    private String nomeEvento;
    private String local;
    private String data;
    private String descricao;
    private String userId;

    public Evento() {
    }

    public String getIdEvento() {
        return idEvento;
    }

    public void setIdEvento(String idEvento) {
        this.idEvento = idEvento;
    }

    public String getNomeEvento() {
        return nomeEvento;
    }

    public void setNomeEvento(String nomeEvento) {
        this.nomeEvento = nomeEvento;
    }

    public String getLocal() {
        return local;
    }

    public void setLocal(String local) {
        this.local = local;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public void deletar() {
        FirebaseFirestore firestore = ManagerFirebase.getFireStore();
        firestore
                .collection("evento")
                .document(getIdEvento())
                .delete();
    }
}
