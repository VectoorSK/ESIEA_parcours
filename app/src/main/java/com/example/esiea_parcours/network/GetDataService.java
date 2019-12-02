package com.example.esiea_parcours.network;

import com.example.esiea_parcours.model.Annee;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface GetDataService {

    @GET("/VectoorSK/ESIEA_API/master/cursus.json")
    Call<List<Annee>> getAllYears();
}
