package com.mcksp.albums.browseAlbums;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mcksp.albums.R;


public class BrowseAlbumsFragment extends Fragment {

    public BrowseAlbumsFragment() {
    }

    public static BrowseAlbumsFragment newInstance() {
        BrowseAlbumsFragment fragment = new BrowseAlbumsFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_browse_albums, container, false);
    }
}
