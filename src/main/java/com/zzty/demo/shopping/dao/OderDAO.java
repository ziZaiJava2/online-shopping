package com.zzty.demo.shopping.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.zzty.demo.shopping.dto.OrderDTO;
import com.zzty.demo.shopping.dto.UserAdressDTO;
import com.zzty.demo.shopping.dto.UserDTO;

public class OderDAO {
	private Connection con = null;

	public boolean addOrder(OrderDTO order) throws ClassNotFoundException, SQLException {
		con = DBUtil.useMysql();
		String sql = "insert into user(create_date,address,adress, state, user_id)values(?, ?, ?, ?, ?)";
		java.sql.PreparedStatement pst = con.prepareStatement(sql);
		pst.setDate(1, (Date) order.getCreateDate());
		pst.setString(2, order.getUserAdress());
		pst.setString(3, order.getState());
		pst.setInt(4, order.getUserId());

		return pst.execute();
	}

	public boolean updateOrder(OrderDTO order) throws ClassNotFoundException, SQLException {
		con = DBUtil.useMysql();
		Statement st = con.createStatement();
		if (!"".equals(user.getEmail()) && user.getEmail() != null) {
			String sql = "update user set email = " + user.getEmail() + "where id = " + user.getId();
			return st.execute(sql);
		} else if (!"".equals(user.getLoginName()) && user.getLoginName() != null) {
			String sql = "update user set loginname = " + user.getLoginName() + "where id = " + user.getId();
			return st.execute(sql);
		} else if (!"".equals(user.getNickName()) && user.getNickName() != null) {
			String sql = "update user set nickname = " + user.getNickName() + "where id = " + user.getId();
			return st.execute(sql);
		} else if (!"".equals(user.getPassword()) && user.getPassword() != null) {
			String sql = "update user set password = " + user.getPassword() + "where id = " + user.getId();
			return st.execute(sql);
		} else {
			String sql = "update user set adress = " + user.getUserAdress().getAdress() + "where id = " + user.getId();
			return st.execute(sql);
		}
	}

	public boolean deleteUser(UserDTO user) throws ClassNotFoundException, SQLException {
		con = DBUtil.useMysql();
		Statement st = con.createStatement();
		String sql = "delete from user where id = " + user.getId();
		return st.execute(sql);
	}

	public String getAllUser() throws ClassNotFoundException, SQLException {
		con = DBUtil.useMysql();
		Statement st = con.createStatement();
		String sql = "select * from user";
		ResultSet rs = st.executeQuery(sql);
		String loginName;
		int adressId;
		String nickName;
		String email;
		String password;
		String userMessage = "";
		while (rs.next()) {
			loginName = rs.getString(2);
			nickName = rs.getString(3);
			adressId = rs.getInt(4);
			email = rs.getString(5);
			password = rs.getString(6);
			userMessage += "loginName: " + loginName + " " + "nickName: " + nickName + " " + "adressId: " + adressId
					+ " " + "email:" + email + "password: " + password + " " + "\n";
		}
		return userMessage;
	}

	public UserDTO getUser(int userId) throws ClassNotFoundException, SQLException {
		UserDTO u = null;
		UserAdressDTO ua = null;
		con = DBUtil.useMysql();
		Statement st = con.createStatement();
		String sql1 = "select * from user where user.id = " + userId;
		String sql2 = "selcet * from user_address where user_id = " + userId;
		ResultSet rs = st.executeQuery(sql1);
		ResultSet rs2 = st.executeQuery(sql2);
		u.setEmail(rs.getString(5));
		u.setLoginName(rs.getString(2));
		u.setNickName(rs.getString(3));
		u.setPassword(rs.getString(6));
		u.setId(rs.getInt(1));
		ua.setAdress(rs2.getString(1));
		ua.setUserId(rs.getInt(2));
		u.setUserAdress(ua);
		return u;
	}

}
