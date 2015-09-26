package com.sea.lattice.widget;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.DatePicker;
import android.widget.TimePicker;

import com.sea.lattice.R;

import java.util.Calendar;
import java.util.Date;

/**
 * Created by Sea on 9/19/2015.
 */
public class TimeDialog extends AlertDialog implements DialogInterface.OnClickListener, TimePicker.OnTimeChangedListener, DatePicker.OnDateChangedListener {

    private Calendar mCalendar;
    private Calendar tmpCalendar;
    private final DatePicker mDatePicker;
    private final TimePicker mTimePicker;
    private boolean is24HourView;

    public TimeDialog(Context context) {
        this(context, 0, Calendar.getInstance(), true);
    }

    static int resovleDialogTheme(Context context, int resid) {
        if (resid == 0) {
            final TypedValue outValue = new TypedValue();
            context.getTheme().resolveAttribute(R.attr.alertDialogTheme, outValue, true);
            return outValue.resourceId;
        } else {
            return resid;
        }
    }

    public TimeDialog(Context context, int theme, Calendar calendar, boolean is24HourView) {
        super(context, resovleDialogTheme(context, theme));
        mCalendar = calendar;
        tmpCalendar = (Calendar)calendar.clone();
        this.is24HourView=is24HourView;
        final Context themeContext = getContext();
        final LayoutInflater inflater = LayoutInflater.from(themeContext);
        final View view = inflater.inflate(R.layout.widget_time_picker, null);
        setView(view);
        setButton(BUTTON_POSITIVE, themeContext.getString(R.string.confirm), this);
        setButton(BUTTON_NEGATIVE, themeContext.getString(R.string.cancel), this);
        mDatePicker = (DatePicker) view.findViewById(R.id.datePicker);
        mTimePicker = (TimePicker) view.findViewById(R.id.timePicker);

        mDatePicker.init(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH), this);
        mTimePicker.setIs24HourView(is24HourView);
        mTimePicker.setCurrentHour(calendar.get(Calendar.HOUR_OF_DAY));
        mTimePicker.setCurrentMinute(calendar.get(Calendar.MINUTE));
        mTimePicker.setOnTimeChangedListener(this);
        setTitle(themeContext.getString(R.string.dialog_title_timechoose));
    }

    @Override
    public void onTimeChanged(TimePicker view, int hourOfDay, int minute) {
        tmpCalendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
        tmpCalendar.set(Calendar.MINUTE, minute);
    }

    @Override
    public void onDateChanged(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
        tmpCalendar.set(Calendar.YEAR, year);
        tmpCalendar.set(Calendar.MONTH, monthOfYear);
        tmpCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
    }

    @Override
    public void onClick(DialogInterface dialog, int which) {
        switch (which) {
            case BUTTON_POSITIVE:
                mCalendar = (Calendar) tmpCalendar.clone();
                break;
            case BUTTON_NEGATIVE:
                cancel();
                break;
        }
    }

    @Override
    public void show() {
        super.show();
        tmpCalendar = (Calendar) mCalendar.clone();
        mDatePicker.init(tmpCalendar.get(Calendar.YEAR), tmpCalendar.get(Calendar.MONTH), tmpCalendar.get(Calendar.DAY_OF_MONTH), this);
        mTimePicker.setIs24HourView(is24HourView);
        mTimePicker.setCurrentHour(tmpCalendar.get(Calendar.HOUR_OF_DAY));
        mTimePicker.setCurrentMinute(tmpCalendar.get(Calendar.MINUTE));
        mTimePicker.setOnTimeChangedListener(this);
    }

    public Date getDate(){
        return mCalendar.getTime();
    }
}
