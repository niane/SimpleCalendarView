package com.yzg.simplecalendarview;

import android.content.Context;

import java.util.Date;

/**
 * Created by yzg on 2016/11/9.
 */

interface ISimpleCalendar {
    void OnDayClick(Date date, int selectedPos); //点击某一天
    void onPageChange(Date date, int pageNum);  //月日历切换
    Context getContext();
    UnitCalendar getInitCalendar();
}
