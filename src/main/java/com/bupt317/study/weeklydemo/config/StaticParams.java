package com.bupt317.study.weeklydemo.config;

public class StaticParams {

    /**
     * 确定运行环境，自己设置
     * jar：用home.getSource()获得jar包运行位置的目录,
     * 并设置对应上传的文件名
     * windows：用request.getServletContext().getRealPath(root)获得当前项目根目录
     */
    public static String RUNNING_ENVIRONMENT = "windows";
    public static String STATIC_FOLD_NAME = "webapp";

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

    /**
     * 周报状态
     * 未评价or已评价
     */
    public static final String REPORT_CREATED = "uploaded";
    public static final String REPORT_FINISHED = "finish";

    /**
     * 公告状态
     * 未读or已读
     */
    public static final String NOTICE_CREATED = "created";
    public static final String NOTICE_FINISH = "finish";

    /**
     * 各种根目录ROOT PATH
     */
    public static final String USER_IMG_ROOT = "img/user/";
    public static final String USER_MODEL_ROOT = "doc/model/";
    public static final String USER_DOC_ROOT = "doc/user/";
    public static final String USER_REPORT_ROOT = "doc/report/";
    public static final String USER_REPORT_UPLOAD = "doc/reportUpload/";

    /**
     * 密码加密方式
     */
    public static final String HASH_ALGORITHM_NAME = "md5";// 加密方式
    public static final Integer HASH_ITERATIONS = 2;// 散列次数
}
