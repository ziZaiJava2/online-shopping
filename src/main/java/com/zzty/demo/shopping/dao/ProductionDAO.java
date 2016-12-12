package com.zzty.demo.shopping.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.zzty.demo.shopping.dto.ProductionDTO;


public class ProductionDAO extends DaoUtil {

	public ProductionDAO() throws ClassNotFoundException, SQLException {
		super();
	}

//	增加产品
	public int addProduction(ProductionDTO product) throws SQLException{
		int newProductonId = -1;
		String sql1 = "insert into production(name, description, original_price, price)"+"values(?, ?, ?, ?)";
		PreparedStatement ps1 = null;
		Connection con = getConnection();
		try {
			ps1 = con.prepareStatement(sql1,Statement.RETURN_GENERATED_KEYS);
			con.setAutoCommit(false);
			ps1.setString(1, product.getName());
			ps1.setString(2, product.getDescription());
			ps1.setDouble(3, product.getOriginal_price());
			ps1.setDouble(4, product.getPrice());
			ps1.execute();
			
			ResultSet generateKeyRs = ps1.getGeneratedKeys();

			while (generateKeyRs.next()) {

				newProductonId = generateKeyRs.getInt(1);

			}
			con.commit();
		} catch (SQLException e) {
			e.printStackTrace();
			con.rollback();
		} finally{
			ps1.close();
			con.close();
		}
		return newProductonId;
	}
	
//	删除产品
	public boolean deleteProductonById(int id) throws SQLException{
		boolean result = false;
		String sql1 = "delete from production where id="+id;
		Connection con = getConnection();
		PreparedStatement ps1 = null;
		
		try {
			con.setAutoCommit(false);
			ps1 = con.prepareStatement(sql1);
			ps1.execute();
			
			result = true;
			con.commit();
		} catch (SQLException e) {
			e.printStackTrace();
			con.rollback();
		} finally{
			ps1.close();
			con.close();
		}
		return result;
	}
//	数据库查询
	public ProductionDTO getProductionById(int id) throws SQLException{
		ProductionDTO product = new ProductionDTO();
		Connection con = getConnection();
		String sql1="select * from production where id="+id;
		PreparedStatement ps1 = null;
		try {
			con.setAutoCommit(false);
			ps1 = con.prepareStatement(sql1);
			ResultSet rs1 = ps1.executeQuery(sql1);
			while(rs1.next()){
				product.setId(rs1.getInt("id"));
				product.setName(rs1.getString("name"));
				product.setDescription(rs1.getString("description"));
				product.setOriginal_price(rs1.getDouble("original_price"));
				product.setPrice(rs1.getDouble("price"));
			}
			con.commit();
		} catch (SQLException e) {
			e.printStackTrace();
			con.rollback();
		} finally{
			ps1.close();
			con.close();
		}
		return product;
	}
//	改变
	public void updateProduction(ProductionDTO product) throws SQLException{
		String sql1="update user set name=?, description=?, original_price=?, price=? where id=?";
		PreparedStatement ps1 = null;
		Connection con = getConnection();
		try {
			con.setAutoCommit(false);
			ps1 = con.prepareStatement(sql1);
			ps1.setString(1, product.getName());
			ps1.setString(2, product.getDescription());
			ps1.setDouble(3, product.getOriginal_price());
			ps1.setDouble(4, product.getPrice());
			ps1.execute();
			con.commit();
		} catch (SQLException e) {
			e.printStackTrace();
			con.rollback();
		} finally{
			ps1.close();
			con.close();
		}
	}
}
