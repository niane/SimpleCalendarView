package com.yzg.simplecalendarview;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by yzg on 2016/11/7.
 */

public class CalendarPageAdapter extends ACalendarPageAdapter {
    private SimpleCalendarView.Mode mode;

    public CalendarPageAdapter(ISimpleCalendar simpleCalendar, Date date, SimpleCalendarView.Mode mode){
        super(simpleCalendar);
        this.mode = mode;
        initUnitList(date);
    }

    @Override
    protected void initUnitList(Date date) {
        UnitCalendar currentCalendar = null;
        if(mode == SimpleCalendarView.Mode.month){
            currentCalendar = new MonthCalendar(date);

            //以初始的日期作为初始的选择位置
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(currentCalendar.getDate());
            selectedPosition = calendar.get(Calendar.DAY_OF_MONTH) -1;
        }else if(mode == SimpleCalendarView.Mode.week){
            currentCalendar = new WeekCalendar(date);

            //以初始的日期的星期作为初始的选择位置
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(currentCalendar.getDate());
            int week = calendar.get(Calendar.DAY_OF_WEEK) - 1;
            if(week == 0) { week = 7;}
            selectedPosition = week - 1;
        }

        if(currentCalendar != null){
            if(mCalendrList == null){
                mCalendrList = new ArrayList<>();
            }else {
                mCalendrList.clear();
            }
            originalPosition = MAX_PAGES / 2;
            mCalendrList.add(currentCalendar.previousUnit());
            mCalendrList.add(currentCalendar);
            mCalendrList.add(currentCalendar.nextUnit());
        }
    }

    protected UnitView createPageView(){
        return new CalendarPageView(mContext);
    }

}
