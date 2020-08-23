package com.bupt317.study.weeklydemo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class AdminPageController {

    /**
     * 跳管理员页面
     * */
    @RequestMapping("/adminHome")
    public String adminHome(){
        // 测试一下能不能拿到当前user -- 可以
//        Subject subject = SecurityUtils.getSubject();
//        User user = (User)subject.getPrincipal();
//        model.addAttribute("user", user);
        return "admin/admin-index";
    }

    /**
     * 跳转管理所有用户页面
     */
    @RequestMapping("/adminListUser")
    public String adminListUser(){
        return "admin/userManag";
    }

    /**
     * 跳转查看用户的项目页面
     * layui传参方法：/#/uid=
     * 会收到？uid,不用获取，直接跳转还在
     */
    @RequestMapping("/adminListUserProject")
    public String adminListUserProject(){
        return "admin/perspro";
    }

    /**
     * 跳转管理所有项目页面
     */
    @RequestMapping("/adminListProject")
    public String adminListProject(){
        return "admin/proManag";
    }

    /**
     * 跳转新增项目页面
     */
    @RequestMapping("/adminAddProject")
    public String adminAddProject(){
        return  "admin/addProject";
    }

    /*
     * 跳增加用户页面
     * */
    @RequestMapping("/add")
    public String add(){
        return "student/add";
    }

    /*
     * 跳修改用户页面
     * */
    @RequestMapping("/update")
    public String update(){
        return "student/update";
    }


}
