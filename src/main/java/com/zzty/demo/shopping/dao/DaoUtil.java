package com.zzty.demo.shopping.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
/**

 * 所有 DAO的父类， 封装了获取jdbc中关于connection的操作， 这样就不需要重复的去写这些代码，

 * 通过 getConnection() 就可以获取一个新的jdbc connection

 *

 */

public class DaoUtil {
	private String url = "jdbc:mysql://127.0.0.1:3306/online_shopping?user=root&password=120221136";

	private Connection connection;
	
	public DaoUtil() throws ClassNotFoundException, SQLException{

		Class.forName("com.mysql.jdbc.Driver");

		connection = DriverManager.getConnection(url);

	}
	
	public Connection getConnection(){
		return connection;
	}
	
	public void  setConnection(Connection connection){
		this.connection=connection;
	}



}
