package com.bupt317.study.weeklydemo.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.bupt317.study.weeklydemo.pojo.Noticemember;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

@Repository
@Mapper
public interface NoticememberMapper extends BaseMapper<Noticemember> {
    @Select("select count(*) from noticemember " +
            "where uid = #{uid} " +
            "and status = #{status}")
    public Integer getCountByUidAndStatus(int uid, String status);

    @Select("select nm.status from noticemember nm " +
            "where nm.nid = #{nid} " +
            "and nm.uid = #{uid}")
    public String getStatusByNidAndUid(int nid, int uid);

    @Update("update noticemember set status = #{status} " +
                "where nid = #{nid} " +
            "and uid = #{uid}")
    public int updateStatusByNidAndUid(String status, int nid, int uid);

}
