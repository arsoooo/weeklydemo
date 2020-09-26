package com.bupt317.study.weeklydemo.util;

import com.bupt317.study.weeklydemo.config.StaticParams;
import com.bupt317.study.weeklydemo.pojo.User;
import com.bupt317.study.weeklydemo.service.UserService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.crypto.SecureRandomNumberGenerator;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.http.HttpServletRequest;

public class UserUtil {

    @Autowired
    private UserService userService;

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

    /**
     * User -> 用户头像照片地址
     */
    public static String getUserImgPath(User user, HttpServletRequest request){
//        String path = PathUtil.getROOTPath(request, StaticParams.USER_IMG_ROOT)
//                + user.getId()
//                + ".jpg";
        String path = StaticParams.USER_IMG_ROOT + user.getId() + ".jpg";
        System.out.println("userUtil:" + path);
        return path;
    }

    /**
     * 由于用户界面经常用到
     * 封装一个获取当前用户的方法
     */
    public static User getLoginUser(){
        Subject subject = SecurityUtils.getSubject();
        // 这里return的是登陆时候的用户信息, 真正获得用户要用dbUser
        return (User)subject.getPrincipal();
    }

    /**
     * 通过salt和明文密码，获得加密后的密码
     */
    public static String getEncodedPwd(String pwd, String salt){
        // 加密次数
        int times = StaticParams.HASH_ITERATIONS;
        // 加密方法
        String algorithmName = StaticParams.HASH_ALGORITHM_NAME;
        // encode
        return new SimpleHash(algorithmName, pwd, salt, times).toString();
    }

    /**
     * 获得salt，这里是随机数
     */
    public static String getNewSalt(){
        return new SecureRandomNumberGenerator().nextBytes().toString();
    }
}
