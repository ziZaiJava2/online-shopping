package com.zzty.demo.shopping.dao;

import java.sql.PreparedStatement;

import com.zzty.demo.shopping.dto.UserDTO;

public class UserDAO {
	private static final String URL = "jdbc:mysql://localhost:3306/store?user=root&password=root";
	private PreparedStatement preState = null;
	public int addUser(UserDTO user){
		UserAddressDAO userAddressDAO=new UserAddressDAO();
		int addressId=userAddressDAO.addUserAddress(user.getUserAddress());
		
		String sql="insert into user(login_name,nick_name,default_address_id,email,password)"
				+ "values(?,?,?,?,?)";
		try {
			preState =SQLUtil.prepare(URL, sql);
			preState.setString(1, user.getLoginName());
			preState.setString(2, user.getNickName());
			preState.setInt(3, addressId);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return 1;
	}
}
