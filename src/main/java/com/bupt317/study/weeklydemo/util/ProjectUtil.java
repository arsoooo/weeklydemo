package com.bupt317.study.weeklydemo.util;

import com.bupt317.study.weeklydemo.config.StaticParams;

public class ProjectUtil {

    /**
     * project的status -> 成中文属性
     * 用于赋给projectVO
     */
    public static String status2desc(String status){
        String desc;
        switch(status){
            case StaticParams.PRJ_CREATED:
                desc="进行中";
                break;
            case StaticParams.PRJ_FINISHED:
                desc="已结项";
                break;
            default:
                desc="未知";
        }
        return desc;
    }

    /**
     * 中文属性 -> project的status
     * 用于赋给project
     */
    public static String desc2status(String desc){
        String status;
        switch(desc){
            case "进行中":
                status=StaticParams.PRJ_CREATED;
                break;
            case "已结项":
                status=StaticParams.PRJ_FINISHED;
                break;
            default:
                status="未知";
        }
        return status;
    }
}
