package com.bupt317.study.weeklydemo.service;

import com.bupt317.study.weeklydemo.pojo.Projectmember;
import java.util.List;

public interface ProjectmemberService {
    public List<Integer> findUidsByPid(int pid);

    public List<Integer> findPidsByUid(int uid);
    public int addProjectmember(Projectmember projectmember);
    public int delProjectMemberByPidAndUid(int pid, int uid);
}
