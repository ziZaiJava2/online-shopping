package com.zzty.demo.shopping.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.zzty.demo.shopping.dto.ProductionDTO;

public class ProductionDAO extends DAO {

	public ProductionDAO() throws ClassNotFoundException, SQLException {
		super();
	}

	public int addProduction(ProductionDTO production) throws SQLException, ClassNotFoundException {
		int newId = -1;
		Connection connection = getConnection();
		PreparedStatement preparedStatement = null;
		String sql = "insert into production (name,description,original_price,price)values(?,?,?,?)";
		preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
		try {
			preparedStatement.setString(1, production.getName());
			preparedStatement.setString(2, production.getDescription());
			preparedStatement.setInt(3, production.getOriginal_price());
			preparedStatement.setInt(4, production.getPrice());
			preparedStatement.execute();
			ResultSet generateKeyRs = preparedStatement.getGeneratedKeys();
			while (generateKeyRs.next()) {
				newId = generateKeyRs.getInt(1);
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

	public void deleteProduction(int id) throws SQLException, ClassNotFoundException {
		Connection connection = getConnection();
		PreparedStatement preparedStatement = null;
		String sql = "delete from production where id =" + id;
		try {
			preparedStatement = connection.prepareStatement(sql);
			preparedStatement.execute();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			preparedStatement.close();
			connection.close();
		}
	}

	public ProductionDTO getProductionById(int id) throws SQLException, ClassNotFoundException {
		ProductionDTO production = new ProductionDTO();
		Connection connection = getConnection();
		PreparedStatement preparedStatement = null;
		String sql = "select * from production where id=" + id;
		try {
			preparedStatement = connection.prepareStatement(sql);
			ResultSet res = preparedStatement.executeQuery();
			while (res.next()) {
				production.setId(res.getInt("id"));
				production.setName(res.getString("name"));
				production.setDescription(res.getString("description"));
				production.setOriginal_price(res.getInt("original_price"));
				production.setPrice(res.getInt("price"));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			preparedStatement.close();
			connection.close();
		}
		return production;
	}

	public void updateProduction(ProductionDTO production) throws SQLException, ClassNotFoundException {
		Connection connection = getConnection();
		PreparedStatement preparedStatement = null;
		String sql = "update production set name=?,description=?,original_price=?,price=? where id=?";
		preparedStatement = connection.prepareStatement(sql);
		try {
			preparedStatement.setString(1, production.getName());
			preparedStatement.setString(2, production.getDescription());
			preparedStatement.setInt(3, production.getOriginal_price());
			preparedStatement.setInt(4, production.getPrice());
			preparedStatement.setInt(5, production.getId());
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			preparedStatement.close();
			connection.close();
		}
	}

	public List<ProductionDTO> getAllProduction() throws SQLException, ClassNotFoundException {
		List<ProductionDTO> productionList = new ArrayList<>();
		Connection connection = getConnection();
		PreparedStatement preparedStatement = null;
		String sql = "select * from production";
		preparedStatement = connection.prepareStatement(sql);
		try {
			ResultSet res = preparedStatement.executeQuery();
			while (res.next()) {
				ProductionDTO production = new ProductionDTO();
				production.setId(res.getInt("id"));
				production.setName(res.getString("name"));
				production.setDescription(res.getString("description"));
				production.setOriginal_price(res.getInt("original_price"));
				production.setPrice(res.getInt("price"));
				productionList.add(production);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			preparedStatement.close();
			connection.close();
		}
		return productionList;
	}

}
