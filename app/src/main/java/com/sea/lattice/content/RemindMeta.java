package com.sea.lattice.content;

import android.net.Uri;

/**
 * Created by Sea on 10/19/2015.
 */
public class RemindMeta {
    public static final String TNAME = "Remind";

    public static final String ID = "_id";
    public static final String HOUR = "hour";
    public static final String MINUTE = "minute";
    public static final String ONUSE = "onuse";
    public static final String SHOCK = "shock";
    public static final String REPEAT = "repeat";
    public static final String TAG = "tag";

    public static final int ITEM = 1;

    public static final String AUTOHORITY = "com.sea.lattice.Remind";
    public static final String CONTENT_TYPE = "vnd.android.cursor.dir/vnd.lattice.Remind";

    public static final Uri CONTENT_URI = Uri.parse("content://" + AUTOHORITY + "/" + TNAME);
}
