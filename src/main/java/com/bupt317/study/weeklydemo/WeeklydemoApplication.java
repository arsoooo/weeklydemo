package com.bupt317.study.weeklydemo;

//import org.mybatis.spring.annotation.MapperScan;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

import javax.annotation.PostConstruct;
import java.util.TimeZone;

// MapperScan告知mapper的位置，用于自动生成实现类
// ComponentScan告知service实现类的位置
@SpringBootApplication
//@MapperScan("com.bupt317.study.weeklydemo.mapper")
public class WeeklydemoApplication {

    /**
     * 保证服务器时区为UTC
     * 处理new Date()时区问题
     */
    @PostConstruct
    void started()
    {
        TimeZone.setDefault(TimeZone.getTimeZone("Asia/Shanghai"));
        //TimeZone.setDefault(TimeZone.getTimeZone("Asia/Shanghai"));
        //TimeZone.setDefault(TimeZone.getTimeZone("GMT+8"));
    }

    public static void main(String[] args) {
        SpringApplication.run(WeeklydemoApplication.class, args);
    }

}
