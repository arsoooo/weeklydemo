package com.bupt317.study.weeklydemo.util;

import com.bupt317.study.weeklydemo.config.StaticParams;

public class NoticeUtil {
    /**
     * notice的status -> 成中文属性
     * 用于赋给noticeVO
     */
    public static String status2desc(String status){
        String desc;
        switch(status){
            case StaticParams.NOTICE_CREATED:
                desc="未读";
                break;
            case StaticParams.NOTICE_FINISH:
                desc="已读";
                break;
            default:
                desc="未知";
        }
        return desc;
    }
}
