package com.sea.lattice.ui;

import android.app.ActionBar;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.SpinnerAdapter;
import android.widget.TextView;

import com.sea.lattice.R;
import com.sea.lattice.content.BehaviorMeta;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

/**
 * Created by Sea on 9/26/2015.
 */
public class BehaviorActivity extends FragmentActivity implements ActionBar.OnNavigationListener, GestureDetector.OnGestureListener, View.OnTouchListener {
    private FragmentManager fragmentManager;
    private Calendar calendar;
    private TextView act_beh_date;
    private ActionBar actionBar;
    private GestureDetector gestureDetector;

    final private static int forWeek = 0;
    final private static int forMonth = 1;
    final private static int forYear = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_behavior);
        act_beh_date = (TextView) findViewById(R.id.act_beh_date);
        SpinnerAdapter adapter = ArrayAdapter.createFromResource(this, R.array.duration, android.R.layout.simple_spinner_dropdown_item);
        calendar = Calendar.getInstance();
        actionBar = getActionBar();
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_LIST);
        actionBar.setListNavigationCallbacks(adapter, this);
        gestureDetector = new GestureDetector(this, this);
        fragmentManager = getSupportFragmentManager();
        BehaviorList list = BehaviorList.newInstance("", new String[]{}, this);
        fragmentManager.beginTransaction().add(R.id.fragment_container, list).commit();
        showDate(actionBar.getSelectedNavigationIndex());
    }

    @Override
    public boolean onNavigationItemSelected(int itemPosition, long itemId) {
        showDate(itemPosition);
        return true;
    }

    private void showDate(int select) {
        SimpleDateFormat format;
        Calendar front = (Calendar) calendar.clone();
        Calendar rear = (Calendar) calendar.clone();
        front.set(Calendar.HOUR_OF_DAY, 0);
        front.set(Calendar.MINUTE, 0);
        front.set(Calendar.SECOND, 0);
        rear.set(Calendar.HOUR_OF_DAY, 23);
        rear.set(Calendar.MINUTE, 59);
        rear.set(Calendar.SECOND, 59);
        switch (select) {
            case forWeek:
                format = new SimpleDateFormat("yyyy.MM.dd", Locale.getDefault());
                while (front.get(Calendar.DAY_OF_WEEK) != 1) front.add(Calendar.DATE, -1);
                while (rear.get(Calendar.DAY_OF_WEEK) != 7) rear.add(Calendar.DATE, 1);
                act_beh_date.setText(format.format(front.getTime()) + "-" + format.format(rear.getTime()));
                break;
            case forMonth:
                format = new SimpleDateFormat("yyyy.MM", Locale.getDefault());
                calendar.get(Calendar.MONTH);
                front.set(Calendar.DAY_OF_MONTH, front.getActualMinimum(Calendar.DAY_OF_MONTH));
                rear.set(Calendar.DAY_OF_MONTH, rear.getActualMaximum(Calendar.DATE));
                act_beh_date.setText(format.format(calendar.getTime()));
                break;
            case forYear:
                format = new SimpleDateFormat("yyyy", Locale.getDefault());
                calendar.get(Calendar.YEAR);
                front.set(Calendar.MONTH, front.getActualMinimum(Calendar.MONTH));
                front.set(Calendar.DAY_OF_MONTH, front.getActualMinimum(Calendar.DAY_OF_MONTH));
                rear.set(Calendar.MONTH, front.getActualMaximum(Calendar.MONTH));
                rear.set(Calendar.DAY_OF_MONTH, rear.getActualMaximum(Calendar.DATE));
                act_beh_date.setText(format.format(calendar.getTime()));
                break;
        }
        BehaviorList list = BehaviorList.newInstance("?<=" + BehaviorMeta.DATE + " and " + BehaviorMeta.DATE + "<=?", new String[]{String.valueOf(front.getTime().getTime()), String.valueOf(rear.getTime().getTime())}, this);
        fragmentManager.beginTransaction().replace(R.id.fragment_container, list).commit();
    }

    private void setDate(int select, int offset) {
        switch (select) {
            case forWeek:
                calendar.add(Calendar.DATE, 7 * offset);
                break;
            case forMonth:
                calendar.add(Calendar.MONTH, offset);
                break;
            case forYear:
                calendar.add(Calendar.YEAR, offset);
                break;
        }
    }

    public boolean onTouchEvent(MotionEvent event) {
        return gestureDetector.onTouchEvent(event);
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        return gestureDetector.onTouchEvent(event);
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
        if (Math.abs(e1.getY() - e2.getY()) > 120)
            return false;
        if (e1.getX() - e2.getX() > 120) {
            setDate(actionBar.getSelectedNavigationIndex(), 1);
            showDate(actionBar.getSelectedNavigationIndex());
            return true;
        }
        if (e1.getX() - e2.getX() < -120) {
            setDate(actionBar.getSelectedNavigationIndex(), -1);
            showDate(actionBar.getSelectedNavigationIndex());
            return true;
        }
        return false;
    }

}