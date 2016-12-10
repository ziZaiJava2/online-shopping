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

/**
 * ������װ�����и�user��������ݿ�Ĳ��� һ����˵���������� �����ű����ɾ�Ĳ������ �̳���DAO.class.
 * ���Ե��ø����getConnection()��������ȡ jdbc connection
 * 
 */
public class UserDAO extends DAO {

	public UserDAO() throws ClassNotFoundException, SQLException {
		super();
	}

	/**
	 * ���ݸ�����UserDTO���� ���Ѷ����ж�Ӧ�����ݲ��뵽���ݱ� user��
	 */
	public int addUser(UserDTO user) throws SQLException, ClassNotFoundException {
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
			// ���������connection�� autoCommit ��Ϊfalse
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
			for (UserAddressDTO address : userAddresses) {
				if (address != null) {
					// user_address ����Ҫ����user id ��������Ҫ�Ѳ���user�����ݿ���������ɵ�
					// userId���ø� UserAddressDTO�е� UserDTO
					user.setId(newUserId);
					address.setUser(user);

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
					userAddressDAO.addUserAddress(address);
				}
			}

			// û���쳣���� ����connection.commit() �����������ݿ��ύ �û����� �͵�ַ���ݵĲ���
			connection.commit();
		} catch (SQLException e) {
			// ���쳣������ ���֮ǰ��������ݿ�����ع��� �൱��û�ж����ݿ����κβ���
			connection.rollback();
		} finally {
			preparedStatement.close();
			connection.close();
		}
		return newUserId;
	}

	public boolean deleteUserById(int id) throws SQLException {
		boolean result = false;
		Connection connection = getConnection();
		PreparedStatement preparedStatement1 = null;
		PreparedStatement preparedStatement2 = null;
		try {
			connection.setAutoCommit(false);

			String sql1 = "delete from user_address where user_id = " + id;
			String sql2 = "delete from user where id = " + id;

			preparedStatement1 = connection.prepareStatement(sql1);
			preparedStatement2 = connection.prepareStatement(sql2);

			preparedStatement1.execute();
			preparedStatement2.execute();

			connection.commit();

			result = true;

		} catch (SQLException e) {
			connection.rollback();
		} finally {
			preparedStatement1.close();
			preparedStatement1.close();
			connection.close();
		}
		return result;
	}

	public UserDTO getUserById(int userId) throws SQLException {
		UserDTO user = new UserDTO();
		Connection connection = getConnection();
		PreparedStatement p1 = null;
		PreparedStatement p2 = null;
		try {
			connection.setAutoCommit(false);

			String sql1 = "select * from user where id = " + userId;
			String sql2 = "select * from user_address where user_id = " + userId;
			p1 = connection.prepareStatement(sql1);
			p2 = connection.prepareStatement(sql2);

			ResultSet rs1 = p1.executeQuery();

			while (rs1.next()) {
				user.setId(rs1.getInt("id"));
				user.setLoginName(rs1.getString("login_name"));
				user.setNickName(rs1.getString("nick_name"));
				user.setEmail(rs1.getString("email"));
				user.setPassword(rs1.getString("password"));
			}

			ResultSet rs2 = p2.executeQuery();
			List<UserAddressDTO> address = new ArrayList<UserAddressDTO>();
			while (rs2.next()) {
				UserAddressDTO userAddress = new UserAddressDTO();
				userAddress.setAddress(rs2.getString("address"));
				address.add(userAddress);
			}
			user.setAddresses(address);

			connection.commit();
		} catch (SQLException e) {
			connection.rollback();
		} finally {
			p1.close();
			p2.close();
			connection.close();
		}
		return user;
	}

	public void updateUser(UserDTO user) throws SQLException {

		String sql1 = "update user set login_name = ?, nick_name = ?,email = ?, password = ? where id = ?";
		String sql2 = "delete from user_address where user_id = " + user.getId();
		String sql3 = "insert into userAddress(address,user_id,is_default) values(?, ? ,?)";
		Connection connect = getConnection();
		PreparedStatement ps1 = null;
		PreparedStatement ps2 = null;
		PreparedStatement ps3 = null;
		try {
			connect.setAutoCommit(false);

			ps1 = connect.prepareStatement(sql1);
			ps1.setString(1, user.getNickName());
			ps1.setString(2, user.getNickName());
			ps1.setString(3, user.getEmail());
			ps1.setString(4, user.getPassword());
			ps1.setInt(5, user.getId());
			ps1.execute();

			ps2 = connect.prepareStatement(sql1);
			ps2.execute(sql2);

			List<UserAddressDTO> addressList = user.getAddresses();
			for (UserAddressDTO address : addressList) {
				ps3 = connect.prepareStatement(sql3);
				ps3.setString(1, address.getAddress());
				ps3.setInt(2, address.getId());
				ps3.setBoolean(3, address.isDefault());

				ps3.execute();
			}

			connect.commit();
		} catch (SQLException e) {
			connect.rollback();
		} finally {
			ps1.close();
			ps2.close();
			ps3.close();
			connect.close();
		}

	}

	public List<UserDTO> listUser() throws SQLException {
		List<UserDTO> allUsers = new ArrayList<UserDTO>();
		Connection connection = getConnection();
		String sql = "select * from user";
		PreparedStatement ps = null;
		try {
			ps = connection.prepareStatement(sql);

			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				UserDTO user = new UserDTO();
				user.setId(rs.getInt("id"));
				user.setLoginName(rs.getString("login_name"));
				user.setNickName(rs.getString("nick_name"));
				user.setEmail(rs.getString("emil"));

				String sql2 = "select * from user_address where id=?" + user.getId();
				ResultSet rs2 = ps.executeQuery(sql2);
				List<UserAddressDTO> addressList = new ArrayList<UserAddressDTO>();
				while (rs2.next()) {
					UserAddressDTO userAddress = new UserAddressDTO();
					userAddress.setAddress(rs2.getString("address"));
					addressList.add(userAddress);
				}
				user.setAddresses(addressList);
				allUsers.add(user);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			ps.close();
			connection.close();
		}

		return allUsers;
	}

}
