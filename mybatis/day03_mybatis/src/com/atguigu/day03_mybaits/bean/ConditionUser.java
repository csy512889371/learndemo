package com.atguigu.day03_mybaits.bean;

public class ConditionUser {

	private String name;
	private int minAge;
	private int maxAge;

	public ConditionUser(String name, int minAge, int maxAge) {
		super();
		this.name = name;
		this.minAge = minAge;
		this.maxAge = maxAge;
	}

	public ConditionUser() {
		super();
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getMinAge() {
		return minAge;
	}

	public void setMinAge(int minAge) {
		this.minAge = minAge;
	}

	public int getMaxAge() {
		return maxAge;
	}

	public void setMaxAge(int maxAge) {
		this.maxAge = maxAge;
	}

	@Override
	public String toString() {
		return "ConditionUser [name=" + name + ", minAge=" + minAge
				+ ", maxAge=" + maxAge + "]";
	}

}
