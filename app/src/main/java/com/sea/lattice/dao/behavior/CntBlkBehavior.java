package com.sea.lattice.dao.behavior;

import java.util.Date;

/**
 * Created by Sea on 9/14/2015.
 */
public class CntBlkBehavior extends CounterBehavior {
    public CntBlkBehavior(String content, int opp) {
        super(CounterBehavior.BALCK_COUNTER, content, opp);
    }

    public CntBlkBehavior(int id, Date date, String content, int opp) {
        super(id, date, CounterBehavior.WHITE_COUNTER, content, opp);
    }
}
