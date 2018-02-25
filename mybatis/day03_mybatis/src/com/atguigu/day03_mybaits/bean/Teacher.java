package com.atguigu.day03_mybaits.bean;

public class Teacher {

	private int id;
	private String name;

	public Teacher(int id, String name) {
		super();
		this.id = id;
		this.name = name;
	}

	public Teacher() {
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

	@Override
	public String toString() {
		return "Teacher [id=" + id + ", name=" + name + "]";
	}

}
