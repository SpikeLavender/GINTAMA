package com.natsumes;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@SpringBootTest //标记该类为spring boot单元测试类，并且加载项目的applicationContext上下文环境
@RunWith(SpringRunner.class)
@Slf4j
public class ApplicationTests {

	@Test
	public void contextLoads() {

	}

}
