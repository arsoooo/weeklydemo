package com.bupt317.study.weeklydemo.service;

import com.bupt317.study.weeklydemo.pojo.Project;
import com.bupt317.study.weeklydemo.vo.DataVO;

public interface ProjectService {

    public DataVO findData();
    public DataVO findDataByUid(int uid);
    public int addProject(Project project);
}
