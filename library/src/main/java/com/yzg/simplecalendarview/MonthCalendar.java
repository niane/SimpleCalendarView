package com.yzg.simplecalendarview;

import java.util.Calendar;
import java.util.Date;

/**
 * Created by yzg on 2016/11/7.
 *
 * 月历
 */

class MonthCalendar extends UnitCalendar{

    public MonthCalendar(Date date) {
        super(date);
    }

    @Override
    void setPosition(int position) {
        if(position >= 0 && position < getUnitSize()){
            calendar.set(Calendar.DATE, position + 1);
        }
    }

    @Override
    int getUnitSize() {
        return calendar.getActualMaximum(Calendar.DATE);  //获得某个月最多有几天
    }

    //前一个月
    @Override
    UnitCalendar previousUnit() {
        Calendar prevCalendar = Calendar.getInstance();
        prevCalendar.setTime(calendar.getTime());
        prevCalendar.add(Calendar.MONTH, -1);  //基于日历规则，加上或者减去指定数额到给定的日历字段

        return new MonthCalendar(prevCalendar.getTime());
    }

    //后一个月
    @Override
    UnitCalendar nextUnit() {
        Calendar prevCalendar = Calendar.getInstance();
        prevCalendar.setTime(calendar.getTime());
        prevCalendar.add(Calendar.MONTH, 1);

        return new MonthCalendar(prevCalendar.getTime());
    }

    @Override
    int getDayOfWeek(int position){
        Calendar calendar1 = Calendar.getInstance();
        calendar1.setTime(calendar.getTime());
        calendar1.set(Calendar.DATE, position + 1);

        int week = calendar1.get(Calendar.DAY_OF_WEEK) - 1;
        if(week == 0) { week = 7;}

        return week;
    }

}
