package com.example.eventos.model;

public class Convidado {
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
}
