package com.zzty.demo.shopping.dto;
import java.util.List;

public class UserDTO {
	private int id;
	private String loginName;
	private String nickName;
	private String email;
	private String password;
	// 昨天漏讲了一部分， 只讲了defaultAddress 要转换为对应的对象。修改了表结构， 把user表中的defaultAddress字段删除掉了。
	// 在user_address表中新加了字段 is_default来标识 是否为默认地址。
	// user表和 user_address是一对多的关系， 所以UserDTO中应该有一个List 这个包含这个user所有的地址
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
