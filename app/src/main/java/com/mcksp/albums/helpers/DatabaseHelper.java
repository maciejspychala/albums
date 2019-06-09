package com.mcksp.albums.helpers;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;

public class DatabaseHelper {
    public static Cursor getAlbumsCursor(Context context, long id) {
        String where = null;
        if (id != -1) {
            where = MediaStore.Audio.Albums._ID + " = " + id;
        }
        ContentResolver cr = context.getContentResolver();
        final Uri uri = MediaStore.Audio.Albums.EXTERNAL_CONTENT_URI;
        final String _id = MediaStore.Audio.Albums._ID;
        final String album_name = MediaStore.Audio.Albums.ALBUM;
        final String artist = MediaStore.Audio.Albums.ARTIST;
        final String albumArt = MediaStore.Audio.Albums.ALBUM_ART;
        final String[] columns = {_id, album_name, artist, albumArt};
        return cr.query(uri, columns, where, null, null);
    }

    public static Cursor getAlbumsCursor(Context context) {
        return getAlbumsCursor(context, -1);
    }
}
