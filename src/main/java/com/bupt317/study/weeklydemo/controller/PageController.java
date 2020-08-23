package com.bupt317.study.weeklydemo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class PageController {
    /*
     * 跳测试页面（主页面）
     * */
    @RequestMapping("/home")
    public String home(){
        //存入model
//        System.out.println("testThymeleaf");
//        model.addAttribute("name", "Thymeleaf");
        //返回test.html
        return "home-simple";
    }

    /*
     * 未登录拦截，跳登录页面
     * */
    @RequestMapping("/toLogin")
    public String tologin(){
        return "login/login";
    }


    /*
     * 跳转未授权提示页面
     * */
    @RequestMapping("/noAuthor")
    public String noAuthor(Model model){
        System.out.println("find no autor");
        return "util/noAuthor";
    }

    /*
     * 退出登录
     * */
    @RequestMapping("/logout")
    public void logout(){}
}
