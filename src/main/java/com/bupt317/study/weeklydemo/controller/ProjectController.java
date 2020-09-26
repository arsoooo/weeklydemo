package com.bupt317.study.weeklydemo.controller;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.bean.copier.CopyOptions;
import com.bupt317.study.weeklydemo.config.StaticParams;
import com.bupt317.study.weeklydemo.pojo.Project;
import com.bupt317.study.weeklydemo.pojo.Projectmember;
import com.bupt317.study.weeklydemo.pojo.User;
import com.bupt317.study.weeklydemo.service.ProjectService;
import com.bupt317.study.weeklydemo.service.ProjectmemberService;
import com.bupt317.study.weeklydemo.service.UserService;
import com.bupt317.study.weeklydemo.util.DateUtil;
import com.bupt317.study.weeklydemo.util.JsonUtil;
import com.bupt317.study.weeklydemo.util.ProjectUtil;
import com.bupt317.study.weeklydemo.vo.DataVO;
import com.bupt317.study.weeklydemo.vo.ProjectVO;
import com.bupt317.study.weeklydemo.vo.UserVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.xml.crypto.Data;
import java.util.Date;
import java.util.List;

@RestController
public class ProjectController {

    @Autowired
    private ProjectService projectService;

    @Autowired
    private ProjectmemberService projectmemberService;

    @Autowired
    private UserService userService;

    //////////////////////// 所有用户 ////////////////////////

    /**
     * 所有用户
     * 新建一个项目
     */
    @PostMapping("/projects")
    public DataVO addProject(
            Project project,
            @RequestParam(value = "deadlineStr") String deadlineStr,
            @RequestParam(value = "users") String users
    ){
        // project只自动装载了title和content
        // 把状态、创建时间、截止时间 装载至 project
        project.setStatus(StaticParams.PRJ_CREATED);
        project.setCreateTime(new Date());
        project.setDeadline(DateUtil.str2date(deadlineStr));
        // insert project
        projectService.addProject(project);

        // 获得id
        int pid = project.getId();
        // 解析json，获得选中的user
        List<UserVO> userVOList = JsonUtil.jsonStr2UserVOList(users);
        for (UserVO userVO: userVOList) {
            // insert project member
            projectmemberService.addProjectmember(new Projectmember(pid, userVO.getId()));
        }

        // 告诉前端ok
        return DataVO.success();
    }

    /**
     * 所有用户（也可改只能管理员，加上一个用户单独的检查，同report）
     * 根据pid查看项目信息
     */
    @GetMapping("/projects/{pid}")
    public DataVO getProjectByPid(@PathVariable("pid") int pid){
        ProjectVO projectVO = projectService.getProjectVOByPid(pid);
        return DataVO.success(projectVO);
    }

    /**
     * 所有用户（也可改只能管理员，加上一个用户单独的检查，同report）
     * 编辑一个项目的内容
     */
    @PutMapping("/projects/{pid}")
    public DataVO editProjectByPid(
            @PathVariable("pid") int pid,
            @RequestParam("deadlineTimeStr") String deadlineStr,
            @RequestParam("statusStr") String statusStr,
            @RequestParam("delUsers") String delUsers,
            @RequestParam("addUsers") String addUsers,
            Project project
    ){
        // 获得原始数据
        Project dbProject = projectService.getProjectByPid(pid);
        // 准备好修改后的project(会自动接收title、content)，赋给dbProject
        String status = ProjectUtil.desc2status(statusStr);
        if(StaticParams.PRJ_FINISHED.equals(status)){project.setFinishTime(new Date());}  // 已结项就赋结束时间
        project.setStatus(status);  // 赋状态值
        project.setDeadline(DateUtil.str2date(deadlineStr)); // 赋deadline
        BeanUtil.copyProperties(project, dbProject,
                true, CopyOptions.create().setIgnoreNullValue(true));  // 把修改值给到dbProject
        // 数据库更新修改的dbproject
        projectService.updateProject(dbProject);
        // 删除projectmember表
        List<UserVO> delUserVOList = JsonUtil.jsonStr2UserVOList(delUsers);
        for (UserVO userVO : delUserVOList) {
            // 根据pid和uid删除projectmember
            projectmemberService.delProjectMemberByPidAndUid(dbProject.getId(), userVO.getId());
        }
        // 根据addUsers增加projectmember表
        List<UserVO> addUserVOList = JsonUtil.jsonStr2UserVOList(addUsers);
        for (UserVO userVO : addUserVOList) {
            // 根据pid和uid增加projectmember
            projectmemberService.addProjectmember(new Projectmember(dbProject.getId(), userVO.getId(), "新增人员"));
        }
        return DataVO.success();
    }

    //////////////////////// 管理员 ////////////////////////

    /**
     * 管理员
     * 查看所有项目
     */
    @GetMapping("/admin/projects")
    public DataVO findProjects(Integer page, Integer limit){
        return projectService.findData(page, limit);
    }

    /**
     * 管理员
     * 查看一个用户的所有项目
     */
    @GetMapping("/admin/projects/users/{uid}")
    public DataVO findProjectsByUid(@PathVariable("uid") int uid, Integer page, Integer limit){
        return projectService.findDataByUid(uid, page, limit);
    }

    /**
     * 管理员
     * 根据pid删除一个项目
     */
    @DeleteMapping("/admin/projects/{pid}")
    public DataVO delProjectsByPid(@PathVariable("pid") int pid){
        int count = projectService.deleteByPid(pid);
        return DataVO.success(count);
    }

    //////////////////////// 普通用户 ////////////////////////

    /**
     * 普通用户
     * 根据登录用户，查询用户的所有项目
     */
    @GetMapping("/projects/users")
    public DataVO findProjectsByLoginUid(Integer page, Integer limit){
        User user = userService.getLoginDBUser();
        return projectService.findDataByUid(user.getId(), page, limit);
    }

}
