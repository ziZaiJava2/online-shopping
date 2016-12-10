package com.zzty.demo.shopping.service;

import java.sql.SQLException;

import com.zzty.demo.shopping.dao.ProductionDAO;
import com.zzty.demo.shopping.dto.ProductionDTO;

public class ProductionService {


	private ProductionDAO productionDao;
	
	public void AddProduction(ProductionDTO production) throws ClassNotFoundException, SQLException{
		productionDao.addProduetion(production);
       }
	}
