package com.zzty.demo.shopping.service;

import java.sql.SQLException;

import com.zzty.demo.shopping.dao.CooperatorDAO;
import com.zzty.demo.shopping.dto.CooperatorDTO;

public class CooperatorService {

	     CooperatorDAO cooperatorDao;
		 public void AddCooperator(CooperatorDTO cooperator) throws SQLException{
			 cooperatorDao.addCooperator(cooperator);
		 }


}
