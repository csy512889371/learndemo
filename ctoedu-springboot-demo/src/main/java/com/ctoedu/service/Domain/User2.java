package com.ctoedu.service.Domain;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

/**
 * 访问mongodb
 *
 *
 */

/*
 * @Document(collection="user2")对应到mongodb里面的集合名字
 * 	@Id对应一个主键
 * 	@Field("id")对应mongodb里的字段名字
 */
@Document(collection="user2")
public class User2 {
	@Id
	@Field("id")
	private Integer id;
	@Field("name")
	private String name;
	@Field("age")
	private Integer age;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getAge() {
		return age;
	}

	public void setAge(Integer age) {
		this.age = age;
	}

}
