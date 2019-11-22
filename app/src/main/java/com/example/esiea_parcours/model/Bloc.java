package com.example.esiea_parcours.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Bloc {
    @SerializedName("nom")
    private String nom;
    @SerializedName("ratio")
    private int ratio;
    @SerializedName("matières")
    private List<Matiere> matieres;

    public Bloc(String nom, int ratio, List<Matiere> matieres) {
        this.nom = nom;
        this.ratio = ratio;
        this.matieres = matieres;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public int getRatio() {
        return ratio;
    }

    public void setRatio(int ratio) {
        this.ratio = ratio;
    }

    public List<Matiere> getMatieres() {
        return matieres;
    }

    public void setMatieres(List<Matiere> matieres) {
        this.matieres = matieres;
    }
}
