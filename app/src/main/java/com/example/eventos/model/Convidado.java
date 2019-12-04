package com.example.eventos.model;

import androidx.annotation.NonNull;

import com.example.eventos.utils.ManagerFirebase;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FirebaseFirestore;

import java.io.Serializable;
import java.util.UUID;

public class Convidado implements Serializable {
    private String idConvidado;
    private String nome;
    private String telefone;
    private String email;

    public Convidado() {
    }

    public String getIdConvidado() {
        return idConvidado;
    }

    public void setIdConvidado(String idConvidado) {
        this.idConvidado = idConvidado;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void salvar(final String idEvento) {
        FirebaseFirestore firestore = ManagerFirebase.getFireStore();
        firestore
                .collection("animal")
                .document(getIdConvidado())
                .set(this)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Convite convite = new Convite();
                        convite.setIdEvento(idEvento);
                        convite.setIdConvidado(getIdConvidado());
                        convite.setIdConvite(UUID.randomUUID().toString());
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        deletar();
                    }
                });
    }

    public void deletar() {
        FirebaseFirestore firestore = ManagerFirebase.getFireStore();
        firestore
                .collection("convidado")
                .document(getIdConvidado())
                .delete();
    }
}
