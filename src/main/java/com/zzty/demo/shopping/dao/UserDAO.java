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
			
			while(rs1.next()){
				user.setId(rs1.getInt("id"));
				user.setLoginName(rs1.getString("LoginName"));
				user.setEmail(rs1.getString("Email"));
				user.setNickName(rs1.getString("NickName"));
				user.setPassword(rs1.getString("Password"));
			}
			
			ResultSet rs2 = preparedStatement2.executeQuery();
			
			List<UserAddressDTO> list=new ArrayList<UserAddressDTO>();
			while(rs2.next()){
				UserAddressDTO userAddress=new UserAddressDTO();
				
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
	
	public void updateUser(UserDTO user) throws SQLException{
		String sql1="update user set login_name=?,nick_name=?,emial=?,password=? where id=?";
		String sql2="delete from user_address where id=?";
		String sql3 = "insert into user_address(address,user_id,is_default) values(?,?,?)";
		PreparedStatement ps = null;
		PreparedStatement ps1 = null;
		PreparedStatement ps2 = null;
		Connection connect = getConnection();
		try {
			connect.setAutoCommit(false);
			//sql
			ps = connect.prepareStatement(sql1);
			ps.setString(1, user.getLoginName());
			ps.setString(2, user.getNickName());
			ps.setString(3, user.getEmail());
			ps.setString(4, user.getPassword());
			ps.setInt(5, user.getId());
			ps.execute();
			//sql2，删除
			ps1 = connect.prepareStatement(sql2);
			ps1.execute(sql2);
			//sql3
			List<UserAddressDTO> addressList = user.getAddresses();
			for(UserAddressDTO address:addressList){
				ps2 = connect.prepareStatement(sql3);
				ps2.setString(1, address.getAddress());
				ps2.setInt(2, address.getUesr().getId());
				ps2.setBoolean(3, address.isDefault());
				ps2.execute();
			}
			connect.commit();
		} catch (SQLException e) {
			e.printStackTrace();
			connect.rollback();
		} finally{
			ps.close();
			ps1.close();
			ps2.close();
			connect.close();
		}
		
	}
	
	public List<UserDTO> getAll() throws SQLException{
		List<UserDTO> userList = new ArrayList<UserDTO>();
		String sql1="select * from user";
		PreparedStatement ps1 = null;
		Connection connect = getConnection();
		try {
			connect.setAutoCommit(false);
			
			ps1 = connect.prepareStatement(sql1);
			ResultSet rs1 = ps1.executeQuery(sql1);
			while(rs1.next()){
				UserDTO user = new UserDTO();
				user.setId(rs1.getInt("id"));
				user.setLoginName(rs1.getString("login_name"));
				user.setNickName(rs1.getString("nick_name"));
				user.setEmail(rs1.getString("email"));
				user.setPassword(rs1.getString("password"));
				String sql2 = "select * from user_address where id=" + user.getId();
				ResultSet rs2 = ps1.executeQuery(sql2);
				List<UserAddressDTO> addressList = new ArrayList<UserAddressDTO>();
				while(rs2.next()){
					UserAddressDTO address = new UserAddressDTO();
					address.setAddress(rs2.getString("address"));
					addressList.add(address);
				}
				user.setAddresses(addressList);
				userList.add(user);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			connect.rollback();
		}finally{
			ps1.close();
			connect.close();
		}
		
		return userList;
	}
}
