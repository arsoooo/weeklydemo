package com.bupt317.study.weeklydemo.service;

import com.bupt317.study.weeklydemo.pojo.User;
import com.bupt317.study.weeklydemo.vo.DataVO;
import org.springframework.stereotype.Service;

public interface UserService {
    public User getByName(String name);
    public User getById(int id);
    public boolean isNameExist(String name);
    public DataVO findData();
}
