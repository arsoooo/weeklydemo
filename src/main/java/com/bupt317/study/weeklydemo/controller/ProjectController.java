package com.bupt317.study.weeklydemo.controller;

import com.bupt317.study.weeklydemo.config.StaticParams;
import com.bupt317.study.weeklydemo.pojo.Project;
import com.bupt317.study.weeklydemo.pojo.Projectmember;
import com.bupt317.study.weeklydemo.service.ProjectService;
import com.bupt317.study.weeklydemo.service.ProjectmemberService;
import com.bupt317.study.weeklydemo.util.DateUtil;
import com.bupt317.study.weeklydemo.util.JsonUtil;
import com.bupt317.study.weeklydemo.vo.DataVO;
import com.bupt317.study.weeklydemo.vo.UserVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@RestController
public class ProjectController {

    @Autowired
    private ProjectService projectService;

    @Autowired
    private ProjectmemberService projectmemberService;

    @GetMapping("/projects")
    public DataVO findProjects(){
        return projectService.findData();
    }

    @GetMapping("/projects/{uid}")
    public DataVO findProjectsByUid(@PathVariable("uid") int uid){
        return projectService.findDataByUid(uid);
    }

    @PostMapping("/projects")
    public DataVO addProject(
            @RequestParam(value = "title") String title,
            @RequestParam(value = "deadline") String deadline,
            @RequestParam(value = "content") String content,
            @RequestParam(value = "users") String users
    ){
        // 把表单信息+创建时间 装载至 project
        Project project = new Project(
                title,
                StaticParams.PRJ_CREATED,
                new Date(),
                DateUtil.str2date(deadline),
                content
                );
        // insert project
        projectService.addProject(project);

        // 获得id
        int pid = project.getId();
        // 解析json，获得选中的user
        List<UserVO> userVOList = JsonUtil.jsonStr2Users(users);
        for (UserVO userVO: userVOList) {
            // insert project member
            projectmemberService.addProjectmember(new Projectmember(pid, userVO.getId(), userVO.getName()));
        }

        // 告诉前端ok
        return DataVO.success(title);
    }

}
