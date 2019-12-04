package com.example.esiea_parcours.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Matiere {
    @SerializedName("nom")
    private String nom;
    @SerializedName("semestre")
    private int semestre;
    @SerializedName("coeff")
    private double coeff;
    @SerializedName("nb_heures")
    private double nb_heures;
    @SerializedName("repartition")
    private double[] repartition;
    @SerializedName("supports")
    private String[] supports;
    @SerializedName("resume")
    private String resume;
    @SerializedName("contenu")
    private String contenu;
    @SerializedName("objectifs/situations")
    private List<Objectif> objectifs_situations;

    public Matiere(String nom, int semestre, double coeff, double nb_heures, double[] repartition, String[] supports, String resume, String contenu, List<Objectif> objectifs_situations) {
        this.nom = nom;
        this.semestre = semestre;
        this.coeff = coeff;
        this.nb_heures = nb_heures;
        this.repartition = repartition;
        this.supports = supports;
        this.resume = resume;
        this.contenu = contenu;
        this.objectifs_situations = objectifs_situations;
    }

    public String getNom() {
        return nom;
    }

    public int getSemestre() {
        return semestre;
    }

    public double getCoeff() {
        return coeff;
    }

    public double getNb_heures() {
        return nb_heures;
    }

    public double[] getRepartition() {
        return repartition;
    }

    public String[] getSupports() {
        return supports;
    }

    public String getResume() {
        return resume;
    }

    public String getContenu() {
        return contenu;
    }

    public List<Objectif> getObjectifs_situations() {
        return objectifs_situations;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public void setSemestre(int semestre) {
        this.semestre = semestre;
    }

    public void setCoeff(double coeff) {
        this.coeff = coeff;
    }

    public void setNb_heures(double nb_heures) {
        this.nb_heures = nb_heures;
    }

    public void setRepartition(double[] repartition) {
        this.repartition = repartition;
    }

    public void setSupports(String[] supports) {
        this.supports = supports;
    }

    public void setResume(String resume) {
        this.resume = resume;
    }

    public void setContenu(String contenu) {
        this.contenu = contenu;
    }

    public void setObjectifs_situations(List<Objectif> objectifs_situations) {
        this.objectifs_situations = objectifs_situations;
    }
}
