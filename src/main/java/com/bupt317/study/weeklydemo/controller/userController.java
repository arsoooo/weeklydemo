package com.bupt317.study.weeklydemo.controller;

import com.bupt317.study.weeklydemo.pojo.User;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class userController {

    /*
    * 跳测试页面（主页面）
    * */
    @RequestMapping("/test")
    public String testThymeleaf(Model model){
        //存入model
        model.addAttribute("name", "Thymeleaf");
        //返回test.html
        return "test";
    }

    /*
     * 未登录拦截，跳登录页面
     * */
    @RequestMapping("/toLogin")
    public String tologin(){
        return "login/login";
    }

    /*
     * 对输入的账户密码登录验证
     * */
    @RequestMapping("/login")
    public String login(String name, String password, Model model){
//        1.SecurityUtils获得subject
        Subject subject = SecurityUtils.getSubject();
//        2.UsernamePasswordToken存user进去
        UsernamePasswordToken token = new UsernamePasswordToken(name, password);
        try {
//        3.执行登录方法subject.login(token);
            subject.login(token);
//        正确跳转
            return "redirect:/test";
        }catch(UnknownAccountException e){
            model.addAttribute("msg", "用户名不存在！");
            // 跳
            return "login/login";
        }catch (IncorrectCredentialsException e) {
            model.addAttribute("msg", "密码不正确！");
            model.addAttribute("name", name);
            return "login/login";
        }
    }

    /*
     * 跳转未授权提示页面
     * */
    @RequestMapping("/noAuthor")
    public String noAuth(Model model){

        return "admin/noAuthor";
    }

    /*
     * 管理员页面
     * */
    @RequestMapping("/adminManager")
    public String adminManager(Model model){
        // 测试一下能不能拿到当前user
        Subject subject = SecurityUtils.getSubject();
        User user = (User)subject.getPrincipal();
        model.addAttribute("user", user);
        return "admin/adminManager";
    }

    /*
    * 跳增加用户页面
    * */
    @RequestMapping("/add")
    public String add(){
        return "user/add";
    }

    /*
    * 跳修改用户页面
    * */
    @RequestMapping("/update")
    public String update(){
        return "user/update";
    }

    /*
     * 退出登录
     * */
    @RequestMapping("/logout")
    public void logout(){}
}
