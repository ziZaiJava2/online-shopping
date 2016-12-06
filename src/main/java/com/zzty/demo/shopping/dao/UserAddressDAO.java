package com.zzty.demo.shopping.dao;

import java.sql.PreparedStatement;

import com.zzty.demo.shopping.dto.UserAddressDTO;

public class UserAddressDAO {
	private static final String URL = "jdbc:mysql://localhost:3306/store?user=root&password=root";
	private PreparedStatement preState = null;
	public int addUserAddress(UserAddressDTO userAddress){
		String sql="insert into user_address(address,user_id)"
				+ "values(?,?)";
		try {
			preState=SQLUtil.prepare(URL, sql);
			preState.setString(1, userAddress.getAddress());
			preState.setInt(2, userAddress.getUser().getId());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return 1;
	}
}
