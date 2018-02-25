package com.atguigu.day03_mybaits.test3;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.junit.Test;

import com.atguigu.day03_mybaits.bean.User;
import com.atguigu.day03_mybaits.util.MybatisUtils;

/*
 * 测试: CRUD操作的注解的实现
 */
public class Test3 {
	
	@Test
	public void testAdd() {
		SqlSessionFactory factory = MybatisUtils.getFactory();
		//默认是手动提交的
		SqlSession session = factory.openSession(true);
		
		UserMapper mapper = session.getMapper(UserMapper.class);
		int add = mapper.add(new User(-1, "SS", 45));
		System.out.println(add);
		
		session.close();
	}
	
}
