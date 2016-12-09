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
        pst.setString(2, production.getDecription());
        pst.setInt(3, production.getOriginalPrice());
        pst.setInt(4, production.getPrice());
        return pst.execute();
    }
    public boolean updateProduction(ProductionDTO production) throws ClassNotFoundException, SQLException{
    	con = DBUtil.useMysql();
    	Statement st = con.createStatement();
    	if(!"".equals(production.getName()) && production.getName() != null ){
            String sql = "update prodcution set name = " + production.getName() + "where id = " + production.getId();
    	   return st.execute(sql);
    	}else if(!"".equals(production.getDecription()) && production.getDecription() != null){
            String sql = "update prodcution set description = " + production.getDecription() + "where id = " + production.getId();
            return st.execute(sql);
    	}else if(!"".equals(production.getOriginalPrice())){
            String sql = "update production set original_price = " + production.getOriginalPrice() + "where id = " + production.getId();
            return st.execute(sql);
    	}else{
            String sql = "update production set price = " + production.getPrice() + "where id = " + production.getId();
            return st.execute(sql);
    	}
     }
    
    public boolean deleteProduction(ProductionDTO production) throws ClassNotFoundException, SQLException{
    	con = DBUtil.useMysql();
    	Statement st = con.createStatement();
    	String sql = "delete from production where id = " + production.getId();
    	return st.execute(sql);
    }
    
    public String getAllProduction() throws ClassNotFoundException, SQLException{
    	con = DBUtil.useMysql();
    	Statement st = con.createStatement();
    	String sql = "select * from production";
    	ResultSet rs = st.executeQuery(sql);
    	String name;
    	int original_price;
    	String description;
        int price;
    	String message = "";
    	while(rs.next()){
    		name = rs.getString(2);
    		description = rs.getString(3);
    		original_price = rs.getInt(4);
    		price = rs.getInt(5);
    		
    		message +="name: " + name + " " + "description: " + description + " " + "original_price: " + original_price + " " + "price:" + price + "\n";
    	}
    	return message;
    }
    
    public ProductionDTO getProduction(int productionId) throws ClassNotFoundException, SQLException{
    	ProductionDTO u = null;
    	
    	con = DBUtil.useMysql();
    	Statement st = con.createStatement();
    	String sql = "select * from production where production_id = " + productionId;
    	
    	ResultSet rs = st.executeQuery(sql);
    	
    	u.setName(rs.getString(2));
    	u.setDecription(rs.getString(3));
    	u.setOriginalPrice(rs.getInt(4));
    	u.setPrice(rs.getInt(5));
    	return u;
    }
}
