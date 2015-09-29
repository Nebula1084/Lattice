package com.sea.lattice.content;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.text.TextUtils;
import android.util.Log;

import com.sea.lattice.dao.LatticeDB;
import com.sea.lattice.dao.behavior.OriginBehavior;

/**
 * Created by Sea on 9/24/2015.
 */
public class BehaviorProvider extends ContentProvider {

    LatticeDB lDB;
    SQLiteDatabase db;

    private static final UriMatcher uriMatcher;

    static {
        uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
        uriMatcher.addURI(BehaviorMeta.AUTOHORITY, BehaviorMeta.TNAME, BehaviorMeta.ITEM);
        uriMatcher.addURI(BehaviorMeta.AUTOHORITY, BehaviorMeta.TNAME + "/#", BehaviorMeta.ITEM_ID);
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
        String id;
        switch (uriMatcher.match(uri)) {
            case BehaviorMeta.ITEM:
                //c = db.query(BehaviorMeta.TNAME, projection, selection, selectionArgs, null, null, null);
                c = db.query(BehaviorMeta.TNAME, projection, selection, selectionArgs, "", "", sortOrder);
                break;
            case BehaviorMeta.ITEM_ID:
                id = uri.getPathSegments().get(1);
                c = db.query(BehaviorMeta.TNAME, projection, BehaviorMeta.ID + "=" + id + (!TextUtils.isEmpty(selection) ? "AND(" + selection + ')' : ""), selectionArgs, null, null, sortOrder);
                break;
            default:
                Log.d("!!!!!!", "Unknown URI" + uri);
                throw new IllegalArgumentException("Unknown URI" + uri);
        }
        return c;
    }

    @Override
    public String getType(Uri uri) {
        switch (uriMatcher.match(uri)) {
            case BehaviorMeta.ITEM:
                return BehaviorMeta.CONTENT_TYPE;
            case BehaviorMeta.ITEM_ID:
                return BehaviorMeta.CONTENT_ITEM_TYPE;
        }
        return null;
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {
        long rowId;
        db = lDB.getWritableDatabase();
        rowId = db.insert(BehaviorMeta.TNAME, BehaviorMeta.ID, values);
        if (rowId > 0) {
            Uri noteUri = ContentUris.withAppendedId(BehaviorMeta.CONTENT_URI, rowId);
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
            case BehaviorMeta.ITEM:
                count = db.delete(BehaviorMeta.TNAME, selection, selectionArgs);
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
            case BehaviorMeta.ITEM:
                db.update(BehaviorMeta.TNAME, values, selection, selectionArgs);
                break;
            default:
                throw new IllegalArgumentException("Unknown URI" + uri);
        }
        return 0;
    }

}
