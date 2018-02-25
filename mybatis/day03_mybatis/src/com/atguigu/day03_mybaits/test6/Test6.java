package com.atguigu.day03_mybaits.test6;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;

import com.atguigu.day03_mybaits.bean.Classes;
import com.atguigu.day03_mybaits.util.MybatisUtils;

/*
 * 测试: 一对多关联表查询
 */
public class Test6 {

	public static void main(String[] args) {
		SqlSessionFactory factory = MybatisUtils.getFactory();
		SqlSession session = factory.openSession();
		
		String statement = "com.atguigu.day03_mybaits.test6.ClassMapper.getClass";
		
		statement = "com.atguigu.day03_mybaits.test6.ClassMapper.getClass2";
		
		Classes c = session.selectOne(statement , 2);
		System.out.println(c);
		
		session.close();
	}
}
