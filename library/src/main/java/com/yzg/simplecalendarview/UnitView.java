package com.yzg.simplecalendarview;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;

/**
 * Created by yzg on 2016/11/9.
 */

abstract class UnitView extends FrameLayout{

    public UnitView(Context context) {
        this(context, null);
    }

    public UnitView(Context context, AttributeSet attrs) {
        super(context, attrs);
        View childView = LayoutInflater.from(context).inflate(getLayoutResource(), this, true);

        init(childView);
    }

    abstract protected int getLayoutResource();

    abstract protected void init(View view);

    abstract void setUnitCalendar(UnitCalendar monthCalendar, ISimpleCalendar simpleCalendar);

    abstract void notifyDataSetChanged();
}
