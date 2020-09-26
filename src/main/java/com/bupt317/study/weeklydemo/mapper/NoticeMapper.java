package com.bupt317.study.weeklydemo.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.bupt317.study.weeklydemo.pojo.Notice;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Mapper
public interface NoticeMapper extends BaseMapper<Notice> {
    @Select("select * from notice n " +
            "left join noticemember nm " +
            "on n.id = nm.nid " +
            "where uid = #{uid} " +
            "and status = #{status}")
    public List<Notice> findNoticeByUidAndStatus(int uid, String status);

    @Select("select * from notice n " +
            "left join noticemember nm " +
            "on n.id = nm.nid " +
            "where uid = #{uid}")
    public IPage<Notice> findNoticeByUid(int uid, IPage<Notice> iPage);

}
