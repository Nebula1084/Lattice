package com.sea.lattice.dao;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.sea.lattice.content.BehaviorMeta;
import com.sea.lattice.dao.behavior.CounterBehavior;
import com.sea.lattice.dao.behavior.OriginBehavior;

/**
 * Created by Sea on 9/13/2015.
 */
public class LatticeDB extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "Lattice.db";
    private static final int DATABASE_VERSION = 8;
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
                + BehaviorMeta.CATEGORY + " integer not null,"
                + BehaviorMeta.CONTENT + "content varchar not null,"
                + BehaviorMeta.OPP + "opp integer default -1)");
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
        db.execSQL("drop table if exists " + CounterBehavior.COUNTER_TABLE);
        db.execSQL("drop table if exists " + OriginBehavior.BEHAVIOR_TABLE);
    }

}
