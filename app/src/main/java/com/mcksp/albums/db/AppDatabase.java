package com.mcksp.albums.db;

import androidx.room.RoomDatabase;
import androidx.room.Database;

@Database(entities = {FetchedAlbum.class}, version = 1, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {
    public abstract FetchedAlbumDao albumDao();
}
