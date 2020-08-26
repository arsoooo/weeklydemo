package com.bupt317.study.weeklydemo.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.bupt317.study.weeklydemo.config.StaticParams;
import com.bupt317.study.weeklydemo.mapper.ProjectMapper;
import com.bupt317.study.weeklydemo.pojo.Project;
import com.bupt317.study.weeklydemo.service.ProjectService;
import com.bupt317.study.weeklydemo.service.ProjectmemberService;
import com.bupt317.study.weeklydemo.service.UserService;
import com.bupt317.study.weeklydemo.util.DateUtil;
import com.bupt317.study.weeklydemo.util.ProjectUtil;
import com.bupt317.study.weeklydemo.vo.DataVO;
import com.bupt317.study.weeklydemo.vo.ProjectVO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class ProjectServiceImpl implements ProjectService {

    @Autowired
    private ProjectMapper projectMapper;

    @Autowired
    private ProjectmemberService projectmemberService;

    @Autowired
    private UserService userService;

    /**
     * 查询全部项目
     */
    @Override
    public DataVO findData() {
        QueryWrapper<Project> wrapper = new QueryWrapper<>();
        wrapper.orderByDesc("id");
        return findDataByWrapper(wrapper);
    }

    // 分离出来不一定有用，可能还得整合到findDATA里去
    private DataVO findDataByWrapper(QueryWrapper<Project> wrapper){
        DataVO dataVO = new DataVO(StaticParams.SUCCESS_CODE,null);
        dataVO.setCount(projectMapper.selectCount(null));
        // projectList -> projectVOList
        List<Project> projectList = projectMapper.selectList(wrapper);
        List<ProjectVO> projectVOList = new ArrayList<>();
        for (Project project : projectList) {
            // 添加到list里去
            projectVOList.add(pro2proVO(project,true));
        }
        dataVO.setData(projectVOList);
        return dataVO;
    }

    /**
     * 获得某用户的所有项目
     */
    @Override
    public DataVO findDataByUid(int uid) {
        // 根据uid 获得这个user的所有project实体类
        List<Project> projectList = projectMapper.findProjectsByUid(uid);
        // 准备DataVO
        DataVO dataVO = new DataVO(StaticParams.SUCCESS_CODE);
        dataVO.setCount(projectList.size());
        // 准备dataVO.data
        List<ProjectVO> projectVOList = new ArrayList<>();
        for (Project project : projectList) {
            projectVOList.add(pro2proVO(project,true));
        }
        dataVO.setData(projectVOList);
        return dataVO;
    }

    @Override
    public Project getProjectByPid(int pid) {
        return projectMapper.selectById(pid);
    }

    @Override
    public ProjectVO getProjectVOByPid(int pid) {
        Project project = projectMapper.selectById(pid);
        ProjectVO projectVO = pro2proVO(project,false);
        projectVO.setContent(project.getContent());
        return projectVO;
    }


    /**
     * pro -> proVO 方法
     * 根据project的值，赋给projectVO给前端展示
     */
    private ProjectVO pro2proVO(Project project, Boolean deadlineTimeSimpleOrNot){
        ProjectVO projectVO = new ProjectVO();
        // 会copy： id title content
        BeanUtils.copyProperties(project, projectVO);
        projectVO.setContent(null); // 减少传输量，需要再加（填充content需要）
        // projectTimeStr:xxxx-xx-xx开始已经XX天
        String createTime = DateFormat.getDateTimeInstance().format(project.getCreateTime());
        String days_between_date = DateUtil.days_between_date(project.getCreateTime(), new Date());
        projectVO.setProjectTimeStr(createTime + "开始，已进行" + days_between_date + "天");
        // deadlineTimeStr(区分显示和赋值给表单)
        if(deadlineTimeSimpleOrNot){
            projectVO.setDeadlineTimeStr(DateUtil.date2strSimple(project.getDeadline()));
        } else{ projectVO.setDeadlineTimeStr(DateUtil.date2str(project.getDeadline())); }
        // finishTimeStr
        projectVO.setFinishTimeStr(DateUtil.date2strSimple(project.getFinishTime()));
        // projectState
        projectVO.setProjectState(ProjectUtil.status2desc(project.getStatus()));
        // names
        projectVO.setNames(userService.findUserNamesByPid(project.getId()));

        return projectVO;
    }

    /**
     * 插入一个project
     * 新的id会赋给该project
     * @param project pojo
     * @return 插入数据的条数
     */
    @Override
    public int addProject(Project project) {
        return projectMapper.insert(project);
    }

    @Override
    public int updateProject(Project project) {
        return projectMapper.updateById(project);
    }
}
