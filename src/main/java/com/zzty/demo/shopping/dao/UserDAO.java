package com.zzty.demo.shopping.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.action.Action;
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

	public void addUser() {

	}

	public void delUse() {

	}

	public void updateUse() {

	}

	public UserDTO selUse() {
		UserDTO user = new UserDTO();
		Connection conn = Action.getConnection();

		return user;
	}

	public List<UserDTO> selectUse() throws SQLException {
		List<UserDTO> UsersList = new ArrayList<UserDTO>();
		Connection conn = Action.getConnection();
		Statement stat = conn.createStatement();
		ResultSet rs = stat.executeQuery("select * from online_shopping");
		UserDTO user = new UserDTO();
		while (rs.next()) {
			user.setLoginName(rs.getString("login_name"));
			user.setEmail(rs.getString("email"));
			user.setNickName(rs.getString("nick_name"));
			user.setPassword(rs.getString("password"));

			UsersList.add(user);

		}
		return UsersList;
	}

	public boolean deleteUserById(int id) throws SQLException {
		boolean result = false;
		Connection conn = getConnection();
		PreparedStatement pst1 = null;
		PreparedStatement pst2 = null;

		try {
			conn.setAutoCommit(false);
			String sql1 = "delete from user_address where user_id=" + id;
			String sql2 = "delete from user where user_id=" + id;

			pst1 = conn.prepareStatement(sql1);
			pst2 = conn.prepareStatement(sql2);

			pst1.execute();
			pst2.execute();

			conn.commit();

			result = true;
		} catch (SQLException e) {
			e.printStackTrace();
			conn.rollback();
		} finally {
			pst1.close();
			pst2.close();
			conn.close();
		}

		return result;
	}

	public UserDTO getUserById(int userId) throws SQLException {
		UserDTO user = new UserDTO();
		Connection conn = getConnection();

		try {
			String sql1 = "select * from user where id=" + userId;
			String sql2 = "select * from user_adress where id=" + userId;
			PreparedStatement pst1 = conn.prepareStatement(sql1);
			PreparedStatement pst2 = conn.prepareStatement(sql2);

			ResultSet rs = pst1.executeQuery();

			while (rs.next()) {
				user.setId(rs.getInt("id"));
				user.setLoginName(rs.getString("login_name"));
				user.setEmail(rs.getString("email"));
			}

			ResultSet rs1 = pst2.executeQuery(sql2);
			List<UserAddressDTO> list = new ArrayList<UserAddressDTO>();
			while (rs1.next()) {
				UserAddressDTO userAddress = new UserAddressDTO();
				userAddress.setAddress(rs1.getString("address"));
				list.add(userAddress);
			}
			user.setAddresses(list);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			conn.close();
		}

		return user;
	}

	public void updateUser(UserDTO user) throws SQLException {

		String sql1 = "update user set login_name=?, nick_name=?, email=?, password=? where id=?";
		String sql2 = "delete user_address where user_id=" + user.getId();
		String sql3 = "insert into user_address(address, user_id, is_default) values(?,?,?)";

		Connection conn = getConnection();
		PreparedStatement ps1 = null;
		PreparedStatement ps2 = null;
		PreparedStatement ps3 = null;

		try {
			conn.setAutoCommit(false);

			ps1 = conn.prepareStatement(sql1);
			ps1.setString(1, user.getLoginName());
			ps1.setString(2, user.getNickName());
			ps1.setString(3, user.getEmail());
			ps1.setString(4, user.getPassword());
			ps1.setInt(5, user.getId());
			ps1.execute();

			ps2 = conn.prepareStatement(sql2);
			List<UserAddressDTO> addressList = user.getAddresses();
			for (UserAddressDTO addresses : addressList) {
				ps3 = conn.prepareStatement(sql3);
				ps3.setString(1, addresses.getAddress());
				ps3.setInt(2, user.getId());
				ps3.setBoolean(3, addresses.isDefault());
				ps3.execute();
			}
			conn.commit();
		} catch (SQLException e) {
			conn.rollback();
			e.printStackTrace();
		} finally {
			conn.close();
			ps1.close();
			ps2.close();
			ps3.close();
		}
		// update user base user info
		// update user addresses 1.delete origin 2.insert new address
	}

	public List<UserDTO> getUserList() throws SQLException {
		List<UserDTO> userList = new ArrayList<UserDTO>();
		Connection conn = getConnection();

		String sql1 = "select * from user";
		PreparedStatement pst1 = null;
		PreparedStatement pst2 = null;
		try {
			pst1 = conn.prepareStatement(sql1);
			ResultSet rs = pst1.executeQuery();
			while (rs.next()) {
				UserDTO user = new UserDTO();
				user.setId(rs.getInt("id"));
				user.setLoginName(rs.getString("login_name"));
				user.setNickName(rs.getString("nick_name"));
				user.setEmail(rs.getString("email"));
				user.setPassword(rs.getString("password"));

				String sql2 = "select * from user_address where id=" + user.getId();
				pst2 = conn.prepareStatement(sql2);
				ResultSet rs2 = pst2.executeQuery(sql2);
				List<UserAddressDTO> addressList = new ArrayList<UserAddressDTO>();
				while (rs2.next()) {
					UserAddressDTO address = new UserAddressDTO();
					address.setAddress(rs.getString("address"));
					addressList.add(address);
				}
				user.setAddresses(addressList);
				userList.add(user);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			conn.close();
			pst1.close();
			pst2.close();
		}
		return userList;
	}

}
