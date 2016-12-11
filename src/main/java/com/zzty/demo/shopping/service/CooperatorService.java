package com.zzty.demo.shopping.service;

import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.zzty.demo.shopping.dao.CooperatorDAO;
import com.zzty.demo.shopping.dto.CooperatorDTO;

public class CooperatorService {
	@Autowired
	private CooperatorDAO cooperatorDao;

	public void addCooperator(CooperatorDTO cooperator) throws SQLException, ClassNotFoundException {
		cooperatorDao.addCooperator(cooperator);
	}

	public void deleteCooperator(int id) throws SQLException, ClassNotFoundException {
		cooperatorDao.deleteCooperator(id);
	}

	public void updateCooperator(CooperatorDTO cooperator) throws SQLException, ClassNotFoundException {
		cooperatorDao.updateCooperator(cooperator);
	}

	public void getCooperatorById(int id) throws SQLException, ClassNotFoundException {
		System.out.println(
				cooperatorDao.getCooperatorById(id).getId() + " " + cooperatorDao.getCooperatorById(id).getName() + " "
						+ cooperatorDao.getCooperatorById(id).getRegister_date());
	}

	public void getAllCooperator() throws SQLException, ClassNotFoundException {
		List<CooperatorDTO> list = cooperatorDao.getAllCooperator();
		System.out.println(list);
	}
}
