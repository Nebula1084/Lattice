package com.sea.lattice.dao;

import android.content.Context;

import com.avos.avoscloud.AVOSCloud;

/**
 * Created by Sea on 6/3/2015.
 */
public class AvosConnection {
    static AvosConnection instance;

    public final static String AppID = "d34k6ord0pj05c6ucqs29nxpd5m3rg17poeqhtp5jmlcxf6v";
    public final static String AppKey = "0clwmkayzws6p8zyfdjsrv1us08s2sxrogr7598jehuo1zbf";
    public final static String MasterKey = "l9rslgq5x54qghzaqsit2zdc3d9rgifn6581ugo8tpu1s0ct";
    public final static int FLAG_CONNECTING = 0;
    public final static int FLAG_DONE = 1;

    public AvosConnection(Context context){
        AVOSCloud.initialize(context, AppID, AppKey);
    }

}
