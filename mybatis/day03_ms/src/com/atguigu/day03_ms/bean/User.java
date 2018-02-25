package com.atguigu.day03_ms.bean;

import java.util.Date;

public class User {

	private int id;
	private String name;
	private Date birthday;
	private double salary;

	public User(int id, String name, Date birthday, double salary) {
		super();
		this.id = id;
		this.name = name;
		this.birthday = birthday;
		this.salary = salary;
	}

	public User() {
		super();
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Date getBirthday() {
		return birthday;
	}

	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}

	public double getSalary() {
		return salary;
	}

	public void setSalary(double salary) {
		this.salary = salary;
	}

	@Override
	public String toString() {
		return "User [id=" + id + ", name=" + name + ", birthday=" + birthday
				+ ", salary=" + salary + "]";
	}

}
