package com.sea.lattice.ui;

import android.content.ContentResolver;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Display;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.sea.lattice.R;
import com.sea.lattice.chart.Pie;
import com.sea.lattice.content.BehaviorMeta;
import com.sea.lattice.content.DirectoryMeta;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

/**
 * Created by Sea on 6/2/2015.
 */
public class StatisticActivity extends AppCompatActivity {
    private LinearLayout layout;
    private Pie pie;
    private Spinner statistics_chart, statistics_range;
    private GestureDetector gestureDetector;
    private TextView act_stt_date;
    private ContentResolver contentResolver;
    private Calendar calendar;
    private Cursor mCursor;

    final private static int forWeek = 0;
    final private static int forMonth = 1;
    final private static int forYear = 2;

    final private static int forPie = 0;
    final private static int forLine = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_statistic);

        calendar = Calendar.getInstance();
        Display mDisplay = getWindowManager().getDefaultDisplay();
        contentResolver = getContentResolver();

        layout = (LinearLayout) findViewById(R.id.chart_container);
        act_stt_date = (TextView) findViewById(R.id.act_stt_date);
        pie = new Pie(this, layout, mDisplay.getWidth(), mDisplay.getHeight());
        Toolbar toolbar = (Toolbar) findViewById(R.id.statistics_bar);
        statistics_chart = (Spinner) findViewById(R.id.statistics_chart);
        statistics_range = (Spinner) findViewById(R.id.statistics_range);
        ArrayAdapter<CharSequence> durationAdapter = ArrayAdapter.createFromResource(this, R.array.duration, R.layout.support_simple_spinner_dropdown_item);
        durationAdapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        statistics_range.setAdapter(durationAdapter);

        statistics_range.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                showDate(position);
                showChart(statistics_chart.getSelectedItemPosition());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        ArrayAdapter<CharSequence> chartAdapter = ArrayAdapter.createFromResource(this, R.array.chart, R.layout.support_simple_spinner_dropdown_item);
        statistics_chart.setAdapter(chartAdapter);
        statistics_chart.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                showDate(statistics_range.getSelectedItemPosition());
                showChart(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        setSupportActionBar(toolbar);

        gestureDetector = new GestureDetector(this, new FlingListener(mDisplay.getWidth(), mDisplay.getHeight()) {

            @Override
            public void onRight() {
                setDate(statistics_range.getSelectedItemPosition(), 1);
                showDate(statistics_range.getSelectedItemPosition());
                showChart(statistics_chart.getSelectedItemPosition());
            }

            @Override
            public void onLeft() {
                setDate(statistics_range.getSelectedItemPosition(), -1);
                showDate(statistics_range.getSelectedItemPosition());
                showChart(statistics_chart.getSelectedItemPosition());
            }
        });
    }

    public boolean onTouchEvent(MotionEvent event) {
        return gestureDetector.onTouchEvent(event);
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
                act_stt_date.setText(format.format(front.getTime()) + "-" + format.format(rear.getTime()));
                break;
            case forMonth:
                format = new SimpleDateFormat("yyyy.MM", Locale.getDefault());
                calendar.get(Calendar.MONTH);
                front.set(Calendar.DAY_OF_MONTH, front.getActualMinimum(Calendar.DAY_OF_MONTH));
                rear.set(Calendar.DAY_OF_MONTH, rear.getActualMaximum(Calendar.DATE));
                act_stt_date.setText(format.format(calendar.getTime()));
                break;
            case forYear:
                format = new SimpleDateFormat("yyyy", Locale.getDefault());
                calendar.get(Calendar.YEAR);
                front.set(Calendar.MONTH, front.getActualMinimum(Calendar.MONTH));
                front.set(Calendar.DAY_OF_MONTH, front.getActualMinimum(Calendar.DAY_OF_MONTH));
                rear.set(Calendar.MONTH, front.getActualMaximum(Calendar.MONTH));
                rear.set(Calendar.DAY_OF_MONTH, rear.getActualMaximum(Calendar.DATE));
                act_stt_date.setText(format.format(calendar.getTime()));
                break;
        }
        String[] projection = new String[]{BehaviorMeta.ID, BehaviorMeta.DATE, BehaviorMeta.CATEGORY, BehaviorMeta.CONTENT, BehaviorMeta.OPP};
        mCursor = contentResolver.query(BehaviorMeta.CONTENT_URI, projection, "?<=" + BehaviorMeta.DATE + " and " + BehaviorMeta.DATE + "<=?", new String[]{String.valueOf(front.getTime().getTime()), String.valueOf(rear.getTime().getTime())}, null);
    }

    private void showChart(int select) {
        int b = 0, ob = 0, w = 0, ow = 0;
        switch (select) {
            case forPie:
                if (mCursor.moveToFirst()) {
                    do {
                        int category = mCursor.getInt(mCursor.getColumnIndex(BehaviorMeta.CATEGORY));
                        switch (category) {
                            case BehaviorMeta.WHITE_BEHAVIOR:
                                w++;
                                break;
                            case BehaviorMeta.WHITE_COUNTER:
                                ow++;
                                break;
                            case BehaviorMeta.BLACK_BEHAVIOR:
                                b++;
                                break;
                            case BehaviorMeta.BALCK_COUNTER:
                                ob++;
                                break;
                        }
                    } while (mCursor.moveToNext());
                }
                pie.paint(b, ob, w, ow);
                break;
            case forLine:
                break;
        }
    }
}
