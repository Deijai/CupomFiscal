package com.dev.deijai.cupomfiscal.model;

import androidx.annotation.NonNull;

import java.io.Serializable;

public class CupomFiscal implements Serializable {

    private Integer codfilial;
    private Integer numseq;
    private String numnota;
    private Integer numcaixa;
    private String descricaopaf;
    private String codauxiliar;
    private String codfunc;
    private String unidade;
    public CupomFiscal(){}

    public String getCodauxiliar() {
        return codauxiliar;
    }

    public void setCodauxiliar(String codauxiliar) {
        this.codauxiliar = codauxiliar;
    }

    public String getCodfunc() {
        return codfunc;
    }

    public String getUnidade() {
        return unidade;
    }

    public void setUnidade(String unidade) {
        this.unidade = unidade;
    }

    public void setCodfunc(String codfunc) {
        this.codfunc = codfunc;
    }

    public CupomFiscal(Integer codfilial, Integer numseq, String numnota, Integer numcaixa, String descricaopaf, String codauxiliar, String codfunc, String unidade) {
        this.codfilial = codfilial;
        this.numseq = numseq;
        this.numnota = numnota;
        this.numcaixa = numcaixa;
        this.descricaopaf = descricaopaf;
        this.codauxiliar = codauxiliar;
        this.codfunc = codfunc;
        this.unidade = unidade;
    }

    public Integer getCodfilial() {
        return codfilial;
    }

    public void setCodfilial(Integer codfilial) {
        this.codfilial = codfilial;
    }

    public Integer getNumseq() {
        return numseq;
    }

    public void setNumseq(Integer numseq) {
        this.numseq = numseq;
    }

    public String getNumnota() {
        return numnota;
    }

    public void setNumnota(String numnota) {
        this.numnota = numnota;
    }

    public Integer getNumcaixa() {
        return numcaixa;
    }

    public void setNumcaixa(Integer numcaixa) {
        this.numcaixa = numcaixa;
    }

    public String getDescricaopaf() {
        return descricaopaf;
    }

    public void setDescricaopaf(String descricaopaf) {
        this.descricaopaf = descricaopaf;
    }

    @Override
    public String toString() {
        return "CupomFiscal{" +
                "codfilial=" + codfilial +
                ", numseq=" + numseq +
                ", numnota='" + numnota + '\'' +
                ", numcaixa=" + numcaixa +
                ", descricaopaf='" + descricaopaf + '\'' +
                ", codauxiliar='" + codauxiliar + '\'' +
                '}';
    }
}
