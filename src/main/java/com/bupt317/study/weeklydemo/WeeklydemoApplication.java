package com.bupt317.study.weeklydemo;

//import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

// MapperScan告知mapper的位置，用于自动生成实现类
// ComponentScan告知service实现类的位置
//@MapperScan("com.bupt317.study.weeklydemo.mapper")
@SpringBootApplication
public class WeeklydemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(WeeklydemoApplication.class, args);
    }

}
