package com.zzty.demo.shopping.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.zzty.demo.shopping.dto.CooperatorDTO;

public class CooperatorDAO extends DaoUtil {
	
	public static void main(String[] args) {
		
	}


	public CooperatorDAO() throws ClassNotFoundException, SQLException {
		super();
	}

	public int addCooperator(CooperatorDTO cooperator) throws SQLException {
		int newCooperatorId = -1;
		String sql = "insert into Cooperator(name,register_date) " + "values(?, ?)";
		Connection connection = getConnection();
		PreparedStatement preparedStatement = null;

		try {
			preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setString(1, cooperator.getName());
			preparedStatement.setString(2, cooperator.getRegister_date());
			preparedStatement.executeQuery();

			ResultSet generateKeyRs = preparedStatement.getGeneratedKeys();

			while (generateKeyRs.next()) {

				newCooperatorId = generateKeyRs.getInt(1);

			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			connection.close();
			preparedStatement.close();
		}

		return newCooperatorId;

	}

	public boolean deleteCooperatorById(int id) throws SQLException {
		boolean result = false;
		Connection connection = getConnection();
		String sql = "delete from cooperator where id=" + id;
		PreparedStatement preparedStatement = null;
		try {
			preparedStatement = connection.prepareStatement(sql);
			preparedStatement.execute();

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			connection.close();
			preparedStatement.close();
		}

		result = true;
		return result;

	}

	public CooperatorDTO getCooperatorById(int cooperatorId) throws SQLException {
		CooperatorDTO cooperator = new CooperatorDTO();
		Connection connection = getConnection();
		String sql = "select from cooperator where id=" + cooperatorId;
		PreparedStatement preparedStatement = null;
		try {
			preparedStatement = connection.prepareStatement(sql);
			ResultSet rs = preparedStatement.executeQuery();

			while (rs.next()) {
				cooperator.setId(rs.getInt("id"));
				cooperator.setName(rs.getString("name"));
				cooperator.setRegister_date(rs.getString("register_date"));

			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			connection.close();
			preparedStatement.close();
		}
		return cooperator;

	}

	public boolean updateCooperator(CooperatorDTO cooperator) throws SQLException {
		boolean result = false;
		Connection connection = getConnection();
		String sql = "update cooperator set name=?,register_date=?" + cooperator;
		PreparedStatement preparedStatement = null;

		try {
			preparedStatement = connection.prepareStatement(sql);

			preparedStatement.setInt(1, cooperator.getId());
			preparedStatement.setString(2, cooperator.getName());
			preparedStatement.setString(3, cooperator.getRegister_date());
			preparedStatement.execute();

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			connection.close();
			preparedStatement.close();
		}
		result = true;
		return result;

	}

	public List<CooperatorDTO> listCooperatorGetAll() throws SQLException {
		List<CooperatorDTO> allCooperator = new ArrayList<CooperatorDTO>();
		Connection connection = getConnection();
		String sql = "select* from cooperator";
		PreparedStatement preparedStatement = null;

		try {
			preparedStatement = connection.prepareStatement(sql);
			ResultSet rs = preparedStatement.executeQuery();
			while (rs.next()) {
				CooperatorDTO cooperator = new CooperatorDTO();
				cooperator.setId(rs.getInt("id"));
				cooperator.setName(rs.getString("name"));
				cooperator.setRegister_date(rs.getString("register_date"));
				allCooperator.add(cooperator);

			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			connection.close();
			preparedStatement.close();
		}
		return allCooperator;

	}
	
}