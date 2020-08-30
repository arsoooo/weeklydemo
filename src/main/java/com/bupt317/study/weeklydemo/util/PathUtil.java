package com.bupt317.study.weeklydemo.util;

import javax.servlet.http.HttpServletRequest;

public class PathUtil {

    /**
     * 获取ROOT PATH
     * root是webapp下的相对初始路径，如“img/user”
     * 返回的不带文件名和后缀
     */
    public static String getROOTPath(HttpServletRequest request, String root){
        return request.getServletContext().getRealPath(root);
    }

}
