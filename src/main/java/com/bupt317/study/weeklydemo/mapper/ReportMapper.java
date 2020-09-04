package com.bupt317.study.weeklydemo.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.bupt317.study.weeklydemo.pojo.Report;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Mapper
public interface ReportMapper  extends BaseMapper<Report>{
    @Select("select id from report r " +
            "where uid = #{uid}")
    public List<Integer> findRidByUid(int uid);
}
