package com.zzty.demo.shopping.dto;

public class UserAddressDTO {
	private int id;
	private String address;
	private UserDTO  uesr;
	private boolean isDefault;

	public static void main(String[] args) {
		

	}

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

	public UserDTO getUesr() {
		return uesr;
	}

	public void setUesr(UserDTO uesr) {
		this.uesr = uesr;
	}

	public boolean isDefault() {
		return isDefault;
	}

	public void setDefault(boolean isDefault) {
		this.isDefault = isDefault;
	}

	

	

}
