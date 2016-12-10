package com.zzty.demo.shopping.dto;

import java.util.List;

public class UserDTO {
	private int id;
	private String loginName;
	private String nickName;
	private String email;
	private String password;
	private List<UserAddressDTO> addresses;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getLoginName() {
		return loginName;
	}

	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}

	public String getNickName() {
		return nickName;
	}

	public void setNickName(String nickName) {
		this.nickName = nickName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public List<UserAddressDTO> getAddresses() {
		return addresses;
	}

	public void setAddresses(List<UserAddressDTO> addresses) {
		this.addresses = addresses;
	}
}
