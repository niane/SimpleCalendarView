package com.yzg.simplecalendardemo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatSpinner;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.yzg.simplecalendarview.SimpleCalendarView;

import java.util.Date;

public class MainActivity extends AppCompatActivity {

    TextView tvDate;
    AppCompatSpinner spinner;
    SimpleCalendarView[] calendarViews;

    private final String[] models = {"月", "周"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        init();
    }

    private void init() {
        tvDate = (TextView) findViewById(R.id.tv_date);
        spinner = (AppCompatSpinner) findViewById(R.id.spinner);

        calendarViews = new SimpleCalendarView[2];
        calendarViews[0] = (SimpleCalendarView) findViewById(R.id.month_calendar);
        calendarViews[1] = (SimpleCalendarView) findViewById(R.id.week_calendar);


        tvDate.setText(TimeUtil.getYYYYMM(new Date()));

        ArrayAdapter spinnerAdapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item, models);
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        spinner.setAdapter(spinnerAdapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                for (int i = 0; i < calendarViews.length; i++) {
                    if (i == position) {
                        calendarViews[i].setVisibility(View.VISIBLE);
                        tvDate.setText(TimeUtil.getYYYYMM(calendarViews[i].getCurrentDate()));
                    } else {
                        calendarViews[i].setVisibility(View.GONE);
                    }
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        calendarViews[0].initDate(new Date());
        calendarViews[0].setOnMonthChangeListener(new SimpleCalendarView.OnMonthChangeListener() {
            @Override
            public void onMonthChange(Date date, int PageNum) {
                tvDate.setText(TimeUtil.getYYYYMM(date));
            }
        });

        calendarViews[1].initDate(new Date(), SimpleCalendarView.Mode.week);
        calendarViews[1].setOnMonthChangeListener(new SimpleCalendarView.OnMonthChangeListener() {
            @Override
            public void onMonthChange(Date date, int PageNum) {
                tvDate.setText(TimeUtil.getYYYYMM(date));
            }
        });

    }
}
