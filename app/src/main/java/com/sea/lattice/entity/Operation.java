package com.sea.lattice.entity;

/**
 * Created by Sea on 6/3/2015.
 */
public interface Operation {
    int INSERT_BEHAVIOR=1;
    int DELETE_BEHAVIOR=2;
    int MODIFY_BEHAVIOR=3;

    int getOpcode();
    String getType();
    String getName();
    String getContent();
    String getDate();
    String getTime();
    void executeLocal();
    void executeBackground();
}
