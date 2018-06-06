package com.hrw.datedialog;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import com.hrw.datedialoglib.date.DateDialog;
import com.hrw.datedialoglib.date.TimeDialog;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void showDialog(View view) {
        switch (view.getId()) {
            case R.id.bt_dialog_time_single:
                new TimeDialog(this, false)
                        .setSingleTimeListener(new TimeDialog.OnSingleTimeListener() {
                            @Override
                            public void onSingleTime(String time, int Hour, int Minute) {
                                Toast.makeText(MainActivity.this, time, Toast.LENGTH_SHORT).show();

                            }
                        })
                        .setStartDefaultTime(12, 12)
                        .show();
                break;
            case R.id.bt_dialog_time:
                new TimeDialog(this, true)
                        .setOnDoubleTimeListener(new TimeDialog.OnDoubleTimeListener() {
                            @Override
                            public void onDoubleTime(String stTime, int startHour, int startMinute, String endTime, int endHour, int endMinute) {
                                Toast.makeText(MainActivity.this, stTime + "~~" + endTime, Toast.LENGTH_SHORT).show();
                            }
                        }).show();
                break;
            case R.id.bt_dialog_date_single:
                new DateDialog(this, false)
                        .setOnSingleDateListener(new DateDialog.OnSingleDateListener() {
                            @Override
                            public void onSingleDate(String date, int year, int month, int day) {
                                Toast.makeText(MainActivity.this, date, Toast.LENGTH_SHORT).show();
                            }
                        })
                        .setStartDefaultDate(2018, 2, 2)
                        .setStartMinDate("2016-09-10")
                        .setStartMaxDate("2018-12-12")
                        .show();
                break;
            case R.id.bt_dialog_date:
                new DateDialog(this, true)
                        .setOnDoubleDateListener(new DateDialog.OnDoubleDateListener() {
                            @Override
                            public void onDoubleDate(String stDate, int stYear, int stMonth, int stDay, String endDate, int endYear, int endMonth, int endDay) {
                                Toast.makeText(MainActivity.this, stDate + "~~" + endDate, Toast.LENGTH_SHORT).show();
                            }
                        }).show();
                break;
        }
    }
}
