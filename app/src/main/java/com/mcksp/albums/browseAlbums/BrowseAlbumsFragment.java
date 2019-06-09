package com.mcksp.albums.browseAlbums;

import android.database.Cursor;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mcksp.albums.R;
import com.mcksp.albums.adapter.AlbumsAdapter;
import com.mcksp.albums.helpers.DatabaseHelper;
import com.mcksp.albums.models.Album;

import java.util.ArrayList;


public class BrowseAlbumsFragment extends Fragment implements AlbumsAdapter.OnAlbumClick {

    private ArrayList<Album> albums = new ArrayList<>();
    private RecyclerView recyclerView;

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

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        recyclerView = view.findViewById(R.id.albums_list);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 1));
        recyclerView.setAdapter(new AlbumsAdapter(albums, this));
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

    @Override
    public void onAlbumClicked(Album album) {
        Log.d("ALBUM", album.title);
    }
}
