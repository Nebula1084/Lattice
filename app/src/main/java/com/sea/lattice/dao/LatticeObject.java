package com.sea.lattice.dao;

import android.content.ContentValues;

/**
 * Created by Sea on 9/13/2015.
 */
public abstract class LatticeObject {
    protected String TABLE_NAME;
    protected int id;

    public final static int NOT_INSERT = -1;

    public String getTableName() {
        return TABLE_NAME;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    abstract public ContentValues toContentValues();
}
