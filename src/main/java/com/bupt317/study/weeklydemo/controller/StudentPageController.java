package com.bupt317.study.weeklydemo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class StudentPageController {
    /*
     * 跳学生页面
     * */
    @RequestMapping("/studentHome")
    public String adminManager(){
        return "student/student-index";
    }
}
