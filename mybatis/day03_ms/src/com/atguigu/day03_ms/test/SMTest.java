package com.atguigu.day03_ms.test;

import java.util.Date;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.atguigu.day03_ms.bean.User;
import com.atguigu.day03_ms.mapper.UserMapper;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("/beans.xml") //加载配置
public class SMTest {

	@Autowired
	private UserMapper mapper;
	
	
	@Test
	public void testAdd() {
		User user = new User(-1, "tom", new Date(), 1234);
		mapper.save(user);
		
		int id = user.getId();
		System.out.println(id);
	}
}
