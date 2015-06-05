package com.sea.lattice.entity;

/**
 * Created by Sea on 6/2/2015.
 */
public class Behavior implements Operation{
    private String bid = FLAG_NOTINSERT;
    private String date;
    private String time;
    private int category;
    private String content;

    final public static int BEHAVIOR_WHITE = 1;
    final public static int BEHAVIOR_BLACK = 2;
    final public static int BEHAVIOR_COUNTERWHITE = 3;
    final public static int BEHAVIOR_COUNTERBLACK = 4;
    final public static String FLAG_NOTINSERT = "not insert yet";

    public Behavior(String date, String time, int category, String content){
        this.date = date;
        this.time = time;
        this.category = category;
        this.content = content;
    }

    public Behavior(String bid, String date, String time, int category, String content){
        this.date = date;
        this.bid = bid;
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

    @Override
    public int getOpcode() {
        return 0;
    }

    @Override
    public String getType() {
        return null;
    }

    @Override
    public String getName() {
        return null;
    }

    @Override
    public String getContent() {
        return null;
    }

    @Override
    public String getTime() {
        return null;
    }

    @Override
    public String getDate() {
        return null;
    }

    @Override
    public void executeLocal() {

    }

    @Override
    public void executeBackground() {

    }
}