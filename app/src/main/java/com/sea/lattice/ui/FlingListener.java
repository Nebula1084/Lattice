package com.sea.lattice.ui;

import android.view.GestureDetector;
import android.view.MotionEvent;

/**
 * Created by Sea on 10/11/2015.
 */
public abstract class FlingListener implements GestureDetector.OnGestureListener {
    private int width;
    private int height;

    public FlingListener(int width, int height){
        this.width = width;
        this.height = height;
    }

    @Override
    public boolean onDown(MotionEvent e) {
        return false;
    }

    @Override
    public void onShowPress(MotionEvent e) {

    }

    @Override
    public boolean onSingleTapUp(MotionEvent e) {
        return false;
    }

    @Override
    public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
        return false;
    }

    @Override
    public void onLongPress(MotionEvent e) {

    }

    @Override
    public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
        if (Math.abs(e1.getY() - e2.getY()) > height/2)
            return false;
        if (e1.getX() - e2.getX() > width/4) {
            onRight();
            return true;
        }
        if (e1.getX() - e2.getX() < -width/4) {
            onLeft();
            return true;
        }
        return false;
    }

    public abstract void onRight();

    public abstract void onLeft();
}
