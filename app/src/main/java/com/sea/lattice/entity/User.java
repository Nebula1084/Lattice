package com.sea.lattice.entity;

import com.avos.avoscloud.AVUser;
import com.avos.avoscloud.SignUpCallback;

/**
 * Created by Sea on 6/2/2015.
 */
public class User extends AVUser {
    public void setNickName(String nickName){
        this.put("nickName", nickName);
    }

    public String getNickName(){
        return this.getString("nickName");
    }

}