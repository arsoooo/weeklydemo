package com.bupt317.study.weeklydemo.service;

import com.bupt317.study.weeklydemo.pojo.Report;
import com.bupt317.study.weeklydemo.vo.DataVO;
import com.bupt317.study.weeklydemo.vo.ReportVO;

public interface ReportService {
    public Integer addReport(Report report);
    public DataVO findData();
    public DataVO findDataByUid(int uid);
    public Report getById(int id);
    public ReportVO getReportVOById(int id);
    public Integer updateReport(Report report);
}
