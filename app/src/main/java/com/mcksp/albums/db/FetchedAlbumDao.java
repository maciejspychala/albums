package com.mcksp.albums.db;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface FetchedAlbumDao {
    @Query("SELECT * FROM FetchedAlbum")
    List<FetchedAlbum> getAll();

    @Insert
    void insertAll(FetchedAlbum... albums);

    @Query("DELETE FROM FetchedAlbum")
    void nukeTable();
}
