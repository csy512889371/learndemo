package com.atguigu.day03_mybaits.bean;

import java.util.List;

public class Classes {

	private int id;
	private String name;
	private Teacher teacher;
	private List<Student> list;

	public Classes(int id, String name, Teacher teacher, List<Student> list) {
		super();
		this.id = id;
		this.name = name;
		this.teacher = teacher;
		this.list = list;
	}

	public Classes(int id, String name, Teacher teacher) {
		super();
		this.id = id;
		this.name = name;
		this.teacher = teacher;
	}

	public Classes() {
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

	public Teacher getTeacher() {
		return teacher;
	}

	public void setTeacher(Teacher teacher) {
		this.teacher = teacher;
	}

	@Override
	public String toString() {
		return "Classes [id=" + id + ", name=" + name + ", teacher=" + teacher
				+ ", list=" + list + "]";
	}

}
