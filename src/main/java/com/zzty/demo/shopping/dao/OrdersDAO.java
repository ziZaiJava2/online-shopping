package com.zzty.demo.shopping.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.zzty.demo.shopping.dto.OrdersDTO;

public class OrdersDAO extends DAO {

	public OrdersDAO() throws ClassNotFoundException, SQLException {
		super();
	}

	public int addOrders(OrdersDTO order) throws SQLException, ClassNotFoundException {
		int newId = -1;
		Connection connection = getConnection();
		PreparedStatement preparedStatement = null;
		String sql = "insert into orders (create_date,address,state,user_id)values(?,?,?,?)";
		preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
		try {
			preparedStatement.setDate(1, new java.sql.Date(order.getCreateDate().getTime()));
			preparedStatement.setString(2, order.getAddress());
			preparedStatement.setString(3, order.getState());
			preparedStatement.setInt(4, order.getUser().getId());
			preparedStatement.execute();
			ResultSet resKey = preparedStatement.getGeneratedKeys();
			while (resKey.next()) {
				newId = resKey.getInt(1);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			preparedStatement.close();
			connection.close();
		}
		return newId;
	}

	public void deleteOrdersById(int orderId) throws SQLException, ClassNotFoundException {
		Connection connection = getConnection();
		PreparedStatement preparedStatement = null;
		String sql = "delete from orders where id=" + orderId;
		preparedStatement = connection.prepareStatement(sql);
		try {
			preparedStatement.execute();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			preparedStatement.close();
			connection.close();
		}
	}

	public void updateOrders(OrdersDTO order) throws SQLException, ClassNotFoundException {
		Connection connection = getConnection();
		PreparedStatement preparedStatement = null;
		String sql = "update orders set create_date=?,address=?,state=?,user_id=? where id=?";
		preparedStatement = connection.prepareStatement(sql);
		try {
			preparedStatement.setDate(1, new java.sql.Date(order.getCreateDate().getTime()));
			preparedStatement.setString(2, order.getAddress());
			preparedStatement.setString(3, order.getState());
			preparedStatement.setInt(4, order.getUser().getId());
			preparedStatement.setInt(5, order.getId());
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			preparedStatement.close();
			connection.close();
		}

	}

	public OrdersDTO getOrdersById(int ordersId) throws ClassNotFoundException, SQLException {
		OrdersDTO orders = new OrdersDTO();
		UserDAO userDao = new UserDAO();
		Connection connection = getConnection();
		PreparedStatement preparedStatement = null;
		String sql = "select * from orders where id=" + ordersId;
		preparedStatement = connection.prepareStatement(sql);
		try {
			ResultSet res = preparedStatement.executeQuery();
			while (res.next()) {
				orders.setId(res.getInt("id"));
				orders.setCreateDate(res.getDate("create_date"));
				orders.setAddress(res.getString("address"));
				orders.setState(res.getString("state"));
				orders.setUser(userDao.getUserById(res.getInt("user_id")));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			preparedStatement.close();
			connection.close();
		}

		return orders;
	}

	public List<OrdersDTO> getAllOrders() throws ClassNotFoundException, SQLException {
		List<OrdersDTO> ordersList = new ArrayList<>();
		UserDAO userDao = new UserDAO();
		Connection connection = getConnection();
		PreparedStatement preparedStatement = null;
		String sql = "select * from orders";
		preparedStatement = connection.prepareStatement(sql);
		try {
			ResultSet res = preparedStatement.executeQuery();
			while (res.next()) {
				OrdersDTO orders = new OrdersDTO();
				orders.setId(res.getInt("id"));
				orders.setCreateDate(res.getDate("create_date"));
				orders.setAddress(res.getString("address"));
				orders.setState(res.getString("state"));
				orders.setUser(userDao.getUserById(res.getInt("user_id")));
				ordersList.add(orders);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			preparedStatement.close();
			connection.close();
		}
		return ordersList;
	}

}
