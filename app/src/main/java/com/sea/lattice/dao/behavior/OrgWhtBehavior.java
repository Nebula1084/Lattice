package com.sea.lattice.dao.behavior;

import java.util.Date;

/**
 * Created by Sea on 9/14/2015.
 */
public class OrgWhtBehavior extends OriginBehavior {
    public OrgWhtBehavior(String content) {
        super(OriginBehavior.WHITE_BEHAVIOR, content);
    }

    public OrgWhtBehavior(int id, Date date, String content, int opp) {
        super(id, date, OriginBehavior.WHITE_BEHAVIOR, content, opp);
    }
}
