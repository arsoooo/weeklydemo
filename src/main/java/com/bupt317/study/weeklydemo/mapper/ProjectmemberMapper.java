package com.bupt317.study.weeklydemo.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.bupt317.study.weeklydemo.pojo.Projectmember;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Mapper
public interface ProjectmemberMapper extends BaseMapper<Projectmember> {

    @Select("select uid from projectmember where pid = #{pid}")
    public List<Integer> findUidsByPid(int pid);

    @Select("select pid from projectmember where uid = #{uid}")
    public List<Integer> findPidsByUid(int uid);

    @Delete("delete from projectmember where pid = #{pid} and uid = #{uid}")
    public Integer delProjectMemberByPidAndUid(int pid, int uid);
}
