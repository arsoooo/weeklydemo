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
import com.bupt317.study.weeklydemo.vo.ReportVO;
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

    //////////////////////// 管理员 ////////////////////////

    /**
     * 管理员
     * 获得所有周报信息
     */
    @GetMapping("/admin/reports")
    public DataVO findReports(){
        return reportService.findData();
    }

    /**
     * 管理员
     * 根据rid获得一个周报的信息
     */
    @GetMapping("/admin/reports/{rid}")
    public DataVO getReportByRid(@PathVariable("rid") int rid){
        return DataVO.success(reportService.getReportVOById(rid));
    }

    /**
     * 管理员
     * 通过uid获得用户的所有周报
     */
    @GetMapping("/admin/reports/users/{uid}")
    public DataVO findUserReportByUid(@PathVariable("uid") int uid){
        return reportService.findDataByUid(uid);
    }

    /**
     * 管理员
     * 对周报修改（修改内容，添加点评）
     */
    @PutMapping("/admin/reports/{rid}")
    public DataVO updateReportByRid(
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

    /**
     * 管理员
     * 删除某个周报
     */
    @DeleteMapping("/admin/reports/{rid}")
    public DataVO delUserReportByRid(@PathVariable("rid") int rid, HttpServletRequest request){
        // 删除上传表和周报表
        WordUtil.deleteReportDoc(rid, request);
        // 删除database相关信息
        int count = reportService.deleteByRid(rid);
        return DataVO.success(count);
    }

    //////////////////////// 普通用户 ////////////////////////

    /**
     * 普通用户
     * 根据当前登录用户获得其所有周报
     */
    @GetMapping("/reports")
    public DataVO findUserReportByLoginUid(){
        // 通过登录用户的id去查，而不是传过来的id，这和管理员不同
        User user = userService.getLoginDBUser();
        return reportService.findDataByUid(user.getId());
    }

    /**
     * 普通用户
     * 与管理员不同，增加了验证rid是否是该用户的，否则查不到内容
     * 根据rid获得一个周报的信息
     */
    @GetMapping("/reports/{rid}")
    public DataVO getReportByRidWithAuth(@PathVariable("rid") int rid){
        User user = userService.getLoginDBUser();
        ReportVO reportVO = reportService.getReportVOById(rid);
        // 根据用户名判断是否是该用户的周报，是的则传过去
        if(user.getName().equals(reportVO.getUserName())){
            return DataVO.success(reportVO);
        }
        // 不是该用用户的周报就啥也不传
        return DataVO.success();
    }

    /**
     * 普通用户
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

    /**
     * 普通用户
     * 对周报修改（修改标题、内容）
     * 已经点评过则无法修改
     */
    @PutMapping("/reports/{rid}")
    public DataVO updateUserReportByRid(
            @PathVariable("rid") int rid,
            Report report,
            HttpServletRequest request
    ){
        // 获得dbReport
        Report dbReport = reportService.getById(rid);
        // 如果已经有评价就不能改了
        if (dbReport.getStatus().equals(StaticParams.REPORT_FINISHED)){
            return DataVO.fail("该周报已有教师评价，无法更改");
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

    /**
     * 普通用户
     * 删除某个周报（与管理员不同，要验证用户是否有该周报）
     */
    @DeleteMapping("/reports/{rid}")
    public DataVO delUserReportByRidWithAuth(@PathVariable("rid") int rid, HttpServletRequest request){
        // 如果这个用户没有这个公告，就不能删除
        Report report = reportService.getById(rid);
        User user = userService.getLoginDBUser();
        if(!report.getUid().equals(user.getId())){
            return DataVO.fail("只能删除自己的公告！");
        }
        // 删除上传表和周报表
        WordUtil.deleteReportDoc(rid, request);
        // 删除database相关信息
        int count = reportService.deleteByRid(rid);
        return DataVO.success(count);
    }
}
