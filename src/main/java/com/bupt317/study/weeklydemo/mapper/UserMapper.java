package com.bupt317.study.weeklydemo.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.bupt317.study.weeklydemo.config.StaticParams;
import com.bupt317.study.weeklydemo.pojo.User;
import com.bupt317.study.weeklydemo.vo.UserVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
@Mapper
public interface UserMapper extends BaseMapper<User> {

    @Select("select * from user u " +
            "where perms = #{perms}")
    public List<User> selectListByPerms(String perms);

    @Select("select u.id,u.name,pm.target from user u " +
            "left join projectmember pm " +
            "on u.id = pm.uid " +
            "where pm.pid = #{pid} " +
            "and perms = #{perms}")
    public List<UserVO> findUsersByPid(int pid, String perms);

    @Select("select u.id,u.name from user u " +
            "where u.id not in " +
            "(select uid from projectmember where pid = #{pid})" +
            "and perms = #{perms}")
    public List<UserVO> findOtherUsersByPid(int pid, String perms);


    @Select("select u.name from user u where u.id = #{id}")
    public String getUserNameById(int id);

    @Select("select u.name from user u " +
            "left join noticemember nm " +
            "on u.id = nm.uid " +
            "where nm.nid = #{nid} " +
            "and nm.status = #{status}")
    public List<String> findUserNamesByNoticeStatusAndNid(int nid, String status);

    @Select("select u.name from user u " +
            "left join noticemember nm " +
            "on u.id = nm.uid " +
            "where nm.nid = #{nid} ")
    public List<String> findUserNamesByNid(int nid);

    @Select("select count(*) from user u " +
            "left join noticemember nm " +
            "on u.id = nm.uid " +
            "where nm.nid = #{nid} ")
    public Integer getUserCountByNid(int nid);
}
