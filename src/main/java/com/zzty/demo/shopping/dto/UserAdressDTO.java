package com.zzty.demo.shopping.dto;

public class UserAdressDTO {
	private String adress;
	private UserDTO userId;
    private int id;
	public String getAdress() {
		return adress;
	}
	public void setAdress(String adress) {
		this.adress = adress;
	}
	public UserDTO getUserId() {
		return userId;
	}
	public void setUserId(UserDTO userId) {
		this.userId = userId;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}


}
