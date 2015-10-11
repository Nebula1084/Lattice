package com.sea.lattice.ui;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Toast;

import com.sea.lattice.R;
import com.sea.lattice.chart.Pie;

/**
 * Created by Sea on 6/2/2015.
 */
public class StatisticActivity extends AppCompatActivity{
    private LinearLayout layout;
    private Pie pie;
    private Spinner statistics_chart, statistics_range;
    private GestureDetector gestureDetector;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_statistic);
        layout = (LinearLayout) findViewById(R.id.chart_container);
        pie = new Pie(this, layout, 1000, 800);
        pie.paint(10, 10, 10, 20);
        Toolbar toolbar = (Toolbar) findViewById(R.id.statistics_bar);
        statistics_chart = (Spinner) findViewById(R.id.statistics_chart);
        statistics_range = (Spinner) findViewById(R.id.statistics_range);
        ArrayAdapter<CharSequence> durationAdapter = ArrayAdapter.createFromResource(this, R.array.duration, R.layout.support_simple_spinner_dropdown_item);
        durationAdapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        statistics_chart.setAdapter(durationAdapter);

        statistics_chart.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        ArrayAdapter<CharSequence> chartAdapter = ArrayAdapter.createFromResource(this, R.array.chart, R.layout.support_simple_spinner_dropdown_item);
        statistics_range.setAdapter(chartAdapter);
        statistics_range.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        setSupportActionBar(toolbar);

        gestureDetector = new GestureDetector(this, new FlingListener() {

            @Override
            public void onRight() {
                Toast.makeText(StatisticActivity.this, "onRight()", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onLeft() {
                Toast.makeText(StatisticActivity.this, "onLeft()", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public boolean onTouchEvent(MotionEvent event) {
        return gestureDetector.onTouchEvent(event);
    }

}
