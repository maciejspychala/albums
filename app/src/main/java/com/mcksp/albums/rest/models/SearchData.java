package com.mcksp.albums.rest.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SearchData {
    @SerializedName("albums")
    @Expose
    public Items data;
}
