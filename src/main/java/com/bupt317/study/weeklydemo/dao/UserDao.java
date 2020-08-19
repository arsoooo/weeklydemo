package com.bupt317.study.weeklydemo.dao;

import com.bupt317.study.weeklydemo.pojo.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

// JPA DAO
public interface UserDao extends JpaRepository<User, Integer> {

    public User findByName(String name);

    public User findById(int id);
}
