package com.bupt317.study.weeklydemo.service;

import com.bupt317.study.weeklydemo.pojo.Project;
import com.bupt317.study.weeklydemo.vo.DataVO;
import com.bupt317.study.weeklydemo.vo.ProjectVO;

public interface ProjectService {

    public DataVO findData(Integer page, Integer limit);
    public DataVO findDataByUid(int uid, Integer page, Integer limit);
    public Project getProjectByPid(int pid);
    public ProjectVO getProjectVOByPid(int pid);
    public int addProject(Project project);
    public int updateProject(Project project);
    public Integer deleteByPid(int pid);
}
