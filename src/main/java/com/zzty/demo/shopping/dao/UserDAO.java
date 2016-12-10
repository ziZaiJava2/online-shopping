package com.zzty.demo.shopping.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.zzty.demo.shopping.dto.UserAddressDTO;
import com.zzty.demo.shopping.dto.UserDTO;

public class UserDAO extends DAO {

	public UserDAO() throws ClassNotFoundException, SQLException {
		super();
	}

	public int addUser(UserDTO user) throws SQLException, ClassNotFoundException {
		int newUserId = -1;

		String sql = "insert into users(login_name, nick_name, email, password) " + "values(?, ?, ?, ?)";

		Connection connection = getConnection();

		PreparedStatement preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
		try {
			connection.setAutoCommit(false);
			preparedStatement.setString(1, user.getLoginName());
			preparedStatement.setString(2, user.getNickName());
			preparedStatement.setString(3, user.getEmail());
			preparedStatement.setString(4, user.getPassword());

			preparedStatement.execute();
			ResultSet generateKeyRs = preparedStatement.getGeneratedKeys();

			while (generateKeyRs.next()) {
				newUserId = generateKeyRs.getInt(1);
			}

			List<UserAddressDTO> userAddresses = user.getAddresses();
			for (UserAddressDTO address : userAddresses) {
				if (address != null) {
					user.setId(newUserId);
					address.setUser(user);

					UserAddressDAO userAddressDAO = new UserAddressDAO();

					userAddressDAO.setConnection(connection);

					userAddressDAO.addUserAddress(address);
				}
			}

			connection.commit();
		} catch (SQLException e) {
			connection.rollback();
		} finally {
			preparedStatement.close();
			connection.close();
		}
		return newUserId;
	}

	public boolean deleteUserById(int id) throws SQLException {
		Connection connection = getConnection();
		try {
			connection.setAutoCommit(false);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		String sql1 = "delete from user_address where user_id=" + id;
		String sql2 = "delete from users where id=" + id;
		PreparedStatement preparedStatement = connection.prepareStatement(sql1);
		PreparedStatement preparedStatement2 = connection.prepareStatement(sql2);
		try {
			preparedStatement.execute();
			preparedStatement2.execute();
			connection.commit();
		} catch (SQLException e) {
			connection.rollback();
			e.printStackTrace();
		} finally {
			preparedStatement.close();
			preparedStatement2.close();
			connection.close();
		}

		return true;
	}

	public UserDTO getUserById(int userId) throws SQLException {
		UserDTO user = new UserDTO();
		Connection connection = getConnection();
		connection.setAutoCommit(false);
		PreparedStatement preparedStatement = null;
		PreparedStatement preparedStatement2 = null;
		List<UserAddressDTO> userList = new ArrayList<>();
		String sql1 = "select * from users where id=" + userId;
		String sql2 = "select address from user_address where user_id=" + userId;
		try {
			preparedStatement = connection.prepareStatement(sql1);
			preparedStatement2 = connection.prepareStatement(sql2);
			ResultSet res = preparedStatement.executeQuery();
			ResultSet res2 = preparedStatement2.executeQuery();
			while (res.next()) {
				user.setId(res.getInt("id"));
				user.setLoginName(res.getString("login_name"));
				user.setNickName(res.getString("nick_name"));
				user.setEmail(res.getString("email"));
				user.setPassword(res.getString("password"));
			}
			while (res2.next()) {
				UserAddressDTO userAddress = new UserAddressDTO();
				userAddress.setAddress(res.getString("address"));
				userList.add(userAddress);
			}
			user.setAddresses(userList);
			connection.commit();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			connection.rollback();
		} finally {
			preparedStatement2.close();
			preparedStatement.close();
			connection.close();
		}

		return user;
	}

	public void updateUser(UserDTO user) throws SQLException {
		Connection connection = getConnection();
		PreparedStatement preparedStatement = null;
		PreparedStatement preparedStatement2 = null;
		PreparedStatement preparedStatement3 = null;
		String sql = "update users set login_name=?,nick_name=?,email=?,password=? where id=?";
		String sql1 = "delete from user_address where user_id=" + user.getId();
		String sql2 = "insert into user_address (address,user_id,is_default) values (?,?,?)";
		connection.setAutoCommit(false);
		try {
			preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setString(1, user.getLoginName());
			preparedStatement.setString(2, user.getNickName());
			preparedStatement.setString(3, user.getEmail());
			preparedStatement.setString(4, user.getPassword());
			preparedStatement.setInt(5, user.getId());
			preparedStatement.execute();
			preparedStatement2 = connection.prepareStatement(sql1);
			preparedStatement2.execute();
			preparedStatement3 = connection.prepareStatement(sql2);
			List<UserAddressDTO> addressList = user.getAddresses();
			for (UserAddressDTO userAddress : addressList) {
				preparedStatement3.setString(1, userAddress.getAddress());
				preparedStatement3.setInt(2, user.getId());
				preparedStatement3.setBoolean(3, userAddress.isDefault());
				preparedStatement3.execute();
			}
			connection.commit();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			connection.rollback();
			e.printStackTrace();
		} finally {
			preparedStatement3.close();
			preparedStatement2.close();
			preparedStatement.close();
			connection.close();
		}
	}

	public List<UserDTO> getAllUser() throws SQLException {
		List<UserDTO> userList = new ArrayList<>();
		Connection connection = getConnection();
		String sql = "select * from users";
		String sql1 = "select address from user_address where user_id=?";
		PreparedStatement preparedStatement = null;
		PreparedStatement preparedStatement2 = null;
		try {
			preparedStatement = connection.prepareStatement(sql);
			preparedStatement2 = connection.prepareStatement(sql1);
			ResultSet res = preparedStatement.executeQuery();
			while (res.next()) {
				UserDTO user = new UserDTO();
				user.setId(res.getInt("id"));
				user.setLoginName(res.getString("login_name"));
				user.setNickName(res.getString("nick_name"));
				user.setEmail(res.getString("email"));
				user.setPassword(res.getString("password"));
				preparedStatement2.setInt(1, user.getId());
				ResultSet res1 = preparedStatement2.executeQuery();
				List<UserAddressDTO> userAddressList = new ArrayList<>();
				while (res1.next()) {
					UserAddressDTO userAddress = new UserAddressDTO();
					userAddress.setAddress(res1.getString("address"));
					userAddressList.add(userAddress);
				}
				user.setAddresses(userAddressList);
				userList.add(user);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			preparedStatement.close();
			connection.close();
		}
		return userList;
	}

}
