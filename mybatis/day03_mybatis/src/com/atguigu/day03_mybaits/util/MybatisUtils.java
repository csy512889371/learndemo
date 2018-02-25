package com.atguigu.day03_mybaits.util;

import java.io.InputStream;

import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import com.atguigu.day03_mybaits.test1.Test;

public class MybatisUtils {

	public static SqlSessionFactory getFactory() {
		String resource = "conf.xml";
		InputStream is = Test.class.getClassLoader().getResourceAsStream(
				resource);

		SqlSessionFactory factory = new SqlSessionFactoryBuilder().build(is);

		return factory;
	}
}
