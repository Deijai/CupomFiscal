package com.dev.deijai.cupomfiscal.model;

import java.io.Serializable;

public class Login implements Serializable {

    private Long ID;
    private String matricula;
    private String senha;

    public Login(){}

    public Long getID() {
        return ID;
    }

    public void setID(Long ID) {
        this.ID = ID;
    }

    public String getMatricula() {
        return matricula;
    }

    public void setMatricula(String matricula) {
        this.matricula = matricula;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }
}
