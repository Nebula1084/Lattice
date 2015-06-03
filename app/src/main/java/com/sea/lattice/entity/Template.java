package com.sea.lattice.entity;

/**
 * Created by Sea on 6/2/2015.
 */
public class Template {
    private String tid = FLAG_NOTINSERT;
    private String tname;
    private int category;
    private String content;

    final public static String FLAG_NOTINSERT = "not insert yet";

    public Template(String tname, int category, String content){
        this.tname = tname;
        this.category = category;
        this.content = content;
    }

    public void setTid(String tid){
        this.tid = tid;
    }

    public void setTname(String tname){
        this.tname = tname;
    }

    public void setCategory(int category){
        this.category = category;
    }

    public void setContent(String content){
        this.content = content;
    }
}