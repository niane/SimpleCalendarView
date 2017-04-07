package com.yzg.simplecalendarview;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.GridView;
import android.widget.TextView;

/**
 * Created by yzg on 2016/11/8.
 */

class CalendarPageView extends UnitView {
    private GridView calendarGrid;
    private int[] weekResId;
    private TextView[] week;

    private UnitCalendarAdapter adapter;

    public CalendarPageView(Context context) {
        this(context, null);
    }

    public CalendarPageView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected int getLayoutResource() {
        return R.layout.month_page_item;
    }

    protected void init(View view) {
        calendarGrid = (GridView) view.findViewById(R.id.calendar_grid);

        weekResId = new int[]{R.id.tv_monday, R.id.tv_tuesday, R.id.tv_wednesday, R.id.tv_thursday, R.id.tv_friday, R.id.tv_saturday, R.id.tv_sunday};
        week = new TextView[weekResId.length];

        String[] weekArr = getContext().getResources().getStringArray(R.array.week);
        for (int i = 0; i < weekResId.length; i++) {
            TextView tv = (TextView) view.findViewById(weekResId[i]);
            tv.setText(weekArr[i]);
            week[i] = tv;
        }
    }

    void setUnitCalendar(UnitCalendar calendar, ISimpleCalendar simpleCalendar) {
        if (adapter == null) {
            adapter = new UnitCalendarAdapter(calendar, simpleCalendar);
            calendarGrid.setAdapter(adapter);
        } else {
            adapter.setCalendar(calendar);
            adapter.notifyDataSetChanged();
        }
    }

    void notifyDataSetChanged(){
        if(adapter != null){
            adapter.notifyDataSetChanged();
        }
    }
}
