package com.bupt317.study.weeklydemo.mapper;

import com.bupt317.study.weeklydemo.config.StaticParams;
import com.bupt317.study.weeklydemo.pojo.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class UserMapperTest {

    @Autowired
    private UserMapper userMapper;

    @Test
    void test(){
        userMapper.selectList(null).forEach(System.out::println);
    }

    @Test
    void findUsersByPid() {
        List<User> userList = userMapper.findUsersByPid(14);
        for (User user : userList) {
            System.out.println(user.toString());
        }
    }

    @Test
    void findOtherUsersByPid() {
        List<User> userList = userMapper.findOtherUsersByPid(13);
        for (User user : userList) {
            System.out.println(user.toString());
        }
    }

    @Test
    void fineUserNamesByNoticeStatus() {

        List<String> stringList = userMapper.fineUserNamesByNoticeStatusAndNid(2, StaticParams.NOTICE_CREATED);
        for (String s : stringList) {
            System.out.println(s);
        }
    }
}