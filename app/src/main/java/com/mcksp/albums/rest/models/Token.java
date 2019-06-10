package com.mcksp.albums.rest.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Token {
    @SerializedName("access_token")
    @Expose
    public String token;
}
