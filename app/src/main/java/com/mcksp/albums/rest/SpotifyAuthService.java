package com.mcksp.albums.rest;

import android.content.Context;
import android.widget.Toast;

import com.mcksp.albums.R;
import com.mcksp.albums.rest.models.Token;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class SpotifyAuthService {
    public static String token;

    public static void init(Context context) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://accounts.spotify.com")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        AuthService service = retrofit.create(AuthService.class);
        service.login("client_credentials", "Basic MjJhYmNiZTA4Mzg1NDI2NmFkMWE1MTA3OTI5MjU2NzQ6NWMwMWNjNjAwZjVkNDlmY2FlNWFmMDg5NDFmY2JhMWI=").enqueue(new Callback<Token>() {
            @Override
            public void onResponse(Call<Token> call, Response<Token> response) {
                assert response.body() != null;
                token = response.body().token;
            }

            @Override
            public void onFailure(Call<Token> call, Throwable t) {
                Toast.makeText(context, context.getString(R.string.cannot_connect), Toast.LENGTH_SHORT);
            }
        });
    }
}
