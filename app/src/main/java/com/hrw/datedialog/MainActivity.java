package com.hrw.datedialog;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.DatePicker;
import android.widget.TimePicker;
import android.widget.Toast;

import com.hrw.datedialoglib.DateDialog;
import com.hrw.datedialoglib.TimeDialog;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void showDialog(View view) {
        switch (view.getId()) {
            case R.id.bt_dialog_time_single:
               new TimeDialog(this, false, new TimeDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker startTimePicker, int startHour, int startMinute, TimePicker endTimePicker, int endHour, int endMinute) {
                        Toast.makeText(MainActivity.this, startHour + ":" + startMinute, Toast.LENGTH_SHORT).show();

                    }
                }).show();
                break;
            case R.id.bt_dialog_time:
                new TimeDialog(this, true, new TimeDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker startTimePicker, int startHour, int startMinute, TimePicker endTimePicker, int endHour, int endMinute) {
                        Toast.makeText(MainActivity.this, startHour + ":" + startMinute
                                + "~~" + endHour + ":" + endMinute, Toast.LENGTH_SHORT).show();
                    }
                }).show();
                break;
            case R.id.bt_dialog_date_single:
                new DateDialog(this, false, new DateDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker startDatePicker, int startYear, int startMonthOfYear, int startDayOfMonth, DatePicker endDatePicker, int endYear, int endMonthOfYear, int endDayOfMonth) {
                        Toast.makeText(MainActivity.this, startYear + "-" + startMonthOfYear + "-" + startDayOfMonth, Toast.LENGTH_SHORT).show();
                    }
                }).show();
                break;
            case R.id.bt_dialog_date:
               new DateDialog(this, true, new DateDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker startDatePicker, int startYear, int startMonthOfYear, int startDayOfMonth, DatePicker endDatePicker, int endYear, int endMonthOfYear, int endDayOfMonth) {
                        Toast.makeText(MainActivity.this, startYear + "-" + startMonthOfYear + "-" + startDayOfMonth +
                                "~~" + endYear + "-" + endMonthOfYear + "-" + endDayOfMonth, Toast.LENGTH_SHORT).show();
                    }
                }).show();
                break;
        }
    }
}
