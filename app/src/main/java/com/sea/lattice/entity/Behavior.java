package com.sea.lattice.entity;

/**
 * Created by Sea on 6/2/2015.
 */
public class Behavior {
    private String bid = FLAG_NOTINSERT;
    private String time;
    private int category;
    private String content;

    final public static int BEHAVIOR_WHITE = 1;
    final public static int BEHAVIOR_BLACK = 2;
    final public static int BEHAVIOR_COUNTERWHITE = 3;
    final public static int BEHAVIOR_COUNTERBLACK = 4;
    final public static String FLAG_NOTINSERT = "not insert yet";

    public Behavior(String time, int category, String content){
        this.time = time;
        this.category = category;
        this.content = content;
    }

    public void setBid(String bid){
        this.bid = bid;
    }

    public void setTime(String time){
        this.time = time;
    }

    public void setCategory(int category){
        this.category = category;
    }

    public void setContent(String content){
        this.content = content;
    }
}