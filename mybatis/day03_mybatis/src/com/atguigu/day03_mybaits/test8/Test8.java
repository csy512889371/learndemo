package com.atguigu.day03_mybaits.test8;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;

import com.atguigu.day03_mybaits.bean.ConditionUser;
import com.atguigu.day03_mybaits.bean.User;
import com.atguigu.day03_mybaits.util.MybatisUtils;
/**
 * 测试调用存储过程
 * @author xfzhang
 *
 */
public class Test8 {

	public static void main(String[] args) {
		SqlSessionFactory factory = MybatisUtils.getFactory();
		SqlSession session = factory.openSession();
		
		String statement = "com.atguigu.day03_mybaits.test8.userMapper.getUserCount";
		
		Map<String, Integer> parameterMap = new HashMap<String, Integer>();
		parameterMap.put("sexid", 1);
		parameterMap.put("usercount", -1);
		
		
		session.selectOne(statement, parameterMap);
		
		Integer result = parameterMap.get("usercount");
		System.out.println(result);
		
		session.close();
	}
}
