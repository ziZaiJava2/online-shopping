package com.zzty.demo.shopping.dto;

import java.util.Date;

public class OrderDTO {
	private int id;
	private Date createDate;
	private UserAdressDTO userAdress;
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

	public UserAdressDTO getUserAdress() {
		return userAdress;
	}

	public void setUserAdress(UserAdressDTO userAdress) {
		this.userAdress = userAdress;
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
