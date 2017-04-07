package com.yzg.simplecalendarview;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.Date;

/**
 * Created by yzg on 2016/11/9.
 */

abstract class ACalendarPageAdapter extends PagerAdapter {
    protected Context mContext;

    /**单元日历列表**/
    protected ArrayList<UnitCalendar> mCalendrList;

    /**日历控件**/
    protected ISimpleCalendar simpleCalendar;

    /**日历控件能够显示的最多单元页**/
    protected final int MAX_PAGES = 1000;

    /**展示单元日历数据的view，为避免无限创建view使用一个数组进行缓存达到重复利用*/
    private ArrayList<UnitView> pageViews = new ArrayList<>();

    /**第一个单元日历在ViewPager中的位置*/
    protected int originalPosition;

    /**单元页中选择的位置**/
    protected int selectedPosition = 0;

    public ACalendarPageAdapter(ISimpleCalendar calendar){
        this.simpleCalendar = calendar;
        mContext = calendar.getContext();
//        originalPosition = Integer.MAX_VALUE / 2;
//        initUnitList(date);
    }

    /**
     * 第一个月份在ViewPager中的位置
     */
    public int getOriginalPosition(){
        return originalPosition;
    }

    /**初始化单元日历列表*/
    abstract protected void initUnitList(Date date);

    /**
     * 向后添加5个月份
     */
    public void expandToPageEnd(){
        for(int i = 1; i <= 5; i++){
            mCalendrList.add(mCalendrList.size(), mCalendrList.get(mCalendrList.size() - 1).nextUnit());
        }
        notifyDataSetChanged();
    }

    /**
     * 向前添加5个月份
     */
    public void expandToPageHeader(){
        for(int i = 1; i <= 5; i++){
            mCalendrList.add(0, mCalendrList.get(0).previousUnit());
        }
        originalPosition -= 5;
        notifyDataSetChanged();
    }

    abstract protected UnitView createPageView();

    int getCalendarSize(){
        return mCalendrList.size();
    }

    UnitCalendar getItem(int position){
        return mCalendrList.get(position - originalPosition);
    }

    protected UnitView getPageView(){
        UnitView view = null;

        if(pageViews.isEmpty()){
            pageViews.add(0, createPageView());

        }
        view = pageViews.remove(0);

        return view;
    }

    @Override
    public int getCount() {
        return MAX_PAGES;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        if(position - originalPosition < mCalendrList.size()
                && position - originalPosition >= 0){

            UnitCalendar unitCalendar = mCalendrList.get(position - originalPosition);
            unitCalendar.setPosition(selectedPosition);
            unitCalendar.selectedPosition = selectedPosition;

            UnitView view = getPageView();
            view.setTag(unitCalendar);
            ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
            container.addView(view, params);

            view.setUnitCalendar(unitCalendar, simpleCalendar);
            return  view;
        }

        return null;
    }

    @Override
    public int getItemPosition(Object object) {
        return POSITION_NONE;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        View view = (View) object;
        container.removeView(view);

        if(view != null){
            view.setTag(null);
            pageViews.add((UnitView) view);
        }

    }
}
