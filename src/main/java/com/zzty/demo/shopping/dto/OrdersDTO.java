package com.zzty.demo.shopping.dto;

import java.util.Date;

public class OrdersDTO {
	private int id;
	private Date createDate;
	private String address;
	private String state;
	private UserDTO userId;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public UserDTO getUserId() {
		return userId;
	}

	public void setUserId(UserDTO userId) {
		this.userId = userId;
	}

}
