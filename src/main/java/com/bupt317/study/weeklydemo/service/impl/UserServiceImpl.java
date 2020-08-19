package com.bupt317.study.weeklydemo.service.impl;

import com.bupt317.study.weeklydemo.dao.UserDao;
import com.bupt317.study.weeklydemo.pojo.User;
import com.bupt317.study.weeklydemo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    // 注入一个JPA接口对象
    @Autowired
    private UserDao userDao;

    @Override
    public User findByName(String name) {
        return userDao.findByName(name);
    }

    @Override
    public User findById(int id) { return userDao.findById(id); }

}
