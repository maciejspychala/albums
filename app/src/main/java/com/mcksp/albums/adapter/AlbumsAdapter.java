package com.mcksp.albums.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.mcksp.albums.R;
import com.mcksp.albums.models.Album;

import java.util.ArrayList;

public class AlbumsAdapter extends RecyclerView.Adapter<AlbumsAdapter.AlbumViewHolder> {
    private ArrayList<Album> albums;
    private OnAlbumClick onAlbumClick;

    public AlbumsAdapter(ArrayList<Album> albums, OnAlbumClick onAlbumClick) {
        this.albums = albums;
        this.onAlbumClick = onAlbumClick;
    }

    public ArrayList<Album> getAlbums() {
        return albums;
    }

    @Override
    public AlbumViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.album_item, parent, false);
        return new AlbumViewHolder(v);
    }

    @Override
    public void onBindViewHolder(AlbumViewHolder holder, int position) {
        final Album album = albums.get(position);
        holder.albumTitle.setText(album.title);
        holder.view.setOnClickListener(v -> onAlbumClick.onAlbumClicked(album));

    }

    @Override
    public int getItemCount() {
        return albums.size();
    }

    public static class AlbumViewHolder extends RecyclerView.ViewHolder {
        View view;
        TextView albumTitle;

        public AlbumViewHolder(View itemView) {
            super(itemView);
            view = itemView;
            albumTitle = itemView.findViewById(R.id.album_title);
        }

    }

    public interface OnAlbumClick {
        void onAlbumClicked(Album album);
    }
}
