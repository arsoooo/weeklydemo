package com.bupt317.study.weeklydemo.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.bean.copier.CopyOptions;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.bupt317.study.weeklydemo.config.StaticParams;
import com.bupt317.study.weeklydemo.mapper.UserMapper;
import com.bupt317.study.weeklydemo.pojo.Projectmember;
import com.bupt317.study.weeklydemo.pojo.User;
import com.bupt317.study.weeklydemo.service.UserService;
import com.bupt317.study.weeklydemo.util.UserUtil;
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
    public Integer addUser(User user) {
        return userMapper.insert(user);
    }

    @Override
    public Integer getCount() {
        return userMapper.selectCount(null);
    }

    @Override
    public User getByName(String name) {
        QueryWrapper wrapper = new QueryWrapper();
        wrapper.eq("name", name);
        return userMapper.selectOne(wrapper);
    }

    @Override
    public User getById(int id) {
        return userMapper.selectById(id);
    }

    @Override
    public String getUserNameById(int id) {
        return userMapper.getUserNameById(id);
    }

    @Override
    public UserVO getUserVOById(int id) {
        // 后加：return userVO并且加入权限显示
        User user = userMapper.selectById(id);
        UserVO userVO = new UserVO(UserUtil.perm2Desc(user.getPerms()));
        BeanUtil.copyProperties(user, userVO,
                true, CopyOptions.create().setIgnoreNullValue(true));
        return userVO;
    }

    @Override
    public Integer updateUser(User user) {
        return userMapper.updateById(user);
    }



    @Override
    public boolean isNameExist(String name) {
        return null != getByName(name);
    }

    @Override
    public String findUserNamesByPid(int pid) {
        List<User> userList = userMapper.findUsersByPid(pid);
        // 按格式把名字列出来
        StringBuilder names = new StringBuilder();
        for (User user : userList) {
            names.append(user.getName()).append(" ");
        }
        return names.toString();
    }

    @Override
    public String findUserNamesByNoticeStatusAndNid(int nid, String status) {
        List<String> stringList = userMapper.fineUserNamesByNoticeStatusAndNid(nid, status);
        // 按格式把名字列出来
        StringBuilder names = new StringBuilder();
        for (String s : stringList) {
            names.append(s).append(" ");
        }
        return names.toString();
    }

    @Override
    public String findUserNamesByNid(int nid) {
        List<String> stringList = userMapper.fineUserNamesByNid(nid);
        // 按格式把名字列出来
        StringBuilder names = new StringBuilder();
        for (String s : stringList) {
            names.append(s).append(" ");
        }
        return names.toString();
    }

    @Override
    public Integer getUserCountByNid(int nid) {
        return userMapper.getUserCountByNid(nid);
    }

    @Override
    public List<UserVO> findUsersByPid(int pid) {
        List<UserVO> userVOList =  new ArrayList<>();
        for (User user : userMapper.findUsersByPid(pid)) {
            userVOList.add(new UserVO(user.getId(), user.getName()));
        }
        return userVOList;
    }

    @Override
    public List<UserVO> findOtherUsersByPid(int pid) {
        List<UserVO> userVOList =  new ArrayList<>();
        for (User user : userMapper.findOtherUsersByPid(pid)) {
            userVOList.add(new UserVO(user.getId(), user.getName()));
        }
        return userVOList;
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
            // 后加：显示用户权限
            userVO.setPermStr(UserUtil.perm2Desc(user.getPerms()));
            userVOList.add(userVO);
        }
        dataVO.setData(userVOList);
        return dataVO;
    }

    /**
     * 获得dbUser确保展示界面是最新的内容
     */
    @Override
    public User getLoginDBUser() {
        int uid = UserUtil.getLoginUser().getId();
        return getById(uid);
    }

    @Override
    public Integer deleteByUid(int uid) {
        return userMapper.deleteById(uid);
    }

}
