package com.mcksp.albums.fragments;

import android.Manifest;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;

import com.mcksp.albums.R;
import com.mcksp.albums.adapter.AlbumsAdapter;
import com.mcksp.albums.helpers.DatabaseHelper;
import com.mcksp.albums.models.Album;
import com.mcksp.albums.rest.SpotifyAuthService;
import com.mcksp.albums.rest.SpotifyService;
import com.mcksp.albums.rest.models.Record;
import com.mcksp.albums.rest.models.SearchData;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SearchAlbumsFragment extends Fragment implements AlbumsAdapter.OnAlbumClick {

    public static final String TAG = SearchAlbumsFragment.class.getSimpleName();
    private static final String ALBUM_KEY = "ALBUM_KEY";
    private static final int PERMISSION_REQUEST_CODE = 1;
    RecyclerView recyclerView;
    AlbumsAdapter adapter;
    ArrayList<Album> albums = new ArrayList<>();
    ImageView search;
    String url;

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
        search = view.findViewById(R.id.search_button);
        search.setOnClickListener(v -> fetchAlbums(title.getText().toString()));
        fetchAlbums(albumToChange.title);
    }

    private void fetchAlbums(String albumName) {
        SpotifyService.getInstance().getSongs("Bearer " + SpotifyAuthService.token, formatString(albumName), "album", 20, 0).enqueue(new Callback<SearchData>() {
            @Override
            public void onResponse(Call<SearchData> call, Response<SearchData> data) {
                if (data.body() != null && data.body().data.records != null) {
                    albums.clear();
                    for (Record album : data.body().data.records) {
                        Album a = new Album(album.name, album.type, album.uri, album.getImageUrl(), album.getBigImageUrl(), albums.size());
                        albums.add(a);
                    }
                    adapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onFailure(Call<SearchData> call, Throwable t) {

            }
        });
    }
    @Override
    public void onAlbumClicked(Album album) {
        url = album.bigAlbumArt;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (getContext().checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED){
                setAlbumArt();
            } else {
                ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, PERMISSION_REQUEST_CODE);
            }
        } else {
            setAlbumArt();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case PERMISSION_REQUEST_CODE:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    setAlbumArt();
                }
                break;
        }
    }

    private void setAlbumArt() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.dialog_album_cover, null);
        Picasso.get().load(url).into((ImageView) dialogView.findViewById(R.id.cover_art_preview));
        builder.setView(dialogView);
        builder.setPositiveButton("SET", (dialog, which) -> {
            Picasso.get()
                    .load(url)
                    .into(new DownloadTarget());
            dialog.dismiss();
        });
        builder.setNegativeButton("CANCEL", null);
        builder.show();
    }

    private static String formatString(String str) {
        str = str.replaceAll("\\(.*?\\) ?", "");
        str = str.replaceAll("\\[.*?\\] ?", "");
        str = str.replaceAll("\\<.*?\\> ?", "");
        return str.trim();
    }

    private class DownloadTarget implements Target {

        @Override
        public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
            DatabaseHelper.setAlbumCover(getContext(), bitmap, albumToChange);
            getActivity().getSupportFragmentManager().popBackStack();
        }

        @Override
        public void onBitmapFailed(Exception e, Drawable errorDrawable) {

        }

        @Override
        public void onPrepareLoad(Drawable placeHolderDrawable) {

        }
    }
}
