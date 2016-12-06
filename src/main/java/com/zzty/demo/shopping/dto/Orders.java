package com.zzty.demo.shopping.dto;

import java.util.Date;

import com.zzty.demo.shopping.dao.UserDAO;

public class Orders {
	private String id;
	private Date createDate;
	private String address;
	private String state;
	private UserDAO user;

	public String getId() {
		return id;
	}

	public void setId(String id) {
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

	public UserDAO getUser() {
		return user;
	}

	public void setUser(UserDAO user) {
		this.user = user;
	}

}
