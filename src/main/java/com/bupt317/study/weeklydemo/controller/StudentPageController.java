package com.bupt317.study.weeklydemo.controller;

import com.bupt317.study.weeklydemo.pojo.User;
import com.bupt317.study.weeklydemo.service.UserService;
import com.bupt317.study.weeklydemo.util.UserUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

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
        User user = userService.getLoginDBUser();
        model.addAttribute("userImgPath", UserUtil.getUserImgPath(user, request));
        model.addAttribute("userName",user.getName());
        return "student/student-index";
    }

    /**
     * 跳显示用户公告页面
     */
    @RequestMapping("/studentListNotice")
    public String studentListNotice(){
        return "student/stuNotice";
    }

    /**
     * 弹窗查看公告详情
     */
    @RequestMapping("/studentShowNotice")
    public String userShowNotice(
            Model model,
            @RequestParam(value = "nid") int nid
    ){
        model.addAttribute("nid", nid);
        return "student/stuShowNotice";
    }

    /**
     * 跳学生所有周报页面
     */
    @RequestMapping("/studentListReport")
    public String studentListReport(){
        return "student/stuPublication";
    }

    /**
     * 跳上传周报页面
     */
    @RequestMapping("/studentAddReport")
    public String studentAddReport(){
        return "student/stuAddPublication";
    }

    /**
     * 跳学生查看周报详情页面
     */
    @RequestMapping("/studentShowReport")
    public String studentShowReport(
            Model model,
            @RequestParam(value = "rid") int rid
    ){
        model.addAttribute("rid", rid);
        return "student/stuShowPublication";
    }

    /**
     * 跳学生所有项目页面
     */
    @RequestMapping("/studentListProject")
    public String studentListProject(){
        return "student/stuProject";
    }

    /**
     * 跳学生编辑项目页面
     */
    @RequestMapping("/studentEditProject")
    public String studentEditProject(
            Model model,
            @RequestParam(value = "pid") int pid
    ){
        model.addAttribute("pid", pid);
        return "student/stuEditProject";
    }

    /**
     * 跳学生新增项目页面
     */
    @RequestMapping("/studentAddProject")
    public String studentAddProject(
    ){
        return "student/stuAddProject";
    }
}
