package com.sea.lattice.dao.behavior;

import java.util.Date;

/**
 * Created by Sea on 9/14/2015.
 */
public class CntWhtBehavior extends CounterBehavior {
    public CntWhtBehavior(String content, int opp) {
        super(CounterBehavior.WHITE_COUNTER, content, opp);
    }

    public CntWhtBehavior(int id, Date date, String content, int opp) {
        super(id, date, CounterBehavior.WHITE_COUNTER, content, opp);
    }
}
