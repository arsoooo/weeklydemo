package com.bupt317.study.weeklydemo.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.bupt317.study.weeklydemo.config.StaticParams;
import com.bupt317.study.weeklydemo.mapper.NoticeMapper;
import com.bupt317.study.weeklydemo.pojo.Notice;
import com.bupt317.study.weeklydemo.service.NoticeService;
import com.bupt317.study.weeklydemo.service.NoticememeberService;
import com.bupt317.study.weeklydemo.service.UserService;
import com.bupt317.study.weeklydemo.util.DateUtil;
import com.bupt317.study.weeklydemo.util.NoticeUtil;
import com.bupt317.study.weeklydemo.vo.DataVO;
import com.bupt317.study.weeklydemo.vo.NoticeVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class NoticeServiceImpl implements NoticeService {
    @Autowired
    private NoticeMapper noticeMapper;

    @Autowired
    private UserService userService;

    @Autowired
    private NoticememeberService noticememeberService;

    @Override
    public DataVO findData(Integer page, Integer limit) {
        DataVO dataVO = new DataVO(StaticParams.SUCCESS_CODE,null);
        List<NoticeVO> noticeVOList = new ArrayList<>();

        IPage<Notice> noticeIPage = new Page<>(page, limit);
        IPage<Notice> result = noticeMapper.selectPage(noticeIPage, new QueryWrapper<Notice>().orderByDesc("id"));
        List<Notice> noticeList = result.getRecords();
        dataVO.setCount(result.getTotal());
        for (Notice notice : noticeList) {
            noticeVOList.add(adminNotice2VO(notice));
        }
        dataVO.setData(noticeVOList);
        return dataVO;
    }

    @Override
    public DataVO findDataByUid(int uid, Integer page, Integer limit) {
        DataVO dataVO = new DataVO(StaticParams.SUCCESS_CODE);
        IPage<Notice> noticeIPage = new Page<>(page, limit);
        IPage<Notice> result = noticeMapper.findNoticeByUid(uid, noticeIPage);
        dataVO.setCount(result.getTotal());
        List<NoticeVO> noticeVOList = new ArrayList<>();
        List<Notice> noticeList = result.getRecords();  // 现在已经是分页后的内容
        for (Notice notice : noticeList) {
            noticeVOList.add(userNotice2VO(notice, uid));
        }
        dataVO.setData(noticeVOList);
        return dataVO;
    }

    @Override
    public Integer addNotice(Notice notice) {
        return noticeMapper.insert(notice);
    }

    @Override
    public Notice getById(int id) {
        return noticeMapper.selectById(id);
    }

    @Override
    public Integer deleteByNid(int nid) {
        return noticeMapper.deleteById(nid);
    }

    /**
     * notice -> noticeVO 管理员方法
     * notice，赋给noticeVO给前端展示
     */
    private NoticeVO adminNotice2VO(Notice notice){
        NoticeVO noticeVO = new NoticeVO();
        // 会copy的有：id title
        BeanUtil.copyProperties(notice, noticeVO);
        // createTimeStr
        noticeVO.setCreateTimeStr(DateUtil.date2str(notice.getCreateTime()));
        // allNames(这里用了notice member service进一步封装，可以判断全部人员)
        noticeVO.setAllNames(noticememeberService.fineUserNamesByNid(notice.getId()));
        // notReadNames
        noticeVO.setNotReadNames(
                userService.findUserNamesByNoticeStatusAndNid(
                        notice.getId(), StaticParams.NOTICE_CREATED)
        );
        // status用于个人显示已读未读
        return noticeVO;
    }

    /**
     * notice -> noticeVO 用户方法
     * notice，赋给noticeVO给前端展示
     */
    private NoticeVO userNotice2VO(Notice notice, int uid){
        NoticeVO noticeVO = new NoticeVO();
        // 会copy的有：id title
        BeanUtil.copyProperties(notice, noticeVO);
        // createTimeStr
        noticeVO.setCreateTimeStr(DateUtil.date2str(notice.getCreateTime()));
        // allNames(个人用户不用)
        // notReadNames（个人用户不用）
        // status
        noticeVO.setStatus(NoticeUtil.status2desc(
                noticememeberService.getStatusByNidAndUid(notice.getId(), uid)));
        return noticeVO;
    }
}
