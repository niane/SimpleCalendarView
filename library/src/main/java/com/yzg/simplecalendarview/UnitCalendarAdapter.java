package com.yzg.simplecalendarview;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

/**
 * Created by yzg on 2016/11/9.
 *
 * 单元页日历适配器
 */

class UnitCalendarAdapter extends BaseAdapter {
    protected Context mContext;
    protected LayoutInflater inflater;

    protected ISimpleCalendar simpleCalendar;
    protected UnitCalendar mCalendar;
//    protected UnitCalendar todayCalendar;

    protected int offset = 0;
    private  int count = 0;

    public UnitCalendarAdapter(UnitCalendar calendar, ISimpleCalendar simpleCalendar) {
        this.mContext = simpleCalendar.getContext();
        this.simpleCalendar = simpleCalendar;
        inflater = LayoutInflater.from(mContext);
        setCalendar(calendar);
    }

    void setCalendar(UnitCalendar calendar){
        this.mCalendar = calendar;
        offset = mCalendar.getDayOfWeek(0) - 1;

        if(calendar instanceof WeekCalendar){
//            todayCalendar = new WeekCalendar(new Date());
            count = mCalendar.getUnitSize() + offset;
        }else if(calendar instanceof MonthCalendar){
//            todayCalendar = new MonthCalendar(new Date());
            count = 42;
        }
    }

    @Override
    public int getCount() {
        return count;
    }

    @Override
    public Object getItem(int position) {
        return position + 1;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        if(convertView == null){
            convertView = inflater.inflate(R.layout.day_view_layout, null);
        }

        if(position < offset || position >= mCalendar.getUnitSize() + offset){
            convertView.setVisibility(View.INVISIBLE);
        }else {
            convertView.setVisibility(View.VISIBLE);
            final TextView tvDay = (TextView) convertView.findViewById(R.id.tv_day);
            final TextView tvAppend = (TextView) convertView.findViewById(R.id.tv_append);

            mCalendar.setPosition(position - offset);
            tvDay.setText(mCalendar.getDay() + "");

            if(mCalendar instanceof MonthCalendar){
                if(isSameDay(mCalendar, simpleCalendar.getInitCalendar())){
                    tvDay.setSelected(true);
                }else {
                    tvDay.setSelected(false);
                }
            }else {
                if(mCalendar.selectedPosition + offset == position){
                    tvDay.setSelected(true);
                }else {
                    tvDay.setSelected(false);
                }
            }

            tvAppend.setText("");
            tvAppend.setEnabled(false);
            if(position - offset < mCalendar.events.size() ){
                CalendarEvent event = mCalendar.events.get(position - offset);
                if(event.eventEnabled()){
                    tvAppend.setText(event.getEvent());
                    tvAppend.setEnabled(true);
                }
            }

            convertView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mCalendar.setPosition(position - offset);
                    mCalendar.selectedPosition = position - offset;
                    notifyDataSetChanged();
                    simpleCalendar.OnDayClick(mCalendar.getDate(), mCalendar.selectedPosition);
                }
            });
        }

        return convertView;
    }

    protected boolean isSameDay(UnitCalendar calendar, UnitCalendar anotherCalenar){
        return !calendar.getDate().after(anotherCalenar.getDate()) && !calendar.getDate().before(anotherCalenar.getDate());
    }
}
