package com.sea.lattice.dao.behavior;

import java.util.Date;

/**
 * Created by Sea on 9/14/2015.
 */
public abstract class CounterBehavior extends Behavior {
    final public static int WHITE_COUNTER = 2;
    final public static int BALCK_COUNTER = 4;
    final public static String COUNTER_TABLE = "Counter";

    public CounterBehavior(int category, String content, int opp) {
        super(category, content, opp);
        TABLE_NAME = COUNTER_TABLE;
    }

    public CounterBehavior(int id, Date date, int category, String content, int opp) {
        super(id, date, category, content, opp);
        TABLE_NAME = COUNTER_TABLE;
    }
}
