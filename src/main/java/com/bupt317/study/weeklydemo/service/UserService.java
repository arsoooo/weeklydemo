package com.bupt317.study.weeklydemo.service;

import com.bupt317.study.weeklydemo.pojo.User;
import com.bupt317.study.weeklydemo.vo.DataVO;
import com.bupt317.study.weeklydemo.vo.UserVO;
import org.springframework.stereotype.Service;

import java.util.List;

public interface UserService {
    public Integer addUser(User user);
    public Integer getCount();
    public User getByName(String name);
    public User getById(int id);
    public String getUserNameById(int id);
    public UserVO getUserVOById(int id);
    public Integer updateUser(User user);
    public boolean isNameExist(String name);
    public String findUserNamesByPid(int pid);
    public String findUserNamesByNoticeStatusAndNid(int nid, String status);
    public String findUserNamesByNid(int nid);
    public Integer getUserCountByNid(int nid);
    public List<UserVO> findUsersByPid(int pid);
    public List<UserVO> findOtherUsersByPid(int pid);
    public DataVO findData();
    public User getLoginDBUser();

}
