package com.ctoedu.service.Domain.Secondary;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

/**
 * spring-data-jpa访问mysql
 *
 *
 */
@Entity
public class Teacher2 {

	@Id
	@GeneratedValue
	private Integer id;

	@Column(nullable = false)
	private String name;

	@Column(nullable = false)
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
