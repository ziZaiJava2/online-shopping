package com.zzty.demo.shopping.dao;


import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.action.Action;
import com.zzty.demo.shopping.dto.UserDTO;

public class UserDAO {

	public void addUser() {
		
	}
	
	public void delUse() {
		
	}
	
	public void updateUse() {
		
	}
	
	public UserDTO selUse() {
		UserDTO user = new UserDTO();
		Connection conn = Action.getConnection();
		
		
		return user;
	}
	
	public List<UserDTO> selectUse() throws SQLException {
		List<UserDTO> UsersList = new ArrayList<UserDTO>();
		Connection conn = Action.getConnection();
		Statement stat = conn.createStatement();
		ResultSet rs = stat.executeQuery("select * from online_shopping");
		UserDTO user = new UserDTO();
		while(rs.next()){
			user.setLogin_name(rs.getString("login_name"));
			user.setEmail(rs.getString("email"));
			user.setNick_name(rs.getString("nick_name"));
			user.setPassword(rs.getString("password"));
			
			UsersList.add(user);
			
		}
		return UsersList;
	}
	
}
