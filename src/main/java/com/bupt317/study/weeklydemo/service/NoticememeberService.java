package com.bupt317.study.weeklydemo.service;

import com.bupt317.study.weeklydemo.pojo.Noticemember;
import org.springframework.stereotype.Service;

import java.util.List;


public interface NoticememeberService {
    public Integer addNoticeMemeber(Noticemember noticemember);
    public String fineUserNamesByNid(int nid);
    public Integer getFinishCountByUid(int uid);
}
