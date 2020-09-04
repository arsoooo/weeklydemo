package com.bupt317.study.weeklydemo.service;

import com.bupt317.study.weeklydemo.pojo.Project;
import com.bupt317.study.weeklydemo.vo.DataVO;
import com.bupt317.study.weeklydemo.vo.ProjectVO;

public interface ProjectService {

    public DataVO findData();
    public DataVO findDataByUid(int uid);
    public Project getProjectByPid(int pid);
    public ProjectVO getProjectVOByPid(int pid);
    public int addProject(Project project);
    public int updateProject(Project project);
    public Integer deleteByPid(int pid);
}
