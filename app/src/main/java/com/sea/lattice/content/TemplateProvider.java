package com.sea.lattice.content;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.util.Log;

import com.sea.lattice.dao.LatticeDB;

/**
 * Created by Sea on 9/30/2015.
 */
public class TemplateProvider extends ContentProvider{
    LatticeDB lDB;
    SQLiteDatabase db;

    private static final UriMatcher uriMatcher;

    static {
        uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
        uriMatcher.addURI(TemplateMeta.AUTOHORITY, TemplateMeta.TNAME, TemplateMeta.ITEM);
    }

    @Override
    public boolean onCreate() {
        this.lDB = new LatticeDB(this.getContext());
        return true;
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        db = lDB.getWritableDatabase();
        Cursor c;
        switch (uriMatcher.match(uri)){
            case TemplateMeta.ITEM:
                c = db.query(TemplateMeta.TNAME, projection, selection, selectionArgs, "", "", sortOrder);
                break;
            default:
                Log.d("!!!!!!", "Unknown URI" + uri);
                throw new IllegalArgumentException("Unknown URI" + uri);
        }
        return c;
    }

    @Override
    public String getType(Uri uri) {
        switch (uriMatcher.match(uri)){
            case DirectoryMeta.ITEM:
                return DirectoryMeta.CONTENT_TYPE;
        }
        return null;
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {
        long rowId;
        db = lDB.getWritableDatabase();
        rowId = db.insert(TemplateMeta.TNAME, TemplateMeta.ID, values);
        if (rowId>0){
            Uri noteUri = ContentUris.withAppendedId(TemplateMeta.CONTENT_URI, rowId);
            getContext().getContentResolver().notifyChange(noteUri, null);
            return noteUri;
        }
        throw new IllegalArgumentException("Unknown URI" + uri);
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        db = lDB.getWritableDatabase();
        int count;
        switch (uriMatcher.match(uri)) {
            case TemplateMeta.ITEM:
                count = db.delete(TemplateMeta.TNAME, selection, selectionArgs);
                break;
            default:
                throw new IllegalArgumentException("Unknown URI" + uri);
        }
        getContext().getContentResolver().notifyChange(uri, null);
        return count;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        db = lDB.getWritableDatabase();
        switch (uriMatcher.match(uri)) {
            case TemplateMeta.ITEM:
                db.update(TemplateMeta.TNAME, values, selection, selectionArgs);
                break;
            default:
                throw new IllegalArgumentException("Unknown URI" + uri);
        }
        getContext().getContentResolver().notifyChange(uri, null);
        return 0;
    }
}
