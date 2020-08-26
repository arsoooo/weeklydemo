package com.bupt317.study.weeklydemo.mapper;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ProjectmemberMapperTest {

    @Autowired
    private ProjectmemberMapper projectmemberMapper;

    @Test
    void findUidsByPid() {
        List<Integer> uids = projectmemberMapper.findUidsByPid(13);
        for (Integer uid : uids) {
            System.out.println(uid);
        }
    }
}