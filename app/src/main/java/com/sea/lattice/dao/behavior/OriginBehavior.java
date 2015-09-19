package com.sea.lattice.dao.behavior;

import java.util.Date;

/**
 * Created by Sea on 9/14/2015.
 */
public abstract class OriginBehavior extends Behavior {
    final public static int WHITE_BEHAVIOR = 1;
    final public static int BLACK_BEHAVIOR = 3;
    final public static String BEHAVIOR_TABLE = "Behavior";

    public OriginBehavior(int category, String content) {
        super(category, content);
        TABLE_NAME = BEHAVIOR_TABLE;
    }

    public OriginBehavior(int id, Date date, int category, String content, int opp) {
        super(id, date, category, content, opp);
        TABLE_NAME = BEHAVIOR_TABLE;
    }

    /**
     * check whether the origin behavior have been confronted
     * @return true when have benn confronted, otherwise, false
     */
    public boolean isConfront(){
        return opp != Behavior.NO_OPP;
    }
}
