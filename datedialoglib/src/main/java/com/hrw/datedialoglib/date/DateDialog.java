package com.hrw.datedialoglib.date;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.DatePicker;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.hrw.datedialoglib.R;

import java.lang.reflect.Field;
import java.util.Calendar;

/**
 * @author:Administrator
 * @date:2017/12/22 下午 4:07
 * @desc:
 */

public class DateDialog extends AlertDialog implements DialogInterface.OnClickListener, DatePicker.OnDateChangedListener {
    private String START_YEAR = "start_year";
    private String END_YEAR = "end_year";
    private String START_MONTH = "start_month";
    private String END_MONTH = "end_month";
    private String START_DAY = "start_day";
    private String END_DAY = "end_day";
    private DatePicker mDatePicker_start;
    private DatePicker mDatePicker_end;
    private OnDateSetListener mCallBack;
    OnSingleDateListener onSingleDateListener;
    OnDoubleDateListener onDoubleDateListener;


    /**
     * The callback used to indicate the user is done filling in the date.
     */
    public interface OnDateSetListener {

        void onDateSet(DatePicker startDatePicker, int startYear, int startMonthOfYear, int startDayOfMonth,
                       DatePicker endDatePicker, int endYear, int endMonthOfYear, int endDayOfMonth);
    }

    public interface OnSingleDateListener {
        void onSingleDate(String date, int year, int month, int day);
    }

    public interface OnDoubleDateListener {
        void onDoubleDate(String stDate, int stYear, int stMonth, int stDay,
                          String endDate, int endYear, int endMonth, int endDay);
    }

    public DateDialog setOnSingleDateListener(OnSingleDateListener onSingleDateListener) {
        this.onSingleDateListener = onSingleDateListener;
        return this;
    }

    public DateDialog setOnDoubleDateListener(OnDoubleDateListener onDoubleDateListener) {
        this.onDoubleDateListener = onDoubleDateListener;
        return this;
    }

    public DateDialog(Context context, boolean isShowDouble) {
        this(context, isShowDouble, true, 0, null, -1, -1, -1);
    }

    public DateDialog(Context context, boolean isShowDouble, OnDateSetListener callBack) {
        this(context, isShowDouble, true, 0, callBack, -1, -1, -1);
    }

    public DateDialog(Context context, boolean isShowDouble, boolean isDayVisible, OnDateSetListener callBack) {
        this(context, isShowDouble, isDayVisible, 0, callBack, -1, -1, -1);
    }

    public DateDialog(Context context, boolean isShowDouble, boolean isDayVisible, OnDateSetListener callBack, int year, int monthOfYear, int dayOfMonth) {
        this(context, isShowDouble, isDayVisible, 0, callBack, year, monthOfYear, dayOfMonth);
    }

    /**
     * @param context     The context the dialog is to run in.
     * @param theme       the theme to apply to this dialog
     * @param callBack    How the parent is notified that the date is set.
     * @param year        The initial year of the dialog.
     * @param monthOfYear The initial month of the dialog.
     * @param dayOfMonth  The initial day of the dialog.
     */
    public DateDialog(Context context, boolean isShowDouble, boolean isDayVisible, int theme, OnDateSetListener callBack, int year, int monthOfYear,
                      int dayOfMonth) {
        super(context, theme);

        mCallBack = callBack;

        Context themeContext = getContext();
        setButton(BUTTON_POSITIVE, "确 定", this);
        setButton(BUTTON_NEGATIVE, "取 消", this);
        setIcon(0);

        LayoutInflater inflater = (LayoutInflater) themeContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.item_popuwindow_datepicker, null);
        setView(view);

        mDatePicker_start = (DatePicker) view.findViewById(R.id.dp_date_picker_start);
        mDatePicker_end = (DatePicker) view.findViewById(R.id.dp_date_picker_end);

        if (year == -1 && monthOfYear == -1 && dayOfMonth == -1) {
            Calendar c = Calendar.getInstance();
            mDatePicker_start.init(c.get(Calendar.YEAR), c.get(Calendar.MONTH), c.get(Calendar.DATE), this);
            mDatePicker_end.init(c.get(Calendar.YEAR), c.get(Calendar.MONTH), c.get(Calendar.DATE), this);
        } else {
            mDatePicker_start.init(year, monthOfYear, dayOfMonth, this);
            mDatePicker_end.init(year, monthOfYear, dayOfMonth, this);
        }

        if (!isShowDouble) {
            LinearLayout llEndDateContainer = (LinearLayout) view.findViewById(R.id.ll_end_date_container);
            TextView llstTitle = (TextView) view.findViewById(R.id.tv_date_picker_stLabel);
            llEndDateContainer.setVisibility(View.GONE);
            llstTitle.setVisibility(View.GONE);

            DatePickerUtils.resizeSinglePicker(mDatePicker_start);
        } else {
            DatePickerUtils.resizePicker(mDatePicker_start);
            DatePickerUtils.resizePicker(mDatePicker_end);
        }


        // 如果要隐藏当前日期，则使用下面方法。
        if (!isDayVisible) {
            if (mDatePicker_start != null) hidDay(mDatePicker_start);
            if (mDatePicker_end != null) hidDay(mDatePicker_end);
        }
    }

    /**
     * 隐藏DatePicker中的日期显示
     *
     * @param mDatePicker
     */
    private void hidDay(DatePicker mDatePicker) {
        Field[] datePickerfFields = mDatePicker.getClass().getDeclaredFields();
        for (Field datePickerField : datePickerfFields) {
            if ("mDaySpinner".equals(datePickerField.getName())) {
                datePickerField.setAccessible(true);
                Object dayPicker = new Object();
                try {
                    dayPicker = datePickerField.get(mDatePicker);
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                } catch (IllegalArgumentException e) {
                    e.printStackTrace();
                }
                ((View) dayPicker).setVisibility(View.GONE);
            }
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

    @Override
    public void onDateChanged(DatePicker view, int year, int month, int day) {
        if (view.getId() == R.id.dp_date_picker_start)
            mDatePicker_start.init(year, month, day, this);
        if (view.getId() == R.id.dp_date_picker_end)
            mDatePicker_end.init(year, month, day, this);
        // updateTitle(year, month, day);
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
    public DatePicker getDatePickerEnd() {
        return mDatePicker_end;
    }

    /**
     * Sets the start date.
     *
     * @param year        The date year.
     * @param monthOfYear The date month.
     * @param dayOfMonth  The date day of month.
     */
    public void updateStartDate(int year, int monthOfYear, int dayOfMonth) {
        mDatePicker_start.updateDate(year, monthOfYear, dayOfMonth);
    }

    /**
     * Sets the end date.
     *
     * @param year        The date year.
     * @param monthOfYear The date month.
     * @param dayOfMonth  The date day of month.
     */
    public void updateEndDate(int year, int monthOfYear, int dayOfMonth) {
        mDatePicker_end.updateDate(year, monthOfYear, dayOfMonth);
    }

    private void tryNotifyDateSet() {
        if (mCallBack != null) {
            mDatePicker_start.clearFocus();
            mDatePicker_end.clearFocus();
            int stYear = mDatePicker_start.getYear();
            int stMonth = mDatePicker_start.getMonth();
            int stDay = mDatePicker_start.getDayOfMonth()+1;
            int endYear = mDatePicker_end.getYear();
            int endMonth = mDatePicker_end.getMonth();
            int endDay = mDatePicker_end.getDayOfMonth();
            mCallBack.onDateSet(mDatePicker_start, stYear, stMonth, stDay, mDatePicker_end, endYear, endMonth, endDay);
        }

        if (onSingleDateListener != null) {
            mDatePicker_start.clearFocus();
            mDatePicker_end.clearFocus();
            int stYear = mDatePicker_start.getYear();
            int stMonth = mDatePicker_start.getMonth() + 1;
            int stDay = mDatePicker_start.getDayOfMonth();
            String stringMonth = stMonth > 9 ? "" + stMonth : "0" + stMonth;
            String stringDay = stDay > 9 ? "" + stDay : "0" + stDay;
            String date = stYear + "-" + stringMonth + "-" + stringDay;
            onSingleDateListener.onSingleDate(date, stYear, stMonth, stDay);
        }

        if (onDoubleDateListener != null) {
            mDatePicker_start.clearFocus();
            mDatePicker_end.clearFocus();
            int stYear = mDatePicker_start.getYear();
            int stMonth = mDatePicker_start.getMonth() + 1;
            int stDay = mDatePicker_start.getDayOfMonth();
            int endYear = mDatePicker_end.getYear();
            int endMonth = mDatePicker_end.getMonth() + 1;
            int endDay = mDatePicker_end.getDayOfMonth();
            String stringStMonth = stMonth > 9 ? "" + stMonth : "0" + stMonth;
            String stringStDay = stDay > 9 ? "" + stDay : "0" + stDay;
            String stringEndMonth = endMonth > 9 ? "" + endMonth : "0" + endMonth;
            String stringEndDay = endDay > 9 ? "" + endDay : "0" + endDay;
            String stDate = stYear + "-" + stringStMonth + "-" + stringStDay;
            String enDDate = endYear + "-" + stringEndMonth + "-" + stringEndDay;
            onDoubleDateListener.onDoubleDate(stDate, stYear, stMonth, stDay, enDDate, endYear, endMonth, endDay);
        }
    }

    @Override
    protected void onStop() {
        // tryNotifyDateSet();
        super.onStop();
    }

    @Override
    public Bundle onSaveInstanceState() {
        Bundle state = super.onSaveInstanceState();
        state.putInt(START_YEAR, mDatePicker_start.getYear());
        state.putInt(START_MONTH, mDatePicker_start.getMonth());
        state.putInt(START_DAY, mDatePicker_start.getDayOfMonth());
        state.putInt(END_YEAR, mDatePicker_end.getYear());
        state.putInt(END_MONTH, mDatePicker_end.getMonth());
        state.putInt(END_DAY, mDatePicker_end.getDayOfMonth());
        return state;
    }

    @Override
    public void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        int start_year = savedInstanceState.getInt(START_YEAR);
        int start_month = savedInstanceState.getInt(START_MONTH);
        int start_day = savedInstanceState.getInt(START_DAY);
        mDatePicker_start.init(start_year, start_month, start_day, this);

        int end_year = savedInstanceState.getInt(END_YEAR);
        int end_month = savedInstanceState.getInt(END_MONTH);
        int end_day = savedInstanceState.getInt(END_DAY);
        mDatePicker_end.init(end_year, end_month, end_day, this);

    }


}
