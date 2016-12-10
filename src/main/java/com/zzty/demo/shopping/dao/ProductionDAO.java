package com.zzty.demo.shopping.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.zzty.demo.shopping.dto.CategoryDTO;
import com.zzty.demo.shopping.dto.ProductionDTO;

public class ProductionDAO extends DAO {

	public ProductionDAO() throws ClassNotFoundException, SQLException {
		super();
	}

	public int addProduction(ProductionDTO production) throws SQLException {
		int newProduction = -1;

		String sql = "insert into production(name, description, originalPrice, price)" + "values(?,?,?,?)";

		Connection connection = getConnection();

		PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

		try {
			connection.setAutoCommit(false);
			ps.setString(1, production.getName());
			ps.setString(2, production.getDescription());
			ps.setDouble(3, production.getOriginalPrice());
			ps.setDouble(4, production.getPrice());

			ps.execute();

			ResultSet rs = ps.getGeneratedKeys();

			while (rs.next()) {
				newProduction = rs.getInt(1);
			}

			connection.commit();
		} catch (SQLException e) {
			connection.rollback();
		} finally {
			ps.close();
			connection.close();

		}
		return newProduction;

	}

	public boolean deleteProductionById(int id) throws SQLException {
		boolean result = false;
		Connection c = getConnection();
		PreparedStatement ps1 = null;
		String sql1 = "delete from production where id = " + id;
		try {
			c.setAutoCommit(false);
			ps1 = c.prepareStatement(sql1);
			ps1.execute();

			result = true;
			c.commit();

		} catch (SQLException e) {
			c.rollback();
		} finally {
			ps1.close();
			c.close();
		}

		return result;
	}

	public ProductionDTO getProductionById(int productionId) throws SQLException {
		ProductionDTO production = new ProductionDTO();

		Connection c = getConnection();
		String sql = "select * from production where id = " + productionId;
		PreparedStatement ps = null;
		try {
			c.setAutoCommit(false);
			ps = c.prepareStatement(sql);
			ResultSet rs = ps.executeQuery();

			while (rs.next()) {
				production.setId(rs.getInt("id"));
				production.setName(rs.getString("name"));
				production.setDescription(rs.getString("description"));
				production.setOriginalPrice(rs.getDouble("original_price"));
				production.setPrice(rs.getDouble("price"));

			}
			c.commit();
		} catch (SQLException e) {
			c.rollback();
		} finally {
			ps.close();
			c.close();
		}
		return production;

	}

	public void UpdateProduction(ProductionDTO production) throws SQLException {
		Connection c = getConnection();
		PreparedStatement ps = null;
		String sql = "updete production set name = ? ,description = ?, original_price = ?, price = ?";
		try {
			c.setAutoCommit(false);
			ps = c.prepareStatement(sql);

			ps.setString(1, production.getName());
			ps.setString(2, production.getDescription());
			ps.setDouble(3, production.getOriginalPrice());
			ps.setDouble(4, production.getPrice());

			ps.execute();

			c.commit();

		} catch (SQLException e) {
			c.rollback();
		} finally {
			ps.close();
			c.close();
		}

	}

	public List<ProductionDTO> listProduction() throws SQLException {
		List<ProductionDTO> allProductions = new ArrayList<ProductionDTO>();
		Connection c = getConnection();
		PreparedStatement ps = null;
		PreparedStatement ps2 = null;
		String sql = "select* from production";
		try {
			ps = c.prepareStatement(sql);

			ResultSet rs = ps.executeQuery();

			while (rs.next()) {
				ProductionDTO p = new ProductionDTO();
				p.setId(rs.getInt("id"));
				p.setName(rs.getString("name"));
				p.setDescription(rs.getString("description"));
				p.setOriginalPrice(rs.getDouble("original_price"));
				p.setPrice(rs.getDouble("price"));
				
				String sql2 = "select name from category where id = "+p.getId();
				ResultSet rs2 =ps2.executeQuery(sql2);
				List<CategoryDTO> cates = new ArrayList<CategoryDTO>();
				while(rs2.next()){
					CategoryDTO cate = new CategoryDTO();
					cate.setId(rs2.getInt("id"));
					cate.setName(rs2.getString("name"));
					cates.add(cate);
				}
				p.setCategorys(cates);
				allProductions.add(p);
				
				
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			ps.close();
			c.close();
		}

		return allProductions;
	}

}
