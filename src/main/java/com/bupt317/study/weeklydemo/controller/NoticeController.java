package com.bupt317.study.weeklydemo.controller;


import com.bupt317.study.weeklydemo.config.StaticParams;
import com.bupt317.study.weeklydemo.pojo.Notice;
import com.bupt317.study.weeklydemo.pojo.Noticemember;
import com.bupt317.study.weeklydemo.pojo.User;
import com.bupt317.study.weeklydemo.service.NoticeService;
import com.bupt317.study.weeklydemo.service.NoticememeberService;
import com.bupt317.study.weeklydemo.util.JsonUtil;
import com.bupt317.study.weeklydemo.vo.DataVO;
import com.bupt317.study.weeklydemo.vo.UserVO;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@RestController
public class NoticeController {
    @Autowired
    private NoticeService noticeService;

    @Autowired
    private NoticememeberService noticememeberService;

    @GetMapping("/notices")
    public DataVO listNotices(){
        return noticeService.findData();
    }

    @PostMapping("/notices")
    public DataVO addNotice(
        @RequestParam(value = "users") String users,
        Notice notice
    ){
        // 判断有没有选中学生
        List<UserVO> userVOList = JsonUtil.jsonStr2UserVOList(users);
        if(userVOList.size() == 0){ return DataVO.fail("请至少选择一个学生！"); }
        // 处理notice（已经传送了title content） + insert notice
        notice.setCreateTime(new Date());
        noticeService.addNotice(notice);
        // notice member表添加信息
        int nid = notice.getId();  // insert后获得id
        for (UserVO userVO : userVOList) {
            noticememeberService.addNoticeMemeber(
                    new Noticemember(nid, userVO.getId(), StaticParams.NOTICE_CREATED));
        }
        return DataVO.success();
    }

    @GetMapping("/notices/{nid}")
    public DataVO getNoticeByNid(
            @PathVariable("nid") int nid
    ){
        System.out.println("//////////////////////////////////////////////");
        System.out.println(nid);
        System.out.println(noticeService.getById(nid));
        return DataVO.success(noticeService.getById(nid));
    }

    /**
     * 用于检查用户有几条未读信息
     */
    @GetMapping("/notices/home")
    public DataVO isExistNotice(){
        // 记得恢复，用于检查用户有几条未读信息
        return DataVO.success();
        // 获得当前用户 - 获得uid
//        Subject subject = SecurityUtils.getSubject();
//        User user = (User)subject.getPrincipal();
//        // 获得其未读通知的数量
//        int count = noticememeberService.getFinishCountByUid(user.getId());
//        if (count == 0){
//            // =0说明没有未读公告
//            return DataVO.success();
//        }
//        return DataVO.fail("有"+count+"条公告未读，请至公告栏处查看！");
    }

}
