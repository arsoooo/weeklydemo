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
        return str2dateByFormat(str, "yyyy-MM-dd HH:mm:ss");
    }

    /**
     * Date -> str
     * 将Date类型 按格式转换成 字符串
     * 字符串格式："yyyy-MM-dd HH:mm:ss"
     */
    public static String date2str(Date date){
        return date2strByFormat(date, "yyyy-MM-dd HH:mm:ss");
    }

    /**
     * str -> Date
     * 将字符串 按格式转换成 Date类型
     * 字符串格式："yyyy-MM-dd"
     * @param str
     * @return
     */
    public static Date str2dateSimple(String str){
        return str2dateByFormat(str, "yyyy-MM-dd");
    }

    /**
     * Date -> str
     * 将Date类型 按格式转换成 字符串
     * 字符串格式："yyyy-MM-dd"
     */
    public static String date2strSimple(Date date){
        return date2strByFormat(date, "yyyy-MM-dd");
    }

    ////// 工具中的工具 //////
    /**
     * str -> Date
     * 将字符串 按format格式转换成 Date类型
     */
    public static Date str2dateByFormat(String str, String format){
        try {
            SimpleDateFormat formatter = new SimpleDateFormat(format);
            return formatter.parse(str);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Date -> str
     * 将Date类型 按format格式转换成 字符串
     */
    public static String date2strByFormat(Date date, String format){
        String str = null;
        if(null!=date){
            SimpleDateFormat f = new SimpleDateFormat(format);
            str = f.format(date);
        }
        return str;
    }

    public static void main(String[] args) {
        System.out.println(date2strSimple(null));

    }
}
