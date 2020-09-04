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

import javax.servlet.http.HttpServletRequest;
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

    //////////////////////// 所有用户 ////////////////////////

    /**
     * 所有用户（可改管理员，但需要加用户自己的查看）
     * 根据nid获得公告的信息
     */
    @GetMapping("/notices/{nid}")
    public DataVO getNoticeByNid(
            @PathVariable("nid") int nid
    ){
        return DataVO.success(noticeService.getById(nid));
    }

    //////////////////////// 管理员 ////////////////////////

    /**
     * 管理员
     * 查看所有的公告
     */
    @GetMapping("/admin/notices")
    public DataVO listNotices(){
        return noticeService.findData();
    }

    /**
     * 管理员
     * 发布一个公告
     */
    @PostMapping("/admin/notices")
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

    /**
     * 管理员
     * 删除某个公告(和用户删除区别，这里是删除公告，级联删除了中间表)
     */
    @DeleteMapping("/admin/notices/{nid}")
    public DataVO delNoticeByNid(@PathVariable("nid") int nid){
        int count = noticeService.deleteByNid(nid);
        return DataVO.success(count);
    }

    //////////////////////// 普通用户 ////////////////////////

    /**
     * 普通用户
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
     * 普通用户
     * 获取当前用户的所有消息公告
     */
    @GetMapping("/notices/user")
    public DataVO findUserNotice(){
        User user = userService.getLoginDBUser() ;
        return noticeService.findDataByUid(user.getId());
    }

    /**
     * 普通用户
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

    /**
     * 普通用户
     * 删除某个公告(和管理员删除区别，这里是删除删除中间表，不影响其他用户收到公告)
     * 可以修改成用户删除是不删除信息表的
     */
    @DeleteMapping("/notices/{nid}")
    public DataVO delNoticeByNidWithAuth(@PathVariable("nid") int nid){
        try {
            User user = userService.getLoginDBUser();
            int count = noticememeberService.deleteNoticeMemberByNidAndUid(nid, user.getId());
            return DataVO.success(count);
        }catch (Exception e){
            return DataVO.fail("未正确删除！");
        }
    }

}
