package com.sea.lattice.dao.behavior;

import android.content.Context;
import android.database.Cursor;
import android.util.Log;

import com.sea.lattice.dao.LatticePool;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Sea on 9/14/2015.
 */
public class BehaviorPool extends LatticePool {

    public BehaviorPool(Context context) {
        super(context);
        createTable(OriginBehavior.BEHAVIOR_TABLE);
        createTable(CounterBehavior.COUNTER_TABLE);
    }

    private void createTable(String tableName) {
        sqLiteDatabase.execSQL("create table if not exists " + tableName + " (id integer primary key autoincrement, date integer, category integer, content varchar, opp integer)");
    }

    public void save(OriginBehavior behavior) throws Exception {
        sqLiteDatabase.beginTransaction();
        try {
            super.save(behavior);
            sqLiteDatabase.setTransactionSuccessful();
        } catch (Exception ex) {
            throw ex;
        } finally {
            sqLiteDatabase.endTransaction();
        }
    }

    public void save(CounterBehavior cntBehavior) throws Exception {
        sqLiteDatabase.beginTransaction();
        try {
            if (cntBehavior.getOpp() == Behavior.NO_OPP)
                throw new Exception("No origin behavior specified");
            OriginBehavior orgBehavior = (OriginBehavior) getBehaviorById(OriginBehavior.BEHAVIOR_TABLE, cntBehavior.getOpp());
            if (!orgBehavior.isConfront()) {
                orgBehavior.setOpp(cntBehavior.getId());
                super.save(orgBehavior);
                super.save(cntBehavior);
            } else {
                if (orgBehavior.getOpp() != cntBehavior.getId())
                    throw new Exception("The origin behavior already have a counter");
                super.save(cntBehavior);
            }
            sqLiteDatabase.setTransactionSuccessful();
        } catch (Exception ex) {
            throw ex;
        } finally {
            sqLiteDatabase.endTransaction();
        }
    }

    public CounterBehavior confront(OriginBehavior orgBehavior) throws Exception {
        if (orgBehavior.getId() == Behavior.NOT_INSERT)
            throw new Exception("No specified behavior found");
        OriginBehavior tmp = (OriginBehavior) getBehaviorById(orgBehavior.getTableName(), orgBehavior.getId());
        if (!orgBehavior.equals(tmp)) {
            throw new Exception("No specified behavior found");
        }
        switch (orgBehavior.getCategory()) {
            case OriginBehavior.BLACK_BEHAVIOR:
                return new CntBlkBehavior("", orgBehavior.getId());
            case OriginBehavior.WHITE_BEHAVIOR:
                return new CntWhtBehavior("", orgBehavior.getId());
        }
        return null;
    }

    public List<OriginBehavior> getOriginBehavior() {
        List<OriginBehavior> ret = new ArrayList<>();
        sqLiteDatabase.beginTransaction();
        try {
            Cursor cursor = sqLiteDatabase.query(OriginBehavior.BEHAVIOR_TABLE, new String[]{"id", "date", "category", "content", "opp"}, "", new String[]{}, "", "", "");
            if (cursor.moveToFirst()) {
                do {
                    switch (cursor.getInt(2)) {
                        case OriginBehavior.WHITE_BEHAVIOR:
                            ret.add(new OrgWhtBehavior(cursor.getInt(0), new Date(cursor.getLong(1)), cursor.getString(3), cursor.getInt(4)));
                            break;
                        case OriginBehavior.BLACK_BEHAVIOR:
                            ret.add(new OrgBlkBehavior(cursor.getInt(0), new Date(cursor.getLong(1)), cursor.getString(3), cursor.getInt(4)));
                    }
                } while (cursor.moveToNext());
            }
            cursor.close();
            sqLiteDatabase.setTransactionSuccessful();
        } finally {
            sqLiteDatabase.endTransaction();
        }
        return ret;
    }

    public void truncateTable() {
        sqLiteDatabase.beginTransaction();
        try {
            Log.v("delete?", String.valueOf(sqLiteDatabase.delete(OriginBehavior.BEHAVIOR_TABLE, null, null)));
            sqLiteDatabase.delete(CounterBehavior.COUNTER_TABLE, null, null);
            sqLiteDatabase.setTransactionSuccessful();
        } finally {
            sqLiteDatabase.endTransaction();
        }
    }

    private Behavior getBehaviorById(String tableName, int id) throws Exception {
        Cursor cursor = sqLiteDatabase.query(tableName, new String[]{"id", "date", "category", "content", "opp"}, "id = ?", new String[]{String.valueOf(id)}, "", "", "");
        cursor.moveToFirst();
        switch (cursor.getInt(2)) {
            case OriginBehavior.WHITE_BEHAVIOR:
                return new OrgWhtBehavior(cursor.getInt(0), new Date(cursor.getLong(1)), cursor.getString(3), cursor.getInt(4));
            case OriginBehavior.BLACK_BEHAVIOR:
                return new OrgBlkBehavior(cursor.getInt(0), new Date(cursor.getLong(1)), cursor.getString(3), cursor.getInt(4));
            case CounterBehavior.WHITE_COUNTER:
                return new CntWhtBehavior(cursor.getInt(0), new Date(cursor.getLong(1)), cursor.getString(3), cursor.getInt(4));
            case CounterBehavior.BALCK_COUNTER:
                return new CntBlkBehavior(cursor.getInt(0), new Date(cursor.getLong(1)), cursor.getString(3), cursor.getInt(4));
        }
        cursor.close();
        return null;
    }
}