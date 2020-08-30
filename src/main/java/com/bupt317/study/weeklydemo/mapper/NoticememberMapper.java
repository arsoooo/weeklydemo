package com.bupt317.study.weeklydemo.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.bupt317.study.weeklydemo.pojo.Noticemember;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

@Repository
@Mapper
public interface NoticememberMapper extends BaseMapper<Noticemember> {
    @Select("select count(*) from noticemember " +
            "where uid = #{uid} " +
            "and status = #{status}")
    public Integer getCountByUidAndStatus(int uid, String status);

}
