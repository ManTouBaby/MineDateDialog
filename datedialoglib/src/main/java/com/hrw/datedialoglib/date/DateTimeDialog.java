package com.hrw.datedialoglib.date;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.TimePicker;

import com.hrw.datedialoglib.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * @author:Administrator
 * @date:2017/12/22 下午 4:07
 * @desc:
 */

public class DateTimeDialog extends AlertDialog implements DialogInterface.OnClickListener, DatePicker.OnDateChangedListener {
    private TextView tvTitle;
    private String START_YEAR = "start_year";
    private String START_MONTH = "start_month";
    private String START_DAY = "start_day";
    private String END_HOUR = "end_hour";
    private String END_MINUTE = "end_minute";
    private DatePicker mDatePicker_start;
    private TimePicker mTimePicker_end;
    OnDateTimeListener onSingleDateListener;


    public interface OnDateTimeListener {
        void onDateTime(String date, String time, int year, int month, int day, int hour, int minute);
    }


    public DateTimeDialog setOnDateTimeListener(OnDateTimeListener onSingleDateListener) {
        this.onSingleDateListener = onSingleDateListener;
        return this;
    }


    public DateTimeDialog(Context context) {
        this(context, 0, -1, -1, -1, -1, -1);
    }

    public DateTimeDialog(Context context, int hour, int minute) {
        this(context, 0, -1, -1, -1, hour, minute);
    }

    public DateTimeDialog(Context context, int year, int monthOfYear, int dayOfMonth) {
        this(context, 0, year, monthOfYear, dayOfMonth, -1, -1);
    }

    public DateTimeDialog setTitle(String title) {
        tvTitle.setText(title);
        return this;
    }

    /**
     * @param context     The context the dialog is to run in.
     * @param theme       the theme to apply to this dialog
     * @param year        The initial year of the dialog.
     * @param monthOfYear The initial month of the dialog.
     * @param dayOfMonth  The initial day of the dialog.
     */
    public DateTimeDialog(Context context, int theme, int year, int monthOfYear, int dayOfMonth, int hour, int minute) {
        super(context, theme);


        Context themeContext = getContext();
        setButton(BUTTON_POSITIVE, "确 定", this);
        setButton(BUTTON_NEGATIVE, "取 消", this);
        setIcon(0);

        LayoutInflater inflater = (LayoutInflater) themeContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.item_popuwindow_date_time_picker, null);
        setView(view);

        mDatePicker_start = (DatePicker) view.findViewById(R.id.dp_date_picker_start);
        mTimePicker_end = (TimePicker) view.findViewById(R.id.tp_time_picker_end);
        tvTitle = (TextView) view.findViewById(R.id.tv_date_picker_stLabel);
        mTimePicker_end.setIs24HourView(true);

        DatePickerUtils.resizePicker(mDatePicker_start);
        DatePickerUtils.resizePicker(mTimePicker_end);

        //初始化日历显示
        if (year == -1 && monthOfYear == -1 && dayOfMonth == -1) {
            Calendar c = Calendar.getInstance();
            mDatePicker_start.init(c.get(Calendar.YEAR), c.get(Calendar.MONTH), c.get(Calendar.DATE), this);
        } else {
            mDatePicker_start.init(year, monthOfYear, dayOfMonth, this);
        }
        if (hour != -1 && minute != -1) {
            mTimePicker_end.setCurrentHour(hour);
            mTimePicker_end.setCurrentMinute(minute);
        }

    }


    public DateTimeDialog setStartMinDate(String date) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        try {
            Date minDate = dateFormat.parse(date);
            mDatePicker_start.setMinDate(minDate.getTime());
        } catch (ParseException e) {
            e.printStackTrace();
            Log.i("DateDialog", "the format of param do`not match");
        }
        return this;
    }

    public DateTimeDialog setStartMaxDate(String date) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        try {
            Date minDate = dateFormat.parse(date);
            mDatePicker_start.setMaxDate(minDate.getTime());
        } catch (ParseException e) {
            e.printStackTrace();
            Log.i("DateDialog", "the format of param do`not match");
        }
        return this;
    }


    public void onClick(DialogInterface dialog, int which) {
        // 如果是“取 消”按钮，则返回，如果是“确 定”按钮，则往下执行
        switch (which) {
            case BUTTON_POSITIVE:
                tryNotifyDateSet();
                break;
            case BUTTON_NEGATIVE:

                break;
        }
    }

    @Override
    public void onDateChanged(DatePicker view, int year, int month, int day) {
        if (view.getId() == R.id.dp_date_picker_start)
            mDatePicker_start.init(year, month, day, this);
    }

    /**
     * 获得开始日期的DatePicker
     *
     * @return The calendar view.
     */
    public DatePicker getDatePickerStart() {
        return mDatePicker_start;
    }

    /**
     * 获得结束日期的DatePicker
     *
     * @return The calendar view.
     */
    public TimePicker getTimePickerEnd() {
        return mTimePicker_end;
    }

    /**
     * Sets the start date.
     *
     * @param year        The date year.
     * @param monthOfYear The date month.
     * @param dayOfMonth  The date day of month.
     */
    public DateTimeDialog setDefaultDate(int year, int monthOfYear, int dayOfMonth) {
        monthOfYear--;
        mDatePicker_start.updateDate(year, monthOfYear, dayOfMonth);
        return this;
    }

    public DateTimeDialog setDefaultTime(int hour, int minute) {
        mTimePicker_end.setCurrentHour(hour);
        mTimePicker_end.setCurrentMinute(minute);
        return this;
    }

    private void tryNotifyDateSet() {
        if (onSingleDateListener != null) {
            mDatePicker_start.clearFocus();
            int stYear = mDatePicker_start.getYear();
            int stMonth = mDatePicker_start.getMonth() + 1;
            int stDay = mDatePicker_start.getDayOfMonth();
            String stringMonth = stMonth > 9 ? "" + stMonth : "0" + stMonth;
            String stringDay = stDay > 9 ? "" + stDay : "0" + stDay;
            String date = stYear + "-" + stringMonth + "-" + stringDay;

            mTimePicker_end.clearFocus();
            int stHour = mTimePicker_end.getCurrentHour();
            int stMinute = mTimePicker_end.getCurrentMinute();
            String stringHour = stHour > 9 ? "" + stHour : "0" + stHour;
            String stringMinute = stMinute > 9 ? "" + stMinute : "0" + stMinute;
            onSingleDateListener.onDateTime(date, stringHour + ":" + stringMinute, stYear, stMonth, stDay, stHour, stMinute);
        }
    }


    @Override
    public Bundle onSaveInstanceState() {
        Bundle state = super.onSaveInstanceState();
        state.putInt(START_YEAR, mDatePicker_start.getYear());
        state.putInt(START_MONTH, mDatePicker_start.getMonth());
        state.putInt(START_DAY, mDatePicker_start.getDayOfMonth());

        state.putInt(END_HOUR, mTimePicker_end.getCurrentHour());
        state.putInt(END_MINUTE, mTimePicker_end.getCurrentMinute());
        return state;
    }

    @Override
    public void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        int start_year = savedInstanceState.getInt(START_YEAR);
        int start_month = savedInstanceState.getInt(START_MONTH);
        int start_day = savedInstanceState.getInt(START_DAY);

        int end_year = savedInstanceState.getInt(END_HOUR);
        int end_month = savedInstanceState.getInt(END_MINUTE);
        setDefaultTime(end_year, end_month);
        mDatePicker_start.init(start_year, start_month, start_day, this);


    }


}
