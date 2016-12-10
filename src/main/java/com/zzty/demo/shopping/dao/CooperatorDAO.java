package com.zzty.demo.shopping.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.zzty.demo.shopping.dto.CooperatorDTO;

public class CooperatorDAO extends DAO {

	public CooperatorDAO() throws ClassNotFoundException, SQLException {
		super();
		// TODO Auto-generated constructor stub
	}

	public void addCooperator(CooperatorDTO coo) throws SQLException {

		Connection c = getConnection();
		PreparedStatement ps = null;
		java.sql.Date d = new java.sql.Date(coo.getRegisterDate().getTime());
		String sql = "insert into cooperator (name, register_date) values(? ,?)";
		try {
			c.setAutoCommit(false);
			ps = c.prepareStatement(sql);
			ps.setString(1, coo.getName());
			ps.setDate(2, d);
			ps.execute();

			c.commit();

		} catch (SQLException e) {
			c.rollback();
		} finally {
			ps.close();
			c.close();
		}
	}

	public boolean deleteCooperator(int id) throws SQLException {
		boolean result = false;
		Connection c = getConnection();
		PreparedStatement ps = null;
		String sql = "delete from cooperator where id = " + id;
		try {
			ps = c.prepareStatement(sql);
			ps.execute();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			ps.close();
			c.close();
		}
		return result;
	}

	public CooperatorDTO getCooperator(int cooperatorId) throws SQLException {
		CooperatorDTO coo = new CooperatorDTO();
		Connection c = getConnection();
		PreparedStatement p1 = null;
		String sql = "select * from cooperator where id =" + cooperatorId;
		try {
			c.setAutoCommit(false);
			p1 = c.prepareStatement(sql);
			ResultSet rs = p1.executeQuery();

			while (rs.next()) {
				coo.setId(rs.getInt("id"));
				coo.setName(rs.getString("name"));
				coo.setRegisterDate(rs.getDate("register_date"));
			}

			c.commit();
		} catch (SQLException e) {
			c.rollback();
		} finally {
			p1.close();
			c.close();
		}
		return coo;
	}

	public void updateCooperator(CooperatorDTO cooperator) throws SQLException {
		Connection c = getConnection();
		PreparedStatement ps = null;
		String sql = "update cooperator set name = ?, register_date = ?";
		try {
			java.sql.Date d = new java.sql.Date(cooperator.getRegisterDate().getTime());
			c.setAutoCommit(false);
			ps = c.prepareStatement(sql);
			ps.setString(1, cooperator.getName());
			ps.setDate(2, d);

			ps.execute();

			c.commit();
		} catch (SQLException e) {
			c.rollback();

		} finally {
			ps.close();
			c.close();
		}
	}
	
	public List<CooperatorDTO> listCooperator() throws SQLException{
		List<CooperatorDTO> allCooperator = new ArrayList<CooperatorDTO>();
		Connection c = getConnection();
		PreparedStatement ps =null;
		String sql = "select *from cooperator";
		try {
			c.setAutoCommit(false);
			ps = c.prepareStatement(sql);
			ResultSet rs =ps.executeQuery();
			while (rs.next()){
				CooperatorDTO coo = new CooperatorDTO();
				coo.setId(rs.getInt("id"));
				coo.setName(rs.getString("name"));
				coo.setRegisterDate(rs.getDate("register_date"));
				allCooperator.add(coo);
			}
			c.commit();
		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
			ps.close();
			c.close();
		}
		return allCooperator;
	}
}
