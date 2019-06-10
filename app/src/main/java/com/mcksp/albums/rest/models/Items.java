package com.mcksp.albums.rest.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class Items {
    @SerializedName("items")
    @Expose
    public List<Record> records = new ArrayList<>();
}
