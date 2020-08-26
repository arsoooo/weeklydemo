package com.bupt317.study.weeklydemo.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.bupt317.study.weeklydemo.mapper.ProjectmemberMapper;
import com.bupt317.study.weeklydemo.mapper.UserMapper;
import com.bupt317.study.weeklydemo.pojo.Projectmember;
import com.bupt317.study.weeklydemo.pojo.User;
import com.bupt317.study.weeklydemo.service.ProjectmemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ProjectmemberServiceImpl implements ProjectmemberService {

    @Autowired
    private ProjectmemberMapper projectmemberMapper;

    @Override
    public List<Integer> findUidsByPid(int pid) {
        return projectmemberMapper.findUidsByPid(pid);
    }



    @Override
    public List<Integer> findPidsByUid(int uid) {
        return projectmemberMapper.findPidsByUid(uid);
    }

    @Override
    public int addProjectmember(Projectmember projectmember) {
        return projectmemberMapper.insert(projectmember);
    }

    @Override
    public int delProjectMemberByPidAndUid(int pid, int uid) {
        return projectmemberMapper.delProjectMemberByPidAndUid(pid, uid);
    }

}
