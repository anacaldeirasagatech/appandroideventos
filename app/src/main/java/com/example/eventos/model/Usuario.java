package com.example.eventos.model;

import android.util.Log;

import com.example.eventos.utils.ManagerFirebase;
import com.example.eventos.utils.UserAuthFirebase;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.Exclude;
import com.google.firebase.firestore.FirebaseFirestore;

import java.io.Serializable;

public class Usuario extends UserAuthFirebase implements Serializable {
    private String userId;
    private String nomeUser;
    private String email;
    private String senha;
    private String telefone;

    public Usuario() {
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getNomeUser() {
        return nomeUser;
    }

    public void setNomeUser(String nomeUser) {
        this.nomeUser = nomeUser;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    @Exclude
    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public void salvar()
    {
        FirebaseFirestore firestore = ManagerFirebase.getFireStore();
        firestore.collection("usuario")
                .document(this.getUserId())
                .set(this)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.d("Salvar usuário", "Usuário salvo com sucesso!");
                    }
                });
    }
}
