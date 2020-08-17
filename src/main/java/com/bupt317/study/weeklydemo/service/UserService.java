package com.bupt317.study.weeklydemo.service;

import com.bupt317.study.weeklydemo.pojo.User;
import org.springframework.stereotype.Service;

public interface UserService {
    public User findByName(String name);
    public User findByid(int id);
}
