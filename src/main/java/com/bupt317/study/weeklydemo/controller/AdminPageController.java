package com.bupt317.study.weeklydemo.controller;

import com.bupt317.study.weeklydemo.pojo.User;
import com.bupt317.study.weeklydemo.service.UserService;
import com.bupt317.study.weeklydemo.util.UserUtil;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;

@Controller
public class AdminPageController {

    @Autowired
    private UserService userService;

    /**
     * 跳管理员页面
     * 同时会把用户信息传过去，加载用户头像
     * */
    @RequestMapping("/adminHome")
    public String adminHome(Model model, HttpServletRequest request){
        // 记得恢复，传过去用来加载用户头像
        User user = userService.getLoginDBUser();
        model.addAttribute("userImgPath", UserUtil.getUserImgPath(user, request));
        model.addAttribute("userName",user.getName());
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
     * 跳转至编辑用户页面
     * 收到uid，传过去
     */
    @RequestMapping("/adminEditUser")
    public String adminEditUser(
            Model model,
            @RequestParam(value = "uid") int uid
    ){
        model.addAttribute("uid", uid);
        return "admin/userManaginfo";
    }

    /**
     * 跳转查看用户的项目页面
     * layui传参方法：/#/uid=
     * 会收到？uid,不用获取，直接跳转还在
     */
    @RequestMapping("/adminListUserProject")
    public String adminListUserProject(
            Model model,
            @RequestParam(value = "uid") int uid
    ){
        model.addAttribute("uid",uid);
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

    /**
     * 跳转编辑项目界面
     */
    @RequestMapping("/adminEditProject")
    public String adminEditProject(
            Model model,
            @RequestParam(value = "pid") int pid
    ){
        model.addAttribute("pid", pid);
        return "admin/proManaginfo";
    }

    /**
     * 跳转管理所有周报页面
     */
    @RequestMapping("/adminListReport")
    public String adminListReport(){
        return "admin/publiManag";
    }

    /**
     * 跳转编辑周报页面
     * 收到rid，传过去
     */
    @RequestMapping("/adminEditReport")
    public String adminEditReport(
            Model model,
            @RequestParam(value = "rid") int rid
    ){
        model.addAttribute("rid", rid);
        return "admin/publiManaginfo";
    }


    /**
     * 跳转查看用户的周报页面
     */
    @RequestMapping("/adminListUserReport")
    public String adminListUserReport(
        Model model,
        @RequestParam(value = "uid") int uid
    ){
        model.addAttribute("uid", uid);
        return "admin/perspubli";
    }

    /**
     * 跳转管理公告页面
     */
    @RequestMapping("/adminListNotice")
    public String adminListNotice(){
        return "admin/noticeManag";
    }

    /**
     * 跳转新增公告页面
     */
    @RequestMapping("/adminAddNotice")
    public String adminAddNotice(){
        return "admin/addNotice";
    }

    /**
     * 弹窗查看公告详情
     */
    @RequestMapping("/adminShowNotice")
    public String adminShowNotice(
        Model model,
        @RequestParam(value = "nid") int nid
    ){
        model.addAttribute("nid", nid);
        return "admin/noticeManaginfo";
    }
}
