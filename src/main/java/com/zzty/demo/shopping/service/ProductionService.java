package com.zzty.demo.shopping.service;

import java.sql.SQLException;

import org.springframework.beans.factory.annotation.Autowired;

import com.zzty.demo.shopping.dao.ProductionDAO;
import com.zzty.demo.shopping.dto.ProductionDTO;

public class ProductionService {

	@Autowired
	private ProductionDAO productionDAO;
	
	public void addProduction(ProductionDTO production) throws ClassNotFoundException, SQLException{
		productionDAO.addProduction(production);
	}
	public void deleteProductionById(int id) throws SQLException{
		productionDAO.deleteProductonById(id);
	}
	public void updateProduction(ProductionDTO production) throws SQLException{
		productionDAO.updateProduction(production);		
	}
	public ProductionDTO getProduction(int id) throws SQLException{
		return productionDAO.getProductionById(id);
	}
}
