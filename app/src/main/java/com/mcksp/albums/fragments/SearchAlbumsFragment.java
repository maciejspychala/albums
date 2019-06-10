package com.mcksp.albums.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.mcksp.albums.R;
import com.mcksp.albums.adapter.AlbumsAdapter;
import com.mcksp.albums.models.Album;
import com.mcksp.albums.rest.SpotifyAuthService;
import com.mcksp.albums.rest.SpotifyService;
import com.mcksp.albums.rest.models.SearchData;

import java.io.IOException;
import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SearchAlbumsFragment extends Fragment implements AlbumsAdapter.OnAlbumClick {

    public static final String TAG = SearchAlbumsFragment.class.getSimpleName();
    private static final String ALBUM_KEY = "ALBUM_KEY";
    RecyclerView recyclerView;
    AlbumsAdapter adapter;
    ArrayList<Album> albums = new ArrayList<>();

    private Album albumToChange;

    public static SearchAlbumsFragment newInstance(Album album) {
        Bundle args = new Bundle();
        args.putParcelable(ALBUM_KEY, album);
        SearchAlbumsFragment fragment = new SearchAlbumsFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (getArguments() != null && getArguments().containsKey(ALBUM_KEY)){
            albumToChange = getArguments().getParcelable(ALBUM_KEY);
        }
        return inflater.inflate(R.layout.fragment_search_albums, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        EditText title = view.findViewById(R.id.search_text);
        title.setText(formatString(albumToChange.title));
        adapter = new AlbumsAdapter(albums, this);
        recyclerView = view.findViewById(R.id.album_search_list);
        recyclerView.setAdapter(adapter);
        recyclerView.setHasFixedSize(true);
        SpotifyService.getInstance().getSongs("Bearer " + SpotifyAuthService.token, formatString(albumToChange.title), "album", 20, 0).enqueue(new Callback<SearchData>() {
            @Override
            public void onResponse(Call<SearchData> call, Response<SearchData> response) {
                Log.d("seidwa", response.body().data.records.get(0).getBigImageUrl());
            }

            @Override
            public void onFailure(Call<SearchData> call, Throwable t) {

            }
        });
    }

    @Override
    public void onAlbumClicked(Album album) {
        Log.d("SEARCH", album.title);
    }

    private static String formatString(String str) {
        str = str.replaceAll("\\(.*?\\) ?", "");
        str = str.replaceAll("\\[.*?\\] ?", "");
        str = str.replaceAll("\\<.*?\\> ?", "");
        return str.trim();
    }
}
