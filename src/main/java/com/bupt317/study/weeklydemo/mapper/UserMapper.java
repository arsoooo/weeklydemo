package com.bupt317.study.weeklydemo.mapper;

import com.bupt317.study.weeklydemo.pojo.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

//@Repository给Dao
@Repository
//@Mapper
public interface UserMapper {

    @Select("select * from user")
    public List<User> listAll();

    @Select("select * from user where name = #{name}")
    public User findByName(String name);

    // 根据id找perms，用于资源授权
    @Select("select * from user where id = #{id}")
    public User findByid(int id);
}
