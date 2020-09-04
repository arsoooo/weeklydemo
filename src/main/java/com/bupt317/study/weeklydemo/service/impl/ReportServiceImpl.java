package com.bupt317.study.weeklydemo.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.bupt317.study.weeklydemo.config.StaticParams;
import com.bupt317.study.weeklydemo.mapper.ReportMapper;
import com.bupt317.study.weeklydemo.pojo.Project;
import com.bupt317.study.weeklydemo.pojo.Report;
import com.bupt317.study.weeklydemo.service.ReportService;
import com.bupt317.study.weeklydemo.service.UserService;
import com.bupt317.study.weeklydemo.util.DateUtil;
import com.bupt317.study.weeklydemo.util.ReportUtil;
import com.bupt317.study.weeklydemo.vo.DataVO;
import com.bupt317.study.weeklydemo.vo.ReportVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ReportServiceImpl implements ReportService {
    @Autowired
    private ReportMapper reportMapper;

    @Autowired
    private UserService userService;

    @Override
    public Integer addReport(Report report) {
        return reportMapper.insert(report);
    }

    @Override
    public DataVO findData() {
        DataVO dataVO = new DataVO(StaticParams.SUCCESS_CODE,null);
        dataVO.setCount(reportMapper.selectCount(null));
        // reportList -> reportVOList
        List<Report> reportList = reportMapper.selectList(
                new QueryWrapper<Report>().orderByDesc("id"));
        List<ReportVO> reportVOList = new ArrayList<>();
        for (Report report : reportList) {
            reportVOList.add(report2VO(report));
        }
        dataVO.setData(reportVOList);
        return dataVO;
    }

    @Override
    public DataVO findDataByUid(int uid) {
        List<ReportVO> reportVOList = new ArrayList<>();
        List<Report> reportList = reportMapper.selectList(new QueryWrapper<Report>().eq("uid", uid));
        for (Report report : reportList) {
            reportVOList.add(report2VO(report));
        }
        return DataVO.success(reportVOList);
    }

    @Override
    public List<Integer> findRidByUid(int uid) {
        return reportMapper.findRidByUid(uid);
    }

    @Override
    public Report getById(int id) {
        return reportMapper.selectById(id);
    }

    @Override
    public ReportVO getReportVOById(int id) {
        Report report = reportMapper.selectById(id);
        ReportVO reportVO = report2VO(report);
        reportVO.setContent(report.getContent());
        reportVO.setComment(report.getComment());
        return reportVO;
    }

    @Override
    public Integer updateReport(Report report) {
        return reportMapper.updateById(report);
    }

    @Override
    public Integer deleteByRid(int rid) {
        return reportMapper.deleteById(rid);
    }

    /**
     * report -> reportVO 方法
     * 根据report的值，赋给reportVO给前端展示
     */
    private ReportVO report2VO(Report report){
        ReportVO reportVO = new ReportVO();
        // 会copy： id title （content comment 去掉，修改时才用）
        BeanUtil.copyProperties(report, reportVO,"content", "comment");
        // userName;
        reportVO.setUserName(userService.getUserNameById(report.getUid()));
        // statusStr;
        reportVO.setStatusStr(ReportUtil.status2desc(report.getStatus()));
        // createTimeStr & finishTimeStr
        reportVO.setCreateTimeStr(DateUtil.date2str(report.getCreateTime()));
        reportVO.setFinishTimeStr(DateUtil.date2str(report.getFinishTime()));
        return reportVO;

    }
}
