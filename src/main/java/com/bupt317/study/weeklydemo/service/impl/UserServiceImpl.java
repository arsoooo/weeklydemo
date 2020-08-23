package com.bupt317.study.weeklydemo.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.bupt317.study.weeklydemo.config.StaticParams;
import com.bupt317.study.weeklydemo.mapper.UserMapper;
import com.bupt317.study.weeklydemo.pojo.User;
import com.bupt317.study.weeklydemo.service.UserService;
import com.bupt317.study.weeklydemo.vo.DataVO;
import com.bupt317.study.weeklydemo.vo.UserVO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    // 注入一个mapper接口对象
    @Autowired
    private UserMapper userMapper;

    @Override
    public User getByName(String name) {
        QueryWrapper wrapper = new QueryWrapper();
        wrapper.eq("name", name);
        return userMapper.selectOne(wrapper);
    }

    @Override
    public User getById(int id) {
        QueryWrapper wrapper = new QueryWrapper();
        wrapper.eq("id", id);
        return userMapper.selectOne(wrapper);
    }

    @Override
    public boolean isNameExist(String name) {
        return null != getByName(name);
    }

    @Override
    public DataVO findData() {
        DataVO dataVO = new DataVO(StaticParams.SUCCESS_CODE,null);
        dataVO.setCount(userMapper.selectCount(null));
        // userList -> userVOList
        List<User> userList = userMapper.selectList(null);
        List<UserVO> userVOList =  new ArrayList<>();
        for (User user : userList) {
            // 使用util工具直接复制属性
            UserVO userVO = new UserVO();
            BeanUtils.copyProperties(user, userVO);
            userVOList.add(userVO);
        }
        dataVO.setData(userVOList);
        return dataVO;
    }

}
