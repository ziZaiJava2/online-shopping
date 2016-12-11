package com.zzty.demo.shopping.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.zzty.demo.shopping.dto.UserAddressDTO;

public class UserAddressDAO extends DAO {

	public UserAddressDAO() throws ClassNotFoundException, SQLException {
		super();
	}

	public void addUserAddress(UserAddressDTO userAddress) throws SQLException, ClassNotFoundException {
		String sql = "insert into user_address(address, user_id, is_default) values(?, ?, ?)";
		Connection connection = getConnection();
		PreparedStatement preparedStatement = connection.prepareStatement(sql);
		try {
			preparedStatement.setString(1, userAddress.getAddress());
			preparedStatement.setInt(2, userAddress.getUser().getId());
			preparedStatement.setBoolean(3, userAddress.isDefault());
			preparedStatement.execute();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			preparedStatement.close();
			connection.close();
		}

	}

	public void deleteUserAddress(int userAddressId) throws SQLException, ClassNotFoundException {
		String sql = "delete from user_address where id=" + userAddressId;
		Connection connection = getConnection();
		PreparedStatement preparedStatement = connection.prepareStatement(sql);
		try {
			preparedStatement.execute();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			preparedStatement.close();
			connection.close();
		}
	}

	public void updateUserAddress(UserAddressDTO userAddress) throws SQLException, ClassNotFoundException {
		Connection connection = getConnection();
		PreparedStatement preparedStatement = null;
		String sql = "update user_address set address=?,user_id=?,is_default=? where id=?";
		preparedStatement = connection.prepareStatement(sql);
		try {
			preparedStatement.setString(1, userAddress.getAddress());
			preparedStatement.setInt(2, userAddress.getUser().getId());
			preparedStatement.setBoolean(3, userAddress.isDefault());
			preparedStatement.setInt(4, userAddress.getId());
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			preparedStatement.close();
			connection.close();
		}

	}

	public UserAddressDTO getUserAddressById(int userAddressId) throws ClassNotFoundException, SQLException {
		UserAddressDTO userAddress = new UserAddressDTO();
		UserDAO userDao = new UserDAO();
		Connection connection = getConnection();
		PreparedStatement preparedStatement = null;
		String sql = "select * from user_address where id=" + userAddressId;
		preparedStatement = connection.prepareStatement(sql);
		try {
			ResultSet res = preparedStatement.executeQuery();
			while (res.next()) {
				userAddress.setId(res.getInt("id"));
				userAddress.setAddress(res.getString("address"));
				userAddress.setDefault(res.getBoolean("is_default"));
				userAddress.setUser(userDao.getUserById(res.getInt("user_id")));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			preparedStatement.close();
			connection.close();
		}
		return userAddress;
	}

	public List<UserAddressDTO> getAllUserAddress() throws ClassNotFoundException, SQLException {
		List<UserAddressDTO> userAddressList = new ArrayList<>();
		UserDAO userDao = new UserDAO();
		Connection connection = getConnection();
		PreparedStatement preparedStatement = null;
		String sql = "select * from user_address";
		preparedStatement = connection.prepareStatement(sql);
		try {
			ResultSet res = preparedStatement.executeQuery();
			while (res.next()) {
				UserAddressDTO userAddress = new UserAddressDTO();
				userAddress.setId(res.getInt("id"));
				userAddress.setAddress(res.getString("address"));
				userAddress.setDefault(res.getBoolean("is_defalult"));
				userAddress.setUser(userDao.getUserById(res.getInt("user_id")));
				userAddressList.add(userAddress);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			preparedStatement.close();
			connection.close();
		}
		return userAddressList;
	}

}
