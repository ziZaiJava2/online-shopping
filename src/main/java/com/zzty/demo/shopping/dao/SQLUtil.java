package com.zzty.demo.shopping.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

public class SQLUtil {
	private static Connection con = null;
	private static PreparedStatement preState;

	public static PreparedStatement prepare(String url, String sql) throws Exception {
		Class.forName("com.mysql.jdbc.Driver");
		con = DriverManager.getConnection(url);
		preState = con.prepareStatement(sql);
		return preState;
	}

	public static void close() throws Exception {
		preState.close();
		con.close();
	}
}
