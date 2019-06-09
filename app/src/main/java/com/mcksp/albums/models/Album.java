package com.mcksp.albums.models;


public class Album {
    public String title;
    public String author;
    public String year;
    public String albumArt;
    public String bigAlbumArt;
    public long id;

    public Album(String title, String author, String year, String albumArt, long id) {
        this.title = title;
        this.author = author;
        this.year = year;
        this.albumArt = albumArt;
        this.id = id;
    }

    public Album(String title, String author, String year, String albumArt, String bigAlbumArt, long id) {
        this.title = title;
        this.author = author;
        this.year = year;
        this.albumArt = albumArt;
        this.bigAlbumArt = bigAlbumArt;
        this.id = id;
    }
}
