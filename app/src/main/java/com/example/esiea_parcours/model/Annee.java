package com.example.esiea_parcours.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Annee {
    @SerializedName("année")
    private int annee;
    @SerializedName("blocs")
    private List<Bloc> blocs;

    public Annee(int annee, List<Bloc> blocs) {
        this.annee = annee;
        this.blocs = blocs;
    }

    public int getAnnee() {
        return annee;
    }

    public void setAnnee(int annee) {
        this.annee = annee;
    }

    public List<Bloc> getBlocs() {
        return blocs;
    }

    public void setBlocs(List<Bloc> blocs) {
        this.blocs = blocs;
    }
}
