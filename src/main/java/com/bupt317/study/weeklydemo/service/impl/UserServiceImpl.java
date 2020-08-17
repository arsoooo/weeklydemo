package com.bupt317.study.weeklydemo.service.impl;

import com.bupt317.study.weeklydemo.mapper.UserMapper;
import com.bupt317.study.weeklydemo.pojo.User;
import com.bupt317.study.weeklydemo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    // 注入一个UserMapper接口对象
    @Autowired
    private UserMapper userMapper;

    @Override
    public User findByName(String name) {
        return userMapper.findByName(name);
    }

    @Override
    public User findByid(int id) { return userMapper.findByid(id); }

}
