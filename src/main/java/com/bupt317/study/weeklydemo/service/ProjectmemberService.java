package com.bupt317.study.weeklydemo.service;

import com.bupt317.study.weeklydemo.pojo.Projectmember;
import java.util.List;

public interface ProjectmemberService {
    public List<Integer> findMemberIdByPid(int pid);
    public String findMemberNamesByPid(int pid);
    public List<Integer> findPidsByUid(int uid);
    public int addProjectmember(Projectmember projectmember);
}
