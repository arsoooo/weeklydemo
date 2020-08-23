package com.bupt317.study.weeklydemo.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.bupt317.study.weeklydemo.mapper.ProjectmemberMapper;
import com.bupt317.study.weeklydemo.pojo.Projectmember;
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
    public List<Integer> findMemberIdByPid(int pid) {
        List<Projectmember> pms = projectmemberMapper.selectList(
                new QueryWrapper<Projectmember>()
                        .eq("pid", pid)
                        .select("uid")
        );
        List<Integer> uids = new ArrayList<>();
        for (Projectmember pm : pms) {
            uids.add(pm.getUid());
        }
        return uids;
    }

    @Override
    public String findMemberNamesByPid(int pid) {
        List<Projectmember> pms = projectmemberMapper.selectList(
                new QueryWrapper<Projectmember>()
                        .eq("pid", pid)
                        .select("uname"));
        StringBuilder names = new StringBuilder();
        for (Projectmember pm : pms) {
            names.append(pm.getUname()).append(" ");
        }
        return names.toString();
    }

    @Override
    public List<Integer> findPidsByUid(int uid) {
        List<Integer> pids = new ArrayList<>();
        List<Projectmember> pms = projectmemberMapper.selectList(
                new QueryWrapper<Projectmember>()
                        .eq("uid", uid)
                        .select("pid")
                        .orderByDesc("id"));
        for (Projectmember pm : pms) {
            pids.add(pm.getPid());
        }
        return pids;
    }

    @Override
    public int addProjectmember(Projectmember projectmember) {
        return projectmemberMapper.insert(projectmember);
    }

}
