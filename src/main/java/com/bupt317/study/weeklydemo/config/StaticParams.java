package com.bupt317.study.weeklydemo.config;

public class StaticParams {
    /**
     * 登录验证返回
     * 正确or错误
     */
    public static final int SUCCESS_CODE = 0;
    public static final int FAIL_CODE = 1;

    /**
     * 项目状态
     * 进行中or已完成
     */
    public static final String PRJ_CREATED = "created";
    public static final String PRJ_FINISHED = "finish";

    /**
     * perms权限
     * 管理员："perms[admin:manager]"
     */
    public static final String ADMIN_PERMS = "admin:manager";
    public static final String USER_PERMS = "user:visitor";

}
