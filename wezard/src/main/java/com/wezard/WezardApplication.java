package com.wezard;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@MapperScan("com.wezard.mapper")
@SpringBootApplication
@EnableScheduling
public class WezardApplication {

	public static void main(String[] args) {
		SpringApplication.run(WezardApplication.class, args);
	}

}
