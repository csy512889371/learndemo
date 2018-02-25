package com.atguigu.day03_mybaits.bean;

public class Order {

	private int id;
	private String orderNo;
	private float price;

	public Order(int id, String orderNo, float price) {
		super();
		this.id = id;
		this.orderNo = orderNo;
		this.price = price;
	}

	public Order() {
		super();
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}

	public float getPrice() {
		return price;
	}

	public void setPrice(float price) {
		this.price = price;
	}

	@Override
	public String toString() {
		return "Order [id=" + id + ", orderNo=" + orderNo + ", price=" + price
				+ "]";
	}

}
