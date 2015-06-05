package com.sea.lattice.entity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by Sea on 6/2/2015.
 */
public class BehaviorPool {
    BehaviorPool(){

    }

    List<Behavior> getBehaviorList(){
        return Arrays.asList(
                new Behavior("1", "2014-05-13", "07:12:23", Behavior.BEHAVIOR_WHITE, "sdf"),
                new Behavior("2", "2014-05-14", "07:12:23", Behavior.BEHAVIOR_BLACK, "sdf"),
                new Behavior("3", "2014-05-15", "07:12:23", Behavior.BEHAVIOR_COUNTERWHITE, "sdf"),
                new Behavior("4", "2014-05-16", "07:12:23", Behavior.BEHAVIOR_COUNTERBLACK, "sdf"));
    }

    List<Integer> getBehaviorIdList(){
        return null;
    }

    Behavior getBehaviorById(Integer id){
        return null;
    }

    void addBehavior(Behavior b){

    }
}
