package com.bupt317.study.weeklydemo.service.impl;

import com.bupt317.study.weeklydemo.config.StaticParams;
import com.bupt317.study.weeklydemo.mapper.NoticememberMapper;
import com.bupt317.study.weeklydemo.pojo.Noticemember;
import com.bupt317.study.weeklydemo.service.NoticememeberService;
import com.bupt317.study.weeklydemo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NoticememeberServiceImpl implements NoticememeberService {
    @Autowired
    private NoticememberMapper noticememberMapper;

    @Autowired
    private UserService userService;

    @Override
    public Integer addNoticeMemeber(Noticemember noticemember) {
        return noticememberMapper.insert(noticemember);
    }

    @Override
    public String fineUserNamesByNid(int nid) {
        if(userService.getCount().equals(userService.getUserCountByNid(nid))){
            return "全部用户";
        } else {
            return userService.findUserNamesByNid(nid);
        }
    }

    @Override
    public Integer getFinishCountByUid(int uid) {
        return noticememberMapper.getCountByUidAndStatus(uid, StaticParams.NOTICE_CREATED);
    }
}
