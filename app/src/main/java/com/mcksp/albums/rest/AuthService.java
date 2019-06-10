package com.mcksp.albums.rest;

import com.mcksp.albums.rest.models.Token;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Header;
import retrofit2.http.POST;

public interface AuthService {
    @FormUrlEncoded
    @POST("/api/token")
    Call<Token> login(@Field("grant_type") String grantType, @Header("Authorization") String authorization);
}
