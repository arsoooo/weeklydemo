package com.bupt317.study.weeklydemo.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.bupt317.study.weeklydemo.config.StaticParams;
import com.bupt317.study.weeklydemo.mapper.NoticeMapper;
import com.bupt317.study.weeklydemo.pojo.Notice;
import com.bupt317.study.weeklydemo.service.NoticeService;
import com.bupt317.study.weeklydemo.service.NoticememeberService;
import com.bupt317.study.weeklydemo.service.UserService;
import com.bupt317.study.weeklydemo.util.DateUtil;
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
    public DataVO findData() {
        List<NoticeVO> noticeVOList = new ArrayList<>();
        List<Notice> noticeList = noticeMapper.selectList(null);
        for (Notice notice : noticeList) {
            noticeVOList.add(notice2VO(notice));
        }
        return DataVO.success(noticeVOList);
    }

    @Override
    public Integer addNotice(Notice notice) {
        return noticeMapper.insert(notice);
    }

    @Override
    public Notice getById(int id) {
        return noticeMapper.selectById(id);
    }

    /**
     * notice -> noticeVO 方法
     * notice，赋给noticeVO给前端展示
     */
    private NoticeVO notice2VO(Notice notice){
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
        return noticeVO;
    }
}
