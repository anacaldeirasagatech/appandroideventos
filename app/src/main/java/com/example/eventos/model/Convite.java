package com.example.eventos.model;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

import com.example.eventos.utils.ManagerFirebase;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.Exclude;
import com.google.firebase.firestore.FirebaseFirestore;

import java.io.Serializable;
import java.lang.annotation.Documented;
import java.util.UUID;

public class Convite implements Parcelable, Serializable {
    private String idConvite;
    private String idConvidado;
    private String idEvento;
    private String confirmacao;
    private String dataConfirmacao;

    public Convite() {
    }

    public String getIdConvite() {
        return idConvite;
    }

    public void setIdConvite(String idConvite) {
        this.idConvite = idConvite;
    }

    public String getIdConvidado() {
        return idConvidado;
    }

    public void setIdConvidado(String idConvidado) {
        this.idConvidado = idConvidado;
    }

    public String getIdEvento() {
        return idEvento;
    }

    public void setIdEvento(String idEvento) {
        this.idEvento = idEvento;
    }

    public String getConfirmacao() {
        return confirmacao;
    }

    public void setConfirmacao(String confirmacao) {
        this.confirmacao = confirmacao;
    }

    public String getDataConfirmacao() {
        return dataConfirmacao;
    }

    public void setDataConfirmacao(String dataConfirmacao) {
        this.dataConfirmacao = dataConfirmacao;
    }

    public void salvar() {
                FirebaseFirestore firestore = ManagerFirebase.getFireStore();
        firestore
                .collection("convite")
                .document(getIdConvite())
                .set(this);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {

    }
}
