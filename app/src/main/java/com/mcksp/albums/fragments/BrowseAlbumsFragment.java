package com.mcksp.albums.fragments;

import android.database.Cursor;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import com.mcksp.albums.R;
import com.mcksp.albums.adapter.AlbumsAdapter;
import com.mcksp.albums.db.AppDatabase;
import com.mcksp.albums.db.FetchedAlbum;
import com.mcksp.albums.helpers.DatabaseHelper;
import com.mcksp.albums.models.Album;

import java.util.ArrayList;
import java.util.List;


public class BrowseAlbumsFragment extends Fragment implements AlbumsAdapter.OnAlbumClick {

    private ArrayList<Album> albums = new ArrayList<>();
    private ArrayList<Album> displayedAlbums = new ArrayList<>();

    private Album choosedAlbum;
    private RecyclerView recyclerView;
    AppDatabase db;
    List<FetchedAlbum> fetchedAlbums;
    Button filter;
    AlbumsAdapter adapter;
    boolean filtering = false;

    public static BrowseAlbumsFragment newInstance() {
        BrowseAlbumsFragment fragment = new BrowseAlbumsFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        db = Room.databaseBuilder(getContext(),
                AppDatabase.class, "fetched_album").allowMainThreadQueries().build();
        db.albumDao().nukeTable();
        fetchedAlbums = db.albumDao().getAll();
        readAlbumsData();
    }

    @Override
    public void onResume() {
        super.onResume();
        refreshChoosedAlbum();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_browse_albums, container, false);
    }

    private void refreshChoosedAlbum() {
        if (choosedAlbum != null) {
            Cursor cursor = DatabaseHelper.getAlbumsCursor(getContext(), choosedAlbum.id);
            if (cursor.moveToFirst()) {
                String albumArt = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Albums.ALBUM_ART));
                String newAlbumArt = "file://" + albumArt;
                if (!newAlbumArt.equals(choosedAlbum.albumArt)) {
                    FetchedAlbum f = new FetchedAlbum();
                    f.id = choosedAlbum.id;
                    if (!isChanged(choosedAlbum)) {
                        db.albumDao().insertAll(f);
                        fetchedAlbums.add(f);
                    }
                }
                choosedAlbum.albumArt = newAlbumArt;
                recyclerView.getAdapter().notifyDataSetChanged();
            }
        }
        choosedAlbum = null;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        recyclerView = view.findViewById(R.id.albums_list);
        recyclerView.setHasFixedSize(true);
        adapter = new AlbumsAdapter(albums, this);
        recyclerView.setAdapter(adapter);
        filter = view.findViewById(R.id.filter);
        filter.setText(filtering ? getText(R.string.display_all) : getText(R.string.filter));
        filter.setOnClickListener(e -> {
            filtering = !filtering;
            filter.setText(filtering ? getText(R.string.display_all) : getText(R.string.filter));
            filterAlbums();
        });
    }

    private boolean isChanged(Album album) {
        boolean changed = false;
        for (int i = 0; i < fetchedAlbums.size(); i++) {
            if (album.id == fetchedAlbums.get(i).id) {
                changed = true;
                break;
            }
        }
        return changed;
    }


    private void filterAlbums() {
        displayedAlbums.clear();
        for (int i = 0; i < albums.size(); i++) {
            if (filtering) {
                if (!isChanged(albums.get(i))) {
                    displayedAlbums.add(albums.get(i));
                }
            } else {
                displayedAlbums.add(albums.get(i));
            }
        }
        recyclerView.setAdapter(new AlbumsAdapter(displayedAlbums, this));
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
        choosedAlbum = album;

        SearchAlbumsFragment fragment = SearchAlbumsFragment.newInstance(album);
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_container, fragment);
        transaction.addToBackStack(SearchAlbumsFragment.TAG);
        transaction.commit();
    }
}
