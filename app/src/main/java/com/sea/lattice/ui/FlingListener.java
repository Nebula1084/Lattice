package com.sea.lattice.ui;

import android.view.GestureDetector;
import android.view.MotionEvent;

/**
 * Created by Sea on 10/11/2015.
 */
public abstract class FlingListener implements GestureDetector.OnGestureListener {
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
        if (Math.abs(e1.getY() - e2.getY()) > 120)
            return false;
        if (e1.getX() - e2.getX() > 120) {
            onRight();
            return true;
        }
        if (e1.getX() - e2.getX() < -120) {
            onLeft();
            return true;
        }
        return false;
    }

    @Override
    public void onLongPress(MotionEvent e) {

    }

    @Override
    public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
        return false;
    }

    public abstract void onRight();

    public abstract void onLeft();
}
