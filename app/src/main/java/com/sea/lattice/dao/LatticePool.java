package com.sea.lattice.dao;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

/**
 * Created by Sea on 9/14/2015.
 */
public abstract class LatticePool {
    protected SQLiteDatabase sqLiteDatabase;
    protected LatticeDB latticeDB;

    public LatticePool(Context context) {
        latticeDB = new LatticeDB(context);
        sqLiteDatabase = latticeDB.getWritableDatabase();
    }

    protected void insert(LatticeObject object) {
        sqLiteDatabase.insert(object.getTableName(), null, object.toContentValues());
        Cursor cursor = sqLiteDatabase.rawQuery("select max(id) from " + object.getTableName(), new String[]{});
        cursor.moveToFirst();
        object.setId(cursor.getInt(0));
    }

    protected void update(LatticeObject object) {
        sqLiteDatabase.update(object.getTableName(), object.toContentValues(), "id = ?", new String[]{String.valueOf(object.getId())});
    }

    protected void save(LatticeObject object) {
        if (object.getId() == LatticeObject.NOT_INSERT) {
            insert(object);
        } else {
            update(object);
        }
    }

    @Override
    public void finalize() {
        sqLiteDatabase.close();
    }
}
