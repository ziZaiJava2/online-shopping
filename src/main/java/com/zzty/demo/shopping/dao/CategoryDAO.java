package com.zzty.demo.shopping.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.zzty.demo.shopping.dto.CategoryDTO;

public class CategoryDAO extends DaoUtil {

	public CategoryDAO() throws ClassNotFoundException, SQLException {
		super();

	}

	public int addCategory(CategoryDTO category) throws SQLException {
		int newCategoryId = -1;
		String sql = "insert into category(name) " + "values(?)";
		Connection connection = getConnection();
		PreparedStatement preparedStatement = null;

		try {
			preparedStatement = connection.prepareStatement(sql);

			preparedStatement.setString(1, category.getName());
			preparedStatement.execute();
			ResultSet generateKeyRs = preparedStatement.getGeneratedKeys();

			while (generateKeyRs.next()) {

				newCategoryId = generateKeyRs.getInt(1);

			}

		} catch (SQLException e) {

			e.printStackTrace();
		} finally {
			connection.close();
			preparedStatement.close();

		}

		return newCategoryId;
	}

	public boolean deleteCategoryById(int id) throws SQLException {
		boolean result = false;

		Connection connection = getConnection();
		String sql = "delete from category where id= " + id;
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
	
	

}
