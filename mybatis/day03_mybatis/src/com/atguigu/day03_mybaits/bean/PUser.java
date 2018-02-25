package com.atguigu.day03_mybaits.bean;

public class PUser {

	private String id;
	private String name;
	private String sex;

	public PUser(String id, String name, String sex) {
		super();
		this.id = id;
		this.name = name;
		this.sex = sex;
	}

	public PUser() {
		super();
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	@Override
	public String toString() {
		return "PUser [id=" + id + ", name=" + name + ", sex=" + sex + "]";
	}

}
