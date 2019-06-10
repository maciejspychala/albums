package com.mcksp.albums.rest;

import com.mcksp.albums.rest.models.SearchData;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Query;

public interface ApiService {
    @GET("search")
    Call<SearchData> getSongs(@Header("Authorization") String token, @Query("q") String name, @Query("type") String type, @Query("limit") int limit, @Query("offset") int offset);
}