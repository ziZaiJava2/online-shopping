package com.zzty.demo.shopping.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import com.zzty.demo.shopping.dto.UserAddressDTO;
import com.zzty.demo.shopping.dto.UserDTO;

/** 
 * ������װ�����и�user��������ݿ�Ĳ���
 * һ����˵���������� �����ű����ɾ�Ĳ������ 
 * �̳���DAO.class. ���Ե��ø����getConnection()��������ȡ jdbc connection
 *  
 */
public class UserDAO extends DAO {

	public UserDAO() throws ClassNotFoundException, SQLException {
		super();
	}

	/**
	 *  ���ݸ�����UserDTO���� ���Ѷ����ж�Ӧ�����ݲ��뵽���ݱ� user��
	 */
	public int addUser(UserDTO user) throws SQLException, ClassNotFoundException {
		// ��ʼ��һ����������ʾ�²��뵽user���е����ݵ�id, ���id���ڲ���userAddress��Ϣ��ʱ���õ�
		// ��Ϊ��û�в��룬���Գ�ʼ����ֵΪ -1 ��ֹ������ֵ����
		int newUserId = -1;
		
		String sql = "insert into users(login_name, nick_name, email, password) " + "values(?, ?, ?, ?)";
		
		// ͨ�����ø����getConnection() ����ȡһ��jdbc connection
		Connection connection = getConnection();
		
		// ����PreparedStatement ���д��������֮ǰ�̵Ĳ�һ���� ����һ������ Statement.RETURN_GENERATED_KEYS
		// �����������ԭ���ǣ� �����ǲ����µ�user���ݵ�user���У� user��id�������ģ� ������������Ǻ�������ڲ����������Ժ�ȡ�����µ�id
		// ���Բο� http://lavasoft.blog.51cto.com/62575/238643/
		PreparedStatement preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
		try {
			// ��Ϊ���ǲ���user��Ϣ��user�� �У� ����Ҫ��UserDTO�ж�Ӧ�� ���е�UserAddressDTO ���뵽user_address���У� ��������Ҫ�� transaction�� ���������ơ�
			// һ������user���� ���� user_address����ʧ�ܣ� ���в��붼����Ч�� ֻ��user��user_address���ݶ�����������һ���ύ�����ݿ���
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

			// user��userAddress�� 1�Զ������� ����UserDTO�л���һ�� List<UserAddressDTO> ���������user���еĵ�ַ
			// ��������������һ���û���ʱ��ֹҪ���û�������Ϣ���뵽 user���У���Ҫ���û������е�ַ��Ϣ���뵽 user_address����
			List<UserAddressDTO> userAddresses = user.getAddresses();
			for (UserAddressDTO address : userAddresses) {
				if (address != null) {
					// user_address ����Ҫ����user id ��������Ҫ�Ѳ���user�����ݿ���������ɵ� userId���ø� UserAddressDTO�е� UserDTO
					user.setId(newUserId);
					address.setUser(user);

					UserAddressDAO userAddressDAO = new UserAddressDAO();
					// �ر�ǿ�� �������� ��Ϊ����Ҫ����������֤ �û���Ϣ �͵�ַ��Ϣ ͬʱ��ȷ���ύ�����ݿ��У�������ͬʱʧ�ܶ����ύ
					// ��Ϊjdbc��connection������������صĲ����� ��������ʵ����һ��UserAddressDAO����ʱ�� ���ʼ��һ���µ�Connection���� 
					// ��UserDAO ʵ������ Connection ����ͬһ������ 
					// �����������ǵ���setConnection() �������� UserDAOʵ������Connection���õ� UserAddressDAO�У� ��������DAO��������ͬһ�� Connection
					userAddressDAO.setConnection(connection);
					
					// ����UserAddressDAO ��addUserAddress()�����������еĵ�ַ��Ϣ ���뵽user_address����
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

}
