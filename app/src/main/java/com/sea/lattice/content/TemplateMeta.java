package com.sea.lattice.content;

import android.net.Uri;

/**
 * Created by Sea on 9/30/2015.
 */
public class TemplateMeta {
    public static final String TNAME = "Template";

    public static final String ID = "_id";
    public static final String NAME = "name";
    public static final String CATEGORY = "category";
    public static final String CONTENT = "content";
    public static final String FREQUENCY = "frequency";
    public static final String DIRECTORY = "directory";

    public static final int ITEM = 1;

    public static final String AUTOHORITY = "com.sea.lattice.Template";
    public static final String CONTENT_TYPE = "vnd.android.cursor.dir/vnd.lattice.Template";

    public static final Uri CONTENT_URI = Uri.parse("content://" + AUTOHORITY + "/" + TNAME);

    public static final int NOT_INSERT = -1;
}
