package com.zzty.demo.shopping.dto;

public class userAddressDTO {

	private int id;
	private String address;
	private int userId;
	private boolean isDrfault;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public int getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}
	public boolean isDrfault() {
		return isDrfault;
	}
	public void setDrfault(boolean isDrfault) {
		this.isDrfault = isDrfault;
	}
}
