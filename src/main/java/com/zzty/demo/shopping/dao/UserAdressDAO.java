package com.zzty.demo.shopping.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.zzty.demo.shopping.dto.UserAdressDTO;
import com.zzty.demo.shopping.dto.UserDTO;

public class UserAdressDAO {
	private Connection con = null;
    public boolean addUserAdress(UserAdressDTO userAdress) throws ClassNotFoundException, SQLException{
    	con = DBUtil.useMysql();
    	String sql = "insert into user(address, user_id)values(?, ?)";
    	java.sql.PreparedStatement pst = con.prepareStatement(sql);
        pst.setString(1,userAdress.getAdress()); 
        pst.setInt(2, userAdress.getUserId().getId());
       
        return pst.execute();
    }
    public boolean updateUserAdress(UserAdressDTO userAdress) throws ClassNotFoundException, SQLException{
    	con = DBUtil.useMysql();
    	Statement st = con.createStatement();
    	if(userAdress.getAdress() != null && !"".equals(userAdress.getAdress())){
            String sql = "update user_address set address = " + userAdress.getAdress() + "where id = " + userAdress.getId();
    	   return st.execute(sql);
    	}else{
            String sql = "update user_address set user_id = " + userAdress.getUserId().getId() + "where id = " + userAdress.getId();
            return st.execute(sql);
    	}
     }
    
    public boolean deleteUseUserAdress(UserAdressDTO userAdress) throws ClassNotFoundException, SQLException{
    	con = DBUtil.useMysql();
    	Statement st = con.createStatement();
    	String sql = "delete from user_address where id = " + userAdress.getId();
    	return st.execute(sql);
    }
    
    public String getAllUserAdress() throws ClassNotFoundException, SQLException{
    	con = DBUtil.useMysql();
    	Statement st = con.createStatement();
    	String sql = "select * from user_address";
    	ResultSet rs = st.executeQuery(sql);
    	int userId;
    	String address;
    	String message = "";
    	while(rs.next()){
    		address = rs.getString(2);
    		userId = rs.getInt(3);
    		message +="address: " + address + " " + "userId: " + userId + "\n";
    	}
    	return message;
    }
    
    public UserAdressDTO getUserAdress(int addressId) throws ClassNotFoundException, SQLException{
    	UserAdressDTO ua = null;
    	con = DBUtil.useMysql();
    	Statement st = con.createStatement();
    	String sql = "select * from user_address where user.id = " +addressId;
    	ResultSet rs = st.executeQuery(sql);
    	ua.setAdress(rs.getString(2));
    	ua.getUserId().setId(rs.getInt(3));
    	
    	return ua;
    }
}
