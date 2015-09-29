package com.sea.lattice.content;

import android.net.Uri;

/**
 * Created by Sea on 9/28/2015.
 */
public class DirectoryMeta {
    public static final String TNAME = "Directory";

    public static final String ID = "_id";
    public static final String NAME = "name";

    public static final int ITEM = 1;

    public static final String AUTOHORITY = "com.sea.lattice.Directory";
    public static final String CONTENT_TYPE = "vnd.android.cursor.dir/vnd.lattice.Directory";

    public static final Uri CONTENT_URI = Uri.parse("content://" + AUTOHORITY + "/" + TNAME);
}