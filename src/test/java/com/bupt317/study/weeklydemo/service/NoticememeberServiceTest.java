package com.bupt317.study.weeklydemo.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class NoticememeberServiceTest {

    @Autowired
    private NoticememeberService noticememeberService;

    @Test
    void getFinishCountByUid() {
        System.out.println(noticememeberService.getFinishCountByUid(5));
    }
}