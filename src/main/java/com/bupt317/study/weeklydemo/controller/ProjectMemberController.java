package com.bupt317.study.weeklydemo.controller;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.bean.copier.CopyOptions;
import com.bupt317.study.weeklydemo.pojo.Projectmember;
import com.bupt317.study.weeklydemo.service.ProjectmemberService;
import com.bupt317.study.weeklydemo.vo.DataVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ProjectMemberController {

    @Autowired
    private ProjectmemberService projectmemberService;


    /**
     * 所有用户
     * 编辑一个用户在一个项目中的分工
     */
    @PutMapping("/projectMembers/{pid}")
    public DataVO editProjectMemberTargetByPid(
            @PathVariable("pid") int pid,
            @RequestParam("fieldName") String fieldName,
            Projectmember projectmember
    ){
        if(!fieldName.equals("target")){
            return DataVO.fail("error: field is not target ！");
        }
        // 原始的DBPm
        Projectmember dbProjectMember = projectmemberService.getByPidAndUid(pid, projectmember.getUid());
        // 用pm修改DBPm
        BeanUtil.copyProperties(projectmember, dbProjectMember,
                true, CopyOptions.create().setIgnoreNullValue(true));  // 把修改值给到dbProjectMember
        // 更新DBPm
        projectmemberService.updateProjectMember(dbProjectMember);
        return DataVO.success();
    }
}
