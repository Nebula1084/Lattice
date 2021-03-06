package com.sea.lattice.dao;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.sea.lattice.content.BehaviorMeta;
import com.sea.lattice.content.DirectoryMeta;
import com.sea.lattice.content.RemindMeta;
import com.sea.lattice.content.TemplateMeta;

/**
 * Created by Sea on 9/13/2015.
 */
public class LatticeDB extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "Lattice.db";
    private static final int DATABASE_VERSION = 18;
    private Context context;
    public static final String ROWID = "rowid";

    public LatticeDB(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL("create table if not exists " + BehaviorMeta.TNAME + " ("
                + BehaviorMeta.ID + " integer primary key autoincrement, "
                + BehaviorMeta.DATE + " integer not null, "
                + BehaviorMeta.CATEGORY + " integer not null, "
                + BehaviorMeta.CONTENT + " varchar not null, "
                + BehaviorMeta.OPP + " integer default -1, "
                + BehaviorMeta.PHOTO + " text, "
                + BehaviorMeta.AUDIO + " text)");

        db.execSQL("create table if not exists " + DirectoryMeta.TNAME + " ("
                + DirectoryMeta.ID + " integer primary key autoincrement, "
                + DirectoryMeta.NAME + " varchar not null)");

        db.execSQL("create table if not exists " + TemplateMeta.TNAME + " ("
                + TemplateMeta.ID + " integer primary key autoincrement, "
                + TemplateMeta.CATEGORY + " integer not null,"
                + TemplateMeta.NAME + " varchar not null, "
                + TemplateMeta.CONTENT + " varchar not null, "
                + TemplateMeta.FREQUENCY + " integer default 0, "
                + TemplateMeta.DIRECTORY + " integer, "
                + BehaviorMeta.PHOTO + " text, "
                + BehaviorMeta.AUDIO + " text, "
                + " foreign key(" + TemplateMeta.ID + ") references " + DirectoryMeta.TNAME + "(" + DirectoryMeta.ID + ") on delete cascade on update cascade)");

        /*
        db.execSQL("create table if not exists " + RemindMeta.TNAME + " ("
                + RemindMeta.ID + " integer primary key autoincrement, "
                + RemindMeta.HOUR + " integer not null, "
                + RemindMeta.MINUTE + " integer not null, "
                + RemindMeta.ONUSE + " integer not null, "
                + RemindMeta.SHOCK + " integer not null, "
                + RemindMeta.REPEAT + " integer not null, "
                + RemindMeta.TAG + " varchar)");*/

    }

    @Override
    public void onOpen(SQLiteDatabase db) {
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.v("execute?", "onUpgrade");
        context.deleteDatabase(DATABASE_NAME);
        //truncateDataBase(db);
    }

    @Override
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        super.onDowngrade(db, oldVersion, newVersion);
        Log.v("execute?", "onDowngrade");
        context.deleteDatabase(DATABASE_NAME);
        //truncateDataBase(db);
    }

    private void truncateDataBase(SQLiteDatabase db) {
        Log.v("execute?", "Yes");

    }

    public void initialize() {
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("delete from " + BehaviorMeta.TNAME);
        db.execSQL("delete from " + DirectoryMeta.TNAME);
        db.execSQL("delete from " + TemplateMeta.TNAME);
        //db.execSQL("update sqlite_sequence set seq=0 where name='"+BehaviorMeta.TNAME+"'");
    }

}
