package com.yzg.simplecalendarview;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.View;

import java.util.Date;
import java.util.List;

/**
 * Created by yzg on 2016/11/8.
 */

public class SimpleCalendarView extends ViewPager implements ISimpleCalendar {
    private ACalendarPageAdapter adapter;
    private OnMonthChangeListener onMonthChangeListener;
    private OnDayClickListener onDayClickListener;
    private UnitCalendar initCalendar;

    public SimpleCalendarView(Context context) {
        this(context, null);
    }

    public SimpleCalendarView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public void initDate(Date date) {
        initDate(date, Mode.month);
    }

    public void resetDate(Date date) {
        initCalendar = new MonthCalendar(date);
        adapter.initUnitList(date);
        adapter.notifyDataSetChanged();
        setCurrentItem(adapter.getOriginalPosition() + 1);
        onPageChange(date, adapter.getOriginalPosition() + 1);
    }

    public void initDate(Date date, Mode mode) {
        if (mode == null) {
            mode = Mode.month;
        }

        initCalendar = new MonthCalendar(date);
        adapter = null;
        adapter = new CalendarPageAdapter(this, date, mode);

        setAdapter(adapter);
        addOnPageChangeListener(new OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {

                //单元页日历的选择的位置改变了需刷新界面
                UnitCalendar calendar = adapter.getItem(position);
                calendar.selectedPosition = adapter.selectedPosition;
                calendar.setPosition(calendar.selectedPosition);
                View view = findViewWithTag(calendar);
                if (view instanceof UnitView) {
                    ((UnitView) view).notifyDataSetChanged();
                }

                onPageChange(adapter.getItem(position).getDate(), position);

                if (position == adapter.getOriginalPosition() + adapter.getCalendarSize() - 1) {
                    adapter.expandToPageEnd();
                } else if (position == adapter.getOriginalPosition()) {
                    adapter.expandToPageHeader();
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        setCurrentItem(adapter.getOriginalPosition() + 1);
    }

    /**
     * 获取当前页日历选择的日期
     **/
    public Date getCurrentDate() {
        UnitCalendar calendar = adapter.getItem(getCurrentItem());
        calendar.setPosition(calendar.selectedPosition);
        return calendar.getDate();
    }

    /**
     * 更新单元页日历的事件
     **/
    public void updatePageView(List<CalendarEvent> events, int pageNum) {
        UnitCalendar calendar = adapter.getItem(pageNum);
        calendar.setEvents(events);

        View view = findViewWithTag(calendar);
        if (view instanceof UnitView) {
            ((UnitView) view).notifyDataSetChanged();
        }
    }

    @Override
    public void OnDayClick(Date date, int selectedPos) {
        if (adapter != null) {
            adapter.selectedPosition = selectedPos;
        }
        if (onDayClickListener != null) {
            onDayClickListener.OnDayClick(date);
        }
    }

    @Override
    public void onPageChange(Date date, int pageNum) {
        if (onMonthChangeListener != null) {
            onMonthChangeListener.onMonthChange(date, pageNum);
        }
    }

    @Override
    public UnitCalendar getInitCalendar() {
        return initCalendar;
    }

    /**
     * @param listener
     */
    public void setOnMonthChangeListener(OnMonthChangeListener listener) {
        onMonthChangeListener = listener;
    }

    public void setOnDayClickListener(OnDayClickListener onDayClickListener) {
        this.onDayClickListener = onDayClickListener;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int width = 0;
        int height = 0;
        for (int i = 0; i < getChildCount(); i++) {
            final View child = getChildAt(i);
            child.measure(widthMeasureSpec, heightMeasureSpec);
            width = Math.max(width, child.getMeasuredWidth());
            height = Math.max(height, child.getMeasuredHeight());
        }

        heightMeasureSpec = MeasureSpec.makeMeasureSpec(height, MeasureSpec.EXACTLY);
        widthMeasureSpec = MeasureSpec.makeMeasureSpec(width, MeasureSpec.EXACTLY);

        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    public interface OnMonthChangeListener {
        void onMonthChange(Date date, int PageNum);
    }

    public interface OnDayClickListener {
        void OnDayClick(Date date);
    }

    public enum Mode {
        week,
        month;
    }
}
