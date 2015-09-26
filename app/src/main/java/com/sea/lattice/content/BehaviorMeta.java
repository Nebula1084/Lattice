package com.sea.lattice.content;

import android.net.Uri;

/**
 * Created by Sea on 9/24/2015.
 */
public class BehaviorMeta {
    public static final String TNAME = "Behavior";

    public static final String ID = "_id";
    public static final String DATE = "date";
    public static final String CATEGORY = "category";
    public static final String CONTENT = "content";
    public static final String OPP = "opp";

    public static final int ITEM = 1;
    public static final int ITEM_ID = 2;

    public static final String AUTOHORITY = "com.sea.lattice.Behavior";
    public static final String CONTENT_TYPE = "vnd.android.cursor.dir/vnd.lattice.Behavior";
    public static final String CONTENT_ITEM_TYPE = "vnd.android.cursor.item/vnd.latice.Behavior";

    public static final Uri CONTENT_URI = Uri.parse("content://" + AUTOHORITY + "/" + TNAME);

    final public static int WHITE_BEHAVIOR = 1;
    final public static int WHITE_COUNTER = 2;
    final public static int BLACK_BEHAVIOR = 3;
    final public static int BALCK_COUNTER = 4;

    public static String getCategory(int category) {
        switch (category) {
            case WHITE_BEHAVIOR:
                return "白业";
            case WHITE_COUNTER:
                return "白业对治";
            case BLACK_BEHAVIOR:
                return "黑业";
            case BALCK_COUNTER:
                return "黑业对治";
        }
        return null;
    }
}