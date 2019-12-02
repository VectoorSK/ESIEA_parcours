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
    @SerializedName("nbh_cours/td")
    private double nbh_cours_td;
    @SerializedName("nbh_cours")
    private double nbh_cours;
    @SerializedName("nbh_td")
    private double nbh_td;
    @SerializedName("nbh_tp")
    private double nbh_tp;
    @SerializedName("nbh_projet")
    private double nbh_projet;
    @SerializedName("nbh_atelier")
    private double nbh_atelier;
    @SerializedName("resume")
    private String resume;
    @SerializedName("contenu")
    private String contenu;
    @SerializedName("objectifs/situations")
    private List<Objectif> objectifs_situations;

    public Matiere(String nom, int semestre, double coeff, double nb_heures, double nbh_cours_td, double nbh_cours, double nbh_td, double nbh_tp, double nbh_projet, double nbh_atelier, String resume, String contenu, List<Objectif> objectifs_situations) {
        this.nom = nom;
        this.semestre = semestre;
        this.coeff = coeff;
        this.nb_heures = nb_heures;
        this.nbh_cours_td = nbh_cours_td;
        this.nbh_cours = nbh_cours;
        this.nbh_td = nbh_td;
        this.nbh_tp = nbh_tp;
        this.nbh_projet = nbh_projet;
        this.nbh_atelier = nbh_atelier;
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

    public double getNbh_cours_td() {
        return nbh_cours_td;
    }

    public double getNbh_cours() {
        return nbh_cours;
    }

    public double getNbh_td() {
        return nbh_td;
    }

    public double getNbh_tp() {
        return nbh_tp;
    }

    public double getNbh_projet() {
        return nbh_projet;
    }

    public double getNbh_atelier() {
        return nbh_atelier;
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

    public void setNbh_cours_td(double nbh_cours_td) {
        this.nbh_cours_td = nbh_cours_td;
    }

    public void setNbh_cours(double nbh_cours) {
        this.nbh_cours = nbh_cours;
    }

    public void setNbh_td(double nbh_td) {
        this.nbh_td = nbh_td;
    }

    public void setNbh_tp(double nbh_tp) {
        this.nbh_tp = nbh_tp;
    }

    public void setNbh_projet(double nbh_projet) {
        this.nbh_projet = nbh_projet;
    }

    public void setNbh_atelier(double nbh_atelier) {
        this.nbh_atelier = nbh_atelier;
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
