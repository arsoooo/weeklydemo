package com.bupt317.study.weeklydemo.controller;


import com.bupt317.study.weeklydemo.config.StaticParams;
import com.bupt317.study.weeklydemo.pojo.Notice;
import com.bupt317.study.weeklydemo.pojo.Noticemember;
import com.bupt317.study.weeklydemo.pojo.User;
import com.bupt317.study.weeklydemo.service.NoticeService;
import com.bupt317.study.weeklydemo.service.NoticememeberService;
import com.bupt317.study.weeklydemo.service.UserService;
import com.bupt317.study.weeklydemo.util.JsonUtil;
import com.bupt317.study.weeklydemo.vo.DataVO;
import com.bupt317.study.weeklydemo.vo.UserVO;
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

    @Autowired
    private UserService userService;

    //////////////////////// 管理员界面 ////////////////////////

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
        return DataVO.success(noticeService.getById(nid));
    }

    //////////////////////// 用户界面 ////////////////////////

    /**
     * 用于检查用户有几条未读信息
     */
    @GetMapping("/notices/home")
    public DataVO isExistNotice(){
        // 记得恢复，用于检查用户有几条未读信息
//        return DataVO.success();
        // 获得当前用户 - 获得uid
        User user = userService.getLoginDBUser();
        // 获得其未读通知的数量
        int count = noticememeberService.getFinishCountByUid(user.getId());
        if (count == 0){
            // =0说明没有未读公告
            return DataVO.success();
        }
        return DataVO.fail("有"+count+"条公告未读，请至公告栏处查看！");
    }

    /**
     * 获取当前用户的所有消息公告
     */
    @GetMapping("/notices/user")
    public DataVO findUserNotice(){
        User user = userService.getLoginDBUser() ;
        return noticeService.findDataByUid(user.getId());
    }

    /**
     * 将通知标为已读
     */
    @PutMapping("/notices/{nid}")
    public DataVO userEditNoticeStatus(
            @PathVariable("nid") int nid
    ){
        User user = userService.getLoginDBUser();
        int count = noticememeberService.updateStatusByNidAndUid(
                StaticParams.NOTICE_FINISH, nid, user.getId());
        if(count!=1){ return DataVO.fail("error：更新了"+count+"条"); } // 防止出错，用uid和nid应该只删除了一条
        return DataVO.success();
    }

}
