package com.zzty.demo.shopping.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.zzty.demo.shopping.dto.ProductionCateGoryDTO;
import com.zzty.demo.shopping.dto.UserAdressDTO;

public class ProductionCateGoryDAO {
	private Connection con = null;
    public boolean addProductionCateGory(ProductionCateGoryDTO productionCategory) throws ClassNotFoundException, SQLException{
    	con = DBUtil.useMysql();
    	String sql = "insert into production_category(production_id, category_id)values(?, ?)";
    	java.sql.PreparedStatement pst = con.prepareStatement(sql);
        pst.setInt(1, productionCategory.getProductionId().getId());
        pst.setInt(2, productionCategory.getCategoryId().getId());
       
        return pst.execute();
    }
    public boolean updateProductionCateGory(ProductionCateGoryDTO productionCategory) throws ClassNotFoundException, SQLException{
    	con = DBUtil.useMysql();
    	Statement st = con.createStatement();
    	if( !"".equals(productionCategory.getProductionId().getId())){
            String sql = "update production_category set category_id = " + productionCategory.getCategoryId().getId() + "where production_id = " + productionCategory.getProductionId().getId();
    	   return st.execute(sql);
    	}else{
            String sql = "update production_category set production_id = " + productionCategory.getProductionId().getId() + "where production_id = " + productionCategory.getProductionId().getId();
            return st.execute(sql);
    	}
     }
    
    public boolean deleteProductionCateGory(ProductionCateGoryDTO productionCategory) throws ClassNotFoundException, SQLException{
    	con = DBUtil.useMysql();
    	Statement st = con.createStatement();
    	String sql = "delete from production_category where id = " + productionCategory.getProductionId().getId();
    	return st.execute(sql);
    }
    
    public String getAllProductionCateGory() throws ClassNotFoundException, SQLException{
    	con = DBUtil.useMysql();
    	Statement st = con.createStatement();
    	String sql = "select * from production_category";
    	ResultSet rs = st.executeQuery(sql);
    	int production_id;
    	int category_id;
    	String message = "";
    	while(rs.next()){
    		production_id = rs.getInt(1);
    		category_id = rs.getInt(2);
    		message +="production_id: " + production_id + " " + "category_id: " + category_id + "\n";
    	}
    	return message;
    }
    
    public ProductionCateGoryDTO getUserProductionCateGory(int productionId) throws ClassNotFoundException, SQLException{
    	ProductionCateGoryDTO ua = null;
    	con = DBUtil.useMysql();
    	Statement st = con.createStatement();
    	String sql = "select * from  = production_category" + productionId;
    	ResultSet rs = st.executeQuery(sql);
    	
    	
    	
    	return ua;
    }
}
