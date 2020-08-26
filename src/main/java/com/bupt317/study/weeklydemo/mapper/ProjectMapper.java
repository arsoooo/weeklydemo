package com.bupt317.study.weeklydemo.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
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
            "where pm.uid = #{uid}")
    public List<Project> findProjectsByUid(int uid);
}
