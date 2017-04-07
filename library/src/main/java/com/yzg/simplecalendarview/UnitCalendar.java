package com.yzg.simplecalendarview;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Created by yzg on 2016/11/9.
 *
 * 单元页日历基类
 */

abstract class UnitCalendar {
    protected Calendar calendar;
    protected int selectedPosition;
    protected List<CalendarEvent> events;

    public UnitCalendar(Date date){
        calendar = Calendar.getInstance();
        calendar.setFirstDayOfWeek(Calendar.MONDAY);
        calendar.setTime(date);
        events = new ArrayList<>();
    }

    /**获取当前日历年份*/
    public int getYear(){
        return calendar.get(Calendar.YEAR);
    }

    /**
     * 获取当前日历月份
     *
     * @return 0~11
     */
    public int getMonth(){
        return calendar.get(Calendar.MONTH);
    }

    /**获取当前日历天*/
    public int getDay(){
        return calendar.get(Calendar.DATE);
    }

    /**获取当前日期*/
    public Date getDate(){
        return calendar.getTime();
    }

    /**
     * 定位到当前周或月的第position天
     *
     * @param position 0~
     */
    abstract void setPosition(int position);

    /**
     * 当前周或月中的第position天是星期几
     *
     * @param position 从0开始
     * @return 1~7 对应星期一~七
     */
    abstract int getDayOfWeek(int position);

    /**获取当前周或月的天数*/
    abstract int getUnitSize();

    /**获取前一个月或一周的日历*/
    abstract UnitCalendar previousUnit();

    /**获取后一个月或一周的日历*/
    abstract UnitCalendar nextUnit();

    void setEvents(List<CalendarEvent> events){
        this.events.clear();
        this.events.addAll(events);
    }

    @Override
    public String toString() {
        return getYear()+ "年" + (getMonth() + 1) + "月" + getDay() + "日";
    }
}
