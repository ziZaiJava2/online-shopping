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
		// 初始化一个变量来表示新插入到user表中的数据的id, 这个id会在插入userAddress信息的时候用到
		// 因为还没有插入，所以初始化的值为 -1 防止跟正常值混淆
		int newUserId = -1;

		String sql = "insert into users(login_name, nick_name, email, password) " + "values(?, ?, ?, ?)";
		// 通过调用父类的getConnection() 来获取一个jdbc connection
		Connection connection = getConnection();

		// 创建PreparedStatement 这个写法跟我们之前教的不一样， 多了一个参数
		// Statement.RETURN_GENERATED_KEYS

		// 加这个参数的原因是， 当我们插入新的user数据到user表中， user的id是自增的，
		// 用这个参数我们后面可以在插入操作完成以后取到最新的id

		// 可以参考 http://lavasoft.blog.51cto.com/62575/238643/

		PreparedStatement preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

		try {

			// 因为我们插入user信息到user表 中， 还需要把UserDTO中对应的 所有的UserAddressDTO
			// 插入到user_address表中， 所以我们要用 transaction。 事务来控制。

			// 一旦插入user数据 或者 user_address数据失败， 所有插入都不生效。
			// 只有user和user_address数据都正常插入后才一起提交到数据库中

			// 所以这里把connection的 autoCommit自动提交设为false

			connection.setAutoCommit(false);

			preparedStatement.setString(1, user.getLoginName());

			preparedStatement.setString(2, user.getNickName());

			preparedStatement.setString(3, user.getEmail());

			preparedStatement.setString(4, user.getPassword());

			preparedStatement.execute();

			// 获取插入数据后的自增主键 可以参考 http://lavasoft.blog.51cto.com/62575/238643/

			ResultSet generateKeyRs = preparedStatement.getGeneratedKeys();

			while (generateKeyRs.next()) {

				newUserId = generateKeyRs.getInt(1);

			}

			// user和userAddress是 1对多的情况， 所以UserDTO中会有一个 List<UserAddressDTO>
			// 来保存这个user所有的地址

			// 所以我们新增加一个用户的时候不止要把用户基本信息插入到 user表中，还要把用户的所有地址信息插入到 user_address表中

			List<UserAddressDTO> userAddresses = user.getAddresses();

			for (UserAddressDTO userAddress : userAddresses) {

				if (userAddress != null) {

					// user_address 表需要关联user id 所以这里要把插入user到数据库后最新生成的
					// userId设置给 UserAddressDTO中的 UserDTO

					user.setId(newUserId);

					userAddress.setUesr(user);

					UserAddressDAO userAddressDAO = new UserAddressDAO();

					// 特别强调 ！！！！ 因为我们要用事务来保证 用户信息 和地址信息 同时正确的提交到数据库中，或者是同时失败都不提交
					// 因为jdbc中connection来负责事务相关的操作。 当我们新实例化一个UserAddressDAO对象时，
					// 会初始化一个新的Connection对象
					// 和UserDAO 实例化的 Connection 不是同一个对象，
					// 所以这里我们调用setConnection() 方法来把 UserDAO实例化的Connection设置到
					// UserAddressDAO中， 这样两个DAO对象都是用同一个 Connection

					userAddressDAO.setConnection(connection);
					// 调用UserAddressDAO 的addUserAddress()方法来把所有的地址信息
					// 插入到user_address表中

					userAddressDAO.addUserAddress(userAddress);

				}

			}

			// 没有异常发生 调用connection.commit() 真正的往数据库提交 用户数据 和地址数据的插入
			// 之前只是将数据提交到数据库的缓存中并没有真正提交

			connection.commit();

		} catch (SQLException e) {

			// 有异常发生， 会把之前缓存的数据库操作回滚，相当于清理下之前的缓存， 相当于没有对数据库做任何操作

			connection.rollback();

		} finally {

			preparedStatement.close();

			connection.close();

		}

		return newUserId;

	}

	// 要删除一条数据的话和他关联的数据也要删除掉
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

	// 数据库的查询
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
		// 更新用户的基本信息

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
			// 更新用户的地址信息。1.删掉原先的信息 2.再添加需要的信息
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
