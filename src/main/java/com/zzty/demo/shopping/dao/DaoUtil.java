package com.zzty.demo.shopping.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
/**

 * ���� DAO�ĸ��࣬ ��װ�˻�ȡjdbc�й���connection�Ĳ����� �����Ͳ���Ҫ�ظ���ȥд��Щ���룬

 * ͨ�� getConnection() �Ϳ��Ի�ȡһ���µ�jdbc connection

 *

 */

public class DaoUtil {
	private String url = "jdbc:mysql://127.0.0.1:3306/online_shopping?user=root&password=root";

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
