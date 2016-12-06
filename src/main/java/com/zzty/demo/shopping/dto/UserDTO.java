package com.zzty.demo.shopping.dto;

public class UserDTO {
	private String loginName;
	private String nickName;
	private UserAdressDTO userAdress;
	private String email;
	private String password;
	private int id;
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
	public UserAdressDTO getUserAdress() {
		return userAdress;
	}
	public void setUserAdress(UserAdressDTO userAdress) {
		this.userAdress = userAdress;
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
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
}
