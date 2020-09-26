package com.bupt317.study.weeklydemo.service;

import com.bupt317.study.weeklydemo.pojo.Notice;
import com.bupt317.study.weeklydemo.vo.DataVO;

public interface NoticeService {
    public DataVO findData(Integer page, Integer limit);
    public DataVO findDataByUid(int uid, Integer page, Integer limit);
    public Integer addNotice(Notice notice);
    public Notice getById(int id);
    public Integer deleteByNid(int nid);
}
