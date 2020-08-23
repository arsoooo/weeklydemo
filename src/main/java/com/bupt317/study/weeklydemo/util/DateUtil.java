package com.bupt317.study.weeklydemo.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtil {
    /**
     * 返回两个日期的相差天数
     * @param oldDate
     * @param newDate
     * @return String
     */
    public static String days_between_date(Date oldDate, Date newDate){
        return (newDate.getTime()-oldDate.getTime())/(3600*24*1000)+"";
    }

    /**
     * str -> Date
     * 将字符串 按格式转换成 Date类型
     * 字符串格式："yyyy-MM-dd HH:mm:ss"
     * @param str
     * @return
     */
    public static Date str2date(String str){
        try {
            SimpleDateFormat formatter = new SimpleDateFormat( "yyyy-MM-dd HH:mm:ss");
            return formatter.parse(str);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Date -> str
     * 将Date类型 按格式转换成 字符串
     * 字符串格式："yyyy-MM-dd HH:mm:ss"
     */
    public static String date2str(Date date){
        SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return f.format(date);
    }

    public static void main(String[] args) {
        System.out.println(date2str(new Date()));

    }
}
