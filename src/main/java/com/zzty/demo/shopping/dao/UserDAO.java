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

public class UserDAO extends DaoUtil {

	public UserDAO() throws ClassNotFoundException, SQLException {
		super();

	}

	public int addUser(UserDTO user) throws ClassNotFoundException, SQLException {
		// ��ʼ��һ����������ʾ�²��뵽user���е����ݵ�id, ���id���ڲ���userAddress��Ϣ��ʱ���õ�
		// ��Ϊ��û�в��룬���Գ�ʼ����ֵΪ -1 ��ֹ������ֵ����
		int newUserId = -1;

		String sql = "insert into users(login_name, nick_name, email, password) " + "values(?, ?, ?, ?)";
		// ͨ�����ø����getConnection() ����ȡһ��jdbc connection
		Connection connection = getConnection();

		// ����PreparedStatement ���д��������֮ǰ�̵Ĳ�һ���� ����һ������
		// Statement.RETURN_GENERATED_KEYS

		// �����������ԭ���ǣ� �����ǲ����µ�user���ݵ�user���У� user��id�������ģ�
		// ������������Ǻ�������ڲ����������Ժ�ȡ�����µ�id

		// ���Բο� http://lavasoft.blog.51cto.com/62575/238643/

		PreparedStatement preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

		try {

			// ��Ϊ���ǲ���user��Ϣ��user�� �У� ����Ҫ��UserDTO�ж�Ӧ�� ���е�UserAddressDTO
			// ���뵽user_address���У� ��������Ҫ�� transaction�� ���������ơ�

			// һ������user���� ���� user_address����ʧ�ܣ� ���в��붼����Ч��
			// ֻ��user��user_address���ݶ�����������һ���ύ�����ݿ���

			// ���������connection�� autoCommit�Զ��ύ��Ϊfalse

			connection.setAutoCommit(false);

			preparedStatement.setString(1, user.getLoginName());

			preparedStatement.setString(2, user.getNickName());

			preparedStatement.setString(3, user.getEmail());

			preparedStatement.setString(4, user.getPassword());

			preparedStatement.execute();

			// ��ȡ�������ݺ���������� ���Բο� http://lavasoft.blog.51cto.com/62575/238643/

			ResultSet generateKeyRs = preparedStatement.getGeneratedKeys();

			while (generateKeyRs.next()) {

				newUserId = generateKeyRs.getInt(1);

			}

			// user��userAddress�� 1�Զ������� ����UserDTO�л���һ�� List<UserAddressDTO>
			// ���������user���еĵ�ַ

			// ��������������һ���û���ʱ��ֹҪ���û�������Ϣ���뵽 user���У���Ҫ���û������е�ַ��Ϣ���뵽 user_address����

			List<UserAddressDTO> userAddresses = user.getAddresses();

			for (UserAddressDTO userAddress : userAddresses) {

				if (userAddress != null) {

					// user_address ����Ҫ����user id ��������Ҫ�Ѳ���user�����ݿ���������ɵ�
					// userId���ø� UserAddressDTO�е� UserDTO

					user.setId(newUserId);

					userAddress.setUesr(user);

					UserAddressDAO userAddressDAO = new UserAddressDAO();

					// �ر�ǿ�� �������� ��Ϊ����Ҫ����������֤ �û���Ϣ �͵�ַ��Ϣ ͬʱ��ȷ���ύ�����ݿ��У�������ͬʱʧ�ܶ����ύ
					// ��Ϊjdbc��connection������������صĲ����� ��������ʵ����һ��UserAddressDAO����ʱ��
					// ���ʼ��һ���µ�Connection����
					// ��UserDAO ʵ������ Connection ����ͬһ������
					// �����������ǵ���setConnection() �������� UserDAOʵ������Connection���õ�
					// UserAddressDAO�У� ��������DAO��������ͬһ�� Connection

					userAddressDAO.setConnection(connection);
					// ����UserAddressDAO ��addUserAddress()�����������еĵ�ַ��Ϣ
					// ���뵽user_address����

					userAddressDAO.addUserAddress(userAddress);

				}

			}

			// û���쳣���� ����connection.commit() �����������ݿ��ύ �û����� �͵�ַ���ݵĲ���
			// ֮ǰֻ�ǽ������ύ�����ݿ�Ļ����в�û�������ύ

			connection.commit();

		} catch (SQLException e) {

			// ���쳣������ ���֮ǰ��������ݿ�����ع����൱��������֮ǰ�Ļ��棬 �൱��û�ж����ݿ����κβ���

			connection.rollback();

		} finally {

			preparedStatement.close();

			connection.close();

		}

		return newUserId;

	}

	// Ҫɾ��һ�����ݵĻ���������������ҲҪɾ����
	public boolean deleteUserById(int id) throws SQLException {
		boolean result = false;
		Connection connection = getConnection();
		PreparedStatement preparedStatement1 = null;
		PreparedStatement preparedStatement2 = null;

		try {
			connection.setAutoCommit(false);
			String sql1 = "delete from user_address where user_id=" + id;
			String sql2 = "delete from user where user_id=" + id;

			preparedStatement1 = connection.prepareStatement(sql1);
			preparedStatement2 = connection.prepareStatement(sql2);

			preparedStatement1.execute();
			preparedStatement2.execute();
		} catch (SQLException e) {

			e.printStackTrace();
			connection.rollback();
		}

		result = true;

		return result;
	}

	// ���ݿ�Ĳ�ѯ
	public UserDTO getUserById(int userId) throws SQLException {
		UserDTO user = new UserDTO();

		Connection connection = getConnection();
		PreparedStatement preparedStatement1 = null;
		PreparedStatement preparedStatement2 = null;
		try {
			connection.setAutoCommit(false);
			String sql1 = "select from user where id=" + userId;
			String sql2 = "select from user where user_address user_id=" + userId;

			preparedStatement1 = connection.prepareStatement(sql1);
			preparedStatement2 = connection.prepareStatement(sql2);

			ResultSet rs1 = preparedStatement1.executeQuery();

			while (rs1.next()) {
				user.setId(rs1.getInt("id"));
				user.setLoginName(rs1.getString("LoginName"));
				user.setEmail(rs1.getString("Email"));
				user.setNickName(rs1.getString("NickName"));
				user.setPassword(rs1.getString("Password"));
			}

			ResultSet rs2 = preparedStatement2.executeQuery();

			List<UserAddressDTO> list = new ArrayList<UserAddressDTO>();
			while (rs2.next()) {
				UserAddressDTO userAddress = new UserAddressDTO();

				userAddress.setAddress(rs2.getString("address"));

				list.add(userAddress);
			}
			user.setAddresses(list);
			connection.commit();

		} catch (SQLException e) {

			e.printStackTrace();
			connection.rollback();
		}
		return user;

	}

	public boolean updateUser(UserDTO user) throws SQLException {
		// �����û��Ļ�����Ϣ

		Connection connection = getConnection();
		PreparedStatement preparedStatement1 = null;
		PreparedStatement preparedStatement2 = null;
		PreparedStatement preparedStatement3 = null;

		try {
			connection.setAutoCommit(false);
			String sql1 = "update user set LoginName=?,nickName=?,email=?,password=?" + user;
			String sql2 = "delete from user_address where user_id=" + user.getId();
			String sql3 = "insert into user_address(address,user_id,is_Default)values(?,?,?)" + user;

			preparedStatement1 = connection.prepareStatement(sql1);

			preparedStatement1.setString(1, user.getLoginName());
			preparedStatement1.setString(2, user.getNickName());
			preparedStatement1.setString(3, user.getEmail());
			preparedStatement1.setString(4, user.getPassword());
			preparedStatement1.setInt(5, user.getId());
			preparedStatement1.execute();

			preparedStatement2 = connection.prepareStatement(sql2);
			preparedStatement2.execute();
			// �����û��ĵ�ַ��Ϣ��1.ɾ��ԭ�ȵ���Ϣ 2.�������Ҫ����Ϣ
			List<UserAddressDTO> addressList = user.getAddresses();
			for (UserAddressDTO address : addressList) {
				preparedStatement3 = connection.prepareStatement(sql3);
				preparedStatement3.setString(1, address.getAddress());
				preparedStatement3.setInt(2, user.getId());
				preparedStatement3.setBoolean(3, address.isDefault());
			}

		} catch (SQLException e) {
			e.printStackTrace();
			connection.rollback();
		} finally {
			connection.close();
			preparedStatement1.close();
			preparedStatement2.close();
			preparedStatement3.close();
		}

		return true;

	}

	public List<UserDTO> listUserGetAll() throws SQLException {
		List<UserDTO> allUsers = new ArrayList<UserDTO>();
		Connection connection = getConnection();
		PreparedStatement preparedStatement = null;

		try {

			String sql1 = "select* from user";
			preparedStatement = connection.prepareStatement(sql1);

			ResultSet rs1 = preparedStatement.executeQuery();
			while (rs1.next()) {
				UserDTO user = new UserDTO();
				user.setId(rs1.getInt("id"));
				user.setLoginName(rs1.getString("LoginName"));
				user.setEmail(rs1.getString("Email"));
				user.setNickName(rs1.getString("NickName"));
				user.setPassword(rs1.getString("Password"));
				String sql2 = "select* from user_address" + user.getId();
				preparedStatement = connection.prepareStatement(sql2);
				ResultSet rs2 = preparedStatement.executeQuery();
				List<UserAddressDTO> addresses = new ArrayList<UserAddressDTO>();
				while (rs2.next()) {
					UserAddressDTO address = new UserAddressDTO();
					address.setAddress(rs2.getString("address"));
					addresses.add(address);
				}
				user.setAddresses(addresses);
				allUsers.add(user);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			connection.close();
			preparedStatement.close();
		}

		return allUsers;

	}

}
