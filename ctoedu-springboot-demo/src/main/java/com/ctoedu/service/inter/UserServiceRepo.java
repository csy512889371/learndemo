package com.ctoedu.service.inter;

public interface UserServiceRepo {
   
	/**
	 * 新增users
	 * @param name
	 * @param age
	 */
	void createUser(String name, Integer age);
	
	/**
	 * 根据name删除users
	 * @param name
	 */
	void deleteByName(String name);
}
