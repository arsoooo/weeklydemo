package com.bupt317.study.weeklydemo.mapper;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.bupt317.study.weeklydemo.pojo.Project;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Mapper
public interface ProjectMapper extends BaseMapper<Project> {
    @Select("select * from project p " +
            "left join projectmember pm " +
            "on p.id = pm.pid " +
            "where pm.uid = #{uid} " +
            "order by p.id desc")
    public IPage<Project> findProjectsByUid(int uid, IPage<Project> iPage);
}
