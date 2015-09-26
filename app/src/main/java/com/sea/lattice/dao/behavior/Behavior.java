package com.sea.lattice.dao.behavior;

import android.content.ContentValues;

import com.sea.lattice.dao.LatticeObject;

import java.util.Date;

/**
 * Created by Sea on 9/14/2015.
 */
public abstract class Behavior extends LatticeObject {
    protected Date date;
    protected int category;
    protected String content;
    protected int opp;

    final public static int NO_OPP = -2;

    public Behavior(int category, String content) {
        this._id = NOT_INSERT;
        this.date = new Date();
        this.category = category;
        this.content = content;
        this.opp = NO_OPP;
    }

    public Behavior(int category, String content, int opp) {
        this._id = NOT_INSERT;
        this.date = new Date();
        this.category = category;
        this.content = content;
        this.opp = opp;
    }

    public Behavior(int id, Date date, int category, String content, int opp) {
        this._id = id;
        this.date = date;
        this.category = category;
        this.content = content;
        this.opp = opp;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public int getCategory() {
        return category;
    }

    public void setCategory(int category) {
        this.category = category;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getOpp() {
        return opp;
    }

    public void setOpp(int opp) {
        this.opp = opp;
    }

    @Override
    public ContentValues toContentValues() {
        ContentValues cv = new ContentValues();
        cv.put("date", date.getTime());
        cv.put("category", category);
        cv.put("content", content);
        cv.put("opp", opp);
        return cv;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("_id:" + _id + "\n");
        sb.append("date:" + date.getTime() + "\n");
        sb.append("category:" + category + "\n");
        sb.append("content:" + content + "\n");
        sb.append("opp:" + opp + "\n");
        return sb.toString();
    }

    public boolean equals(Behavior b) {
        if (this._id != b._id) return false;
        if (!this.date.equals(b.date)) return false;
        if (this.category != b.category) return false;
        if (!this.content.equals(b.content)) return false;
        if (this.opp != b.opp) return false;
        return true;
    }

}
