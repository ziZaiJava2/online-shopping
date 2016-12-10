package com.zzty.demo.shopping.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.zzty.demo.shopping.dto.CategoryDTO;

public class CategoryDAO extends DAO {

	public CategoryDAO() throws ClassNotFoundException, SQLException {
		super();
		// TODO Auto-generated constructor stub
	}

	public int addCategory(CategoryDTO category) throws SQLException {
		int categoryId = -1;
		Connection c = getConnection();
		PreparedStatement ps = null;
		String sql = "insert into category (name) values (?)";

		try {
			c.setAutoCommit(false);
			ps = c.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			ps.setString(1, category.getName());
			ps.execute();

			ResultSet rs = ps.getGeneratedKeys();

			while (rs.next()) {
				categoryId = rs.getInt(1);
			}

			c.commit();
		} catch (SQLException e) {
			c.rollback();
		} finally {
			ps.close();
			c.close();
		}

		return categoryId;
	}

	public boolean deleteCategoryById(int categoryId){
		boolean result = false;
		Connection c = getConnection();
		PreparedStatement ps = null;
		String sql = "delete from category where id = "+ categoryId;
		try {
			ps = c.prepareStatement(sql);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		return result;
	}
}


































