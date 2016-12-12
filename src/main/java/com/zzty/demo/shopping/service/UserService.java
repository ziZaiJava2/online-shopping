package com.zzty.demo.shopping.service;

import java.sql.SQLException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.zzty.demo.shopping.dao.UserDAO;
import com.zzty.demo.shopping.dto.UserDTO;

public class UserService {
	
	@Autowired
	private UserDAO userDao;
	
	public void AddUser(UserDTO user) throws ClassNotFoundException, SQLException{
		userDao.addUser(user);
	}

	
}
