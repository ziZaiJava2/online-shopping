package com.zzty.demo.shopping.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;

import com.zzty.demo.shopping.dto.CooperatorDTO;
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
public class CooperatorDAO extends DAO {
	
	public CooperatorDAO() throws ClassNotFoundException, SQLException {
		super();
	}
	
	public int addCooperator(CooperatorDTO cooperator) throws SQLException, ClassNotFoundException {
		int newId = -1;
		Connection connection = getConnection();
		String sql = "insert into cooperator(name,register_date)values(?,?)";
		PreparedStatement preparedStatement = null;
		preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
		try {
			preparedStatement.setString(1, cooperator.getName());
			preparedStatement.setDate(2, new java.sql.Date(cooperator.getRegister_date().getTime()));
			preparedStatement.execute();
			ResultSet generateKeyRs = preparedStatement.getGeneratedKeys();
			while (generateKeyRs.next()) {
				newId = generateKeyRs.getInt(1);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			preparedStatement.close();
			connection.close();
		}
		return newId;
	}

	public void deleteCooperator(int cooperatorId) throws SQLException, ClassNotFoundException {
		Connection connection = getConnection();
		PreparedStatement preparedStatement = null;
		String sql = "delete from cooperator where id =?" + cooperatorId;
		preparedStatement = connection.prepareStatement(sql);
		try {
			preparedStatement.setInt(1, cooperatorId);
			preparedStatement.execute();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			preparedStatement.close();
			connection.close();
		}
	}

	public CooperatorDTO getCooperatorById(int cooperatorId) throws SQLException, ClassNotFoundException {
		CooperatorDTO cooperator = new CooperatorDTO();
		Connection connection = getConnection();
		PreparedStatement preparedStatement = null;
		String sql = "select * from cooperator where id=" + cooperatorId;
		preparedStatement = connection.prepareStatement(sql);
		try {
			ResultSet res = preparedStatement.executeQuery();
			while (res.next()) {
				cooperator.setId(res.getInt("id"));
				cooperator.setName(res.getString("name"));
				cooperator.setRegister_date(res.getDate("register_date"));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			preparedStatement.close();
			connection.close();
		}
		return cooperator;
	}

	public void updateCooperator(CooperatorDTO cooperator) throws SQLException, ClassNotFoundException {
		Connection connection = getConnection();
		PreparedStatement preparedStatement = null;
		String sql = "update cooperator set name=?,register_date=? where id=?";
		preparedStatement = connection.prepareStatement(sql);
		try {
			preparedStatement.setDate(2, new java.sql.Date(cooperator.getRegister_date().getTime()));
			preparedStatement.setString(1, cooperator.getName());
			preparedStatement.execute();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			preparedStatement.close();
			connection.close();
		}
	}

	public List<CooperatorDTO> getAllCooperator() throws SQLException, ClassNotFoundException {
		List<CooperatorDTO> cooperatorList = new ArrayList<>();
		Connection connection = getConnection();
		PreparedStatement preparedStatement = null;
		String sql = "select * from cooperator";
		preparedStatement = connection.prepareStatement(sql);
		try {
			ResultSet res = preparedStatement.executeQuery();
			while (res.next()) {
				CooperatorDTO cooperator = new CooperatorDTO();
				cooperator.setName(res.getString("name"));
				cooperator.setRegister_date(res.getDate("register_date"));
				cooperatorList.add(cooperator);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			preparedStatement.close();
			connection.close();
		}
		return cooperatorList;
	}

}
