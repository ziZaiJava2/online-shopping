package com.action;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class Action {

	public static final String URL = "jdbc:mysql://127.0.0.1/online_shopping";
	public static final String USER = "root";
	public static final String PASSWORD = "3610";
	public static Connection conn = null;
	
	static{
		try {
			Class.forName("com.mysql.jdbc.Driver");
			Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public static Connection getConnection() {
		return conn;
	}
	public static void main(String[] args) throws ClassNotFoundException, SQLException {
		
		
		
	}

}
