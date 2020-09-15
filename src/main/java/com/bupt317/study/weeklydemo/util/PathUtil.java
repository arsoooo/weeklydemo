package com.bupt317.study.weeklydemo.util;

import com.alibaba.druid.support.spring.stat.annotation.Stat;
import com.bupt317.study.weeklydemo.config.StaticParams;
import org.springframework.boot.system.ApplicationHome;

import javax.servlet.http.HttpServletRequest;
import java.io.File;

public class PathUtil {

    /**
     * 获取ROOT PATH
     * root是webapp下的相对初始路径，如“img/user”
     * 返回的不带文件名和后缀
     */
    public static String getROOTPath(HttpServletRequest request, String root){
        if(StaticParams.RUNNING_ENVIRONMENT.equals("jar")){
            ApplicationHome home = new ApplicationHome(PathUtil.class);
            File sysfile = home.getSource();
            String jarPath = sysfile.getParentFile().toString();
            return jarPath + "/" + StaticParams.STATIC_FOLD_NAME + "/" + root;
        }else {
            // 其余默认是windows环境
            return request.getServletContext().getRealPath(root);
        }
    }

}
