package com.sea.lattice.dao.behavior;

import java.util.Date;

/**
 * Created by Sea on 9/14/2015.
 */
public class OrgBlkBehavior extends OriginBehavior {
    public OrgBlkBehavior(String content) {
        super(OriginBehavior.BLACK_BEHAVIOR, content);
    }

    public OrgBlkBehavior(int id, Date date, String content, int opp) {
        super(id, date, OriginBehavior.BLACK_BEHAVIOR, content, opp);
    }
}
