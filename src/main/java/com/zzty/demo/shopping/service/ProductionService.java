package com.zzty.demo.shopping.service;

import java.sql.SQLException;

import org.springframework.beans.factory.annotation.Autowired;

import com.zzty.demo.shopping.dao.ProductionDAO;
import com.zzty.demo.shopping.dto.ProductionDTO;

public class ProductionService {
	@Autowired
	private ProductionDAO productionDao;

	public void addProduction(ProductionDTO production) throws SQLException, ClassNotFoundException {
		productionDao.addProduction(production);
	}

	public void deleteProduction(int productionId) throws SQLException, ClassNotFoundException {
		productionDao.deleteProduction(productionId);
	}

	public void getProductionById(int productionId) throws SQLException, ClassNotFoundException {
		productionDao.getProductionById(productionId);
	}

	public void getAllProduction() throws SQLException, ClassNotFoundException {
		productionDao.getAllProduction();
	}

	public void updateProduction(ProductionDTO production) throws SQLException, ClassNotFoundException {
		productionDao.updateProduction(production);
	}
}
