package com.natsumes.wezard;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author  hetengjiao
 * @date    2020-12-06
 */
@SpringBootApplication
//@EnableDiscoveryClient
//@RefreshScope
@MapperScan("com.natsumes.wezard.mapper")
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

}
