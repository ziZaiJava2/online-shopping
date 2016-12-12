package com.zzty.demo.shopping.dto;

import java.util.List;

public class UserDTO {
	private int id;
	private String LoginName;
	private String nickName;
	private List<UserAddressDTO> addresses;
	private String email;
	private String password;

	
	
	public static void main(String[] args) {
		

	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getLoginName() {
		return LoginName;
	}

	public void setLoginName(String loginName) {
		LoginName = loginName;
	}

	public String getNickName() {
		return nickName;
	}

	public void setNickName(String nickName) {
		this.nickName = nickName;
	}

	public List<UserAddressDTO> getAddresses() {
		return addresses;
	}

	public void setAddresses(List<UserAddressDTO> addresses) {
		this.addresses = addresses;
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

}
