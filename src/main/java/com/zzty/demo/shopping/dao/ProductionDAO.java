package com.zzty.demo.shopping.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.zzty.demo.shopping.dto.ProductionDTO;
import com.zzty.demo.shopping.dto.UserAdressDTO;
import com.zzty.demo.shopping.dto.UserDTO;

public class ProductionDAO {
	private Connection con = null;
    public boolean Production(ProductionDTO production) throws ClassNotFoundException, SQLException{
    	con = DBUtil.useMysql();
    	String sql = "insert into user(name, description, original_price, price)values(?, ?, ?, ?)";
    	java.sql.PreparedStatement pst = con.prepareStatement(sql);
        pst.setString(1, production.getName()); 
        pst.setString(2, production.getDecription();
        pst.setInt(3, production.getOriginalPrice());
        pst.setInt(4, production.getPrice());
        return pst.execute();
    }
    public boolean updateProduction(ProductionDTO production) throws ClassNotFoundException, SQLException{
    	con = DBUtil.useMysql();
    	Statement st = con.createStatement();
    	if("".equals(production.getName()) && production.getName() != null ){
            String sql = "update prodcution set name = " + production.getName() + "where id = " + production.getId();
    	   return st.execute(sql);
    	}else if("".equals(production.getDecription()) && production.getDecription() != null){
            String sql = "update prodcution set description = " + production.getDecription() + "where id = " + production.getId();
            return st.execute(sql);
    	}else if(){
            String sql = 
            return st.execute(sql);
    	}else{
            String sql = 
            return st.execute(sql);
    	}
     }
    
    public boolean deleteProduction(ProductionDTO production) throws ClassNotFoundException, SQLException{
    	con = DBUtil.useMysql();
    	Statement st = con.createStatement();
    	String sql = "delete from user where id = " + user.getId();
    	return st.execute(sql);
    }
    
    public String getAllProduction() throws ClassNotFoundException, SQLException{
    	con = DBUtil.useMysql();
    	Statement st = con.createStatement();
    	String sql = "select * from user";
    	ResultSet rs = st.executeQuery(sql);
    	String loginName;
    	int adressId;
    	String nickName;
    	String email;
    	String password;
    	String userMessage = "";
    	while(rs.next()){
    		loginName = rs.getString(2);
    		nickName = rs.getString(3);
    		adressId = rs.getInt(4);
    		email = rs.getString(5);
    		password = rs.getString(6);
    		userMessage +="loginName: " + loginName + " " + "nickName: " + nickName + " " + "adressId: " + adressId + " " + "email:" + email + "password: " + password + " " + "\n";
    	}
    	return userMessage;
    }
    
    public UserDTO getProduction(int userId) throws ClassNotFoundException, SQLException{
    	UserDTO u = null;
    	UserAdressDTO ua = null;
    	con = DBUtil.useMysql();
    	Statement st = con.createStatement();
    	String sql1 = "select * from user where user.id = " + userId;
    	String sql2 = "selcet * from user_address where user_id = " + userId;
    	ResultSet rs = st.executeQuery(sql1);
    	ResultSet rs2 = st.executeQuery(sql2);
    	u.setEmail(rs.getString(5));
    	u.setLoginName(rs.getString(2));
    	u.setNickName(rs.getString(3));
    	u.setPassword(rs.getString(6));
    	u.setId(rs.getInt(1));
    	ua.setAdress(rs2.getString(1));
    	ua.setUserId(rs.getInt(2));
    	u.setUserAdress(ua);
    	return u;
    }
}
