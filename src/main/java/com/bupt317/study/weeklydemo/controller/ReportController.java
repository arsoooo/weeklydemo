package com.bupt317.study.weeklydemo.controller;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.bean.copier.CopyOptions;
import com.bupt317.study.weeklydemo.config.StaticParams;
import com.bupt317.study.weeklydemo.pojo.Report;
import com.bupt317.study.weeklydemo.service.ReportService;
import com.bupt317.study.weeklydemo.service.UserService;
import com.bupt317.study.weeklydemo.util.WordUtil;
import com.bupt317.study.weeklydemo.vo.DataVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;

@RestController
public class ReportController {
    @Autowired
    private ReportService reportService;

    @Autowired
    private UserService userService;

    @GetMapping("/reports")
    public DataVO findReports(){
        return reportService.findData();
    }

    @GetMapping("/reports/{rid}")
    public DataVO getReportById(@PathVariable("rid") int rid){
        return DataVO.success(reportService.getReportVOById(rid));
    }

    @GetMapping("/reports/users/{uid}")
    public DataVO getUserReportByUid(@PathVariable("uid") int uid){
        return reportService.findDataByUid(uid);
    }

    /**
     * 周报的修改（修改内容，添加点评）
     * 管理员操作
     */
    @PutMapping("/reports/{rid}")
    public DataVO updateReport(
            @PathVariable("rid") int rid,
            Report report,
            HttpServletRequest request
    ){
        // 获得dbReport
        Report dbReport = reportService.getById(rid);
        // 判断是否评价
        if(report.getComment() != null){
            report.setFinishTime(new Date());
            report.setStatus(StaticParams.REPORT_FINISHED);
        }
        // report -> dbReport
        BeanUtil.copyProperties(report, dbReport,
                true, CopyOptions.create().setIgnoreNullValue(true));
        // 更新数据库（dbReport）
        reportService.updateReport(dbReport);
        // 修改周报表（以另一种形式保存到report）
        WordUtil.writeReportDoc(dbReport, userService.getById(dbReport.getUid()), request);

        return DataVO.success();
    }



}
