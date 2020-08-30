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
public class StudentPageController {

    @Autowired
    private UserService userService;

    /**
     * 跳学生主页
     */
    @RequestMapping("/studentHome")
    public String adminManager(Model model, HttpServletRequest request){
        // 记得恢复，传过去用来加载用户头像
        User user = UserUtil.getLoginUser();
        model.addAttribute("userImgPath", UserUtil.getUserImgPath(user, request));
        model.addAttribute("userName",user.getName());
        return "student/student-index";
    }

    /**
     * 跳用户中心（有个人信息和各种修改）
     */
    @RequestMapping("/studentEditUser")
    public String studentEditUser(Model model, HttpServletRequest request){
        User user = UserUtil.getLoginUser();
        model.addAttribute("userImgPath", UserUtil.getUserImgPath(user, request));
        model.addAttribute("userName",user.getName());
        return "student/userCenter";
    }

    /**
     * 跳修改头像页面
     */
    @RequestMapping("/studentEditUserImg")
    public String studentEditUserImg(Model model, HttpServletRequest request){
        User user = UserUtil.getLoginUser();
        model.addAttribute("userImgPath", UserUtil.getUserImgPath(user, request));
        return "student/editImg";
    }

    /**
     * 跳修改用户名 + 密码 + 简介页面
     */
    @RequestMapping("/studentEditUserNameAndPwd")
    public String studentEditUserNameAndPwd(Model model, HttpServletRequest request){
        User user = UserUtil.getLoginUser();
        model.addAttribute("userImgPath", UserUtil.getUserImgPath(user, request));
        return "student/editNameAndPwd";
    }

    /**
     * 跳修改用户邮箱页面
     */
    @RequestMapping("/studentEditUserEmail")
    public String studentEditUserEmail(Model model, HttpServletRequest request){
        User user = UserUtil.getLoginUser();
        model.addAttribute("userImgPath", UserUtil.getUserImgPath(user, request));
        return "student/editEmail";
    }

    /**
     * 跳修改用户手机页面
     */
    @RequestMapping("/studentEditUserPhone")
    public String studentEditUserPhone(Model model, HttpServletRequest request){
        User user = UserUtil.getLoginUser();
        model.addAttribute("userImgPath", UserUtil.getUserImgPath(user, request));
        return "student/editPhone";
    }
}
