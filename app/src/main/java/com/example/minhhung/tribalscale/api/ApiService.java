package com.example.minhhung.tribalscale.api;

import com.example.minhhung.tribalscale.model.ResultsList;

import java.util.Map;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.QueryMap;

/**
 * Created by minhhung on 2/5/2017.
 */
public interface ApiService {

    @GET("/api")
    Call<ResultsList> getRandomJSON(@QueryMap Map<String, String> params);
}