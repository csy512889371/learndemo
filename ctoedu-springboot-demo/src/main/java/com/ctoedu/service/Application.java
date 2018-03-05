package com.ctoedu.service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling   //开始定时任务
@EnableAsync      //开启异步处理
public class Application {

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}
}
