package com.yzg.simplecalendarview;

import java.util.Calendar;
import java.util.Date;

/**
 * Created by yzg on 2016/11/9.
 *
 * 一周日历
 * 从周一开始
 */

class WeekCalendar extends UnitCalendar {

    public WeekCalendar(Date date) {
        super(date);
    }

    @Override
    void setPosition(int position) {
        if(position >= 0 && position < getUnitSize()){
            int week = calendar.get(Calendar.DAY_OF_WEEK) - 1;
            if(week == 0) {week = 7;}   //星期日

            calendar.add(Calendar.DATE, position - week + 1);
        }
    }

    @Override
    int getDayOfWeek(int position) {
        return position + 1;
    }


    @Override
    int getUnitSize() {
        return 7;
    }

    @Override
    UnitCalendar previousUnit() {
        Date date = (Date) calendar.getTime().clone();
        Calendar prevCalendar = Calendar.getInstance();
        prevCalendar.setTime(date);
        prevCalendar.add(Calendar.DATE, -7);

        return new WeekCalendar(prevCalendar.getTime());
    }

    @Override
    UnitCalendar nextUnit() {
        Date date = (Date) calendar.getTime().clone();
        Calendar nextCalendar = Calendar.getInstance();
        nextCalendar.setTime(date);
        nextCalendar.add(Calendar.DATE, 7);

        return new WeekCalendar(nextCalendar.getTime());
    }
}
