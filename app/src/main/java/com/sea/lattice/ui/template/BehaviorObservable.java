package com.sea.lattice.ui.template;

import com.sea.lattice.content.BehaviorMeta;

import java.util.Observable;

/**
 * Created by Sea on 10/2/2015.
 */
public class BehaviorObservable extends Observable {
    private int category = BehaviorMeta.ALL;
    private int dirctory;

    public int getCategory() {
        return category;
    }

    public int getDirectory(){
        return dirctory;
    }

    public void chooseBehavior(int directory, int category) {
        if (this.category != category || this.dirctory != directory) {
            this.category = category;
            this.dirctory = directory;
            setChanged();
            notifyObservers();
        }

    }
}
