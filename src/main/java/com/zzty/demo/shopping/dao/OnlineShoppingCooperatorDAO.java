package com.zzty.demo.shopping.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import com.zzty.demo.shopping.dto.OnlineShoppingCooperatorDTO;

public class OnlineShoppingCooperatorDAO extends DaoUtil  {
	public OnlineShoppingCooperatorDAO()throws ClassNotFoundException, SQLException{
		super();
	}
	
//增加
	public void addCooperator(OnlineShoppingCooperatorDTO cooperator) throws SQLException{
	String sql="insert into online_shopping.cooperator(name,date)"+"values(?,?)";
	PreparedStatement ps = null;
	Connection con = getConnection();
	try {
//		注意date的转换
		java.sql.Date day = new java.sql.Date(cooperator.getDate().getTime());
		con.setAutoCommit(false);
		ps = con.prepareStatement(sql);
		ps.setString(1, cooperator.getName());
		ps.setDate(2, day);
		ps.execute();
		con.commit();
	} catch (SQLException e) {
		e.printStackTrace();
		con.rollback();
	} finally{
		ps.close();
		con.close();
	}
	}
	
//	刪除
	public void deleteCooperatorById(int id) throws SQLException{
		String sql = "delete from online_shopping.cooperator where id ="+ id;
		Connection con = getConnection();
		PreparedStatement ps = null;
		try {
			con.setAutoCommit(false);
			ps = con.prepareStatement(sql);
			ps.execute();
			con.commit();
			
		} catch (SQLException e) {
			e.printStackTrace();
			con.rollback();
		} finally{
			ps.close();
			con.close();
		}
	}
	

}
