package com.mcksp.albums.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.mcksp.albums.R;
import com.mcksp.albums.adapter.AlbumsAdapter;
import com.mcksp.albums.models.Album;

public class SearchAlbumsFragment extends Fragment implements AlbumsAdapter.OnAlbumClick {

    public static final String TAG = SearchAlbumsFragment.class.getSimpleName();
    private static final String ALBUM_KEY = "ALBUM_KEY";

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
        TextView title = view.findViewById(R.id.search_title);
        title.setText(albumToChange.title);
    }

    @Override
    public void onAlbumClicked(Album album) {
        Log.d("SEARCH", album.title);
    }
}
