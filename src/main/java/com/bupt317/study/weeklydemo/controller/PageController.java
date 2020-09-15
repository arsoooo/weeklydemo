package com.bupt317.study.weeklydemo.controller;

import com.bupt317.study.weeklydemo.pojo.User;
import com.bupt317.study.weeklydemo.service.UserService;
import com.bupt317.study.weeklydemo.util.UserUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;

@Controller
public class PageController {

    @Autowired
    private UserService userService;

    //////////////////////// 登录、注册、鉴权等跳转 ////////////////////////

    /**
     * 跳测试页面（登录界面）
     * */
    @RequestMapping("/")
    public String home(){
        //存入model
//        System.out.println("testThymeleaf");
//        model.addAttribute("name", "Thymeleaf");
        //返回test.html
        return "login/login";
    }

    /**
     * 未登录拦截，跳登录页面
     * */
    @RequestMapping("/toLogin")
    public String tologin(){
        return "login/login";
//        return "login/login";
    }


    /**
     * 跳转未授权提示页面
     * */
    @RequestMapping("/noAuthor")
    public String noAuthor(Model model){
//        System.out.println("find no author");
        return "util/noAuthor";
    }

    /**
     * 退出登录
     * */
    @RequestMapping("/logout")
    public void logout(){}

    /**
     * 跳注册界面
     */
    @RequestMapping("/toRegister")
    public String toRegister(){
        return "login/register";
    }


    //////////////////////// 用户中心 ////////////////////////

    /**
     * 跳用户中心（有个人信息和各种修改）
     */
    @RequestMapping("/editUser")
    public String editUser(Model model, HttpServletRequest request){
        User user = userService.getLoginDBUser();
        model.addAttribute("userImgPath", UserUtil.getUserImgPath(user, request));
        model.addAttribute("userName",user.getName());
        model.addAttribute("other", user.getOther());
        model.addAttribute("userPhone",user.getPhone());
        model.addAttribute("userEmail",user.getEmail());
        return "center/userCenter";
    }

    /**
     * 跳修改头像页面
     */
    @RequestMapping("/editUserImg")
    public String editUserImg(Model model, HttpServletRequest request){
        User user = userService.getLoginDBUser();
        model.addAttribute("userImgPath", UserUtil.getUserImgPath(user, request));
        return "center/editImg";
    }

    /**
     * 跳修改用户名 + 简介页面
     */
    @RequestMapping("/editUserName")
    public String editUserName(Model model, HttpServletRequest request){
        User user = userService.getLoginDBUser();
        model.addAttribute("userName", user.getName());
        model.addAttribute("userOther", user.getOther());
        return "center/editName";
    }

    /**
     * 跳修改密码
     */
    @RequestMapping("/editUserPwd")
    public String editUserPwd(){
        return "center/editPwd";
    }

    /**
     * 跳修改用户手机页面
     */
    @RequestMapping("/editUserPhone")
    public String editUserPhone(){
        return "center/editPhone";
    }

    /**
     * 跳修改用户邮箱页面
     */
    @RequestMapping("/editUserEmail")
    public String editUserEmail(){
        return "center/editEmail";
    }

}
