package com.bupt317.study.weeklydemo.controller;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.bean.copier.CopyOptions;
import com.bupt317.study.weeklydemo.config.StaticParams;
import com.bupt317.study.weeklydemo.pojo.Report;
import com.bupt317.study.weeklydemo.pojo.User;
import com.bupt317.study.weeklydemo.service.ReportService;
import com.bupt317.study.weeklydemo.service.UserService;
import com.bupt317.study.weeklydemo.util.WordUtil;
import com.bupt317.study.weeklydemo.vo.DataVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.xml.crypto.Data;
import java.util.Date;

@RestController
public class ReportController {
    @Autowired
    private ReportService reportService;

    @Autowired
    private UserService userService;

    //////////////////////// 管理员界面 ////////////////////////

    @GetMapping("/reports")
    public DataVO findReports(){
        return reportService.findData();
    }

    @GetMapping("/reports/{rid}")
    public DataVO getReportById(@PathVariable("rid") int rid){
        return DataVO.success(reportService.getReportVOById(rid));
    }

    /**
     * 通过uid获得用户的所有周报
     * 管理员查看某个用户用的到
     */
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
        if(report.getComment() != null && !report.getComment().equals("")){
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

    //////////////////////// 用户界面 ////////////////////////

    /**
     * 通过uid获得用户的所有周报
     * 管理员查看某个用户用的到
     */
    @GetMapping("/reports/users")
    public DataVO getUserReportByLoginUid(){
        // 通过登录用户的id去查，而不是传过来的id，这和管理员不同
        User user = userService.getLoginDBUser();
        return reportService.findDataByUid(user.getId());
    }

    /**
     * 处理上传的周报文件
     */
    @PostMapping("/reports")
    public DataVO addReport(@RequestParam("file") MultipartFile doc, HttpServletRequest request){
        User user = userService.getLoginDBUser() ;
        // 把用户上传的文件存储到reportUpload的tmp.doc(此时没有rid)
        WordUtil.uploadUserReport(doc, request);
        // 从reportUpload/tmp.doc读取出内容 -> report -> dataBase
        Report report = WordUtil.readReportTmpDoc(request);
        report.setUid(user.getId());
        report.setStatus(StaticParams.REPORT_CREATED);
        report.setCreateTime(new Date());
        reportService.addReport(report);
        // tmp.doc -> rid.doc
        WordUtil.tmpDoc2ridDoc(report, request);
        // report -> 根据模板写一个文件(会比上传的更加工整，信息更多，可去掉这个功能)
        WordUtil.writeReportDoc(report, user, request);

        return DataVO.success();
    }
}
