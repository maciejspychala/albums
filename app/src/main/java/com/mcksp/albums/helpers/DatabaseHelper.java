package com.mcksp.albums.helpers;

import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;

import com.mcksp.albums.models.Album;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Calendar;

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

    public static void setAlbumCover(Context context, Bitmap bitmap, Album album) {
        Uri albumArtUri = Uri.parse("content://media/external/audio/albumart");

        int lol = context.getContentResolver().delete(ContentUris.withAppendedId(albumArtUri, album.id), null, null);
        Log.d("DELETED", String.valueOf(lol));


        String dirName = Environment.getExternalStorageDirectory().getPath() + "/albumthumbs/";
        String filename = dirName + Calendar.getInstance().getTimeInMillis();
        File dir = new File(dirName);
        if (!dir.exists()) {
            dir.mkdirs();
        }
        File file = new File(filename);
        FileOutputStream out = null;
        try {
            out = new FileOutputStream(file);
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, out);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (out != null) {
                    out.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }


        ContentValues values = new ContentValues();
        values.put("album_id", album.id);
        values.put("_data", filename);

        Uri num_updates = context.getContentResolver().insert(albumArtUri, values);
        Log.d("NUM UPDATES", num_updates.toString());
    }
}
