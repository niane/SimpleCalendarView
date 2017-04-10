package com.yzg.simplecalendardemo;

import java.util.Date;

/**
 * Created by yzg on 2017/1/5.
 *
 * 时间日期工具类
 */

public class TimeUtil {

    /**
     *
     * @param date
     * @return yyyy-MM
     */
    public static String getYYYYMM(Date date){
        if(date == null) return "";

        java.text.DateFormat dateFormat = new java.text.SimpleDateFormat("yyyy-MM");
        return dateFormat.format(date);
    }

    /**
     *
     * @param date
     * @return yyyy-MM-dd
     */
    public static String getYYYYMMdd(Date date){
        if(date == null) return "";

        java.text.DateFormat dateFormat = new java.text.SimpleDateFormat("yyyy-MM-dd");
        return dateFormat.format(date);
    }
}
