package com.hrw.datedialoglib;

import android.annotation.TargetApi;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.TimePicker;

/**
 * @author:Administrator
 * @date:2018/03/08 下午 3:48
 * @desc:
 */

public class TimeDialog extends AlertDialog implements DialogInterface.OnClickListener {
    private String START_HOUR = "start_hour";
    private String END_HOUR = "end_hour";
    private String START_MINUTE = "start_minute";
    private String END_MINUTE = "end_minute";
    private TimePicker mTimePicker_start;
    private TimePicker mTimePicker_end;
    private TimeDialog.OnTimeSetListener mCallBack;
    OnSingleTimeListener onsingleTimeListener;
    OnDoubleTimeListener onDoubleTimeListener;


    /**
     * The callback used to indicate the user is done filling in the date.
     */
    public interface OnTimeSetListener {

        void onTimeSet(TimePicker startTimePicker, int startHour, int startMinute,
                       TimePicker endTimePicker, int endHour, int endMinute);
    }

    public interface OnSingleTimeListener {
        void onSingleTime(String time, int Hour, int Minute);
    }

    public interface OnDoubleTimeListener {
        void onDoubleTime(String stTime, int startHour, int startMinute,
                          String endTime, int endHour, int endMinute);
    }

    public TimeDialog setSingleTimeListener(OnSingleTimeListener singleTimeListener) {
        this.onsingleTimeListener = singleTimeListener;
        return this;
    }

    public TimeDialog setOnDoubleTimeListener(OnDoubleTimeListener onDoubleTimeListener) {
        this.onDoubleTimeListener = onDoubleTimeListener;
        return this;
    }

    public TimeDialog(Context context, boolean isShowDouble) {
        this(context, isShowDouble, 0, null);
    }

    public TimeDialog(Context context, boolean isShowDouble, TimeDialog.OnTimeSetListener callBack) {
        this(context, isShowDouble, 0, callBack);
    }

    /**
     * @param context  The context the dialog is to run in.
     * @param theme    the theme to apply to this dialog
     * @param callBack How the parent is notified that the date is set.
     */
    public TimeDialog(Context context, boolean isShowDouble, int theme, TimeDialog.OnTimeSetListener callBack) {
        super(context, theme);

        mCallBack = callBack;

        Context themeContext = getContext();
        setButton(BUTTON_POSITIVE, "确 定", this);
        setButton(BUTTON_NEGATIVE, "取 消", this);
        setIcon(0);

        LayoutInflater inflater = (LayoutInflater) themeContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.item_popuwindow_timepicker, null);
        setView(view);


        mTimePicker_start = (TimePicker) view.findViewById(R.id.tp_time_picker_start);
        mTimePicker_end = (TimePicker) view.findViewById(R.id.tp_time_picker_end);
        mTimePicker_start.setIs24HourView(true);
        mTimePicker_end.setIs24HourView(true);


        if (!isShowDouble) {
            LinearLayout llEndDateContainer = (LinearLayout) view.findViewById(R.id.ll_end_time_container);
            TextView llstTitle = (TextView) view.findViewById(R.id.tv_time_picker_stLabel);
            llEndDateContainer.setVisibility(View.GONE);
            llstTitle.setVisibility(View.GONE);

            DatePickerUtils.resizeSinglePicker(mTimePicker_start);
        } else {
            DatePickerUtils.resizePicker(mTimePicker_start);
            DatePickerUtils.resizePicker(mTimePicker_end);
        }


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


    /**
     * 获得开始日期的DatePicker
     *
     * @return The calendar view.
     */
    public TimePicker getTimePickerStart() {
        return mTimePicker_start;
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
     * @param hour   The time hour.
     * @param minute The time minute.
     */
    @TargetApi(Build.VERSION_CODES.M)
    public void setStartDefaultTime(int hour, int minute) {
        mTimePicker_start.setHour(hour);
        mTimePicker_start.setMinute(minute);
    }

    /**
     * Sets the end date.
     *
     * @param hour   The time hour.
     * @param minute The time minute.
     */
    @TargetApi(Build.VERSION_CODES.M)
    public void setEndDefaultTime(int hour, int minute) {
        mTimePicker_end.setHour(hour);
        mTimePicker_end.setMinute(minute);
    }

    @TargetApi(Build.VERSION_CODES.M)
    private void tryNotifyDateSet() {
        if (mCallBack != null) {
            mTimePicker_start.clearFocus();
            mTimePicker_end.clearFocus();
            int stHour = mTimePicker_start.getHour();
            int stMinute = mTimePicker_start.getMinute();
            int endHour = mTimePicker_end.getHour();
            int endMinute = mTimePicker_end.getMinute();
            mCallBack.onTimeSet(mTimePicker_start, stHour, stMinute, mTimePicker_end, endHour, endMinute);
        }

        if (onsingleTimeListener != null) {
            mTimePicker_start.clearFocus();
            mTimePicker_end.clearFocus();
            int stHour = mTimePicker_start.getHour();
            int stMinute = mTimePicker_start.getMinute();
            String stringHour = stHour > 9 ? "" + stHour : "0" + stHour;
            String stringMinute = stMinute > 9 ? "" + stMinute : "0" + stMinute;
            onsingleTimeListener.onSingleTime(stringHour + ":" + stringMinute, stHour, stMinute);
        }
        if (onDoubleTimeListener != null) {
            mTimePicker_start.clearFocus();
            mTimePicker_end.clearFocus();
            int stHour = mTimePicker_start.getHour();
            int stMinute = mTimePicker_start.getMinute();
            int endHour = mTimePicker_end.getHour();
            int endMinute = mTimePicker_end.getMinute();
            String stringStHour = stHour > 9 ? "" + stHour : "0" + stHour;
            String stringStMinute = stMinute > 9 ? "" + stMinute : "0" + stMinute;
            String stringEndHour = endHour > 9 ? "" + endHour : "0" + endHour;
            String stringEndMinute = endMinute > 9 ? "" + endMinute : "0" + endMinute;
            onDoubleTimeListener.onDoubleTime(stringStHour + ":" + stringStMinute, stHour, stMinute, stringEndHour + ":" + stringEndMinute, endHour, endMinute);
        }
    }

    @Override
    protected void onStop() {
        // tryNotifyDateSet();
        super.onStop();
    }

    @TargetApi(Build.VERSION_CODES.M)
    @Override
    public Bundle onSaveInstanceState() {
        Bundle state = super.onSaveInstanceState();
        state.putInt(START_HOUR, mTimePicker_start.getHour());
        state.putInt(START_MINUTE, mTimePicker_start.getMinute());
        state.putInt(END_HOUR, mTimePicker_end.getHour());
        state.putInt(END_MINUTE, mTimePicker_end.getMinute());
        return state;
    }

    @Override
    public void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        int start_year = savedInstanceState.getInt(START_HOUR);
        int start_month = savedInstanceState.getInt(START_MINUTE);
        setStartDefaultTime(start_year, start_month);

        int end_year = savedInstanceState.getInt(END_HOUR);
        int end_month = savedInstanceState.getInt(END_MINUTE);
        setStartDefaultTime(end_year, end_month);

    }
}
