package com.zzty.demo.shopping.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import com.zzty.demo.shopping.dto.UserAddressDTO;

public class UserAddressDAO extends DAO {

	public UserAddressDAO() throws ClassNotFoundException, SQLException {
		super();
	}

	public void addUserAddress(UserAddressDTO userAddress) throws SQLException {
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
		}

	}
}
