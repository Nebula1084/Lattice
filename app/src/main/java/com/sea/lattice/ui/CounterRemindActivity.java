package com.sea.lattice.ui;

import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.TimePicker;

import com.sea.lattice.R;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Created by Sea on 10/19/2015.
 */
public class CounterRemindActivity extends AppCompatActivity implements View.OnClickListener, CompoundButton.OnCheckedChangeListener {
    private Toolbar toolbar;
    private LinearLayout couterremind_summary;
    private CheckBox couterremind_black, couterremind_white, couterremind_everyday;
    private TextView couterremind_time;
    private SharedPreferences sharedPreferences;

    private boolean cr_black;
    private boolean cr_white;
    private boolean cr_every;
    private int cr_hour;
    private int cr_minute;

    final public static String CR_BLACK = "CR_BLACK";
    final public static String CR_WHITE = "CR_WHITE";
    final public static String CR_EVERY = "CR_EVERY";
    final public static String CR_HOUR = "CR_HOUR";
    final public static String CR_MINUTE = "CR_MINUTE";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_counterremind);
        toolbar = (Toolbar) findViewById(R.id.tool_bar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        couterremind_black = (CheckBox) this.findViewById(R.id.couterremind_black);
        couterremind_black.setOnCheckedChangeListener(this);
        couterremind_white = (CheckBox) this.findViewById(R.id.couterremind_white);
        couterremind_white.setOnCheckedChangeListener(this);
        couterremind_everyday = (CheckBox) this.findViewById(R.id.couterremind_everyday);
        couterremind_everyday.setOnCheckedChangeListener(this);
        couterremind_summary = (LinearLayout) this.findViewById(R.id.couterremind_summary);
        couterremind_summary.setOnClickListener(this);
        couterremind_time = (TextView) this.findViewById(R.id.couterremind_time);

        /***
         * remind configure data
         */
        sharedPreferences = getSharedPreferences("LatticeConfig", MODE_PRIVATE);
        cr_black = sharedPreferences.getBoolean(CR_BLACK, true);
        cr_white = sharedPreferences.getBoolean(CR_WHITE, false);
        cr_every = sharedPreferences.getBoolean(CR_EVERY, true);
        cr_hour = sharedPreferences.getInt(CR_HOUR, 20);
        cr_minute = sharedPreferences.getInt(CR_MINUTE, 0);

        couterremind_black.setChecked(cr_black);
        couterremind_white.setChecked(cr_white);
        couterremind_everyday.setChecked(cr_every);
        setTime();
    }

    protected void onPause() {
        super.onPause();
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean(CR_BLACK, cr_black);
        editor.putBoolean(CR_WHITE, cr_white);
        editor.putBoolean(CR_EVERY, cr_every);
        editor.putInt(CR_HOUR, cr_hour);
        editor.putInt(CR_MINUTE, cr_minute);
        editor.commit();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // TODO Auto-generated method stub
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        switch (buttonView.getId()) {
            case R.id.couterremind_black:
                cr_black = isChecked;
                break;
            case R.id.couterremind_white:
                cr_white = isChecked;
                break;
            case R.id.couterremind_everyday:
                cr_every = isChecked;
                break;
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.couterremind_summary:
                Dialog timeDialog = new TimePickerDialog(this,
                        new TimePickerDialog.OnTimeSetListener() {
                            @Override
                            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                                cr_hour = hourOfDay;
                                cr_minute = minute;
                            }
                        },
                        cr_hour,
                        cr_minute,
                        true
                );
                timeDialog.setTitle("请选择时间");
                timeDialog.show();
                break;
        }
    }

    private void setTime() {
        SimpleDateFormat format = new SimpleDateFormat("HH:mm");
        Date date = new Date();
        date.setHours(cr_hour);
        date.setMinutes(cr_minute);
        couterremind_time.setText(format.format(date));
    }
}
