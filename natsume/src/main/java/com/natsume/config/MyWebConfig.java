package com.natsume.config;

import com.natsume.interceptor.UserLoginInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@EnableScheduling
public class MyWebConfig implements WebMvcConfigurer {

	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(userLoginInterceptor())
				.addPathPatterns("/**")
				.excludePathPatterns("/user/login", "/user/register", "/categories", "/products", "/products/*", "/error");
	}

	@Bean
	public UserLoginInterceptor userLoginInterceptor() {
		return new UserLoginInterceptor();
	}
}
