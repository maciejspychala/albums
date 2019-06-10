package com.mcksp.albums.rest;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class SpotifyService {
    private static ApiService service;

    public static ApiService getInstance() {
        if (service == null) {
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl("https://api.spotify.com/v1/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
            service = retrofit.create(ApiService.class);
        }
        return service;
    }
}