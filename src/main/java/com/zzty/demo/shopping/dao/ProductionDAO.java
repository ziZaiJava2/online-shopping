package com.zzty.demo.shopping.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.zzty.demo.shopping.dto.ProductionDTO;

public class ProductionDAO extends DaoUtil {

	public ProductionDAO() throws ClassNotFoundException, SQLException {
		super();

	}

	public int addProduetion(ProductionDTO production) throws SQLException {
		int newProductionId = -1;
		String sql = "insert into production(name, description, original_price, price) " + "values(?, ?, ?, ?)";
		Connection connection = getConnection();
		PreparedStatement preparedStatement = connection.prepareStatement(sql);
		try {

			preparedStatement.setString(1, production.getName());
			preparedStatement.setString(1, production.getDescription());
			preparedStatement.setString(1, production.getOriginal_price());
			preparedStatement.setString(1, production.getPrice());
			preparedStatement.execute();
			ResultSet generateKeyRs = preparedStatement.getGeneratedKeys();

			while (generateKeyRs.next()) {

				newProductionId = generateKeyRs.getInt(1);

			}

		} catch (SQLException e) {

			e.printStackTrace();
		} finally {
			connection.close();
			preparedStatement.close();
		}

		return newProductionId;

	}

	public boolean deleteProductionById(int id) throws SQLException {
		boolean result = false;
		Connection connection = getConnection();
		String sql = "delete from production where id= " + id;
		PreparedStatement preparedStatement = null;
		try {
			preparedStatement = connection.prepareStatement(sql);
			preparedStatement.execute();
		} catch (SQLException e) {

			e.printStackTrace();
		} finally {
			connection.close();
			preparedStatement.close();
		}

		result = true;
		return result;

	}

	public ProductionDTO getProductionById(int productionId) throws SQLException {
		ProductionDTO production = new ProductionDTO();
		Connection connection = getConnection();
		String sql = "select *from production where id= " + productionId;
		PreparedStatement preparedStatement = null;
		try {
			preparedStatement = connection.prepareStatement(sql);

			ResultSet rs = preparedStatement.executeQuery();

			while (rs.next()) {
				production.setId(rs.getInt("id"));
				
				production.setDescription(rs.getString("description"));

				production.setName(rs.getString("name"));

				production.setOriginal_price(rs.getString("original_price"));

				production.setPrice(rs.getString("price"));

			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			connection.close();
			preparedStatement.close();
		}

		return production;

	}

	public boolean updateProduction(ProductionDTO production) throws SQLException {
		Connection connection = getConnection();
		String sql = "update production set name=?,description=?,original_price=?,price=?" + production;
		PreparedStatement preparedStatement = null;
		try {
			preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setString(1, production.getName());
			preparedStatement.setString(2, production.getDescription());
			preparedStatement.setString(3, production.getOriginal_price());
			preparedStatement.setString(4, production.getPrice());
			preparedStatement.setInt(5, production.getId());

		} catch (SQLException e) {

			e.printStackTrace();
		} finally {
			connection.close();
			preparedStatement.close();
		}
		return true;

	}

	public List<ProductionDTO> listProductionGetAll() throws SQLException {
		List<ProductionDTO> allProduction = new ArrayList<ProductionDTO>();
		Connection connection = getConnection();
		String sql = "select * from production";
		PreparedStatement preparedStatement = null;
		try {

			preparedStatement = connection.prepareStatement(sql);
			ResultSet rs = preparedStatement.executeQuery();
			while (rs.next()) {
				ProductionDTO production = new ProductionDTO();
				production.setName(rs.getString("name"));
				production.setDescription(rs.getString("description"));
				production.setOriginal_price(rs.getString("original_price"));
				production.setPrice(rs.getString("price"));
				
				allProduction.add(production);
			}
			
		} catch (SQLException e) {
            e.printStackTrace();
		}finally{
			connection.close();
			preparedStatement.close();
			
		}

		return allProduction;

	}

}
