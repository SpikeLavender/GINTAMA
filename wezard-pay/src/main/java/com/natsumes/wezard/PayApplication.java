package com.natsumes.wezard;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.context.config.annotation.RefreshScope;

/**
 * @author  hetengjiao
 * @date    2020-12-06
 */
@SpringBootApplication
@EnableDiscoveryClient
@RefreshScope
//@MapperScan("com.natsumes.wezard.mapper")
public class PayApplication {

    public static void main(String[] args) {
        SpringApplication.run(PayApplication.class, args);
    }

}
