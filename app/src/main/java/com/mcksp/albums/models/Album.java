package com.mcksp.albums.models;


import android.os.Parcel;
import android.os.Parcelable;

public class Album implements Parcelable {
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

    protected Album(Parcel in) {
        title = in.readString();
        author = in.readString();
        year = in.readString();
        albumArt = in.readString();
        id = in.readLong();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(title);
        dest.writeString(author);
        dest.writeString(year);
        dest.writeString(albumArt);
        dest.writeLong(id);
    }
}
