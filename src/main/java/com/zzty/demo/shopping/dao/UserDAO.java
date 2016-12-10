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
 * 这个类封装了所有跟user表相关数据库的操作 一般来说包含基本的 对这张表的增删改查操作。 继承与DAO.class.
 * 可以调用父类的getConnection()方法来获取 jdbc connection
 * 
 */
public class UserDAO extends DAO {

	public UserDAO() throws ClassNotFoundException, SQLException {
		super();
	}

	/**
	 * 根据给定的UserDTO对象 来把对象中对应的数据插入到数据表 user中
	 */
	public int addUser(UserDTO user) throws SQLException, ClassNotFoundException {
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
			// 所以这里把connection的 autoCommit 设为false
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
			for (UserAddressDTO address : userAddresses) {
				if (address != null) {
					// user_address 表需要关联user id 所以这里要把插入user到数据库后最新生成的
					// userId设置给 UserAddressDTO中的 UserDTO
					user.setId(newUserId);
					address.setUser(user);

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
					userAddressDAO.addUserAddress(address);
				}
			}

			// 没有异常发生 调用connection.commit() 真正的往数据库提交 用户数据 和地址数据的插入
			connection.commit();
		} catch (SQLException e) {
			// 有异常发生， 会把之前缓存的数据库操作回滚， 相当于没有对数据库做任何操作
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

	public UserDTO getUserById(int userId) throws SQLException  {
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

}
