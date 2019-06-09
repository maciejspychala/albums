package com.mcksp.albums.browseAlbums;

import android.database.Cursor;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mcksp.albums.R;
import com.mcksp.albums.helpers.DatabaseHelper;
import com.mcksp.albums.models.Album;

import java.util.ArrayList;


public class BrowseAlbumsFragment extends Fragment {

    private ArrayList<Album> albums = new ArrayList<>();

    public BrowseAlbumsFragment() {
    }

    public static BrowseAlbumsFragment newInstance() {
        BrowseAlbumsFragment fragment = new BrowseAlbumsFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        readAlbumsData();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_browse_albums, container, false);
    }

    private void readAlbumsData() {
        Cursor cursor = DatabaseHelper.getAlbumsCursor(getContext());
        saveAlbumsToArray(cursor);
    }

    private void saveAlbumsToArray(Cursor cursor) {
        if (cursor.moveToFirst()) {
            do {
                String artist = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Albums.ARTIST));
                String nameAlbum = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Albums.ALBUM));
                String albumArt = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Albums.ALBUM_ART));
                long id = cursor.getLong(cursor.getColumnIndex(MediaStore.Audio.Albums._ID));
                Album album = new Album(nameAlbum, artist, albumArt, "file://" + albumArt, id);
                albums.add(album);
            } while (cursor.moveToNext());
        }
    }
}
