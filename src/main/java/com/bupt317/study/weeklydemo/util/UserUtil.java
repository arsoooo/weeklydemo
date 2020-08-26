package com.bupt317.study.weeklydemo.util;

import com.bupt317.study.weeklydemo.config.StaticParams;

public class UserUtil {

    /**
     * 管理员/普通用户 字符串 —> 数据库的perms
     * */
    public static String desc2Perm(String desc){
        String perm;
        switch(desc){
            case "管理员":
                perm=StaticParams.ADMIN_PERMS;
                break;
            case "普通用户":
                perm=StaticParams.USER_PERMS;
                break;
            default:
                perm="未知";
        }
        return perm;
    }

    /**
     * 数据库的perms —> 管理员/普通用户 字符串
     * */
    public static String perm2Desc(String perm){
        String desc;
        switch(perm){
            case StaticParams.ADMIN_PERMS:
                desc="管理员";
                break;
            case StaticParams.USER_PERMS:
                desc="普通用户";
                break;
            default:
                desc="未知";
        }
        return desc;
    }
}
