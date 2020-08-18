package com.plat.webm;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;

@SpringBootApplication
@MapperScan({ "com.plat.webm.user.mapper","com.plat.webm.news.mapper" })
//针对实体类，用处同上
@EntityScan({"com.plat.webm.user.entity","com.plat.webm.news.entity"})

public class WebmApplication {

    public static void main(String[] args) {
        SpringApplication.run(WebmApplication.class, args);
    }

}
