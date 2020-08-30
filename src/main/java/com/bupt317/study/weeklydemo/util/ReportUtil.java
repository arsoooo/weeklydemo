package com.bupt317.study.weeklydemo.util;

import com.bupt317.study.weeklydemo.config.StaticParams;

public class ReportUtil {

    /**
     * report的status -> 成中文属性
     */
    public static String status2desc(String status){
        String desc;
        switch(status){
            case StaticParams.REPORT_CREATED:
                desc="未评价";
                break;
            case StaticParams.REPORT_FINISHED:
                desc="已评价";
                break;
            default:
                desc="未知";
        }
        return desc;
    }

    /**
     * 中文属性 -> report的status
     */
    public static String desc2status(String desc){
        String status;
        switch(desc){
            case "未评价":
                status=StaticParams.REPORT_CREATED;
                break;
            case "已评价":
                status=StaticParams.REPORT_FINISHED;
                break;
            default:
                status="未知";
        }
        return status;
    }
}
