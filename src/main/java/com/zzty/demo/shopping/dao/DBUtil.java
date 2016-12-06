package com.zzty.demo.shopping.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBUtil {

	public static Connection useMysql() throws ClassNotFoundException, SQLException {
	       String url = "jdbc:mysql://localhost:3306/online_shopping?user=root&password=skyking";
           Class.forName("com.mysq.jdbc.Driver");
           Connection con = DriverManager.getConnection(url);
		return con;
           
           
           
	}

}
